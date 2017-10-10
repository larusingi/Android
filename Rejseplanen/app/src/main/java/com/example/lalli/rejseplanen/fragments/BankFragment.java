package com.example.lalli.rejseplanen.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import com.example.lalli.rejseplanen.Reg;
import com.example.lalli.rejseplanen.dataB.BankAccount;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

import java.util.UUID;

public class BankFragment extends Fragment {

    static MainDB mainDB;
    static BankDB bankDB;
    Button addMoney;
    String user;
    TextView balText;
    EditText addCnumber;
    EditText addcrc;
    EditText ammountToAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.bank_fragment, container, false);

        mainDB= mainDB.get(getActivity());
        bankDB = bankDB.get(getActivity());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        user = preferences.getString("mobile","");

        addCnumber  = (EditText) v.findViewById(R.id.addCcard);
        addcrc      = (EditText) v.findViewById(R.id.addCRC);

        final User u = mainDB.getUser(user);
        balText = (TextView) v.findViewById(R.id.balanceKR);
        balText.setText(mainDB.getBalance(u).toString()+ " kr");
        addCnumber.setText(u.getMcreditcardnr());
        addcrc.setText(u.getMcrc());

        ammountToAdd = (EditText) v.findViewById(R.id.ammountToAddEdit);
        addMoney = (Button) v.findViewById(R.id.addCreditButton);
        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    BankAccount b = new BankAccount();
                    double moneyEntered = Double.parseDouble(ammountToAdd.getText().toString());

                    Context context = getActivity().getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    String text = "";


                    b.setMcrc(addcrc.getText().toString());
                    b.setMcreditcardnr(addCnumber.getText().toString());
                    b.setMbalance(moneyEntered);
                    if (bankDB.withdraw(b)) {
                        User currU = new User("", "");
                        currU.setCredit(moneyEntered);
                        currU.setMobile(user);
                        mainDB.addMoney(currU);
                        Double newBal = mainDB.getBalance(currU);
                        balText.setText(newBal.toString() + " kr");
                        ammountToAdd.setText(null);
                        text = moneyEntered + " kr added to account !";
                    } else {
                        text = "invalid card or balance";
                    }
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }catch(Exception e){
                    System.out.println(e);
                }

            }
        }

        );


        return v;
    }


}
