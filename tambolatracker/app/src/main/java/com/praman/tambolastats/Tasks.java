package com.praman.tambolastats;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Tasks extends AppCompatActivity {
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ExpandAddAdditional(View view){
        CardView cardview = findViewById(R.id.card_view);

        if(cardview.getVisibility()==View.GONE){
            TransitionManager.beginDelayedTransition(cardview,new AutoTransition());
            cardview.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(cardview,new AutoTransition());
            cardview.setVisibility(View.GONE);
        }
    }

    public void startTacker(View view){
        Intent intent = new Intent(this,Tracker.class);
        startActivity(intent);
    }

    /*
     * Function to insert data to the existing database
     * @param view
     */
    public void AddAdditionalEntry(View view){
        EditText et = findViewById(R.id.name);
        String inName = et.getText().toString();

        if(inName.replace(" ","").length() > 0){
            if(dbHelper.countElement(inName).getCount()>0){
                AlertDialog.Builder builder = new AlertDialog.Builder(Tasks.this);
                builder.setTitle("Error!");
                builder.setMessage("Unable to add record.\n'"+ inName+"' already exists in the DB.");
                builder.setPositiveButton("OK",
                        (dialog, which) -> Toast.makeText(getApplicationContext(),inName+" not added",Toast.LENGTH_SHORT).show());
                builder.show();
            }else{
                int isAdded = dbHelper.addrecord(inName);
                if(isAdded>0){
                    Toast.makeText(Tasks.this,inName+" added.",Toast.LENGTH_SHORT).show();
                    /*TODO : clear EditText area after the data is added*/
                }else{
                    Toast.makeText(Tasks.this,"Something went wrong.\nUnable to add "+inName,Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(Tasks.this,"Please enter the required name.", Toast.LENGTH_SHORT).show();
        }

    }

    /* Function to get a toast of all the data available
     * @param view
     */
    public void ViewData(View view){
        @SuppressLint("Recycle") Cursor cursor = dbHelper.readalldata();

        if(cursor.getCount()==0){
            Toast.makeText(Tasks.this,"No Records Exist",Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder buffer = new StringBuilder();
        while(cursor.moveToNext()){
            buffer.append(" - ").append(cursor.getString(1)).append("\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Tasks.this);
        builder.setCancelable(true);
        builder.setTitle("Tambola Participants");
        builder.setMessage(buffer.toString());
        builder.show();
    }

    /* Function to delete all the existing data available at the database
     * @param view
     */
    public void TotalWipeOut(View view){
        dbHelper.deleteAllData();
        if(dbHelper.readalldata().getCount()>0){
            Toast.makeText(Tasks.this,"Something went wrong.\nUnable to wipe out data.",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Tasks.this,"Data cleared.",Toast.LENGTH_SHORT).show();
        }
    }

}