package com.example.termproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditActivity extends AppCompatActivity {

    TextView start;
    TextView end;
    Button signup ;
    DatePicker date ;
    TimePicker time;
    Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        start = (TextView) findViewById(R.id.textView5);
        end = (TextView) findViewById(R.id.textView6);
        signup = (Button) findViewById(R.id.button2);
        date = (DatePicker) findViewById(R.id.datePicker2);
        time = (TimePicker) findViewById(R.id.timePicker3);
        Back = (Button) findViewById(R.id.button3);

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {

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
