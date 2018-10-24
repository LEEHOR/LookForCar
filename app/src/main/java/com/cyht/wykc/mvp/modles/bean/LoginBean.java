package com.cyht.wykc.mvp.modles.bean;

/**
 * Author： hengzwd on 2017/6/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class LoginBean {


    /**
     * message : 绑定成功！
     * result : 1
     * sessionid : b794cd47aff548b29aef21d317009543
     * touxiang : http://wx.qlogo.cn/mmopen/kZrDdAjCpWdzrLuCCibiaWExFYOBB0RAuotAgPkicufNMdrILIugFHToXr8ZSZSicXlXibmJHZ0rqc21iaN46nW0m5bcn85snZ5oYS/0
     * userid : b794cd47aff548b29aef21d317009543
     * username : 哈喽
     */

    private String message;
    private String msg;
    private int result;
    private String sessionid;
    private String touxiang;
    private String userid;
    private String username;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
