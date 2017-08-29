package com.nciebt.zyj.data.sql;

import com.nciebt.zyj.utils.TraceUtils;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：MenuTable
 * 类描述：TODO (菜单实体)
 * 创建人：xs
 * 创建时间：2017/4/24 11:03
 * 修改人：xs
 * 修改时间：2017/4/24 11:03
 * @version v1.0
 */
public class MenuTable  implements Serializable {


    /**
     * 父菜单
     */
    private MenuTable mParentNode = null;
    /**
     * authId : 4FB532ED0BCD1992E053CC5C010A811F
     * authName : 全部
     * parentId : 4FB532ED0BCB1992E053CC5C010A811F
     * authLevel : 2
     * url : 全部url
     * sortNum : 1
     * iconId : 全部
     */
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

    public MenuTable(){}
    public MenuTable(String AUTH_ID, String AUTH_NAME, int AUTH_LEVEL, String PARENT_ID, String URL, int SORT_NUM, String ICON_ID, MenuTable mParentNode, Map< String, MenuTable > mChildMap, List< MenuTable > mChildList){
        super();

        this.mParentNode = mParentNode;
        this.mChildMap = mChildMap;
        this.mChildList = mChildList;
    }

    public void setmParentNode(MenuTable mParentNode) {
        this.mParentNode = mParentNode;
    }

    public MenuTable getmParentNode() {
        return mParentNode;
    }

    /**
     * 子菜单列表 无序
     */
    private Map< String, MenuTable > mChildMap = null;

    /**
     * 子节点数据list 有序
     */
    private List< MenuTable > mChildList = null;


    /**
     * 添加一个子节点
     */
    public void addChild( MenuTable menuNodeEntity )
    {
        if ( null == menuNodeEntity )
        {
            TraceUtils.e( "MenuNodeEntity", "不能添加null作为子菜单" );
            return;
        }

        if ( menuNodeEntity.hasParent() )
        {
            TraceUtils.e( "MenuNodeEntity", "子菜单不能有其他父及菜单" );
            return;
        }

        if ( null == mChildMap )
        {
            mChildMap = new HashMap< String, MenuTable >();
            mChildList = new ArrayList< MenuTable >();
        }

        // 添加子节点
        mChildMap.put( menuNodeEntity.getAuthId(), menuNodeEntity );
        mChildList.add( menuNodeEntity );

        // 设置父节点
        menuNodeEntity.setmParentNode( this );
        // 设置子节点等级
        if ( this.getAuthLevel() >= 1 )
            menuNodeEntity.setAuthLevel( this.authLevel + 1 );
    }

    /**
     * 是否有上级菜单
     */
    public boolean hasParent()
    {
        return null != mParentNode;
    }



    /**
     * 根据菜单ID获取子菜单
     */
    public MenuTable getChildById( int menuId )
    {
        if ( hasChild() )
        {
            return null;
        }

        return mChildMap.get( menuId );
    }

    /**
     * 是否有子菜单
     */
    public boolean hasChild()
    {
        return !(null == mChildMap || mChildMap.size() < 1);
    }


    public Map<String, MenuTable> getmChildMap() {
        return mChildMap;
    }

    public void setmChildMap(Map<String, MenuTable> mChildMap) {
        this.mChildMap = mChildMap;
    }

    public List<MenuTable> getmChildList() {
        return mChildList;
    }

    public void setmChildList(List<MenuTable> mChildList) {
        this.mChildList = mChildList;
    }

    /**
     * 子节点排序
     */
    public void shortChild()
    {
        if ( !hasChild() )
            return;

        Collections.sort( mChildList, new Comparator< MenuTable >()
        {
            @Override
            public int compare( MenuTable lhs, MenuTable rhs )
            {
                if ( null == lhs || null == rhs )
                    return 1;

                if ( lhs.getSortNum() < rhs.getSortNum() )
                    return -1;
                return 1;
            }
        } );

        // 排序子节点
        int len = mChildList.size();
        for ( int i = 0; i < len; i++ )
        {
            MenuTable menuNodeEntity = mChildList.get( i );
            if ( null == menuNodeEntity )
                continue;
            menuNodeEntity.shortChild();
        }
    }

    /**
     * 获取子节点总数
     */
    public int getChildCount()
    {
        if ( hasChild() )
        {
            return mChildMap.size();
        }
        return 0;
    }

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
