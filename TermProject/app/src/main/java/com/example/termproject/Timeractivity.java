package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Timeractivity extends AppCompatActivity {

    TextView Event,lat,lon;
    EditText write;
    Button save;
    Double lati,longi;
    String msg;
    int s_year=0,s_month=0,s_day=0,s_hour=0,s_minute=0,e_year=0,e_month=0,e_day=0,e_hour=0,e_minute=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeractivity);


        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "LOGGER.db" , null ,1);
        Intent intent = getIntent();
        lati= intent.getDoubleExtra("lat",0);
        longi = intent.getDoubleExtra("lon",0);

        Event = (TextView) findViewById(R.id.textView11);
        lat = (TextView) findViewById(R.id.textView12);
        lon = (TextView) findViewById(R.id.textView13);
        write = (EditText) findViewById(R.id.editText2);
        save = (Button) findViewById(R.id.button8);


        lat.setText("위도 : " + lati);
        lon.setText("경도 : " + longi);






        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg = write.getText().toString();

                dbHelper.insert(msg,lati,longi,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute);

                Intent intent1 = new Intent(Timeractivity.this , MapsActivity.class);
                startActivity(intent1);

            }
        });






    }
}
