package com.maian.mmd.utils;

import android.app.Activity;
import android.widget.Toast;

import com.maian.mmd.entity.ChildResult;

import org.xutils.DbManager;
import org.xutils.db.Selector;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

/**
 * Created by ashan on 2016/10/17.
 */

public class HDbManager extends DbManager.DaoConfig {
    public static int SAVEDBBIAOZHI = 0;

    public static DbManager.DaoConfig getZhuYeDb() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("zhuye_db")
                .setDbVersion(1)
                .setAllowTransaction(true)
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {

                    }
                });
        return daoConfig;
    }

    public static DbManager.DaoConfig getServiceDB() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("service_db")
                .setDbVersion(1)
                .setAllowTransaction(true);
                /*.setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {

                    }
                });*/
        return daoConfig;
    }

    public static DbManager.DaoConfig getUserDB() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("user_db")
                .setDbVersion(1)
                .setAllowTransaction(true);
        return daoConfig;
    }

    public static DbManager.DaoConfig getFormDB() {

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("form_db")
                .setDbVersion(1)
                .setAllowTransaction(true);

        return daoConfig;
    }

    public static void collectForm(ChildResult child, Activity activity,String id) {
        DbManager db = x.getDb(getFormDB());
        try {
            Selector<ChildResult> n = db.selector(ChildResult.class).where("id","=",id);
            long a =n.count();
            if (a == 0){
                db.save(child);
                Toast.makeText(activity, "收藏成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(activity, "已经被收藏过", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "收藏失败", Toast.LENGTH_SHORT).show();
        }
    }
}
