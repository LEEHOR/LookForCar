package com.cyht.wykc.mvp.modles.bean;

import com.cyht.wykc.widget.SortRecyclerView.Indexable;

import java.io.Serializable;
import java.util.List;

/**
 * Author： hengzwd on 2017/6/8.
 * Email：hengzwdhengzwd@qq.com
 * 车型类
 */

public class BrandListBean implements Serializable{

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

        public List<BrandListEntity> getBrandList() {
            return brandList;
        }

        public void setBrandList(List<BrandListEntity> brandList) {
            this.brandList = brandList;
        }

        public static class BrandListEntity implements Indexable {
            /**
             * code : A
             * id : 58a81840002849989c76d958579904c3
             * logo : http://android.woyaokanche.com:8082/51kanche/upload/file/201702141550032248.jpg
             * name : ALPINA
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

            @Override
            public String getIndex() {
                return code;
            }
        }
    }
}
