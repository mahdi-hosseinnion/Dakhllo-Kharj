package com.example.dakhllokharj.adapter;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dakhllokharj.R;
import com.example.dakhllokharj.database.Receipt;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReceiptRecyclerAdapter extends RecyclerView.Adapter<ReceiptRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Receipt> data;

    public ReceiptRecyclerAdapter(Context context, List<Receipt> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transcation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receipt receipt=data.get(position);
        holder.txt_title.setText(receipt.getTitle());
        holder.txt_amount.setText(""+receipt.getAmount());
        if (receipt.getTimeStamp()!=null)
        holder.txt_timeStamp.setText(""+receipt.getTimeStamp());
        else
            holder.txt_amount.setText("null");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title,txt_amount,txt_timeStamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_timeStamp = itemView.findViewById(R.id.txt_timeStamp);

        }
    }
}
