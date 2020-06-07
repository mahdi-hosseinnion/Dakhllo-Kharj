package com.example.dakhllokharj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

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
    public static final String CATEGORY_TAG = "دسته بندی ";
    //var
    boolean isNewCategoryAdded = false;
    MyDataBase myDataBase;
    ArrayAdapter<String> autocompletetxtAdapter;
    List<String> arrayList = new ArrayList<>();

    // ui component
    EditText edt_title, edt_amount, edt_person, edt_subTitle;
    ImageButton imb_done, imb_back, imb_addCate;
    RadioButton rab_withdraw, rab_deposit;
    Spinner spinner_category;

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

    private void subscribeToCategoryLiveData() {
        myDataBase.dao().getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(final List<Category> categories1) {
                arrayList.clear();
                arrayList.add(CATEGORY_TAG);
                for (Category category : categories1) {
                    arrayList.add(category.getCategory());
                }
                //notify data changed
                autocompletetxtAdapter = null;
                autocompletetxtAdapter = new ArrayAdapter<String>(AddActivity.this,
                        android.R.layout.simple_spinner_item, arrayList) {
                    @Override
                    public boolean isEnabled(int position) {
                        return super.isEnabled(position);
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };

                spinner_category.setAdapter(autocompletetxtAdapter);
                if (isNewCategoryAdded) {
                    spinner_category.setSelection(spinner_category.getCount() - 1);
                    isNewCategoryAdded = false;
                }

            }
        });

    }

    private void initAutoCompletetextView() {
        spinner_category = (Spinner) findViewById(R.id.spinner_category);
        //TODO SOME
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        findViewById(R.id.imb_addCate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: runned");
//                new InsertCategoryAsyncTask(myDataBase.dao()).execute(new Category(autoCompleteTextView.getText().toString(),0));
//            }
//        });

    }

    private void findViews() {
        edt_title = findViewById(R.id.edt_title);
        edt_amount = findViewById(R.id.edt_amount);
        edt_person = findViewById(R.id.edt_person);
        edt_subTitle = findViewById(R.id.edt_subTitle);
        imb_done = findViewById(R.id.imb_done);
        imb_back = findViewById(R.id.imb_back);
        imb_addCate = findViewById(R.id.imb_addCate);
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
        int a = 2 / 5 == 5 ? 5 : 54;
        switch (view.getId()) {
            case R.id.imb_back:
                onBackPressed();
                break;
            case R.id.imb_done:
                if (edt_title.getText().toString().isEmpty()) {
                    edt_title.setError("لطفا عنوانی را وارد کنید!");

                } else if (edt_amount.getText().toString().isEmpty()) {
                    edt_amount.setError("لطفا مبلقی را وارد کنید!");
                } else {
                    int amount = Integer.parseInt(edt_amount.getText().toString());
                    if (rab_withdraw.isChecked()) {
                        amount = amount * (-1);
                    }
                    new InsertAsyncTask(myDataBase.dao()).execute(new Receipt(edt_person.getText().toString(),
                            edt_title.getText().toString(),
                            edt_subTitle.getText().toString(),
                            (spinner_category.getSelectedItemPosition() > 0) ? spinner_category.getSelectedItem().toString() : "غیره",
                            Utility.getCurrentTime(),
                            ShamsiTarikh.getCurrentShamsidate(),
                            amount));
                    disableEditMode();
                    onBackPressed();
                }
                break;
            case R.id.imb_addCate:
                displayAlertDialog();
            case R.id.spinner_category:
                //nothing in category
                if (spinner_category.getCount() == 0) {
                    displayAlertDialog();
                }

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

    private void displayAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        FrameLayout layout = new FrameLayout(this);
        final EditText mNameOfCategory = new EditText(this);
        mNameOfCategory.setHint("نام دسته بندی");
        layout.addView(mNameOfCategory);
        dialogBuilder.setView(layout);
        dialogBuilder.setTitle("دخل و خرج");
        dialogBuilder.setMessage("اضافه کردن دسته بندی جدید");
        dialogBuilder.setCancelable(true);
        dialogBuilder.setPositiveButton("تایید", null);
        dialogBuilder.setNegativeButton("لغو", null);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();
//        dialogBuilder.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNameOfCategory.getText().toString().isEmpty()) {
                    new InsertCategoryAsyncTask(myDataBase.dao()).execute(new Category(mNameOfCategory.getText().toString()));
                    isNewCategoryAdded = true;
                    dialog.cancel();
                    dialog.dismiss();
                } else
                    mNameOfCategory.setError("لطفا نامی برای دسته بندی وارد کنید!");

            }
        });


    }
}
