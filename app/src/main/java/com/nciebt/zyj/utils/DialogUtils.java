package com.nciebt.zyj.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager.LayoutParams;

import com.nciebt.zyj.App;

/**
 * 类名称：DialogUtils
 * 类描述：提示框工具类
 * 创建人：ghl
 * 创建时间：2017/5/5 9:44
 * 修改人：ghl
 * 修改时间：2017/5/5 9:44
 *
 * @version v1.0
 */

public class DialogUtils {


    /**
     * 显示 一个按钮的dialog
     */
    public static void showOneBtnDialog( String title, String msg, String btnTxt, OnClickListener listener )
    {
        App.getContext().dismissAlertDialog();
        AlertDialog dialog = App.getContext().getCreateAlertDialog();
        if ( dialog == null )
            return;
        dialog.setTitle( title );
        dialog.setMessage( msg );
        dialog.setButton( DialogInterface.BUTTON_POSITIVE, btnTxt, listener );

        dialog.show();
    }

    /**
     * 显示两个按钮的dialog
     */
    public static synchronized void showTwoBtnDialog( String title, String msg, String btn1Txt, String btn2Txt, OnClickListener btn1Listener, OnClickListener btn2Listener )
    {
        AlertDialog dialog = App.getContext().getCreateAlertDialog();
        if ( dialog == null )
            return;
        dialog.setTitle( title );
        dialog.setMessage( msg );
        dialog.setButton( DialogInterface.BUTTON_NEGATIVE, btn1Txt, btn1Listener );
        dialog.setButton( DialogInterface.
                BUTTON_POSITIVE, btn2Txt, btn2Listener );
        dialog.show();
    }
    /**
     * 关闭AlertDialog
     * */
    public static void dismissAlertDialog()
    {
        App.getContext().dismissAlertDialog();
    }

    /**
     * 显示 请稍等 dialog
     */
    public static synchronized void showPleaseWaitDialog()
    {
        ProgressDialog dialog = App.getContext().getCreateProgressDialog();

        if ( dialog == null )
            return;
        dialog.setCancelable( true );
        dialog.setTitle( "请稍候..." );
        dialog.setMessage( "正在处理，请稍候..." );
        dialog.show();
    }

    /**
     * 可输入内容的 dialog
     */
    public static synchronized ProgressDialog showPleaseWaitDialog( String title, String msg, boolean Cancelable )
    {

        ProgressDialog dialog = App.getContext().getCreateProgressDialog();
        if ( dialog == null )
            return null;
        dialog.setCancelable( Cancelable );
        dialog.setTitle( title );
        dialog.setMessage( msg );
        dialog.show();
        return dialog;
    }

    /**
     * 显示有一个按钮的等待进度条dialog
     */
    public static synchronized void showPleaseWaitOneBtnDialog( String title, String msg, String btnText, OnClickListener onClickListener )
    {
        ProgressDialog dialog = App.getContext().getCreateProgressDialog();
        if ( dialog == null )
            return;
        dialog.setTitle( title );
        dialog.setCancelable( false );
        dialog.setMessage( msg );
        dialog.setButton( DialogInterface.BUTTON_POSITIVE, btnText, onClickListener );
        dialog.show();
    }

    /**
     * 有进度的 progress dialog
     * */
    public static synchronized void showProgressDialog()
    {
        ProgressDialog progressDialog = App.getContext().getCreateProgressDialog();
        if ( progressDialog == null )
            return;
        progressDialog.setCancelable( false );
        progressDialog.setTitle( "请稍候..." );
        progressDialog.setMessage( "正在处理，请稍候..." );
        progressDialog.setProgressStyle( ProgressDialog.STYLE_HORIZONTAL );
        progressDialog.show();
    }
    /**
     * 关闭progressDialog
     * */
    public static void dismissProgressDialog()
    {
        App.getContext().dismissProgressDialog();
    }

    /**
     * 有下载进度的 progress dialog
     */
    public static synchronized void showHProgressDialog()
    {
        showHProgressDialog( "提示", "正在下载程序最新版本" );
    }

    /**
     * 有下载进度的 progress dialog
     */
    public static synchronized void showHProgressDialog( String title, String msg )
    {
        ProgressDialog dialog = App.getContext().getCreateHProgressDialog();
        if(dialog == null)
            return;
        dialog.setTitle( title );
        dialog.setMessage( msg );
        dialog.show();
    }

    /**
     * 隐藏有进度条的dialog
     * */
    public static void dismissHProgressDialog()
    {
        try
        {
            App.getContext().dismissHProgressDialog();
        }
        catch ( Exception e )
        {
        }
    }

    /**
     * 设置当前进度条位置
     */
    public static void setProgress( int value )
    {
        App.getContext().setProgress( value );
    }

    /**
     * 设置当前进度条位置
     */
    public static void setMax( int value )
    {
        App.getContext().setMax( value );
    }

}
