package com.uet.stockmanager.dialogs;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uet.stockmanager.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.category;
import static android.R.attr.mimeType;

public class AddSaleDialog extends Dialog implements TimePickerDialog.OnTimeSetListener {

    private static final String ADD_NEW_SALE = "new sale";
    private static final String ADD_NAME_SALE = "name";
    private static final String ADD_TIME_SALE = "time sale";
    private static final String ADD_PRICE_SALE = "price sale";
    private static final String ADD_QUANLITY_SALE = "quanlity sale";
    private static final String ADD_PRODUCT_ID_SALE = "product id";

    @BindView(R.id.edt_add_sale_name) EditText edtName;
    @BindView(R.id.edt_add_product_id) EditText edtProductID;
    @BindView(R.id.edt_add_sale_quanlity) EditText edtQuanlity;
    @BindView(R.id.edt_add_sale_price) EditText edtPrice;
    @BindView(R.id.edt_add_time_sale) EditText edtTimeSale;
    @BindView(R.id.btn_add_sale) Button btnAddSale;
    @BindView(R.id.btn_time_picker) View btnTimePicker;

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

                if(edtName.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Name cann't be empty!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edtProductID.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Product ID cann't be empty!",Toast.LENGTH_SHORT).show();
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
                if(edtTimeSale.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Time Sale cann't be empty!",Toast.LENGTH_SHORT).show();
                }

                String name = edtName.getText().toString();
                long productID = Long.parseLong(edtProductID.getText().toString());
                int price = Integer.parseInt(edtPrice.getText().toString());
                int quantity = Integer.parseInt(edtQuanlity.getText().toString());

                Intent mIntent = new Intent(ADD_NEW_SALE);
                mIntent.putExtra(ADD_NAME_SALE,name);
                mIntent.putExtra(ADD_TIME_SALE,timeMilis);
                mIntent.putExtra(ADD_PRICE_SALE,price);
                mIntent.putExtra(ADD_QUANLITY_SALE,quantity);
                mIntent.putExtra(ADD_PRODUCT_ID_SALE,productID);
                getContext().sendBroadcast(mIntent);

                dismiss();
            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                int h = now.get(Calendar.HOUR_OF_DAY);
                int m = now.get(Calendar.MINUTE);
                int s = now.get(Calendar.SECOND);

                TimePickerDialog timePicker = new TimePickerDialog(getContext(),AddSaleDialog.this,h,m,true);

                timePicker.show();
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar now = Calendar.getInstance();

        now.set(now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DATE),
                hourOfDay,
                minute);
        now.getTimeInMillis();

        String timeScheduleAt = String.format(
                "%02d:%02d %02d/%02d/%04d",
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.DATE),
                now.get(Calendar.MONTH),
                now.get(Calendar.YEAR)
                );
        edtTimeSale.setText(timeScheduleAt);

        timeMilis = now.getTimeInMillis();
    }
}
