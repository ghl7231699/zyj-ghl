package com.nciebt.zyj.entity;
/**
 * 类名称：VersionEntity
 * 类描述：(版本更新实体)
 * 创建人：xs
 * 创建时间：2017/5/27 11:33
 * 修改人：xs
 * 修改时间：2017/5/27 11:33
 * @version v1.0
 */
public class VersionEntity {

    /**
     * header : {"response_code":"0","response_msg":""}
     * data : {"getSoftVersionInfo":{"channel":"1,8","modDate":"2016-12-01","modUid":"1000004","nwxzdz":"http://192.168.190.38/NCI_EBT2.0_GZ_1.0.14.apk","type":"9","versionnumber":"1","versionstate":0,"wwxzdz":"http://newchinalife.cdn.lenovows.com/EBT2/NCI_EBT2.0_GZ_1.0.14.apk"}}
     */

    private HeaderBean header;
    private DataBean data;

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class HeaderBean {
        /**
         * response_code : 0
         * response_msg :
         */

        private String response_code;
        private String response_msg;

        public String getResponse_code() {
            return response_code;
        }

        public void setResponse_code(String response_code) {
            this.response_code = response_code;
        }

        public String getResponse_msg() {
            return response_msg;
        }

        public void setResponse_msg(String response_msg) {
            this.response_msg = response_msg;
        }
    }

    public static class DataBean {
        /**
         * getSoftVersionInfo : {"channel":"1,8","modDate":"2016-12-01","modUid":"1000004","nwxzdz":"http://192.168.190.38/NCI_EBT2.0_GZ_1.0.14.apk","type":"9","versionnumber":"1","versionstate":0,"wwxzdz":"http://newchinalife.cdn.lenovows.com/EBT2/NCI_EBT2.0_GZ_1.0.14.apk"}
         */

        private GetSoftVersionInfoBean getSoftVersionInfo;

        public GetSoftVersionInfoBean getGetSoftVersionInfo() {
            return getSoftVersionInfo;
        }

        public void setGetSoftVersionInfo(GetSoftVersionInfoBean getSoftVersionInfo) {
            this.getSoftVersionInfo = getSoftVersionInfo;
        }

        public static class GetSoftVersionInfoBean {
            /**
             * channel : 1,8
             * modDate : 2016-12-01
             * modUid : 1000004
             * nwxzdz : http://192.168.190.38/NCI_EBT2.0_GZ_1.0.14.apk
             * type : 9
             * versionnumber : 1
             * versionstate : 0
             * wwxzdz : http://newchinalife.cdn.lenovows.com/EBT2/NCI_EBT2.0_GZ_1.0.14.apk
             */

            private String channel;
            private String modDate;
            private String modUid;
            private String nwxzdz;
            private String type;
            private int versionnumber;
            private int versionstate;
            private String wwxzdz;

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getModDate() {
                return modDate;
            }

            public void setModDate(String modDate) {
                this.modDate = modDate;
            }

            public String getModUid() {
                return modUid;
            }

            public void setModUid(String modUid) {
                this.modUid = modUid;
            }

            public String getNwxzdz() {
                return nwxzdz;
            }

            public void setNwxzdz(String nwxzdz) {
                this.nwxzdz = nwxzdz;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setVersionnumber(int versionnumber) {
                this.versionnumber = versionnumber;
            }

            public int getVersionnumber() {
                return versionnumber;
            }

            public int getVersionstate() {
                return versionstate;
            }

            public void setVersionstate(int versionstate) {
                this.versionstate = versionstate;
            }

            public String getWwxzdz() {
                return wwxzdz;
            }

            public void setWwxzdz(String wwxzdz) {
                this.wwxzdz = wwxzdz;
            }
        }
    }
}
