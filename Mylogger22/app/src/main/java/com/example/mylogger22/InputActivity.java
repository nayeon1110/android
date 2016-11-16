package com.example.mylogger22;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    String title_;
    String content_;
    String category_ ="";
    Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent2 = getIntent();
        lat = intent2.getDoubleExtra("lat",0);
        lon = intent2.getDoubleExtra("lon",0);


        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Statistics.db" , null ,1);

        String[] str = getResources().getStringArray(R.array.spinnerArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,str);
        final Spinner spi = (Spinner)findViewById(R.id.spinner);
        spi.setAdapter(adapter);


        spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category_ = (String)spi.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText title = (EditText)findViewById(R.id.editText);
        title.setHint("제목을 입력하세요");


        final EditText content = (EditText)findViewById(R.id.editText2);
        content.setHint("내용을 입력하세요");



        Button back = (Button)findViewById(R.id.button6);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Button signup = (Button)findViewById(R.id.button5);
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                title_ = title.getText().toString();
                content_ = content.getText().toString();

                dbHelper.insert(category_,lat,lon,title_,content_);


                Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });










    }






}
