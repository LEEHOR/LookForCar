package com.cyht.wykc.mvp.modles.bean;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.List;

/**
 * Author： hengzwd on 2017/6/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class CollectionBean {
    /**
     * list : [{"cxid":"4b1ab2993c03436097a1cff617e0ca2a","cxmc":"V12 Vantage","id":"6e433a0023e4402da63d1cb0686a2e31","imageurl":"http://android.woyaokanche.com:8082/51kanche/upload/file/201704171044230300.png","title":"阿斯顿·马丁制造全过程"}]
     * msg : 请求成功！
     * result : 1
     */

    private String msg;
    private int result;
    private List<ListEntity> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
        /**
         * cxid : 4b1ab2993c03436097a1cff617e0ca2a
         * cxmc : V12 Vantage
         * id : 6e433a0023e4402da63d1cb0686a2e31
         * imageurl : http://android.woyaokanche.com:8082/51kanche/upload/file/201704171044230300.png
         * title : 阿斯顿·马丁制造全过程
         */

        private String cxid;
        private String cxmc;
        private String id;
        private String imageurl;
        private String title;
        private int videoType;
        public int getVideoType() {
            return videoType;
        }
        public void setVideoType(int videoType) {
            this.videoType = videoType;
        }

        public String getCxid() {
            return cxid;
        }

        public void setCxid(String cxid) {
            this.cxid = cxid;
        }

        public String getCxmc() {
            return cxmc;
        }

        public void setCxmc(String cxmc) {
            this.cxmc = cxmc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
