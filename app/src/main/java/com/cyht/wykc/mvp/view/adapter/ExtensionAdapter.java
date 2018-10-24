package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.modles.bean.ExtensionBean;
import com.cyht.wykc.utils.DisplayUtils;
import com.cyht.wykc.utils.Imageloader;
import com.socks.library.KLog;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author： hengzwd on 2017/11/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class ExtensionAdapter extends RecyclerView.Adapter {

    private Context context;
    private String tittle;
    private String time;
    private List<String> extensionList;
    private ExtensionBean.DataEntity dataEntity;

    public ExtensionAdapter(Context context) {
        this.context = context;
    }

    public ExtensionAdapter(Context context, ExtensionBean.DataEntity dataEntity) {
        this.context = context;
        extensionList = dataEntity.getHotArray();
        tittle = dataEntity.getHotTitle();
        time = dataEntity.getPushTime();
        this.dataEntity = dataEntity;
    }

    public void setDataEntity(ExtensionBean.DataEntity dataEntity) {
        extensionList = dataEntity.getHotArray();
        tittle = dataEntity.getHotTitle();
        time = dataEntity.getPushTime();
        this.dataEntity = dataEntity;
        KLog.e("size:"+extensionList.size());
    }

    public enum ITEM_TYPE {
        TYPE_TILLE, TYPE_IMAGE, TYPE_TEXT
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_TILLE.ordinal()) {
            return new TittleViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_extentsion_tittle, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_IMAGE.ordinal()) {
            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_extension_image, parent, false));
        } else {
            return new TextViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_extension_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (dataEntity != null) {
            if (position == 0) {
                ((TittleViewHolder) holder).tittle.setText(tittle);
            } else if (position == 1) {
                ((TittleViewHolder) holder).tittle.setText(time);
            } else {
                if (getItemViewType(position) == ITEM_TYPE.TYPE_IMAGE.ordinal()) {
                    Imageloader.loadImage(extensionList.get(position - 2), ((ImageViewHolder) holder).image, Imageloader.NO_DEFAULT);
                } else {
                    ((TextViewHolder) holder).textView.setText("\t\t" + extensionList.get(position - 2));
                }
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position < 2) {
            return ITEM_TYPE.TYPE_TILLE.ordinal();
        } else {
            if (extensionList.get(position - 2).contains("http:")) {
                return ITEM_TYPE.TYPE_IMAGE.ordinal();
            }
            return ITEM_TYPE.TYPE_TEXT.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        if (dataEntity != null) {
            return extensionList.size() + 2;
        }
        return 0;
    }


    private class TittleViewHolder extends RecyclerView.ViewHolder {
        public TextView tittle;

        public TittleViewHolder(View itemView) {
            super(itemView);
            tittle = (TextView) itemView.findViewById(R.id.tv_extension_tittle);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_extension);
        }
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_extension);
        }
    }
}
