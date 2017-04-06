package com.uet.stockmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uet.stockmanager.R;
import com.uet.stockmanager.models.Product;

import java.util.List;

/**
 * Created by TooNies1810 on 4/6/17.
 */

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lf;
    private List<Product> pList;

    public ProductAdapter(Context context) {
        this.context = context;
        lf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pList.size();
    }

    @Override
    public Object getItem(int position) {
        return pList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = lf.inflate(R.layout.item_product, null);
        }
        return convertView;
    }

    public void setpList(List<Product> pList) {
        this.pList = pList;
    }

    public List<Product> getpList() {
        return pList;
    }
}
