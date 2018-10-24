package com.cyht.wykc.mvp.modles.bean;


import org.litepal.crud.DataSupport;

/**
 * Author： hengzwd on 2017/7/3.
 * Email：hengzwdhengzwd@qq.com
 */

public class BrandBean extends DataSupport {


    private int id;
    private String brandid;
    private String code;
    private String name;
    private String logo;
    private String type;

    public BrandBean() {
    }



    public BrandBean(String brandid, String name, String code, String logo, String type) {
        super();
        this.brandid = brandid;
        this.name = name;
        this.code = code;
        this.logo = logo;
        this.type=type;

    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrandid() {
        return brandid;
    }
    public void setBrandid(String id) {
        this.brandid = brandid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
}
