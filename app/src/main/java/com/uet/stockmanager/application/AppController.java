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

        Product p2 = new Product();
        p2.setName("Pepsi");
        p2.setCategory("Nước ngọt");
        p2.setQuantity((long) 2000);
        p2.setPrice((long) 8000);

        long p2_id = pDao.insert(p2);


        Product p3 = new Product();
        p3.setName("Chicken");
        p3.setCategory("Thực phẩm");
        p3.setQuantity((long) 500);
        p3.setPrice((long) 80000);

        long p3_id = pDao.insert(p3);

        Product p4 = new Product();
        p4.setName("Táo");
        p4.setCategory("Hoa quả");
        p4.setQuantity((long) 5000);
        p4.setPrice((long) 7000);

        long p4_id = pDao.insert(p4);

        Product p5 = new Product();
        p5.setName("Socola");
        p5.setCategory("Bánh kẹo");
        p5.setQuantity((long) 2000);
        p5.setPrice((long) 15000);

        long p5_id = pDao.insert(p5);

        Product p6 = new Product();
        p6.setName("Chocco-pice");
        p6.setCategory("Bánh kẹo");
        p6.setQuantity((long) 2000);
        p6.setPrice((long) 10000);

        long p6_id = pDao.insert(p6);

        Product p7 = new Product();
        p7.setName("Fanta");
        p7.setCategory("Nước ngọt");
        p7.setQuantity((long) 2000);
        p7.setPrice((long) 8000);

        long p7_id = pDao.insert(p7);

        Product p8 = new Product();
        p8.setName("Dưa hấu");
        p8.setCategory("Hoa quả");
        p8.setQuantity((long) 1000);
        p8.setPrice((long) 7000);

        long p8_id = pDao.insert(p8);

        Product p9 = new Product();
        p9.setName("Dưa chuột");
        p9.setCategory("Hoa quả");
        p9.setQuantity((long) 2000);
        p9.setPrice((long) 3000);

        long p9_id = pDao.insert(p9);

        Product p10 = new Product();
        p10.setName("Thịt lợn");
        p10.setCategory("Thực phẩm");
        p10.setQuantity((long) 200);
        p10.setPrice((long) 70000);

        long p10_id = pDao.insert(p10);

        Product p11 = new Product();
        p11.setName("Gạo");
        p11.setCategory("Thực phẩm");
        p11.setQuantity((long) 13000);
        p11.setPrice((long) 8000);

        long p11_id = pDao.insert(p11);

        Product p12 = new Product();
        p12.setName("Khoai lang");
        p12.setCategory("Thực phẩm");
        p12.setQuantity((long) 2000);
        p12.setPrice((long) 6000);

        long p12_id = pDao.insert(p12);

        Product p13 = new Product();
        p13.setName("Thịt bò");
        p13.setCategory("Thực phẩm");
        p13.setQuantity((long) 2000);
        p13.setPrice((long) 18000);

        long p13_id = pDao.insert(p13);

        Product p14 = new Product();
        p14.setName("Sữa");
        p14.setCategory("Thực phẩm");
        p14.setQuantity((long) 3000);
        p14.setPrice((long) 6000);

        long p14_id = pDao.insert(p14);

        Product p15 = new Product();
        p15.setName("Nước mắm");
        p15.setCategory("Gia vị");
        p15.setQuantity((long) 2000);
        p15.setPrice((long) 14000);

        long p15_id = pDao.insert(p15);

        Product p16 = new Product();
        p16.setName("Muối");
        p16.setCategory("Gia vị");
        p16.setQuantity((long) 5000);
        p16.setPrice((long) 3000);

        long p16_id = pDao.insert(p16);

        Product p17 = new Product();
        p17.setName("Hành tây");
        p17.setCategory("Thực phẩm");
        p17.setQuantity((long) 4000);
        p17.setPrice((long) 4500);

        long p17_id = pDao.insert(p17);

        Product p18 = new Product();
        p18.setName("Tỏi");
        p18.setCategory("Thực Phẩm");
        p18.setQuantity((long) 6000);
        p18.setPrice((long) 3500);

        long p18_id = pDao.insert(p18);

        Product p19 = new Product();
        p19.setName("Nho");
        p19.setCategory("Hoa quả");
        p19.setQuantity((long) 6000);
        p19.setPrice((long) 50000);

        long p19_id = pDao.insert(p19);

        Product p20 = new Product();
        p20.setName("Lê");
        p20.setCategory("Hoa quả");
        p20.setQuantity((long) 20000);
        p20.setPrice((long) 19000);

        long p20_id = pDao.insert(p20);

        //insertSale for 12 months

        insertSale(p1, p1_id, 900, "1491826605193"); // 10/4/2017
        insertSale(p2, p2_id, 1000, "1488716230445"); // 5/3/2017
        insertSale(p1, p1_id, 2000, "1486556257462"); // 8/2/2017
        insertSale(p3, p3_id, 500, "1483619162923"); // 5/1/2017
        insertSale(p2, p2_id, 100, "1481200430778"); // 8/12/2016
        insertSale(p7, p7_id, 600, "1478694861275"); // 09/11/2016
        insertSale(p5, p5_id, 5000, "1476102892268"); // 10/10/2016
        insertSale(p11, p11_id, 700, "1472906121510"); // 03/09/2016
        insertSale(p9, p9_id, 300, "1471523756803"); // 18/08/2016
        insertSale(p14, p14_id, 50, "1469622978667"); // 27/07/2016
        insertSale(p20, p20_id, 600, "1464784598245"); // 1/06/2016
        insertSale(p18, p18_id, 800, "1462106286717"); // 1/05/2016
        insertSale(p4, p4_id, 2000, "1491568881227"); // 7/04/2017
        insertSale(p6, p6_id, 900, "1483274559726"); // 1/01/2017
        insertSale(p12, p12_id, 900, "1472820230889"); // 2/09/2016
        insertSale(p3, p3_id, 1000, "1479646014419"); // 20/11/2016






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
