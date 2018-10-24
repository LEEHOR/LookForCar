package com.cyht.wykc.mvp.contract.Interface;

import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.mvp.view.adapter.MainAdapter;

/**
 * Author： hengzwd on 2017/8/21.
 * Email：hengzwdhengzwd@qq.com
 */

public interface BannerItemClickLiistener {
    void onclick(MainBean.DataEntity.TopicListEntity item);
}
