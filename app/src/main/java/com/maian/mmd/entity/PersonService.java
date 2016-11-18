package com.maian.mmd.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/16.
 */
public class PersonService implements Serializable {
    public String serviceName;
    public String serviceAddress;

    public PersonService() {
    }

    public PersonService(String serviceName, String serviceAddress) {
        this.serviceName = serviceName;
        this.serviceAddress = serviceAddress;
    }
}
