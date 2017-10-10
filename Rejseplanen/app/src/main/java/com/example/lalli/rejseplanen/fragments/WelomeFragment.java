package com.example.lalli.rejseplanen.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lalli.rejseplanen.MainMenu;
import com.example.lalli.rejseplanen.R;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

public class WelomeFragment extends Fragment {

    static MainDB mainDB;
    static BankDB bankDB;
    TextView welcomeN;
    TextView welcomeBal;
    Button startB;
    String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainDB = mainDB.get(getActivity());
        bankDB = bankDB.get(getActivity());

        View v = inflater.inflate(R.layout.welome_fragment, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        user = preferences.getString("mobile", "");
        User u = new User("", "");
        u = mainDB.getUser(user);

        welcomeN = (TextView) v.findViewById(R.id.welcomeName);
        welcomeN.setText("Welcome  " + u.getMfirstname());

        welcomeBal = (TextView) v.findViewById(R.id.welcomeBalance);
        welcomeBal.setText("Balance : " + u.getCredit());

        startB = (Button) v.findViewById(R.id.welcomeStartButton);
        startB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loadFragment("travel");


            }
        });

        return v;
    }

    //Create method replace fragment
    private void loadFragment(String fragmentPar) {
        Fragment fragment = null;
        switch (fragmentPar) {
            case "travel":
                fragment = new TravelFragment();
                break;
            default:
                fragment = new WelomeFragment();
                break;
        }

        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment1_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


}
