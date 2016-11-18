package com.maian.mmd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/15.
 */
public class User implements Serializable {
    public String name;
    public String id;
    public String pwd;

    public User(String name, String id, String pwd) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
    }
}
