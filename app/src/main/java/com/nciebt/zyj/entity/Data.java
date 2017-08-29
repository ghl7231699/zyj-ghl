package com.nciebt.zyj.entity;

import com.nciebt.zyj.data.sql.Menu;
import com.nciebt.zyj.data.sql.MenuTable;

import java.util.List;

/**
 * 类名称：Data
 * 类描述：产品清单
 * 创建人：ghl
 * 创建时间：2017/5/15 14:22
 * 修改人：ghl
 * 修改时间：2017/5/15 14:22
 *
 * @version v1.0
 */

public class Data {
    private List<Menu> menu;//菜单

    private List<FileDetails> content;//产品

    private VersionEntity getSoftVersionInfo;


    public List<FileDetails> getProduct() {
        return content;
    }

    public void setProduct(List<FileDetails> product) {
        this.content = product;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public VersionEntity getGetSoftVersionInfo() {
        return getSoftVersionInfo;
    }

    public void setGetSoftVersionInfo(VersionEntity getSoftVersionInfo) {
        this.getSoftVersionInfo = getSoftVersionInfo;
    }
}
