package com.example.dakhllokharj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.dakhllokharj.asyncTask.InsertAsyncTask;
import com.example.dakhllokharj.database.MyDataBase;
import com.example.dakhllokharj.database.Receipt;

public class AddActivity extends AppCompatActivity {
    //var
    MyDataBase myDataBase;
    // ui component
    EditText edt_title, edt_amount;
    ImageButton imb_done, imb_back;
    RadioButton rab_withdraw, rab_deposit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        init();
        enableEditMode();

    }

    private void findViews() {
        edt_title = findViewById(R.id.edt_title);
        edt_amount = findViewById(R.id.edt_amount);
        imb_done = findViewById(R.id.imb_done);
        imb_back = findViewById(R.id.imb_back);
        rab_withdraw = findViewById(R.id.rab_withdraw);
        rab_deposit = findViewById(R.id.rab_deposit);

    }

    private void init() {
        myDataBase = MyDataBase.getInstance(this);
    }

    private void enableEditMode() {
        imb_done.setVisibility(View.VISIBLE);
        imb_back.setVisibility(View.INVISIBLE);
    }
    private void disableEditMode() {
        imb_done.setVisibility(View.INVISIBLE);
        imb_back.setVisibility(View.VISIBLE);
    }


    public void onClickAddActivity(View view) {
        switch (view.getId()) {
            case R.id.imb_back:
                onBackPressed();
                break;
            case R.id.imb_done:
                int amount=Integer.parseInt(edt_amount.getText().toString());
                if (rab_withdraw.isChecked()){
                    amount=amount*(-1);
                }
                new InsertAsyncTask(myDataBase.dao()).execute(new Receipt("mahdi", edt_title.getText().toString(),
                        "", "", "", "",
                        amount));
                disableEditMode();
                onBackPressed();
                break;

        }
    }
}
