package com.cyht.wykc.common;


import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.umeng.message.entity.UMessage;

/**
 * Author： hengzwd on 2017/8/16.
 * Email：hengzwdhengzwd@qq.com
 */

public class EventData {

    public int from;
    public int to;
    public int to1;
    public int doWhat;
    public  MainBean.DataEntity.BrandListEntity brandListEntity;
    public String url;
    public UMessage message;
    public EventData(int from, int to) {
        this.from = from;
        this.to = to;
    }
    public EventData(int from, int to,int doWhat) {
        this.from = from;
        this.to = to;
        this.doWhat=doWhat;
    }
    public EventData(int from, int to,int to1,int doWhat) {
        this.from = from;
        this.to = to;
        this.to1=to1;
        this.doWhat=doWhat;
    }
    public EventData(String url,int from) {
        this.url = url;
        this.from = from;
    }

    public EventData(int to) {
        this.to = to;
    }

    public EventData(int from, int to, MainBean.DataEntity.BrandListEntity brandListEntity)
    {
        this.brandListEntity=brandListEntity;
        this.from = from;
        this.to = to;
    }

    public EventData(int from, int to,int doWhat, UMessage msg)
    {
        this.message=msg;
        this.doWhat=doWhat;
        this.from = from;
        this.to = to;
    }
}
