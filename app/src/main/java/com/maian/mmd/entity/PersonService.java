package com.maian.mmd.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/16.
 */

@Table(name = "personService")
public class PersonService implements Serializable {
    @Column(name = "_id",isId = true,autoGen = true)
    public int ID;
    @Column(name = "serviceName")
    public String serviceName;

    @Column(name = "serviceAddress")
    public String serviceAddress;

    @Column(name = "duankou")
    public  String duankou;

    @Column(name = "url")
    public String url;

    public PersonService() {
    }

    public PersonService(String serviceName, String serviceAddress) {
        this.serviceName = serviceName;
        this.serviceAddress = serviceAddress;
    }

    public PersonService(String serviceName, String serviceAddress, String duankou, String url) {
        this.serviceName = serviceName;
        this.serviceAddress = serviceAddress;
        this.duankou = duankou;
        this.url = url;
    }
}
