package com.example.lalli.rejseplanen.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lalli.rejseplanen.R;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.Trip;
import com.example.lalli.rejseplanen.dataB.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TripsOverviewFragment extends Fragment {

    static MainDB mainDB;
    static BankDB bankDB;
    TextView noTrips;
    String user;
    ArrayAdapter<Trip> adapter;
    List<Trip> tripList = new ArrayList<Trip>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trips_overview_fragment, container, false);

        mainDB = mainDB.get(getActivity());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        noTrips = (TextView) v.findViewById(R.id.noTripsLabel);
        user = preferences.getString("mobile", "");
        final User u = mainDB.getUser(user);

        try {
            tripList = mainDB.getTrips(u);
            List<Trip> tripLisst = new ArrayList<Trip>();


            if (tripList.size() == 0) {
                noTrips.setText("You have not made any trips");
            }
            adapter = new ArrayAdapter<Trip>(getActivity(), android.R.layout.simple_list_item_1, tripList);
            ListView lv = (ListView) v.findViewById(R.id.tripListView);
            lv.setAdapter(adapter);
        } catch (Exception e) {

        }


        return v;
    }
}
