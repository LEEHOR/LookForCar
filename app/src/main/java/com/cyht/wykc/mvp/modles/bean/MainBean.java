package com.cyht.wykc.mvp.modles.bean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/21.
 * Email：hengzwdhengzwd@qq.com
 */

public class MainBean {
    /**
     * data : {"brandList":[{"code":"B","id":"352186adfe7d4eeb99a58f1a66a0b267","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201702051154281647.jpg","name":"广汽本田","type":1},{"code":"B","id":"0eba8e7da89642fd896be097e831d8a4","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708101016228070.jpg","name":"奔驰(进口)","type":1},{"code":"J","id":"c2bec6e615c3495c8d9dbcf71a05b4ea","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708101344501361.jpg","name":"吉利汽车","type":1},{"code":"B","id":"f68048eadf084bf9a6983f719cfd1cee","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708100951496688.jpg","name":"上汽通用别克","type":1},{"code":"A","id":"58a81840002849989c76d958579904c3","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201702141550032248.jpg","name":"ALPINA","type":1},{"code":"B","id":"1fb867ca1a704385b8289266d02758e8","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201702141603225728.jpg","name":"华晨宝马","type":1},{"code":"A","id":"dbbf5c2ab5c64fc2b617171815fc1055","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708100945581971.jpg","name":"一汽-大众 奥迪","type":1},{"code":"A","id":"9c3013b02e7f4a4ca8458b28d0cd05b0","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708100946349391.jpg","name":"阿斯顿·马丁","type":1},{"code":"A","id":"1d19f845070a4ebe9a4703b5371da51e","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201702141550231427.jpg","name":"Arash","type":1},{"code":"A","id":"6e30bc19f8c0492c8f5d9526a1d99354","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201702211626166392.jpg","name":"阿尔法·罗密欧","type":1},{"code":"B","id":"8a11cb2db274404c8ec7dbf92f7e5bd1","logo":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708101008200932.jpg","name":"宾利","type":1}],"carouselList":[{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201709301629065992.jpg","type":1,"video":"e1e7c35a4d584147a2d062c2e1a1524a","videoName":"超值 安全 新跨界"},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201706281530205707.jpg","type":3},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201709301623240744.jpg","type":1,"video":"c50ccd9828d94ac5a50573311b2df9c8","videoName":"纳智捷U5 SUV 敢真由我"}],"topicList":[{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201708311033211213.png","id":"6795dc489b564af9b747b5aba30c4cf8","type":4},{"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201706281530205707.jpg","id":"a984358dcebe48bf997f9446098207fd","type":3}],"videoList":[{"comment":146,"count":940,"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201707071540276195.png","id":"a3bcb9bac50e4143a70b0b11c9ff95d3","title":"东风御风国五极限测试","url":"https://v.qq.com/iframe/player.html?vid=f0302r4amet&tiny=0&auto=0"},{"comment":26,"count":823,"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201709301629065992.jpg","id":"d1a557605f0341b7b23c4d2bd223b707","title":"纳智捷U5 SUV 酷靓颜值","url":"https://v.qq.com/iframe/player.html?vid=q0544vvg8wp&tiny=0&auto=0"},{"comment":2,"count":113,"cover":"http://android.woyaokanche.com:8082/51kanche/upload/file/201709301627478873.jpg","id":"818c753788784a32afc0e804617a2cd7","title":"纳智捷U5 SUV 炫奇配置","url":"https://v.qq.com/iframe/player.html?vid=a0544qj4ypj&tiny=0&auto=0"}]}
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
        private List<BrandListEntity> brandList;
        private List<CarouselListEntity> carouselList;
        private List<TopicListEntity> topicList;
        private List<VideoListEntity> videoList;

        public List<BrandListEntity> getBrandList() {
            return brandList;
        }

        public void setBrandList(List<BrandListEntity> brandList) {
            this.brandList = brandList;
        }

        public List<CarouselListEntity> getCarouselList() {
            return carouselList;
        }

        public void setCarouselList(List<CarouselListEntity> carouselList) {
            this.carouselList = carouselList;
        }

        public List<TopicListEntity> getTopicList() {
            return topicList;
        }

        public void setTopicList(List<TopicListEntity> topicList) {
            this.topicList = topicList;
        }

        public List<VideoListEntity> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListEntity> videoList) {
            this.videoList = videoList;
        }

        public static class BrandListEntity {
            /**
             * code : B
             * id : 352186adfe7d4eeb99a58f1a66a0b267
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201702051154281647.jpg
             * name : 广汽本田
             * type : 1
             */

            private String code;
            private String id;
            private String logo;
            private String name;
            private int type;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class CarouselListEntity {
            /**
             * cover : http://android.woyaokanche.com:8082/51kanche/upload/file/201709301629065992.jpg
             * type : 1
             * video : e1e7c35a4d584147a2d062c2e1a1524a
             * videoName : 超值 安全 新跨界
             */

            private String url;
            private String car;
            private String carName;
            private String cover;
            private int type;
            private String video;
            private String videoName;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getVideoName() {
                return videoName;
            }

            public void setVideoName(String videoName) {
                this.videoName = videoName;
            }

            public String getCar() {
                return car;
            }

            public void setCar(String car) {
                this.car = car;
            }

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }


            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class TopicListEntity {
            /**
             * cover : http://android.woyaokanche.com:8082/51kanche/upload/file/201708311033211213.png
             * id : 6795dc489b564af9b747b5aba30c4cf8
             * type : 4
             */

            private String cover;
            private String id;
            private int type;
            private int videoType;
            private String url;
            private String car;
            private String carName;
            private String video;
            private String videoName;

            public int getVideoType() {
                return videoType;
            }

            public void setVideoType(int videoType) {
                this.videoType = videoType;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCar() {
                return car;
            }

            public void setCar(String car) {
                this.car = car;
            }

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getVideoName() {
                return videoName;
            }

            public void setVideoName(String videoName) {
                this.videoName = videoName;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class VideoListEntity {
            /**
             * comment : 146
             * count : 940
             * cover : http://android.woyaokanche.com:8082/51kanche/upload/file/201707071540276195.png
             * id : a3bcb9bac50e4143a70b0b11c9ff95d3
             * title : 东风御风国五极限测试
             * url : https://v.qq.com/iframe/player.html?vid=f0302r4amet&tiny=0&auto=0
             */

            private int comment;
            private int count;
            private String cover;
            private String id;
            private String title;
            private String url;
            private String car;
            private String carName;
            private int videoType;
            public int getVideoType() {
                return videoType;
            }
            public void setVideoType(int videoType) {
                this.videoType = videoType;
            }
            public String getCar() {
                return car;
            }

            public void setCar(String car) {
                this.car = car;
            }

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
