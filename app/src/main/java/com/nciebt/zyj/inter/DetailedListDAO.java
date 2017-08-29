package com.nciebt.zyj.inter;

import com.nciebt.zyj.data.sql.Menu;
import java.util.List;

/**
 * 类名称：DetailedListDAO
 * 类描述：TODO (数据库操作类)
 * 创建人：xs
 * 创建时间：2017/4/24 11:38
 * 修改人：xs
 * 修改时间：2017/4/24 11:38
 * @version v1.0
 */
public interface DetailedListDAO  {


    /**
     * 作者 xs
     * 描述：TODO (添加权限)
     * 创建时间：2017/4/24 13:26
     */
    String addJurisdiction(String ORGANIZATION_ID,String BRANCHTYPE,String AGENT_GRADE);
    /**
     * 作者 xs
     * 描述：TODO (查询权限)
     * 创建时间：2017/4/24 13:26
     */
    String getJurisdictionID(String ORGANIZATION_ID,String BRANCHTYPE,String AGENT_GRADE);


    /**
     * 作者 xs
     * 描述：TODO (判断是否有菜单)
     * 创建时间：2017/4/24 13:26
     * @param jurisdictionId  菜单/文件ID
     */
    boolean isExistMenu(String jurisdictionId );
    /**
     * 作者 xs
     * 描述：TODO (判断该职级权限是存在)
     * 创建时间：2017/4/24 13:26
     * @param ORGANIZATION_ID  机构
     * @param BRANCHTYPE  渠道
     * @param  AGENT_GRADE  职级
     */
    String isExist(String ORGANIZATION_ID,String BRANCHTYPE,String AGENT_GRADE);
    /**
     * 作者 xs
     * 描述：TODO (批量插入菜单)
     * 创建时间：2017/4/24 13:26
     * @param list  菜单/文件ID
     */
    void insertMenu(List<Menu> list,String jurisdictionId);

    /**
     * 作者 xs
     * 描述：TODO (查询本地菜单)
     * 创建时间：2017/4/24 13:26
     */
    List<Menu> queryMenu(String jurisdictionId);
}
