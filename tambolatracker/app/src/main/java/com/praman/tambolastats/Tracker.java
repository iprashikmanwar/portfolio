package com.praman.tambolastats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Tracker extends AppCompatActivity {
    private static final String TAG = "Tracker";

    RecyclerView recyclerView, hrecyclerview;
    RecyclerAdapter adapter;
    RecyclerViewAdapter hadapter;
    ArrayList<String> arrayList;
    ArrayList<String> Letters = new ArrayList<String>();
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        initRecyclerViewdapter();
        recyclerView = findViewById(R.id.recyclerview);
        setRecyclerView();
    }

    private void initRecyclerViewdapter(){
        Log.d(TAG,"initRecyclerViewAdapter: called");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.hrecyclerview);
        recyclerView.setLayoutManager(layoutManager);
        hadapter = new RecyclerViewAdapter(this, Letters);
        recyclerView.setAdapter(hadapter);
    }


    private void setHRecyclerView() {
        hrecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Tracker.this,LinearLayoutManager.HORIZONTAL,false);
        hrecyclerview.setLayoutManager(layoutManager);

    }

    private void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, getList());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<dataModel> getList(){
        ArrayList<dataModel> dataModelList = new ArrayList<dataModel>();

        Cursor cursor = new DBHelper(this).readalldata();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String N = cursor.getString(1);
            String M = cursor.getString(2);
            String C = cursor.getString(3);
            dataModel data = new dataModel(N,M,C);
            dataModelList.add(data);
            cursor.moveToNext();
        }
        return dataModelList;
    }

    public void rmvElement(View view){
        EditText et = findViewById(R.id.rmvElement);
        String inElma = et.getText().toString().replace(" ","");

        if(inElma.length() > 0){
            Letters.add(inElma);
            dbHelper.removeElement(inElma);
            adapter.updateSimple(getList());
            hadapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(Tracker.this,"Please enter the required name.", Toast.LENGTH_SHORT).show();
        }

    }

    public void resetGame(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(Tracker.this);
        builder.setMessage("Do you want to reset the game?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.resetGame();
                        Letters.clear();
                        adapter.updateSimple(getList());
                        hadapter.notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Decline", null);

        AlertDialog alert = builder.create();
        alert.show();



    }

}