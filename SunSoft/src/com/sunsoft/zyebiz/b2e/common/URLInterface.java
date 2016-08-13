package com.sunsoft.zyebiz.b2e.common;

public class URLInterface {

		/**
		 * 请求地址
		 */
//		public static final String SERVER_ADDRESS = "http://www.ygzykj.com/";//生产环境
//		public static final String SERVER_ADDRESS = "http://www.ygzykj.cn:8080/";//演示环境
//		public static final String SERVER_ADDRESS = "http://182.92.141.215/";//生产环境
//		public static final String SERVER_ADDRESS = "http://192.168.1.223:8080/";//生产环境
//		public static final String SERVER_ADDRESS = "http://192.168.1.222:8080/sunsoft/";  //测试环境
//		public static final String SERVER_ADDRESS = "http://101.201.146.179:8080/";
//		 public static final String SERVER_ADDRESS = "http://192.168.1.128:8080/sunsoft/";  //富国的
//		 public static final String SERVER_ADDRESS = "http://192.168.1.45:8080/sunsoft/";  //乔的
//		 public static final String SERVER_ADDRESS = "http://192.168.1.39:8080/";  //h5使用的
		public static String SERVER_ADDRESS = "";
		/**
		 * 公共前缀
		 */
		public static  String SERVER_URL_PREFIX = SERVER_ADDRESS;
				
		/**
		 * 登陆页轮播图片和视频地址接口
		 */
		public static  String LOGIN_PIC_VIDEO = SERVER_URL_PREFIX
				+ "main/slidead.json";
		/**
		 * 登陆之用户注册界面
		 */
		public static  String LOGIN_REGIST = SERVER_URL_PREFIX
				+ "user/adduser.json";

		/**
		 * 登陆页登陆按钮验证
		 */
		public static  String LOGIN_BUTTON = SERVER_URL_PREFIX
				+ "login/login.json";
		/**
		 * 登陆页登陆加载WebView
		 */
		public static  String LOGIN_BUTTON_WEBVIEW = SERVER_URL_PREFIX
				+ "login/localStorage.shtml";
		/**
		 * 检测H5是否登陆
		 */
		public static  String LOGIN_HTML_TO_LOGIN = SERVER_URL_PREFIX
				+ "login/toLogin.shtml";
		/**
		 * 版本更新
		 * versionCode=v1.0
		 * ]&type=Android
		 */
		public static  String VERSION_UPDATE = SERVER_URL_PREFIX + "version/checkVersion.json";
		/**
		 * 团购是否有数据
		 * TOKEN=?
		 */
		public static  String IS_HAVE_DATE_FOR_GROUP = SERVER_URL_PREFIX + "school/schoolgoodsisnull.json";
		
		
		/** ---------------------------------------------普通用户登陆-----------------------------------------*/
		/**
		 * 首页地址
		 */
		public static  String HOME_PAGE = SERVER_URL_PREFIX
				+ "main/main.shtml";
		/**
		 * 学校商城之团购地址
		 */
		public static  String GROUP_BUY_DATE = SERVER_URL_PREFIX
				+ "school/school.shtml";

		/**
		 * 学校商城团购之公告查看详细页
		 */
		public static  String GROUP_BUY_XIANG_XI = SERVER_URL_PREFIX
				+ "notice/noticeDetail.shtml";
		/**
		 * 学校商城团购之商品详情页
		 */
		public static  String GROUP_BUY_GOODS_XIANG_QING = SERVER_URL_PREFIX
				+ "goods/good.shtml";
		/**
		 * 学校商城团购之厂家详情页
		 */
		public static  String GROUP_BUY_FACTORY_MESSAGE = SERVER_URL_PREFIX
				+ "supplier/supplier.shtml";
		/**
		 * 学校商城团购之立即购买按钮
		 */
		public static  String GROUP_BUY_PAYMENT = SERVER_URL_PREFIX
				+ "order/toOrder.json";

