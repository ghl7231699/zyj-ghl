package com.nciebt.zyj.data.sql;

import org.litepal.crud.DataSupport;

/**
 * 类名称：CommonFileDetailed
 * 类描述：常用界面添加的功能表(
 * 创建人：ghl
 * 创建时间：2017/6/8 10:24
 * 修改人：ghl
 * 修改时间：2017/6/8 10:24
 *
 * @version v1.0
 */

public class CommonFileDetailed extends DataSupport {
    private int flag;  //是否添加常用
    private String contentId;//唯一标识，保存到本地的名字
    private String permission;//唯一标识，对应的该业务员可以查看的产品权限

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
