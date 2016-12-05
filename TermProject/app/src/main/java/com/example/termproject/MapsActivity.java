package com.example.termproject;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        Button l_edit = (Button) findViewById(R.id.button18);
        Button m_refresh = (Button) findViewById(R.id.button16);
        Button m_statistics = (Button) findViewById(R.id.button17);
        Button w_start = (Button) findViewById(R.id.button19);
        Button w_end = (Button) findViewById(R.id.button20);
        Button w_refresh = (Button) findViewById(R.id.button21);
        ImageView walking = (ImageView) findViewById(R.id.imageView);
        walking.setImageResource(R.drawable.icon);


        ListView listView;
        ListViewAdapter adapter;
        adapter = new ListViewAdapter();

        listView = (ListView) findViewById(R.id.text_list_view);
        listView.setAdapter(adapter);
        adapter.addItem("커피 마심");

        l_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, EditActivity.class);
                startActivity(intent);

            }
        });








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

            showCurrentLocation(latitude,longitude);





        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
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
                return false;
            }
        });



    }
}
