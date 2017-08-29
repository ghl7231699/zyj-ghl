package com.nciebt.zyj.inter;

import com.nciebt.zyj.App;

/**
 * 类名称：FinalInter
 * 类描述：(常量接口)
 * 创建人：xs
 * 创建时间：2017/5/4 11:15
 * 修改人：xs
 * 修改时间：2017/5/4 11:15
 *
 * @version v1.0
 */
public interface FinalInter {


    /**
     * 登录数据
     */
    String LOCAL_LOCAL_DATA_FILE_NAME = "local_data.o";
    /**
     * 产品SD卡路径
     */
    String PRODUCT_FILE_NAME = App.getLocalDataPath() + "Product/";



    // 显示设置进度 what
    public static final int UPDATE_PROGRESS_WHAT = 0x004000;


    /**
     * 软件更新文件下载路径
     * */
    public static final String 	UPDATE_FILE_PATH = App.getLocalDataPath() + "update_pkg.apk";

}
