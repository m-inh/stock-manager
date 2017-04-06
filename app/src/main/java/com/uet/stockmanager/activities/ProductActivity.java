package com.uet.stockmanager.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.models.ProductDao;

import butterknife.BindView;

/**
 * Created by TooNies1810 on 4/6/17.
 */

public class ProductActivity extends AppCompatActivity {

    @BindView(R.id.lv_main)
    ListView lvMain;

    private ProductDao pDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initViews();

        pDao = ((AppController) getApplication()).getDaoSession().getProductDao();
    }

    private void initViews() {

    }
}
