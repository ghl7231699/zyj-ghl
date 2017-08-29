package com.nciebt.zyj.entity;

import java.io.Serializable;

/**
 * 类名称：BaseEntity
 * 类描述：服务端返回数据的基本类型
 * 创建人：ghl
 * 创建时间：2017/4/12 14:15
 * 修改人：ghl
 * 修改时间：2017/4/12 14:15
 *
 * @version v1.0
 */

public class BaseEntity<T> implements Serializable {
    private Header header;
    private T data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
