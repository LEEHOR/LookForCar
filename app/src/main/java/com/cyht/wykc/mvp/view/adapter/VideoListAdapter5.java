package com.cyht.wykc.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.CarVideoItemClickListener;
import com.cyht.wykc.mvp.modles.bean.CarMediaInfoBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;

/**
 * Author： hengzwd on 2018/3/28.
 * Email：hengzwdhengzwd@qq.com
 */

public class VideoListAdapter5 extends BaseQuickAdapter<CarMediaInfoBean.DataEntity.GexingEntity,BaseViewHolder> {
    private CarVideoItemClickListener itemClickListener;
    public VideoListAdapter5() {
        super(R.layout.item_video_recycleview,null);
    }


    @Override
    protected void convert(BaseViewHolder helper,final CarMediaInfoBean.DataEntity.GexingEntity item) {

        if (item != null) {
            Glide.with(BaseApplication.mContext).load(item.getLogo()).into((ImageView) helper.getView(R.id.iv_video));
            helper.setText(R.id.video_tv_title, item.getName());
            helper.setText(R.id.video_time_length, item.getLength());
            helper.setOnClickListener(R.id.ll_video_list, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onclick(item.getId(), item.getName(), item.getLogo(),item.getVideoType());
                }
            });
        }
    }

    public void setItemClickListener(CarVideoItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
