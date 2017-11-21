package com.example.andres.shiroestore.activity.admin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.andres.shiroestore.Helper;
import com.example.andres.shiroestore.R;
import com.example.andres.shiroestore.model.product.Product;
import com.example.andres.shiroestore.model.product.ProductData;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateProuctActivity extends AppCompatActivity {

    private EditText mName;
    private Spinner mCategory;
    private EditText mPrice;
    private EditText mCant;
    private ImageView mPhoto;
    private EditText mDetail;
    private Button mCreateProdut, mClean;
    private StorageReference mStorageRef;
    private String[] mSpinnerOptions;
    private Uri filePath;

    private View.OnClickListener mCreateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            createProduct(view);
        }
    };

    private View.OnClickListener mCleanListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clean();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prouct);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
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

        mName = (EditText) findViewById(R.id.txtName);
        mDetail = (EditText) findViewById(R.id.txtDetail);
        mCategory = (Spinner) findViewById(R.id.cmdCategory);
        mPhoto = (ImageView) findViewById(R.id.imgCreate);
        mPrice = (EditText) findViewById(R.id.txtPrice);
        mCant = (EditText) findViewById(R.id.txtCant);
        mCreateProdut = (Button) findViewById(R.id.btnCreate);
        mClean = (Button) findViewById(R.id.btnClear);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mSpinnerOptions = getResources().getStringArray(R.array.categoryArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerOptions);
        mCategory.setAdapter(adapter);

        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        mCreateProdut.setOnClickListener(mCreateListener);
        mClean.setOnClickListener(mCleanListener);

    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setType(getString(R.string.typeImage));
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.selectImage)), 1);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1){
            filePath = data.getData();
            if (filePath != null){
                mPhoto.setImageURI(filePath);
            }
        }
    }

    private void createProduct(View view) {
        String id = ProductData.getId();
        String photo = id + ".jpg";
        String name = mName.getText().toString();
        int category = mCategory.getSelectedItemPosition();
        int price = Integer.parseInt(mPrice.getText().toString());
        int cant = Integer.parseInt(mCant.getText().toString());
        String detail = mDetail.getText().toString();

        if (Helper.isEmpty(mName)) return;
        if (Helper.isEmpty(mPrice)) return;
        if (Helper.isEmpty(mCant)) return;

        Product product = new Product(id, name, category, price, cant, photo, detail);
        product.save();
        uploadImage(photo);
        clean();
        Snackbar.make(view, R.string.productAdded, Snackbar.LENGTH_LONG).show();
    }

    private void clean() {
        mName.setText("");
        mDetail.setText("");
        mPhoto.setImageResource(R.drawable.defaultproductimage);
        mPrice.setText("");
        mCant.setText("");
    }

    public void uploadImage(String img){
        StorageReference childRef = mStorageRef.child(img);
        UploadTask uploadTask = childRef.putFile(filePath);
    }
}
