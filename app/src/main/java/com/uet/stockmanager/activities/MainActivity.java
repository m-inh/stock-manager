package com.uet.stockmanager.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_product)
    Button btnProduct;
    @BindView(R.id.btn_sale)
    Button btnSale;
    @BindView(R.id.btn_statistic)
    Button btnStatistic;
    @BindView(R.id.tb_main_activity)
    Toolbar toolBar;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Quản lí kho hàng");
    }

    private void initViews() {
        ButterKnife.bind(this);
        btnProduct.setOnClickListener(this);
        btnSale.setOnClickListener(this);
        btnStatistic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_product:
                Intent mIntent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(mIntent);
                break;
            case R.id.btn_sale:
                Intent intentSale = new Intent(MainActivity.this, SaleActivity.class);
                startActivity(intentSale);
                break;
            case R.id.btn_statistic:
                Intent i = new Intent(MainActivity.this, StatisticActivity.class);
                startActivity(i);
                break;
        }
    }
}
