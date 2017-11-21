package com.example.andres.shiroestore.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andres.shiroestore.FinalString;
import com.example.andres.shiroestore.R;
import com.example.andres.shiroestore.activity.admin.AdminMainViewActivity;
import com.example.andres.shiroestore.activity.admin.ProductDetailActivity;
import com.example.andres.shiroestore.activity.user.StoreActivity;
import com.example.andres.shiroestore.activity.user.UserMainActivity;
import com.example.andres.shiroestore.model.product.Product;
import com.example.andres.shiroestore.model.product.ProductData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserProductAdapter extends RecyclerView.Adapter<UserProductAdapter.UserProductViewHolder>  {

    private UserMainActivity mActivity;
    private ArrayList<Product> mProducts;

    public UserProductAdapter(UserMainActivity adminMainViewActivity, ArrayList<Product> products) {
        this.mActivity = adminMainViewActivity;
        this.mProducts = products;
    }

    @Override
    public UserProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new UserProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserProductViewHolder holder, int position) {
        holder.bindView(mProducts.get(position), mActivity);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public static class UserProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView photo;
        private TextView price ;
        private TextView name ;
        private TextView cant ;
        private TextView desciption ;
        private UserMainActivity mActivity;
        private Product mProduct;
        private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        public UserProductViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.itemImge);
            price = (TextView) itemView.findViewById(R.id.itemPrice);
            cant = (TextView) itemView.findViewById(R.id.itemCant);
            desciption = (TextView) itemView.findViewById(R.id.itemDescription);
            name = (TextView) itemView.findViewById(R.id.itemName);
            itemView.setOnClickListener(this);
        }

        public void bindView(Product product, UserMainActivity activity) {
            mProduct = product;
            mActivity = activity;

            storageReference.child(product.getPhoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(mActivity).load(uri).into(photo);
                }
            });
            price.setText("$" + String.valueOf(product.getPrice()));
            cant.setText(String.valueOf(product.getPrice()));
            desciption.setText(product.getDetail());
            name.setText(product.getName());
        }

        @Override
        public void onClick(View v) {
            /*Intent intent = new Intent(mActivity, StoreActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(FinalString.PRODUCT_ID, mProduct.getId());
            bundle.putString(FinalString.PRODUCT_NAME, mProduct.getName());
            bundle.putInt(FinalString.PRODUCT_CATEGORY, mProduct.getCategory());
            bundle.putInt(FinalString.PRODUCT_PRICE, mProduct.getPrice());
            bundle.putInt(FinalString.PRODUCT_CANT, mProduct.getCant());
            bundle.putString(FinalString.PRODUCT_PHOTO, mProduct.getPhoto());
            bundle.putString(FinalString.PRODUCT_DETAIL, mProduct.getDetail());

            intent.putExtra(FinalString.DATA, bundle);
            mActivity.startActivity(intent);*/
            String positive,negative;

            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle(R.string.hey);
            builder.setMessage(R.string.buyItem);
            positive = mActivity.getString(R.string.buy);
            negative = mActivity.getString(R.string.cancel);


            builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
