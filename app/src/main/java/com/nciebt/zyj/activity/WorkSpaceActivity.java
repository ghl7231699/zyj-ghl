package com.nciebt.zyj.activity;

import android.R.anim;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nciebt.zyj.App;
import com.nciebt.zyj.R;
import com.nciebt.zyj.activity.base.BaseFragmentActivity;
import com.nciebt.zyj.adapter.WorkSpaceMenuAdapter;
import com.nciebt.zyj.data.DataCenter;
import com.nciebt.zyj.data.DataRepository;
import com.nciebt.zyj.data.module.LoginModel;
import com.nciebt.zyj.data.module.LoginModel.HttpCallBackListener;
import com.nciebt.zyj.data.module.SoftUpdateModule;
import com.nciebt.zyj.data.module.SoftUpdateModule.OnUpdateListener;
import com.nciebt.zyj.data.sql.DetailedListModule;
import com.nciebt.zyj.data.sql.Menu;
import com.nciebt.zyj.data.sql.MenuTable;
import com.nciebt.zyj.entity.VersionParam;
import com.nciebt.zyj.fragment.base.BaseFragment;
import com.nciebt.zyj.fragment.base.BaseMenuFragment;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.NetworkUtils;
import com.nciebt.zyj.utils.SrxUtil;
import com.nciebt.zyj.utils.ToastUtils;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类名称：WorkSpaceActivity
 * 类描述：TODO(界面承载Activity)
 * 创建人：xs
 * 创建时间：2017/4/14 10:57
 * 修改人：xs
 * 修改时间：2017/4/14 10:57
 *
 * @version v1.0
 */
public class WorkSpaceActivity extends BaseFragmentActivity implements HttpCallBackListener, OnUpdateListener {

    @BindView(R.id.ebt_work_space_menu_lv)
    ListView mEbtWorkSpaceMenuLv;


    private boolean initFragment = false;

    private final int loginID = 0x00001;

    /**
     * 是否有缓存数据
     */
    private boolean isHave;

    /**
     * 一级菜单adapter
     */
    private WorkSpaceMenuAdapter mAdapter;

    /**
     * 放置着一级菜单的实体
     */
    private List<MenuTable> mWorkspaceRootMenuNodeList;

    /**
     * 当前菜单
     */
    private String currentMenu;

    /**
     * 内网外网 1 表示是外网 2 表示是内网
     */
    private int mNetType;

    String[] menuName = {"常用", "产品", "其他"};
    /**
     * 记录当前ID，判断点击的是否是当前页面
     */
    protected String mIid = "";
    /**
     * 所有子fragment的引用
     */
    protected BaseFragment mFragment;


    private LoginModel LoginModel;

    /**
     * 是否重新登录
     */
    private boolean mIsReLogin = true;

    /***
     *数据库操作实现类
     */
    private DetailedListModule detailedListModule;


    /**
     * 当前要展示的菜单id
     */
    private String mCurrentShouldShowMenuId;

    /**
     * (覆盖方法的注释)
     *
     * @see BaseFragmentActivity#setContentView()
     */
    @Override
    public void setContentView() {
        setContentView(R.layout.ac_work_space);
        overridePendingTransition(anim.fade_in, anim.fade_out);

        mEbtWorkSpaceMenuLv = (ListView) findViewById(R.id.ebt_work_space_menu_lv);
    }