		/**
		 * 学校商城团购之提交订单按钮
		 */
		public static  String GROUP_BUY_SUB = SERVER_URL_PREFIX
				+ "order/confirmOrder.shtml";
		/**
		 * 学校商城团购之无数据
		 */
		public static  String GROUP_BUY_NO_DATE = SERVER_URL_PREFIX
				+ "school/schoolgoodsisnull.json";

		/**
		 * 学校商城之零售地址
		 */
		public static  String SELL_RETAIL_BUY_DATE = SERVER_URL_PREFIX
				+ "retail/retail.shtml";
		/**
		 * 学校商城之零售详情地址
		 */
		public static  String SELL_RETAIL_DETAIL = SERVER_URL_PREFIX
				+ "retail/retailDetail.shtml";
		/**
		 * 学校商城零售之立即购买按钮
		 */
		public static  String GROUP_BUY_RETAIL_PAYMENT = SERVER_URL_PREFIX
				+ "retail/retailtoorder.json";
		/**
		 * 购物车获取数据列表页
		 */
		public static  String SHOPPING_CART_DATE = SERVER_URL_PREFIX
				+ "shoppingCart/getShoppingCart.json";
		/**
		 * 购物车之删除功能
		 */
		public static  String SHOPPING_CART_DEL = SERVER_URL_PREFIX
				+ "shoppingCart/deleteShoppingCartGoods.json";

		/**
		 * 购物车之数量修改功能
		 */
		public static  String SHOPPING_CART_NUMBER = SERVER_URL_PREFIX
				+ "shoppingCart/updateShoppingCartGoods.json";
		/**
		 * 购物车之结算中心
		 */
		public static  String ACCOUNT_CENTER = SERVER_URL_PREFIX
				+ "shoppingCart/cartojiesuan.shtml";
		/**
		 * 购物车之结算中心收货地址选择
		 */
		public static  String ACCOUNT_CENTER_ADDRESS = SERVER_URL_PREFIX
				+ "shoppingCart/cartojiesuan.shtml";

		/**
		 * 我的智园之用户资料
		 */
		public static  String USER_MESSAGE = SERVER_URL_PREFIX
				+ "user/getUserDetail.json";

		/**
		 * 我的订单全部
		 */
		public static  String MY_ORDER_ALL = SERVER_URL_PREFIX
				+ "order/orderList.shtml";
		/**
		 * 我的订单退货换货
		 */
		public static  String MY_ORDER_TUIHUO = SERVER_URL_PREFIX
				+ "/order/returnGoods.shtml";

		/**
		 * 管理收货地址列表页
		 */
		public static  String USER_SHOU_HUO_ADDRESS = SERVER_URL_PREFIX
				+ "address/addressList.shtml";
		/**
		 * 管理收货之修改地址页
		 */
		public static  String USER_UPDATE_ADDRESS = SERVER_URL_PREFIX
				+ "user/updateAddress.shtml";
		/**
		 * 管理收货之默认地址接口
		 */
		public static  String USER_ADDRESS_MOREN = SERVER_URL_PREFIX
				+ "/user/getdefultaddress.json";
		/**
		 * 我的智园之关于智园页
		 */
		public static  String ABOUT_ZHIYUAN = SERVER_URL_PREFIX
				+ "aboutsunsoft/aboutsunsoft.shtml";
		/**
		 * 我的智园之意见反馈提交
		 */
		public static  String ABOUT_ZHIYUAN_ADVICE = SERVER_URL_PREFIX
				+ "feedback/insertfeedback.json";
		/**
		 * 我的智园之设置修改密码
		 */
		public static  String ZHIYUAN_SET_PASSWORD = SERVER_URL_PREFIX
				+ "user/updatepassword.json";
		/**
		 * 我的智园之设置退出登陆
		 */
		public static  String ZHIYUAN_SET_EXIT_LOGIN = SERVER_URL_PREFIX
				+ "login/exitlogin.json";
		/**
		 * 我的智园之绑定手机
		 */
		public static  String ZHIYUAN_SET_CHANGE_PHONE = SERVER_URL_PREFIX
				+ "user/updatemobilephone.json";
		/**
		 * 家长身份登录获取用户信息?token=
		 */
		public static  String PARENT_LOGIN_GET_USER_INFO = SERVER_URL_PREFIX
				+ "user/getstudentdetail.json";
		
