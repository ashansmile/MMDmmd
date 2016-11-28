package com.maian.mmd.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.entity.ResultCode;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class GridViewAdapter extends BaseAdapter {
    List<ResultCode> listText;
    public int[] imgs = { R.drawable.icon_big_1, R.drawable.icon_big_2,
            R.drawable.icon_big_3, R.drawable.icon_big_4,
            R.drawable.icon_big_5, R.drawable.icon_big_6,
            R.drawable.icon_big_7, R.drawable.icon_big_8, R.drawable.icon_big_9, };

    public GridViewAdapter(List<ResultCode> listText) {
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
        ViewHolder vh = null;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.grildview_item,null);
            vh.text = (TextView) convertView.findViewById(R.id.textView_grildView);
            vh.img = (ImageView) convertView.findViewById(R.id.img_grildView) ;
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.text.setText(listText.get(position).catName);
        vh.img.setImageResource(imgs[position]);

        return convertView;
    }
    class ViewHolder{
        TextView text;
        ImageView img;
    }
}
