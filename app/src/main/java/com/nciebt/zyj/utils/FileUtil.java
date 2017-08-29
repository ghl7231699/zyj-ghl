package com.nciebt.zyj.utils;

import com.nciebt.zyj.App;

import java.io.File;

/**
 * 类名称：FileUtil
 * 类描述：文件处理工具类
 * 创建人：ghl
 * 创建时间：2017/4/9 14:54
 * 修改人：ghl
 * 修改时间：2017/4/9 14:54
 *
 * @version v1.0
 */
public class FileUtil {
    /**
     * 新建文件夹
     *
     * @param s 文件名
     */
    public static String create(String s) {
        File file = new File(s);
        if (!file.exists()) {
            file.mkdirs();
        }
        return App.getLocalDataPath() + s;
    }

    public static File createFile(String s, String name) {
        File file = new File(s, name);
        return file;
    }

    /**
     * 文件是否存在
     *
     * @param s
     * @return
     */
    public static boolean fileExits(String s) {
        try {
            File f = new File(s);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
