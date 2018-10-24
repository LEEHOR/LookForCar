package com.cyht.wykc.mvp.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.contract.CarSearchContract;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.modles.bean.CarBean;
import com.cyht.wykc.mvp.presenter.CarSearchPresenter;
import com.cyht.wykc.mvp.view.adapter.HistorySearchAdapter;
import com.cyht.wykc.mvp.view.adapter.HotAdapter;
import com.cyht.wykc.mvp.view.adapter.SearchAdapter;
import com.cyht.wykc.mvp.view.base.BaseActivity;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.carselect.VideoListActivity;
import com.cyht.wykc.utils.DatabaseUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.utils.ShareprefrenceStackUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Author： hengzwd on 2017/9/8.
 * Email：hengzwdhengzwd@qq.com
 */

public class CarSearchActivity extends BaseActivity<CarSearchContract.Presenter> implements CarSearchContract.view {
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.ll_hot_history)
    LinearLayout llHotHistory;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.ll_searchgroup)
    LinearLayout llSearchgroup;
    @BindView(R.id.rv_carmodles)
    RecyclerView rvCarmodles;
    @BindView(R.id.fl_search)
    FrameLayout flSearch;


    private List<CarBean> carmodels = new ArrayList<CarBean>();
    private LinearLayoutManager mlinearlayoutmanager;
    private StaggeredGridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager gridLayoutManager0;
    private SearchAdapter madapter;
    private HistorySearchAdapter nadapter;
    private HotAdapter wadapter;
    private List<CarBean> historycarmoles = new ArrayList<CarBean>();
    private List<CarBean> hotcarmodles = new ArrayList<CarBean>();

    @Override
    public void showLoading() {

    }

    @Override
    public void onHistorySuccess(List<CarBean> carBeanList) {
        KLog.e("historycarmodels:" + carBeanList.get(0).getModelId());
        if (carBeanList.size()>4) {
            gridLayoutManager.setSpanCount(2);
        }
        historycarmoles.clear();
        historycarmoles.addAll(carBeanList);
        nadapter.setNewData(carBeanList);
        nadapter.notifyDataSetChanged();
    }

    @Override
    public void onHotFailue(Throwable throwable) {
        tvHot.setVisibility(View.GONE);
    }

    @Override
    public void onHotSuccess(List<CarBean> carBeanList) {
        if (carBeanList.size()>4) {
            gridLayoutManager0.setSpanCount(2);
        }
        tvHot.setVisibility(View.VISIBLE);
        hotcarmodles.clear();
        hotcarmodles.addAll(carBeanList);
        wadapter.setNewData(hotcarmodles);
        wadapter.notifyDataSetChanged();
    }

    @Override
    public CarSearchContract.Presenter createPresenter() {
        return new CarSearchPresenter(this);
    }

    @Override
    public int binLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        flSearch.setPadding(flSearch.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), flSearch.getPaddingRight(), flSearch.getPaddingBottom());
        tvHistory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    boolean touchable = event.getX() > (tvHistory.getWidth() - tvHistory.getTotalPaddingRight())
                            && (event.getX() < ((tvHistory.getWidth() - tvHistory.getPaddingRight())));

                    KLog.e(TAG + "tvhistory.getTotalPaddingRight():" + tvHistory.getTotalPaddingRight() + "tvhistory.getPaddingRight():" + tvHistory.getPaddingRight());
                    if (touchable) {
                        //里面写上自己想做的事情，也就是DrawableRight的触发事件
                        ShareprefrenceStackUtils.getInstance().clearstack();
                        unSpreadrecycleview(historycarmoles, nadapter);
                        KLog.e(TAG + "触发drawright");
                    }
                }
                return true;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                KLog.e(TAG, s.toString());
                if (s.toString().equals("") || s.toString() == null) {
                    unSpreadrecycleview(carmodels, madapter);
                    rvCarmodles.setVisibility(View.GONE);
                } else {
                    carmodels = DatabaseUtils.getInstance().findCarModels(s.toString());
                    KLog.e(TAG, carmodels.size());
                    madapter.setNewData(carmodels);
                    madapter.notifyDataSetChanged();
                    if (carmodels.size() > 0) {
                        rvCarmodles.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressedSupport();
            }
        });

        flSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSpreadrecycleview(carmodels, madapter);
            }
        });

    }

    @Override
    public void initData() {
        mlinearlayoutmanager = new LinearLayoutManager(BaseApplication.mContext);
        gridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);//网格布局的recycleview，3列
        gridLayoutManager0 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        madapter = new SearchAdapter();
        nadapter = new HistorySearchAdapter();
        wadapter = new HotAdapter();
        madapter.setItemClickListener(itemClickListener);
        nadapter.setItemClickListener(itemClickListener);
        wadapter.setItemClickListener(itemClickListener);
        rvCarmodles.setLayoutManager(mlinearlayoutmanager);
        rvHistory.setLayoutManager(gridLayoutManager);
        rvHot.setLayoutManager(gridLayoutManager0);
        rvCarmodles.setAdapter(madapter);
        rvHistory.setAdapter(nadapter);
        rvHot.setAdapter(wadapter);
        mPresenter.requestHotSearch();
        mPresenter.getSearchHistory();
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (historycarmoles.size() != 0 && historycarmoles.get(position) != null) {
//                    int b = historycarmoles.get(position).getName().length();
//                    int a = historycarmoles.get(position).getName().getBytes().length;
//                    return (a < 4 && b < 3) ? 1 : (a < 10 && b < 4) ? 2 : (a < 20 && b < 8) ? 3 : a < 30 ? 4 : a < 40 ? 5 : a < 50 ? 6 : a < 60 ? 7 : a < 70 ? 8 : 8;
//                } else {
//                    return 0;
//                }
//            }
//        });
//        gridLayoutManager0.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                if (hotcarmodles.size() != 0 && hotcarmodles.get(position) != null) {
//                    int b = hotcarmodles.get(position).getName().length();
//                    int a = hotcarmodles.get(position).getName().getBytes().length;
//                    return (a < 4 && b < 3) ? 1 : (a < 10 && b < 4) ? 2 : (a < 20 && b < 8) ? 3 : a < 30 ? 4 : a < 40 ? 5 : a < 50 ? 6 : a < 60 ? 7 : a < 70 ? 8 : 8;
//                } else {
//                    return 0;
//                }
//            }
//        });
    }

    ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onclick(final CarBean carmodel) {
            super.onclick(carmodel);
            String carid = carmodel.getModelId();
            if (DatabaseUtils.getInstance().getCarModelById(carid) != null) {
                if (!TextUtils.isEmpty(carid)) {
                    Constants.carid = carid;
                    Observable.just(1)
                            .observeOn(Schedulers.computation())
                            .subscribe(new Action1<Integer>() {
                                @Override
                                public void call(Integer integer) {
                                    ShareprefrenceStackUtils.getInstance().add(carmodel);
                                }
                            });
                }
                Intent intent = new Intent(CarSearchActivity.this, VideoListActivity.class);
                KLog.e("carid:"+carmodel.getId());
                intent.putExtra(Constants.CAR_ID,carmodel.getModelId());
                intent.putExtra(Constants.CAR_NAME,carmodel.getName());
                startActivity(intent);
            } else {
                Toast.makeText(CarSearchActivity.this, "未找到车型", Toast.LENGTH_SHORT).show();
            }
        }
    };


    /**
     * recycleview叠起来
     */
    private void unSpreadrecycleview(List<CarBean> list, BaseQuickAdapter adapter) {
        list = null;
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        KLog.e("onkeydown");

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (madapter.getItemCount() != 0) {
                unSpreadrecycleview(carmodels, madapter);
                rvCarmodles.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
