package com.maian.mmd.entity;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/23.
 */

public class ErJiLiebiao implements Serializable {
    public String id;
    public String name;
    public String alias;
    public String showOnPhone;
    public Boolean hasChild;
    public String customMobileImage;

    public ErJiLiebiao() {
    }

    public ErJiLiebiao(String id, String name, String showOnPhone, Boolean hasChild) {
        this.id = id;
        this.name = name;
        this.showOnPhone = showOnPhone;
        this.hasChild = hasChild;
    }

    public ErJiLiebiao(String id, String name, String showOnPhone, Boolean hasChild, String customMobileImage) {
        this.id = id;
        this.name = name;
        this.showOnPhone = showOnPhone;
        this.hasChild = hasChild;
        this.customMobileImage = customMobileImage;
    }

    public ErJiLiebiao(String id, String name, String alias, String showOnPhone, Boolean hasChild, String customMobileImage) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.showOnPhone = showOnPhone;
        this.hasChild = hasChild;
        this.customMobileImage = customMobileImage;
    }
}
