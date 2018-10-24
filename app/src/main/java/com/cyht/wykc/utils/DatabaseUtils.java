package com.cyht.wykc.utils;

import android.content.ContentValues;
import android.database.Cursor;


import com.cyht.wykc.mvp.modles.bean.BrandBean;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.socks.library.KLog;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Author： hengzwd on 2017/6/21.
 * Email：hengzwdhengzwd@qq.com
 */

public class DatabaseUtils {

    private volatile static DatabaseUtils INSTANCE;

    private DatabaseUtils() {

    }

    public static DatabaseUtils getInstance() {
        synchronized (DatabaseUtils.class) {
            if (INSTANCE != null) {
                return INSTANCE;
            } else {
                return new DatabaseUtils();
            }
        }
    }

    /**
     * 增加品牌车型搜索次数
     */
    public void raiseSearchCount(String id) {
        List<CarBean> carmodels = DataSupport.where("modelid = ?", id).find(CarBean.class);
        int count = carmodels.get(0).getSearchCount();
        int IDL = carmodels.get(0).getId();
        count++;
        ContentValues values = new ContentValues();
        values.put("searchcount", count);
        DataSupport.update(CarBean.class, values, IDL);

    }

    /**
     * 模糊搜索
     *
     * @param input 输入的字
     * @return 车型列表
     */
    public List<CarBean> findCarModels(String input) {

        List<CarBean> carmodels = DataSupport.where("name like ?", "%" + input + "%")
                .find(CarBean.class);
      KLog.e(carmodels.size());
        return carmodels;
    }

    /**
     * @param ID 车型id
     * @return
     */
    public CarBean getCarModelById(String ID) {

        Cursor cursor = DataSupport.findBySQL("select * from carBean where modelid = ?", ID);
      KLog.e(cursor.getColumnIndex("name"));
        while (cursor.moveToNext()) {
            return new CarBean(ID, cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("brand")));
        }
        return null;
    }


    public List<CarBean> getCarListOfBrand(String brand, String type) {
        return DataSupport.where("brand = ? AND type = ?", brand,type).find(CarBean.class);
    }

    /**
     * 保存数据到数据库
     */

    public void save(Class clazz, List list) {
        DataSupport.deleteAll(clazz, "");
        DataSupport.saveAll(list);
    }

    /**
     * 搜索品牌列表
     *
     * @param type 商用车2，乘用车1
     * @return
     */
    public List<BrandBean> findCarbrands(String type) {
        List<BrandBean> brandModels = DataSupport.where("type = ?", type).find(BrandBean.class);
        return brandModels;
    }

}
