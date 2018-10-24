package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/18.
 * Email：hengzwdhengzwd@qq.com
 */

public class HotVideoBean {


    /**
     * data : {"hotList":[{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201704251352583230.png","title":"宋 盖世版动态展示","url":"https://v.qq.com/iframe/player.html?vid=i0325ka2hb8&tiny=0&auto=0"},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201707071900373508.png","title":"猎豹CS9非凡SUV","url":"https://v.qq.com/iframe/player.html?vid=u0507k40j8n&tiny=0&auto=0"},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201706121514370985.png","title":"搭载12个高级音响","url":"https://v.qq.com/iframe/player.html?vid=n0502dmz9b0&tiny=0&auto=0"}],"pageNo":1}
     * msg : 请求成功！
     * result : 1
     */

    private DataEntity data;
    private String msg;
    private int result;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

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

    public static class DataEntity {
        /**
         * hotList : [{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201704251352583230.png","title":"宋 盖世版动态展示","url":"https://v.qq.com/iframe/player.html?vid=i0325ka2hb8&tiny=0&auto=0"},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201707071900373508.png","title":"猎豹CS9非凡SUV","url":"https://v.qq.com/iframe/player.html?vid=u0507k40j8n&tiny=0&auto=0"},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201706121514370985.png","title":"搭载12个高级音响","url":"https://v.qq.com/iframe/player.html?vid=n0502dmz9b0&tiny=0&auto=0"}]
         * pageNo : 1
         */

        private int pageNo;
        private List<MainBean.DataEntity.VideoListEntity> hotList;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public List<MainBean.DataEntity.VideoListEntity> getHotList() {
            return hotList;
        }

        public void setHotList(List<MainBean.DataEntity.VideoListEntity> hotList) {
            this.hotList = hotList;
        }

    }
}
