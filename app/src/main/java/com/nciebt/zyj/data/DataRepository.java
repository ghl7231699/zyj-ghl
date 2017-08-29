package com.nciebt.zyj.data;

import android.util.Log;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.nciebt.zyj.common.rx.RxBus;
import com.nciebt.zyj.common.rx.RxHttpTransFormer;
import com.nciebt.zyj.data.module.LoginModel;
import com.nciebt.zyj.data.module.SoftUpdateModule;
import com.nciebt.zyj.data.sql.CommonFileDetailed;
import com.nciebt.zyj.data.sql.Menu;
import com.nciebt.zyj.entity.Data;
import com.nciebt.zyj.entity.FileDetails;
import com.nciebt.zyj.entity.Header;
import com.nciebt.zyj.entity.ProductListParam;
import com.nciebt.zyj.entity.RequestParam;
import com.nciebt.zyj.entity.VersionEntity;
import com.nciebt.zyj.entity.VersionParam;
import com.nciebt.zyj.inter.UpdateProductListener;
import com.nciebt.zyj.utils.DialogUtils;
import com.nciebt.zyj.utils.ToastUtils;

import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;
import static org.litepal.crud.DataSupport.where;

/**
 * 类名称：DataRepository
 * 类描述：数据处理类
 * 创建人：ghl
 * 创建时间：2017/5/4 14:34
 * 修改人：ghl
 * 修改时间：2017/5/4 14:34
 *
 * @version v1.0
 */

public class DataRepository {

    /**
     * * 检索本地数据
     *
     * @param id        fragment 类型
     * @param mListener 是否有更新
     */
    public static void LoadAllList(Class<? extends DataSupport> c, String s, String id, UpdateProductListener mListener) {
        mListener.obtainProductList(readAllRecords(c, s, id));
    }

