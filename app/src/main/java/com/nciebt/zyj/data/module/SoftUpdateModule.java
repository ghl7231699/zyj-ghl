package com.nciebt.zyj.data.module;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.nciebt.zyj.App;
import com.nciebt.zyj.common.config.RemindMessage;
import com.nciebt.zyj.data.DataCenter;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.entity.VersionEntity;
import com.nciebt.zyj.inter.FinalInter;
import com.nciebt.zyj.inter.PermissonListener;
import com.nciebt.zyj.inter.UpdateProductListener;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.PermissionUtil;
import com.nciebt.zyj.utils.ToastUtils;
import com.nciebt.zyj.utils.TraceUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * 类名称：SoftUpdateModule
 * 类描述：(更新APK的处理)
 * 创建人：xs
 * 创建时间：2017/5/26 11:30
 * 修改人：xs
 * 修改时间：2017/5/26 11:30
 * @version v1.0
 */
public class SoftUpdateModule implements FinalInter{

    public SoftUpdateModule(OnUpdateListener listener) {
        this.listener = listener;
    }
    private OnUpdateListener listener;

    public OnUpdateListener getListener() {
        return listener;
    }

    /**
     * 更新回调接口
     **/
    public interface OnUpdateListener {
        void onCancelUpdate();

        void onFailure();

        void onNoUpdate();

        void onNoPermission();

        void onResponseFailed();
    }


    public  void parseUpdate(VersionEntity.DataBean.GetSoftVersionInfoBean versionEntity){
        int localVersion = App.getContext().getmVersionNumber();
        int version = versionEntity.getVersionnumber();
        Boolean isInerNet=App.getNetType();
        String downLoadUpdate;
        if ( isInerNet )
        {
            downLoadUpdate=versionEntity.getNwxzdz();
        }else {
            downLoadUpdate=versionEntity.getWwxzdz();
        }
        DataCenter.getInstance().setmUpdatePackageUrl(downLoadUpdate);
        if( version > localVersion && !TextUtils.isEmpty( downLoadUpdate ) )
        {
            //需要升级
            ToastUtils.toast( "有新版本,准备更新" );
            DialogUtils.showTwoBtnDialog( "提示", "发现程序新版本，是否更新！", "取消", "确定",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which )
                        {
                            dialog.dismiss();
                            ToastUtils.toast( "用户取消了更新" );
                            if ( null != listener )
                            {
                                listener.onCancelUpdate();
                            }
                        }
                    }, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick( DialogInterface dialog, int which )
                        {
                            dialog.dismiss();
                            PermissionUtil.requestPermission(App.getContext().getCurrentActivityContext(), new PermissonListener() {
                                @Override
                                public void onGranted() {
                                    //用户同意授权
                                    downLoadDetail( DataCenter.getInstance().getmUpdatePackageUrl());
                                }

                                @Override
                                public void onDenied() {
                                    //用户不同意授权，但没有选中不在询问
                                    listener.onNoPermission();
                                }
                            }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                    } );
        }
        else
        {
            //不需要升级
            ToastUtils.toast( "当前是最新版本" );
            if ( null != listener )
            {
                listener.onNoUpdate();
            }
        }

    }

    private void downLoadDetail(String ApkUrl){
        DialogUtils.showHProgressDialog();
        DialogUtils.setProgress( 0 );
        deleteAPK(UPDATE_FILE_PATH);
        RxDownload.getInstance(App.getContext().getCurrentActivityContext())
                .defaultSavePath(App.getLocalDataPath())
                .download(ApkUrl,"update_pkg.apk")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(@NonNull DownloadStatus downloadStatus) throws Exception {

                        int TotalSize = (int)downloadStatus.getTotalSize();
                        int DownloadSize = (int)downloadStatus.getDownloadSize();
                        DialogUtils.setProgress(DownloadSize);
                        DialogUtils.setMax( TotalSize);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //下载失败,请检查您的网络

                        if ( null != listener )
                        {
                            listener.onResponseFailed();
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
//                        DialogUtils.dismissHProgressDialog();
                        String path = UPDATE_FILE_PATH;
                        TraceUtils.e( "download Path:" + path );
                        // ToastUtils.toast(
                        // "文件下载成功:" + path );
                        // 安装新版本apk
                        DialogUtils.dismissHProgressDialog();
                        installApk( path );
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                });
    }

    private void installApk(String path) {
        File apkFile = new File( path );
        if ( !apkFile.exists() )
        {
            return;
        }

        try
        {
            // 通过Intent安装APK文件
            Intent i = new Intent( Intent.ACTION_VIEW );
            i.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            i.setDataAndType( Uri.parse("file://" +  apkFile.toString() ), "application/vnd.android.package-archive" );
            App.getContext().getCurrentActivityContext().startActivity( i );
            App.getContext().exit();

        }
        catch ( Exception e )
        {
            App.getContext().exit();
        }
    }

    private  void deleteAPK(String path){
        File file = new File(path);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }
}
