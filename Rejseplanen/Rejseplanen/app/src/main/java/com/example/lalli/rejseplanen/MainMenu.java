package com.example.lalli.rejseplanen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity{

    Button logOut;
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
        preferences.getBoolean("loggedIn", false);
        int i = 3;
       if(!preferences.getBoolean("loggedIn", false)){
            Intent intent = new Intent(this, Rejseplanen.class);
            this.startActivity(intent);
        }
    }

    public void setPref(String attribute1,String par1,String attribute2, boolean booleanPar1){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainMenu.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(attribute1,par1);
        editor.putBoolean(attribute2,booleanPar1);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            //add the function to perform here
            return(true);
        case R.id.reset:
            return(true);
        case R.id.about:
            Toast.makeText(this, R.string.about, Toast.LENGTH_LONG).show();
            //add the function to perform here
            System.out.println("about is clicked");
            return(true);
        case R.id.exit:
            System.out.println("logged out");
            setPref("mobile","","loggedIn", false);
            Intent intent = new Intent(MainMenu.this, Rejseplanen.class);
            MainMenu.this.startActivity(intent);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }


}
