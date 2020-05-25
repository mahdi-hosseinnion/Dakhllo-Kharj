package com.example.dakhllokharj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;

import com.example.dakhllokharj.database.MyDataBase;
import com.example.dakhllokharj.database.Receipt;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //var
    private static final String TAG = "MainActivity";
    MyDataBase myDataBase;
    //ui component
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private void init(){
        myDataBase=MyDataBase.getInstance(this);

    }
}
