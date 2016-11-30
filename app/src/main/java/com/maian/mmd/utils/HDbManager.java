package com.maian.mmd.utils;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;

/**
 * Created by ashan on 2016/10/17.
 */

public class HDbManager extends DbManager.DaoConfig {
    public static int SAVEDBBIAOZHI = 0;
    public static DbManager.DaoConfig getZhuYeDb(){
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
    public static DbManager.DaoConfig getServiceDB(){
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

    public static DbManager.DaoConfig getUserDB(){
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("user_db")
                .setDbVersion(1)
                .setAllowTransaction(true);
                /*.setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {

                    }
                });*/
        return daoConfig;
    }
}
