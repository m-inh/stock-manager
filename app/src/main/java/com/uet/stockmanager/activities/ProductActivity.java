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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uet.stockmanager.R;
import com.uet.stockmanager.adapters.ProductAdapter;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.common.CommonVls;
import com.uet.stockmanager.dialogs.AddProductDialog;
import com.uet.stockmanager.dialogs.EditProductDialog;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {


    private static final String TAG = "ProductActivity";

    @BindView(R.id.lv_main)
    ListView lvMain;
    @BindView(R.id.tb_product_activity)
    Toolbar toolbar;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;


    private ProductDao pDao;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private BroadcastReceiver addNewProduct = new MyBroadcast();
    private Product productTemp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Kho sản phẩm");

        pDao = ((AppController) getApplication()).getDaoSession().getProductDao();
        productList = pDao.queryBuilder().list();
        productAdapter = new ProductAdapter(this, productList);
        lvMain.setAdapter(productAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productList.get(position);

                EditProductDialog editProductDialog = new EditProductDialog(ProductActivity.this, R.style.PauseDialogAnimation);
                editProductDialog.setpName(product.getName());
                editProductDialog.setpId(product.getId() + "");
                editProductDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                editProductDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Log.d(TAG, "Product name: " + product.getName());
//                Toast.makeText(ProductActivity.this, "Add more " + product.getName(), Toast.LENGTH_LONG).show();
                productTemp = product;

                Log.d(TAG, productTemp.getName());
                editProductDialog.setTitle("");
                editProductDialog.show();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductDialog addProductDialog = new AddProductDialog(ProductActivity.this, R.style.PauseDialogAnimation);
                addProductDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                addProductDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addProductDialog.setTitle("");
                addProductDialog.show();
            }
        });

        //add new product
        IntentFilter filter = new IntentFilter();
        filter.addAction(CommonVls.PRODUCT_ACTIVITY_ADD_NEW_PRODUCT);
        filter.addAction(CommonVls.PRODUCT_ACTIVITY_UPDATE_PRODUCT);
        this.registerReceiver(addNewProduct, filter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.product_activity, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_add_product:
//                AddProductDialog addProductDialog = new AddProductDialog(this, R.style.PauseDialogAnimation);
//                addProductDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
//                addProductDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                addProductDialog.setTitle("");
//                addProductDialog.show();

//                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(addNewProduct);
    }

    private void insertListProduct(Product product) {
        pDao.insert(product);

        updateListProduct();
    }

    private void addMoreProduct(Product product) {
        pDao.update(product);

        updateListProduct();
    }

    private void updateListProduct() {
        productList.clear();
        productList.addAll(pDao.queryBuilder().list());
        productAdapter.notifyDataSetChanged();
    }

    private class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CommonVls.PRODUCT_ACTIVITY_ADD_NEW_PRODUCT)) {
                String name = intent.getStringExtra(CommonVls.PRODUCT_ACTIVITY_ADD_NAME_PRODUCT);
                String category = intent.getStringExtra(CommonVls.PRODUCT_ACTIVITY_ADD_CATEGORY_PRODUCT);
                long price = intent.getLongExtra(CommonVls.PRODUCT_ACTIVITY_ADD_PRICE_PRODUCT, 1);
                long quanlity = intent.getLongExtra(CommonVls.PRODUCT_ACTIVITY_ADD_QUANLITY_PRODUCT, 1);

                Product product = new Product();
                product.setName(name);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quanlity);

                insertListProduct(product);
            }

            if (intent.getAction().equals(CommonVls.PRODUCT_ACTIVITY_UPDATE_PRODUCT)) {
                long quanlity = Long.parseLong(intent.getStringExtra(CommonVls.PRODUCT_ACTIVITY_ADD_MORE));
                productTemp.setQuantity(productTemp.getQuantity() + quanlity);
                addMoreProduct(productTemp);

            }

        }
    }
}
