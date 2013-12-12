package com.lge.auctionsniper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SnipersAdapter extends BaseAdapter {

    private Context context;
    private Item item;

    public SnipersAdapter(Context context, Item item) {
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
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        
        setText(convertView, R.id.item, this.item.item);
        setText(convertView, R.id.price, String.valueOf(this.item.price));
        setText(convertView, R.id.bid, String.valueOf(this.item.bid));
        setText(convertView, R.id.status, String.valueOf(this.item.status));
        
        return convertView;
    }

    private void setText(View view, int id, String text) {
        TextView tv = (TextView) view.findViewById(id);
        tv.setText(text);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    
}
