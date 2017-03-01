package com.bignerdranch.android.tingle;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lalli on 28-02-2017.
 */

public class ListFragment extends Fragment {

    static private ArrayAdapter listAdapter;
    private static ThingsDB thingsDB;
    ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_thingslist, container, false);

        thingsDB= ThingsDB.get(getActivity());

        mListView = (ListView) v.findViewById(R.id.allThingsList);

        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, thingsDB.getThingsDB() );
        mListView.setAdapter(listAdapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                promptUser(position);
            }
        });

        return v;
    }


    private void promptUser(final int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(),"Item is deleted!", Toast.LENGTH_LONG).show();
                        listAdapter.notifyDataSetChanged();
                        listAdapter.notifyDataSetInvalidated();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void stateChange() { listAdapter.notifyDataSetChanged(); }
}
