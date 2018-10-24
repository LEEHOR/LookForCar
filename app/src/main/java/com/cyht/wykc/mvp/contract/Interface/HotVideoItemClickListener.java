package com.cyht.wykc.mvp.contract.Interface;

import com.cyht.wykc.mvp.modles.bean.HotVideoBean;
import com.cyht.wykc.mvp.modles.bean.MainBean;

/**
 * Author： hengzwd on 2017/8/18.
 * Email：hengzwdhengzwd@qq.com
 */

public interface HotVideoItemClickListener {

    void onItemclick(MainBean.DataEntity.VideoListEntity item);
    void onCarItemClick(MainBean.DataEntity.VideoListEntity item);
    void onShareItemClick(MainBean.DataEntity.VideoListEntity item);
}
