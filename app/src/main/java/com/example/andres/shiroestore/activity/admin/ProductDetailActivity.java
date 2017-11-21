package com.example.andres.shiroestore.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andres.shiroestore.FinalString;
import com.example.andres.shiroestore.R;
import com.example.andres.shiroestore.activity.login.MainActivity;
import com.example.andres.shiroestore.model.product.Product;
import com.example.andres.shiroestore.model.product.ProductData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    private Bundle mBundle;
    private StorageReference storageReference;
    private CollapsingToolbarLayout mToolbarLayout;

    private ImageView mPhoto;
    private TextView mPriceDetail, mCantDetail, mDescriptionDetail;

    private String id, name, photo, detail;
    private int category, price, cant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        Drawable arrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

        mPhoto = (ImageView) findViewById(R.id.app_bar_image);
        mPriceDetail = (TextView) findViewById(R.id.itemDetailPrice);
        mCantDetail = (TextView) findViewById(R.id.itemDetailCant);
        mDescriptionDetail = (TextView) findViewById(R.id.itemDetailDescription);

        Intent intent = getIntent();
        mBundle = intent.getBundleExtra(FinalString.DATA);

        id = mBundle.getString(FinalString.PRODUCT_ID);
        name = mBundle.getString(FinalString.PRODUCT_NAME);
        category = mBundle.getInt(FinalString.PRODUCT_CATEGORY);
        price = mBundle.getInt(FinalString.PRODUCT_PRICE);
        cant = mBundle.getInt(FinalString.PRODUCT_CANT);
        photo = mBundle.getString(FinalString.PRODUCT_PHOTO);
        detail = mBundle.getString(FinalString.PRODUCT_DETAIL);

        mToolbarLayout.setTitle(name);
        storageReference.child(photo).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ProductDetailActivity.this).load(uri).into(mPhoto);
            }
        });
        mPriceDetail.setText("$" + String.valueOf(price));
        mCantDetail.setText(String.valueOf(cant));
        mDescriptionDetail.setText(detail);

    }

    public void delete(View v){
        final String positive,negative;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.hey);
        builder.setMessage(R.string.deleteUser);
        positive = getResources().getString(R.string.delete);
        negative = getString(R.string.cancel);

        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Product product = new Product(id);
                ProductData.deleteProduct(product);
                onBackPressed();
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_blue_light);
    }

    public void edit2(View v){
        Intent intent = new Intent(ProductDetailActivity.this, ProductEditActivity.class);
        intent.putExtra(FinalString.DATA, mBundle);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){
        Intent i = new Intent(ProductDetailActivity.this, AdminMainViewActivity.class);
        startActivity(i);
        finish();
    }

}
