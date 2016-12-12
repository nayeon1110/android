package com.example.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    boolean Isintent = false;
    String msg;
    ListView listView;
    ListViewAdapter adapter;
    Double lat_,lon_;
    private GoogleMap mMap;
    TextView statistics;
    String result;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final DBHelper dbHelp2 = new DBHelper(getApplicationContext(), "LOGGER.db" , null ,1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.list);
        spec1.setIndicator("List");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("Map");
        spec2.setContent(R.id.Map);


        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab 3");
        spec3.setContent(R.id.Walk);
        spec3.setIndicator("Walk");


        TabHost.TabSpec spec4 = tabHost.newTabSpec("Tab 4");
        spec4.setContent(R.id.check);
        spec4.setIndicator("Check");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
        tabHost.addTab(spec4);

        final TextView task_list = (TextView)findViewById(R.id.textView);
        final TextView walk = (TextView)findViewById(R.id.textView2);
        Button m_refresh = (Button) findViewById(R.id.button16);
        Button m_statistics = (Button) findViewById(R.id.button17);
        Button w_start = (Button) findViewById(R.id.button19);
        Button w_end = (Button) findViewById(R.id.button20);
        Button w_refresh = (Button) findViewById(R.id.button21);
        Button list_w = (Button) findViewById(R.id.button10); //list 버튼
        reset = (Button) findViewById(R.id.button12);
        ImageView walking = (ImageView) findViewById(R.id.imageView);
        walking.setImageResource(R.drawable.icon);
        statistics = (TextView) findViewById(R.id.textView14);

        result = dbHelp2.getResult();
        statistics.setText(result);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = dbHelp2.getResult();
                statistics.setText(result);
            }
        });


        list_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MapsActivity.this,Timeractivity.class);
                intent3.putExtra("lat",lat_);
                intent3.putExtra("lon",lon_);
                startActivity(intent3);



            }
        });


        SQLiteDatabase db = dbHelp2.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOGGER",null);





        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.text_list_view);
        listView.setAdapter(adapter);


        while(cursor.moveToNext())
        {
            msg = cursor.getString(1);
            adapter.addItem(msg);

        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//길게 클릭했을 때
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("삭제");
                alert.setMessage("이 리스트를 삭제하시겠습니까?");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        dbHelp2.delete(position);
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();

                        //DB delete하는 명령, 리스트뷰 갱신
                    }
                });
                alert.show();
                return false;
            }
        });













    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();//리스트뷰 갱신
   }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE) ;
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;


        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener);
        Toast.makeText(getApplicationContext(), "위치 확인 시작함. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }


    private class GPSListener implements LocationListener {
        Double latitude;
        Double longitude;
        Location location;

        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            getLat(latitude);
            getLongi(longitude);



            showCurrentLocation(latitude,longitude);





        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void getLat(double lat)
        {
            lat_ = lat;

        }
        public void getLongi(double lon)
        {
            lon_ = lon;
        }




    }


    private void showCurrentLocation(final Double latitude, final Double longitude) {
        // 현재 위치를 이용해 LatLon 객체 생성




        LatLng curPoint = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(curPoint));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

        MarkerOptions optFirst = new MarkerOptions();
        optFirst.position(curPoint);// 위도 • 경도
        optFirst.title("Current Position");// 제목 미리보기
        optFirst.snippet("Snippet");
        optFirst.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        mMap.addMarker(optFirst).showInfoWindow();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("lon",longitude);

                startActivity(intent);

                return false;
            }
        });



    }
}
