package com.maian.mmd.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.entity.ErJiLiebiao;
import com.maian.mmd.utils.ScreenHelper;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class ErJiListViewAdapter extends BaseAdapter {
    private List<ErJiLiebiao> listText;
    private Activity activity;

    public ErJiListViewAdapter(List<ErJiLiebiao> listText,Activity activity) {
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

        vh.img_erji.setLayoutParams(ScreenHelper.setItemPix(activity,vh.img_erji));
        vh.textView_title.setText(listText.get(position).alias);

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER)
                .setRadius(DensityUtil.dip2px(60))
                .setLoadingDrawableId(R.drawable.icon_big_2)
                .setFailureDrawableId(R.drawable.icon_big_2)
                .setCrop(true)
                .build();
        x.image().bind(vh.img_erji,listText.get(position).customMobileImage,imageOptions);

        return convertView;
    }
    class ViewHolder{
        TextView textView_title;
        ImageView img_erji;
    }
}
