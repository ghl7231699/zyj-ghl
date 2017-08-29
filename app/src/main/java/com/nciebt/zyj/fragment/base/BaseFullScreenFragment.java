package com.nciebt.zyj.fragment.base;

import android.os.Bundle;
import android.widget.RadioButton;

import com.nciebt.zyj.R;
import com.nciebt.zyj.utils.PadConfigInfo;
import com.nciebt.zyj.utils.TraceUtils;

/**
 * 类名称：BaseFullScreenFragment
 * 类描述：TODO (全屏显示)
 * 创建人：xs
 * 创建时间：2017/4/18 17:12
 * 修改人：xs
 * 修改时间：2017/4/18 17:12
 * @version v1.0
 */
public class BaseFullScreenFragment extends BaseFragment{

    public BaseMenuFragment	baseMenuFragment;

    public boolean			isFullScreen	= true;



    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        // 设置当前的Fragment是否可以全屏显示
        setIsChangeToFullScreen( true );
    }

    /**
     * 将当前Fragment启动成全屏模式
     */
    public void startFullScreenMod( RadioButton toFullScreenBtn )
    {
        if ( isFullScreen )
        {
            isFullScreen = false;
            toFullScreenBtn.setChecked( false );
            setExpandScrollListlViewWidth();
        }
        else
        {
            isFullScreen = true;
            toFullScreenBtn.setChecked( true );
            setNarrowScrollListlViewWidth();
        }

        if ( baseMenuFragment != null )
        {
            baseMenuFragment.setImageOnClick();
        }
    }
    /**
     *获取当前页面内容区域的宽高
     **/
    public void setIScreen( boolean isFullScreenMod )
    {
        refreshScrollListView();
    }

    /**
     * 刷新、重置ScrollListView
     * */
    public void refreshScrollListView()
    {
    }

    public boolean onBackPressed()
    {
        return false;
    }

    /**
     * 隐藏全屏切换按钮
     * */
    public void hiddFullBtn()
    {

    }

    /**
     * 放大ScrollListlView
     * */
    protected void setExpandScrollListlViewWidth()
    {

    }

    /**
     * 缩小ScrollListlView
     * */
    public void setNarrowScrollListlViewWidth()
    {

    }

    @Override
    public void init() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {

    }
}
