package com.cyht.wykc.common;

import com.cyht.wykc.mvp.modles.bean.BrandListBean;

import java.util.Comparator;

/**
 * Author： hengzwd on 2017/8/16.
 * Email：hengzwdhengzwd@qq.com
 * 排序
 */

public class BrandComparator implements Comparator<BrandListBean.DataEntity.BrandListEntity>  {

    @Override
    public int compare(BrandListBean.DataEntity.BrandListEntity lhs, BrandListBean.DataEntity.BrandListEntity rhs) {
        String a = lhs.getCode();
        String b = rhs.getCode();
        int flag = a.compareTo(b);
        if (flag == 0) {
            return a.compareTo(b);
        } else {
            return flag;
        }
    }
}
