package com.nciebt.zyj.entity;

/**
 * 类名称：ProfitBookEntity
 * 类描述：分红宝典实体类
 * 创建人：ghl
 * 创建时间：2017/7/5 10:09
 * 修改人：ghl
 * 修改时间：2017/7/5 10:09
 *
 * @version v1.0
 */

public class ProfitBookEntity {
    private String menu;    //二级菜单
    private String url;     //二级菜单对应的WebView地址

    public ProfitBookEntity(String menu, String url) {
        this.menu = menu;
        this.url = url;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
