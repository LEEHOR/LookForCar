package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.BrandItemClickListener;
import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.utils.Imageloader;
import com.cyht.wykc.widget.SortRecyclerView.IndexAdapter;
import com.jiang.android.lib.adapter.BaseAdapter;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersAdapter;

import java.util.List;

/**
 * Author： hengzwd on 2017/8/14.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarBrandAdapter extends BaseAdapter<BrandListBean.DataEntity.BrandListEntity, CarBrandAdapter.BrandViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder>, IndexAdapter {

    private BrandItemClickListener brandItemClickListener;
    private Context context;
    private List<BrandListBean.DataEntity.BrandListEntity> listEntities;

    public CarBrandAdapter(Context context) {
        this.context = context;
    }

    public CarBrandAdapter(Context context, List<BrandListBean.DataEntity.BrandListEntity> listEntities) {
        this.listEntities = listEntities;
        this.context = context;
        this.addAll(listEntities);
    }

    public void setNewData(List<BrandListBean.DataEntity.BrandListEntity> listEntities) {
        this.listEntities = listEntities;
        this.addAll(listEntities);
    }

    public void setOnItemClickLisenter(BrandItemClickListener brandItemClickListener) {
        this.brandItemClickListener = brandItemClickListener;
    }

    @Override
    public CarBrandAdapter.BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleview_item_brand, parent, false);
        return new BrandViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CarBrandAdapter.BrandViewHolder holder, final int position) {

//        Glide.with(context).load(listEntities.get(position).getLogo()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.image);
        Imageloader.loadImage(listEntities.get(position).getLogo(),holder.image,Imageloader.NO_DEFAULT);
        holder.text.setText(listEntities.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                brandItemClickListener.onclick(listEntities.get(position));
            }
        });
    }


    @Override
    public long getHeaderId(int position) {
        return getItem(position).getCode().charAt(0);
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((TextView) holder.itemView).setText(String.valueOf(getItem(position).getCode().charAt(0)));

    }

    public class BrandViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public BrandViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.icon_item);
            text = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = listEntities.get(i).getCode();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }
}
