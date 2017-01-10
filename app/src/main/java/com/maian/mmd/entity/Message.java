package com.maian.mmd.entity;

import java.io.Serializable;

/**
 * Created by admin on 2017/1/4.
 */

public class Message implements Serializable {
    public String id;
    public String title;
    public String description;
    public String url;

    public Message(){

    }

    public Message(String id, String title, String description, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
