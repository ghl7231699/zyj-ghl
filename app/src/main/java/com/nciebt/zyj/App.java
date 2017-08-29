package com.nciebt.zyj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.R.anim;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nciebt.zyj.activity.WorkSpaceActivity;
import com.nciebt.zyj.common.httpurl.HttpUrls;
import com.nciebt.zyj.fragment.base.BaseFragment;
import com.nciebt.zyj.inter.FinalInter;
import com.nciebt.zyj.utils.SrxUtil;
import com.nciebt.zyj.utils.TraceUtils;
import com.nciebt.zyj.utils.http.HttpManager;
import com.nciebt.zyj.utils.http.HttpService;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * 类名称：App
 * 类描述：TODO(用一句话描述该类做什么)
 * 创建人：x_s
 * 创建时间：2017年4月6日 下午3:47:06
 * 修改人：x_s
 * 修改时间：2017年4月6日 下午3:47:06
 * 修改备注：
 *
 * @version v1.0
 */
public class App extends LitePalApplication implements FinalInter {
    private static App mApp;

    // 应用程序是否为调试模式（true测试/false生产）
    public static final boolean DEBUG = true;

    //网络类型，1 表示电信 2 表示是内网 3 联通
    public static int netType = 1;


    // 当前显示的Activity
    private Activity mCurrentActivityContext;

    // Gson
    private Gson mGson;

    // parse
    private JsonParser mJsonParser;

    // 当前需要全屏显示的Fragment
    private BaseFragment mCurrentFragment = null;


    // 当前需要全屏显示的Fragment的 xml布局文件ID
    private int mCurrentFragmentLayoutId = -1;

    public int getmVersionNumber() {
        return mVersionNumber;
    }

    // 版本号
    private int mVersionNumber;
    // 版本名称
    private String mVersionName;

    //加载提示进度条
    private ProgressDialog mProgressDialog = null;
    //对话框
    private AlertDialog mAlertDialog = null;
    //有进度条的Dialog
    private ProgressDialog mHDialog = null;

    //当前登录环境是否是内网
    private static boolean NET_TYPE_IS_INERERNET = true;

    //网络请求
    private HttpService mService;

    /**
     * Asstes管理器
     */
    private AssetManager mAssetManager;
    /**
     * 数据存放在sd卡的路径
     */
    private static String LOCAL_DATA_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "srxzyj" + File.separator + "cache" + File.separator;


    public static final String getLocalDataPath() {
        return LOCAL_DATA_PATH;
    }

    /**
     * 当前网络环境
     **/
    public static boolean getNetType() {
        return NET_TYPE_IS_INERERNET;
    }


    /**
     * 设置当前登录的网络类型
     */
    public static void setNetType(boolean isOuterNet, Object obj) {
        if (obj instanceof WorkSpaceActivity) {
            NET_TYPE_IS_INERERNET = isOuterNet;
        } else {
            throw new IllegalAccessError("只能在WorkspaceActivity中修改登录的网络类型！");
        }
    }

    /**
     * 获得全局 Application Context
     */
    public static App getContext() {
        return App.mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        App.mApp = this;
        TraceUtils.e("app111" + App.mApp);
        init();

    }

    /**
     * @author x_s
     * 方法名称：init
     * 描述：TODO(初始化)
     * 创建时间：2017年4月6日 下午3:53:18
     * void
     */
    private void init() {
        //OkHttpUtils初始化
//        OkHttpUtils.init(this);

        mGson = new Gson();
        mJsonParser = new JsonParser();
        LitePal.initialize(this);
        initHttpService();
//			SQLiteDatabase db = Connector.getDatabase();
//			// 初始化xutils 框架
//			initBitmapUtils();
//			initHttpUtils();
        mVersionName = SrxUtil.getAppVersionName(getApplicationContext())[0];
        mVersionNumber = Integer.valueOf(SrxUtil.getAppVersionName(getApplicationContext())[1]);
    }


    /**
     * 获得当前运行的Activity Context
     */
    public Activity getCurrentActivityContext() {
        return mCurrentActivityContext;
    }

    /**
     * 设置当前运行的Activity context
     */
    public void setCurrentActivityContext(Activity mCurrentActivityContext) {
        this.mCurrentActivityContext = mCurrentActivityContext;
    }


