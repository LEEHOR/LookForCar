package com.cyht.wykc.mvp.view.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.platform.comapi.map.K;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.contract.setting.HistoryContract;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;
import com.cyht.wykc.mvp.presenter.setting.HistoryPresenter;
import com.cyht.wykc.mvp.view.adapter.HistoryAdapter;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.mvp.view.videoplay.TBSWebView;
import com.cyht.wykc.utils.NetUtil;
import com.cyht.wykc.utils.PreferenceUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.cyht.wykc.widget.UnConnectView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */

public class HistoryFragment extends BaseFragment<HistoryContract.Presenter> implements HistoryContract.View {

    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.cyht_swipe)
    SwipeRefreshLayout cyhtSwipe;
    @BindView(R.id.loading)
    UnConnectView unConnectView;
    @BindView(R.id.collecti_btn_select)
    Button BtnSelect;
    @BindView(R.id.collecti_btn_delete)
    Button BtnDelete;
    @BindView(R.id.collect_layout_edit)
    RelativeLayout LayoutEdit;
    @BindView(R.id.layout_nohistory)
    RelativeLayout layoutNohistory;
    private HistoryAdapter mAdapter;
    private ArrayList<HistoryBean.ListEntity> mDatas = new ArrayList<>();
    private Map<String, String> params = new HashMap<>();
    private boolean isEdit = false;
    private boolean isAllSelct=false;
    private ArrayList<HistoryBean.ListEntity> mSelects = new ArrayList<>();

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {

    }

    @Override
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setText("观看历史");
        tbTittle.getRightTV().setVisibility(View.VISIBLE);
        tbTittle.getRightTV().setText(getResources().getText(R.string.collection_edit));
        tbTittle.getRightTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDatas != null && mDatas.size() > 0) {
                    isEdit = !isEdit;
                    isAllSelct = false;
                    mSelects.clear();
                    tbTittle.getRightTV().setText(getResources().getText(isEdit ? R.string.collection_cancle : R.string.collection_edit));
                    BtnSelect.setText(isEdit ? R.string.collection_select : R.string.collection_unselect);
                    LayoutEdit.setVisibility(isEdit ? View.VISIBLE : View.GONE);
                    BtnDelete.setTextColor(ContextCompat.getColor(BaseApplication.mContext, R.color.font_gray_color));
                    mAdapter.setIsedit(isEdit);
                    mAdapter.setCheck(mSelects);
                    mAdapter.notifyDataSetChanged();
                    cyhtSwipe.setEnabled(isEdit ? false : true);
                }
            }
        });
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingActivity)_mActivity).onBackPressedSupport();
            }
        });
        cyhtSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cyhtSwipe.post(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.requestMyHistory();
                    }
                });
            }
        });
        BtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mSelects.clear();
                    if (isAllSelct){
                        mSelects.clear();
                        BtnSelect.setText(getResources().getString(R.string.collection_select));
                        BtnDelete.setTextColor(ContextCompat.getColor(_mActivity, R.color.font_gray_color));
                        isAllSelct = false;
                    }else {
                        mSelects.addAll(mDatas);
                        BtnSelect.setText(getResources().getString(R.string.collection_unselect));
                        BtnDelete.setTextColor(ContextCompat.getColor(_mActivity, R.color.material_red_500));
                        isAllSelct = true;
                    }
                    mAdapter.setCheck(mSelects);
                    mAdapter.notifyDataSetChanged();
                }
        });
        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelects.size()>0) {
                    new AlertDialog.Builder(_mActivity)
                            .setTitle(getResources().getString(R.string.dialog_connect_title))
                            .setMessage(R.string.history_dialog_message)
                            .setNegativeButton(R.string.cyht_button_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mSelects.size()<=0) {
                                        Toast.makeText(_mActivity,getResources().getString(R.string.collection_delete_toast),Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    mPresenter.deleleHistory(mSelects);
                                }
                            })
                            .setNeutralButton(R.string.cyht_button_cancel, null)
                            .show();
                }
            }
        });
    }

    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(BaseApplication.mContext);
        rvHistory.setLayoutManager(manager);
        mAdapter = new HistoryAdapter(isEdit,mSelects);
        rvHistory.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onHistroryItemPlay(HistoryBean.ListEntity item, BaseViewHolder helper) {
                if (!isEdit) {
                    Constants.carid = item.getCxid();
                    PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.CAR_ID, Constants.carid);
                    PreferenceUtils.setPrefString(BaseApplication.mContext, Constants.CAR_NAME, item.getCxmc());
                    Intent intent = new Intent(_mActivity, TBSWebView.class);
                    intent.putExtra("videoid", item.getId());
                    intent.putExtra("tittle", item.getTitle());
                    intent.putExtra("cover", item.getImageurl());
                    intent.putExtra("videoType",item.getVideoType());
                    startActivity(intent);
                } else {
                    if (mSelects.contains(item)) {
                        mSelects.remove(item);
                        helper.setBackgroundRes(R.id.collect_item_btn_select, R.mipmap.selected_f);
                        if (isAllSelct) {
                            BtnSelect.setText(getResources().getString(R.string.collection_select));
                            isAllSelct = false;
                        }
                        if (mSelects.size() == 0) {
                            BtnDelete.setTextColor(ContextCompat.getColor(BaseApplication.mContext, R.color.font_gray_color));
                        }
                    } else {
                        mSelects.add(item);
                        helper.setBackgroundRes(R.id.collect_item_btn_select, R.mipmap.selected);
                        if (mSelects.size() == mDatas.size()) {
                            BtnSelect.setText(getResources().getString(R.string.collection_unselect));
                            isAllSelct = true;
                        }
                        BtnDelete.setTextColor(ContextCompat.getColor(BaseApplication.mContext, R.color.material_red_500));
                    }
                }
            }

            @Override
            public void onHistroryItemDelete(HistoryBean.ListEntity item) {
                super.onHistroryItemDelete(item);
                ArrayList<HistoryBean.ListEntity> listEntities = new ArrayList<>();
                listEntities.add(item);
                mPresenter.deleleHistory(listEntities);
            }
        });

        mPresenter.requestMyHistory();
    }

    @Override
    public void showLoading() {
        cyhtSwipe.setRefreshing(true);
    }

    @Override
    public void onRequestSuccess(HistoryBean historyBean) {
        cyhtSwipe.setRefreshing(false);
        unConnectView.close();
        if (historyBean.getList() != null&&historyBean.getList().size()>0) {
            mDatas.clear();
            mDatas.addAll(historyBean.getList());
            mAdapter.setNewData(mDatas);
            mAdapter.notifyDataSetChanged();
            layoutNohistory.setVisibility(View.GONE);
        }else {
            layoutNohistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onrequestFailue(Throwable throwable) {
        KLog.e("onrequestFailue:");
        if (!NetUtil.checkNetWork(BaseApplication.mContext)) {
            unConnectView.show();
        }else {
            unConnectView.close();
        }
        if (throwable != null) {
            KLog.e("throwable:"+throwable.getMessage());
        }else {

        }
        cyhtSwipe.setRefreshing(false);
    }

    @Override
    public void ondeleteSuccess(List<HistoryBean.ListEntity> listEntities) {
        Toast.makeText(_mActivity,getResources().getString(R.string.collection_toast_delete_success),Toast.LENGTH_SHORT).show();
        mDatas.removeAll(listEntities);
        mSelects.removeAll(listEntities);
        mAdapter.notifyDataSetChanged();
        if (mDatas.size()==0) {
            tbTittle.getRightTV().setText(R.string.collection_edit);
            LayoutEdit.setVisibility(View.GONE);
            layoutNohistory.setVisibility(View.VISIBLE);
            isAllSelct=false;
            isEdit=false;
        }
    }

    @Override
    public void ondeleteFailue(Throwable throwable) {
        Toast.makeText(_mActivity,getResources().getString(R.string.collection_toast_delete_failure),Toast.LENGTH_SHORT).show();
    }

}
