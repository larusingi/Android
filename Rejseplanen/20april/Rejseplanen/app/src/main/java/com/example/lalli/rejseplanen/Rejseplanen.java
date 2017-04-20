package com.example.lalli.rejseplanen;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lalli.rejseplanen.dataB.MainDB;
import com.example.lalli.rejseplanen.dataB.User;

import java.text.ParseException;

public class Rejseplanen extends AppCompatActivity {
    private static Rejseplanen sRejseplanen;
    public static final String PREFS_NAME = "MyPrefsFile";

    Button toRegButt;
    Button loginB;
    private Context mContext;
    private MainDB mainDB;
    EditText  phone;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejseplanen);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Rejseplanen.this);
        SharedPreferences.Editor editor = preferences.edit();
  /*      editor.putBoolean("loggedIn",false);
        editor.apply();*/

        toRegButt   = (Button)   findViewById(R.id.toRegButton);
        loginB      = (Button)   findViewById(R.id.loginButton);
        phone       = (EditText) findViewById(R.id.logInphoneNO);
        pass        = (EditText) findViewById(R.id.logIngPassword);

        toRegButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    toReg();
            }
        });

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNO = phone.getText().toString();
                String passw = pass.getText().toString();

                checkUsr(phoneNO,passw);
            }
        });

        mainDB = MainDB.get(this);


     editor.putString("mobile","");
        editor.putBoolean("loggedIn",false);
        editor.apply();

/*      Intent intent = new Intent(this, MainMenu.class);
        this.startActivity(intent);*/

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(preferences.getBoolean("loggedIn", true)){
            Intent intent = new Intent(this, MainMenu.class);
            this.startActivity(intent);
        }

    }

    public void toReg() {
        Intent intent = new Intent(this, Reg.class);
        this.startActivity(intent);
}

    public void checkUsr(String phone,String pass){
        if(mainDB.checkUser(new User(phone,pass))){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Rejseplanen.this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("mobile",phone);
            editor.putBoolean("loggedIn",true);
            editor.apply();

            Intent intent = new Intent(this, MainMenu.class);
            this.startActivity(intent);

        }else{

            Context context = getApplicationContext();
            CharSequence text = "wrong password/username";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }



}
