package com.maian.mmd.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by admin on 2016/11/22.
 */
@Table(name = "zhuye")
public class ResultCode implements Serializable{

    /**
     * accessible : true
     * catId : I402881bb0153cf85cf855eb10153cfad2dbd0341
     * catName : 功能演示
     * deleted : false
     * extended : {"hiddenInBrowse":"false","showOnPC":true,"showOnPad":false,"showOnPhone":false}
     * fullPath : 资源定制\仪表盘目录\功能演示
     * hiddenInBrowse : false
     * order : 2147483647
     * relatePage : openresource.jsp?resid=I402881bb0153d0e4d0e4e83b0153d138dc430d64&refresh=true&showtoolbar=false
     * themeId : PUBLISH.T.I402881361d8f368901511eaf2644069d
     * themeName : 仪表盘主题
     * type : DEFAULT_TREENODE
     */
    @Column(name = "_id",isId = true,autoGen = true)
    public int _ID;

    @Column(name = "accessible")
    public boolean accessible;

    @Column(name = "catId")
    public String catId;

    @Column(name = "catName")
    public String catName;

    @Column(name = "deleted")
    public boolean deleted;

    @Column(name = "extended")
    public String extended;

    @Column(name = "fullPath")
    public String fullPath;

    @Column(name = "hiddenInBrowse")
    public boolean hiddenInBrowse;

    @Column(name = "order")
    public int order;

    @Column(name = "relatePage")
    public String relatePage;

    @Column(name = "themeId")
    public String themeId;

    @Column(name = "themeName")
    public String themeName;

    @Column(name = "type")
    public String type;


    public ResultCode() {
    }

    public ResultCode(boolean accessible, String catId, String catName, boolean deleted, String extended, String fullPath, boolean hiddenInBrowse, int order, String relatePage, String themeId, String themeName, String type) {
        this.accessible = accessible;
        this.catId = catId;
        this.catName = catName;
        this.deleted = deleted;
        this.extended = extended;
        this.fullPath = fullPath;
        this.hiddenInBrowse = hiddenInBrowse;
        this.order = order;
        this.relatePage = relatePage;
        this.themeId = themeId;
        this.themeName = themeName;
        this.type = type;
    }
}
