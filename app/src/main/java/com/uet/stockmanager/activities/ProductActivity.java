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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uet.stockmanager.R;
import com.uet.stockmanager.adapters.ProductAdapter;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.dialogs.AddProductDialog;
import com.uet.stockmanager.dialogs.EditProductDialog;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    private static final String DIALOG_TITLE = "Add New Product";
    private static final String ADD_NEW_PRODUCT = "update";
    private static final String ADD_NAME_PRODUCT = "name";
    private static final String ADD_CATEGORY_PRODUCT = "category";
    private static final String ADD_PRICE_PRODUCT = "price";
    private static final String ADD_QUANLITY_PRODUCT = "quanlity";
    private static final String UPDATE_PRODUCT = "update product";
    private static final String TAG = "ProductActivity";
    private static final String ADD_MORE = "add more";

    @BindView(R.id.lv_main)
    ListView lvMain;
    @BindView(R.id.tb_product_activity)
    Toolbar toolbar;

    private ProductDao pDao;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private BroadcastReceiver addNewProduct = new MyBroadcast();
    private Product productTemp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Products");

        pDao = ((AppController) getApplication()).getDaoSession().getProductDao();
        productList = pDao.queryBuilder().list();
        productAdapter = new ProductAdapter(this, productList);
        lvMain.setAdapter(productAdapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditProductDialog editProductDialog = new EditProductDialog(ProductActivity.this, R.style.PauseDialogAnimation);
                editProductDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                editProductDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                long idQuery = position + 1;
                Product product = pDao.queryBuilder().where(ProductDao.Properties.Id.eq(idQuery)).unique();
                Log.i(TAG, "Product name: " + product.getName());
                Toast.makeText(ProductActivity.this,"Add more " + product.getName(),Toast.LENGTH_LONG).show();
                productTemp = product;
                editProductDialog.setTitle("");
                editProductDialog.show();
            }
        });

        //add new product
        IntentFilter filter = new IntentFilter();
        filter.addAction(ADD_NEW_PRODUCT);
        filter.addAction(UPDATE_PRODUCT);
        this.registerReceiver(addNewProduct, filter);
    }

    private void initViews() {

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_product:
                AddProductDialog addProductDialog = new AddProductDialog(this, R.style.PauseDialogAnimation);
                addProductDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                addProductDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                addProductDialog.setTitle(DIALOG_TITLE);
                addProductDialog.show();

                break;
            case android.R.id.home:
                Intent mIntent = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService((ServiceConnection) this.addNewProduct);
    }

    private void updateListProduct(Product product) {
        pDao.insert(product);
        productList.add(product);
        productAdapter.notifyDataSetChanged();
    }

    private class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ADD_NEW_PRODUCT)) {
                String name = intent.getStringExtra(ADD_NAME_PRODUCT);
                String category = intent.getStringExtra(ADD_CATEGORY_PRODUCT);
                int price = intent.getIntExtra(ADD_PRICE_PRODUCT, 1);
                int quanlity = intent.getIntExtra(ADD_QUANLITY_PRODUCT, 1);

                Product product = new Product();
                product.setName(name);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quanlity);

                updateListProduct(product);
            }
            if (intent.getAction().equals(UPDATE_PRODUCT)) {
                int quanlity = intent.getIntExtra(ADD_MORE,1);
                productTemp.setQuantity(productTemp.getQuantity() + quanlity);
                pDao.update(productTemp);
                int id = (int) (productTemp.getId() - 1);
                productList.get(id).setQuantity(productTemp.getQuantity() + quanlity);
                productAdapter.notifyDataSetChanged();
            }

        }
    }
}
