package com.cyht.wykc.mvp.modles.bean;

/**
 * Author： hengzwd on 2017/9/20.
 * Email：hengzwdhengzwd@qq.com
 */

public class UpdateBean {


    /**
     * isnew : 1
     * msg : 请求成功！
     * result : 1
     * url : http://android.woyaokanche.com:8082/51kanche/wykc.apk
     */

    private int isnew;
    private String msg;
    private int result;
    private String url;

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
