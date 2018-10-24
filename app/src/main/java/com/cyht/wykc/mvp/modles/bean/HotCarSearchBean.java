package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/9/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class HotCarSearchBean {
    /**
     * data : {"carList":[{"brand":"9c3013b02e7f4a4ca8458b28d0cd05b0","id":"20e003735c0a4c1a81489329159bded6","name":"阿斯顿·马丁DB9","type":1},{"brand":"42cbd7387a05499b86e6dd21acc19b11","id":"f8d1b1717b2d4e968263ac709891b567","name":"宝斯通","type":2},{"brand":"1d19f845070a4ebe9a4703b5371da51e","id":"e15b205c6c6045bab3406a8267bc196b","name":"AF10","type":1},{"brand":"b068eb3aefc14e288f714b5c1e68f470","id":"073ed338783647d0b9644d64383cd68e","name":"奥迪Q2","type":1},{"brand":"b2fb0bf19c2c499aadcedc4d922a4e5b","id":"01df55876b904120a2d9ec5c57100e3d","name":"奥迪TT RS","type":1},{"brand":"6e30bc19f8c0492c8f5d9526a1d99354","id":"c9a70ad1189044dc9ab8016e94c08218","name":"Giulia","type":1},{"brand":"6e30bc19f8c0492c8f5d9526a1d99354","id":"33241877c3dc4137b01c92731b04be2a","name":"Stelvio","type":1},{"brand":"b15dc9a40bea4797a3530b8e654db7d4","id":"bda328ca734349ddb1e7f431c51e0dcd","name":"奥驰轻卡","type":2}]}
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
        private List<CarListEntity> carList;

        public List<CarListEntity> getCarList() {
            return carList;
        }

        public void setCarList(List<CarListEntity> carList) {
            this.carList = carList;
        }

        public static class CarListEntity {
            /**
             * brand : 9c3013b02e7f4a4ca8458b28d0cd05b0
             * id : 20e003735c0a4c1a81489329159bded6
             * name : 阿斯顿·马丁DB9
             * type : 1
             */

            private String brand;
            private String id;
            private String name;
            private int type;

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