    /**
     * 获取ProgressDialog
     *
     * @return
     */
    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }


    /**
     * 初始化HttpService
     *
     * @return
     */
    public void initHttpService() {
        mService = HttpManager.provideHttpService(HttpUrls.URL_LOGIN_IP);
    }

    /**
     * 获取HttpService
     *
     * @return
     */
    public HttpService getService() {
        return mService;
    }

    /**
     * 设置当前可能需要全屏显示的Fragment
     */
    public void setCurrentFragment(int layoutId, BaseFragment fragment) {
        this.mCurrentFragmentLayoutId = layoutId;
        this.mCurrentFragment = fragment;
    }

    /**
     * 获取当前全屏显示的Fragment 的xml布局文件的id
     */
    public int getCurrentFragmentLayoutId() {
        return mCurrentFragmentLayoutId;
    }


    /**
     * 根据xml布局文件获取当前的全屏的Fragment
     * 如果xml布局文件id和当前全屏的Fragment的xml布局文件id不同。
     * 则返回null
     */
    public BaseFragment getCurrentFragment(int layoutId) {

        if (mCurrentFragmentLayoutId == layoutId) {
            return mCurrentFragment;
        }
        return null;
    }

    /**
     * 根据Fragment名字、创建并返回Fragment
     */
    public Fragment getFragment(String fragmentName) {
        if (TextUtils.isEmpty(fragmentName)) {
            TraceUtils.d("App getFragment fragmentName 不能为空");
            return null;
        }

        try {
            Fragment fragment = (Fragment) Class.forName("com.nciebt.zyj.fragment." + fragmentName).newInstance();
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 初始化菜单
     */
    public void setMenu() {
        mAssetManager = mApp.getAssets();
        try {
            InputStream listFile = mAssetManager.open("");
            byte[] by = new byte[listFile.available()];
            listFile.read(by);
            String data = new String(by, "ISO-8859-1").toString();
            JsonObject mapJson = mJsonParser.parse(data).getAsJsonObject();
            listFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public Gson getmGson() {
        return mGson;
    }

    public JsonParser getmJsonParser() {
        return mJsonParser;
    }


    /**
     * 退出应用程序
     */
    public void exit() {
        if (null != mCurrentActivityContext) {
            mCurrentActivityContext.finish();
            mCurrentActivityContext.overridePendingTransition(anim.fade_in, anim.fade_out);
        }

        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 初始化ProgressDialog
     */
    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(mCurrentActivityContext, android.R.style.Theme_Holo_Light_Dialog);
        mProgressDialog.getWindow().setBackgroundDrawableResource(R.color.alpha);
        mProgressDialog.setTitle("请稍候...");
        mProgressDialog.setMessage("正在处理，请稍候...");
    }

    /**
     * 初始化AlertDialog
     */
    private void initDialog() {
        mAlertDialog = new AlertDialog.Builder(mCurrentActivityContext, android.R.style.Theme_Holo_Light_Dialog).create();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.color.alpha);
        mAlertDialog.setTitle("请稍候...");
        mAlertDialog.setMessage("正在处理，请稍候...");
        mAlertDialog.setCancelable(false);
    }


    /**
     * 得到Dialog 对象
     */
    public AlertDialog getCreateAlertDialog() {
        if (null != mAlertDialog && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
        initDialog();
        return mAlertDialog;
    }


    /**
     * 关闭Dialog
     */
    public void dismissAlertDialog() {
        if (null != mAlertDialog && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    /**
     * 得到 ProgressDialog 对象
     */
    public ProgressDialog getCreateProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        initProgressDialog();
        return mProgressDialog;
    }

    /**
     * 关闭 ProgressDialog
     */
    public void dismissProgressDialog() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 设置Progress 当前进度
     */
    public void setProgress(int value) {
        Message msg = Message.obtain();
        msg.what = UPDATE_PROGRESS_WHAT;
        msg.arg1 = value;
        // mHandler.sendMessage( msg );
        if (null != mHDialog && mHDialog.isShowing()) {
            mHDialog.setProgress(value);
        }
    }


    /**
     * 设置Progress 当前进度
     */
    public void setMax(int value) {
        Message msg = Message.obtain();
        msg.what = UPDATE_PROGRESS_WHAT;
        msg.arg1 = value;
        if (null != mHDialog && mHDialog.isShowing()) {
            mHDialog.setMax(value);
        }
    }


    /**
     * 初始化 有进度ProgressDialog
     **/
    private void initHProgressDialog() {
        mHDialog = getCreateProgressDialog();
        mHDialog.setCancelable(false);
        mHDialog.setTitle("提示");
        mHDialog.setMessage("正在处理...");
        mHDialog.setIndeterminate(false);
//		mHDialog.setMax( 1000 );
        mHDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    /**
     * 创建有进度ProgressDialog
     **/
    public ProgressDialog getCreateHProgressDialog() {
        if (null != mHDialog && mHDialog.isShowing()) {
            mHDialog.dismiss();
            mHDialog = null;
        }
        initHProgressDialog();
        return mHDialog;
    }

    /**
     * 关闭有进度ProgressDialog
     **/
    public void dismissHProgressDialog() {
        if (null != mHDialog && mHDialog.isShowing()) {
            mHDialog.dismiss();
            mHDialog = null;
        }
    }

}
