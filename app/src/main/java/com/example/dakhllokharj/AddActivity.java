package com.example.dakhllokharj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.dakhllokharj.asyncTask.InsertAsyncTask;
import com.example.dakhllokharj.asyncTask.InsertCategoryAsyncTask;
import com.example.dakhllokharj.database.Category;
import com.example.dakhllokharj.database.MyDataBase;
import com.example.dakhllokharj.database.Receipt;
import com.example.dakhllokharj.util.ShamsiTarikh;
import com.example.dakhllokharj.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements
        TextWatcher {
    private static final String TAG = "AddActivity";
    //var
    MyDataBase myDataBase;
    ArrayAdapter<String> autocompletetxtAdapter;
    List<String> arrayList=new ArrayList<>();

    // ui component
    EditText edt_title, edt_amount, edt_person, edt_subTitle;
    ImageButton imb_done, imb_back;
    RadioButton rab_withdraw, rab_deposit;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        init();
        enableEditMode();
        initAutoCompletetextView();
        subscribeToCategoryLiveData();
    }
    private void subscribeToCategoryLiveData(){
        myDataBase.dao().getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(final List<Category> categories1) {
                arrayList.clear();
                for (Category category:categories1) {
                    arrayList.add(category.getCategory());
                }
                //notify data changed
                autocompletetxtAdapter=null;
                autocompletetxtAdapter= new ArrayAdapter<>(AddActivity.this,
                        android.R.layout.simple_dropdown_item_1line, arrayList);
                autoCompleteTextView.setAdapter(autocompletetxtAdapter);

            }
        });

    }
    private void initAutoCompletetextView() {
         autoCompleteTextView =  findViewById(R.id.actxt_category);
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                autoCompleteTextView.showDropDown();
            }
        });
        findViewById(R.id.imb_addCate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: runned");
                new InsertCategoryAsyncTask(myDataBase.dao()).execute(new Category(autoCompleteTextView.getText().toString(),0));
            }
        });

    }
    private void findViews() {
        edt_title = findViewById(R.id.edt_title);
        edt_amount = findViewById(R.id.edt_amount);
        edt_person = findViewById(R.id.edt_person);
        edt_subTitle = findViewById(R.id.edt_subTitle);
        imb_done = findViewById(R.id.imb_done);
        imb_back = findViewById(R.id.imb_back);
        rab_withdraw = findViewById(R.id.rab_withdraw);
        rab_deposit = findViewById(R.id.rab_deposit);

    }

    private void init() {
        myDataBase = MyDataBase.getInstance(this);
        edt_amount.addTextChangedListener(this);
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
                int amount = Integer.parseInt(edt_amount.getText().toString());
                if (rab_withdraw.isChecked()) {
                    amount = amount * (-1);
                }
                new InsertAsyncTask(myDataBase.dao()).execute(new Receipt(edt_person.getText().toString(),
                        edt_title.getText().toString(),
                        edt_subTitle.getText().toString(),
                        "Any",
                        Utility.getCurrentTime(),
                        ShamsiTarikh.getCurrentShamsidate(),
                        amount));
                disableEditMode();
                onBackPressed();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        edt_amount.removeTextChangedListener(this);
//
//        String str = convertNumberToEnglish(s.toString());
//        String str=edt_amount.getText().toString();
//
//        str = str.replace(",", "");
//        if (str.length() > 0) {
//            DecimalFormat sdd = new DecimalFormat("#,###,###,###");
//            Double doubleNumber = Double.parseDouble(str);
//
//            String format = sdd.format(doubleNumber);
//            edt_amount.setText(format);
//            edt_amount.setSelection(format.length());
//
//        }
//        edt_amount.addTextChangedListener(this);
    }

    public String convertNumberToEnglish(String num) {
        String d = num;
        d = d.replace("۰", "0");
        d = d.replace("۱", "1");
        d = d.replace("۲", "2");
        d = d.replace("٣", "3");
        d = d.replace("٤", "4");
        d = d.replace("۵", "5");
        d = d.replace("٦", "6");
        d = d.replace("٧", "7");
        d = d.replace("۸", "8");
        d = d.replace("۹", "9");

        return d;
    }
}
