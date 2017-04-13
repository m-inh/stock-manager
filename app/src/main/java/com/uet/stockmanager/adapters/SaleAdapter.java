package com.uet.stockmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.models.Sale;

import java.util.List;

public class SaleAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater lf;
    private List<Sale> saleList;

    public SaleAdapter(Context context, List<Sale> saleList){
        this.context = context;
        this.saleList = saleList;
        lf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return saleList.size();
    }

    @Override
    public Object getItem(int position) {
        return saleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = lf.inflate(R.layout.item_sale,null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name_sale);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tv_sale_quanlity);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_sale_price);
            holder.tvTimeStamp = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else {
            holder = (SaleAdapter.ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvQuantity;
        TextView tvPrice;
        TextView tvTimeStamp;
    }
}
