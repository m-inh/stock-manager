package com.uet.stockmanager.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.uet.stockmanager.models.DaoMaster;
import com.uet.stockmanager.models.DaoSession;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;
import com.uet.stockmanager.models.Sale;
import com.uet.stockmanager.models.SaleDao;

import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;

public class AppController extends Application {

    private DaoSession daoSession;

    private SaleDao sDao;
    private ProductDao pDao;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String IS_INIT_MOCK_DATA = "mock_data";

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "stockmgr.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        pDao = getDaoSession().getProductDao();
        sDao = getDaoSession().getSaleDao();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if (!sharedpreferences.getBoolean(IS_INIT_MOCK_DATA, false)) {
            initMockData();
        }

    }

    private void initMockData() {
        mockData();

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(IS_INIT_MOCK_DATA, true);
        editor.apply();

        Toast.makeText(this, "Khởi tạo dữ liệu lần đầu tiên", Toast.LENGTH_LONG).show();
    }

    private void mockData() {
        // mock 20 product
        Product p1 = new Product();
        p1.setName("Cocacola");
        p1.setCategory("Nước ngọt");
        p1.setQuantity((long) 1000);
        p1.setPrice((long) 6000);

        long p1_id = pDao.insert(p1);

        insertSale(p1, p1_id, 900, "1491125708954");
    }

    private void insertSale(Product p, long p_id, int quantity, String timeAdd) {

        Sale s1 = new Sale();
        s1.setQuanlity((long) 900);

        long totalPrice = quantity * p.getPrice();
        s1.setPrice(totalPrice);
        s1.setName(p.getName());
        s1.setTimestamp(Long.parseLong(timeAdd));
        s1.setProductId(p_id);

        sDao.insert(s1);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