    private void setUserData() {
        Intent intent = getIntent();
        String AGENT_GRADE = intent.getStringExtra("agentCode");
        String BRANCHTYPE = intent.getStringExtra("branchtype");
        String ORGANIZATION_ID = intent.getStringExtra("comcode");
        // 内网还是外网 1 表示电信 2 表示是内网 3 联通
        mNetType = intent.getIntExtra("netType", -1);
        mNetType = 1;
        AGENT_GRADE = "17205261";
        BRANCHTYPE = "1";
        ORGANIZATION_ID = "86260000";


        if (TextUtils.isEmpty(AGENT_GRADE) || TextUtils.isEmpty(BRANCHTYPE) || TextUtils.isEmpty(ORGANIZATION_ID)) {
            DialogUtils.showPleaseWaitOneBtnDialog("提示", "业务员的个人信息不全，无法登录", "确定", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogUtils.dismissProgressDialog();
                    App.getContext().exit();
                }
            });
        }

        detailedListModule = DataCenter.getInstance().getDetailedListModule();
        String AuthId = detailedListModule.addJurisdiction(ORGANIZATION_ID, BRANCHTYPE, AGENT_GRADE);

        if (mNetType == 2) {
            App.setNetType(true, this);
        } else {
            App.setNetType(false, this);
        }

        App.netType = mNetType;

        DataCenter.getInstance().setAuthId(AuthId);
        DataCenter.getInstance().setORGANIZATION_ID(ORGANIZATION_ID);
        DataCenter.getInstance().setAGENT_GRADE(AGENT_GRADE);
        DataCenter.getInstance().setBRANCHTYPE(BRANCHTYPE);
    }

    /**
     * (覆盖方法的注释)
     *
     * @see BaseFragmentActivity#obtainData()
     */
    @Override
    public void obtainData() {
//        mWorkspaceRootMenuNodeList = DataCenter.getInstance()
        setUserData();
        updateApk();
        //判断更新
    }

    /**
     * 判断是否需要更新APK，如果是则更新，否则直接登录
     */
    private void updateApk() {
        if (!mIsReLogin) {
            DialogUtils.showPleaseWaitDialog("提示", "正在处理，请稍候...", false);
        } else {
            DialogUtils.showPleaseWaitDialog("提示", "获取版本更新...", false);
        }
        if (mIsReLogin) {
            SoftUpdateModule softUpdateModule = new SoftUpdateModule(this);
            DataRepository.getHttpVersions(new VersionParam(), softUpdateModule);
        } else {
            login();
        }
    }

    /**
     * 作者 xs
     * 方法名称：login
     * 描述：TODO (登录)
     * 创建时间：2017/5/4 14:45
     */
    private void login() {
        boolean networkAvailable = NetworkUtils.isNetworkAvailable(this);
        List<Menu> list = detailedListModule.queryMenu(DataCenter.getInstance().getAuthId());
        if (!networkAvailable && (null == list || list.size() == 0)) {

            DialogUtils.showPleaseWaitOneBtnDialog("提示", "请在有网络的情况下进行首次的登录!", "确定", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogUtils.dismissProgressDialog();
                    App.getContext().exit();
                }
            });
        }

        if (list != null && list.size() != 0)
            isHave = true;
        LoginModel = new LoginModel(this, loginID);
        if (isHave) {
            //加载本地数据
            LoginModel.parseMenuArr(list, false);
            new Thread() {
                public void run() {
                    Message message = Message.obtain();
                    message.what = 0x123;
                    mHandler.sendMessage(message);
                }
            }.start();
            // 从本地读取上次请求过的数据、如果上次请求数据在有效期之内。直接读取上次请求数据。
            // 如果上次请求时间失效、则重新登录、请求
            // 如果不是从壳程序跳转过来的。则直接从本地读取数据
            thread.start();
        } else {
            //获取网络数据
            DialogUtils.showPleaseWaitDialog("提示", "首次登录，正在获取网络数据！", false);
            DataRepository.MergeData(DataRepository.get(), LoginModel);
        }

    }

    private Thread thread = new Thread() {

        public void run() {
            Message message = Message.obtain();
            message.what = 0x456;
            mHandler.sendMessage(message);
        }
    };
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 0x111:
                    setFragment(mWorkspaceRootMenuNodeList.get(0));
                    break;
                case 0x123:
                    delayInit(1);
                    break;
                case 0x456:
                    //TODO(请求登录网络数据)
                    DataRepository.MergeData(DataRepository.get(), LoginModel);
                    break;
                case 0x885566:
                    String id = (String) msg.obj;
                    if (TextUtils.isEmpty(id)) {
                        return;
                    }
                    showFragment(id);
                    break;
                default:
                    break;
            }
        }

    };

    private void setMenu() {

        mWorkspaceRootMenuNodeList = DataCenter.getInstance()
                .getmMenuNodeManager().getmWorkspaceRootMenuList();

//        setmWorkspaceRootMenuNodeList(mWorkspaceRootMenuNodeList);
        mAdapter = new WorkSpaceMenuAdapter(this, mWorkspaceRootMenuNodeList);
        setFragment(mWorkspaceRootMenuNodeList.get(0));
        mEbtWorkSpaceMenuLv.setAdapter(mAdapter);
        mEbtWorkSpaceMenuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                MenuTable menu = mWorkspaceRootMenuNodeList.get(position);
                DataCenter.getInstance().setMainName(menu.getAuthName());
                BaseFragment fragment = setFragment(menu);
                if (null != fragment) {
                    mAdapter.setPosition(position);
                }
            }
        });
    }

    /**
     * 延迟加载界面
     */
    public void delayInit(int index) {

        if (DataCenter.getInstance().getmMenuNodeManager()
                .getmWorkspaceRootMenuList().size() < 1) {
            DialogUtils.showPleaseWaitOneBtnDialog("提示", "未获取到登录人数据！请尝试重新登录或者重新启动本程序!", "确定", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DialogUtils.dismissProgressDialog();
                    App.getContext().exit();
                }
            });
            return;
        }
        if (isHave) {
            if (mAdapter != null) {
                boolean isDelete = true;
                for (MenuTable menuTable : mWorkspaceRootMenuNodeList) {
                    if (menuTable.getAuthId() == currentMenu) {
                        isDelete = false;
                    }
                }
                if (isDelete) {
                    setMenu();
                } else {
                    mAdapter.notifyDataSetChanged();
                }

            } else
                setMenu();
        } else {

            setMenu();
        }

        MenuTable menu = DataCenter.getInstance().getmMenuNodeManager()
                .getMenuNodeById(mCurrentShouldShowMenuId);

        if (null != menu && !TextUtils.isEmpty(mCurrentShouldShowMenuId)) {
            Message message = Message.obtain();
            message.what = 0x885566;
            message.arg2 = 1;
            message.obj = menu.getAuthId();
            mHandler.sendMessage(message);

        } else if (null != mWorkspaceRootMenuNodeList
                && !mWorkspaceRootMenuNodeList.isEmpty()) {
            if (!initFragment) {
                if (isHave) {
                    if (index == 1) {
                        setThread();
                    }
                } else {
                    setThread();
                }

            }

        }

        /**
         * 提示框
         * */
