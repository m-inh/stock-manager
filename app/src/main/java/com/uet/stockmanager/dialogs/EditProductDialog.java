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

public class EditProductDialog extends Dialog{

    public static final String UPDATE_PRODUCT = "update_product";
    public static final String ADD_MORE = "add_more";

    @BindView(R.id.edt_edit_product_quanlity) EditText edtEditText;
    @BindView(R.id.btn_edit_product) Button btnUpdateEdited;

    public EditProductDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_product);

        ButterKnife.bind(this);

        btnUpdateEdited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtEditText.getText().toString().equals("")){
                    Toast.makeText(getContext(),"Quanlity cann't be empty!!!", Toast.LENGTH_SHORT);
                }
                Intent mIntent = new Intent(UPDATE_PRODUCT);
                mIntent.putExtra(ADD_MORE,String.valueOf(edtEditText.getText().toString()));
                getContext().sendBroadcast(mIntent);
                dismiss();
            }
        });
    }
}
