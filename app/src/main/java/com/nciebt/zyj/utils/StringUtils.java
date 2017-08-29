package com.nciebt.zyj.utils;

/**
 * 类名称：StringUtils
 * 类描述：字符截取工具
 * 创建人：ghl
 * 创建时间：2017/5/9 10:56
 * 修改人：ghl
 * 修改时间：2017/5/9 10:56
 *
 * @version v1.0
 */

public class StringUtils {
    /**
     * 通过下载地址获取保存文件的名称
     *
     * @param s
     * @return
     */
    public static String splitFileName(String s,String format) {
        String[] split = s.split(format);
        String name = null;
        try {
            name = split[split.length - 1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
