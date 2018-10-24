package com.cyht.wykc.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;
import com.cyht.wykc.mvp.view.base.BaseApplication;

import java.util.ArrayList;

/**
 * Author： hengzwd on 2017/9/6.
 * Email：hengzwdhengzwd@qq.com
 */

public class CollectionAdapter  extends BaseQuickAdapter<CollectionBean.ListEntity, BaseViewHolder> {


    private boolean isedit;
//    private boolean isAllSelct;
    private ArrayList<CollectionBean.ListEntity> mSelects;
    private ItemClickListener itemClickListener;
//    public CollectionAdapter( boolean isedit,boolean isAllSelct) {
//        super(R.layout.collection_item, null);
//        this.isedit=isedit;
//        this.isAllSelct=isAllSelct;
//    }

    public CollectionAdapter( boolean isedit,ArrayList<CollectionBean.ListEntity> mSelects) {
        super(R.layout.collection_item, null);
        this.isedit=isedit;
        this.mSelects=mSelects;
    }



    //设置是否全部check
//    public void setIsalledit(boolean isAllSelct)
//    {
//        this.isAllSelct=isAllSelct;
//    }

    public void setCheck(ArrayList<CollectionBean.ListEntity> mSelects){

        this.mSelects=mSelects;
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    public void setIsedit(boolean isedit)
    {
        this.isedit=isedit;
    }
    @Override
    protected void convert(final BaseViewHolder helper, final CollectionBean.ListEntity item) {
        helper.setText(R.id.collect_item_tv_title, item.getTitle());
        helper.setText(R.id.collect_item_tv_type, "车型："+ item.getCxmc());

        Glide.with(BaseApplication.mContext).load(item.getImageurl()).into((ImageView) helper.getView(R.id.collect_item_iv_img));
        helper.setBackgroundRes(R.id.collect_item_btn_select, mSelects.contains(item)?R.mipmap.selected:R.mipmap.selected_f);

        helper.setVisible(R.id.collect_item_btn_select,isedit);
        helper.setOnClickListener(R.id.collect_item_layout, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onCollectionItemSelect(item,helper);
            }
        });
        helper.setOnClickListener(R.id.item_contact_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onCollectionItemDelete(item);
            }
        });
    }

}
