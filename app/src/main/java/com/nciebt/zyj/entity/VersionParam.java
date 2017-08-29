package com.nciebt.zyj.entity;

import com.nciebt.zyj.data.DataCenter;

/**
 * 类名称：VersionParam
 * 类描述：(更新请求类的请求参数)
 * 创建人：xs
 * 创建时间：2017/5/26 10:16
 * 修改人：xs
 * 修改时间：2017/5/26 10:16
 *
 * @version v1.0
 */
public class VersionParam {


    public VersionParam(){
        String channel = DataCenter.getInstance().getBRANCHTYPE();
        setType("11");
        String orgId = DataCenter.getInstance().getORGANIZATION_ID();
        setOrgId(orgId);
        setChannel(channel);
    }
    /**
     * 下载的APK类型
     * */
    private String type;
    /**
     * 渠道
     * */
    private String channel;
    /**
     * 机构代码
     * */
    private String orgId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }



}
