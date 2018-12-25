package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.HotBrandItemclicklistener;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.utils.Imageloader;
import com.cyht.wykc.widget.CircleImageView;

import java.util.List;


/**
 * Author： hengzwd on 2017/8/18.
 * Email：hengzwdhengzwd@qq.com
 */

public class HotBrandAdapter extends RecyclerView.Adapter<HotBrandAdapter.Viewholder> {
    private List<MainBean.DataEntity.BrandListEntity> listEntities;
    private Context context;
    private Resources resources;
    private HotBrandItemclicklistener brandItemClickListener;
    private static int HOTBRANDSIZE = 15;

    public HotBrandAdapter(Context context) {
        this.context = context;
        resources = context.getResources();
    }

    public HotBrandAdapter(Context context, List<MainBean.DataEntity.BrandListEntity> listEntities) {
        this.context = context;
        this.listEntities = listEntities;
        resources = context.getResources();
    }


    public enum ITEM_TYPE {
        TYPE_ITEM, TYPE_FOOTER
    }

    public void addData(List<MainBean.DataEntity.BrandListEntity> list) {
        if (listEntities == null) {
            listEntities = list;
        } else {
            listEntities.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new Viewholder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_hotbrand_more, parent, false));
        } else {
            return new Viewholder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_hotbrand, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        if (listEntities != null && listEntities.size() != 0) {
            if (position != getItemCount() - 1) {
//                Glide.with(context).load(listEntities.get(position).getLogo()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imageView);
                Imageloader.loadImage(listEntities.get(position).getLogo(),holder.imageView,Imageloader.NO_DEFAULT);
                holder.textView.setText(listEntities.get(position).getName());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position != getItemCount() - 1) {
                        brandItemClickListener.onclick(listEntities.get(position));
                    } else {
                        brandItemClickListener.onclick(null);
                    }
                }
            });
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount()-1) {
            return ITEM_TYPE.TYPE_FOOTER.ordinal();
        } else {
            return ITEM_TYPE.TYPE_ITEM.ordinal();
        }
    }

    @Override
    public int getItemCount() {
        if (listEntities != null && listEntities.size() != 0) {
            if (listEntities.size() > HOTBRANDSIZE) {
                return HOTBRANDSIZE + 1;
            }
            return listEntities.size() + 1;
        }
        return HOTBRANDSIZE + 1;
    }

    public HotBrandAdapter setBrandItemClickListener(HotBrandItemclicklistener brandItemClickListener) {
        this.brandItemClickListener = brandItemClickListener;
        return this;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

//                public CircleImageView imageView;
        public ImageView imageView;
        public TextView textView;
        public Viewholder(View itemView) {
            super(itemView);
//            imageView = (CircleImageView) itemView.findViewById(R.id.iv_brand);
            imageView = (ImageView) itemView.findViewById(R.id.iv_brand);
            textView = (TextView) itemView.findViewById(R.id.tv_brand);
        }
    }
}
