package com.uet.stockmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uet.stockmanager.R;
import com.uet.stockmanager.models.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater lf;
    private List<Product> pList;

    public ProductAdapter(Context context, List<Product> pList) {
        this.context = context;
        lf = LayoutInflater.from(context);
        this.pList = pList;
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

        ViewHolder holder;

        if (convertView == null) {
            convertView = lf.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCategory = (TextView) convertView.findViewById(R.id.tv_category);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tv_quantity);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Product product = this.pList.get(position);
        holder.tvName.setText(product.getName());
        holder.tvCategory.setText("Loại: " + product.getCategory());
        holder.tvQuantity.setText("Số Lượng: " + product.getQuantity());
        holder.tvPrice.setText("Giá nhập: " + product.getPrice() + "VND");

        return convertView;
    }

    public void setpList(List<Product> pList) {
        this.pList = pList;
    }

    public List<Product> getpList() {
        return pList;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvCategory;
        TextView tvQuantity;
        TextView tvPrice;
    }
}
