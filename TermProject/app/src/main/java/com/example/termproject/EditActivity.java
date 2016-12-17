package com.example.termproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

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
    ImageView cam;
    Bitmap bitmap;
    Spinner option2;
    String location;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap)  data.getExtras().get("data");
        cam.setImageURI(data.getData());
        byte[] resl = bitmaptobyte(bitmap);
        DBHelper2 dbHelper2 = new DBHelper2(getApplicationContext(), "LOGGER2.db", null,1);
        dbHelper2.insertimage(resl);


    }

    public byte[] bitmaptobyte(Bitmap bmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytearr = stream.toByteArray();
        return bytearr;
    }

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
        cam = (ImageView) findViewById(R.id.imageView2);


        lat.setText("위도 : " + lati);
        lon.setText("경도 : " + longi);



        String[] str2 = getResources().getStringArray(R.array.spinnerArray2);
        ArrayAdapter<String> adapter22 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str2);
        option2 = (Spinner) findViewById(R.id.spinner2);
        option2.setAdapter(adapter22);

        option2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = (String)option2.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_event = write.getText().toString();
                dbHelp.insert(db_event,lati,longi,s_year,s_month,s_day,s_hour,s_minute,e_year,e_month,e_day,e_hour,e_minute,location);
                Toast.makeText(getApplicationContext(),"저장되었습니다",Toast.LENGTH_SHORT).show();
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


        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in,1);

            }
        });











    }
}
