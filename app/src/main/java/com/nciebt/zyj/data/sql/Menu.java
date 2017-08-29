package com.nciebt.zyj.data.sql;


import org.litepal.crud.DataSupport;

/**
 * 类名称：Menu
 * 类描述：TODO (菜单表)
 * 创建人：xs
 * 创建时间：2017/4/24 11:03
 * 修改人：xs
 * 修改时间：2017/4/24 11:03
 * @version v1.0
 */
public class Menu extends DataSupport {
    /**
     * 功能节点id
     * */
    private String authId;
    /**
     * 节点名
     * */
    private String authName;
    /**
     * 父节点id
     * */
    private String parentId;
    /**
     * 节点等级
     * */
    private int authLevel;
    /**
     * 路径
     * */
    private String url;
    /**
     * 排序
     * */
    private int sortNum;
    /**
     * 菜单图片ID
     * */
    private String iconId;
    /**
     * 权限ID
     * */
    private String jurisdictionId;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(int authLevel) {
        this.authLevel = authLevel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getJurisdictionId() {
        return jurisdictionId;
    }

    public void setJurisdictionId(String jurisdictionId) {
        this.jurisdictionId = jurisdictionId;
    }
}
