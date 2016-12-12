package com.example.termproject;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 김나연 on 2016-11-29.
 */

public class ListViewAdapter extends BaseAdapter {
    long timeWhenstopped=0;

    private ArrayList<ListViewItem> listViewItems  = new ArrayList<ListViewItem>();
    //Adapter에 추가된 데이터를 저장하기 위한 Arraylist

    public ListViewAdapter()
    {

    }


    @Override
    public int getCount() {//Adapter에 사용되는 데이터의 개수를 리턴
        return listViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView2) ;
        Button save = (Button) convertView.findViewById(R.id.save);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView4) ;
        Switch switchview = (Switch) convertView.findViewById(R.id.switch1);
        final Chronometer chs = (Chronometer)convertView.findViewById(R.id.chronometer2);



        // Data Set(listViewItems)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItems.get(position);//생성한 클래스

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        switchview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    chs.setBase(SystemClock.elapsedRealtime()+timeWhenstopped);
                    chs.start();
                }
                else {
                    timeWhenstopped = chs.getBase() - SystemClock.elapsedRealtime();
                    chs.stop();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chs.setBase(SystemClock.elapsedRealtime());
                timeWhenstopped = 0;
            }
        });

        return convertView;

    }

    public void addItem(String str)//아이템 데이터 추가를 위한 함수
    {
        ListViewItem item = new ListViewItem();

       // item.setIcon(icon);
        item.setTitle(str);

        listViewItems.add(item);



    }
    public void deleteItem(int position)
    {
        listViewItems.remove(position);
    }






}
