package com.nciebt.zyj.data;

import android.text.TextUtils;

import com.nciebt.zyj.data.sql.Menu;
import com.nciebt.zyj.data.sql.MenuTable;
import com.nciebt.zyj.utils.TraceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类名称：MenuNodeManager
 * 类描述：TODO (菜单数据管理器)
 * 创建人：xs
 * 创建时间：2017/4/14 15:46
 * 修改人：xs
 * 修改时间：2017/4/14 15:46
 * @version v1.0
 */
public class MenuNodeManager extends BaseData{


    /**
     * 工作区 全部集合
     */
    private Map< String, MenuTable >	mMenuNodeMap;
    private List<MenuTable>	mWorkMenuList;

    /**
     * 工作区 一级菜单集合
     */
    private Map< String, MenuTable >	mWorkspaceRootMenuMap;
    private List< MenuTable > mWorkspaceRootMenuList;


    @Override
    public void init()
    {
        mMenuNodeMap = new HashMap< String, MenuTable >();
        mWorkMenuList = new ArrayList< MenuTable >();

        mWorkspaceRootMenuMap = new HashMap< String, MenuTable >();
        mWorkspaceRootMenuList = new ArrayList< MenuTable >();
    }

    public void clear()
    {
        mMenuNodeMap.clear();
        mWorkMenuList.clear();

        mWorkspaceRootMenuList.clear();
        mWorkspaceRootMenuMap.clear();
    }

    /**
     * 作者 xs
     * 方法名称：getmWorkspaceRootMenuList
     * 描述：TODO (获取一级菜单)
     * 创建时间：2017/4/28 10:38
     */
    public List< MenuTable > getmWorkspaceRootMenuList()
    {
        return mWorkspaceRootMenuList;
    }


    /**
     *
     * @author gzq
     * 方法名称：getmWorkspaceMenuList
     * 描述：TODO(获取所有工作区菜单节点集合)
     * 创建时间：2015年11月3日 下午2:02:39
     * List<MenuNodeEntity>
     * @return
     */
    public List< MenuTable > getmWorkspaceMenuList()
    {
        return mWorkMenuList;
    }


    /**
     * 根据一个唯一菜单ID 获取这个菜单数据对象
     */
    public MenuTable getMenuNodeById( String menuId )
    {
        if( TextUtils.isEmpty( menuId ))
        {
            return null;
        }
        return hasMenuNode() ? mMenuNodeMap.get( menuId ) : null;
    }


    /**
     * 菜单节点排序
     */
    public void shortNode()
    {
        // 排序工作区菜单
        shortNode( mWorkspaceRootMenuList );

    }

    /**
     * 排序节点
     */
    public void shortNode( List< MenuTable > list )
    {
        if ( null == list )
            return;
        // 排序菜单节点
        Collections.sort( list, new Comparator< MenuTable >()
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
        for ( int i = 0; i < list.size(); i++ )
        {
            MenuTable menuNodeEntity = list.get( i );
            if ( null == menuNodeEntity )
                continue;
            menuNodeEntity.shortChild();
        }

    }

    /**
     * 是否有菜单数据
     */
    public boolean hasMenuNode()
    {
        return mWorkMenuList.size() >= 1;
    }

    /**
     * 是否有工作区主菜单数据
     */
    public boolean hasWorkspaceRootMenu()
    {
        return mWorkspaceRootMenuMap.size() >= 1;
    }




    public Map<String, MenuTable> getmAllMenuMap() {
        return mMenuNodeMap;
    }


    public List<MenuTable> getmWorkMenuList() {
        return mWorkMenuList;
    }

    public void setmWorkMenuList(List<Menu> mWorkMenuList) {
        for (Menu menu:mWorkMenuList){
            MenuTable menuTable = new MenuTable();
            menuTable.setAuthId(menu.getAuthId());
            menuTable.setAuthName(menu.getAuthName());
            menuTable.setAuthLevel(menu.getAuthLevel());
            menuTable.setUrl(menu.getUrl());
            menuTable.setIconId(menu.getIconId());
            menuTable.setJurisdictionId(menu.getJurisdictionId());
            menuTable.setParentId(menu.getParentId());
            menuTable.setSortNum(menu.getSortNum());
            add(menuTable);
        }
    }

    /**
     * @author brok1n
     *         方法名称：handleNodeLevel
     *         描述：TODO(处理菜单节点关系)
     *         创建时间：2015年10月13日 下午5:57:01
     *         void
     */
    public void handleNodeLevel()
    {
        try
        {
            // 遍历所有菜单节点集合
            Iterator< String > it = getmAllMenuMap().keySet().iterator();
            while ( it.hasNext() )
            {
                String menuId = it.next();
                MenuTable menuNodeEntity =  getMenuNodeById( menuId );
                if ( null == menuNodeEntity )
                    continue;
                // 设置父子节点关系
                if ( !menuNodeEntity.hasParent() )
                {
                    setParent( menuNodeEntity );
                }
            }

            // 排序
            shortNode();
        }
        catch ( Exception e )
        {
            TraceUtils.e( "handleNodeLevel error" );
        }
    }


    /**
     * @author brok1n
     *         方法名称：setParent
     *         描述：TODO(绑定父节点和子节点关系)
     *         创建时间：2015年10月14日 上午9:24:17
     *         void
     * @param menuNodeEntity
     */
    private void setParent( MenuTable menuNodeEntity )
    {
        if ( null == menuNodeEntity )
        {
            return;
        }
        String parentId = menuNodeEntity.getParentId();
        MenuTable parentNode = DataCenter.getInstance().getmMenuNodeManager().getMenuNodeById( parentId );
        if ( null != parentNode )
        {
            parentNode.addChild( menuNodeEntity );
        }
    }


    /**
     * 添加一个菜单数据对象
     */
    public void add( MenuTable menuNodeEntity )
    {
        if ( null == menuNodeEntity )
            return;

        //去除菜单重复异常情况
        if( mMenuNodeMap.containsKey( menuNodeEntity.getAuthId() ))
        {
            return ;
        }

        mMenuNodeMap.put( menuNodeEntity.getAuthId(), menuNodeEntity );


        // 如果是工作区菜单。就添加到工作区菜单list中
        // 如果是工作区主菜单。就添加到工作区菜单中
//        if ( menuNodeEntity.getAuthType() == 1 )
//        {
            mWorkMenuList.add(menuNodeEntity);
            if ( menuNodeEntity.getAuthLevel() == 2 )
            {
                mWorkspaceRootMenuMap.put( menuNodeEntity.getAuthId(), menuNodeEntity );
                mWorkspaceRootMenuList.add( menuNodeEntity );
            }
//        }

    }
}
