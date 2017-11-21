package com.example.andres.shiroestore.model.product;

import com.example.andres.shiroestore.FinalString;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductData {
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static ArrayList<Product> mProducts = new ArrayList<>();

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static void addProduct(Product product){
        product.setId(databaseReference.push().getKey());
        databaseReference.child(FinalString.productsDB).child(product.getId()).setValue(product);
    }

    public static void deleteProduct(Product product) {
        databaseReference.child(FinalString.productsDB).child(product.getId()).removeValue();
    }

    public static void editProdduct(Product product) {
        databaseReference.child(FinalString.productsDB).child(product.getId()).setValue(product);
    }

    public static ArrayList<Product> getProducts(){
        return mProducts;
    }

}
