package com.maian.mmd.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.utils.ScreenHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/11/14.
 */
public class GridViewAdapter extends BaseAdapter {
    List<ResultCode> listText;
    private Activity activity;
    public int[] imgs = {R.drawable.icon_big_1, R.drawable.icon_big_2,
            R.drawable.icon_big_3, R.drawable.icon_big_4,
            R.drawable.icon_big_5, R.drawable.icon_big_6,
            R.drawable.icon_big_7, R.drawable.icon_big_8};

    public GridViewAdapter(List<ResultCode> listText,Activity activity) {
        this.listText = listText;
        this.activity = activity;
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
        int temp = 0;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.grildview_item, null);
            vh.text = (TextView) convertView.findViewById(R.id.textView_grildView);
            vh.img = (ImageView) convertView.findViewById(R.id.img_grildView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.img.setLayoutParams(ScreenHelper.setgrildViewItemPix(activity,vh.img));
        vh.text.setText(listText.get(position).catName);
        if (position > 7) {
            vh.img.setImageResource(imgs[position - 8]);
        } else if (position > 14) {
            vh.img.setImageResource(imgs[0]);
        } else {
            vh.img.setImageResource(imgs[position]);
        }
        return convertView;
    }

    class ViewHolder {
        TextView text;
        ImageView img;
    }
}
