package com.bignerdranch.android.tingle;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TingleActivity  extends FragmentActivity implements TingleFragment.toActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingle);
        FragmentManager fm = getSupportFragmentManager();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            Fragment fragment = fm.findFragmentById(R.id.fragment_container);
            if (fragment == null) {
                fragment = new TingleFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }

        }

    }

    @Override
    public void stateChange() {
        FragmentManager fm= getSupportFragmentManager();
        Fragment fragmentListLand=  fm.findFragmentById(R.id.activity_fragment);

        ((ListFragment) fragmentListLand).stateChange();
    }

}
