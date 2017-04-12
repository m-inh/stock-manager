package com.uet.stockmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.adapters.SaleAdapter;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.models.Sale;
import com.uet.stockmanager.models.SaleDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleActivity extends AppCompatActivity{

    @BindView(R.id.tb_sale_activity)
    Toolbar toolbar;
    @BindView(R.id.lv_list_sale)
    ListView lvListSale;

    private SaleDao saleDao;
    private List<Sale> saleList;
    private SaleAdapter saleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sale);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Sale");

        saleDao = ((AppController) getApplication()).getDaoSession().getSaleDao();
        saleList = saleDao.queryBuilder().list();
        saleAdapter = new SaleAdapter(this,saleList);
        lvListSale.setAdapter(saleAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sale_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Intent mIntent = new Intent(SaleActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
