package com.cyht.wykc.mvp.modles.bean;

import org.litepal.crud.DataSupport;

/**
 * Author： hengzwd on 2017/6/21.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarBean extends DataSupport {


    private String name;
    private int searchCount;
    private String type;

    private String brand;
    private int id;
    private String modelId;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brandid) {
        this.brand = brandid;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSearchCount() {
        return searchCount;
    }


    public void setSearchCount(int searchCount) {
        this.searchCount = searchCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CarBean() {
    }

    public CarBean(String modelId, String name, String brand) {
        this.modelId = modelId;
        this.name = name;
        this.brand=brand;
    }

    public CarBean(String modelId, String name, String brand, String type) {
        this.modelId = modelId;
        this.name = name;
        this.brand=brand;
        this.type=type;
    }
    public CarBean(String modelId, String name) {
        this.modelId = modelId;
        this.name = name;
    }

}
