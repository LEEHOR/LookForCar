package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.DistributorPhotoClickListener;
import com.cyht.wykc.mvp.contract.Interface.Goto4Slinstener;
import com.cyht.wykc.mvp.contract.Interface.PhoneClickListener;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.utils.Imageloader;

import org.w3c.dom.ProcessingInstruction;


/**
 * Author： hengzwd on 2017/8/30.
 * Email：hengzwdhengzwd@qq.com
 */

public class DistributorAdapter extends BaseQuickAdapter<DistributorInfoBean.DataEntity.DealerEntity, BaseViewHolder> {


    public  static final int TYPE_PIC = 0;
    public static final int TYPE_VIDEO = 1;

    private Context context;
    private PhoneClickListener phoneClickListener;
    private DistributorPhotoClickListener distributorPhotoClickListener;
    private Goto4Slinstener goto4slinstener;

    public DistributorAdapter(Context context) {
        super(R.layout.recycleview_item_distributor, null);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DistributorInfoBean.DataEntity.DealerEntity item) {
        helper
//                .setText(R.id.tv_contract_name, item.getLinkMan())
                .setText(R.id.tv_distributor_name, item.getStoreName())
                .setText(R.id.tv_phone, item.getTel() != null && !item.getTel().equals("") ? item.getTel() : "暂无")
                .setText(R.id.tv_distance, "距离" + item.getDistance() + "KM")
                .setText(R.id.tv_distributor_address, item.getLocation());

//        if (item.getUrl()!=null&&!("").equals(item.getUrl())) {
//            ((WebView)helper.getView(R.id.wv_play)).loadUrl(item.getUrl());
//        }else {
//            helper.getView(R.id.wv_play).setVisibility(View.GONE);
//        }
//        if (item.getMobile() != null&&!("").equals(item.getMobile())) {
//            setTextviewDrawableLeft((TextView)helper.getView(R.id.tv_mobile),R.mipmap.mobile);
//           ((TextView) helper.getView(R.id.tv_mobile)).setTextColor(Color.parseColor("#2fc1ff"));
//        }else {
//            setTextviewDrawableLeft((TextView)helper.getView(R.id.tv_mobile),R.mipmap.mobile_no);
//            ((TextView) helper.getView(R.id.tv_mobile)).setTextColor(Color.parseColor("#999999"));
//        }

        Imageloader.loadImage(item.getPic(), ((ImageView) helper.getView(R.id.iv_photo)), R.mipmap.distributor_no_pic);
//        Imageloader.loadImage(item.getAvatar(),(ImageView) helper.getView(R.id.iv_head_pic),R.mipmap.pcenter_user);

//        if (item.getUrl() == null||item.getUrl().equals(""))
//                helper.getView(R.id.iv_play).setVisibility(View.GONE);
        helper.setOnClickListener(R.id.fl_distributor_photo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getUrl() != null && !item.getUrl().equals(""))
                    distributorPhotoClickListener.onClick(item.getUrl(), TYPE_VIDEO);
                else
                    distributorPhotoClickListener.onClick(item.getPic(),TYPE_PIC);
            }
        });
        helper.setOnClickListener(R.id.tv_phone, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getTel() != null && !("").equals(item.getTel()))
                    phoneClickListener.onClick(item.getTel());
            }
        });
//        helper.setOnClickListener(R.id.tv_mobile, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (item.getMobile() != null&&!("").equals(item.getMobile()))
//                    phoneClickListener.onClick(item.getMobile());
//            }
//        });
        helper.setOnClickListener(R.id.tv_goto, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goto4slinstener.onclick(item.getLatitude(), item.getLongitude(), item.getStoreName());
            }
        });
    }


    public void setPhoneClickListener(PhoneClickListener phoneClickListener) {
        this.phoneClickListener = phoneClickListener;
    }

    public void setDistributorPhotoClickListener(DistributorPhotoClickListener distributorPhotoClickListener) {
        this.distributorPhotoClickListener = distributorPhotoClickListener;
    }

    public void setgoto4slinstener(Goto4Slinstener goto4slinstener) {
        this.goto4slinstener = goto4slinstener;
    }


    private void setTextviewDrawableLeft(TextView textview, int id) {
        Drawable drawable = context.getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textview.setCompoundDrawables(drawable, null, null, null);
    }
}
