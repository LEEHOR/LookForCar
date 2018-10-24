package com.cyht.wykc.mvp.view.main;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cyht.wykc.R;
import com.cyht.wykc.common.CYHTConstantsUrl;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.Interface.BannerItemClickLiistener;
import com.cyht.wykc.mvp.contract.Interface.HotBrandItemclicklistener;
import com.cyht.wykc.mvp.contract.Interface.HotVideoItemClickListener;
import com.cyht.wykc.mvp.contract.Interface.LoadMoreListener;
import com.cyht.wykc.mvp.contract.main.MainContract;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.mvp.presenter.main.MainFragmentPresenter;
import com.cyht.wykc.mvp.view.CarSearchActivity;
import com.cyht.wykc.mvp.view.ExtensionActivity;
import com.cyht.wykc.mvp.view.LoginActivity;
import com.cyht.wykc.mvp.view.MainActivity;
import com.cyht.wykc.mvp.view.TweetActivity;
import com.cyht.wykc.mvp.view.adapter.MainAdapter;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.mvp.view.carselect.VideoListActivity;
import com.cyht.wykc.mvp.view.distributor.DistributorVideoActivity;
import com.cyht.wykc.mvp.view.setting.SettingActivity;
import com.cyht.wykc.mvp.view.videoplay.TBSWebView;
import com.cyht.wykc.utils.BitmapUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.ShareprefrenceStackUtils;
import com.cyht.wykc.widget.MyTittleBar.SearchTittleBar;
import com.cyht.wykc.widget.ShareDialog;
import com.socks.library.KLog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.util.Const;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author： hengzwd on 2017/8/7.
 * Email：hengzwdhengzwd@qq.com
 */

public class MainFragment extends BaseFragment<MainContract.Presenter> implements ShareDialog.OnShareIndexClickListener, MainContract.View {

