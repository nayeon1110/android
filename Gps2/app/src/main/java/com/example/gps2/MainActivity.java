package com.example.gps2;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    TextView tv2;
    ToggleButton tb;

    //final test coding

    static String p1[] = new String[144];
    static String p2[] = new String[144];
    static String p3[] = new String[144];

    static double gps_longitude[] = new double[144];
    static double gps_latitude[] = new double[144];
    static double gps_altitude[] = new double[144];


    int i=0;
    int a = 0;
    static String sum = "첫번째 경도,위도,고도는 \n";

    TextView mText;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tv = (TextView) findViewById(R.id.textView);
        tv.setText("위치정보 미수신중입니다");

        tb = (ToggleButton)findViewById(R.id.toggleButton);


        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        mText = (TextView)findViewById(R.id.textView2);

        mHandler = new Handler() {
            public void handleMessage(Message msg)
            {
                a++;


                p1[i] = String.valueOf(gps_altitude[i]);
                p2[i] = String.valueOf(gps_latitude[i]);
                p3[i] = String.valueOf(gps_longitude[i]);


                sum += p1[i]+ "\n" + p2[i] + "\n" +p3[i]+"\n";

                mText.setText(sum);
                sum += "저장한"+(i+2)+"번째 경도,위도,고도 \n";
                i++;
                mHandler.sendEmptyMessageDelayed(0, 30000);

            }
        };
        mHandler.sendEmptyMessage(0);

        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    if(tb.isChecked()){
                        tv.setText("수신중입니다");

                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                                100,
                                1,
                                mLocationListener);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                                100,
                                1,
                                mLocationListener);
                    }

                    else{
                        tv.setText("위치정보 미수신중입니다");
                        lm.removeUpdates(mLocationListener);
                    }
                }catch(SecurityException ex){
                }
            }
        });
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {


            Log.d("test", "onLocationChanged, location:" + location);
            double longitude = location.getLongitude();
            gps_longitude[i] = longitude;
            double latitude = location.getLatitude();
            gps_latitude[i] = latitude;
            double altitude = location.getAltitude();
            gps_altitude[i] = altitude;
            float accuracy = location.getAccuracy();
            String provider = location.getProvider();

            tv.setText("위치정보 : " + provider + "\n위도 : " + longitude + "\n경도 : " + latitude
                    + "\n고도 : " + altitude + "\n정확도 : "  + accuracy);


        }
        public void onProviderDisabled(String provider) {

            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {

            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };
}