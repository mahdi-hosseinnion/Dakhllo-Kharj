package com.example.dakhllokharj.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
    public static String getCurrentTime(){
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        return format.format(new Date());
    }
}
