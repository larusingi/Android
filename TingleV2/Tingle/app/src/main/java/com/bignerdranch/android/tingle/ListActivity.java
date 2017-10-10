package com.bignerdranch.android.tingle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {

    private Button backButton;
    ArrayAdapter listAdapter;
    private static ThingsDB thingsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        thingsDB= ThingsDB.get(this);
        listAdapter = new ArrayAdapter<>(ListActivity.this, android.R.layout.simple_list_item_1, thingsDB.getThingsDB() );
        ((ListView) findViewById(R.id.allThingsList)).setAdapter(listAdapter);



        backButton = (Button) findViewById(R.id.backToTingleButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListActivity.this, TingleActivity.class);
                startActivity(i);
            }
        });

    }
}
