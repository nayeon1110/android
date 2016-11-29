package com.example.termproject;

/**
 * Created by 김나연 on 2016-11-29.
 */

import android.graphics.drawable.Drawable;
import android.widget.Chronometer;
import android.widget.Switch;

public class ListViewItem {
    private Drawable iconDrawble;
    private String event;
    private Switch on_off;
    private Chronometer timer;


    public void setIcon (Drawable icon)
    {
        iconDrawble = icon;
    }

    public void setTitle (String str)
    {
        event = str;
    }
    public void setSwitch (Switch sw)
    {
        on_off = sw;
    }
    public void setTimer (Chronometer ch)
    {
        timer = ch;
    }

    public Drawable getIcon()
    {
        return this.iconDrawble;
    }
    public String getTitle()
    {
        return this.event;
    }
    public Switch getswitch()
    {
        return this.on_off;
    }
    public Chronometer gettimer()
    {
        return this.timer;
    }

}
