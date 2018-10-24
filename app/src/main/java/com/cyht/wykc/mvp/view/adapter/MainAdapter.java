package com.cyht.wykc.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cyht.wykc.R;
import com.cyht.wykc.mvp.contract.Interface.BannerItemClickLiistener;
import com.cyht.wykc.mvp.contract.Interface.HotBrandItemclicklistener;
import com.cyht.wykc.mvp.contract.Interface.HotVideoItemClickListener;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.contract.Interface.LoadMoreListener;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.widget.BannerView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： hengzwd on 2017/8/21.
 * Email：hengzwdhengzwd@qq.com
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private  int videoSize;
    private Context context;
    private MainBean mainBean;
    private BannerItemClickLiistener bannerItemClickLiistener;
    private HotBrandItemclicklistener brandItemClickListener;
    private HotVideoItemClickListener hotVideoItemClickListener;
    private LoadMoreListener loadMoreListener;
    private List<String> imglist;


    public enum ITEM_TYPE {
        TYPE_VIDEO, TYPE_VIDEO_TITTLE, TYPE_BANNER, TYPE_BRAND, TYPE_FOOTER
    }

    public MainAdapter(Context context) {
        this.context = context;
        imglist = new ArrayList<>();
    }

    public MainAdapter(Context context, MainBean mainBean) {
        this.context = context;
        this.mainBean = mainBean;
        imglist = new ArrayList<>();
        videoSize = this.mainBean.getData().getVideoList().size();
    }

    public void setnewData(MainBean mainBean) {
        this.mainBean = null;
        this.mainBean = mainBean;
        if (this.mainBean != null&&this.mainBean.getData()!=null) {
            if (this.mainBean.getData().getVideoList() != null) {
                videoSize = this.mainBean.getData().getVideoList().size();
            }
            notifyDataSetChanged();
        }
    }

    public void addVideo(List<MainBean.DataEntity.VideoListEntity> list) {
        if (list != null && list.size() > 0) {
            mainBean.getData().getVideoList().addAll(list);
            videoSize+=list.size();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE.TYPE_BANNER.ordinal();
        } else if (position == 1) {
            return ITEM_TYPE.TYPE_BRAND.ordinal();
        } else if (position == 2) {
            return ITEM_TYPE.TYPE_VIDEO_TITTLE.ordinal();
        } else if (position > 2) {
            //第一页默认加载三个视频。如果为2个则没有更多视频了。关闭加载更多
            if (getItemCount() < 7) {
                return ITEM_TYPE.TYPE_VIDEO.ordinal();
            } else {
                //如果大于3个说明还有更多视频，开启加载更多
                if (position < getItemCount()-1) {
                    return ITEM_TYPE.TYPE_VIDEO.ordinal();
                } else {
                    return ITEM_TYPE.TYPE_FOOTER.ordinal();
                }
            }
        }else {
            return 0;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.TYPE_BANNER.ordinal()) {
            return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_banner, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_BRAND.ordinal()) {
            return new HotBrandViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_hotbrand, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_VIDEO_TITTLE.ordinal()) {
            return new VideoTittleViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_videotittle, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_VIDEO.ordinal()) {
            return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item_hotvideo, parent, false));
        } else if (viewType == ITEM_TYPE.TYPE_FOOTER.ordinal()) {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_foot, parent, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mainBean != null) {
            if (holder instanceof BannerViewHolder) {
//                if (mainBean.getData().getTopicList() != null && mainBean.getData().getTopicList().size() > 0) {
                    imglist.clear();
                    for (int i = 0; i < mainBean.getData().getTopicList().size(); i++) {
                        if (mainBean.getData().getTopicList().get(i).getType()<=5) {
                            imglist.add(mainBean.getData().getTopicList().get(i).getCover());
                        }
                    }
                    if (mainBean.getData().getTopicList().size()>1) {
                        ((BannerViewHolder) holder).bannerView.startLoop(true);
                    }
                    KLog.e(imglist.size());
                    ((BannerViewHolder) holder).bannerView.setImgList(imglist).setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onclick(int position) {
                            super.onclick(position);
                            bannerItemClickLiistener.onclick(mainBean.getData().getTopicList().get(position));
                        }
                    });
