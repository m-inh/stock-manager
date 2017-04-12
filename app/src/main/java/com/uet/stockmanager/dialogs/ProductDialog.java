package com.uet.stockmanager.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

import com.uet.stockmanager.R;

/**
 * Created by TooNies1810 on 4/11/17.
 */

public class ProductDialog extends Dialog {

    // todo: create custom product in here
    public ProductDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_add_product);
    }
}
