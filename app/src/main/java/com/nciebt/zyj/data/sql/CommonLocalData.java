package com.nciebt.zyj.data.sql;

/**
 * 类名称：CommonLocalData
 * 类描述：常用界面数据
 * 创建人：ghl
 * 创建时间：2017/4/26 14:54
 * 修改人：ghl
 * 修改时间：2017/4/26 14:54
 *
 * @version v1.0
 */

public class CommonLocalData  {

    private String name;
    private int imageId;
    private int t;

    public CommonLocalData(String name, int imageId, int t) {
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
}
