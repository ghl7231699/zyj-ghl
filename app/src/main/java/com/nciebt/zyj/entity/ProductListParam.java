package com.nciebt.zyj.entity;

/**
 * 类名称：ProductListParam
 * 类描述：获取产品清单的请求参数
 * 创建人：ghl
 * 创建时间：2017/5/15 10:35
 * 修改人：ghl
 * 修改时间：2017/5/15 10:35
 *
 * @version v1.0
 */

public class ProductListParam {
    /**
     * organization_id : 111
     * branchtype : 222
     * agent_grade : 333
     */

    private String organizationId; //机构
    private String branchtype;//渠道
    private String agentGrade;//职级

    public String getOrganization_id() {
        return organizationId;
    }

    public void setOrganization_id(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getBranchtype() {
        return branchtype;
    }

    public void setBranchtype(String branchtype) {
        this.branchtype = branchtype;
    }

    public String getAgent_grade() {
        return agentGrade;
    }

    public void setAgent_grade(String agentGrade) {
        this.agentGrade = agentGrade;
    }
}
