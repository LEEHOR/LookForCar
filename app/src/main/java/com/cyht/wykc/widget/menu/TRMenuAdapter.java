package com.cyht.wykc.widget.menu;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.utils.PreferenceUtils;


import java.util.List;

public class TRMenuAdapter extends RecyclerView.Adapter<TRMenuAdapter.TRMViewHolder> {
    private Context mContext;
    private List<MenuItem> menuItemList;
    private boolean showIcon;
    private TopRightMenu mTopRightMenu;
    private TopRightMenu.OnMenuItemClickListener onMenuItemClickListener;
    private int textType;

    public TRMenuAdapter(Context context, TopRightMenu topRightMenu, List<MenuItem> menuItemList, boolean show) {
        this.mContext = context;
        this.mTopRightMenu = topRightMenu;
        this.menuItemList = menuItemList;
        this.showIcon = show;
    }

    public void  setTextType(int type)
    {
        this.textType=type;
    }
    public void setData(List<MenuItem> data){
        menuItemList = data;
        notifyDataSetChanged();
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
        notifyDataSetChanged();
    }

    @Override
    public TRMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (textType== Constants.TEXT_TYPE_MID) {
             view = LayoutInflater.from(mContext).inflate(R.layout.trm_item_popup_menu_list, parent, false);
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_search_layout, parent, false);
        }
        return new TRMViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TRMViewHolder holder, final int position) {
        final MenuItem menuItem = menuItemList.get(position);
        if (textType==Constants.TEXT_TYPE_MID) {
            if (showIcon) {
                holder.icon.setVisibility(View.VISIBLE);
                int resId = menuItem.getIcon();
                holder.icon.setImageResource(resId < 0 ? 0 : resId);
            } else {
                holder.icon.setVisibility(View.GONE);
            }
            holder.text.setText(menuItem.getText());
            if (menuItem.getId().equals(Constants.carid)) {
                holder.text.setTextColor(ContextCompat.getColor(mContext, R.color.cyht_title_text_color));
            } else {
                holder.text.setTextColor(ContextCompat.getColor(mContext, R.color.cyht_content_color));
            }
            holder.container.setBackgroundResource(R.drawable.cyht_item_selector);

        }else {
            holder.text.setText(menuItem.getText());
        }
//        final int pos = holder.getAdapterPosition();
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuItemClickListener != null) {
                    mTopRightMenu.dismiss();
                    onMenuItemClickListener.onMenuItemClick(position);
                }
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return menuItemList == null ? 0 : menuItemList.size();
    }

    class TRMViewHolder extends RecyclerView.ViewHolder{
        ViewGroup container;
        ImageView icon;
        TextView text;

        TRMViewHolder(View itemView) {
            super(itemView);
            container = (ViewGroup) itemView;
            if (textType==Constants.TEXT_TYPE_MID) {
                icon = (ImageView) itemView.findViewById(R.id.trm_menu_item_icon);
            }
            text = (TextView) itemView.findViewById(R.id.trm_menu_item_text);
        }
    }

    public void setOnMenuItemClickListener(TopRightMenu.OnMenuItemClickListener listener){
        this.onMenuItemClickListener = listener;
    }
}
