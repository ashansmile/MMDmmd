package com.maian.mmd.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maian.mmd.R;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ChildResult;
import com.maian.mmd.utils.Contact;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by admin on 2017/2/6.
 */

public class LevelGridAdapter extends BaseAdapter {
    private List<ChildResult> listResult;
    private Activity activity;
    public int[] imgs = {R.drawable.dir1, R.drawable.dir2,
            R.drawable.dir3, R.drawable.dir4,
            R.drawable.dir5, R.drawable.dir6,
            R.drawable.dir7, R.drawable.dir8, R.drawable.dir9};

    public LevelGridAdapter(List<ChildResult> listResult, Activity activity) {
        this.listResult = listResult;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listResult.size();
    }

    @Override
    public Object getItem(int position) {
        return listResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LevelGridAdapter.ViewHolder vh;
        if (convertView == null) {
            vh = new LevelGridAdapter.ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.grildview_item, null);
            vh.textView_title = (TextView) convertView.findViewById(R.id.textView_grildView);
            vh.img_grid = (ImageView) convertView.findViewById(R.id.img_grildView);
            convertView.setTag(vh);
        } else {
            vh = (LevelGridAdapter.ViewHolder) convertView.getTag();
        }
        vh.textView_title.setText(listResult.get(position).alias);
        if (MMDApplication.fromNet) {
            ImageOptions imageOptions = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER)
                    .setLoadingDrawableId(R.drawable.dir3)
                    .setFailureDrawableId(R.drawable.dir3)
                    .setCircular(true)
                    .setCrop(true)
                    .build();
            x.image().bind(vh.img_grid, Contact.getImgUrl(listResult.get(position).customImageId),imageOptions);
        } else {

            if (position > 8) {
                vh.img_grid.setImageResource(imgs[position - 9]);
            } else if (position > 16) {
                vh.img_grid.setImageResource(imgs[0]);
            } else {
                vh.img_grid.setImageResource(imgs[position]);
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView textView_title;
        ImageView img_grid;
    }
}