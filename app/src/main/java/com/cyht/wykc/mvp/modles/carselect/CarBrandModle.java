package com.cyht.wykc.mvp.modles.carselect;

import com.cyht.wykc.mvp.modles.HttpHelper;
import com.cyht.wykc.mvp.modles.base.BaseModel;
import com.cyht.wykc.mvp.contract.carselect.CarBrandContract;
import com.cyht.wykc.mvp.modles.bean.BrandListBean;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarBrandModle extends BaseModel<CarBrandContract.Presenter> implements CarBrandContract.Model {

    public CarBrandModle(CarBrandContract.Presenter mpresenter) {
        super(mpresenter);
    }

    @Override
    public void start() {

        Map map=new HashMap();

//        HttpHelper.getInstance().initService().getBrandList(map).
    }

    @Override
    public void createRequestUrl() {

    }

    @Override
    public void requestBrandVideo() {


    }


    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator<BrandListBean.DataEntity.BrandListEntity> comparator = new Comparator<BrandListBean.DataEntity.BrandListEntity>() {
        @Override
        public int compare(BrandListBean.DataEntity.BrandListEntity lhs , BrandListBean.DataEntity.BrandListEntity rhs) {
            String a = lhs.getCode();
            String b = rhs.getCode();
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

}
