package com.nciebt.zyj.data.sql;

import android.text.TextUtils;
import com.nciebt.zyj.inter.DetailedListDAO;
import org.litepal.crud.DataSupport;
import java.util.List;
import java.util.UUID;

/**
 * 类名称：DetailedListModule
 * 类描述：TODO (数据库操作实现类)
 * 创建人：xs
 * 创建时间：2017/4/24 11:46
 * 修改人：xs
 * 修改时间：2017/4/24 11:46
 *
 * @version v1.0
 */
public class DetailedListModule implements DetailedListDAO {


    @Override
    public String addJurisdiction(String ORGANIZATION_ID, String BRANCHTYPE, String AGENT_GRADE) {
        String authId = isExist(ORGANIZATION_ID, BRANCHTYPE, AGENT_GRADE);
        if (TextUtils.isEmpty(authId)) {
            JurisdictionTable jurisdictionTable = new JurisdictionTable();
            jurisdictionTable.setAGENT_GRADE(AGENT_GRADE);
            jurisdictionTable.setORGANIZATION_ID(ORGANIZATION_ID);
            jurisdictionTable.setBRANCHTYPE(BRANCHTYPE);
            jurisdictionTable.setAuthId(UUID.randomUUID().toString().replaceAll("\\-", ""));
            jurisdictionTable.save();
            return jurisdictionTable.getAuthId();
        }
        return authId;
    }

    @Override
    public String getJurisdictionID(String ORGANIZATION_ID, String BRANCHTYPE, String AGENT_GRADE) {
        String authId = isExist(ORGANIZATION_ID, BRANCHTYPE, AGENT_GRADE);
        return authId;
    }

    @Override
    public boolean isExistMenu(String jurisdictionId) {
        List<Menu> mDestList = DataSupport.findAll(Menu.class);
        for (Menu menuTable : mDestList) {
            if (TextUtils.equals(jurisdictionId, menuTable.getJurisdictionId())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String isExist(String ORGANIZATION_ID, String BRANCHTYPE, String AGENT_GRADE) {
        List<JurisdictionTable> mDestList = DataSupport.findAll(JurisdictionTable.class);
        for (JurisdictionTable jurisdictionTable : mDestList) {
            if (TextUtils.equals(ORGANIZATION_ID, jurisdictionTable.getORGANIZATION_ID()) &&
                    TextUtils.equals(BRANCHTYPE, jurisdictionTable.getBRANCHTYPE()) &&
                    TextUtils.equals(AGENT_GRADE, jurisdictionTable.getAGENT_GRADE())) {
                return jurisdictionTable.getAuthId();
            }
        }
        return null;
    }

    @Override
    public void insertMenu(List<Menu> list, String jurisdictionId) {
        if (isExistMenu(jurisdictionId))
            DataSupport.deleteAll(Menu.class, "jurisdictionId = ? ", jurisdictionId);
        for (Menu menuTable : list) {
            menuTable.save();
        }
    }

    @Override
    public List<Menu> queryMenu(String jurisdictionId) {
        List<Menu> list = DataSupport.where("jurisdictionId = ?", jurisdictionId).find(Menu.class);
        return list;
    }


}