    @BindView(R.id.search_bar)
    SearchTittleBar searchBar;
    @BindView(R.id.main_swipe)
    SwipeRefreshLayout mainSwipe;
    @BindView(R.id.rv_main)
    RecyclerView rvmain;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private int[] imgs = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d};
    private List<MainBean.DataEntity.VideoListEntity> videoList;
    private LinearLayoutManager linearLayoutManager;
    private int distance = 0;
    private int maxDistance = 200;
    private MainAdapter mainAdapter;
    private int lastVisibleItemPosition;
    private boolean isLoading;
    private Bitmap selectCoverBitmap;
    private String selectCoverUrl;
    private String selectTittle;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void showLoading() {
        mainSwipe.setRefreshing(true);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainFragmentPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {
        searchBar.setPadding(searchBar.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), searchBar.getPaddingRight(), searchBar.getPaddingBottom());
//        searchBar.getTvContent().setText("武汉");
//        searchBar.getTvContent().setTextColor(Color.parseColor("#666666"));
        searchBar.getSearch().setHintTextColor(Color.parseColor("#999999"));
        searchBar.getSearch().setBackgroundResource(R.drawable.bg_search_bar_two);
        searchBar.getSearch().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_mActivity, CarSearchActivity.class);
                _mActivity.startActivity(intent);
            }
        });
        mainSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainSwipe.post(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.start();
                    }
                });
            }
        });
        rvmain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == mainAdapter.getItemCount() && videoList.size() >= 1) {
                    if (!isLoading) {
                        rvmain.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.loadMore();
                                isLoading = true;
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (!rvmain.canScrollVertically(-1)) {
                    distance = 0;
                } else {
                    distance += dy;
                }
                final float percent = (distance > maxDistance ? maxDistance : distance) * 1f / maxDistance;
//                searchBar.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        searchBar.getBackground().setAlpha((int) (255 * percent));
//                    }
//                });
//
//                if (distance >= maxDistance * 0.5) {
//                    searchBar.getTvContent().setTextColor(Color.parseColor("#666666"));
//                    searchBar.getSearch().setHintTextColor(Color.parseColor("#999999"));
//                    searchBar.getSearch().setBackgroundResource(R.drawable.bg_search_bar_two);
//                } else {
//                    searchBar.getTvContent().setTextColor(Color.parseColor("#ffffff"));
//                    searchBar.getSearch().setHintTextColor(Color.parseColor("#666666"));
//                    searchBar.getSearch().setBackgroundResource(R.drawable.bg_search_bar);
//                }
                if (linearLayoutManager.findLastVisibleItemPosition() > 8) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvmain.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void initData() {
        videoList = new ArrayList<>();
        mainAdapter = new MainAdapter(BaseApplication.mContext);
        linearLayoutManager = new LinearLayoutManager(BaseApplication.mContext);
        rvmain.setLayoutManager(linearLayoutManager);
        rvmain.setAdapter(mainAdapter);
        mainAdapter.setBannerItemclickListener(new BannerItemClickLiistener() {
            @Override
            public void onclick(final MainBean.DataEntity.TopicListEntity item) {
                if (item.getType() == 1) {
                    Intent intent = new Intent(_mActivity, TBSWebView.class);
                    intent.putExtra("videoid", item.getVideo());
                    intent.putExtra("tittle", item.getVideoName());
                    intent.putExtra("cover", item.getCover());
                    intent.putExtra("videoType",item.getVideoType());
                    _mActivity.startActivity(intent);
                } else if (item.getType() == 2) {
                    Observable.just(1)
                            .observeOn(Schedulers.computation())
                            .subscribe(new Action1<Integer>() {
                                @Override
                                public void call(Integer integer) {
                                    ShareprefrenceStackUtils.getInstance().add(new CarBean(item.getCar(), item.getCarName()));
                                }
                            });

                    Intent intent = new Intent(_mActivity, VideoListActivity.class);
                    intent.putExtra(Constants.CAR_ID, item.getCar());
                    intent.putExtra(Constants.CAR_NAME, item.getCarName());
                    _mActivity.startActivity(intent);
                } else {
                    Intent intent = new Intent(_mActivity, TweetActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("type", item.getType());
                    intent.putExtra("videoType",item.getVideoType());
                    _mActivity.startActivity(intent);
                }
            }
        });
        mainAdapter.setBrandItemClickListener(new HotBrandItemclicklistener() {

            @Override
            public void onclick(MainBean.DataEntity.BrandListEntity entity) {
                if (entity != null) {
                    EventBus.getDefault().postSticky(new EventData(Constants.MAINFRAGMENT, Constants.CARBRANDFRAGMENT, entity));
                }
                ((MainActivity) _mActivity).showFragment(1);
            }
        });
        mainAdapter.setHotVideoItemClickListener(new HotVideoItemClickListener() {

            @Override
            public void onItemclick(MainBean.DataEntity.VideoListEntity item) {
                Intent intent = new Intent(_mActivity, TBSWebView.class);
                intent.putExtra("videoid", item.getId());
                intent.putExtra("tittle", item.getTitle());
                intent.putExtra("cover", item.getCover());
                intent.putExtra("videoType",item.getVideoType());
                _mActivity.startActivity(intent);
            }

            @Override
            public void onCarItemClick(MainBean.DataEntity.VideoListEntity item) {
                Intent intent = new Intent(_mActivity, VideoListActivity.class);
                intent.putExtra(Constants.CAR_ID, item.getCar());
                intent.putExtra(Constants.CAR_NAME, item.getCarName());
                _mActivity.startActivity(intent);
            }

            @Override
            public void onShareItemClick(MainBean.DataEntity.VideoListEntity item) {
                if (!PreferenceUtils.contains(BaseApplication.mContext, Constants.USERNAME)) {
                    Intent intent = new Intent(_mActivity, LoginActivity.class);
                    _mActivity.startActivity(intent);
                } else {
                    selectCoverUrl = item.getCover();
                    selectTittle = item.getTitle();
                    Glide.with(BaseApplication.mContext).load(selectCoverUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            selectCoverBitmap = resource;
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            KLog.e(e.getMessage().toString());
                        }
                    });

                    showShare();
                }
            }

        });
        mainAdapter.setOnloadMoreListener(new LoadMoreListener() {
            @Override
            public void loadmore() {
                mPresenter.loadMore();
            }
        });
        mPresenter.start();
    }


    private void showShare() {
        new ShareDialog.Builder(_mActivity)
                .setCanCancel(true)
                .setShareIndexClickListener(this)
                .create()
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onRequestMainSuccess(MainBean mainBean) {
//        fab.setVisibility(View.GONE);
        videoList = mainBean.getData().getVideoList();
        isLoading = false;
        mainSwipe.setRefreshing(false);
        mainAdapter.setnewData(mainBean);
    }

    @Override
    public void onRequestMainFailue(Throwable t) {
        isLoading = false;
        mainSwipe.setRefreshing(false);
    }

    @Override
    public void onloadmoreSuccess(List<MainBean.DataEntity.VideoListEntity> list) {
        isLoading = false;
        if (list.size() == 0) {
            //之后没有数据了，ui怎么显示
            mainAdapter.notifyItemRemoved(mainAdapter.getItemCount());
            Toast.makeText(_mActivity, "没有更多热门视频了", Toast.LENGTH_SHORT).show();

        } else {
            videoList.addAll(list);
            mainAdapter.addVideo(list);
            mainAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onloadmoreFailue(Throwable t) {
        if (t != null) {
            if (t instanceof NetworkErrorException) {
                Toast.makeText(_mActivity, "网络不给力呀", Toast.LENGTH_SHORT).show();
            } else {
                KLog.e("throwable:" + t.getMessage());
            }
        }
        mainAdapter.notifyItemRemoved(mainAdapter.getItemCount());
        isLoading = false;
    }

    @Override
    public void onClick(int index) {
        UMImage image;
        if (selectCoverBitmap != null) {
            image = new UMImage(_mActivity, BitmapUtils.ImageCompress(selectCoverBitmap));
        } else {
            image = new UMImage(_mActivity, selectCoverUrl);
        }
        ShareAction shareAction = new ShareAction(_mActivity)
                .withTitle(getString(R.string.app_name))
                .withText(selectTittle)
                .withMedia(image)
                .withTargetUrl(CYHTConstantsUrl.SHARE_URL + selectCoverUrl)//分享后的url地址
                .setCallback(umShareListener);
        switch (index) {
            case ShareDialog.INDEX_WX:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                break;
            case ShareDialog.INDEX_CYCLE:
                ShareAction shareAction0 = new ShareAction(_mActivity)
                        .withTitle(selectTittle)
                        .withText(selectTittle)
                        .withMedia(image)
                        .withTargetUrl(CYHTConstantsUrl.SHARE_URL + selectCoverUrl)
                        .setCallback(umShareListener);
                shareAction0.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                shareAction0.share();
                break;
            case ShareDialog.INDEX_QQ:
                shareAction.setPlatform(SHARE_MEDIA.QQ);
                break;
            case ShareDialog.INDEX_SPACE:
                shareAction.setPlatform(SHARE_MEDIA.QZONE);
                break;
            case ShareDialog.INDEX_Wb:
                shareAction.setPlatform(SHARE_MEDIA.SINA);
                break;
        }
        shareAction.share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(_mActivity, " 分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(_mActivity, " 分享失败", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(TBSWebView.this, " 分享取消", Toast.LENGTH_SHORT).show();
        }
    };

}
