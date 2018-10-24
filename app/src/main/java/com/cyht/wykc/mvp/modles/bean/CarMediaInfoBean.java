package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/6/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarMediaInfoBean {


    /**
     * data : {"baoyang":[{"id":"f397ba276eb744dda6b48643f071d2f4","length":"03:18","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201805180956309180.png","name":"保养维修","videoType":"7"}],"caozuo":[{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"4"}],"dongtai":[{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"3"}],"gexing":[{"id":"394197b6164a44d78c0b58af2c39e317","length":"02:05","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201805180955459203.png","name":"个性改装","videoType":"6"}],"guidePrice":"10.60-11.60","guke":[{"id":"894b2925c0cc4392b607e214ea680034","length":"13:03","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261140560861.jpg","name":"销售讲解介绍哈弗H4","videoType":"5"},{"id":"8d3291e4479646e4912c736e16584f9f","length":"01:19","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261130501438.jpg","name":"哈弗H4试驾评说","videoType":"5"},{"id":"c8cf94250a64433981c9539d378a3db8","length":"34","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201805031007352314.jpg","name":"测试测试测试","videoType":"5"},{"id":"f0c581e008974c16bd68ad29b7cb9a34","length":"01:19","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201803291352153341.jpg","name":"哈佛H4测试","videoType":"5"}],"id":"哈弗H4","img":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261106586571.jpg","jingtai":[{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"2"}],"name":"哈弗H4","zhizao":[{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"1"}]}
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
         * baoyang : [{"id":"f397ba276eb744dda6b48643f071d2f4","length":"03:18","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201805180956309180.png","name":"保养维修","videoType":"7"}]
         * caozuo : [{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"4"}]
         * dongtai : [{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"3"}]
         * gexing : [{"id":"394197b6164a44d78c0b58af2c39e317","length":"02:05","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201805180955459203.png","name":"个性改装","videoType":"6"}]
         * guidePrice : 10.60-11.60
         * guke : [{"id":"894b2925c0cc4392b607e214ea680034","length":"13:03","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261140560861.jpg","name":"销售讲解介绍哈弗H4","videoType":"5"},{"id":"8d3291e4479646e4912c736e16584f9f","length":"01:19","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261130501438.jpg","name":"哈弗H4试驾评说","videoType":"5"},{"id":"c8cf94250a64433981c9539d378a3db8","length":"34","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201805031007352314.jpg","name":"测试测试测试","videoType":"5"},{"id":"f0c581e008974c16bd68ad29b7cb9a34","length":"01:19","logo":"http://test.woyaokanche.com:8080/51kanche/upload/file/201803291352153341.jpg","name":"哈佛H4测试","videoType":"5"}]
         * id : 哈弗H4
         * img : http://android.woyaokanche.com:8082/51kanche/upload/file/201803261106586571.jpg
         * jingtai : [{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"2"}]
         * name : 哈弗H4
         * zhizao : [{"id":"b5a99852b8b94934b1cf251eec8ca0ae","length":"00:30","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg","name":"哈弗H4 安全实力派SUV","videoType":"1"}]
         */

        private String guidePrice;
        private String id;
        private String img;
        private String name;
        private List<BaoyangEntity> baoyang;
        private List<CaozuoEntity> caozuo;
        private List<DongtaiEntity> dongtai;
        private List<GexingEntity> gexing;
        private List<GukeEntity> guke;
        private List<JingtaiEntity> jingtai;
        private List<ZhizaoEntity> zhizao;

        public String getGuidePrice() {
            return guidePrice;
        }

        public void setGuidePrice(String guidePrice) {
            this.guidePrice = guidePrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<BaoyangEntity> getBaoyang() {
            return baoyang;
        }

        public void setBaoyang(List<BaoyangEntity> baoyang) {
            this.baoyang = baoyang;
        }

        public List<CaozuoEntity> getCaozuo() {
            return caozuo;
        }

        public void setCaozuo(List<CaozuoEntity> caozuo) {
            this.caozuo = caozuo;
        }

        public List<DongtaiEntity> getDongtai() {
            return dongtai;
        }

        public void setDongtai(List<DongtaiEntity> dongtai) {
            this.dongtai = dongtai;
        }

        public List<GexingEntity> getGexing() {
            return gexing;
        }

        public void setGexing(List<GexingEntity> gexing) {
            this.gexing = gexing;
        }

        public List<GukeEntity> getGuke() {
            return guke;
        }

        public void setGuke(List<GukeEntity> guke) {
            this.guke = guke;
        }

        public List<JingtaiEntity> getJingtai() {
            return jingtai;
        }

        public void setJingtai(List<JingtaiEntity> jingtai) {
            this.jingtai = jingtai;
        }

        public List<ZhizaoEntity> getZhizao() {
            return zhizao;
        }

        public void setZhizao(List<ZhizaoEntity> zhizao) {
            this.zhizao = zhizao;
        }

        public static class BaoyangEntity {
            /**
             * id : f397ba276eb744dda6b48643f071d2f4
             * length : 03:18
             * logo : http://test.woyaokanche.com:8080/51kanche/upload/file/201805180956309180.png
             * name : 保养维修
             * videoType : 7
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }

        public static class CaozuoEntity {
            /**
             * id : b5a99852b8b94934b1cf251eec8ca0ae
             * length : 00:30
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg
             * name : 哈弗H4 安全实力派SUV
             * videoType : 4
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }

        public static class DongtaiEntity {
            /**
             * id : b5a99852b8b94934b1cf251eec8ca0ae
             * length : 00:30
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg
             * name : 哈弗H4 安全实力派SUV
             * videoType : 3
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }

        public static class GexingEntity {
            /**
             * id : 394197b6164a44d78c0b58af2c39e317
             * length : 02:05
             * logo : http://test.woyaokanche.com:8080/51kanche/upload/file/201805180955459203.png
             * name : 个性改装
             * videoType : 6
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }

        public static class GukeEntity {
            /**
             * id : 894b2925c0cc4392b607e214ea680034
             * length : 13:03
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201803261140560861.jpg
             * name : 销售讲解介绍哈弗H4
             * videoType : 5
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }

        public static class JingtaiEntity {
            /**
             * id : b5a99852b8b94934b1cf251eec8ca0ae
             * length : 00:30
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg
             * name : 哈弗H4 安全实力派SUV
             * videoType : 2
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }

        public static class ZhizaoEntity {
            /**
             * id : b5a99852b8b94934b1cf251eec8ca0ae
             * length : 00:30
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201803261123255274.jpg
             * name : 哈弗H4 安全实力派SUV
             * videoType : 1
             */

            private String id;
            private String length;
            private String logo;
            private String name;
            private String videoType;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLength() {
                return length;
            }

            public void setLength(String length) {
                this.length = length;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVideoType() {
                return videoType;
            }

            public void setVideoType(String videoType) {
                this.videoType = videoType;
            }
        }
    }
}
