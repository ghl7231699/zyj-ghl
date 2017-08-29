package com.nciebt.zyj.fragment;

import android.view.View;
import android.widget.ExpandableListView;

import com.nciebt.zyj.adapter.MenuUiAdapter;
import com.nciebt.zyj.data.DataCenter;
import com.nciebt.zyj.data.sql.MenuTable;
import com.nciebt.zyj.fragment.base.BaseMenuFragment;

/**
 * 类名称：SecondaryMenuFragment
 * 类描述：TODO (二级菜单实现类)
 * 创建人：xs
 * 创建时间：2017/4/19 16:26
 * 修改人：xs
 * 修改时间：2017/4/19 16:26
 * @version v1.0
 */
public class SecondaryMenuFragment extends BaseMenuFragment {

    @Override
    public int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    public void setMenuAdapter(ExpandableListView listView, MenuUiAdapter adapter) {
        mList = DataCenter.getInstance().getmMenuNodeManager().getMenuNodeById( getmGroupID() ).getmChildList();
        adapter =  mAdapter  = new MenuUiAdapter( getActivity(), getmGroupID() );
        listView.setAdapter( adapter );

        if(!isInit){
            MenuTable menu = mList.get( 0 ).getmChildList().get( 0 );
            setFragment( menu );
        }

    }

    @Override
    public void init() {
        super.init();
    }
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        MenuTable menu = mList.get( groupPosition ).getmChildList().get( childPosition );
        // setFragment(menu);
        if ( setFragment( menu ) )
        {
            mAdapter.setRefresh( groupPosition, childPosition );
        }
        return false;
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        MenuTable menu = mList.get( groupPosition );
        if ( menu.getChildCount() <= 0 )
        {
            mAdapter.isChild = false;
            if ( setFragment( menu ) )
            {
                mAdapter.setRefresh( groupPosition, -1 );
            }
        }
        else
        {
            mAdapter.isChild = true;
        }

        return false;
    }

    @Override
    public void initData() {

    }
}
