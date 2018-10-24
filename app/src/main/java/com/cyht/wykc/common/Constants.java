package com.cyht.wykc.common;


import com.cyht.wykc.mvp.view.base.BaseApplication;
import com.cyht.wykc.utils.DirectoryUtils;

import java.io.File;


/**
 * Author： hengzwd on 2017/5/31.
 * Email：hengzwdhengzwd@qq.com
 */

public class Constants extends CYHTConstants{

    /**
     * 不允许new
     */
    private Constants() {
        throw new Error("Do not need instantiate!");
    }

//    	public static final String BASE_URL = "http://192.168.191.1:8080/51kanche/";
//    public static final String BASE_URL = "http://192.168.191.8:8081/51kanche/";
   // 国内版本
  //  public static final String BASE_URL = "http://android.woyaokanche.com:8082/51kanche/";
        //海外版
    public static final String BASE_URL="http://en.ccarccar.com:8085/51kanche/";

//    public static final String BASE_URL =  "http://test.woyaokanche.com:8080/51kanche/";
//

    //SD卡路径
    public static final String PATH_DATA = DirectoryUtils.getDiskCacheDirectory(BaseApplication.mContext, "data").getAbsolutePath();
    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_NET_CACHE = PATH_DATA + File.separator + "NetCache";
    public static final String PATH_APK_CACHE = PATH_DATA + File.separator + "ApkCache";


    public static final String DOMAIN_1 = "refreshing...";
    public static final String DOMAIN_2 = "refreshing...";
    public static final String ISFIRSTENTRY = "is_first_entry";
    public static final String ISFIRSTGUIDE = "is_first_guide";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TOUXIANG = "touxiang";
    public static final String USER_INFO_BEAN = "user_info_bean";
    public static final int DEFAULT_TIMEOUT = 5;

    public static final String USER_ID = "user_id";

    public static final String REGISTER_CODE = "register_code";


    //作为登录的参数，固定这个写法
    public static final String OSTYPE = "2";
    public static final String PRESS_AGAIN = "再按一次退出";
    public static int IS_FIRST_OR_NOT = 0;//是否是第一次安装进入程序

    public static int HAS_LOGIN_OR_NOT = 0;//是否已经登录  0 未登录 1 已经登录

    //drawlayout是否展开  0没展开  1已经展开
    public static int IS_DRAW_OPEN = 0;


    //检测App升级
    public static final int CHECKUPDATE = 0;


    public static final int FROM_SPLASH = 0;
    public static final int FROM_MAIN = 1;
    public static final int FROM_GUIDE = 2;
    public static int VERSION_INIT_COUNT = 0;


    //业务功能模块字段
    public static final String SESSION_ID = "sessionid";
    public static final String CAR_ID = "carid";
    public static final String CAR_NAME = "carname";
    public static final String YINDAO_SHOW = "yindao_show";


    public static final String BRANDLIST = "brandList";
    public static final String DATA = "data";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String LOGO = "logo";
    public static final String CARLIST = "carList";
    public static final String BRAND = "brand";
    public static final String TYPE = "type";
    public static final String FAVORITES = "favorites";
    public static final String WATCHED = "watched";
    public static final String HEADPIC = "headPic";
    public static final String CXMC = "cxmc";
    public static final String CXID = "cxid";

    public static final String USERCODE = "usercode";
    public static final String XINGMING = "xingming";
    public static final String PHOTO = "photo";
    public static final String VERSION = "version";
    public static final String QQ_NAME = "qq_name";
    public static final String QQ_USERCODE = "qq_usercode";
    public static final String WEIXIN_NAME = "weixin_name";
    public static final String WEIXIN_USERCODE = "weixin_usercode";
    public static final String SINA_NAME = "sina_name";
    public static final String SINA_USERCODE = "sina_usercode";

    //    public static final String KEFUDIANHUA = "kefudianhua";
    public static final String RETURNURL = "returnurl=";

    public static final String OPERATE_SUCCESS = "true";
    public static final String OPERATE_FAILURE = "false";

    public static final String HAS_NEW_MESSAGE = "hasnewmessage";
    public static final String CAR_CHENYONG = "1";
    public static final String CAR_SHANGYONG = "2";

    public static String sessionid;
    public static String channelid;
    public static String carid;
    public static String touxiang;
    public static String username;
    public static String city;

    //UMENG相对手机的devicestoken
    public static String devicestoken;
    public static String DEVICESTOKEN="token";

    //系统字段
    public static final String SYSTEM = "system";
    public static final String ANDROID = "1";

    public static final String TYPEVALUE = "typevalue";

    public static final int REQUEST_LOGIN_CODE = 0x001;
    public static final int REQUEST_SELECT_CODE = 0x002;
    public static final int REQUEST_UNBIND_CODE = 0x003;


    //代表各种逻辑行为
    public static final int NOT_REFRESH = 0X005;
    public static final int BE_REFRESH = 0X006;
    public static final int DO_LOAD_HEADPIC = 0X007;
    public static final int DO_SET_NAME = 0X008;
    public static final int DO_UNLOGIN = 0X009;
    public static final int DO_LOGIN = 0X010;
    public static final int TO_LETTER = 0X011;
    public static final int TO_EXTENSION = 0X012;




    public static int PAGESIZE = 10;    //主页加载一页显示几个视频
    public static int CURRENTPAGE =0 ;  //当前第几页

    //代表activity 或者fragment固定字段

    public static final int MAINACTIVITY = 0X007;
    public static final int MAINFRAGMENT = 0X008;
    public static final int CARBRANDFRAGMENT = 0X006;
    public static final int PASSENGERCARFRAGMENT = 0X009;
    public static final int COMMERCIALCARFRAGMENT = 0X010;
    public static final int   TBSWEBVIEW=0X011;
    public static final int HISTROYFRAGMENT = 0X012;
    public static final int COLLECTIONFRAGMENT = 0X013;
    public static final int ABOUTFRAGMENT = 0X014;
    public static final int SETTINGFRAGMENT = 0X015;
    public static final int OPINIONFRAGMENT = 0X016;
    public static final int NAMEACTIVITY= 0X017;
    public static final int CONTRACTFRAGMENT = 0X018;
    public static final int LETTERSFRAGMENT = 0X019;
    public static final int PERSONALCENTERFRAGMENT = 0X020;
    public static final int SETTINGACTIVITY = 0X021;
    public static final int LETTERSDETAILACTIVITY = 0X022;
    public static final int VIDEOLISTACTIVITY = 0X023;
    public static final int BASEAPPLICATION = 0X024;
    public static final int RECIEVEUMENGPUSHSERVICE = 0X025;
    public static final int TWEETACTIVITY = 0X026;







    //出错的代表字段
    public static int NETWORK_ERROR = 0X100;
    public static int REQUEST_ERROR = 0X101;



    public static int  TEXT_TYPE_MID=0;//下拉列表控件toprightmenu中recycleview中填充哪一种风格的itemview，0为中间对齐，1为左对齐
    public static int  TEXT_TYPE_left=1;



    public static int screenWidth = 720;
    public static int screenHeight = 1280;


    //定位数据
    public  static String  locationAddress;
    public static double latitude;
    public static double longitude;

}
