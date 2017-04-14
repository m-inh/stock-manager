package com.uet.stockmanager.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.adapters.SaleAdapter;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.common.CommonVls;
import com.uet.stockmanager.dialogs.AddSaleDialog;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;
import com.uet.stockmanager.models.Sale;
import com.uet.stockmanager.models.SaleDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleActivity extends AppCompatActivity {

    private static final String TAG = "SaleActivity";

    @BindView(R.id.tb_sale_activity)
    Toolbar toolbar;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;
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
        getSupportActionBar().setTitle("Bán hàng");

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSaleDialog addSaleDialog = new AddSaleDialog(SaleActivity.this, R.style.PauseDialogAnimation);
                addSaleDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                addSaleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addSaleDialog.setTitle("");
                addSaleDialog.show();
            }
        });

        saleDao = ((AppController) getApplication()).getDaoSession().getSaleDao();
        saleList = saleDao.queryBuilder().list();
        saleAdapter = new SaleAdapter(this, saleList);
        lvListSale.setAdapter(saleAdapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(CommonVls.SALE_ACTIVITY_ADD_NEW_SALE);

        this.registerReceiver(myBroadcast,filter);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sale_activity, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.menu_add_sale:
//                AddSaleDialog addSaleDialog = new AddSaleDialog(this, R.style.PauseDialogAnimation);
//                addSaleDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
//                addSaleDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                addSaleDialog.setTitle("");
//                addSaleDialog.show();
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertSale(Sale sale){
        saleDao.insert(sale);
        updateListSale();
    }

    private void updateListSale() {
        saleList.clear();
        saleList.addAll(saleDao.queryBuilder().list());
        saleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.myBroadcast);
    }


    private class MyBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(CommonVls.SALE_ACTIVITY_ADD_NEW_SALE)){
                long productID = intent.getLongExtra(CommonVls.SALE_ACTIVITY_ADD_PRODUCT_ID_SALE,1);
                int quanlity = intent.getIntExtra(CommonVls.SALE_ACTIVITY_ADD_QUANLITY_SALE,1);
                long timeSale = intent.getLongExtra(CommonVls.SALE_ACTIVITY_ADD_TIME_SALE,1);

                Sale sale = new Sale();
                sale.setQuanlity(quanlity);

                ProductDao productDao = ((AppController)getApplicationContext()).getDaoSession().getProductDao();

                Product p = productDao.load(productID);
                int totalPrice = p.getPrice()*quanlity;
                String name = p.getName();
                sale.setPrice(totalPrice);
                sale.setName(name);
                sale.setTimestamp(timeSale);
                sale.setProductId(productID);
                insertSale(sale);

            }
        }
    }
}
