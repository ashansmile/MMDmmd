package com.maian.mmd.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.entity.PersonService;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public class ServiceListViewAdapter extends BaseAdapter {
    private List<PersonService> list;

    public ServiceListViewAdapter(List<PersonService> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.service_listview_item, null);
            vh.textView_service_name = (TextView) convertView.findViewById(R.id.textView_service_name);
            // vh.getTextView_service_adress = (TextView) convertView.findViewById(R.id.textView_service_adress);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.textView_service_name.setText("服务器名：" + list.get(position).serviceName);
        //vh.getTextView_service_adress.setText(list.get(position).url);
        return convertView;
    }

    class ViewHolder {
        TextView textView_service_name;
        //TextView getTextView_service_adress;
    }
}