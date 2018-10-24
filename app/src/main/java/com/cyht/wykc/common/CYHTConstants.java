package com.cyht.wykc.common;


import com.cyht.wykc.utils.StoreSpaceUtils;

public class CYHTConstants {
    public static final String BASE_SERVER_URL = "http://android.woyaokanche.com:8082/51kanche";
//
//	public static final String BASE_SERVER_URL =  "http://test.woyaokanche.com:8080/51kanche/";
//	public static final String BASE_SERVER_URL = "http://192.168.191.1:8080/51kanche";

    public static final String BASE_SERVER_URL_WX = "http://android.woyaokanche.com/51kanche";

//	public static final String BASE_SERVER_URL_WX =  "http://test.woyaokanche.com:8080/51kanche";

//	public static final String BASE_SERVER_URL_WX = "http://192.168.191.1:8080/51kanche";

//	public static final String BASE_SERVER_URL = "http://ibuckle.cn/51kanche";
//
//	public static final String BASE_SERVER_URL = "http://192.168.191.2:8081/51kanche";
    //	public static final String BASE_SERVER_URL = "http://192.168.0.198:9094";


    public static final int COMPRESS_PHOTO_SIZE = 800;
    public static final String PAGE = "page";
    public static final String NEXT_PAGE = "nextPage";
    public static final String LIST = "list";

    //SDCard路径
    public static String SDCARD_PATH = StoreSpaceUtils.getSDCardPath();

    /**
     * 本地存储总目录
     */
    public static String SAVE_DIR_BASE = SDCARD_PATH.concat("/wykc/");

    /**
     * 图片存储位置
     */
    public static String SAVE_DIR_GLIDE_CACHE = SAVE_DIR_BASE.concat("GlideCache/");
    /**
     * 图片存储位置
     */
    public static String SAVE_DIR_PHOTO = SAVE_DIR_BASE.concat("photo/");
    /**
     * 头像存储位置
     */
    public static String SAVE_DIR_ICON = SAVE_DIR_BASE.concat("icon/");
    /**
     * 视频存储位置
     */
    public static String SAVE_DIR_VIDEO = SAVE_DIR_BASE.concat("video/");
    /**
     * 语音存储位置
     */
    public static String SAVE_DIR_VOICE = SAVE_DIR_BASE.concat("voice/");
    /**
     * 奔溃存储路径
     */
    public static String SAVE_DIR_CRASH = SAVE_DIR_BASE.concat("crash/");


    public static final String USER_ID = "userid";
    public static final String TEL = "tel";
    public static final String VCODE = "vcode";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String NEWPASSWORD = "newpassword";
    public static final String CONTENT = "content";
    public static final String TOUXIANG = "touxiang";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";
    //	public static final String FENXIANG = "fenxiang";
    public static final String URL = "url";
    public static final String ISNEW = "isnew";//1:是,0:否
    public static final String VERSION = "version";

    public static final String USERTYPE = "usertype";
    public static final String CHANNELID = "channelid";
//	public static final String YAOQINGMA = "yaoqingma";

    public static String usertype = "0";

    public static final String SEX = "sex";
    public static final String NIAN = "nian";
    public static final String YUE = "yue";
    public static final String RI = "ri";

    public static final String TITLE = "title";
    public static final String TIME = "time";

    public static final String ID = "id";
    public static final String IMAGEURL = "imageurl";

    public static final String KEY = "key";
    public static final String HOT_LIST = "remenlist";
    public static final String CITY = "city";
    public static final String PINYIN = "pinyin";
    public static final String FIRST_LETTER = "szm";
    public static final String HOT = "isremen";

    public static final String DATE = "date";
    public static final String TYPEVALUE = "typevalue";

    public static int screenWidth = 720;
    public static int screenHeight = 1280;


}
