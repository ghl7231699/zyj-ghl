package com.nciebt.zyj.fragment.base;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nciebt.zyj.App;
import com.nciebt.zyj.R;
import com.nciebt.zyj.adapter.MenuUiAdapter;
import com.nciebt.zyj.data.sql.MenuTable;
import com.nciebt.zyj.utils.SrxUtil;
import com.nciebt.zyj.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;


/**
 * 类名称：BaseMenuFragment
 * 类描述：TODO (二级菜单基类)
 * 创建人：xs
 * 创建时间：2017/4/18 17:13
 * 修改人：xs
 * 修改时间：2017/4/18 17:13
 *
 * @version v1.0
 */
public abstract class BaseMenuFragment extends BaseFragment implements OnChildClickListener,
        OnGroupExpandListener, OnGroupClickListener {

    @BindView(R.id.huanying_layout)
    LinearLayout huanyingLayout;

    @BindView(R.id.frameLayout1)
    FrameLayout frameLayout1;

    // 左侧二级菜单是否被关闭
    private boolean mBisShow = false;

    /**
     * 要显示的子菜单
     */
    private MenuTable mMenuTable;

    /**
     * 二级菜单listview
     */
    @BindView(R.id.ebt_second_level_menu_listView)
    ExpandableListView ebtSecondLevelMenuListView;

    /**
     * 二级菜单总布局
     */
    @BindView(R.id.ebt_second_level_fl)
    RelativeLayout mMenuLayout;
    /**
     * 二级菜单内容布局
     */
    @BindView(R.id.layout_content)
    FrameLayout layoutContent;
    /**
     * 二级菜单内容布局
     */
    @BindView(R.id.layout_content_bg)
    FrameLayout mContentLayoutBg;

    // 放置二级菜单页面和标题
    protected List<MenuTable> mList;

    protected BaseFragment mFragment;                    // 各个fragment的总引用

    /**
     * 二级菜单mAdapter
     */
    protected MenuUiAdapter mAdapter;

    protected String mIid = "";        // 点击的是否是当前页面
    /**
     * 是否初始化
     * */
    protected boolean isInit = false;

    @Override
    public int getLayoutId() {
        return R.layout.fm_menu_ui;
    }


    /**
     * listView 设置二级菜单<br>
     * adapter 为索引，需要实例化 ，并传参
     */
    public abstract void setMenuAdapter(ExpandableListView listView, MenuUiAdapter adapter);


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void init() {
        setMenuAdapter(ebtSecondLevelMenuListView, mAdapter);
        ebtSecondLevelMenuListView.setOnGroupExpandListener(this);
        ebtSecondLevelMenuListView.setOnChildClickListener(this);
        ebtSecondLevelMenuListView.setOnGroupClickListener(this);
        ebtSecondLevelMenuListView.expandGroup(getGroupIndex());
        mAdapter.setRefresh( getGroupIndex(), getChildIndex() );
    }

    /**
     * 获取要展开的Group index
     */
    public int getGroupIndex() {
        if (null == mMenuTable) {
            return 0;
        }

        int lev = mMenuTable.getAuthLevel();
        MenuTable secondNode = mMenuTable;
        while (lev > 3) {
            secondNode = mMenuTable.getmParentNode();
            lev = secondNode.getAuthLevel();
        }
        mAdapter.isChild = !(secondNode.hasChild() && mAdapter != null);
        boolean flag = false;
        int index = 0;
        for (int i = 0; i < mList.size(); i++) {
            MenuTable entity = mList.get(i);
            if (TextUtils.equals(entity.getAuthId(), secondNode.getAuthId())) {
                if (TextUtils.equals(entity.getAuthName(), secondNode.getAuthName())
                        && TextUtils.equals(entity.getUrl(), secondNode.getUrl())
                        && entity.getAuthLevel() == secondNode.getAuthLevel()
                        && entity.getSortNum() == secondNode.getSortNum()
                        && TextUtils.equals(entity.getParentId(), secondNode.getParentId())) {
                    flag = true;
                    index = i;
                }
            }
        }
        int res = flag ? index : 0;
        return res;
    }

    /**
     * 获取要展开的 child index
     */
    public int getChildIndex() {
        if (null == mMenuTable) {
            return 0;
        }

        int lev = mMenuTable.getAuthLevel();
        MenuTable secondNode = mMenuTable;
        while (lev > 4) {
            secondNode = mMenuTable.getmParentNode();
            lev = secondNode.getAuthLevel();
        }
        if (null == secondNode) {
            return 0;
        }

        List<MenuTable> tmpList = secondNode.getmParentNode().getmChildList();
        if (null == tmpList) {
            return 0;
        }

        int tmpIndex = tmpList.indexOf(secondNode);

        return tmpIndex >= 0 ? tmpIndex : 0;
    }

    /**
     * 根据ID创建一个Fragment
     */
    public boolean setFragment(MenuTable MenuTable) {
        if (null == MenuTable)
            return false;
        if (MenuTable.getAuthId().equals(mIid)) {
            ToastUtils.toast(SrxUtil.getStringById(R.string.ebt_string_no_click));
            return false;
        } // 判断点击需要创建的Fragment是否是当前Fragment

        mIid = MenuTable.getAuthId();
        FragmentActivity fragmentActivity = getActivity();
        if (null == fragmentActivity) {
            return false;
        }
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mFragment != null) {
            fragmentTransaction.remove(mFragment);
            mFragment = null;
        }

        mFragment = (BaseFragment) App.getContext().getFragment(MenuTable.getUrl());
        if (mFragment != null) {
            mFragment.setArguments(getArguments());
            fragmentTransaction.replace(R.id.layout_content, mFragment);
            fragmentTransaction.commit();

            try {
                BaseFullScreenFragment baseFullScreenFragment = (BaseFullScreenFragment) mFragment;
                baseFullScreenFragment.baseMenuFragment = this;
            } catch (Exception e) {
                e.printStackTrace();
            }
            mFragment.setmGroupID(MenuTable.getAuthId());
            mFragment.setSubName(MenuTable.getAuthName());
            isInit = true;
            return true;
        } else {
            ToastUtils.toast(SrxUtil.getStringById(R.string.ebt_string_no_page));
            return false;
        }
    }


    /**
     * 隐藏二级菜单<br>
     * true - 显示<br>
     * false - 隐藏<br>
     **/
    public void setImageOnClick() {

        if (null == mMenuLayout) {
            return;
        }

        if (mBisShow) {
            mMenuLayout.setVisibility(View.VISIBLE);
            mContentLayoutBg.setBackgroundResource(R.drawable.ebt_drawable_content_top_b_bg);
            mBisShow = false;
            if (null != mFragment)
                mFragment.setFragmentMod(FragmentMod.NORMAL);
        } else {
            mMenuLayout.setVisibility(View.GONE);
            mContentLayoutBg.setBackgroundResource(R.drawable.ebt_drawable_content_top_b_bg);
            mBisShow = true;
            if (null != mFragment)
                mFragment.setFragmentMod(FragmentMod.WIDESCREEN);
        }
    }

    /**
     * 记录当前显示的子页面
     */
    public void showFragment(MenuTable entity) {
        if (null == entity) {
            return;
        }

        mMenuTable = entity;

    }

    /**
     * 从全屏模式返回时，在这里重新加载全屏模式编辑后的Fragment
     */
    public void resumeFullScreenFragment() {
        FragmentActivity fragmentActivity = getActivity();
        if (null == fragmentActivity) {
            return;
        }
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int fragmentID = App.getContext().getCurrentFragmentLayoutId();
        BaseFragment fragment = App.getContext().getCurrentFragment(fragmentID);
        int id = R.id.layout_content;
        try {
            fragmentTransaction.replace(id, fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            // TraceUtils.e( "XS", "!!!!!!!" + TraceUtils.getStackTraceString( e
            // ) );
        }

        // App.getContext().setCurrentFragment( -1, null );

    }

    /**
     * listView只显示一个分组
     */
    @Override
    public void onGroupExpand(int groupPosition) {
        if (null == mList) {
            // TraceUtils.e( " BaseMenuFragmet ",
            // "BaseMenuFragment onGroupExpand listView is null! " );
            return;
        }
        for (int i = 0, count = ebtSecondLevelMenuListView.getExpandableListAdapter().getGroupCount(); i < count; i++) {
            if (groupPosition != i) {// 关闭其他分组
                ebtSecondLevelMenuListView.collapseGroup(i);
            }
        }
    }

}
