package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/6/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarListBean {


    /**
     * data : {"carList":[{"id":"e15b205c6c6045bab3406a8267bc196b","name":"AF10"}]}
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
             * id : e15b205c6c6045bab3406a8267bc196b
             * name : AF10
             */

            private String id;
            private String name;

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
        }
    }
}
