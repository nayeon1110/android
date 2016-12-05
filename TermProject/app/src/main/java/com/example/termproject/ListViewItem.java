package com.example.termproject;

/**
 * Created by 김나연 on 2016-11-29.
 */

import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Switch;

public class ListViewItem {
    private String event;
    private Switch on_off;
    private Button save;
    private Chronometer timer;



    public void setButton (Button btn) {save = btn;}
    public void setTitle (String str)
    {
        event = str;
    }
    public void setSwitch (Switch sw) {on_off = sw;}
    public void setTimer (Chronometer ch)
    {
        timer = ch;
    }



    public Button getButton() {return this.save;}
    public String getTitle()
    {
        return this.event;
    }
    public Switch getSwitch() {  return this.on_off;}
    public Chronometer getTimer()
    {
        return this.timer;
    }

}
