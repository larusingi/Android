package com.example.lalli.rejseplanen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lalli.rejseplanen.dataB.BankAccount;
import com.example.lalli.rejseplanen.dataB.BankDB;
import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

import java.util.List;
import java.util.UUID;

public class Reg extends AppCompatActivity {

    Button registerButton;
    Button loadb;
    static MainDB maindDB;
    static BankDB bankDB;
    EditText phoneNO;
    EditText email;
    EditText passw;
    EditText fname;
    EditText lName;
    EditText creditCa;
    EditText crc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        maindDB = MainDB.get(this);
        bankDB = BankDB.get(this);

        phoneNO = (EditText) findViewById(R.id.regPhone);
        fname = (EditText) findViewById(R.id.regFirst);
        lName = (EditText) findViewById(R.id.regLast);
        email = (EditText) findViewById(R.id.regEmail);
        passw = (EditText) findViewById(R.id.regPass);
        creditCa = (EditText) findViewById(R.id.regCcard);
        crc = (EditText) findViewById(R.id.regCRC);

        registerButton = (Button) findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phone = phoneNO.getText().toString().trim();
                String firstN = fname.getText().toString().trim();
                String lN = lName.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = passw.getText().toString().trim();
                String cred = creditCa.getText().toString().trim();
                String c = crc.getText().toString().trim();

                Context context = getApplicationContext();
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;

                if (phone.length() > 0 && firstN.length() > 0 && lN.length() > 0 && mail.length() > 0 && pass.length() > 0) {


                    User user = new User(phone, pass);
                    user.setmId(UUID.randomUUID());
                    user.setEmail(mail);
                    user.setMfirstname(firstN);
                    user.setMlastname(lN);
                    user.setMcreditcardnr(cred);
                    user.setMcrc(c);
                    user.setCredit(0);

                    BankAccount bankAccount = new BankAccount();
                    bankAccount.setMcrc(c);
                    bankAccount.setMcreditcardnr(cred);
                    bankAccount.setMlastname(lN);
                    bankAccount.setMfirstname(firstN);
                    bankAccount.setMbalance(1000);


                    if (maindDB.addUser(user)) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Reg.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("mobile", phone);
                        editor.putBoolean("loggedIn", true);
                        editor.apply();
                        bankDB.addBank(bankAccount);
                        Intent intent = new Intent(Reg.this, MainMenu.class);
                        Reg.this.startActivity(intent);
                    }
                } else {
                    text = "number already in use or invalid input";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.getBoolean("loggedIn", true)) {
            Intent intent = new Intent(this, MainMenu.class);
            this.startActivity(intent);
        }

    }
}
