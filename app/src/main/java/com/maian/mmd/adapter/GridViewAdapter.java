package com.maian.mmd.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maian.mmd.R;
import com.maian.mmd.base.MMDApplication;
import com.maian.mmd.entity.ResultCode;
import com.maian.mmd.utils.Contact;
import com.maian.mmd.utils.ScreenHelper;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import static com.maian.mmd.utils.ScreenHelper.getScreenHeight;

/**
 * Created by Administrator on 2016/11/14.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<ResultCode> listText;
    private String imgUrl;
    private Activity activity;
    public int[] imgs = {R.drawable.icon_1, R.drawable.icon_2,
            R.drawable.icon_3, R.drawable.icon_4,
            R.drawable.icon_5, R.drawable.icon_6,
            R.drawable.icon_7, R.drawable.icon_8,
            R.drawable.icon_9};

    public GridViewAdapter(List<ResultCode> listText, Activity activity) {
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
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.grildview_item, null);
            vh.text = (TextView) convertView.findViewById(R.id.textView_grildView);
            vh.img = (ImageView) convertView.findViewById(R.id.img_grildView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.text.setLayoutParams(ScreenHelper.setgrildViewItemPix(activity, vh.text));

        //网络解析extens
        ResultCode n = listText.get(position);
        JSONObject obj = (JSONObject) JSON.parse(n.extended);
        try {
            imgUrl = obj.getString("customImageId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        vh.text.setText(listText.get(position).catName);
        if (MMDApplication.fromNet) {
            //网络获取图片处理
            ImageOptions option = new ImageOptions.Builder()
                    .setImageScaleType(ImageView.ScaleType.CENTER)
                    .setLoadingDrawableId(R.drawable.icon_default)
                    .setFailureDrawableId(R.drawable.icon_default)
                    .setCircular(true)
                    .setCrop(true)
                    .build();

            x.image().bind(vh.img, Contact.getImgUrl(imgUrl), option);
        } else {
            if (position > 8) {
                vh.img.setImageResource(imgs[position - 9]);
            } else if (position > 16) {
                vh.img.setImageResource(imgs[0]);
            } else {
                vh.img.setImageResource(imgs[position]);
            }
        }
        return convertView;
    }

    class ViewHolder {
        TextView text;
        ImageView img;
    }
}
