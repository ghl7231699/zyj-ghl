package com.nciebt.zyj.data.module;

import android.util.Log;

import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.inter.FinalInter;
import com.nciebt.zyj.inter.UpdateProductListener;
import com.nciebt.zyj.utils.ToastUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static android.content.ContentValues.TAG;

/**
 * 类名称：DetailModule
 * 类描述：详情内容module
 * 创建人：ghl
 * 创建时间：2017/4/24 13:58
 * 修改人：ghl
 * 修改时间：2017/4/24 13:58
 *
 * @version v1.0
 */

public class DetailModule implements FinalInter {
    private UpdateProductListener mListener;
    private String path;

    public DetailModule(String path, UpdateProductListener listener) {
        this.path = path;
        mListener = listener;
    }

    /**
     * 查询本地是否存在数据，否则下载
     */
    public void loadDetailData() {
        DataRepository.readAllRecords(FileDetails.class, path)
                .subscribe(new Consumer<List<FileDetails>>() {
                    @Override
                    public void accept(@NonNull List<FileDetails> fileDetailses) throws Exception {
                        for (FileDetails f : fileDetailses
                                ) {
                            String s = "http://vpan.bj189.cn/xhrs/EBT2/NCI_EBT2.0_ZY_5.0.apk";
                            String substring = s.substring(s.lastIndexOf("."));
//                            String path = PRODUCT_FILE_NAME + f.getContentId() + substring;
                            String path = PRODUCT_FILE_NAME + f.getContentId() + ".mp4";
                            String savePath = f.getSavePath();
                            Log.d(TAG, "detailModule: " + path);
//                            if (FileUtil.fileExits(path)) {
                                mListener.noDownLoading(path);
//                            } else {
//                                //下载
//                                mListener.DownLoading();
//                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtils.toast(throwable.getMessage());
                        mListener.loadFailed();
                    }
                });
    }
}
