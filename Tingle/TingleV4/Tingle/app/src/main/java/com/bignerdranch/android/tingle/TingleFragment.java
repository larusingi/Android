package com.bignerdranch.android.tingle;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalli on 25-02-2017.
 */

public class TingleFragment extends Fragment {
    private TingleActivity mTingleActivity;
    // GUI variables
    private Button addThing;
    private Button listAll;
    private TextView lastAdded;
    private TextView newWhat, newWhere;
    //fake database
    private static ThingsDB thingsDB;
    private  List<Thing> oldList;

    public interface toActivity { public void stateChange(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thingsDB= thingsDB.get(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tingle, container, false);
        lastAdded= (TextView) v.findViewById(R.id.last_thing);
        // Button for adding
        addThing= (Button) v.findViewById(R.id.add_button);
        // list all button

            listAll = (Button) v.findViewById(R.id.listAll_button);
            listAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start ListActivity
                    Intent i = new Intent(getActivity(), ListActivity.class);
                    startActivity(i);
                }
            });

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            listAll.setVisibility(View.GONE);
        }





        // Textfields for describing a thing

        newWhat= (TextView) v.findViewById(R.id.what_text);
        newWhere=(TextView) v.findViewById(R.id.where_text);

        // view products click event
        addThing.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                if ((newWhat.getText().length()>0) && (newWhere.getText().length()>0 )){
                    thingsDB.addThing(
                            new Thing( newWhat.getText().toString(),
                                    newWhere.getText().toString()));
                    newWhat.setText(""); newWhere.setText("");

                    updateUI();
                    //inform the Activity about the change state interface defintion
                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                        ((toActivity) getActivity()).stateChange();
                    }
                }


            }
        });




        updateUI();
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }

    // This method fill a few things into ThingsDB for testing
    private void fillThingsDB() {
        thingsDB.addThing(new Thing("Android Pnone", "Desk"));
        thingsDB.addThing(new Thing("Apple", "Fridge"));
        thingsDB.addThing(new Thing("Chromecast", "TV"));
        thingsDB.addThing(new Thing("Laptop", "Desk"));
        thingsDB.addThing(new Thing("Paper", "Desk"));
    }
    private void updateUI(){
        int s= thingsDB.size();
        if (s>0) lastAdded.setText(thingsDB.get(s-1).toString());
    }
}
