package com.maian.mmd.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maian.mmd.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ErJiListViewAdapter extends BaseAdapter {
    List<String> listText;

    public ErJiListViewAdapter(List<String> listText) {
        this.listText = listText;
    }

    @Override
    public int getCount() {
        return listText.size();
    }

    @Override
    public Object getItem(int position) {
        return listText.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.listview_item,null);
            vh.textView_title = (TextView) convertView.findViewById(R.id.textView_erji);
            vh.img_erji = (ImageView) convertView.findViewById(R.id.img_erji);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textView_title.setText(listText.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView textView_title;
        ImageView img_erji;
    }
}
