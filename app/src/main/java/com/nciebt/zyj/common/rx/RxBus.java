package com.nciebt.zyj.common.rx;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static android.content.ContentValues.TAG;

/**
 * 类名称：RxBus
 * 类描述：RxBus
 * 创建人：ghl
 * 创建时间：2017/4/25 14:52
 * 修改人：ghl
 * 修改时间：2017/4/25 14:52
 *
 * @version v1.0
 */

public class RxBus {
    private final Subject<Object> bus;
    private static RxBus instance;

    private RxBus() {

        bus = PublishSubject.create().toSerialized();

    }

    public static synchronized RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 发送消息
     *
     * @param o
     */
    public void post(Object o) {
        Log.d(TAG, "post: " + bus.hashCode());
        bus.onNext(o);
    }

    /**
     * 接受消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
