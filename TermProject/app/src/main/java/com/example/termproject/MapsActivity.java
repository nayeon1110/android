package com.example.termproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Calendar;
import java.util.Stack;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , SensorEventListener {

    boolean Isintent = false;
    String msg;
    ListView listView;
    ListViewAdapter adapter;
    Double lat_,lon_;
    private GoogleMap mMap;
    TextView statistics,walk;
    String result;
    Button reset;
    Spinner option,total;
    String op="";
    String to="";
    Button m_statistics,checking;
    SensorManager sm;
    Sensor sensor;
    int dir_UP=0;
    int dir_DOWN =0;
    int count =0;
    double acceleration =0;
    double gravity = 9.81;
    Stack<LatLng> stack1 ;
    LatLng lng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final DBHelper dbHelp2 = new DBHelper(getApplicationContext(), "LOGGER.db" , null ,1);
        final DBHelper3 dbHelper3 = new DBHelper3(getApplicationContext(),"LOGGER3.db",null,1);
        stack1 = new Stack<LatLng>();

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
        walk = (TextView)findViewById(R.id.textView2);
        m_statistics = (Button) findViewById(R.id.button17);
        Button w_end = (Button) findViewById(R.id.button20);
        Button w_refresh = (Button) findViewById(R.id.button21);
        checking = (Button) findViewById(R.id.button10);
        //reset = (Button) findViewById(R.id.button12);
        ImageView walking = (ImageView) findViewById(R.id.imageView);
        walking.setImageResource(R.drawable.icon);
        statistics = (TextView) findViewById(R.id.textView14);

        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        w_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count =0;
                walk.setText("" + 0 + " Steps!!");
            }
        });

        w_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar rightnow = Calendar.getInstance();
                int years = rightnow.get(Calendar.YEAR);
                int months = rightnow.get(Calendar.MONTH)+1;
                int dates = rightnow.get(Calendar.DATE);


                dbHelper3.Insertwalk(years,months,dates,count);

            }
        });




        String[] str = getResources().getStringArray(R.array.spinnerArray);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str);
        option = (Spinner) findViewById(R.id.spinner);
        option.setAdapter(adapter2);

        option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                op = (String) option.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] str3 = getResources().getStringArray(R.array.spinnerArray3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str3);
        total = (Spinner) findViewById(R.id.spinner3);
        total.setAdapter(adapter3);

        total.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                to = (String) total.getSelectedItem();




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });


        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(to.equals("하루통계"))
                {
                    if(op.equals("장소"))
                    {
                        statistics.setText(dbHelp2.getlocation_one());
                    }
                    else if(op.equals("시작시간"))
                    {
                        statistics.setText(dbHelp2.getstarttime_one());
                    }
                    else if (op.equals("걸음수"))
                    {
                        statistics.setText(dbHelper3.getwalk_one());
                    }
                }
                else if(to.equals("일주일통계"))
                {
                    if(op.equals("장소"))
                    {
                        statistics.setText(dbHelp2.getlocation_seven());
                    }
                    else if(op.equals("시작시간"))
                    {
                        statistics.setText(dbHelp2.getstarttime_seven());
                    }
                    else if(op.equals("걸음수"))
                    {
                        statistics.setText(dbHelper3.getwalk_seven());
                    }
                }


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



        cursor.moveToFirst();
        Calendar rightnow = Calendar.getInstance();
        int years = rightnow.get(Calendar.YEAR);
        int months = rightnow.get(Calendar.MONTH)+1;
        int dates = rightnow.get(Calendar.DATE);
        while(cursor.moveToNext())
        {

            if(cursor.getInt(4) == years && cursor.getInt(5) == months && cursor.getInt(6) == dates)
            {
                lng = new LatLng(cursor.getDouble(2),cursor.getDouble(3));
                stack1.push(lng);
            }
        }




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {//길게 클릭했을 때
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, final long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("삭제");
                alert.setMessage("이 리스트를 삭제하시겠습니까?");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        dbHelp2.delete(id,dbHelp2);
                        adapter.deleteItem(position);
                        adapter.notifyDataSetChanged();

                        //DB delete하는 명령, 리스트뷰 갱신
                    }
                });
                alert.show();
                return false;
            }
        });


        m_statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.addPolyline(new PolylineOptions().addAll(stack1).width(5).color(Color.RED));


            }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();//리스트뷰 갱신
        sm.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
   }


    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            acceleration = Math.sqrt(x*x+y*y+z*z);
        }
        if(acceleration - gravity >5) {
            dir_UP =1;
        }
        if(dir_UP == 1 && gravity - acceleration > 5)
        {
            dir_DOWN =1;
        }

        if(dir_DOWN == 1)
        {
            count++;
            walk.setText("" + count + " Steps!!");

            dir_UP =0;
            dir_DOWN =0;
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

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
