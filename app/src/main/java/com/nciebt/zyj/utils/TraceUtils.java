package com.nciebt.zyj.utils;

import android.util.Log;

import com.nciebt.zyj.App;

/**
 * 类名称：TraceUtils
 * 类描述：TODO(log打印)
 * 创建人：x_s
 * 创建时间：2017年4月6日 下午4:15:41
 * 修改人：x_s
 * 修改时间：2017年4月6日 下午4:15:41
 * 修改备注：
 * @version v1.0
 */
public class TraceUtils
{
	public static void e( String log )
	{
		e( App.getContext().getPackageName(), log );
	}
	public static void d( String log )
	{
		d( App.getContext().getPackageName(), log );
	}
	/**
	 * 打印e级log
	 * */
	public static void e( String tag, String log )
	{
		if ( App.DEBUG )
//		{
			Log.e( tag, log );
//		}
	}
	/**
	 * 打印d级log
	 * */
	public static void d( String tag, String log )
	{
		if ( App.DEBUG )
//		{
			Log.d( tag, log );
//		}
	}
}
