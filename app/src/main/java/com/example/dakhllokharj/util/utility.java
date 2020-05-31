package com.example.dakhllokharj.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class utility {
    public static String getCurrentTime(){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return format.format(new Date());
    }
}
