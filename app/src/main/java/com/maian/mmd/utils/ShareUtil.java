package com.maian.mmd.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.maian.mmd.entity.ChildResult;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Created by admin on 2017/1/18.
 */

public class ShareUtil {
    private Activity activity;

    public ShareUtil(Activity activity) {
        this.activity = activity;
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
           // System.out.println("----plat" + platform);

            Toast.makeText(activity, " 分享成功", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            // Toast.makeText(getBaseContext(), platform.toString()+t.toString() + " 分享失败啦", Toast.LENGTH_LONG).show();
            if (t != null) {
                //System.out.println("----platform" + platform);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(activity, " 你取消了分享", Toast.LENGTH_SHORT).show();
        }
    };

    //分享链接
    public void shareMessageURL(ChildResult entity) {
        if (entity.hasChild == true) {
            Toast.makeText(activity, "不是报表不能分享", Toast.LENGTH_SHORT).show();
        } else {
         //   System.out.println("----url"+Contact.getwebUrl(entity.id, "demo", "demo"));
            new ShareAction(activity)
                    .withTitle("报表" + entity.alias)
                    .withTargetUrl(Contact.getwebUrl(entity.id, "demo", "demo"))
                    .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
                   // .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                    .setCallback(umShareListener).open();
        }


    }

    public void shareMessageImg( Bitmap bitmap) {
        new ShareAction(activity)
                //.withFile(new File(imgPath))
                .withTitle("图片")
                .withMedia(new UMImage(activity, bitmap))
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN_FAVORITE)
               // .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setCallback(umShareListener).open();
    }

}
