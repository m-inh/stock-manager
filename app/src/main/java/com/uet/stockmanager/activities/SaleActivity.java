package com.uet.stockmanager.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.adapters.SaleAdapter;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.dialogs.AddSaleDialog;
import com.uet.stockmanager.models.Sale;
import com.uet.stockmanager.models.SaleDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleActivity extends AppCompatActivity {

    private static final String ADD_NEW_SALE = "new sale";
    private static final String ADD_NAME_SALE = "name";
    private static final String ADD_TIME_SALE = "time sale";
    private static final String ADD_PRICE_SALE = "price sale";
    private static final String ADD_QUANLITY_SALE = "quanlity sale";
    private static final String ADD_PRODUCT_ID_SALE = "product id";

    @BindView(R.id.tb_sale_activity)
    Toolbar toolbar;
    @BindView(R.id.lv_list_sale)
    ListView lvListSale;

    private SaleDao saleDao;
    private List<Sale> saleList;
    private SaleAdapter saleAdapter;
    private BroadcastReceiver myBroadcast = new MyBroadcast();

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
        saleAdapter = new SaleAdapter(this, saleList);
        lvListSale.setAdapter(saleAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ADD_NEW_SALE);

        this.registerReceiver(myBroadcast,filter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sale_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent mIntent = new Intent(SaleActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
            case R.id.menu_add_sale:
                AddSaleDialog addSaleDialog = new AddSaleDialog(this, R.style.PauseDialogAnimation);
                addSaleDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                addSaleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addSaleDialog.setTitle("");
                addSaleDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ADD_NEW_SALE)){
                String name = intent.getStringExtra(ADD_NAME_SALE);
                long productID = intent.getLongExtra(ADD_PRODUCT_ID_SALE,1);
                long timeSale = intent.getLongExtra(ADD_TIME_SALE,1);
                int quanlity = intent.getIntExtra(ADD_QUANLITY_SALE,1);
                int price = intent.getIntExtra(ADD_PRICE_SALE,1);

                Sale sale = new Sale();
                sale.setProductId(productID);
                sale.setName(name);
                sale.setTimestamp(timeSale);
                sale.setPrice(price);
                sale.setQuanlity(quanlity);

                saleDao.insert(sale);
                saleList.add(sale);
                saleAdapter.notifyDataSetChanged();
            }
        }
    }
}
