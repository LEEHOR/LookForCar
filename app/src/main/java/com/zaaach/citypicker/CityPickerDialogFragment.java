package com.zaaach.citypicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cyht.wykc.R;
import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.KeyBoardUtils;
import com.cyht.wykc.utils.ScreenUtils;
import com.cyht.wykc.widget.MyTittleBar.NormalTittleBar;
import com.gyf.barlibrary.ImmersionBar;
import com.socks.library.KLog;
import com.zaaach.citypicker.adapter.CityListAdapter;
import com.zaaach.citypicker.adapter.InnerListener;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.adapter.decoration.DividerItemDecoration;
import com.zaaach.citypicker.adapter.decoration.SectionItemDecoration;
import com.zaaach.citypicker.db.DBManager;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.CountryCity;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zaaach.citypicker.view.SideIndexBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Bro0cL
 * @Date: 2018/2/6 20:50
 */
public class CityPickerDialogFragment extends AppCompatDialogFragment implements TextWatcher,
        View.OnClickListener, SideIndexBar.OnIndexTouchedChangedListener, InnerListener {
    private View mContentView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private TextView mOverlayTextView;
    private SideIndexBar mIndexBar;
    private EditText mSearchBox;
    private NormalTittleBar mTittlebar;
//    private TextView mCancelBtn;
//    private ImageView mClearAllBtn;

    private LinearLayoutManager mLayoutManager;
    private CityListAdapter mAdapter;
    private List<City> mAllCities;
    private List<HotCity> mHotCities;
    private List<City> mResults;

    private DBManager dbManager;

    private boolean enableAnim = false;
    private int mAnimStyle = R.style.DefaultCityPickerAnimation;
    private LocatedCity mLocatedCity;
    private CountryCity mCountry;
    private int locateState;
    private OnPickListener mOnPickListener;

    /**
     * 获取实例
     * @param enable 是否启用动画效果
     * @return
     */
    public static CityPickerDialogFragment newInstance(boolean enable){
        final CityPickerDialogFragment fragment = new CityPickerDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("cp_enable_anim", enable);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            enableAnim = args.getBoolean("cp_enable_anim");
        }
        initCountryCity();
//        initHotCities();
        initLocatedCity();

        dbManager = new DBManager(getContext());
        mAllCities = dbManager.getAllCities();
        mAllCities.add(0, mLocatedCity);
        mAllCities.add(1,mCountry);
//        mAllCities.add(1, new HotCity("热门城市", "未知", "0"));
        mResults = mAllCities;
    }

    private void initLocatedCity() {
        if (mLocatedCity == null){
            mLocatedCity = new LocatedCity("定位失败", "未知", "0");
            locateState = LocateState.FAILURE;
        }else{
            locateState = LocateState.SUCCESS;
        }
    }

    private void initCountryCity(){
        if (mCountry == null) {
            mCountry = new CountryCity("全国","地球","0");
        }
    }
    private void initHotCities() {
        if (mHotCities == null || mHotCities.isEmpty()) {
            mHotCities = new ArrayList<>();
            mHotCities.add(new HotCity("北京", "北京", "101010100"));
            mHotCities.add(new HotCity("上海", "上海", "101020100"));
            mHotCities.add(new HotCity("广州", "广东", "101280101"));
            mHotCities.add(new HotCity("深圳", "广东", "101280601"));
            mHotCities.add(new HotCity("天津", "天津", "101030100"));
            mHotCities.add(new HotCity("杭州", "浙江", "101210101"));
            mHotCities.add(new HotCity("南京", "江苏", "101190101"));
            mHotCities.add(new HotCity("成都", "四川", "101270101"));
            mHotCities.add(new HotCity("武汉", "湖北", "101200101"));
        }
    }

    public void setLocatedCity(LocatedCity location){
        mLocatedCity = location;
    }