//        DialogUtils.dismissProgressDialog();

    }

    private void setThread() {
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                message.what = 0x111;
                mHandler.sendMessage(message);
            }

        }.start();
    }

    /**
     * 作者 xs
     * 方法名称：showFragment
     * 描述：TODO (根据一个菜单id 直接显示这个菜单(跳转到这个菜单))
     * 创建时间：2017/5/5 13:48
     */
    private void showFragment(String tId, Bundle bundle) {

        if (DataCenter.getInstance().getmMenuNodeManager().getMenuNodeById(tId) == null) {
            return;
        }

        String id = noticeCenterToWorkNoticeNode(tId);

        if (DataCenter.getInstance().getmMenuNodeManager().getMenuNodeById(id) == null) {
            id = tId;
        }

        MenuTable entity = DataCenter.getInstance().getmMenuNodeManager()
                .getMenuNodeById(id);

        int lev = entity.getAuthLevel();
        MenuTable rootEntity = entity;

        while (lev > 2) {
            rootEntity = rootEntity.getmParentNode();
            lev = rootEntity.getAuthLevel();
        }

        BaseFragment rootFragment = setFragment(rootEntity);
        int index = DataCenter.getInstance().getmMenuNodeManager()
                .getmWorkspaceRootMenuList().indexOf(rootEntity);
        if (index >= 0) {
            mAdapter.setPosition(index);
        }

        if (null != bundle) {
            rootFragment.setArguments(bundle);
        }

        if (rootEntity != entity) {
            // 这里切换子Fragment
            if (rootFragment instanceof BaseMenuFragment) {
                BaseMenuFragment menuFragment = (BaseMenuFragment) rootFragment;
                menuFragment.showFragment(entity);

            }

        }
    }

    private void showFragment(String id) {
        showFragment(id, null);
    }

    /**
     * 获取菜单节点ID
     */
    private String noticeCenterToWorkNoticeNode(String id) {

        if (DataCenter.getInstance().getmMenuNodeManager().getmAllMenuMap()
                .get(id) == null) {
            return id;
        }

        MenuTable entityNC = DataCenter.getInstance()
                .getmMenuNodeManager().getMenuNodeById(id);
        HashSet<String> keySet = new HashSet<>();
        keySet.addAll(DataCenter.getInstance().getmMenuNodeManager()
                .getmAllMenuMap().keySet());
        for (String string : keySet) {
            MenuTable entityWN = DataCenter.getInstance()
                    .getmMenuNodeManager().getMenuNodeById(string);
            if (entityWN.getUrl().equals(entityNC.getUrl())
                    && !entityWN.equals(entityNC)) {

                return entityWN.getAuthId();
            }
        }

        return null;
    }

    /**
     * (覆盖方法的注释)
     *
     * @see BaseFragmentActivity#refreshUiData()
     */
    @Override
    public void refreshUiData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    /**
     * 构造一个菜单Node
     */
    public MenuTable getMenuNode(String name, String fragmentName) {
        MenuTable child = new MenuTable();
        child.setAuthId(UUID.randomUUID().toString());
//        child.setAuthType(1);
        Resources res = App.getContext().getResources();
        child.setAuthName(name);
        child.setUrl(fragmentName);
        return child;
    }

    public void setmWorkspaceRootMenuNodeList(List<MenuTable> mWorkspaceRootMenuNodeList) {
        for (int i = 0; i < menuName.length; i++) {
            MenuTable menuNodeEntity = getMenuNode(menuName[i], "ProductFragment");
            mWorkspaceRootMenuNodeList.add(menuNodeEntity);
        }
    }


    /**
     * 根据ID创建一个Fragment
     */
    public BaseFragment setFragment(MenuTable menu) {
        // 判断点击需要创建的Fragment是否是当前Fragment
        // 是就返回。不重新添加当前fragment
        if (null == menu || menu.getAuthId().equals(mIid))
            return null;
        mIid = menu.getAuthId();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        if (mFragment != null) {
            fragmentTransaction.remove(mFragment);
            mFragment = null;
        }
        mFragment = (BaseFragment) App.getContext().getFragment(menu.getUrl());
        if (mFragment != null) {
            mFragment.setmGroupID(menu.getAuthId());
            fragmentTransaction.replace(R.id.workspace_fragment_content,
                    mFragment);
//            fragmentTransaction.commit();
            fragmentTransaction.commitAllowingStateLoss();
            int index = DataCenter.getInstance().getmMenuNodeManager()
                    .getmWorkspaceRootMenuList().indexOf(menu);
            if (index >= 0) {
                mAdapter.setPosition(index);
                currentMenu = menu.getAuthId();
            }

        } else {
            ToastUtils.toast(SrxUtil
                    .getStringById(R.string.ebt_string_no_page));
        }
        initFragment = true;
        return mFragment;
    }


    @Override
    public void CallbackSuccess(int loginModelId) {
        DialogUtils.dismissProgressDialog();
        switch (loginModelId) {
            case loginID:
                delayInit(1);
                break;
        }
    }

    @Override
    public void CallbackError(int loginModelId) {

        switch (loginModelId) {
            case loginID:
                if (!isHave) {
                    DialogUtils.showOneBtnDialog("提示", "获取网络数据失败，请检查网络！", "确定", new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DialogUtils.dismissProgressDialog();
                            App.getContext().exit();
                        }
                    });
                } else {
                    ToastUtils.toast("更新产品失败");
                }
                break;
        }
    }

    /**
     * 取消更新就退出程序
     */
    @Override
    public void onCancelUpdate() {
        /**
         * 取消更新就退出程序
         */
        Intent intent = new Intent();
        intent.putExtra("isRefresh", true);
        setResult(1001, intent);
        App.getContext().exit();

    }

    /**
     * 版本检测请求失败就退出程序
     */
    @Override
    public void onFailure() {
        DialogUtils.showOneBtnDialog("提示", "获取更新数据失败!", "确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogUtils.dismissProgressDialog();
                login();
            }
        });
    }
    /**
     * 取消选择权限
     */
    @Override
    public void onNoPermission() {
        DialogUtils.showOneBtnDialog("提示", "无法更新程序", "确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogUtils.dismissProgressDialog();
                App.getContext().exit();
            }
        });
    }

    /**
     * 没有新版本。就可以直接登录
     */
    @Override
    public void onNoUpdate() {
        login();
    }

    /**
     * 版本检测请求失败就退出程序
     */
    @Override
    public void onResponseFailed() {
        DialogUtils.dismissHProgressDialog();
        showWLYCDiaLog();
    }

    /**
     * 显示Dialog
     **/
    public void showWLYCDiaLog() {

        String msg = "程序下载失败!";

        DialogUtils.showOneBtnDialog("提示", msg, "确认", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogUtils.dismissAlertDialog();
                Intent intent = new Intent();
                intent.putExtra("isRefresh", true);
                setResult(1001, intent);
                App.getContext().exit();
            }
        });
    }


    @OnClick({R.id.change_rbt, R.id.ebt_workspace_close_app_btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.change_rbt:
                App.getContext().exit();
                break;
            case R.id.ebt_workspace_close_app_btn:
                Intent intent = new Intent();
                intent.putExtra("toLogin", true);
                setResult(1000, intent);
                App.getContext().exit();
                break;
            default:
                break;
        }
    }
}