//                }
            } else if (holder instanceof HotBrandViewHolder) {
                ((HotBrandViewHolder) holder).recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                ((HotBrandViewHolder) holder).recyclerView.setNestedScrollingEnabled(false);
                ((HotBrandViewHolder) holder).recyclerView.setAdapter(new HotBrandAdapter(context, mainBean.getData().getBrandList()).setBrandItemClickListener(brandItemClickListener));

            } else if (holder instanceof VideoViewHolder) {
                Glide.with(context).load(mainBean.getData().getVideoList().get(position - 3).getCover()).into(((VideoViewHolder) holder).imageView);
//                ((VideoViewHolder) holder).tv_tittle.getBackground().setAlpha(102);
                ((VideoViewHolder) holder).tv_tittle.setText(mainBean.getData().getVideoList().get(position - 3).getTitle());
                KLog.e(mainBean.getData().getVideoList().get(position-3).getComment()+"");
                ((VideoViewHolder) holder).tv_comment_count.setText(mainBean.getData().getVideoList().get(position-3).getComment()+"");
                ((VideoViewHolder) holder).tv_brand.setText(mainBean.getData().getVideoList().get(position-3).getCarName());
                ((VideoViewHolder) holder).tv_play_count.setText(mainBean.getData().getVideoList().get(position-3).getCount()+"次播放");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hotVideoItemClickListener.onItemclick(mainBean.getData().getVideoList().get(position - 3));
                    }
                });
                ((VideoViewHolder) holder).tv_brand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hotVideoItemClickListener.onCarItemClick(mainBean.getData().getVideoList().get(position - 3));
                    }
                });
                ((VideoViewHolder) holder).iv_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hotVideoItemClickListener.onShareItemClick(mainBean.getData().getVideoList().get(position - 3));
                    }
                });

            }
//            else if (holder instanceof FooterViewHolder) {
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        loadMoreListener.loadmore();
//                    }
//                });
//            }
        }
    }


    @Override
    public int getItemCount() {
        if (mainBean != null) {
            if (videoSize >= 3) {
                return videoSize + 4;
            } else {
                return videoSize + 3;
            }
        } else {
            return 3;
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        private BannerView bannerView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            bannerView = (BannerView) itemView.findViewById(R.id.banner);
        }
    }

    public class HotBrandViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;

        public HotBrandViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_hot_brand);
        }
    }

    public class VideoTittleViewHolder extends RecyclerView.ViewHolder {
        public VideoTittleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tv_tittle;
        private TextView tv_play_count;
        private TextView tv_brand;
        private TextView tv_comment_count;
        private ImageView iv_share;

        public VideoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_video_cover);
            tv_tittle = (TextView) itemView.findViewById(R.id.tv_video_tittle);
            tv_play_count=(TextView) itemView.findViewById(R.id.tv_play_count);
            tv_brand=(TextView) itemView.findViewById(R.id.tv_brand);
            tv_comment_count=(TextView) itemView.findViewById(R.id.tv_comment_count);
            iv_share=(ImageView) itemView.findViewById(R.id.iv_share);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setBannerItemclickListener(BannerItemClickLiistener bannerItemClickLiistener) {
        this.bannerItemClickLiistener = bannerItemClickLiistener;
    }

    public void setBrandItemClickListener(HotBrandItemclicklistener brandItemClickListener) {
        this.brandItemClickListener = brandItemClickListener;
    }

    public void setHotVideoItemClickListener(HotVideoItemClickListener hotVideoItemClickListener) {
        this.hotVideoItemClickListener = hotVideoItemClickListener;
    }

    public void setOnloadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
