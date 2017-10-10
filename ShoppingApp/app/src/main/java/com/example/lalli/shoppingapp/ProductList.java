package com.example.lalli.shoppingapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lalli on 18-03-2017.
 */

public class ProductList {

    private static ProductList sProductList;

    private List<Product> products;
    public static ProductList get(Context context){
    if(sProductList==null){
        sProductList = new ProductList(context);
    }
        return sProductList;
    }
    public List<Product> getProductList(){return products;}

public ProductList(Context context){
        products = new ArrayList<Product>();

        Product p = new Product("Razor",3);
    Product g = new Product("shampoo",2);

        products.add(p);
        products.add(g);
}


}
