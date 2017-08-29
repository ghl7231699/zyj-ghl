package com.nciebt.zyj.data.module;

import com.nciebt.zyj.data.DataCenter;
import com.nciebt.zyj.data.sql.Menu;
import com.nciebt.zyj.utils.TraceUtils;

import java.util.List;

/**
 * 类名称：LoginModel
 * 类描述：(登录获取数据)
 * 创建人：xs
 * 创建时间：2017/5/4 15:17
 * 修改人：xs
 * 修改时间：2017/5/4 15:17
 *
 * @version v1.0
 */
public class LoginModel {

    private HttpCallBackListener listener;

    private  int loginModelId ;



    public LoginModel(HttpCallBackListener listener, int loginModelId) {
        this.listener = listener;
        this.loginModelId = loginModelId;
    }


    public interface HttpCallBackListener {
        void CallbackSuccess(int loginModelId);
        void CallbackError(int loginModelId);
    }


    /**
     * 作者 xs
     * 方法名称：parseMenuArr
     * 描述：TODO (解析菜单)
     * 创建时间：2017/5/4 15:20
     */
    public void parseMenuArr(List<Menu> list, boolean isUpdate) {
        try {

//            JsonArray menuArr = gsonObj.getAsJsonArray("menu");
            if(isUpdate){
                DataCenter.getInstance().getmMenuNodeManager().clear();
                for (int i = 0;i<list.size();i++){
                    list.get(i).setJurisdictionId(DataCenter.getInstance().getAuthId());
                }
                DataCenter.getInstance().getDetailedListModule().insertMenu(list,DataCenter.getInstance().getAuthId());
            }
            DataCenter.getInstance().getmMenuNodeManager().setmWorkMenuList(list);
            DataCenter.getInstance().getmMenuNodeManager().handleNodeLevel();
            listener.CallbackSuccess(loginModelId);
        } catch (Exception e) {
            TraceUtils.e("parseMenuArr error");
        }
    }

    public HttpCallBackListener getListener() {
        return listener;
    }

    public int getLoginModelId() {
        return loginModelId;
    }
}
