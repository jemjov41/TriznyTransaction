package com.example.triznylarasati.incomeexpense.homepage;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.triznylarasati.incomeexpense.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trizny Larasati on 11/3/2017.
 */

public class HomepageFragmentAdapterExpense extends RecyclerView.Adapter<HomepageFragmentAdapterExpense.Viewholder>{
    private final Activity activity;
    private final List<String> exName = new ArrayList<>();
    private final List<String> exAmount = new ArrayList<>();

    public HomepageFragmentAdapterExpense(final Activity activity) {
        this.activity = activity;
    }

    //method untuk menambah data inName inAmount dan mentrigger recyclerview
    public void add(final String upName, final String upAmount) {
        this.exName.add(upName);
        this.exAmount.add(upAmount);
        notifyItemInserted(this.exName.size());
    }

    //method untuk menyiapkan template recyclerview
    @Override
    public HomepageFragmentAdapterExpense.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomepageFragmentAdapterExpense.Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_home_fragment_ex_text, parent, false));
    }

    //method untuk menyiapkan viewholder untuk mengisi elemen data-data ke textview
    @Override
    public void onBindViewHolder(HomepageFragmentAdapterExpense.Viewholder holder, int position) {
        holder.tv_expense_name.setText(String.format("%s",exName.get(position)));
        holder.tv_expense_amount.setText(String.format("%s",exAmount.get(position)));
    }

    //method untuk menghitung banyaknya data berdasarkan inName dan mentrigger notifyItemInserted
    @Override
    public int getItemCount() {
        int size = exName.size();
        return size;
    }

    //class untuk memasukkan viewholder ke textview
    public class Viewholder extends RecyclerView.ViewHolder {

        public final TextView tv_expense_name;
        public final TextView tv_expense_amount;
        public Viewholder(View itemView) {
            super(itemView);

            this.tv_expense_name = (TextView) itemView.findViewById(R.id.tv_expense_name);
            this.tv_expense_amount = (TextView) itemView.findViewById(R.id.tv_expense_amount);

        }
    }
}
