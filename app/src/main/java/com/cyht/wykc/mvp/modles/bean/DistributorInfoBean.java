package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/30.
 * Email：hengzwdhengzwd@qq.com
 */

public class DistributorInfoBean {


    /**
     * data : {"dealer":[{"avatar":"","brand":"ed5af8357ea94fcc9b78b539314eadb4","city":"武汉市","distance":1509.684,"latitude":18,"linkMan":"","location":"武汉经济 开发区","longitude":120,"mobile":"","pic":"http://192.168.191.1:8080/51kanche/upload/file/201709271450383544.jpg","storeName":"武汉东风小康","tel":"400-9898877","url":""}]}
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
        private List<DealerEntity> dealer;

        public List<DealerEntity> getDealer() {
            return dealer;
        }

        public void setDealer(List<DealerEntity> dealer) {
            this.dealer = dealer;
        }

        public static class DealerEntity {
            /**
             * avatar :
             * brand : ed5af8357ea94fcc9b78b539314eadb4
             * city : 武汉市
             * distance : 1509.684
             * latitude : 18.0
             * linkMan :
             * location : 武汉经济 开发区
             * longitude : 120.0
             * mobile :
             * pic : http://192.168.191.1:8080/51kanche/upload/file/201709271450383544.jpg
             * storeName : 武汉东风小康
             * tel : 400-9898877
             * url :
             */

            private String avatar;
            private String brand;
            private String city;
            private double distance;
            private double latitude;
            private String linkMan;
            private String location;
            private double longitude;
            private String mobile;
            private String pic;
            private String storeName;
            private String tel;
            private String url;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public double getDistance() {
                return distance;
            }

            public void setDistance(double distance) {
                this.distance = distance;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String getLinkMan() {
                return linkMan;
            }

            public void setLinkMan(String linkMan) {
                this.linkMan = linkMan;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getStoreName() {
                return storeName;
            }

            public void setStoreName(String storeName) {
                this.storeName = storeName;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
