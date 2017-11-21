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

import com.example.andres.shiroestore.FinalString;
import com.example.andres.shiroestore.R;
import com.example.andres.shiroestore.model.product.Product;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProductEditActivity extends AppCompatActivity {

    private String id, name, photo, detail;
    private int category, price, cant;

    private EditText mName;
    private Spinner mCategory;
    private EditText mPrice;
    private EditText mCant;
    private ImageView mPhoto;
    private EditText mDetail;
    private Button mEditProdut, mClean;
    private StorageReference mStorageRef;
    private String[] mSpinnerOptions;
    private Uri filePath;
    private Bundle mBundle;

    private View.OnClickListener mEditListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            edit(view);
        }
    };

    private View.OnClickListener mSelectPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectPhoto();
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
        setContentView(R.layout.activity_product_edit);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar5);
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

        mName = (EditText) findViewById(R.id.txtEditName);
        mCategory = (Spinner) findViewById(R.id.cmdEditCategory);
        mPrice = (EditText) findViewById(R.id.txtEditPrice);
        mCant = (EditText) findViewById(R.id.txtEditCant);
        mPhoto = (ImageView) findViewById(R.id.imgEdit);
        mDetail = (EditText) findViewById(R.id.txtEditDetail);
        mEditProdut = (Button) findViewById(R.id.btnEdit);
        mClean = (Button) findViewById(R.id.btnEditClear);


        Intent intent = getIntent();
        mBundle = intent.getBundleExtra(FinalString.DATA);

        id = mBundle.getString(FinalString.PRODUCT_ID);
        name = mBundle.getString(FinalString.PRODUCT_NAME);
        category = mBundle.getInt(FinalString.PRODUCT_CATEGORY);
        price = mBundle.getInt(FinalString.PRODUCT_PRICE);
        cant = mBundle.getInt(FinalString.PRODUCT_CANT);
        photo = mBundle.getString(FinalString.PRODUCT_PHOTO);
        detail = mBundle.getString(FinalString.PRODUCT_DETAIL);

        mSpinnerOptions = getResources().getStringArray(R.array.categoryArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerOptions);
        mCategory.setAdapter(adapter);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        mName.setText(name);
        mStorageRef.child(photo).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(ProductEditActivity.this).load(uri).into(mPhoto);
            }
        });
        mPrice.setText(String.valueOf(price));
        mCant.setText(String.valueOf(cant));
        mDetail.setText(detail);

        mEditProdut.setOnClickListener(mEditListener);
        mClean.setOnClickListener(mCleanListener);
        mPhoto.setOnClickListener(mSelectPhoto);
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

    public void uploadImage(String img){
        StorageReference childRef = mStorageRef.child(img);
        UploadTask uploadTask = childRef.putFile(filePath);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        String newName = mName.getText().toString();
        int newPrice = Integer.parseInt(mPrice.getText().toString());
        int newCant = Integer.parseInt(mCant.getText().toString());
        String newDetail = mDetail.getText().toString();
        int newCategory = mCategory.getSelectedItemPosition();

        Product mProduct = new Product(id, newName, newCategory, newPrice, newCant, photo, newDetail);

        Intent intent = new Intent(ProductEditActivity.this, ProductDetailActivity.class);
        Bundle newBundle = new Bundle();
        newBundle.putString(FinalString.PRODUCT_ID, id);
        newBundle.putString(FinalString.PRODUCT_NAME, mProduct.getName());
        newBundle.putInt(FinalString.PRODUCT_CATEGORY, mProduct.getCategory());
        newBundle.putInt(FinalString.PRODUCT_PRICE, mProduct.getPrice());
        newBundle.putInt(FinalString.PRODUCT_CANT, mProduct.getCant());
        newBundle.putString(FinalString.PRODUCT_PHOTO, mProduct.getPhoto());
        newBundle.putString(FinalString.PRODUCT_DETAIL, mProduct.getDetail());
        intent.putExtra(FinalString.DATA, newBundle);
        startActivity(intent);
        finish();
    }

    public void edit(View v){
        String newName = mName.getText().toString();
        int newPrice = Integer.parseInt(mPrice.getText().toString());
        int newCant = Integer.parseInt(mCant.getText().toString());
        String newDetail = mDetail.getText().toString();
        int newCategory = mCategory.getSelectedItemPosition();

        Product mProduct = new Product(id, newName, newCategory, newPrice, newCant, photo, newDetail);
        if (filePath !=null) uploadImage(photo);
        mProduct.edit();
        Snackbar.make(v, R.string.productUpdated, Snackbar.LENGTH_LONG).setAction("action", null).show();
        onBackPressed();
    }

    private void clean() {
        mName.setText("");
        mDetail.setText("");
        mPhoto.setImageResource(R.drawable.defaultproductimage);
        mPrice.setText("");
        mCant.setText("");
    }


}
