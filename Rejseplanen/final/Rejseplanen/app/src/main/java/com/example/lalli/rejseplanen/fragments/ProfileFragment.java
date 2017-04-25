package com.example.lalli.rejseplanen.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lalli.rejseplanen.R;
import com.example.lalli.rejseplanen.dataB.BankAccount;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

public class ProfileFragment extends Fragment {

    static MainDB mainDB;
    static BankDB bankDB;
    String user;

    TextView firstN;
    TextView lastN;
    TextView mobile;
    EditText mail;
    EditText pass;
    EditText creditNR;
    EditText credcrc;
    Button updateUserButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        //find out who the user is from the shared prefereces and get his data from db
        mainDB = mainDB.get(getActivity());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        user = preferences.getString("mobile", "");
        final User u = mainDB.getUser(user);

        //instansiate UI-components
        mobile = (TextView) v.findViewById(R.id.profileMobileLabel);
        firstN = (TextView) v.findViewById(R.id.profileFirstName);
        lastN = (TextView) v.findViewById(R.id.profileLastName);
        mail = (EditText) v.findViewById(R.id.profileEmail);
        pass = (EditText) v.findViewById(R.id.profilePass);
        creditNR = (EditText) v.findViewById(R.id.profileCcard);
        credcrc = (EditText) v.findViewById(R.id.profileCRC);
        updateUserButton = (Button) v.findViewById(R.id.updateProfileButton);

        //give them values from db
        mobile.setText("Mobile : " + u.getMobile());
        firstN.setText("First Name : " + u.getMfirstname());
        lastN.setText("Last Name : " + u.getMlastname());
        mail.setText(u.getEmail());
        pass.setText(u.getPassword());
        creditNR.setText(u.getMcreditcardnr());
        credcrc.setText(u.getMcrc());

        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newMail = mail.getText().toString();
                String newPass = pass.getText().toString();
                String newcred = creditNR.getText().toString();
                String newcrc = credcrc.getText().toString();
                Context context = getActivity();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;

                if (newMail.length() > 0 && newPass.length() > 0 && newcred.length() > 13 && newcrc.length() == 3) {

                    User newUser = new User("", "");
                    newUser.setMobile(user);
                    newUser.setEmail(newMail);
                    newUser.setPassword(newPass);
                    newUser.setMcreditcardnr(newcred);
                    newUser.setMcrc(newcrc);

                    if (mainDB.updateProfile(newUser)) {
                        text = "Profile updated !";
                    }
                } else {
                    text = "Imput is not valid !";
                }
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });


        return v;
    }


}
