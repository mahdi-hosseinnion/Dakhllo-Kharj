package com.example.dakhllokharj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dakhllokharj.adapter.ReceiptRecyclerAdapter;
import com.example.dakhllokharj.asyncTask.GetAmountsAsyncTask;
import com.example.dakhllokharj.database.MyDataBase;
import com.example.dakhllokharj.database.Receipt;
import com.example.dakhllokharj.interfaces.TaskDelegate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FloatingActionButton.OnClickListener ,
        TaskDelegate
         {
    //var
    private static final String TAG = "MainActivity";
    MyDataBase myDataBase;
    private List<Receipt> mReceipt = new ArrayList<>();
    private ReceiptRecyclerAdapter mAdapter;
    //ui component
    private RecyclerView recycler_receipt;
    private FloatingActionButton fab_add;
    private TextView txt_mojodi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();

    }

    private void init() {
        //init data base
        myDataBase = MyDataBase.getInstance(this);
        //init recycler
        mAdapter = new ReceiptRecyclerAdapter(this, mReceipt);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recycler_receipt.setLayoutManager(linearLayoutManager);
        recycler_receipt.setAdapter(mAdapter);
        //set data to recyclerView
        myDataBase.dao().getAllReceipt().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                if (mReceipt.size() > 0) {
                    mReceipt.clear();
                }
                if (receipts != null) {
                    Collections.reverse(receipts);
                    mReceipt.addAll(receipts);
                }
                mAdapter.notifyDataSetChanged();

            }
        });
        //
    }

    private void findViews() {
        recycler_receipt = findViewById(R.id.recycler_receipt);
        txt_mojodi = findViewById(R.id.txt_mojodi);
        fab_add=findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,AddActivity.class));
    }

    @Override
    protected void onResume() {
        new GetAmountsAsyncTask(myDataBase.dao(),this).execute();
        super.onResume();
    }

    @Override
    public void onAmountsRetrieved(int sum) {
        NumberFormat formatter =new DecimalFormat("#,###,###,###");
        if (sum<0){
            txt_mojodi.setTextColor(Color.RED);
        }else
            txt_mojodi.setTextColor(Color.WHITE);
        txt_mojodi.setText("موجودی\n"+formatter.format(sum));
    }


}
