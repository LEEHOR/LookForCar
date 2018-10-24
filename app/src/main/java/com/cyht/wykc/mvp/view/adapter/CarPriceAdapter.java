package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.modles.bean.CarPriceBean;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Author： hengzwd on 2017/8/25.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarPriceAdapter extends RecyclerView.Adapter {
    private Context context;
    private CarPriceBean carPriceBean;

    private List<Integer> typeList;

    private int stage = 0;  //TittleViewHolder 的阶段
    private int itemposition = 0;  //PriceViewHolder 属于item中的position
    public CarPriceAdapter(Context context) {
        this.context = context;
        typeList = new ArrayList<>();
    }

    public CarPriceAdapter(Context context, CarPriceBean carPriceBean) {
        this.context = context;
        this.carPriceBean = carPriceBean;
        typeList = new ArrayList<>();
    }

    public void setNewData(CarPriceBean carPriceBean) {
        this.carPriceBean = carPriceBean;
        stage = 0;
        itemposition = 0;
        notifyDataSetChanged();
    }

    public enum ITEM_TYPE {
        TYPE_TILLE, TYPE_PRICE
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_PRICE.ordinal()) {
            return new PriceViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_price, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_TILLE.ordinal()) {
            return new TittleViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_pricetittle, parent, false));
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        return typeList.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (carPriceBean != null) {
            if (typeList.get(position) == ITEM_TYPE.TYPE_TILLE.ordinal()) {
                if (position != 0) {
                    stage++;
                    itemposition = 0;
                }
                KLog.e("STAGE:"+stage+"itempositon"+itemposition);
                ((TittleViewHolder) holder).textView.setText(carPriceBean.getData().getDetail().get(stage).getEngine());
            } else if (typeList.get(position) == ITEM_TYPE.TYPE_PRICE.ordinal()) {
                KLog.e("STAGE:"+stage+"itempositon"+itemposition);
                ((PriceViewHolder) holder).tv_type.setText(carPriceBean.getData().getDetail().get(stage).getInfo().get(itemposition).getType());
                ((PriceViewHolder) holder).tv_price.setText("指导价：¥" + carPriceBean.getData().getDetail().get(stage).getInfo().get(itemposition).getPrice()+"万");
                itemposition++;
            }
        }
    }


    @Override
    public int getItemCount() {
        int itemcount = 0;
        typeList.clear();
        if (carPriceBean != null) {
            for (int i = 0; i < carPriceBean.getData().getDetail().size(); i++) {
                itemcount++;
                typeList.add(ITEM_TYPE.TYPE_TILLE.ordinal());
                for (int x = 0; x < carPriceBean.getData().getDetail().get(i).getInfo().size(); x++) {
                    itemcount++;
                    typeList.add(ITEM_TYPE.TYPE_PRICE.ordinal());
                }
            }
        }
        KLog.e("itemcount;" + itemcount);
        return itemcount;
    }


    private class TittleViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public TittleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_price_tittle);

        }
    }

    private class PriceViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_type;
        public TextView tv_price;

        public PriceViewHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

        }
    }
}
