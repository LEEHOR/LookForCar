package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/25.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarPriceBean {

    /**
     * data : {"detail":[{"engine":"1.6L/117马力 自然吸气","info":[{"price":"12.78万","type":"2017款 自动先锋型"},{"price":"11.88万","type":"2017款 自动时尚型"},{"price":"11.78万","type":"2017款 手动先锋型"},{"price":"10.88万","type":"2017款 手动时尚型"}]},{"engine":"1.2L/136马力 涡轮增压","info":[{"price":"12.78万","type":"2017款 230THP 手动先锋型"},{"price":"14.98万","type":"2017款 230THP 自动智能型 "},{"price":"13.98万","type":"2017款 230THP 自动先锋型 "}]},{"engine":"1.6L/167马力 涡轮增压","info":[{"price":"17.18万","type":"2017款 350THP 自动旗舰型"}]}],"sum":8}
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
         * detail : [{"engine":"1.6L/117马力 自然吸气","info":[{"price":"12.78万","type":"2017款 自动先锋型"},{"price":"11.88万","type":"2017款 自动时尚型"},{"price":"11.78万","type":"2017款 手动先锋型"},{"price":"10.88万","type":"2017款 手动时尚型"}]},{"engine":"1.2L/136马力 涡轮增压","info":[{"price":"12.78万","type":"2017款 230THP 手动先锋型"},{"price":"14.98万","type":"2017款 230THP 自动智能型 "},{"price":"13.98万","type":"2017款 230THP 自动先锋型 "}]},{"engine":"1.6L/167马力 涡轮增压","info":[{"price":"17.18万","type":"2017款 350THP 自动旗舰型"}]}]
         * sum : 8
         */

        private int sum;
        private List<DetailEntity> detail;

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public List<DetailEntity> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailEntity> detail) {
            this.detail = detail;
        }

        public static class DetailEntity {
            /**
             * engine : 1.6L/117马力 自然吸气
             * info : [{"price":"12.78万","type":"2017款 自动先锋型"},{"price":"11.88万","type":"2017款 自动时尚型"},{"price":"11.78万","type":"2017款 手动先锋型"},{"price":"10.88万","type":"2017款 手动时尚型"}]
             */

            private String engine;
            private List<InfoEntity> info;

            public String getEngine() {
                return engine;
            }

            public void setEngine(String engine) {
                this.engine = engine;
            }

            public List<InfoEntity> getInfo() {
                return info;
            }

            public void setInfo(List<InfoEntity> info) {
                this.info = info;
            }

            public static class InfoEntity {
                /**
                 * price : 12.78万
                 * type : 2017款 自动先锋型
                 */

                private double price;
                private String type;

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
