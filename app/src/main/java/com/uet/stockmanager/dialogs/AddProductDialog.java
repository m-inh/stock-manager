package com.uet.stockmanager.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uet.stockmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProductDialog extends Dialog {
    private static final String UPDATE_DATABASE = "update";
    private static final String ADD_NAME_PRODUCT = "name";
    private static final String ADD_CATEGORY_PRODUCT = "category";
    private static final String ADD_PRICE_PRODUCT = "price";
    private static final String ADD_QUANLITY_PRODUCT = "quanlity";

    @BindView(R.id.edt_add_product_name) EditText edtName;
    @BindView(R.id.edt_add_product_category) EditText edtCategory;
    @BindView(R.id.edt_add_product_quanlity) EditText edtQuanlity;
    @BindView(R.id.edt_add_product_price) EditText edtPrice;
    @BindView(R.id.btn_add_product) Button btnAddProduct;

    public AddProductDialog(@NonNull final Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_product);

        ButterKnife.bind(AddProductDialog.this);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Name cann't be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtCategory.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Category cann't be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtPrice.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Price cann't be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtQuanlity.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Quantity cann't be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = edtName.getText().toString();
                String category = edtCategory.getText().toString();
                int price = Integer.parseInt(edtPrice.getText().toString());
                int quantity = Integer.parseInt(edtQuanlity.getText().toString());

                Intent mIntent = new Intent(UPDATE_DATABASE);
                mIntent.putExtra(ADD_NAME_PRODUCT,name);
                mIntent.putExtra(ADD_CATEGORY_PRODUCT,category);
                mIntent.putExtra(ADD_PRICE_PRODUCT,price);
                mIntent.putExtra(ADD_QUANLITY_PRODUCT,quantity);

                getContext().sendBroadcast(mIntent);

                dismiss();
            }
        });
    }
}
