package com.cyht.wykc.mvp.view.setting;


import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cyht.wykc.R;
import com.cyht.wykc.common.Constants;
import com.cyht.wykc.common.EventData;
import com.cyht.wykc.mvp.contract.Interface.ItemClickListener;
import com.cyht.wykc.mvp.contract.setting.LettersContract;
import com.cyht.wykc.mvp.modles.bean.MsgBean;
import com.cyht.wykc.mvp.presenter.setting.LettersPresenter;
import com.cyht.wykc.mvp.view.MainActivity;
import com.cyht.wykc.mvp.view.TweetActivity1;
import com.cyht.wykc.mvp.view.adapter.LettersAdapter;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.mvp.view.base.BaseFragment;
import com.cyht.wykc.mvp.view.videoplay.TBSWebView;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Author： hengzwd on 2017/9/5.
 * Email：hengzwdhengzwd@qq.com
 */

public class LettersFragment extends BaseFragment<LettersContract.Presenter> implements LettersContract.View {


    @BindView(R.id.tb_tittle)
    NormalTittleBar tbTittle;
    @BindView(R.id.rv_letters)
    RecyclerView rvLetters;
    @BindView(R.id.swipe_letters)
    SwipeRefreshLayout swipeRefreshLayout;

    private int lastVisibleItemPosition;
    private LettersAdapter lettersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading;
    private List<MsgBean.DataEntity.ListEntity> msglist;

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void recieveEventBus(EventData eventData) {

    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onRequestSuccess(List<MsgBean.DataEntity.ListEntity> list) {
        isLoading = false;
        msglist = list;
        swipeRefreshLayout.setRefreshing(false);
        lettersAdapter.setMsglist(list);
        lettersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onrequestFailue(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    @Override
    public void onloadmoreSuccess(List<MsgBean.DataEntity.ListEntity> list) {
        isLoading = false;
        if (list.size() == 0) {
            //之后没有数据了，ui怎么显示
            lettersAdapter.notifyItemRemoved(lettersAdapter.getItemCount());
            Toast.makeText(_mActivity, "没有更多信息了", Toast.LENGTH_SHORT).show();
        } else {
            msglist.addAll(list);
            lettersAdapter.addMsglist(list);
            lettersAdapter.notifyDataSetChanged();
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
        lettersAdapter.notifyItemRemoved(lettersAdapter.getItemCount());
        isLoading = false;
    }

    @Override
    public void updateFailure(Throwable throwable) {

    }

    @Override
    public void updateSuccess(String msgId) {
        EventBus.getDefault().postSticky(new EventData(Constants.LETTERSFRAGMENT, Constants.PERSONALCENTERFRAGMENT, Constants.BE_REFRESH));

    }

    @Override
    public LettersContract.Presenter createPresenter() {
        return new LettersPresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_letters;
    }

    @Override
    public void initView() {
        tbTittle.setPadding(tbTittle.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), tbTittle.getPaddingRight(), tbTittle.getPaddingBottom());
        tbTittle.getTvTittle().setText("我的消息");
        tbTittle.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SettingActivity) _mActivity).onBackPressedSupport();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestLetters();
            }
        });

        rvLetters.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == lettersAdapter.getItemCount() && msglist.size() >= 10) {
                    if (!isLoading) {
                        rvLetters.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.loadmore();
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
            }
        });
    }

    @Override
    public void initData() {
        lettersAdapter = new LettersAdapter(BaseApplication.mContext);
        linearLayoutManager = new LinearLayoutManager(BaseApplication.mContext);
        rvLetters.setLayoutManager(linearLayoutManager);
        rvLetters.setAdapter(lettersAdapter);
        mPresenter.requestLetters();
        isLoading = true;
        lettersAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onSystemclick(MsgBean.DataEntity.ListEntity entity) {
                Intent intent = new Intent(_mActivity, LetterDetailsActivity.class);
                intent.putExtra("type", entity.getType());
                if (entity.getType() == 0) {
                    intent.putExtra("tittle", entity.getTitle());
                    intent.putExtra("content", entity.getBody());
                } else if (entity.getType() == 1) {
                    intent.putExtra("tittle", entity.getName());
                    intent.putExtra("content", entity.getContent());
                } else {
                    //备用
                }
                _mActivity.startActivity(intent);
                Map map = new HashMap();
                map.put("sessionid", Constants.sessionid);
                map.put("type", entity.getType() + "");
                map.put("msgid", entity.getId());
                KLog.e("isread:" + entity.getIsRead());
                if (entity.getIsRead() == 0) {
                    mPresenter.updateMsg(map);
                }
            }

            @Override
            public void onReplyclick(MsgBean.DataEntity.ListEntity entity) {
                KLog.e("type:"+entity.getType());
                if (entity.getType() == 1) {
                    Intent intent = new Intent(_mActivity, TBSWebView.class);
                    intent.putExtra("videoid", entity.getTarget());
                    intent.putExtra("cover", entity.getCover());
                    intent.putExtra("videoType",entity.getVideoType());
                    _mActivity.startActivity(intent);
                } else if (entity.getType() == 3 || entity.getType() == 4) {
                    KLog.e("onReplyclick111111111111");

                    Intent intent = new Intent(_mActivity, TweetActivity1.class);
                    intent.putExtra("videoType",entity.getVideoType());
                    intent.putExtra("id", entity.getTarget());
                    intent.putExtra("type", entity.getType());
                    _mActivity.startActivity(intent);
                } else {
                    //备用
                }
                Map map = new HashMap();
                map.put("sessionid", Constants.sessionid);
                map.put("type", entity.getType() + "");
                map.put("msgid", entity.getId());
                KLog.e("isread:" + entity.getIsRead());
                if (entity.getIsRead() == 0) {
                    mPresenter.updateMsg(map);
                }
            }
        });
    }
}
