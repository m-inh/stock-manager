package com.uet.stockmanager.dialogs;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uet.stockmanager.R;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.common.CommonVls;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddSaleDialog extends Dialog {

    private static final String TAG = "AddSaleDialog";

    @BindView(R.id.edt_add_product_id)
    EditText edtProductID;
    @BindView(R.id.edt_add_sale_quanlity)
    EditText edtQuanlity;
    @BindView(R.id.btn_add_sale)
    Button btnAddSale;

    private long timeMilis;


    public AddSaleDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_sale);

        ButterKnife.bind(this);

        btnAddSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtProductID.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Product ID cann't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edtQuanlity.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Quantity cann't be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long productID = Long.parseLong(edtProductID.getText().toString());
                long quantity = Long.parseLong(edtQuanlity.getText().toString());

                ProductDao productDao = ((AppController) getContext().getApplicationContext()).getDaoSession().getProductDao();

                Product p = productDao.queryBuilder().where(ProductDao.Properties.Id.eq(productID)).unique();

                if (p == null) {
                    Toast.makeText(getContext(), "ProductID  isn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                long quanlityProductAfterSale = p.getQuantity() - quantity;
                if (quanlityProductAfterSale < 0) {
                    Toast.makeText(getContext(), "The product in stock is not enough, re-enter the quantity", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    p.setQuantity(quanlityProductAfterSale);
                    productDao.update(p);
                }

                Intent mIntent = new Intent(CommonVls.SALE_ACTIVITY_ADD_NEW_SALE);
                timeMilis = System.currentTimeMillis();
                mIntent.putExtra(CommonVls.SALE_ACTIVITY_ADD_TIME_SALE, timeMilis);
                mIntent.putExtra(CommonVls.SALE_ACTIVITY_ADD_QUANLITY_SALE, quantity);
                mIntent.putExtra(CommonVls.SALE_ACTIVITY_ADD_PRODUCT_ID_SALE, productID);
                getContext().sendBroadcast(mIntent);

                dismiss();
            }
        });
    }
}
