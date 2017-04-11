package com.uet.stockmanager.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.uet.stockmanager.R;
import com.uet.stockmanager.adapters.ProductAdapter;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity{

    private static final String DIALOG_TITLE = "Add New Product";

    @BindView(R.id.lv_main)
    ListView lvMain;
    @BindView(R.id.tb_product_activity)
    Toolbar toolbar;

    private ProductDao pDao;
    private List<Product> productList;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_back);
        toolbar.setTitle("Products");
        toolbar.setTitleTextColor(Color.WHITE);

        pDao = ((AppController) getApplication()).getDaoSession().getProductDao();

        productList = pDao.queryBuilder().list();
        productAdapter = new ProductAdapter(this,productList);
        lvMain.setAdapter(productAdapter);
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
        switch (item.getItemId()){
            case R.id.menu_add_product:
                final Dialog dialog = new Dialog(ProductActivity.this);
                dialog.setContentView(R.layout.dialog_add_product);
                dialog.setTitle(DIALOG_TITLE);
                final EditText edtName = (EditText) dialog.findViewById(R.id.edt_add_product_name);
                final EditText edtCategory = (EditText) dialog.findViewById(R.id.edt_add_product_category);
                final EditText edtID = (EditText) dialog.findViewById(R.id.edt_add_product_id);
                final EditText edtPrice = (EditText) dialog.findViewById(R.id.edt_add_product_price);
                final EditText edtQuantity = (EditText) dialog.findViewById(R.id.edt_add_product_quantity);

                Button btnAdd = (Button) dialog.findViewById(R.id.btn_add_product);
                dialog.show();
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtName.getText().toString().equals("")){
                            Toast.makeText(ProductActivity.this,"Name cann't be empty!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(edtCategory.getText().toString().equals("")){
                            Toast.makeText(ProductActivity.this,"Category cann't be empty!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(edtID.getText().toString().equals("")){
                            Toast.makeText(ProductActivity.this,"ID cann't be empty!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(edtPrice.getText().toString().equals("")){
                            Toast.makeText(ProductActivity.this,"Price cann't be empty!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(edtQuantity.getText().toString().equals("")){
                            Toast.makeText(ProductActivity.this,"Quantity cann't be empty!",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String name = edtName.getText().toString();
                        String category = edtCategory.getText().toString();
                        long id = Long.parseLong(edtID.getText().toString(),10);
                        int price = Integer.parseInt(edtPrice.getText().toString());
                        int quantity = Integer.parseInt(edtQuantity.getText().toString());
                        Product product = new Product(id,name,category,price,quantity);
                        updateListProduct(product);
                        dialog.dismiss();

                    }
                });
                break;
            case android.R.id.home:
                Intent mIntent = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateListProduct(Product product){
        pDao.insert(product);
        productList.add(product);
        productAdapter.notifyDataSetChanged();
    }
}
