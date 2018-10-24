package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： hengzwd on 2017/9/6.
 * Email：hengzwdhengzwd@qq.com
 */

public class HistoryAdapter extends BaseQuickAdapter<HistoryBean.ListEntity, BaseViewHolder>  {


    private boolean isedit;
//    private boolean isAllSelct;
    private ArrayList<HistoryBean.ListEntity> mSelects;
    private ItemClickListener itemClickListener;
//    public HistoryAdapter( boolean isedit,boolean isAllSelct) {
//        super(R.layout.collection_item, null);
//        this.isedit=isedit;
//        this.isAllSelct=isAllSelct;
//    }
    public HistoryAdapter( boolean isedit,ArrayList<HistoryBean.ListEntity> mSelects) {
        super(R.layout.collection_item, null);
        this.isedit=isedit;
        this.mSelects=mSelects;
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    //设置check是否显示
    public void setIsedit(boolean isedit)
    {
        this.isedit=isedit;
        KLog.e("isedit:"+isedit);

    }

    public void setCheck(ArrayList<HistoryBean.ListEntity> mSelects){

        this.mSelects=mSelects;
    }
//    //设置是否全部check
//    public void setIsalledit(boolean isAllSelct)
//    {
//        this.isAllSelct=isAllSelct;
//    }
    @Override
    protected void convert(final BaseViewHolder helper, final HistoryBean.ListEntity item) {

        KLog.e("covert:","00000000000000000000000");
        helper.setText(R.id.collect_item_tv_title, item.getTitle());
        helper.setText(R.id.collect_item_tv_type,"车型："+ item.getCxmc());

        Glide.with(BaseApplication.mContext).load(item.getImageurl()).into((ImageView) helper.getView(R.id.collect_item_iv_img));
//        helper.setVisible(R.id.collect_item_btn_play, true);
        helper.setVisible(R.id.collect_item_btn_select,isedit);
        helper.setBackgroundRes(R.id.collect_item_btn_select, mSelects.contains(item)?R.mipmap.selected:R.mipmap.selected_f);
        helper.setOnClickListener(R.id.collect_item_layout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onHistroryItemPlay(item,helper);
            }
        });
        helper.setOnClickListener(R.id.item_contact_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onHistroryItemDelete(item);
            }
        });

    }

}
