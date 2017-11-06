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

public class HomepageFragmentAdapterIncome extends RecyclerView.Adapter<HomepageFragmentAdapterIncome.Viewholder> {
    private final Activity activity;
    private final List<String> inName = new ArrayList<>();
    private final List<String> inAmount = new ArrayList<>();

    public HomepageFragmentAdapterIncome(final Activity activity) {
        this.activity = activity;
    }

    //method untuk menambah data inName inAmount dan mentrigger recyclerview
    public void add(final String upName, final String upAmount) {
        this.inName.add(upName);
        this.inAmount.add(upAmount);
        notifyItemInserted(this.inName.size());
    }

    //method untuk menyiapkan template recyclerview
    @Override
    public HomepageFragmentAdapterIncome.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomepageFragmentAdapterIncome.Viewholder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_home_fragment_in_text, parent, false));
    }

    //method untuk menyiapkan viewholder untuk mengisi elemen data-data ke textview
    @Override
    public void onBindViewHolder(HomepageFragmentAdapterIncome.Viewholder holder, int position) {
        holder.tv_income_name.setText(String.format("%s",inName.get(position)));
        holder.tv_income_amount.setText(String.format("%s",inAmount.get(position)));
    }

    //method untuk menghitung banyaknya data berdasarkan inName dan mentrigger notifyItemInserted
    @Override
    public int getItemCount() {
        int size = inName.size();
        return size;
    }

    //class untuk memasukkan viewholder ke textview
    public class Viewholder extends RecyclerView.ViewHolder {

        public final TextView tv_income_name;
        public final TextView tv_income_amount;
        public Viewholder(View itemView) {
            super(itemView);

            this.tv_income_name = (TextView) itemView.findViewById(R.id.tv_income_name);
            this.tv_income_amount = (TextView) itemView.findViewById(R.id.tv_income_amount);

        }
    }
}
