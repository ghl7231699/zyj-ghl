package com.nciebt.zyj.data.sql;


import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 类名称：JurisdictionTable
 * 类描述：(权限表)
 * 创建人：xs
 * 创建时间：2017/5/4 11:24
 * 修改人：xs
 * 修改时间：2017/5/4 11:24
 * @version v1.0
 */
public class JurisdictionTable extends DataSupport implements Serializable {


    /**
     * 职级
     * */
   private String AGENT_GRADE ;

    /**
     * 渠道
     * */
    private String BRANCHTYPE ;
    /**
     * 机构
     * */
    private String ORGANIZATION_ID ;

    /**
     * 主键ID
     * */
    private String AuthId ;


    public String getAGENT_GRADE() {
        return AGENT_GRADE;
    }

    public void setAGENT_GRADE(String AGENT_GRADE) {
        this.AGENT_GRADE = AGENT_GRADE;
    }

    public String getBRANCHTYPE() {
        return BRANCHTYPE;
    }

    public void setBRANCHTYPE(String BRANCHTYPE) {
        this.BRANCHTYPE = BRANCHTYPE;
    }

    public String getORGANIZATION_ID() {
        return ORGANIZATION_ID;
    }

    public void setORGANIZATION_ID(String ORGANIZATION_ID) {
        this.ORGANIZATION_ID = ORGANIZATION_ID;
    }

    public String getAuthId() {
        return AuthId;
    }

    public void setAuthId(String authId) {
        AuthId = authId;
    }
}
