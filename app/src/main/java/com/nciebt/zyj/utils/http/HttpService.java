package com.nciebt.zyj.utils.http;

import com.nciebt.zyj.entity.BaseEntity;
import com.nciebt.zyj.entity.Data;
import com.nciebt.zyj.entity.VersionEntity;
import com.nciebt.zyj.entity.RequestParam;
import com.nciebt.zyj.entity.VersionParam;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 类名称：HttpService
 * 类描述：网络请求
 * 创建人：ghl
 * 创建时间：2017/4/28 14:52
 * 修改人：ghl
 * 修改时间：2017/4/28 14:52
 *
 * @version v1.0
 */

public interface HttpService {
    //    @GET("user/{id}")
//    Call<User> getUserInfoWithPath(@Path("id") int user_id);
    //获取产品清单
    @POST("isb-upd-adapter-in/isb/isb-upd-adapter-in/selAllMenuContent")
    Observable<BaseEntity<Data>> productDetailedList(@Body RequestParam param);

    //获取版本信息
//    @POST("/isb-upd-adapter-in/isb/isb-upd-adapter-in/getVersionIn")
//    Observable<BaseEntity<Data>> checkUpdate(@Body VersionParam param);
    @POST("/isb-upd-adapter-in/isb/isb-upd-adapter-in/getVersionIn")
    Observable<VersionEntity> checkUpdate(@Body VersionParam param);
}
