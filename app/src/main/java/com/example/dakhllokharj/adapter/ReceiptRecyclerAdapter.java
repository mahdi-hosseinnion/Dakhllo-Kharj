package com.example.dakhllokharj.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dakhllokharj.R;
import com.example.dakhllokharj.database.Receipt;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ReceiptRecyclerAdapter";
    private static final int RECEIPT_TYPE = 1703;
    private static final int HEADER_TYPE = 1422;
    public static final String HEADER_TITLE="ITS HEADER.45649";
    private List<Receipt> mReceipt;
    private OnReceiptListener mOnReceiptListener;

    public ReceiptRecyclerAdapter(List<Receipt> data, OnReceiptListener onReceiptListener) {
        this.mReceipt = data;
        this.mOnReceiptListener = onReceiptListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_day_header_list_item, parent, false);
                return new DayHeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transcation, parent, false);
                return new ViewHolder(view, mOnReceiptListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Receipt receipt = mReceipt.get(position);
        int viewType = getItemViewType(position);
        if (viewType == RECEIPT_TYPE) {
            ((ViewHolder) holder).txt_title.setText(receipt.getTitle());
            ((ViewHolder) holder).txt_category.setText(receipt.getCategory());
            NumberFormat formatter = new DecimalFormat("#,###,###,###");
            ((ViewHolder) holder).txt_amount.setText("" + formatter.format(receipt.getAmount()));
            ((ViewHolder) holder).txt_amount.setTextColor(receipt.getAmount() < 0 ? Color.RED : Color.BLUE);
//            if (receipt.getDayTime() != null&&receipt.getSpecificTime() != null)
//                ((ViewHolder) holder).txt_timeStamp.setText(((receipt.getDayTime()).substring(2)) + "\n" + receipt.getSpecificTime());
//            else
//                ((ViewHolder) holder).txt_amount.setText("null");
        } else {
            ((DayHeaderViewHolder)holder).txt_date.setText(receipt.getDayTime());
            ((DayHeaderViewHolder)holder).txt_expenses.setText("Expenses: "+receipt.getAmount());
            ((DayHeaderViewHolder)holder).txt_income.setText("Income: "+receipt.getCategory());
        }
    }

    @Override
    public int getItemCount() {
        if (mReceipt != null){

            Log.d(TAG, "getItemCount: size = "+mReceipt.size());
            return mReceipt.size();
        }
        return 0;

    }

    @Override
    public int getItemViewType(int position) {
        if (mReceipt.get(position).getTitle().equals(HEADER_TITLE)){
            return HEADER_TYPE;
        }else
            return RECEIPT_TYPE;

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView txt_title, txt_amount, txt_category;
        OnReceiptListener mOnReceiptListener;

        public ViewHolder(@NonNull View itemView, OnReceiptListener onReceiptListener) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_amount = itemView.findViewById(R.id.txt_amount);
//            txt_timeStamp = itemView.findViewById(R.id.txt_timeStamp);
            txt_category = itemView.findViewById(R.id.txt_category);
            this.mOnReceiptListener = onReceiptListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mOnReceiptListener.onReceiptClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            mOnReceiptListener.onLongRecipeClick(getAdapterPosition());
            return false;
        }
    }
}
