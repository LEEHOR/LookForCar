package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/28.
 * Email：hengzwdhengzwd@qq.com
 */

public class ChexingBean {


    /**
     * data : {"carList":[{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"1d06fb037b6647ecb20cb4990a496678","name":"天逸 C5 AIRCROSS","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"259b90c988294b2b8339f0c85b618626","name":"雪铁龙C3-XR","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"36ef694f6f4f4632969b1c116b6bfe87","name":"世嘉","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"3d74b0c9c065449db28a7c81792e27d7","name":"爱丽舍","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"61592df2cc9249d5977c08026d6841e5","name":"雪铁龙C4L","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"770185ecd7fb480da3c60ac02f33bc82","name":"雪铁龙C5","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"adc198b78f3343f186d5b37b7068ca75","name":"雪铁龙C6","type":1},{"brand":"ed5af8357ea94fcc9b78b539314eadb4","id":"caba3fef10bb4cd9b5c1400c4e0dfdea","name":"C4世嘉","type":1}]}
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
             * brand : ed5af8357ea94fcc9b78b539314eadb4
             * id : 1d06fb037b6647ecb20cb4990a496678
             * name : 天逸 C5 AIRCROSS
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
