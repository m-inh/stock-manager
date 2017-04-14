package com.uet.stockmanager.activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.uet.stockmanager.R;
import com.uet.stockmanager.application.AppController;
import com.uet.stockmanager.charts.BarChartItem;
import com.uet.stockmanager.charts.ChartItem;
import com.uet.stockmanager.charts.LineChartItem;
import com.uet.stockmanager.charts.PieChartItem;
import com.uet.stockmanager.dialogs.AddProductDialog;
import com.uet.stockmanager.models.Product;
import com.uet.stockmanager.models.ProductDao;
import com.uet.stockmanager.models.Sale;
import com.uet.stockmanager.models.SaleDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity {

    private static final String TAG = "StatisticActivity";

    @BindView(R.id.lv_main)
    ListView lvChart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SaleDao saleDao;
    private ProductDao pDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Thống kê");

        saleDao = ((AppController) getApplication()).getDaoSession().getSaleDao();
        pDao = ((AppController) getApplication()).getDaoSession().getProductDao();

        // init chart before add any data to it
        Utils.init(this);

        initViews();
    }

    private void initViews() {
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        list.add(new LineChartItem(generateDataLine(1), getApplicationContext()));
        list.add(new BarChartItem(generateDataBar(2), getApplicationContext()));
        list.add(new PieChartItem(generateDataPie(3), getApplicationContext()));

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lvChart.setAdapter(cda);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * adapter that supports 3 different item types
     */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine(int cnt) {
        // todo: query data from db

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 1; i <= 12; i++) {
            e1.add(new Entry(i, (int) (Math.random() * 65) + 40));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 1; i <= 12; i++) {
            e2.add(new Entry(i, e1.get(i - 1).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);

        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(sets);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {
        // todo: query data from db

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 1; i <= 12; i++) {
            entries.add(new BarEntry(i, (int) (Math.random() * 70) + 30));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet " + cnt);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie(int cnt) {
        Database db = ((AppController) getApplication()).getDaoSession().getDatabase();
        Cursor cursor = db.rawQuery("SELECT PRODUCT_ID, SUM(PRICE) AS total_price FROM SALE GROUP BY PRODUCT_ID ORDER BY total_price DESC", new String[]{});

        int total_price_index = cursor.getColumnIndex("total_price");
        int product_id_index = cursor.getColumnIndex("PRODUCT_ID");

        long totalPrice = 0;
        long priceTop1 = 0;
        long priceTop2 = 0;
        long priceTop3 = 0;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            totalPrice += cursor.getLong(total_price_index);

            cursor.moveToNext();
        }

        Log.i(TAG, "Tổng bán ra: " + totalPrice + " (VND)");

        cursor.moveToFirst();

        Product p_top_1 = pDao.load(cursor.getLong(product_id_index));
        String nameTop1 = p_top_1.getName();
        priceTop1 = cursor.getLong(total_price_index);
        Log.i(TAG, "Top 1: " + nameTop1);
        Log.i(TAG, "Top 1: " + priceTop1 + " (VND)");

        cursor.moveToNext();
        Product p_top_2 = pDao.load(cursor.getLong(product_id_index));
        String nameTop2 = p_top_2.getName();
        priceTop2 = cursor.getLong(total_price_index);
        Log.i(TAG, "Top 2: " + nameTop2);
        Log.i(TAG, "Top 2: " + priceTop2 + " (VND)");

        cursor.moveToNext();
        Product p_top_3 = pDao.load(cursor.getLong(product_id_index));
        String nameTop3 = p_top_3.getName();
        priceTop3 = cursor.getLong(total_price_index);
        Log.i(TAG, "Top 3: " + nameTop3);
        Log.i(TAG, "Top 3: " + priceTop3 + " (VND)");

        long other = totalPrice - priceTop1 - priceTop2 - priceTop3;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((float) priceTop1, nameTop1));
        entries.add(new PieEntry((float) priceTop2, nameTop2));
        entries.add(new PieEntry((float) priceTop3, nameTop3));
        entries.add(new PieEntry((float) other, "Other"));

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(d);
        return cd;
    }
}
