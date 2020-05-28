package com.example.dakhllokharj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.dakhllokharj.adapter.ReceiptRecyclerAdapter;
import com.example.dakhllokharj.database.MyDataBase;
import com.example.dakhllokharj.database.Receipt;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        FloatingActionButton.OnClickListener {
    //var
    private static final String TAG = "MainActivity";
    MyDataBase myDataBase;
    private List<Receipt> mReceipt = new ArrayList<>();
    private ReceiptRecyclerAdapter mAdapter;
    //ui component
    private RecyclerView recycler_receipt;
    private FloatingActionButton fab_add;

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
        fab_add=findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,AddActivity.class));
    }
}
