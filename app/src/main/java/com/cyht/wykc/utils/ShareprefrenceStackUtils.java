package com.cyht.wykc.utils;


import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author： hengzwd on 2017/6/23.
 * Email：hengzwdhengzwd@qq.com
 */

public class ShareprefrenceStackUtils {//以stack结构特性来存储搜索历史

    private String TAG = ShareprefrenceStackUtils.class.getSimpleName().toString();
    private static ShareprefrenceStackUtils INSTANCE;
    private static Stack<String> stack = new Stack<String>();
    private static Stack<CarBean> carstack = new Stack<CarBean>();
    private List<CarBean> carmodelList = new ArrayList<CarBean>();

    private static final int TOTAL_HISTORY_COUNT = 5;   //搜索历史存储数量

    private ShareprefrenceStackUtils() {
        initStack();
    }

    public static ShareprefrenceStackUtils getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else {
            INSTANCE = new ShareprefrenceStackUtils();
        }
        return INSTANCE;
    }

    private void initStack() {
        for (int i = TOTAL_HISTORY_COUNT - 1; i >= 0; i--) {
            if (PreferenceUtils.contains(BaseApplication.mContext, i + "")&&PreferenceUtils.contains(BaseApplication.mContext,"name"+i)) {
                stack.push(PreferenceUtils.getPrefString(BaseApplication.mContext, i + "", ""));
                carstack.push(new CarBean(PreferenceUtils.getPrefString(BaseApplication.mContext, i + "", ""),PreferenceUtils.getPrefString(BaseApplication.mContext, "name"+i, "")));
            } else {
                KLog.e(TAG, stack.size());
                continue;
            }
        }
    }


    public void clearstack() {
        stack.clear();
        carstack.clear();
        int i = 0;
        while (i < TOTAL_HISTORY_COUNT) {
            PreferenceUtils.remove(BaseApplication.mContext, (i++) + "");
            PreferenceUtils.remove(BaseApplication.mContext, "name"+(i++));
        }
        KLog.e("mcarstack"+carstack.size());
    }

    public void save() {
        int i = 0;
        Stack<String> mstack = new Stack<String>();
        mstack.addAll(stack);
        Stack<CarBean> mcarstack = new Stack<CarBean>();
        mcarstack.addAll(carstack);
        String id;
        while (!mstack.empty()&&!mcarstack.empty() && i < TOTAL_HISTORY_COUNT) {
            KLog.e("save"+i);
            id=mstack.pop();
            CarBean carmodel=mcarstack.pop();
            PreferenceUtils.setPrefString(BaseApplication.mContext, i + "", id);
            PreferenceUtils.setPrefString(BaseApplication.mContext, "name"+i , carmodel.getName());
            i++;
        }
    }


    public void add(CarBean carmodel) {
        if (!stack.contains(carmodel.getModelId())) {
            stack.push(carmodel.getModelId());
            carstack.push(carmodel);
        }
        KLog.e(TAG, "历史搜索栈的size：" + stack.size());
        save();
    }

    public List<CarBean> getCarModelList() {
        carmodelList.clear();
        Stack<CarBean> mstack = new Stack<CarBean>();
        mstack.addAll(carstack);
        int i = 0;
        while (!mstack.empty() && i < 10) {
            carmodelList.add(mstack.pop());
            i++;
        }
        KLog.e(TAG, "历史查询数" + carmodelList.size());
        return carmodelList;
    }
}
