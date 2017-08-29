package com.nciebt.zyj.common.rx;

import com.nciebt.zyj.common.exception.ApiException;
import com.nciebt.zyj.entity.BaseEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 类名称：RxHttpTransFormer
 * 类描述：过滤器
 * 创建人：ghl
 * 创建时间：2017/4/12 14:33
 * 修改人：ghl
 * 修改时间：2017/4/12 14:33
 *
 * @version v1.0
 */

public class RxHttpTransFormer {
    public static <T> ObservableTransformer<BaseEntity<T>, T> handleResult() {
        return new ObservableTransformer<BaseEntity<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseEntity<T>> upstream) {

                return upstream.flatMap(new Function<BaseEntity<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull final BaseEntity<T> tBaseEntity) throws Exception {
                        if (tBaseEntity != null) {
                            if (tBaseEntity.getHeader().isSuccess()) {//是否返回成功
                                return createData(tBaseEntity);
                            } else if (tBaseEntity.getHeader().getResponse_msg() != null) {
                                //返回错误原因
                                return Observable.error(new ApiException(Integer.valueOf(tBaseEntity.getHeader().getResponse_code()),
                                        tBaseEntity.getHeader().getResponse_msg().getDefault_msg()));
                            }
                            return Observable.empty();
                        } else
                            return Observable.empty();
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }

            private ObservableSource<T> createData(@NonNull final BaseEntity<T> tBaseEntity) {
                return Observable.create(new ObservableOnSubscribe<T>() {
                    @Override
                    public void subscribe(ObservableEmitter<T> e) throws Exception {
                        try {
                            e.onNext(tBaseEntity.getData());
                            e.onComplete();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        };
    }
}
