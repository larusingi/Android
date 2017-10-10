package com.example.lalli.shoppingapp;

import com.example.lalli.shoppingapp.ConnectTask.Param;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText product;
    EditText qty;
    TextView result;
    Button addButton;

    ArrayAdapter productsAdapter;
   public static ProductList plist;

    public final static String RESOURCE = "https://itu.dk/people/jacok/mmad/services/shoppinglist/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        product = (EditText) findViewById(R.id.productText);
        qty = (EditText) findViewById(R.id.EditTextqty);
        result = (TextView) findViewById(R.id.resultBox);
        addButton = (Button) findViewById(R.id.Button01);
        plist = ProductList.get(this);

        productsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,plist.getProductList());



    }

    public void submit(View v) {
        Param nameParam = new Param("name", product.getText().toString());
        Param ageParam = new Param("age", qty.getText().toString());

        new ConnectTask(RESOURCE, result).execute(nameParam, ageParam);

    }




}