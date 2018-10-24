package com.cyht.wykc.mvp.view.carselect;

import android.accounts.NetworkErrorException;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.Interface.CarVideoItemClickListener;
import com.cyht.wykc.mvp.contract.Interface.OnDismissListener;
import com.cyht.wykc.mvp.contract.carselect.VideoListContract;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.CarMediaInfoBean;
import com.cyht.wykc.mvp.modles.bean.CarPriceBean;
import com.cyht.wykc.mvp.presenter.carselect.VideoListPresenter;
import com.cyht.wykc.mvp.view.adapter.CarPriceAdapter;
import com.cyht.wykc.mvp.view.adapter.SpaceItemDecoration;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter0;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter1;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter2;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter3;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter4;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter5;
import com.cyht.wykc.mvp.view.adapter.VideoListAdapter6;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.distributor.DistributorActivity;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.widget.menu.MenuItem;
import com.cyht.wykc.widget.menu.TopRightMenu;
import com.cyht.wykc.mvp.view.videoplay.TBSWebView;
import com.cyht.wykc.utils.DensityUtils;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.ShareprefrenceStackUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.cyht.wykc.widget.UnConnectView;
import com.socks.library.KLog;


import java.util.List;


import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Author： hengzwd on 2017/8/22.
 * Email：hengzwdhengzwd@qq.com
 */

public class VideoListActivity extends BaseActivity<VideoListContract.Presenter> implements VideoListContract.View {
    @BindView(R.id.iv_carphoto)
    ImageView ivCarphoto;
    @BindView(R.id.tb_tittle)
    NormalTittleBar tittlebar;
    @BindView(R.id.iv_zhizaogongyi)
    ImageView ivZhizaogongyi;
    @BindView(R.id.tv_gongyi_none)
    TextView tvGongyiNone;
    @BindView(R.id.rv_zhizaogongyi)
    RecyclerView rv_gongyi;
    @BindView(R.id.tv_jingtaijiexi)
    ImageView tvJingtaijiexi;
    @BindView(R.id.tv_jiexi_none)
    TextView tvJiexiNone;
    @BindView(R.id.rv_jingtaijiexi)
    RecyclerView rv_jiexi;
    @BindView(R.id.iv_dongtaixinshang)
    ImageView ivDongtaixinshang;
    @BindView(R.id.tv_xinshang_none)
    TextView tvXinshangNone;
    @BindView(R.id.rv_dongtaixinshang)
    RecyclerView rv_xinshang;
    @BindView(R.id.iv_caozuoshuoming)
    ImageView ivCaozuoshuoming;
    @BindView(R.id.tv_shuoming_none)
    TextView tvShuomingNone;
    @BindView(R.id.rv_caozuoshuoming)
    RecyclerView rv_shuoming;
    @BindView(R.id.iv_gukepingshuo)
    ImageView ivGukepingshuo;
    @BindView(R.id.tv_pingshuo_none)
    TextView tvPingshuoNone;
    @BindView(R.id.rv_gukepingshuo)
    RecyclerView rv_pingshuo;
    @BindView(R.id.iv_gexinggaizhuang)
    ImageView ivGeXinggaizhaung;
    @BindView(R.id.tv_gaizhuang_none)
    TextView tvGaiZhuangNone;
    @BindView(R.id.rv_gexinggaizhuang)
    RecyclerView rv_gaizhuang;
    @BindView(R.id.iv_baoyangweixiu)
    ImageView ivBaoYangweixiu;
    @BindView(R.id.tv_weixiu_none)
    TextView tvBaoYangweixiu;
    @BindView(R.id.rv_baoyangweixiu)
    RecyclerView rv_weixiu;
    @BindView(R.id.unconnect)
    UnConnectView unconnect;
    @BindView(R.id.tbs_swipe)
    SwipeRefreshLayout tbsSwipe;
    @BindView(R.id.iv_car_price)
    TextView ivCarPrice;
    @BindView(R.id.rv_price)
    RecyclerView rvPrice;
    @BindView(R.id.ll_videolist_top)
    LinearLayout llVideolistTop;
    @BindView(R.id.ll_videolist_down)
    LinearLayout llVideolistDown;
    @BindView(R.id.mirror_small)
    View mirrorSmall;
    @BindView(R.id.mirror_big)
    View mirrorBig;

    private VideoListAdapter0 madapter;
    private VideoListAdapter1 nadapter;
    private VideoListAdapter2 oadapter;
    private VideoListAdapter3 padapter;
    private VideoListAdapter4 qadapter;
    private VideoListAdapter5 sadapter;
    private VideoListAdapter6 tadapter;
    private CarPriceAdapter radapter;