		/**
		 * 短信发送接口?mobile=13835899466&type=0    type   0：用户注册 1：找回密码
		 */
		public static  String MOBILE_SEND_MESSAGE = SERVER_URL_PREFIX
				+ "send/getMessage.json";
		
		/**
		 * 登陆之重置密码
		 */
		public static  String LOGIN_RESET_PASSWORD = SERVER_URL_PREFIX
				+ "user/resetpassword.json";
		/**
		 * 国付宝通知后台地址
		 */
		public static  String PAY_NOTIFY_SERVICE = "http://101.201.146.179:8080/sunsoft-main/gfbpay/payNotic.do";
		/**
		 * 国付宝通知后台地址
		 */
		public static  String RECEIVE_DATE = SERVER_URL_PREFIX+ "pay/getorderstate.json";
		
		public URLInterface(String SERVER_ADDRESS){
			this.SERVER_ADDRESS = SERVER_ADDRESS;
			reSetUrl();
		}
		
		private void reSetUrl() {
			LOGIN_PIC_VIDEO = SERVER_ADDRESS + URLInterface.LOGIN_PIC_VIDEO;
			LOGIN_REGIST = SERVER_ADDRESS + URLInterface.LOGIN_REGIST;
			LOGIN_BUTTON = SERVER_ADDRESS + URLInterface.LOGIN_BUTTON;
			LOGIN_BUTTON_WEBVIEW = SERVER_ADDRESS + URLInterface.LOGIN_BUTTON_WEBVIEW;
			LOGIN_HTML_TO_LOGIN = SERVER_ADDRESS + URLInterface.LOGIN_HTML_TO_LOGIN;
			VERSION_UPDATE = SERVER_ADDRESS + URLInterface.VERSION_UPDATE;
			IS_HAVE_DATE_FOR_GROUP = SERVER_ADDRESS + URLInterface.IS_HAVE_DATE_FOR_GROUP;
			HOME_PAGE = SERVER_ADDRESS + URLInterface.HOME_PAGE;
			GROUP_BUY_DATE = SERVER_ADDRESS + URLInterface.GROUP_BUY_DATE;
			GROUP_BUY_XIANG_XI = SERVER_ADDRESS + URLInterface.GROUP_BUY_XIANG_XI;
			GROUP_BUY_GOODS_XIANG_QING = SERVER_ADDRESS + URLInterface.GROUP_BUY_GOODS_XIANG_QING;
			GROUP_BUY_FACTORY_MESSAGE = SERVER_ADDRESS + URLInterface.GROUP_BUY_FACTORY_MESSAGE;
			GROUP_BUY_PAYMENT = SERVER_ADDRESS + URLInterface.GROUP_BUY_PAYMENT;
			GROUP_BUY_SUB = SERVER_ADDRESS + URLInterface.GROUP_BUY_SUB;
			GROUP_BUY_NO_DATE = SERVER_ADDRESS + URLInterface.GROUP_BUY_NO_DATE;
			SELL_RETAIL_BUY_DATE = SERVER_ADDRESS + URLInterface.SELL_RETAIL_BUY_DATE;
			SELL_RETAIL_DETAIL = SERVER_ADDRESS + URLInterface.SELL_RETAIL_DETAIL;
			GROUP_BUY_RETAIL_PAYMENT = SERVER_ADDRESS + URLInterface.GROUP_BUY_RETAIL_PAYMENT;
			SHOPPING_CART_DATE = SERVER_ADDRESS + URLInterface.SHOPPING_CART_DATE;
			SHOPPING_CART_DEL = SERVER_ADDRESS + URLInterface.SHOPPING_CART_DEL;
			SHOPPING_CART_NUMBER = SERVER_ADDRESS + URLInterface.SHOPPING_CART_NUMBER;
			ACCOUNT_CENTER = SERVER_ADDRESS + URLInterface.ACCOUNT_CENTER;
			ACCOUNT_CENTER_ADDRESS = SERVER_ADDRESS + URLInterface.ACCOUNT_CENTER_ADDRESS;
			USER_MESSAGE = SERVER_ADDRESS + URLInterface.USER_MESSAGE;
			MY_ORDER_ALL = SERVER_ADDRESS + URLInterface.MY_ORDER_ALL;
			MY_ORDER_TUIHUO = SERVER_ADDRESS + URLInterface.MY_ORDER_TUIHUO;
			USER_SHOU_HUO_ADDRESS = SERVER_ADDRESS + URLInterface.USER_SHOU_HUO_ADDRESS;
			USER_UPDATE_ADDRESS = SERVER_ADDRESS + URLInterface.USER_UPDATE_ADDRESS;
			USER_ADDRESS_MOREN = SERVER_ADDRESS + URLInterface.USER_ADDRESS_MOREN;
			ABOUT_ZHIYUAN = SERVER_ADDRESS + URLInterface.ABOUT_ZHIYUAN;
			ABOUT_ZHIYUAN_ADVICE = SERVER_ADDRESS + URLInterface.ABOUT_ZHIYUAN_ADVICE;
			ZHIYUAN_SET_PASSWORD = SERVER_ADDRESS + URLInterface.ZHIYUAN_SET_PASSWORD;
			ZHIYUAN_SET_EXIT_LOGIN = SERVER_ADDRESS + URLInterface.ZHIYUAN_SET_EXIT_LOGIN;
			ZHIYUAN_SET_CHANGE_PHONE = SERVER_ADDRESS + URLInterface.ZHIYUAN_SET_CHANGE_PHONE;
			PARENT_LOGIN_GET_USER_INFO = SERVER_ADDRESS + URLInterface.PARENT_LOGIN_GET_USER_INFO;
			MOBILE_SEND_MESSAGE =  SERVER_ADDRESS + URLInterface.MOBILE_SEND_MESSAGE;
			LOGIN_RESET_PASSWORD = SERVER_ADDRESS + URLInterface.LOGIN_RESET_PASSWORD;
			PAY_NOTIFY_SERVICE = SERVER_ADDRESS + URLInterface.PAY_NOTIFY_SERVICE;
			RECEIVE_DATE = SERVER_ADDRESS + URLInterface.RECEIVE_DATE;
		}
		
//		/**----------------------------------------------教育局用户登录--------------------------------------------------*/
//		/**
//		 * 教育局之订购通知
//		 */
//		public static final String TONGZHI_ORDER = SERVER_URL_PREFIX
//		+ "education/educate.shtml";
//		/**
//		 * 教育局之通知管理
//		 */
//		public static final String TONGZHI_MANAGER = SERVER_URL_PREFIX
//		+ "education/edumanage.shtml";
//		/**
//		 * 教育局之公告详情
//		 */
//		public static final String GONGGAO_XQ = SERVER_URL_PREFIX
//		+ "education/tonoticedetail.shtml";
//		/**
//		 * 教育局之学校信息
//		 */
//		public static final String SCHOOL_MESSAGE = SERVER_URL_PREFIX
//		+ "education/eduinfo.shtml";
//		/**
//		 * 教育局之意见反馈
//		 */
//		public static final String ADVICE_FANKUI = SERVER_URL_PREFIX
//		+ "education/Getfeedback.shtml";
//		/**
//		 * 教育局之学校管理
//		 */
//		public static final String SCHOOL_MANAGER = SERVER_URL_PREFIX
//		+ "education/schoolmanage.shtml";
	//	
	//	
	//	
	//	
//		/** ------------------------------------学校管理员用户登陆-----------------------------------------------------------*/
	//	
//		/**
//		 * 学校管理员登陆之公告管理
//		 */
//		public static final String SCHOOL_LOGIN_GONGGAO_MANAGER = SERVER_URL_PREFIX
//		+ "schoolclass/announcement.shtml";
//		/**
//		 * 学校管理员登陆之订单管理
//		 */
//		public static final String SCHOOL_LOGIN_ORDER_MANAGER = SERVER_URL_PREFIX
//		+ "schoolclass/ordermanagerment.shtml";
//		/**
//		 * 学校管理员登陆之订单管理之班级订单二级页面
//		 */
//		public static final String SCHOOL_LOGIN_ORDER_MANAGER_CLASS = SERVER_URL_PREFIX
//		+ "schoolclass/classorder.shtml";
//		/**
//		 * 学校管理员登陆之订单管理之班级订单之详情三级页面
//		 * noticeId=327ee569fc4a4a2794427e99bf9ceae2
//		 * &schoolId=f83bb05ffaff44c3b22b4bb129ba5d35
//		 * &gradeNo=01
//		 * &classNo=01
//		 */
//		public static final String SCHOOL_LOGIN_ORDER_MANAGER_CLASS_XQ = SERVER_URL_PREFIX
//		+ "schoolclass/classorderdetail.shtml";
//		/**
//		 * 学校管理员登陆之订单管理之待支付订单二级页面
//		 */
//		public static final String SCHOOL_LOGIN_ORDER_MANAGER_DAI_ZHIFU = SERVER_URL_PREFIX
//		+ "schoolclass/nopayorderList.shtml";
//		/**
//		 * 学校管理员登陆之订单管理之未购买二级页面
//		 */
//		public static final String SCHOOL_LOGIN_ORDER_MANAGER_NO_BUY = SERVER_URL_PREFIX
//		+ "schoolclass/nobuystudentList.shtml";
//		/**
//		 * 学校管理员登陆之学校管理
//		 */
//		public static final String SCHOOL_LOGIN_SCHOOL_MANAGER = SERVER_URL_PREFIX
//		+ "schoolclass/schoolmanage.shtml";
//		/**
//		 * 学校管理员登陆之学校管理之班级详情
//		 * gradeNo=11
//		 * &classNo=02
//		 */
//		public static final String SCHOOL_LOGIN_SCHOOL_MANAGER_CLASS_XQ = SERVER_URL_PREFIX
//		+ "schoolclass/classdetail.shtml";
//		/**
//		 * 学校管理员登陆之公告管理之公告内容二级页面
//		 */
//		public static final String SCHOOL_LOGIN_GONGGAO_MANAGER_CONTENT = SERVER_URL_PREFIX
//		+ "schoolclass/orderannouncement.shtml";
//		/**
//		 * 学校管理员登陆之我的智园之意见反馈二级页面
//		 */
//		public static final String SCHOOL_LOGIN_MY_GARDEN_ADVICE = SERVER_URL_PREFIX
//		+ "schoolclass/Getfeedback.shtml";
//		/**
//		 * 学校管理员登陆之我的智园之学校信息二级页面
//		 */
//		public static final String SCHOOL_LOGIN_MY_GARDEN_SCHOOL_MESSAGE = SERVER_URL_PREFIX
//		+ "schoolclass/schooinformate.shtml";
//		/**
//		 * 学校管理员登陆之首页
//		 */
//		public static final String SCHOOL_LOGIN_CONSUL = SERVER_URL_PREFIX
//		+ "schoolclass/schooladmin.shtml";
		
		
		
		
	}

