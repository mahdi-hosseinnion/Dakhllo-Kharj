package com.example.dakhllokharj.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.dakhllokharj.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayHeaderViewHolder extends RecyclerView.ViewHolder {
    TextView txt_date,txt_income,txt_expenses;
    public DayHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_date=itemView.findViewById(R.id.txt_date);
        txt_income=itemView.findViewById(R.id.txt_income);
        txt_expenses=itemView.findViewById(R.id.txt_expenses);
    }
}
