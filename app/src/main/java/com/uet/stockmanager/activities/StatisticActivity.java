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
import java.util.Calendar;
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
//        list.add(new LineChartItem(generateDataLine(1), getApplicationContext()));
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
        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 11; i >= 0; i--) {
            // get sales in target month
            Calendar now = Calendar.getInstance();
            Calendar pivotTime = Calendar.getInstance();
            int nowMonth = now.get(Calendar.MONTH);

            pivotTime.set(pivotTime.get(Calendar.YEAR), pivotTime.get(Calendar.MONTH), 1);

            long upTime = 0;
            long downTime = 0;

            if (i == 0) {
                upTime = now.getTimeInMillis();
                downTime = pivotTime.getTimeInMillis();
            } else {
                upTime = getTimeMiliBeforeMonth(pivotTime, i - 1);
                downTime = getTimeMiliBeforeMonth(pivotTime, i);
            }

            //query db
            int salesInMonth = countSalesBetweenTime(upTime, downTime);

            e1.add(new Entry(12 - i, salesInMonth));
        }

        LineDataSet d1 = new LineDataSet(e1, "Số lượng đơn hàng");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        ArrayList<Entry> e2 = new ArrayList<Entry>();

        for (int i = 11; i >= 0; i--) {
            // get sales in target month
            Calendar now = Calendar.getInstance();
            Calendar pivotTime = Calendar.getInstance();
            pivotTime.set(pivotTime.get(Calendar.YEAR), pivotTime.get(Calendar.MONTH), 1);

            long upTime = 0;
            long downTime = 0;

            if (i == 0) {
                upTime = now.getTimeInMillis();
                downTime = pivotTime.getTimeInMillis();
            } else {
                upTime = getTimeMiliBeforeMonth(pivotTime, i - 1);
                downTime = getTimeMiliBeforeMonth(pivotTime, i);
            }

            //query db
            int totalProductInMonth = countProductIsSaledBetweenTime(upTime, downTime);

            e2.add(new Entry(12 - i, totalProductInMonth));
        }

        LineDataSet d2 = new LineDataSet(e2, "Số lượng sản phẩm bán");
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

    private int countProductIsSaledBetweenTime(long upTime, long downTime) {
        String[] values = {downTime + "", upTime + ""};

        Database db = ((AppController) getApplication()).getDaoSession().getDatabase();
        Cursor cursor = db.rawQuery("SELECT PRODUCT_ID FROM SALE WHERE (TIMESTAMP >= ?) AND (TIMESTAMP <= ?) GROUP BY PRODUCT_ID", values);

        int result = cursor.getCount();
        cursor.close();

        return result;
    }

    private int countSalesBetweenTime(long upTime, long downTime) {
        String[] values = {downTime + "", upTime + ""};

        Database db = ((AppController) getApplication()).getDaoSession().getDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS total_sales FROM SALE WHERE (TIMESTAMP >= ?) AND (TIMESTAMP <= ?)", values);

        int total_sales_index = cursor.getColumnIndex("total_sales");

        if (cursor.getCount() == 0) {
            return 0;
        }

        cursor.moveToFirst();
        int result = cursor.getInt(total_sales_index);
        cursor.close();

        return result;
    }

    private long getTimeMiliBeforeMonth(Calendar pivot, int beforeMonth) {
        int targetMonth = pivot.get(Calendar.MONTH) - beforeMonth;
        int targetYear = pivot.get(Calendar.YEAR);
        int targetDate = pivot.get(Calendar.DATE);

        if (targetMonth < 1) {
            targetMonth = 12 + targetMonth;
            targetYear--;
        }

        pivot.set(targetYear, targetMonth, targetDate);
        return pivot.getTimeInMillis();
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        Calendar now = Calendar.getInstance();
        int monthToStart = now.get(Calendar.MONTH);
        Log.i("statistic_act", now.getTime().toString());
        int nowMonth = now.get(Calendar.MONTH);

        for (int i = 0; i <= nowMonth; i++) {
            // get sales in target month

            long upTime = 0;
            long downTime = 0;

            if (i == nowMonth) {
                upTime = now.getTimeInMillis();
                downTime = getTimeMiliInThisYear(i);
            } else {
                upTime = getTimeMiliInThisYear(i + 1);
                downTime = getTimeMiliInThisYear(i);
            }

            //query db
            long moneyInMonth = countMoneyBetweenTime(upTime, downTime);

            entries.add(new BarEntry(i + 1, (int) moneyInMonth));
        }

        for (int i = nowMonth + 1; i <= 12; i++) {
            entries.add(new BarEntry(i, 0));
        }

        BarDataSet d = new BarDataSet(entries, "Doanh số");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private long getTimeMiliInThisYear(int targetMonth) {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), targetMonth, 1);

        return c.getTimeInMillis();
    }

    private long countMoneyBetweenTime(long upTime, long downTime) {
        String[] values = {downTime + "", upTime + ""};

        Database db = ((AppController) getApplication()).getDaoSession().getDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(PRICE) AS total_money FROM SALE WHERE (TIMESTAMP >= ?) AND (TIMESTAMP <= ?)", values);

        int total_money_index = cursor.getColumnIndex("total_money");

        if (cursor.getCount() == 0) {
            return 0;
        }

        cursor.moveToFirst();
        long result = cursor.getLong(total_money_index);
        cursor.close();

        return result;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private PieData generateDataPie(int cnt) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        Calendar now = Calendar.getInstance();
        Log.d("statistic_act", now.getTime().toString());
        int nowMonth = now.get(Calendar.MONTH);

        for (int i = 0; i <= nowMonth; i++) {
            // get sales in target month

            long upTime = 0;
            long downTime = 0;

            if (i == nowMonth) {
                upTime = now.getTimeInMillis();
                downTime = getTimeMiliInThisYear(i);
            } else {
                upTime = getTimeMiliInThisYear(i + 1);
                downTime = getTimeMiliInThisYear(i);
            }

            //query db
            long moneyInMonth = countMoneyBetweenTime(upTime, downTime);

            entries.add(new PieEntry((float) moneyInMonth, "Tháng " + (i + 1)));
        }

//        for (int i = nowMonth + 1; i <= 12; i++) {
//            entries.add(new PieEntry((float) 0, "Tháng " + (i + 1)));
//        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(d);
        return cd;
    }
}
