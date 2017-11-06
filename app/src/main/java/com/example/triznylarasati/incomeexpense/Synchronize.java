package com.example.triznylarasati.incomeexpense;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.triznylarasati.incomeexpense.database.DatabaseHelper;
import com.example.triznylarasati.incomeexpense.entities.*;
import com.example.triznylarasati.incomeexpense.entities.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Trizny Larasati on 11/3/2017.
 */

public class Synchronize extends Fragment {
    public Synchronize(){}
    private String TAG = "Synchronize";
    RelativeLayout view;
    DatabaseHelper myDB;

    //ini program buat ngesyncronyze sqlite dan firebase jika ada jumlah data yang berbeda maka data yang kurang akan di masukan sehingga jumlah dari ke2 db menjadi sama
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myDB = new DatabaseHelper(getActivity());
        view = (RelativeLayout) inflater.inflate(R.layout.synchronize, container, false);

        getActivity().setTitle("Synchronize");


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //syncronize expense table
        DatabaseReference myRef = database.getReference("expense");
        //get expense table firebase
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Cursor list_expense = myDB.list_expense();
                list_expense.getCount();
                Log.e("synchronize",String.format("count sqlite ex %s ", list_expense.getCount()));
                Log.d(TAG, String.format("expense firebase %s ", dataSnapshot.getChildrenCount()));
                //cek jumlah keduanya
                if(list_expense.getCount()!=dataSnapshot.getChildrenCount()){
                    //jika jumlah berbeda dan nilai di sql lebih sedikit dari firebase
                    if(list_expense.getCount()<dataSnapshot.getChildrenCount()){
                        //mencari data yang tidak ada di sql
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            while (list_expense.moveToNext()){
                                String createdOnSql = list_expense.getString(3);
                                Transaction transFire = postSnapshot.getValue(com.example.triznylarasati.incomeexpense.entities.Transaction.class);
                                String createdOnFirebase = transFire.getCreatedOn();
                                //cek data sama atau tidak
                                //difTime adalah method untuk mencari tahu apakah data sama atau tidak berdasarkan waktu pembuatan data
                                Long timedif = difTime(createdOnSql,createdOnFirebase);
                                if(timedif!=0){
                                    //ketemu data yang kurang di sql dan insert data yang kurang ke sql
                                    myDB.save_expense(transFire.getName(),
                                            transFire.getAmount(),transFire.getCreatedOn());
                                }
                            }
                        }
                    }
                    //jika jumlah berbeda dan nilai di firebase lebih sedikit dari sql
                    if(list_expense.getCount()>dataSnapshot.getChildrenCount()){
                        //mencari data yang tidak ada di firebase
                        while (list_expense.moveToNext()){
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                String createdOnSql = list_expense.getString(3);
                                Transaction transFire = postSnapshot.getValue(com.example.triznylarasati.incomeexpense.entities.Transaction.class);
                                String createdOnFirebase = transFire.getCreatedOn();
                                //cek data sama atau tidak
                                //difTime adalah method untuk mencari tahu apakah data sama atau tidak berdasarkan waktu pembuatan data
                                Long timedif = difTime(createdOnSql,createdOnFirebase);
                                if(timedif!=0){
                                    //ketemu data yang kurang di firebase dan insert data yang kurang ke firebase
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    UUID uniqueKey = UUID.randomUUID();
                                    DatabaseReference myRef = database.getReference("expense");
                                    com.example.triznylarasati.incomeexpense.entities.Transaction transactionPost = new com.example.triznylarasati.incomeexpense.entities.Transaction();
                                    transactionPost.setName(list_expense.getString(1));
                                    transactionPost.setAmount( list_expense.getString(2));
                                    transactionPost.setCreatedOn(list_expense.getString(3));
                                    transactionPost.setFlag("expense");
                                    myRef.child(uniqueKey.toString()).setValue(transactionPost);
                                }
                            }
                        }
                    }
                }else{
                    Log.d(TAG, String.format("same count"));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //syncronize income table
        DatabaseReference myRefIn = database.getReference("income");
        //flow sama kaya expense beda table query saja jadi income
        myRefIn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //jadi query income di sql
                Cursor list_income = myDB.list_income();
                Log.e("synchronize",String.format("count sqlite ex %s ", list_income.getCount()));
                Log.d(TAG, String.format("expense firebase %s ", dataSnapshot.getChildrenCount()));
                if(list_income.getCount()!=dataSnapshot.getChildrenCount()){
                    if(list_income.getCount()<dataSnapshot.getChildrenCount()){
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            while (list_income.moveToNext()){
                                String createdOnSql = list_income.getString(3);
                                Transaction transFire = postSnapshot.getValue(com.example.triznylarasati.incomeexpense.entities.Transaction.class);
                                String createdOnFirebase = transFire.getCreatedOn();
                                Long timedif = difTime(createdOnSql,createdOnFirebase);
                                if(timedif!=0){
                                    myDB.save_expense(transFire.getName(),
                                            transFire.getAmount(),transFire.getCreatedOn());
                                }
                            }
                        }
                    }
                    if(list_income.getCount()>dataSnapshot.getChildrenCount()){
                        while (list_income.moveToNext()){
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                String createdOnSql = list_income.getString(3);
                                Transaction transFire = postSnapshot.getValue(com.example.triznylarasati.incomeexpense.entities.Transaction.class);
                                String createdOnFirebase = transFire.getCreatedOn();
                                Long timedif = difTime(createdOnSql,createdOnFirebase);
                                if(timedif!=0){
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    UUID uniqueKey = UUID.randomUUID();
                                    //data insert child jadi income
                                    DatabaseReference myRef = database.getReference("income");
                                    com.example.triznylarasati.incomeexpense.entities.Transaction transactionPost = new com.example.triznylarasati.incomeexpense.entities.Transaction();
                                    transactionPost.setName(list_income.getString(1));
                                    transactionPost.setAmount( list_income.getString(2));
                                    transactionPost.setCreatedOn(list_income.getString(3));
                                    //data insert child jadi income
                                    transactionPost.setFlag("income");
                                    myRef.child(uniqueKey.toString()).setValue(transactionPost);
                                }
                            }
                        }
                    }
                }else{
                    Log.d(TAG, String.format("same count"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        return view;
    }
    //method untuk mencari tahu data sama atau tidak berdasarkan waktu pembuatan
    protected long difTime(String createOnSql, String createOnFirebase){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long diffSeconds=0;
        try {
            Date d1 = formatter.parse(createOnSql);
            Date d2 = formatter.parse(createOnFirebase);

            long diff = d2.getTime() - d1.getTime();
            diffSeconds = diff / 1000 % 60;

        } catch (Exception e ){
            e.printStackTrace();
        }
        return diffSeconds;
    }

}
