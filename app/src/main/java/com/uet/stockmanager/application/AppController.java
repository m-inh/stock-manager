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
        p1.setQuantity((long) 100);
        p1.setPrice((long) 6000);

        long p1_id = pDao.insert(p1);

        Product p2 = new Product();
        p2.setName("Pepsi");
        p2.setCategory("Nước ngọt");
        p2.setQuantity((long) 20);
        p2.setPrice((long) 8000);

        long p2_id = pDao.insert(p2);


        Product p3 = new Product();
        p3.setName("Gà");
        p3.setCategory("Thực phẩm");
        p3.setQuantity((long) 50);
        p3.setPrice((long) 80000);

        long p3_id = pDao.insert(p3);

        Product p4 = new Product();
        p4.setName("Táo");
        p4.setCategory("Hoa quả");
        p4.setQuantity((long) 10);
        p4.setPrice((long) 7000);

        long p4_id = pDao.insert(p4);

        Product p5 = new Product();
        p5.setName("Socola");
        p5.setCategory("Bánh kẹo");
        p5.setQuantity((long) 20);
        p5.setPrice((long) 15000);

        long p5_id = pDao.insert(p5);

        Product p6 = new Product();
        p6.setName("Chocco-pice");
        p6.setCategory("Bánh kẹo");
        p6.setQuantity((long) 20);
        p6.setPrice((long) 10000);

        long p6_id = pDao.insert(p6);

        Product p7 = new Product();
        p7.setName("Fanta");
        p7.setCategory("Nước ngọt");
        p7.setQuantity((long) 20);
        p7.setPrice((long) 8000);

        long p7_id = pDao.insert(p7);

        Product p16 = new Product();
        p16.setName("Muối");
        p16.setCategory("Gia vị");
        p16.setQuantity((long) 50);
        p16.setPrice((long) 3000);

        long p16_id = pDao.insert(p16);

        Product p18 = new Product();
        p18.setName("Tỏi");
        p18.setCategory("Thực Phẩm");
        p18.setQuantity((long) 60);
        p18.setPrice((long) 3500);

        long p18_id = pDao.insert(p18);

        Product p20 = new Product();
        p20.setName("Lê");
        p20.setCategory("Hoa quả");
        p20.setQuantity((long) 200);
        p20.setPrice((long) 500);

        long p20_id = pDao.insert(p20);

        //insertSale for 12 months

        insertSale(p6, p6_id, 2, "1483274559726"); // 1/01/2017
        insertSale(p18, p18_id, 2, "1483373676019"); // 2/1/2017
        insertSale(p3, p3_id, 1, "1483619162923"); // 5/1/2017

        insertSale(p20, p20_id, 3, "1486052029169"); // 2/2/2017
        insertSale(p1, p1_id, 2, "1486556257462"); // 8/2/2017

        insertSale(p2, p2_id, 1, "1488716230445"); // 5/3/2017
        insertSale(p3, p3_id, 1, "1488989564454"); // 8/3/2017
        insertSale(p5, p5_id, 4, "1490631082696"); // 27/3/2017

        insertSale(p7, p7_id, 3, "1491062985803"); // 1/4/2017
        insertSale(p16, p16_id, 3, "1491581353822"); // 7/4/2017
        insertSale(p4, p4_id, 2, "1491568881227"); // 7/04/2017

    }

    private void insertSale(Product p, long p_id, int quantity, String timeAdd) {

        Sale s1 = new Sale();
        s1.setQuanlity((long) quantity);

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
