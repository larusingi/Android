package com.example.lalli.rejseplanen.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lalli.rejseplanen.R;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

public class TravelFragment extends Fragment {

    static MainDB mainDB;
    static BankDB bankDB;
    String user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainDB= mainDB.get(getActivity());
        bankDB = bankDB.get(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.travel_fragment, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        user = preferences.getString("mobile","");
        User u = new User("","");
        u=mainDB.getUser(user);







        return v;
    }


}
