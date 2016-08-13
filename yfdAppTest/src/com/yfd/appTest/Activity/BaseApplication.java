package com.yfd.appTest.Activity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.service.LocationService;
import com.baidu.location.service.WriteLog;
import com.baidu.mapapi.SDKInitializer;
import com.yfd.appTest.Beans.LoginBeans;
import com.yfd.appTest.Beans.LoginBeansc;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

public class BaseApplication extends Application {
	//�ٶȵ�ͼ
	public LocationService locationService;
    public Vibrator mVibrator;
    public static String lon,lat;
	//update����
	public static boolean ifupdate = false;
	public static String updateUrl = "http://222.73.22.122:8083/hyfdsta/appupdate/android/yfdAppR.apk";
	public static String updateszvelocal = "/sdcard/updateApk/";
	public static String Savefilename = updateszvelocal + "yfdAppR.apk";
	public static String xmlName = "kversion.xml";
	public static String updateFile = "http://222.73.22.122:8083/hyfdsta/appupdate/android/";
	public static String versioninfo;
	
	
	public static List<Activity> listacty=new ArrayList<Activity>();
	
	public static RequestQueue Requeue;
	
	public static LoginBeans loginbeans;
	public static LoginBeansc loginbeansc;
	
	public static boolean isMainPic = false;
	public static String test_ip="http://112.230.203.115:8083/";
	//����urlpublic static String IP="http://222.73.22.122:8083/hyfdsta/";
	public static String IP="http://222.73.22.122:8083/hyfdsta/";
//	public static String IP="http://192.168.1.91:8080/hyfdsta/";
//	public static String IP_L="http://222.73.22.122:8083/hyfdsta/";
	/**注册上传数据接口*/
	public static String  REGIST_DATA_URL=IP+"RegisterController/addRegister.html";
	/**协议页面地址*/
	public static String XIEYI_URL = IP+"xieyi.jsp";
	public static String  LOGIN_URL=IP+"LoginController/loginCheck.html";
	
	public static String IFLORK_URL=IP+"LoginController/getUserType.html";
		
	public static String  CHANGE_PSD_URL=IP+"UserController/userPassOp.html";
	public static String  CHANGE_PHONE_URL=IP+"UserController/userInfoOp.html";	
	public static String  SAOMIAO_URL=IP+"ScardController/getScardInfo.html";
	public static String  Phkk_URL=IP+"MobileInfoController/addOrUpdate.html";
	public static String  KSEARCH_LIST=IP+"MobileInfoController/getList.html";
	public static String  bkgSEARCH_LIST=IP+"AppController/ftToList.html";
	public static String YJFK_URL=IP+"OpinionController/addOpinion.html";
	
	public static String MAPS_URL=IP+"AppController/getLoginLogList.html";
	
	public static String REGEST_URL=IP+"RegisterController/addRegister.html";
	
	public static String BUKA_URL=IP+"AppController/toAddOrUpdateFill.html";
	public static String GUOHU_URL=IP+"AppController/toAddOrUpdateTransfer.html";
	public static String HFCZ_URL="rcmp/jf/api/app/quotaOrder";
	//public static String IPC="http://112.230.203.115:8083/";
    public static String IPC="http://www.sdhyfd.com/";
	public static String  CHANGE_PSD_URLLF=IPC+"UserController/userPassOp.html";
    public static String  LOGIN_URL_L=IPC+"rcmp/jf/api/login";
    public static String searchHF_URL="http://222.73.22.122/appwxzf/OrderSearch/";
    public static String searchLL_URL="http://222.73.22.122/appwxzf/OrderSearchll/";
	public static String  PINFO_SB=IPC+"rcmp/jf/api/getPhoneAddress";
	public static String  PHPNENUM_SB=IPC+"rcmp/jf/api/app/getOrderPriceByNumber";
	public static String INTERNET_ZHE_KOU ="http://192.168.1.78:8080/rcmp/jf/api/app/getOrderPriceByNumber";
	//��ֵurl	
	public static BaseApplication instance;
	public static String  LOGIN_URLC=IPC+"/rcmp/jf/api/login";
	public static String  GET_PRICE=IPC+"rcmp/jf/api/app/queryBalance";
	
//	public static String WEIXIN_HF_URL="http://581698.ichengyun.net/appwxzf/WxPayServer/";
//	public static String WEIXIN_HF_URL="http://192.168.1.49:8080/appwxzf/WxPayServer/";
//	public static String WEIXIN_LL_URL="http://581698.ichengyun.net/appwxzf/LlPayServer/";
//	public static String WEIXIN_HF_URL="http://dijiejiaofei.com/appwxzf/WxPayServer/";
	public static String WEIXIN_HF_URL="http://155i2n7933.iok.la/appwxzf/WxPayServer/";
//	public static String WEIXIN_HF_URL="http://192.168.1.49:8080/appwxzf/WxPayServer/";
//	public static String WEIXIN_LL_URL="http://dijiejiaofei.com/appwxzf/LlPayServer/";
	public static String WEIXIN_LL_URL="http://155i2n7933.iok.la/appwxzf/LlPayServer/";
	public static int  which=0;
	
	public static int  FromImg=0;
	
	public static String Saoma;
	
	private boolean isDownload;

	public boolean isDownload() {

		return isDownload;

	}
public static BaseApplication getInstance() {

		return instance;

	}
	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance=this;
		Requeue = Volley.newRequestQueue(this);
		   /***
         * ��ʼ����λsdk��������Application�д���
         */
        locationService = new LocationService(this);
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
       WriteLog.getInstance().init(); // ��ʼ����־
        SDKInitializer.initialize(this);
	}
}