//
//    public void setHotCities(List<HotCity> data){
//        if (data != null && !data.isEmpty()){
//            this.mHotCities = data;
//        }
//    }

    @SuppressLint("ResourceType")
    public void setAnimationStyle(@StyleRes int style){
        this.mAnimStyle = style <= 0 ? R.style.DefaultCityPickerAnimation : style;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.cp_dialog_city_picker, container, false);

        mRecyclerView = mContentView.findViewById(R.id.cp_city_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SectionItemDecoration(getActivity(), mAllCities), 0);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()), 1);
        mAdapter = new CityListAdapter(getActivity(), mAllCities, mHotCities, locateState);
        mAdapter.setInnerListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mEmptyView = mContentView.findViewById(R.id.cp_empty_view);
        mOverlayTextView = mContentView.findViewById(R.id.cp_overlay);

        mIndexBar = mContentView.findViewById(R.id.cp_side_index_bar);
        mIndexBar.setOverlayTextView(mOverlayTextView)
                .setOnIndexChangedListener(this);

        mSearchBox = mContentView.findViewById(R.id.cp_search_box);
        mSearchBox.addTextChangedListener(this);
        mTittlebar=mContentView.findViewById(R.id.tb_tittle);
        mTittlebar.setPadding(mTittlebar.getPaddingLeft(), ScreenUtils.getStatusBarHeight(BaseApplication.mContext), mTittlebar.getPaddingRight(), mTittlebar.getPaddingBottom());
        mTittlebar.getTvTittle().setText("选择城市");
        mTittlebar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        mCancelBtn = mContentView.findViewById(R.id.cp_cancel);
//        mClearAllBtn = mContentView.findViewById(R.id.cp_clear_all);
//        mCancelBtn.setOnClickListener(this);
//        mClearAllBtn.setOnClickListener(this);
        KeyBoardUtils.UpdateUI(mContentView.getRootView(),BaseApplication.mContext);

        return mContentView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        ImmersionBar.with(this,dialog)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        Window window = dialog.getWindow();
        if(window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            if (enableAnim) {
                window.setWindowAnimations(mAnimStyle);
            }
        }
        return dialog;
    }



    /** 搜索框监听 */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String keyword = s.toString();
        if (TextUtils.isEmpty(keyword)){
//            mClearAllBtn.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mResults = mAllCities;
            ((SectionItemDecoration)(mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            mAdapter.updateData(mResults);
        }else {
//            mClearAllBtn.setVisibility(View.VISIBLE);
            //开始数据库查找
            mResults = dbManager.searchCity(keyword);
            ((SectionItemDecoration)(mRecyclerView.getItemDecorationAt(0))).setData(mResults);
            if (mResults == null || mResults.isEmpty()){
                mEmptyView.setVisibility(View.VISIBLE);
            }else {
                mEmptyView.setVisibility(View.GONE);
                mAdapter.updateData(mResults);
            }
        }
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.cp_cancel) {
//            dismiss(-1, null);
//        }else if(id == R.id.cp_clear_all){
//            mSearchBox.setText("");
//        }
    }

    @Override
    public void onIndexChanged(String index, int position) {
        //滚动RecyclerView到索引位置
        KLog.e("string:"+index+"pisiton:"+position);
        if (mResults == null || mResults.isEmpty()) return;
        if (TextUtils.isEmpty(index)) return;
        int size = mResults.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(index.substring(0, 1), mResults.get(i).getSection().substring(0, 1))){
                if (mLayoutManager != null){
                    mLayoutManager.scrollToPositionWithOffset(i, 0);
                    return;
                }
            }
        }
    }

    public void locationChanged(LocatedCity location, int state){
        mAdapter.updateLocateState(location, state);
    }

    @Override
    public void dismiss(int position, City data) {
        dismiss();
        if (mOnPickListener != null){
            mOnPickListener.onPick(position, data);
        }
    }

    @Override
    public void locate(){
        if (mOnPickListener != null){
            mOnPickListener.onLocate();
        }
    }

    public void setOnPickListener(OnPickListener listener){
        this.mOnPickListener = listener;
    }
}
