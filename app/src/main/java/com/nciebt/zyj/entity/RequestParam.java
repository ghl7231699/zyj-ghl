package com.nciebt.zyj.entity;

import java.util.Objects;

/**
 * 类名称：RequestParam
 * 类描述：请求参数实体类
 * 创建人：ghl
 * 创建时间：2017/3/26 9:56
 * 修改人：ghl
 * 修改时间：2017/3/26 9:56
 *
 * @version v1.0
 */

public class RequestParam {
    private Header header;
    private Object data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
