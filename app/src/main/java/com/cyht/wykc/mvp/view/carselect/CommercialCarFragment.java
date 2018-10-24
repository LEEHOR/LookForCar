package com.cyht.wykc.mvp.view.carselect;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.K;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cyht.wykc.R;
import com.cyht.wykc.common.BrandComparator;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.Interface.BrandItemClickListener;
import com.cyht.wykc.mvp.contract.Interface.CarItemClickLinstener;
import com.cyht.wykc.mvp.contract.carselect.CommercialCarContract;
import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.modles.bean.CarListBean;
import com.cyht.wykc.mvp.presenter.carselect.CommercialCarPresenter;
import com.cyht.wykc.mvp.view.adapter.CarAdapter;
import com.cyht.wykc.mvp.view.adapter.CarBrandAdapter;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.utils.DisplayUtils;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.utils.SharedPreferencesUtils;
import com.cyht.wykc.utils.ShareprefrenceStackUtils;
import com.cyht.wykc.widget.MyDrawLayout;
import com.cyht.wykc.widget.SortRecyclerView.SideBar;
import com.cyht.wykc.widget.SortRecyclerView.TouchableRecyclerView;
import com.cyht.wykc.widget.UnConnectView;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersDecoration;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class CommercialCarFragment extends BaseFragment<CommercialCarContract.Presenter> implements CommercialCarContract.View {
    @BindView(R.id.rv_carbrnad)
    TouchableRecyclerView rvCarbrnad;
    @BindView(R.id.brand_sidebar)
    SideBar Sidebar;
    @BindView(R.id.loading)
    UnConnectView unConnectView;
    @BindView(R.id.commercial_swipe)
    SwipeRefreshLayout commercialSwipe;
    @BindView(R.id.select_brand_icon)
    ImageView selectbrandicon;
    @BindView(R.id.select_brand_tittle)
    TextView selectbrandtittle;
    @BindView(R.id.rv_car)
    RecyclerView rvcar;
    @BindView(R.id.right_drawer)
    FrameLayout rightDrawer;
    @BindView(R.id.drawer_layout)
    MyDrawLayout drawerLayout;
    @BindView(R.id.ptr_layout)
    PtrFrameLayout ptrCommercial;

    private BrandComparator brandComparator;
    private boolean iscandorefresh = false;

    private boolean inited = false;
    private LinearLayoutManager linearLayoutManager0;
    private LinearLayoutManager linearLayoutManager1;
    private BrandListBean.DataEntity.BrandListEntity brandFoucused;
    private CarBrandAdapter brandAdapter;
    private CarAdapter carAdapter;

    public static CommercialCarFragment newInstance() {
        return new CommercialCarFragment();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        KLog.e("onViewStateRestored");
    }

    @Override
    public void showLoading() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {
        if (eventData.from == Constants.CARBRANDFRAGMENT && eventData.to == Constants.COMMERCIALCARFRAGMENT) {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
            }
            brandFoucused = new BrandListBean.DataEntity.BrandListEntity();
            brandFoucused.setId(eventData.brandListEntity.getId());
            brandFoucused.setLogo(eventData.brandListEntity.getLogo());
            brandFoucused.setType(eventData.brandListEntity.getType());
            brandFoucused.setName(eventData.brandListEntity.getName());
            mPresenter.requestCarForBrand(brandFoucused);
            Glide.with(BaseApplication.mContext).load(brandFoucused.getLogo()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).into(selectbrandicon);
            selectbrandtittle.setText(brandFoucused.getName());
        }
        if (eventData.to == Constants.COMMERCIALCARFRAGMENT && !hasStarted) {
            mPresenter.start();
            hasStarted = true;
        }
    }


    @Override
    public CommercialCarContract.Presenter createPresenter() {
        return new CommercialCarPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_brand_commercial;
    }

    @Override
    public void initView() {
        commercialSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
                commercialSwipe.post(new Runnable() {
                    @Override
                    public void run() {
                        commercialSwipe.setRefreshing(true);
                    }
                });
            }
        });

        Sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {

                if (brandAdapter != null) {
                    int position = brandAdapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        rvCarbrnad.getLayoutManager().scrollToPosition(position);
                    }
                }

            }
        });
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
                Constants.IS_DRAW_OPEN = 1;
                KLog.e("onDrawerOpened");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
                Constants.IS_DRAW_OPEN = 0;
                KLog.e("onDrawerOpened");
            }

        });
        initPtr();
    }

    @Override
    public void initData() {
        brandComparator = new BrandComparator();
        linearLayoutManager0 = new LinearLayoutManager(BaseApplication.mContext);
        linearLayoutManager1 = new LinearLayoutManager(BaseApplication.mContext);
        rvCarbrnad.setLayoutManager(linearLayoutManager0);
        rvcar.setLayoutManager(linearLayoutManager1);
        brandAdapter = new CarBrandAdapter(BaseApplication.mContext);
        carAdapter = new CarAdapter();
        rvCarbrnad.setAdapter(brandAdapter);
        rvcar.setAdapter(carAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(brandAdapter);
        rvCarbrnad.addItemDecoration(headersDecor);
        brandAdapter.setOnItemClickLisenter(new BrandItemClickListener() {
            @Override
            public void onclick(BrandListBean.DataEntity.BrandListEntity entity) {
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                brandFoucused = entity;
                mPresenter.requestCarForBrand(entity);
                Glide.with(BaseApplication.mContext).load(entity.getLogo()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).into(selectbrandicon);
                selectbrandtittle.setText(entity.getName());
            }
        });
        carAdapter.setCarItemClickLinstener(new CarItemClickLinstener() {
            @Override
            public void onclick(final CarListBean.DataEntity.CarListEntity item) {
                Observable.just(1)
                        .observeOn(Schedulers.computation())
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                ShareprefrenceStackUtils.getInstance().add(new CarBean(item.getId(), item.getName()));
                            }
                        });

                Intent intent = new Intent(_mActivity, VideoListActivity.class);
                intent.putExtra(Constants.CAR_ID, item.getId());
                intent.putExtra(Constants.CAR_NAME, item.getName());
                startActivity(intent);

            }
        });
        rvcar.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(-1)) {//滑动到顶部
                    iscandorefresh = true;
                } else if (!recyclerView.canScrollVertically(1)) {//滑动到底部
                    iscandorefresh = false;
                } else {
                    iscandorefresh = false;
                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    private void initPtr() {
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, DisplayUtils.dp2px(20), 0, DisplayUtils.dp2px(20));
        header.initWithString("Ultra PTR");
        header.setTextColor(Color.RED);

        ptrCommercial.setDurationToCloseHeader(1500);
        ptrCommercial.setHeaderView(header);
        ptrCommercial.addPtrUIHandler(header);
        ptrCommercial.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                return iscandorefresh;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrCommercial.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.requestCarForBrand(brandFoucused);
                        ptrCommercial.refreshComplete();
                    }
                }, 1500);
            }
        });
    }

    @Override
    public boolean onBackPressedSupport() {
        KLog.e("onBackPressedSupport");
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawers();
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    @Override
    public void onRequestBrandSuccess(List<BrandListBean.DataEntity.BrandListEntity> list) {
        commercialSwipe.setRefreshing(false);
        Collections.sort(list, brandComparator);
        brandAdapter.setNewData(list);
        brandAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestCarSuccess(List<CarListBean.DataEntity.CarListEntity> list) {
        carAdapter.setData(list);
    }

    @Override
    public void onrequestBrandFailue(@Nullable Throwable e) {
        commercialSwipe.setRefreshing(false);
        if (!NetUtil.checkNetWork(BaseApplication.mContext)) {
            unConnectView.show();
        } else {
            unConnectView.close();
        }
    }

    @Override
    public void onrequestCarFailue(@Nullable Throwable e) {
        if (e != null) {
            if (e instanceof NetworkErrorException) {
//                unConnectView.show(getResources().getString(R.string.unconnect));
                Toast.makeText(_mActivity, "网络不给力呀", Toast.LENGTH_SHORT).show();
            } else {
                KLog.e("throwable:" + e.getMessage());
            }
        }
    }
}
