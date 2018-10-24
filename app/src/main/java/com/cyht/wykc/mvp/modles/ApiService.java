package com.cyht.wykc.mvp.modles;

import com.cyht.wykc.common.Constants;
import com.cyht.wykc.mvp.modles.bean.BrandDBbean;
import com.cyht.wykc.mvp.modles.bean.BrandListBean;
import com.cyht.wykc.mvp.modles.bean.CarDBbean;
import com.cyht.wykc.mvp.modles.bean.CarListBean;
import com.cyht.wykc.mvp.modles.bean.CarMediaInfoBean;
import com.cyht.wykc.mvp.modles.bean.CarPriceBean;
import com.cyht.wykc.mvp.modles.bean.ChexingBean;
import com.cyht.wykc.mvp.modles.bean.CollectionBean;
import com.cyht.wykc.mvp.modles.bean.DeletedBean;
import com.cyht.wykc.mvp.modles.bean.DistributorInfoBean;
import com.cyht.wykc.mvp.modles.bean.ExtensionBean;
import com.cyht.wykc.mvp.modles.bean.HistoryBean;
import com.cyht.wykc.mvp.modles.bean.HotCarSearchBean;
import com.cyht.wykc.mvp.modles.bean.HotVideoBean;
import com.cyht.wykc.mvp.modles.bean.LoginBean;
import com.cyht.wykc.mvp.modles.bean.MainBean;
import com.cyht.wykc.mvp.modles.bean.MsgBean;
import com.cyht.wykc.mvp.modles.bean.NameBean;
import com.cyht.wykc.mvp.modles.bean.OpinionBean;
import com.cyht.wykc.mvp.modles.bean.ResultBean;
import com.cyht.wykc.mvp.modles.bean.UnReadBean;
import com.cyht.wykc.mvp.modles.bean.UpdateBean;
import com.cyht.wykc.mvp.modles.bean.UploadPicBean;
import com.cyht.wykc.mvp.view.setting.NameActivity;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Author： hengzwd on 2017/6/1.
 * Email：hengzwdhengzwd@qq.com
 */
public interface ApiService {

    //第三方登录信息后台绑定
    @FormUrlEncoded
    @POST("app/three/bind.htm")
    Call<LoginBean> bindThreelogin(@QueryMap Map<String, String> options, @Field("xingming") String xingming);

    //手机号登陆
    @POST("app/three/bind.htm")
    Call<LoginBean> phoneLogin(@QueryMap Map<String, String> options);


    //根据定位坐标查询附近经销商
    @FormUrlEncoded
    @POST("app/car/dealer.htm")
    Call<DistributorInfoBean> getDistributorInfo(@QueryMap Map<String, String> options,@Field("city") String city);

    //根据车型id查询与自己同品牌下的车型
    @POST("app/car/detail.htm")
    Call<CarPriceBean> getPrice(@QueryMap Map<String, String> options);

    //根据车型id查询与自己同品牌下的车型
    @POST("app/car/chexing.htm")
    Call<ChexingBean> getChexing(@QueryMap Map<String, String> options);

    //获取推荐热门视频
    @POST("app/home/hot.htm")
    Call<MainBean> getHotVideo(@QueryMap Map<String, String> options);

   //获取首页数据

    @POST("app/home/hot.htm")
    Call<MainBean>  getMain(@QueryMap Map<String, String> options);
    //获取汽车品牌列表
    @POST("app/brand/list.htm")
    Call<BrandListBean> getBrandList(@QueryMap Map<String, String> options);

    //获取品牌车型列表
    @POST("app/car/list.htm")
    Call<CarListBean> getCarList(@QueryMap Map<String, String> options);

    //获取车型多媒体信息列表
    @POST("app/car/info.htm")
    Call<CarMediaInfoBean>  getCarVideoList(@QueryMap Map<String, String> options);


    //获取收藏视频列表
    @POST("app/favorite/list.htm")
    Call<CollectionBean> getCollection(@QueryMap Map<String, String> options);

    //删除选中的视频收藏
    @POST("app/favorite/delete.htm")
    Call<DeletedBean>  deleteCollection(@QueryMap Map<String, String> options);

    //添加视频收藏
    @POST("app/favorite/add.htm")
    Response<String>  addCollection(@QueryMap Map<String, String> options);

    //查看历史视频观看记录
    @POST("app/video/wathList.htm")
    Call<HistoryBean>  getHistory(@Query("sessionid") String sessionid,@Query("isapp") String isapp);
    //删除历史视频观看记录
    @POST("app/video/deleteWatched.htm")
    Call<DeletedBean>  deleteHistory(@QueryMap Map<String, String> options);
    //热门搜索
    @POST("app/car/hot.htm")
    Call<HotCarSearchBean>  getHotSearch(@QueryMap Map<String, String> options);
    //车型数据库
    @POST("app/car/version.htm")
    Call<CarDBbean>  getCarDB(@QueryMap Map<String, String> options);

    //品牌数据库
    @POST("app/brand/version.htm")
    Call<BrandDBbean>  getBrandDB(@QueryMap Map<String, String> options);

    //uuid收集
    @POST("app/machine/save.htm")
    Call<ResultBean>  updateUUID(@QueryMap Map<String, String> options);


    //请求未读信息条数
    @POST("app/message/unRead.htm")
    Call<UnReadBean>  unRead(@QueryMap Map<String, String> options);

    //将消息标记为已读
    @POST("app/message/update.htm")
    Call<ResultBean>  updateMsg(@QueryMap Map<String, String> options);


    //请求信息列表
    @POST("app/message/list.htm")
    Call<MsgBean>  getMsgList(@QueryMap Map<String, String> options);



    //改名字
    @FormUrlEncoded
    @POST("app/user/nickname/update.htm")
    Call<NameBean> reName(@Query("sessionid") String sessionid,@Field("username") String xingming);

    //改头像
    @Multipart
    @POST("app/user/headPic/update.htm")
    Call<UploadPicBean> uploadPic(@Query("sessionid") String sessionid, @Part MultipartBody.Part  file);

    //解绑第三方账号
    @POST("app/three/upbind.htm")
    Call<LoginBean> unbind(@QueryMap Map<String, String> options);

    //意见反馈
    @FormUrlEncoded
    @POST("app/user/feedback.htm")
    Call<OpinionBean> opinionFeed(@Query("sessionid") String sessionid,@Field("content") String content);


    //解绑第三方账号
    @POST("app/push/save.htm")
    Call<ResultBean> saveToken(@QueryMap Map<String, String> options);



    //版本更新检测
    @POST("app/version.htm")
    Call<UpdateBean> updatecheck(@QueryMap Map<String, String> options);


    //推送信息展示
    @POST("app/push/hot.htm")
    Call<ExtensionBean> getExtension(@Query("msgId") String  msgId);


    //推送信息展示
    @POST("app/sms/send.htm")
    Call<ResultBean> getVerificationCode(@Query("mobile") String  msgId);

}
