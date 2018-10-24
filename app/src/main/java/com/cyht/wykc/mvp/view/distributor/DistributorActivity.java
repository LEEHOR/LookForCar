package com.cyht.wykc.mvp.view.distributor;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.Interface.DistributorPhotoClickListener;
import com.cyht.wykc.mvp.contract.Interface.Goto4Slinstener;
import com.cyht.wykc.mvp.contract.Interface.PhoneClickListener;
import com.cyht.wykc.mvp.contract.distributor.DistributorContract;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.mvp.presenter.distributor.DistributorPresenter;
import com.cyht.wykc.mvp.view.adapter.DistributorAdapter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.utils.PermissionUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.StringUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.cyht.wykc.widget.UnConnectView;
import com.socks.library.KLog;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.litepal.util.Const;

import android.support.v4.app.FragmentTransaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author： hengzwd on 2017/8/30.
 * Email：hengzwdhengzwd@qq.com
 */

public class DistributorActivity extends BaseActivity<DistributorContract.Presenter> implements DistributorContract.View {


    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.tv_own_address)
    TextView tvOwnAddress;
    @BindView(R.id.rv_distributor_info)
    RecyclerView rvDistributorInfo;
    @BindView(R.id.swipe_distributor)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rl_not_avalible)
    RelativeLayout rlNotAvalible;
    @BindView(R.id.unconnect)
    UnConnectView unconnect;
    private DistributorAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private View loadingFootview, noMoreFootview;
    private Map map = new HashMap();
    private BDLocation bdLocation;
    private LocatedCity mLocatedCity;
    private List<DistributorInfoBean.DataEntity.DealerEntity> mAllDealerList;
    private int SHOW_DEALER_COUNT = 0;
    private int ALL_DEALER_COUNT = 0;
    private String mobilePhoneNumber;

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Constants.carid=savedInstanceState.getString("car");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("car", Constants.carid);
    }

    @Override
    public void showError(@Nullable Throwable e) {
        if (!NetUtil.checkNetWork(BaseApplication.mContext)) {
            unconnect.show();
        } else {
            unconnect.close();
        }
        if (e != null) {
            if (e instanceof NetworkErrorException) {
                Toast.makeText(DistributorActivity.this, "网络不给力呀", Toast.LENGTH_SHORT).show();
            } else {
                KLog.e("throwable:" + e.getMessage());
            }
        }

    }

    @Override
    public void onRequestDistributorFailure(Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        showError(t);
    }

    @Override
    public void onRequestDistributorSuccess(DistributorInfoBean distributorBean) {
        swipeRefreshLayout.setRefreshing(false);
        if (distributorBean.getData().getDealer().size() > 0) {
            rlNotAvalible.setVisibility(View.GONE);
            mAllDealerList = distributorBean.getData().getDealer();
            ALL_DEALER_COUNT = mAllDealerList.size();
            if (ALL_DEALER_COUNT >= 10) {
                adapter.setNewData(mAllDealerList.subList(0, 10));
                SHOW_DEALER_COUNT = 10;
            } else {
                adapter.setNewData(mAllDealerList);
                SHOW_DEALER_COUNT = mAllDealerList.size();
            }
            adapter.notifyDataSetChanged();

        } else {
            rlNotAvalible.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void locationSuccess(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
        mLocatedCity = new LocatedCity(StringUtils.getCityName(bdLocation.getCity()), bdLocation.getProvince(), bdLocation.getCityCode());
        tvOwnAddress.setText(StringUtils.getCityName(bdLocation.getCity()));
        map.put("car", Constants.carid);
        map.put("latitude", Constants.latitude + "");
        map.put("longitude", Constants.longitude + "");
        map.put("pageNo", "0");
        map.put("pageSize", 10000 + "");
        Constants.city = StringUtils.getCityName(bdLocation.getCity());
        mPresenter.requestDistributor(map);

    }

    @Override
    public void locationFailue(Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        showError(t);
    }

    @Override
    public void onloadmoreSuccess(DistributorInfoBean distributorBean) {
        adapter.removeAllFooterView();
        adapter.addData(distributorBean.getData().getDealer());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onloadmoreFailue(Throwable t) {
        adapter.removeAllFooterView();
        if (t != null) {
            if (t instanceof NetworkErrorException) {
                Toast.makeText(DistributorActivity.this, "网络不给力呀", Toast.LENGTH_SHORT).show();
            } else {
                KLog.e("throwable:" + t.getMessage());
            }
        }
    }

    @Override
    public DistributorContract.Presenter createPresenter() {
        return new DistributorPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_distributor;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        tbTittle.getTvTittle().setText("本地经销商");
        loadingFootview = getLayoutInflater().inflate(R.layout.item_footer_loading, (ViewGroup) rvDistributorInfo.getParent(), false);
        noMoreFootview = getLayoutInflater().inflate(R.layout.item_footer_not_loading, (ViewGroup) rvDistributorInfo.getParent(), false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        if (bdLocation != null || mLocatedCity != null) {//定位成功或者选择过某个城市
                            mPresenter.requestDistributor(map);
                        } else {
                            startLocation();
                        }
                    }
                });
            }
        });

        rvDistributorInfo.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                KLog.e("dy;" + dy);
                if (linearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1 && Math.abs(dy) > 5) {
//                    adapter.removeAllFooterView();
//                    adapter.addFooterView(loadingFootview);
//                    mPresenter.loadMore(map);
                    if (ALL_DEALER_COUNT - SHOW_DEALER_COUNT >= 10) {
                        adapter.setNewData(mAllDealerList.subList(0, SHOW_DEALER_COUNT + 10));
                        SHOW_DEALER_COUNT = SHOW_DEALER_COUNT + 10;
                    } else if (ALL_DEALER_COUNT == SHOW_DEALER_COUNT) {
                        adapter.removeAllFooterView();
                        adapter.addFooterView(noMoreFootview);
                    } else {
                        adapter.setNewData(mAllDealerList.subList(0, ALL_DEALER_COUNT));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        tvOwnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker.getInstance()
                        .setFragmentManager(getSupportFragmentManager())
                        .enableAnimation(true)
                        .setAnimationStyle(R.style.CustomAnim)
                        .setLocatedCity(mLocatedCity)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                Map map = new HashMap();
                                map.put("car", Constants.carid);
                                map.put("pageNo", "0");
                                map.put("pageSize", 10000 + "");
                                if (bdLocation != null && !bdLocation.getCity().equals("")) {//定位成功
                                    map.put("latitude", Constants.latitude + "");
                                    map.put("longitude", Constants.longitude + "");
                                } else {//定位失败
                                    map.put("latitude", -1 + "");
                                    map.put("longitude", -1 + "");
                                }
                                if (data.getName().equals("全国")) {//选择全国
                                    Constants.city = "";
                                } else {//选择城市
                                    Constants.city = data.getName();
                                }
                                mPresenter.requestDistributor(map);
                                swipeRefreshLayout.setRefreshing(true);
                                tvOwnAddress.setText(data.getName());
                                if (mLocatedCity != null) {
                                    mLocatedCity.setName(data.getName());
                                    mLocatedCity.setCode(data.getCode());
                                    mLocatedCity.setProvince(data.getProvince());
                                }
                            }

                            @Override
                            public void onLocate() {
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void initData() {
        adapter = new DistributorAdapter(BaseApplication.mContext);
        linearLayoutManager = new LinearLayoutManager(BaseApplication.mContext);
        rvDistributorInfo.setLayoutManager(linearLayoutManager);
        rvDistributorInfo.setAdapter(adapter);
        adapter.setPhoneClickListener(new PhoneClickListener() {
            @Override
            public void onClick(String mobile) {
                mobilePhoneNumber = mobile;
                if (PermissionUtils.isPermissionsGranted(DistributorActivity.this, new int[]{PermissionUtils.CODE_CALL_PHONE})) {
                    call(mobile);
                } else {
                    PermissionUtils.requestPermission(DistributorActivity.this, PermissionUtils.CODE_CALL_PHONE, permissionGrant);
                }
            }
        });
        adapter.setgoto4slinstener(new Goto4Slinstener() {
            @Override
            public void onclick(double latitude, double longitude, String name) {
                Intent intent = new Intent(DistributorActivity.this, StationMapLocationActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("distributorName", name);
                startActivity(intent);
            }
        });
        adapter.setDistributorPhotoClickListener(new DistributorPhotoClickListener() {
            @Override
            public void onClick(String url, int type) {
                if (type == DistributorAdapter.TYPE_VIDEO) {
                    Intent intent = new Intent(DistributorActivity.this, DistributorVideoActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else if (type == DistributorAdapter.TYPE_PIC) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    PhotoSeeDialogFragment photoSeeDialogFragment = PhotoSeeDialogFragment.newInstance(url);
                    photoSeeDialogFragment.show(fragmentTransaction, "distributor");


                }
            }
        });
        startLocation();
    }

    private PermissionUtils.PermissionGrant permissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CALL_PHONE:
                    call(mobilePhoneNumber);
                    break;
                case PermissionUtils.CODE_MULTI_ANOTHER_PERMISSION:
                    startLocation();
                    break;
            }
        }
    };

    private void startLocation() {
        swipeRefreshLayout.setRefreshing(false);
        if (PermissionUtils.isPermissionsGranted(this, new int[]{PermissionUtils.CODE_ACCESS_FINE_LOCATION, PermissionUtils.CODE_ACCESS_COARSE_LOCATION})) {
            mPresenter.startLocation();
        } else {
            PermissionUtils.requestMultiAnotherPermissions(this, new int[]{PermissionUtils.CODE_ACCESS_FINE_LOCATION, PermissionUtils.CODE_ACCESS_COARSE_LOCATION}, permissionGrant);
        }
    }

    private void call(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, permissionGrant);
    }
}
