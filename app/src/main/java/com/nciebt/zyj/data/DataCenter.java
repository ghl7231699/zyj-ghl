package com.nciebt.zyj.data;

import com.nciebt.zyj.common.httpurl.HttpUrls;
import com.nciebt.zyj.data.sql.DetailedListModule;
import com.nciebt.zyj.utils.http.HttpManager;
import com.nciebt.zyj.utils.http.HttpService;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：DataCenter
 * 类描述：TODO(数据中心)
 * 创建人：x_s
 * 创建时间：2017年4月6日 下午4:07:00
 * 修改人：x_s
 * 修改时间：2017年4月6日 下午4:07:00
 * 修改备注：
 *
 * @version v1.0
 */
public class DataCenter {


    private int mPositiion;
    /**
     * 网络请求响应code消息
     */
    private Map<String, String> mResponseCodeMsg = new HashMap<String, String>();

    /**
     * 菜单数据管理器
     */
    private MenuNodeManager mMenuNodeManager;
    /***
     *数据库操作实现类
     */
    private DetailedListModule detailedListModule;

    /**
     * 缓存数据管理器
     */
    private CacheManager mCacheManager;


    // single instance
    private volatile static DataCenter mInstance;

    /**
     * 职级
     */
    private String AGENT_GRADE;

    /**
     * 渠道
     */
    private String BRANCHTYPE;
    /**
     * 机构
     */
    private String ORGANIZATION_ID;

    /**
     * 软件更新地址
     * */
    private String															mUpdatePackageUrl;

    /**
     * 主键ID
     */
    private String AuthId;

    private DataCenter() {
        init();
    }

    private HttpService httpService;
    /**
     * 主菜单名称
     */
    private String mainName = null;

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    /**
     * @author x_s
     * 方法名称：init
     * 描述：TODO(初始化)
     * 创建时间：2017年4月6日 下午4:10:14
     * void
     */
    private void init() {
        mMenuNodeManager = new MenuNodeManager();
        detailedListModule = new DetailedListModule();
        httpService = HttpManager.provideHttpService(HttpUrls.URL_LOGIN_IP);
        mCacheManager = new CacheManager();
    }

    /**
     * 获取单实例对象
     */
    public static DataCenter getInstance() {
        if (null == mInstance) {
            synchronized (DataCenter.class) {
                if (null == mInstance)
                    mInstance = new DataCenter();
            }
        }
        return mInstance;
    }

    public MenuNodeManager getmMenuNodeManager() {
        return mMenuNodeManager;
    }

    public DetailedListModule getDetailedListModule() {
        return detailedListModule;
    }

    public HttpService getHttpService() {
        return httpService;
    }


    public String getAGENT_GRADE() {
        return AGENT_GRADE;
    }

    public void setAGENT_GRADE(String AGENT_GRADE) {
        this.AGENT_GRADE = AGENT_GRADE;
    }

    public String getBRANCHTYPE() {
        return BRANCHTYPE;
    }

    public void setBRANCHTYPE(String BRANCHTYPE) {
        this.BRANCHTYPE = BRANCHTYPE;
    }

    public String getORGANIZATION_ID() {
        return ORGANIZATION_ID;
    }

    public void setORGANIZATION_ID(String ORGANIZATION_ID) {
        this.ORGANIZATION_ID = ORGANIZATION_ID;
    }

    public String getAuthId() {
        return AuthId;
    }

    public void setAuthId(String authId) {
        AuthId = authId;
    }

    public CacheManager getmCacheManager() {
        return mCacheManager;
    }

    public String getmUpdatePackageUrl() {
        return mUpdatePackageUrl;
    }

    public void setmUpdatePackageUrl(String mUpdatePackageUrl) {
        this.mUpdatePackageUrl = mUpdatePackageUrl;
    }
}
