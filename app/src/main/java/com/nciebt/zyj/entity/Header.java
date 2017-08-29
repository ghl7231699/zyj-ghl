package com.nciebt.zyj.entity;

import java.io.Serializable;

/**
 * Created by guhongliang on 2017/3/27.
 */

public class Header implements Serializable{
    private static final String SUCCESS = "0";
    /**
     * user_id : 373F0C4ED4D444C6B50B3633EBEC9080
     * service_code : selAllMenuContent
     * response_code : 0
     * response_msg : {"default_msg":"查询成功"}
     */

    private String user_id;
    private String service_code;
    private String response_code;
    private ResponseMsg response_msg;

    public Header(String user_id, String service_code) {
        this.user_id = user_id;
        this.service_code = service_code;
    }

    public boolean isSuccess() {
        return (response_code.equals(SUCCESS));
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_code() {
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public ResponseMsg getResponse_msg() {
        return response_msg;
    }

    public void setResponse_msg(ResponseMsg response_msg) {
        this.response_msg = response_msg;
    }

    public static class ResponseMsg {
        /**
         * default_msg : 查询成功
         */

        private String default_msg;

        public String getDefault_msg() {
            return default_msg;
        }

        public void setDefault_msg(String default_msg) {
            this.default_msg = default_msg;
        }
    }
}
