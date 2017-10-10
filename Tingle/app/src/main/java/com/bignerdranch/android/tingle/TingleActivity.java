package com.bignerdranch.android.tingle;

import android.support.v4.app.*;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TingleActivity extends FragmentActivity implements UIFragment.toActivity {

    @Override
    public void stateChange() {
        FragmentManager fm= getSupportFragmentManager();
        Fragment fragmentListLand=  fm.findFragmentById(R.id.frag_container_list);

        ((ListFragment) fragmentListLand).stateChange();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingle);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new UIFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

    }
}
