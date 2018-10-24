package com.cyht.wykc.mvp.contract.Interface;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;
import com.cyht.wykc.mvp.modles.bean.MsgBean;

/**
 * Author： hengzwd on 2017/9/6.
 * Email：hengzwdhengzwd@qq.com
 */

public abstract  class ItemClickListener  {

   public   void  onHistroryItemPlay(HistoryBean.ListEntity item, BaseViewHolder helper){}
   public   void  onHistroryItemDelete(HistoryBean.ListEntity item){}

   public   void  onCollectionItemSelect(CollectionBean.ListEntity item, BaseViewHolder helper){}
   public   void  onCollectionItemDelete(CollectionBean.ListEntity item){}

   public void onclick(CarBean carmodel){}
   public void onclick(String id,String name,String logo ){}
   public void onclick(int position){}
   public void onReplyclick(MsgBean.DataEntity.ListEntity entity){}
   public void onSystemclick(MsgBean.DataEntity.ListEntity entity){}


}
