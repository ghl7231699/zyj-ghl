package com.nciebt.zyj.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.nciebt.zyj.App;

/**
 * 类名称：SimpleFragmentActivity
 * 类描述：TODO(没有菜单栏的FragmentActivity基类)
 * 创建人：x_s
 * 创建时间：2017年4月6日 下午5:09:18
 * 修改人：x_s
 * 修改时间：2017年4月6日 下午5:09:18
 * 修改备注：
 * @version v1.0
 */
public class SimpleFragmentActivity extends FragmentActivity
{

	@Override
	protected void onCreate( Bundle arg0 )
	{
		super.onCreate( arg0 );
		App.getContext().setCurrentActivityContext( this );
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		App.getContext().setCurrentActivityContext( this );
	}
}

