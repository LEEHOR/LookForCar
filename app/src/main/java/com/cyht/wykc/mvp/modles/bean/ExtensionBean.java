package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/11/10.
 * Email：hengzwdhengzwd@qq.com
 */

public class ExtensionBean {


    /**
     * data : {"hotArray":["http://192.168.2.201:8080/51kanche/upload/file/201711092105194016.jpg","恶趣味无群二无群二无群二无群二群翁而我却而我却二群翁王企鹅气温气温无群二其味无穷其味无穷请问气温气温而且我"],"hotTitle":"而为全文","pushTime":"2017-11-09 21:05"}
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
         * hotArray : ["http://192.168.2.201:8080/51kanche/upload/file/201711092105194016.jpg","恶趣味无群二无群二无群二无群二群翁而我却而我却二群翁王企鹅气温气温无群二其味无穷其味无穷请问气温气温而且我"]
         * hotTitle : 而为全文
         * pushTime : 2017-11-09 21:05
         */

        private String hotTitle;
        private String pushTime;
        private List<String> hotArray;

        public String getHotTitle() {
            return hotTitle;
        }

        public void setHotTitle(String hotTitle) {
            this.hotTitle = hotTitle;
        }

        public String getPushTime() {
            return pushTime;
        }

        public void setPushTime(String pushTime) {
            this.pushTime = pushTime;
        }

        public List<String> getHotArray() {
            return hotArray;
        }

        public void setHotArray(List<String> hotArray) {
            this.hotArray = hotArray;
        }
    }
}
