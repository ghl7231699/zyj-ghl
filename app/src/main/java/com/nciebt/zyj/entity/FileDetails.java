package com.nciebt.zyj.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 类名称：FileDetails
 * 类描述：文件详情
 * 创建人：ghl
 * 创建时间：2017/4/3 16:12
 * 修改人：ghl
 * 修改时间：2017/4/3 16:12
 *
 * @version v1.0
 */

public class FileDetails extends DataSupport implements Serializable {

    /**
     * contentId : 502CE18C5C03F191E053CC5C010A3B31
     * title : jiankangxian
     * directoriesId : 4FB532ED0BCC1992E053CC5C010A811F
     * type : PDF
     * content : url3
     * sort : 3
     * remark : REMARK
     * iconId : 健康险
     */

    private String title;//标题
    private String directoriesId;//归属目录(菜单的功能节点id)
    private String type;//类型（文本/FLASH/PPT/VIDEO/PDF）
    private String content;//内容/地址
    private String sort;//排序
    private String remark;//备注
    private String iconId;//菜单图片ID
    private String savePath;//保存路径
    private String permission;//唯一标识，对应的该业务员可以查看的产品权限
    private String contentId;//唯一标识，保存到本地的名字

    public FileDetails() {
    }

    public FileDetails(String name, String type) {
        this.title = name;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectoriesId() {
        return directoriesId;
    }

    public void setDirectoriesId(String directoriesId) {
        this.directoriesId = directoriesId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
