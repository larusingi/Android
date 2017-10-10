package com.bignerdranch.android.tingle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bignerdranch.android.tingle.DB.ThingsDB;

public class ListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayAdapter listAdapter;
    private static ThingsDB thingsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        thingsDB= ThingsDB.get(this);
        mListView = (ListView) findViewById(R.id.thing_list_view);
        PopulateListView();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                ShowDialog(position);
            }
        });
    }



    void PopulateListView(){
        listAdapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, thingsDB.getThingsDB());
        mListView.setAdapter(listAdapter);
    }

    void ShowDialog(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        builder.setMessage("Are you sure that you want to delete this item ?");
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
                        PopulateListView();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

}


