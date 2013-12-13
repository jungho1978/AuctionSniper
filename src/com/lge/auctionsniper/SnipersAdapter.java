package com.lge.auctionsniper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SnipersAdapter extends BaseAdapter {
    Context context;
    private SniperState item;

    public SnipersAdapter(Context context, SniperState item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        
        TextView item = (TextView) convertView.findViewById(R.id.item);
        item.setText(this.item.itemId);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        price.setText(String.valueOf(this.item.price));
        TextView bid = (TextView) convertView.findViewById(R.id.bid);
        bid.setText(String.valueOf(this.item.bid));
        TextView status = (TextView) convertView.findViewById(R.id.status);
        status.setText(this.item.status);
        
        return convertView;
    }

}
