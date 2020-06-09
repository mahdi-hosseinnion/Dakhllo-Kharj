package com.example.dakhllokharj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dakhllokharj.adapter.OnReceiptListener;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements
        FloatingActionButton.OnClickListener
        , OnReceiptListener
        , TaskDelegate {
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
        mAdapter = new ReceiptRecyclerAdapter(mReceipt, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recycler_receipt.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_recycler_divider));
        recycler_receipt.addItemDecoration(itemDecorator);
        recycler_receipt.setAdapter(mAdapter);

        //set data to recyclerView
        myDataBase.dao().getAllReceipt().observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                if (receipts != null && receipts.size() > 0) {
                    Log.d(TAG, "onChanged: size in observer "+receipts.size());
                    if (mReceipt.size() > 0) {
                        mReceipt.clear();
                    }
                    if (receipts != null) {
                        //TODO uncomment this line
//                        Collections.reverse(receipts);
                        mReceipt.addAll(setHeaders(receipts));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        //
    }

    private List<Receipt> setHeaders(List<Receipt> receiptList) {
        String startDay = "01";
        String nowDate="00";
        List<Receipt> result = new ArrayList<>();
        for (int i = 0; i < receiptList.size(); i++) {
//1399/03/17
            if (receiptList.get(i).getDayTime().substring(5, 7).equals(startDay)) {
                result.add(receiptList.get(i));
                nowDate=receiptList.get(i).getDayTime().substring(6);
            } else {
                Receipt receipt1 = new Receipt();
                receipt1.setTitle(ReceiptRecyclerAdapter.HEADER_TITLE);
                int income = 0;
                int expenses = 0;
                for (int j = 0; j <= i; j++) {
                    int amount = receiptList.get(j).getAmount();
                    if (amount > 0) {
                        //+ positive(income)
                        income += amount;
                    } else {
                        //- negative (expenses)
                        expenses += (amount * -1);
                    }
                }
                receipt1.setAmount(expenses);
                receipt1.setCategory(String.valueOf(income));
                receipt1.setDayTime(nowDate);
                nowDate="";
                startDay=receiptList.get(i).getDayTime().substring(5, 7);
                result.add(receipt1);

            }
        }
        Log.d(TAG, "setHeaders: size in result "+result.size());
        return result;
    }

    private void findViews() {
        recycler_receipt = findViewById(R.id.recycler_receipt);
        txt_mojodi = findViewById(R.id.txt_mojodi);
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, AddActivity.class));
    }

    @Override
    protected void onResume() {
        new GetAmountsAsyncTask(myDataBase.dao(), this).execute();
        super.onResume();
    }

    @Override
    public void onAmountsRetrieved(int sum) {
        NumberFormat formatter = new DecimalFormat("#,###,###,###");
        if (sum < 0) {
            txt_mojodi.setTextColor(Color.RED);
        } else
            txt_mojodi.setTextColor(Color.WHITE);
        txt_mojodi.setText("موجودی شما \n" + formatter.format(sum));
    }


    @Override
    public void onReceiptClick(int position) {
        Toast.makeText(this, "click " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongRecipeClick(int position) {
        Toast.makeText(this, "long " + position, Toast.LENGTH_SHORT).show();
    }
}
