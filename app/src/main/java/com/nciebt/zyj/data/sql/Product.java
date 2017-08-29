package com.nciebt.zyj.data.sql;

import org.litepal.crud.DataSupport;

/**
 * 类名称：Product
 * 类描述：保存产品到本地
 * 创建人：ghl
 * 创建时间：2017/5/3 15:13
 * 修改人：ghl
 * 修改时间：2017/5/3 15:13
 *
 * @version v1.0
 */

public class Product extends DataSupport {
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品类型
     */
    private int imageId;
    /**
     * 是否添加到常用
     */
    private int t;
    /**
     * 本地存储路径
     */
    private String path;

    public Product(String name, int imageId, int t) {
        this.name = name;
        this.imageId = imageId;
        this.t = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /**
     * content : url1
     * directories_id : 1
     * icon_id : 常用哦
     * id : 739759
     * remark : REMARK
     * sort : 1
     * title : changyong
     * type : type
     */

    private String content;
    private String directories_id;
    private String icon_id;
    private String id;
    private String remark;
    private String sort;
    private String title;
    private String type;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDirectories_id() {
        return directories_id;
    }

    public void setDirectories_id(String directories_id) {
        this.directories_id = directories_id;
    }

    public String getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
