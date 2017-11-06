package com.example.triznylarasati.incomeexpense;

import android.app.Fragment;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.triznylarasati.incomeexpense.database.DatabaseHelper;
import com.example.triznylarasati.incomeexpense.homepage.HomepageFragmentAdapterExpense;
import com.example.triznylarasati.incomeexpense.homepage.HomepageFragmentAdapterIncome;

/**
 * Created by Trizny Larasati on 11/3/2017.
 */

public class Homepage extends Fragment {
    public Homepage(){}
    LinearLayout view;
    DatabaseHelper myDB;
    private TextView tv_total_expense,tv_total_income,tv_balance_amount;
    private RecyclerView fragmentHomeExRecycleView;
    private RecyclerView fragmentHomeInRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.homepage, container, false);
        myDB = new DatabaseHelper(getActivity());

        tv_total_expense = (TextView) view.findViewById(R.id.tv_total_expense);
        tv_total_income = (TextView) view.findViewById(R.id.tv_total_income);
        tv_balance_amount = (TextView) view.findViewById(R.id.tv_balance_amount);

        fragmentHomeExRecycleView = (RecyclerView) view.findViewById(R.id.fragmentHomeExRecyclerView);
        fragmentHomeInRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentHomeInRecyclerView);


        //menunjuk ke list_expense
        Cursor list_expense = myDB.list_expense();

        //jika belum ada data expense yang dimasukkan
        if (list_expense.getCount() == 0) {
            alert_message("Message", "No expense data found");
        }

        //menyiapkan layout adapter dan layout manager untuk recycler view untuk list_expense
        final HomepageFragmentAdapterExpense adapter = new HomepageFragmentAdapterExpense(getActivity());
        fragmentHomeExRecycleView.setAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragmentHomeExRecycleView.setLayoutManager(manager);

        //untuk menghitung total expense
        Integer total_expense = 0;
        while (list_expense.moveToNext()) {
            String name = list_expense.getString(1);
            String amount = list_expense.getString(2);
            String createdOn = list_expense.getString(3);
            Log.e("create",String.format("created On %s ",createdOn));
            adapter.add(name,amount);
            total_expense = total_expense + Integer.parseInt(amount);

        }

        //menampilkan hasil total expense dengan format titik
        tv_total_expense.setText(String.format("%,8d%n",total_expense));

        //Log.e("jumlah expense",String.valueOf(total_expense));



        //menunjuk ke list_income
        Cursor list_income = myDB.list_income();

        //jika belum ada data expense yang dimasukkan
        if (list_income.getCount() == 0) {
            alert_message("Message", "No income data found");
        }

        //menyiapkan layout adapter dan layout manager untuk recycler view untuk list_income
        final HomepageFragmentAdapterIncome adapterIn = new HomepageFragmentAdapterIncome(getActivity());
        fragmentHomeInRecyclerView.setAdapter(adapterIn);
        final LinearLayoutManager managerIn = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        fragmentHomeInRecyclerView.setLayoutManager(managerIn);

        Integer total_income = 0;
        while (list_income.moveToNext()) {
            String name = list_income.getString(1);
            String amount = list_income.getString(2);
            adapterIn.add(name,amount);
            total_income = total_income + Integer.parseInt(amount);
        }

        tv_total_income.setText(String.format("%,8d%n",total_income));

        tv_balance_amount.setText(String.format("%,8d%n",total_income-total_expense));


        return view;
    }

    public void alert_message(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
