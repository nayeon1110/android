package com.example.termproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditActivity extends AppCompatActivity {

    EditText write;
    TextView start;
    TextView end,d_s,t_s,d_e,t_e;
    TextView event,lat,lon;
    Button signup ;
    Button Back,Camera;
    Button Date_s,Time_s,Date_e,Time_e;
    int year,month,day,hour,minute;
    Double lati, longi;
    String db_event;
    int s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final DBHelper dbHelp = new DBHelper(getApplicationContext(), "LOGGER.db" , null ,1);

        Intent intent2 = getIntent();
        lati = intent2.getDoubleExtra("lat",0);
        longi = intent2.getDoubleExtra("lon",0);


        d_s = (TextView) findViewById(R.id.textView3);
        t_s = (TextView) findViewById(R.id.textView5);
        d_e = (TextView) findViewById(R.id.textView6);
        t_e = (TextView) findViewById(R.id.textView7);
        event = (TextView) findViewById(R.id.textView8);
        lat = (TextView) findViewById(R.id.textView9);
        lon = (TextView) findViewById(R.id.textView10);
        write = (EditText) findViewById(R.id.editText);

        signup = (Button) findViewById(R.id.button2);
        Back = (Button) findViewById(R.id.button3);
        Date_s = (Button) findViewById(R.id.button4);
        Time_s = (Button) findViewById(R.id.button6);
        Date_e = (Button) findViewById(R.id.button5);
        Time_e = (Button) findViewById(R.id.button7);
        Camera = (Button) findViewById(R.id.button9);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


        lat.setText("위도 : " + lati);
        lon.setText("경도 : " + longi);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_event = write.getText().toString();
                dbHelp.insert(db_event,lati,longi,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute);
            }
        });




        Date_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayofMonth) {
                        String msg = String.format("%d / %d / %d" , year, monthOfYear+1, dayofMonth);
                        d_s.setText(msg);
                        s_year = year;
                        s_month = monthOfYear+1;
                        s_day = dayofMonth;
                    }
                }, year, month, day).show();
            }
        });
        Time_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                        String msg = String.format("%d시 %d분" , hourofDay, minute);
                        t_s.setText(msg);
                        s_hour = hourofDay;
                        s_minute = minute;
                    }
                }, hour, minute, false).show();
            }
        });
        Date_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayofMonth) {
                        String msg = String.format("%d / %d / %d" , year, monthOfYear+1, dayofMonth);
                        d_e.setText(msg);
                        e_year = year;
                        e_month = monthOfYear+1;
                        e_day = dayofMonth;
                    }
                }, year, month, day).show();
            }
        });
        Time_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                        String msg = String.format("%d시 %d분" , hourofDay, minute);
                        t_e.setText(msg);
                        e_hour = hourofDay;
                        e_minute = minute;
                    }
                }, hour, minute, false).show();
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });






    }
}
