package com.bignerdranch.android.tingle;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.tingle.DB.Thing;
import com.bignerdranch.android.tingle.DB.ThingsDB;


public class UIFragment extends Fragment {
    // GUI variables
    private Button addThing;
    private TextView lastAdded;
    private TextView newWhat, newWhere;
    private Button mListAllThings;

    //fake database
    private ThingsDB thingsDB;


    public interface toActivity { public void stateChange(); }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thingsDB= ThingsDB.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ui, container, false);

        //Accessing GUI element
        lastAdded= (TextView) v.findViewById(R.id.last_thing);
        updateUI();

        // Button
        addThing= (Button) v.findViewById(R.id.add_button);

        // Textfields for describing a thing
        newWhat= (TextView) v.findViewById(R.id.what_text);
        newWhere= (TextView) v.findViewById(R.id.where_text);

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

        mListAllThings = (Button) v.findViewById(R.id.list_All_Thing_button);
        mListAllThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ListActivity.class);
                startActivity(i);
            }
        });


        return v;
    }

    private void updateUI(){
        int s= thingsDB.size();
        if (s>0) lastAdded.setText(thingsDB.get(s-1).toString());
    }

}

