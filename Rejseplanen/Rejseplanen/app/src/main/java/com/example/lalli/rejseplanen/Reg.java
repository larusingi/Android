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

import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

import java.util.List;

public class Reg extends AppCompatActivity {

    Button registerButton;
    Button loadb;
    static MainDB maindDB;
    EditText phoneNO;
    EditText email;
    EditText passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        maindDB = MainDB.get(this);
        phoneNO = (EditText) findViewById(R.id.regPhone);
        email = (EditText) findViewById(R.id.regEmail);
        passw = (EditText) findViewById(R.id.regPass);

        registerButton = (Button) findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click click ");

                String phone = phoneNO.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String pass = passw.getText().toString().trim();
                User user = new User(phone,pass);
                user.setEmail(mail);
                user.setCredit(50);
                if(maindDB.addUser(user)){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Reg.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("mobile",phone);
                    editor.putBoolean("loggedIn",true);
                    editor.apply();

                    Intent intent = new Intent(Reg.this, MainMenu.class);
                    Reg.this.startActivity(intent);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "number already in use or invalid input";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

        });

    }
}
