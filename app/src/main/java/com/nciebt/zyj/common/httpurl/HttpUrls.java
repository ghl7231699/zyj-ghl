package com.nciebt.zyj.common.httpurl;

import com.nciebt.zyj.App;

/**
 * 类名称：HttpUrls
 * 类描述： Ip地址
 * 创建人：ghl
 * 创建时间：2017/4/6 14:34
 * 修改人：ghl
 * 修改时间：2017/4/6 14:34
 *
 * @version v1.0
 */

public class HttpUrls {

    /********************* 测试环境 *******************/

    /**
     * 测试内网地址
     */
//    public static final String WEB_SERVER_REQUEST_INTERNET_DEBUG_IP = "http://10.1.92.203:8081/";

    /**
     * 测试开发地址
     */
    public static final String WEB_SERVER_REQUEST_INTERNET_DEBUG_IP = "http://10.1.92.243:8081/";
    /**
     * 测试回归地址
     */
//    public static final String WEB_SERVER_REQUEST_INTERNET_DEBUG_IP = "http://192.168.180.81/";


    /********************* 测试环境 *******************/


    /********************* 生产环境 *******************/
    // 接口请求地址 生产 内网IP
    public static final String WEB_SERVER_REQUEST_INTERNET_RELASE_IP = "https://192.168.190.38/";


    /********************* 外网 电信 *******************/
    // 接口请求地址 生产 外网IP 电信
    public static final String WEB_SERVER_REQUEST_OUTERNET_DX_RELASE_IP = "https://123.127.246.11/";

    /********************* 外网 电信 *******************/


    /********************* 外网 联通*******************/
    // 接口请求地址 生产 外网IP 联通
    public static final String WEB_SERVER_REQUEST_OUTERNET_LT_RELASE_IP = "https://219.142.99.52/";
    /********************* 外网 联通*******************/

    /********************* 生产环境 *******************/

    public static final String URL_LOGIN_IP = App.DEBUG ? WEB_SERVER_REQUEST_INTERNET_DEBUG_IP : App.netType == 2 ? WEB_SERVER_REQUEST_INTERNET_RELASE_IP : App.netType == 1 ? WEB_SERVER_REQUEST_OUTERNET_DX_RELASE_IP : WEB_SERVER_REQUEST_OUTERNET_LT_RELASE_IP;
    //模拟测试地址
    public static final String urls = "http://vpan.bj189.cn/xhrs/EBT2/NCI_EBT2.0_ZY_5.0.apk";

}
