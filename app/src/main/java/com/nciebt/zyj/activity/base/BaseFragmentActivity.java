package com.nciebt.zyj.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.nciebt.zyj.App;
import com.nciebt.zyj.fragment.base.BaseFragment;
import com.nciebt.zyj.utils.PadConfigInfo;
import com.nciebt.zyj.utils.TraceUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 类名称：BaseFragmentActivity
 * 类描述：TODO( 所有项目中要使用的FragmentActivity的基类<br>
 * 项目中所有要使用的FragmentActivity <br>
 * 必须都要继承当前这个BaseFragmentActivity)
 * 创建人：x_s
 * 创建时间：2017年4月6日 下午4:55:14
 * 修改人：x_s
 * 修改时间：2017年4月6日 下午4:55:14
 * 修改备注：
 *
 * @version v1.0
 */
public abstract class BaseFragmentActivity extends FragmentActivity  {

    /**
     * 是否是在前台
     */
    public static boolean mIsForeground = false;

    /**
     * 根据xml布局文件ID 获取当前需要全屏显示的Fragment
     */
    public BaseFragment getFullScreenFragment(int layoutId) {
        return App.getContext().getCurrentFragment(layoutId);
    }


    /**
     * Activity生命周期的创建、初始化周期
     */
    @Override
    protected void onCreate(Bundle saveInstanceBundle) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(saveInstanceBundle);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        PadConfigInfo.setDensity(dm.densityDpi);
        PadConfigInfo.setWidth(dm.widthPixels);
        PadConfigInfo.setHeight(dm.heightPixels);
        App.getContext().setCurrentActivityContext(this);
        try {
            initView();
        } catch (Exception e) {
            TraceUtils.e(e.toString());
        }

    }


    /**
     * (当界面显示时，将当前Activity保存到全局)
     *
     * @see FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        mIsForeground = true;
        super.onResume();
        App.getContext().setCurrentActivityContext(this);
    }

    /**
     * @author x_s
     * 方法名称：initView
     * 描述：TODO(初始化当前界面)
     * 创建时间：2017年4月6日 下午5:05:12
     * void
     */
    private void initView() {
        // TODO Auto-generated method stub
        // 设置布局文件及初始化
        setContentView();
        // 给当前Activity初始化ViewUtils

        ButterKnife.bind(this);
        //findViewById();
        //setListener();
        // 获取网络数据
        obtainData();

    }


    /**
     * 设置布局xml文件 setContentView(R.layout.activity_main);
     */
    public abstract void setContentView();

    /**
     * 从网络上获取数据
     */
    public abstract void obtainData();

    /**
     * 刷新界面数据
     */
    public abstract void refreshUiData();


    /**
     * 从全屏模式返回时，在这里重新加载全屏模式编辑后的Fragment
     */
    public void resumeFullScreenFragment() {

    }

    /**
     * 当使用StartActivity启动一个Activity之后。 返回时。在这里通知当前Activity下管理的第一级的所有的Fragmnet
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        App.getContext().setCurrentActivityContext(this);


        List<Fragment> arrayList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : arrayList) {
            if (null != fragment)
                fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onPause() {
        mIsForeground = false;
        super.onPause();
    }
}
