package com.cyht.wykc.mvp.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;

import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.modles.bean.CarBean;

/**
 * Author： hengzwd on 2017/7/4.
 * Email：hengzwdhengzwd@qq.com
 */

public class HotAdapter extends BaseQuickAdapter<CarBean,BaseViewHolder> {
    private ItemClickListener itemClickListener;
    public HotAdapter() {
        super(R.layout.item_history_search, null);
    }


    @Override
    protected void convert(BaseViewHolder helper, final CarBean item) {

        if (item != null) {
            helper.setText(R.id.tv_history_search, item.getName())
                    .setOnClickListener(R.id.tv_history_search, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListener.onclick(item);

                        }
                    });
        }

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
