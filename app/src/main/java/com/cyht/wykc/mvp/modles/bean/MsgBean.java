package com.cyht.wykc.mvp.modles.bean;


import java.util.List;

/**
 * Author： hengzwd on 2017/9/11.
 * Email：hengzwdhengzwd@qq.com
 */

public class MsgBean {


    /**
     * data : {"list":[{" createTimeStr":"2017-9-9 9:09","body":"系统将在今天9点-12进行系统维护，给您带来的不便敬请谅解。","title":"系统提醒","name":"Boring Man","content":"真好","type":0}]}
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

        public List<ListEntity> getMsgList() {
            return list;
        }

        public void setMsgList(List<ListEntity> list) {
            this.list = list;
        }

        private List<ListEntity> list;

        public static class ListEntity {
            /**
             * id : "a86dca84ca22486aa3b72cebc40b5a58"
             * createTimeStr : 2017-9-9 9:09
             * body : 系统将在今天9点-12进行系统维护，给您带来的不便敬请谅解。
             * title : 系统提醒
             * name : Boring Man
             * content : 真好
             * type : 0
             */

            private String createTimeStr;
            private String body;
            private String title;
            private String name;
            private String content;
            private String id;
            private String target;
            private String cover;
            private int isRead;
            private int type;
            private int videoType;
            public int getVideoType() {
                return videoType;
            }
            public void setVideoType(int videoType) {
                this.videoType = videoType;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
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

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
            }

            public String getCreateTimeStr() {
                return createTimeStr;
            }

            public void setCreateTimeStr(String createTimeStr) {
                this.createTimeStr = createTimeStr;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
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
