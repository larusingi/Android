package com.bignerdranch.android.tingle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    ListView mListView;
    private Button backButton;
    ArrayAdapter listAdapter;
    private static ThingsDB thingsDB;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    FragmentManager fm = getSupportFragmentManager();
    Fragment fragment = fm.findFragmentById(R.id.fragment_containerlist);
    if (fragment == null) {
        fragment = new ListFragment();
        fm.beginTransaction()
                .add(R.id.fragment_containerlist, fragment)
                .commit();
    }
}
/*    thingsDB= ThingsDB.get(this);

    mListView = (ListView) findViewById(R.id.allThingsList);
    populateList();

    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                final int position, long id) {
            promptUser(position);
        }
    });*/
/*
}

    private void populateList(){
        listAdapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, thingsDB.getThingsDB() );
        mListView.setAdapter(listAdapter);
    }

    private void promptUser(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setMessage("Are you sure that you want delete : "+mListView.getItemAtPosition(index)+" ?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        thingsDB.deleteThing(index);
                        Toast.makeText(ListActivity.this,"Item is deleted!", Toast.LENGTH_LONG).show();
                        populateList();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }*/
}
