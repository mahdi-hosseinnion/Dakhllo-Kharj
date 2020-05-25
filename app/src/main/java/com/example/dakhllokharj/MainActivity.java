package com.example.dakhllokharj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.dakhllokharj.adapter.ReceiptRecyclerAdapter;
import com.example.dakhllokharj.database.MyDataBase;
import com.example.dakhllokharj.database.Receipt;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //var
    private static final String TAG = "MainActivity";
    MyDataBase myDataBase;
    private List<Receipt> mReceipt = new ArrayList<>();
    private ReceiptRecyclerAdapter mAdapter;
    //ui component
    private RecyclerView recycler_receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();

    }

    private void init() {
        myDataBase = MyDataBase.getInstance(this);
        //init recycler
        mAdapter = new ReceiptRecyclerAdapter(this, mReceipt);
        recycler_receipt.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_receipt.setAdapter(mAdapter);
        myDataBase.dao().getAllReceipt().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                if (mReceipt.size() > 0) {
                    mReceipt.clear();
                }
                if (receipts != null) {
                    mReceipt.addAll(receipts);
                }

            }
        });
    }

    private void findViews() {
        recycler_receipt = findViewById(R.id.recycler_receipt);

    }
}
