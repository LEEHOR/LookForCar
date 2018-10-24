package com.cyht.wykc.mvp.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.CarItemClickLinstener;
import com.cyht.wykc.mvp.modles.bean.CarListBean;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/17.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarAdapter extends BaseQuickAdapter<CarListBean.DataEntity.CarListEntity, BaseViewHolder> {

    private CarItemClickLinstener carItemClickLinstener;

    public CarAdapter() {
        super(R.layout.recycleview_item_car, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CarListBean.DataEntity.CarListEntity item) {

        helper.setText(R.id.select_car_name, item.getName());
        helper.setOnClickListener(R.id.rl_select_car, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carItemClickLinstener.onclick(item);
            }
        });

    }

    public void setCarItemClickLinstener(CarItemClickLinstener carItemClickLinstener) {
        this.carItemClickLinstener = carItemClickLinstener;
    }

    public void setData(List<CarListBean.DataEntity.CarListEntity> data) {
       this.setNewData(data);
        notifyDataSetChanged();
    }
}
