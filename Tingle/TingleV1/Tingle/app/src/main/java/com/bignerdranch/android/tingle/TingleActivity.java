package com.bignerdranch.android.tingle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TingleActivity extends AppCompatActivity {

    // GUI variables
    private Button addThing;
    private TextView lastAdded;
    private TextView newWhat, newWhere;
    //fake database
    private List<Thing> thingsDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingle);
        thingsDB= new ArrayList<Thing>();
        fillThingsDB();

        //Accessing GUI element
        lastAdded= (TextView) findViewById(R.id.last_thing);
        updateUI();
        // Button
        addThing= (Button) findViewById(R.id.add_button);
        // Textfields for describing a thing

        newWhat= (TextView) findViewById(R.id.what_text);
        newWhere=(TextView) findViewById(R.id.where_text);

        // view products click event
        addThing.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                if ((newWhat.getText().length()>0) && (newWhere.getText().length()>0 )){
                    thingsDB.add(
                            new Thing( newWhat.getText().toString(),
                                    newWhere.getText().toString()));
                    newWhat.setText(""); newWhere.setText("");
                    updateUI();
                }
            }
        });
    }
    // This method fill a few things into ThingsDB for testing
    private void fillThingsDB() {
        thingsDB.add(new Thing("Android Pnone", "Desk"));
        thingsDB.add(new Thing("Apple", "Fridge"));
        thingsDB.add(new Thing("Chromecast", "TV"));
        thingsDB.add(new Thing("Laptop", "Desk"));
        thingsDB.add(new Thing("Paper", "Desk"));
    }
    private void updateUI(){
        int s= thingsDB.size();
        if (s>0) lastAdded.setText(thingsDB.get(s-1).toString());
    }

}