    /**
     * 整合本地与网络数据
     *
     * @param p
     */
    public static void MergeData(RequestParam p, final LoginModel model) {
        DataCenter.getInstance().getHttpService().productDetailedList(p)
                .compose(RxHttpTransFormer.<Data>handleResult())
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(@NonNull Data data) throws Exception {
                        //同本地数据比较，检测是否有更新
                        List<FileDetails> product = data.getProduct();
                        List<FileDetails> f = getClusterQuery().find(FileDetails.class);
                        distinctAndMerge(product, f);
                        List<Menu> menu = data.getMenu();
                        model.parseMenuArr(menu, true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        model.getListener().CallbackError(model.getLoginModelId());
                    }
                });
    }

    /**
     * 比对本地数据与网络数据
     *
     * @param list 网络数据
     * @param file 本地数据
     */
    private static void distinctAndMerge(final List<FileDetails> list, final List<FileDetails> file) {
        Observable.just(list)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<FileDetails>>() {
                    @Override
                    public void accept(@NonNull List<FileDetails> fileDetailses) throws Exception {
                        //排序
                        Collections.sort(list, new Comparator<FileDetails>() {
                            @Override
                            public int compare(FileDetails o1, FileDetails o2) {
                                int sort1 = Integer.valueOf(o1.getSort());
                                int sort2 = Integer.valueOf(o2.getSort());
                                return sort1 - sort2;
                            }
                        });
                        for (FileDetails f : list
                                ) {
                            Log.d(TAG, "排序后: " + f.getTitle() + "            " + f.getSort());
                        }
                        //删除本地数据库
                        if (list.size() != file.size()) {
                            Log.d(TAG, "distinctAndMerge: delete local db");
                            String authId = DataCenter.getInstance().getAuthId();
                            if (file.size() > 0) {
//                                for (FileDetails net : list
//                                        ) {
//                                    dataCompareToNet(net, list);
//                                }
                                DataSupport.deleteAll(FileDetails.class, "PERMISSION is " + "'" + authId + "'");
                            }
                            //更新数据库，插入服务端数据
                            for (FileDetails netData : list
                                    ) {
                                netData.setPermission(authId);
                                netData.save();
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }

    /**
     * 比较清单是否有更新，同步本地常用数据库表
     *
     * @param fs
     * @param fileList 本地数据
     */
    private static void dataCompareToNet(FileDetails fs, List<FileDetails> fileList) {
        List<CommonFileDetailed> commonFileDetailed = getClusterQuery("FLAG = ?", String.valueOf(1))
                .find(CommonFileDetailed.class);
        for (CommonFileDetailed c : commonFileDetailed
                ) {
            //本地常用数据包含服务端不存在的数据时，删除本地数据
            List<FileDetails> list = getClusterQuery("CONTENTID = ?", fs.getContentId())
                    .find(FileDetails.class);
            if (list.size() < 0) {
                Log.d(TAG, "dataCompareToNet: 删除" + list.get(0).getTitle());
                c.delete();
//                RxBus.getInstance().post();
            }
        }
    }

    /**
     * 本地获取所有产品列表
     *
     * @return
     */
    public static Observable<List<FileDetails>> readAllRecords() {
        return Observable.create(new ObservableOnSubscribe<List<FileDetails>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FileDetails>> e) throws Exception {
                List<FileDetails> value = DataSupport.where("PERMISSION = ?", DataCenter.getInstance().getAuthId())
                        .find(FileDetails.class);
                e.onNext(value);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据类型查询产品列表
     *
     * @param query 查询sql
     * @param s     查询条件
     * @param clazz 表实体类
     * @param <T>
     * @return
     */
    public static <T extends DataSupport> Observable<List<T>> readAllRecords(final Class<T> clazz, final String query, final String s) {
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> e) throws Exception {
                List<T> value = getClusterQuery(query, s)
                        .find(clazz);
                e.onNext(value);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 网络搜索功能&&本地搜索功能
     *
     * @param editText
     * @return
     */
    public static Observable<List<FileDetails>> searchNetWorkData(final EditText editText) {
        return RxTextView
                .textChanges(editText)
                //延迟执行搜索操作
                .debounce(500, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .switchMap(new Function<CharSequence, ObservableSource<List<FileDetails>>>() {
                    @Override
                    public ObservableSource<List<FileDetails>> apply(@NonNull CharSequence charSequence) throws Exception {
                        //获取数据
                        List<FileDetails> fileDetailses = getClusterQuery("TITLE like ?", "%" + editText.getText().toString().trim() + "%")
                                .find(FileDetails.class);
                        return Observable.just(fileDetailses);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 读取本地常用数据
     *
     * @return
     */
    public static Observable<List<FileDetails>> readCommonListRecords() {
        return Observable.create(new ObservableOnSubscribe<List<FileDetails>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FileDetails>> e) throws Exception {
                List<CommonFileDetailed> CommonFileDetailed = getClusterQuery("FLAG = ?", String.valueOf(1))
                        .find(CommonFileDetailed.class);
                List<FileDetails> mFileDetailses = new ArrayList<>();
                for (CommonFileDetailed f : CommonFileDetailed
                        ) {
                    List<FileDetails> list = getClusterQuery("CONTENTID = ?", f.getContentId())
                            .find(FileDetails.class);
                    for (FileDetails fs : list
                            ) {
                        mFileDetailses.add(fs);
                    }
                }
                e.onNext(mFileDetailses);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据类型查询产品列表
     *
     * @param tClass 表实体类
     * @param s      查询条件
     * @param <T>    继承DataSupport泛型
     * @return
     */
    public static <T extends DataSupport> Observable<List<T>> readAllRecords(final Class<T> tClass, final String s) {
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(ObservableEmitter<List<T>> e) throws Exception {
                ClusterQuery clusterQuery = getClusterQuery("DIRECTORIESID = ?", s);
                List<T> ts = clusterQuery
                        .find(tClass);
                if (ts.size() > 0) {
                    e.onNext(ts);
                    e.onComplete();
                } else
                    e.onError(new Throwable("未查询到数据"));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 更新数据库
     *
     * @param tClass 数据库表
     * @param id     查询条件
     * @param f      保存路径
     */
    public static void updateRecords(final Class<? extends DataSupport> tClass, final String id, final String f) {
        Observable.just(id)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        List<? extends DataSupport> list = getClusterQuery("DIRECTORIESID = ?", s)
                                .find(tClass);
                        for (int i = 0; i < list.size(); i++) {
                            FileDetails fileDetails = (FileDetails) list.get(i);
                            fileDetails.setSavePath(f);
                            fileDetails.save();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtils.toast("保存失败");
                    }
                });
    }

    /**
     * 产品界面添加
     *
     * @param o
     */
    public static void updateCommonRecords(final FileDetails o, final int t) {
        Observable.just(o)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FileDetails>() {
                    @Override
                    public void accept(@NonNull FileDetails fileDetails) throws Exception {
                        List<CommonFileDetailed> mFileDetailses = getClusterQuery("CONTENTID = ?", o.getContentId())
                                .find(CommonFileDetailed.class);
                        if (mFileDetailses.size() > 0) {
                            switch (t) {
                                case 0:
                                    for (CommonFileDetailed f : mFileDetailses) {
                                        f.delete();
                                    }
                                    break;
//                                case 1:
//                                    for (CommonFileDetailed f : mFileDetailses) {
////                                        f.setFlag(1);
//                                        f.save();
//                                        RxBus.getInstance().post(f);
//                                    }
//                                    break;
                            }
                        } else
                            insertCommonDb(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "updateCommonRecords: 添加产品失败");
                    }
                });

    }

    /**
     * 插入本地常用表
     *
     * @param o
     */
    private static void insertCommonDb(FileDetails o) {
        CommonFileDetailed fileDetailed = new CommonFileDetailed();
        fileDetailed.setFlag(1);
        fileDetailed.setContentId(o.getContentId());
        fileDetailed.setPermission(DataCenter.getInstance().getAuthId());
        fileDetailed.save();
        RxBus.getInstance().post(fileDetailed);
    }

    /**
     * 获取产品清单的请求参数
     *
     * @return
     */
    public static RequestParam get() {
        RequestParam p = new RequestParam();
        Header header = new Header("373F0C4ED4D444C6B50B3633EBEC9080", "selAllMenuContent");
        ProductListParam param = new ProductListParam();
        param.setOrganization_id(DataCenter.getInstance().getORGANIZATION_ID());
        param.setBranchtype(DataCenter.getInstance().getBRANCHTYPE());
        param.setAgent_grade(DataCenter.getInstance().getAGENT_GRADE());
        p.setData(param);
        p.setHeader(header);
        return p;
    }

    private static ClusterQuery getClusterQuery() {
        return
                where("PERMISSION = ?", DataCenter.getInstance().getAuthId());
    }

    public static ClusterQuery getClusterQuery(String sql, String conditions) {
        String id = DataCenter.getInstance().getAuthId();
        return
                where("PERMISSION = ? and " + sql, id, conditions);
    }


    public static void getHttpVersions(VersionParam p, final SoftUpdateModule updateModule) {
        DataCenter.getInstance().getHttpService().checkUpdate(p).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VersionEntity>() {
                    @Override
                    public void accept(@NonNull VersionEntity versionEntity) throws Exception {
                        VersionEntity.DataBean.GetSoftVersionInfoBean info = versionEntity.getData().getGetSoftVersionInfo();
                        DialogUtils.dismissProgressDialog();
                        updateModule.parseUpdate(info);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        updateModule.getListener().onFailure();
                    }
                });
    }
}
