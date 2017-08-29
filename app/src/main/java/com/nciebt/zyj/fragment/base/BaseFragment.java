package com.nciebt.zyj.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nciebt.zyj.App;
import com.nciebt.zyj.utils.TraceUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类名称：BaseFragment
 * 类描述：TODO(Fragment基类,项目中所有Fragment都需要继承此类,（全屏Fragment需要继承BaseFullScreenFragment）)
 * 创建人：x_s
 * 创建时间：2017年4月6日 下午4:17:39
 * 修改人：x_s
 * 修改时间：2017年4月6日 下午4:17:39
 * 修改备注：
 *
 * @version v1.0
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;

    /**
     * 是否是需要全屏
     */
    private boolean mIsChangeToFullScreen = false;


    private View rootView;
    /**
     * 主菜单ID
     */
    private String mGroupID = null;

    /**
     * 二级菜单名称
     */
    private String subName;

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    /**
     * Fragment显示模式 默认模式、宽屏模式
     */
    public enum FragmentMod {
        NORMAL, WIDESCREEN
    }

    /**
     * 当前Fragmnet显示模式 默认模式(右侧)、宽屏模式(二级菜单被关闭)
     */
    private FragmentMod mFragmentMod = FragmentMod.NORMAL;

    /**
     * 初始化
     */
    private void initView() {
        // 初始化逻辑处理
        init();

        // 获取网络数据
        initData();

    }


    /**
     * 初始化
     */
    public abstract void init();

    //获取布局Id
    public abstract int getLayoutId();

    /**
     * 获取基础数据
     */
    public abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            if (App.getContext().getCurrentFragmentLayoutId() == getLayoutId()) {
                return;
            }
            initView();
        } catch (Exception e) {
            TraceUtils.e("xs", "xs!!!!!" + e.getMessage());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    /**
     * 主菜单ID
     */
    public String getmGroupID() {
        return mGroupID;
    }

    /**
     * 主菜单ID
     */
    public void setmGroupID(String mGroupID) {
        this.mGroupID = mGroupID;
    }



    /**
     * 设置是否可以全屏显示
     */
    public void setIsChangeToFullScreen(boolean mIsChangeToFullScreen) {
        this.mIsChangeToFullScreen = mIsChangeToFullScreen;
    }

    /**
     * 当前Fragmnet显示模式: 默认模式(右侧)、宽屏模式(二级菜单被关闭)
     */
    public FragmentMod getFragmentMod() {
        return mFragmentMod;
    }

    /**
     * 当前Fragmnet显示模式: 默认模式(右侧)、宽屏模式(二级菜单被关闭)
     */
    public void setFragmentMod(FragmentMod fragmentMod) {
        this.mFragmentMod = fragmentMod;
    }
}
