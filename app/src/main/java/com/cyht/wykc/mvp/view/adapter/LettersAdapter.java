package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.os.TokenWatcher;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.platform.comapi.map.K;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.modles.bean.MsgBean;
import com.cyht.wykc.utils.DateUtils;
import com.socks.library.KLog;

import java.util.List;

/**
 * Author： hengzwd on 2017/9/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class LettersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;

    private List<MsgBean.DataEntity.ListEntity> msglist;
    private ItemClickListener itemClickListener;
    public LettersAdapter(Context context, List<MsgBean.DataEntity.ListEntity> msglist) {
        this.context = context;
        this.msglist = msglist;
    }

    public LettersAdapter(Context context) {
        this.context = context;
    }

    public enum ITEM_TYPE {
        TYPE_SYSTEM, TYPE_REPLY, TYPE_FABULOUS, TYPE_FOLLOW, TYPE_FOOTER
    }

    public void setMsglist(List<MsgBean.DataEntity.ListEntity> msglist) {
        this.msglist = msglist;
    }

    public void addMsglist(List<MsgBean.DataEntity.ListEntity> msglist) {
        if (this.msglist != null) {
            this.msglist.addAll(msglist);
        } else {
            this.msglist = msglist;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_SYSTEM.ordinal()) {
            return new SystemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_systeminfo, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_REPLY.ordinal()) {
            return new ReplyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_reply, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new FootViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));
        } else {
            return null;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (msglist != null && msglist.size() > 0) {
            if (holder instanceof SystemViewHolder) {
                ((SystemViewHolder) holder).tvTime.setText(DateUtils.ChangeYMDHMSToYMD(msglist.get(position).getCreateTimeStr()));
                ((SystemViewHolder) holder).tvContent.setText(msglist.get(position).getBody());
                ((SystemViewHolder) holder).tvIsread.setVisibility(msglist.get(position).getIsRead()==0?View.VISIBLE:View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onSystemclick(msglist.get(position));
                        ((SystemViewHolder) holder).tvIsread.setVisibility(View.GONE);
                    }
                });
            } else if (holder instanceof ReplyViewHolder) {

                ((ReplyViewHolder) holder).tvTime.setText(DateUtils.ChangeYMDHMSToYMD(msglist.get(position).getCreateTimeStr()));
                ((ReplyViewHolder) holder).tvname.setText(msglist.get(position).getName() + "回复了你：");
                ((ReplyViewHolder) holder).tvContent.setText(msglist.get(position).getContent());
                ((ReplyViewHolder) holder).tvIsread.setVisibility(msglist.get(position).getIsRead()==0?View.VISIBLE:View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        KLog.e("onClick");
                        itemClickListener.onReplyclick(msglist.get(position));
                        ((ReplyViewHolder) holder).tvIsread.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }
    @Override
    public int getItemViewType(int position) {
        if (msglist != null && msglist.size() != 0) {
            if (msglist.size() >=10) {
                if (position + 1 == getItemCount()) {
                    return ITEM_TYPE.TYPE_FOOTER.ordinal();
                }
            }
            if (msglist.get(position).getType() == 0) {
                return ITEM_TYPE.TYPE_SYSTEM.ordinal();
            } else if (msglist.get(position).getType()<=5&&msglist.get(position).getType()>0) {
                return ITEM_TYPE.TYPE_REPLY.ordinal();
            } else {
                return ITEM_TYPE.TYPE_FABULOUS.ordinal();
            }
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        if (msglist != null && msglist.size() != 0) {
            if (msglist.size() >= 10) {
                return msglist.size() + 1;
            }
            return msglist.size();
        }
        return 0;
    }


    public class SystemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTime;
        public TextView tvContent;
        public TextView tvIsread;
        public SystemViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_system_text);
            tvTime = (TextView) itemView.findViewById(R.id.tv_system_time);
            tvIsread = (TextView) itemView.findViewById(R.id.tv_isread);

        }
    }

    public class ReplyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvname;
        public TextView tvTime;
        public TextView tvContent;
        public TextView tvIsread;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_reply_text);
            tvTime = (TextView) itemView.findViewById(R.id.tv_date);
            tvname = (TextView) itemView.findViewById(R.id.tv_user_reply);
            tvIsread = (TextView) itemView.findViewById(R.id.tv_isread);

        }
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    public class FabulousViewHolder extends RecyclerView.ViewHolder {
        public FabulousViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FollowViewHolder extends RecyclerView.ViewHolder {
        public FollowViewHolder(View itemView) {
            super(itemView);
        }
    }
}
