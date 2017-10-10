package com.example.lalli.rejseplanen.fragments;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.example.lalli.rejseplanen.Beacons;
import com.example.lalli.rejseplanen.MainMenu;
import com.example.lalli.rejseplanen.R;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.Trip;
import com.example.lalli.rejseplanen.dataB.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class TravelFragment extends Fragment {

    static MainDB mainDB;
    String user;
    ArrayAdapter<Beacons> adapter;
    List<Beacons> BeaconList = new ArrayList<Beacons>();
    TextView start;
    TextView curr;
    Button checkIn;
    Button checkOut;
    String finalTime;
    boolean locked = false;
    User u;
    List<String> zones = Arrays.asList("1", "2", "3", "4", "5");
    private BeaconManager beaconManager;
    private BeaconRegion region;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainDB = mainDB.get(getActivity());

        View v = inflater.inflate(R.layout.travel_fragment, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        user = preferences.getString("mobile", "");
        u = new User("", "");
        u = mainDB.getUser(user);

        Calendar c = Calendar.getInstance();
        String date = c.getTime().toString();
        String startTime = date.substring(3, 19);
        int length = date.length();
        String year = date.substring(length - 4, length);
        finalTime = startTime + " " + year;
        start = (TextView) v.findViewById(R.id.startStLabel);
        curr = (TextView) v.findViewById(R.id.travelExitStLabel);

        checkIn = (Button) v.findViewById(R.id.travelStartButton);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mainDB.getBalance(u) >= 60) {

                    if (BeaconList.size() > 0) {
                        System.out.println(" i go here");
                        String startST = Integer.toString(BeaconList.get(0).getMajor());
                        start.setText(startST);
                        locked = true;
                    }
                } else {
                    Context context = getActivity().getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    String text = "Balance to low";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });


        checkOut = (Button) v.findViewById(R.id.travelStopButton);
        checkOut.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            String sz = start.getText().toString().trim();
                                            String ez = curr.getText().toString().trim();

                                            if (sz.length() > 0 && ez.length() > 0) {

                                                int startzone = Integer.parseInt(sz);
                                                int endzone = Integer.parseInt(ez);
                                                int difference = startzone - endzone;
                                                int finalprice = 0;
                                                if (startzone > endzone) {
                                                    finalprice = (startzone - endzone) * 12;
                                                } else if (endzone > startzone) {
                                                    finalprice = (endzone - startzone) * 12;
                                                }
                                                if (finalprice > 0) {
                                                    if (mainDB.withdraw(u, finalprice)) {

                                                        Calendar ct = Calendar.getInstance();
                                                        String date2 = ct.getTime().toString();
                                                        String subTime = date2.substring(3, 19);
                                                        int length2 = date2.length();
                                                        String yearEnd = date2.substring(length2 - 4, length2);
                                                        String finalTimeEnd = subTime + " " + yearEnd;


                                                        Trip t = new Trip();
                                                        t.setmId(u.getmId().toString());
                                                        t.setStartST(Integer.toString(startzone));
                                                        t.setEndST(Integer.toString(endzone));
                                                        t.setStartTime(finalTime);
                                                        t.setEndTime(finalTimeEnd);
                                                        t.setTripprice(finalprice);
                                                        mainDB.addTrip(t);
                                                        //go to overview
                                                        Fragment fragment = new TripsOverviewFragment();
                                                        FragmentManager fragmentManager = getFragmentManager();
                                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                        transaction.replace(R.id.fragment1_container, fragment);
                                                        transaction.addToBackStack(null);
                                                        transaction.commit();

                                                    } else {
                                                        Context context = getActivity().getApplicationContext();
                                                        int duration = Toast.LENGTH_SHORT;
                                                        String text = "Problem with payment";
                                                        Toast toast = Toast.makeText(context, text, duration);
                                                        toast.show();
                                                    }
                                                }

                                            }
                                        }
                                    }
        );

        // beacon stuff
        beaconManager = new BeaconManager(getActivity());
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> list) {
                if (!list.isEmpty()) {

                    //set start station if its not locked

                    //now populate current station with the strongest signal
                    BeaconList.clear();
                    for (Beacon b : list) {

                        Beacons beac = new Beacons();

                        beac.setUUID(b.getProximityUUID().toString());
                        beac.setMajor(b.getMajor());
                        beac.setMinor(b.getMinor());
                        beac.setBeacPower(b.getRssi());

                        BeaconList.add(beac);
                    }

                    String destination = Integer.toString(list.get(0).getMajor());
                    curr.setText(destination);


                }

            }

        });
        region = new BeaconRegion("ranged region",
                UUID.fromString("E3B54450-AB73-4D79-85D6-519EAF0F45D9"), null, null);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(getActivity());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    public void onPause() {
        beaconManager.stopRanging(region);
        super.onPause();
    }
}
