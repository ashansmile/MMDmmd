package com.maian.mmd.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/15.
 */
@Table(name = "user")
public class User implements Serializable {
    @Column(name = "_id",isId = true,autoGen = true)
    public String id;
    @Column(name = "name")
    public String name;
    @Column(name = "pwd")
    public String pwd;
    @Column(name ="inServiceUrl")
    public String inServiceUrl;

    public User() {
    }

    public User(String name,  String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public User(String name, String pwd, String inServiceUrl) {
        this.name = name;
        this.pwd = pwd;
        this.inServiceUrl = inServiceUrl;
    }
}
