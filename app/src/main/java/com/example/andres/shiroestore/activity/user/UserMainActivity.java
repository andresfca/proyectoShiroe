package com.example.andres.shiroestore.activity.user;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.andres.shiroestore.FinalString;
import com.example.andres.shiroestore.R;
import com.example.andres.shiroestore.adapter.ProductAdapter;
import com.example.andres.shiroestore.adapter.UserProductAdapter;
import com.example.andres.shiroestore.model.product.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserMainActivity extends AppCompatActivity {

    private RecyclerView mList;
    private ArrayList<Product> mProducts;
    private Resources mResource;
    private UserProductAdapter mAdapter;
    private LinearLayoutManager mManager;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        mList = (RecyclerView) findViewById(R.id.otherid);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mProducts = new ArrayList<>();
        mResource = this.getResources();
        databaseReference.child(FinalString.productsDB).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProducts.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Product product = snapshot.getValue(Product.class);
                        mProducts.add(product);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new UserProductAdapter(this, mProducts);

        mList.setLayoutManager(mManager);
        mList.setAdapter(mAdapter);

    }
}