    private LinearLayoutManager mlinearlayoutmanager;
    private LinearLayoutManager nlinearlayoutmanager;
    private LinearLayoutManager olinearlayoutmanager;
    private LinearLayoutManager plinearlayoutmanager;
    private LinearLayoutManager rlinearlayoutmanager;
    private LinearLayoutManager qlinearlayoutmanager;
    private LinearLayoutManager slinearlayoutmanager;
    private LinearLayoutManager tlinearlayoutmanager;
    private TopRightMenu mTopRightMenu;
    private List<MenuItem> menuItemList;


    @Override
    public void showLoading() {
        tbsSwipe.setRefreshing(true);

    }

    @Override
    public VideoListContract.Presenter createPresenter() {
        return new VideoListPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_videolist;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            Constants.carid = intent.getExtras().getString(Constants.CAR_ID);
            tittlebar.getTvTittle().setText(intent.getExtras().getString(Constants.CAR_NAME));
            mPresenter.requestCarMedias(Constants.carid);
        }
        tittlebar.setPadding(tittlebar.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tittlebar.getPaddingRight(), tittlebar.getPaddingBottom());
        tittlebar.getTvTittle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuItemList != null) {
                    showCarMenu();
                } else {
                    mPresenter.requestCheXing(Constants.carid);
                }
            }
        });
        tittlebar.getRightIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动经销商页面
                Intent intent1 = new Intent(VideoListActivity.this, DistributorActivity.class);
                startActivity(intent1);
            }
        });
        tittlebar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        tbsSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestCarMedias(Constants.carid);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);//数据保存
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);//数据恢复
    }

    @Override
    public void initData() {
        mlinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        mlinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        nlinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        nlinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        olinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        olinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        plinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        plinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        rlinearlayoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        qlinearlayoutmanager= new LinearLayoutManager(BaseApplication.mContext);
        qlinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        slinearlayoutmanager= new LinearLayoutManager(BaseApplication.mContext);
        slinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tlinearlayoutmanager= new LinearLayoutManager(BaseApplication.mContext);
        tlinearlayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
        madapter = new VideoListAdapter0();
        nadapter = new VideoListAdapter1();
        oadapter = new VideoListAdapter2();
        padapter = new VideoListAdapter3();
        qadapter = new VideoListAdapter4();
        sadapter = new VideoListAdapter5();
        tadapter = new VideoListAdapter6();
        radapter = new CarPriceAdapter(BaseApplication.mContext);
        rv_gongyi.setLayoutManager(mlinearlayoutmanager);
        rv_jiexi.setLayoutManager(nlinearlayoutmanager);
        rv_xinshang.setLayoutManager(olinearlayoutmanager);
        rv_shuoming.setLayoutManager(plinearlayoutmanager);
        rv_pingshuo.setLayoutManager(qlinearlayoutmanager);
        rvPrice.setLayoutManager(rlinearlayoutmanager);
        rv_gaizhuang.setLayoutManager(slinearlayoutmanager);
        rv_weixiu.setLayoutManager(tlinearlayoutmanager);
        rv_gongyi.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_jiexi.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_xinshang.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_shuoming.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_pingshuo.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_gaizhuang.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_weixiu.addItemDecoration(new SpaceItemDecoration(DensityUtils.dp2px(BaseApplication.mContext, 6)));
        rv_gongyi.setAdapter(madapter);
        rv_jiexi.setAdapter(nadapter);
        rv_xinshang.setAdapter(oadapter);
        rv_shuoming.setAdapter(padapter);
        rv_pingshuo.setAdapter(qadapter);
        rvPrice.setAdapter(radapter);
        rv_gaizhuang.setAdapter(sadapter);
        rv_weixiu.setAdapter(tadapter);
        madapter.setItemClickListener(itemClickListener);
        nadapter.setItemClickListener(itemClickListener);
        oadapter.setItemClickListener(itemClickListener);
        padapter.setItemClickListener(itemClickListener);
        qadapter.setItemClickListener(itemClickListener);
        sadapter.setItemClickListener(itemClickListener);
        tadapter.setItemClickListener(itemClickListener);
    }

    CarVideoItemClickListener itemClickListener = new CarVideoItemClickListener() {
        @Override
        public void onclick(String id, String name, String logo,String videoType) {
            Intent intent = new Intent(VideoListActivity.this, TBSWebView.class);
            intent.putExtra("videoid", id);
            intent.putExtra("tittle", name);
            intent.putExtra("cover", logo);
            intent.putExtra("videoType",Integer.parseInt(videoType));
            intent.putExtra("from",Constants.VIDEOLISTACTIVITY);
            startActivity(intent);
        }
    };

    private void clearRecyclerview(BaseQuickAdapter adapter) {
        adapter.setNewData(null);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onRequestMediasFailure(Throwable t) {
        tbsSwipe.post(new Runnable() {
            @Override
            public void run() {
                tbsSwipe.setRefreshing(false);
            }
        });
        if (!NetUtil.checkNetWork(BaseApplication.mContext)) {
            unconnect.show();
        } else {
            unconnect.close();
        }
        if (t != null) {
            if (t instanceof NetworkErrorException) {
                Toast.makeText(VideoListActivity.this, "网络不给力呀", Toast.LENGTH_SHORT).show();
            } else {
                KLog.e("throwable:" + t.getMessage());
            }
        }
    }

    @Override
    public void onRequestMediasSuccess(CarMediaInfoBean carMediaInfoBean) {
        unconnect.close();
        tbsSwipe.post(new Runnable() {
            @Override
            public void run() {
                tbsSwipe.setRefreshing(false);
            }
        });
        tittlebar.getTvTittle().setText(carMediaInfoBean.getData().getName());
        if (carMediaInfoBean.getData().getGuidePrice() == null || carMediaInfoBean.getData().getGuidePrice().equals("")) {
            ivCarPrice.setText("该车暂无官方指导价格");
            ivCarPrice.setCompoundDrawables(null, null, null, null);
            ivCarPrice.setOnClickListener(null);
            rvPrice.setVisibility(View.GONE);
            mirrorSmall.setVisibility(View.GONE);
        } else {
            setTextviewDrawableRight(ivCarPrice, R.mipmap.arrow_down);
            ivCarPrice.setText("官方指导价：" + carMediaInfoBean.getData().getGuidePrice()+"万");
            ivCarPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rvPrice.getVisibility() == View.VISIBLE) {
                        revealClose();
                    } else {
                        revealOpen();
                    }
                }
            });
        }
        Glide.with(BaseApplication.mContext).load(carMediaInfoBean.getData().getImg()).diskCacheStrategy(DiskCacheStrategy.RESULT).into(ivCarphoto);
        if (carMediaInfoBean.getData().getZhizao().size() > 0) {
            madapter.setNewData(carMediaInfoBean.getData().getZhizao());
            madapter.notifyDataSetChanged();
            tvGongyiNone.setVisibility(View.GONE);
        } else {
            clearRecyclerview(madapter);
            tvGongyiNone.setVisibility(View.VISIBLE);
        }
        if (carMediaInfoBean.getData().getJingtai().size() > 0) {
            nadapter.setNewData(carMediaInfoBean.getData().getJingtai());
            nadapter.notifyDataSetChanged();
            tvJiexiNone.setVisibility(View.GONE);
        } else {
            clearRecyclerview(nadapter);
            tvJiexiNone.setVisibility(View.VISIBLE);
        }
        if (carMediaInfoBean.getData().getDongtai().size() > 0) {
            oadapter.setNewData(carMediaInfoBean.getData().getDongtai());
            oadapter.notifyDataSetChanged();
            tvXinshangNone.setVisibility(View.GONE);
        } else {
            clearRecyclerview(oadapter);
            tvXinshangNone.setVisibility(View.VISIBLE);
        }
        if (carMediaInfoBean.getData().getCaozuo().size() > 0) {
            padapter.setNewData(carMediaInfoBean.getData().getCaozuo());
            padapter.notifyDataSetChanged();
            tvShuomingNone.setVisibility(View.GONE);
        } else {
            clearRecyclerview(padapter);
            tvShuomingNone.setVisibility(View.VISIBLE);
        }
        if (carMediaInfoBean.getData().getGuke().size() > 0) {
            KLog.e("carMediaInfoBean.getData().getGuke().size()",carMediaInfoBean.getData().getGuke().size()+"");
            qadapter.setNewData(carMediaInfoBean.getData().getGuke());
            qadapter.notifyDataSetChanged();
            tvPingshuoNone.setVisibility(View.GONE);
        } else {
            clearRecyclerview(qadapter);
            tvPingshuoNone.setVisibility(View.VISIBLE);
        }
        if (carMediaInfoBean.getData().getGexing().size() > 0) {
            KLog.e("carMediaInfoBean.getData().getGuke().size()",carMediaInfoBean.getData().getGexing().size()+"");
            sadapter.setNewData(carMediaInfoBean.getData().getGexing());
            sadapter.notifyDataSetChanged();
            tvGaiZhuangNone.setVisibility(View.GONE);
        } else {
            clearRecyclerview(sadapter);
            tvGaiZhuangNone.setVisibility(View.VISIBLE);
        }
        if (carMediaInfoBean.getData().getBaoyang().size() > 0) {
            KLog.e("carMediaInfoBean.getData().getGuke().size()",carMediaInfoBean.getData().getBaoyang().size()+"");
            tadapter.setNewData(carMediaInfoBean.getData().getBaoyang());
            tadapter.notifyDataSetChanged();
            tvBaoYangweixiu.setVisibility(View.GONE);
        } else {
            clearRecyclerview(tadapter);
            tvBaoYangweixiu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestCarPriceSuccess(CarPriceBean carPriceBean) {
        KLog.e("onRequestCarPriceSuccess");
        radapter.setNewData(carPriceBean);
    }

    @Override
    public void onRequestCarPriceFailure(Throwable t) {

    }

    @Override
    public void onRequestChexingSuccess(List<MenuItem> menuItems) {
        if (menuItemList != null) {
            menuItemList.clear();
            menuItemList.addAll(menuItems);
        } else {
            menuItemList = menuItems;
        }
    }

    @Override
    public void onRequestChexingFailue(Throwable t) {
    }


    private void revealClose() {
        rvPrice.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                    rvPrice.setVisibility(View.GONE);
                    mirrorSmall.setVisibility(View.GONE);
                    return;
                }
                int cx = (rvPrice.getLeft() + rvPrice.getRight()) / 2;
                int cy = 0;
                int w = rvPrice.getWidth();
                int h = rvPrice.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);

                Animator anim = ViewAnimationUtils.createCircularReveal(rvPrice, cx, cy, finalRadius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setTextviewDrawableRight(ivCarPrice, R.mipmap.arrow_down);
                        rvPrice.setVisibility(View.GONE);
                        mirrorSmall.setVisibility(View.GONE);
                    }
                });
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(618);
                anim.start();
            }
        });
    }

    private void revealOpen() {
        rvPrice.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                    rvPrice.setVisibility(View.VISIBLE);
                    mirrorSmall.setVisibility(View.VISIBLE);
                    return;
                }
                int cx = (rvPrice.getLeft() + rvPrice.getRight()) / 2;
                int cy = 0;
                int w = rvPrice.getWidth();
                int h = rvPrice.getHeight();

                // 勾股定理 & 进一法
                int finalRadius = (int) Math.hypot(w, h);
                Animator anim = ViewAnimationUtils.createCircularReveal(rvPrice, cx, cy, 0, finalRadius);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        setTextviewDrawableRight(ivCarPrice, R.mipmap.arrow_up);
                        mirrorSmall.setVisibility(View.VISIBLE);
                        rvPrice.setVisibility(View.VISIBLE);

                    }
                });
                anim.setInterpolator(new AccelerateDecelerateInterpolator());
                anim.setDuration(618);
                anim.start();
            }
        });
    }


    private void showCarMenu() {
        setTextviewDrawableRight(tittlebar.getTvTittle(), R.mipmap.arrow_up);
        if (mTopRightMenu == null) {
            mTopRightMenu = new TopRightMenu(this, Constants.TEXT_TYPE_MID);
            mTopRightMenu.setOnDismissListener(new OnDismissListener() {
                @Override
                public void ondismiss() {
                    setTextviewDrawableRight(tittlebar.getTvTittle(), R.mipmap.arrow_down);
                    mirrorBig.setVisibility(View.GONE);
                }
                @Override
                public void onSpread() {
                    mirrorBig.setVisibility(View.VISIBLE);
                }
            });
            mTopRightMenu
                    .showIcon(false)     //显示菜单图标，默认为true
                    .dimBackground(true)           //背景变暗，默认为true
                    .needAnimationStyle(true)   //显示动画，默认为true
                    .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                    .addMenuList(menuItemList)
                    .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                        @Override
                        public void onMenuItemClick(int position) {
                            final String carid = menuItemList.get(position).getId();
                            final String carname = menuItemList.get(position).getText();
                            if (!TextUtils.isEmpty(carid)) {
                                PreferenceUtils.setPrefString(VideoListActivity.this, Constants.CAR_ID, carid);
                                PreferenceUtils.setPrefString(VideoListActivity.this, Constants.CAR_NAME, carname);
                                Constants.carid = carid;
                                tittlebar.getTvTittle().setText(carname);
                                mPresenter.requestCarMedias(Constants.carid);
                                Observable.just(1)
                                        .observeOn(Schedulers.computation())
                                        .subscribe(new Action1<Integer>() {
                                            @Override
                                            public void call(Integer integer) {
                                                ShareprefrenceStackUtils.getInstance().add(new CarBean(carid, carname));
                                            }
                                        });
                            }
                        }
                    })
                    .showAsDropDown(tittlebar);
        } else {
            mTopRightMenu.showAsDropDown(tittlebar);
        }
    }


    private void setTextviewDrawableRight(TextView textview,  int id) {
        Drawable drawable = getResources().getDrawable(id);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textview.setCompoundDrawables(null, null, drawable, null);
    }
}

