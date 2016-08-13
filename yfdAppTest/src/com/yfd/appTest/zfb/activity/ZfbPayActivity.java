package com.yfd.appTest.zfb.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.yfd.appTest.Activity.BaseActivity;
import com.yfd.appTest.Beans.ZFBseverbackBeans;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;

public class ZfbPayActivity extends BaseActivity {	
	
	// 商户PID
	public static final String PARTNER = "";
	// 商户收款账号
	public static final String SELLER = "";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIIEoAIBADANBgkqhkiG9w0BAQEFAASCBIowggSGAgEAAoH8MlHd0Cjrk+z4KJQ+FL/Lyuhtqb8o1pSoBmTFHA/O+xjcnzZvNonPCzOV2lYteanFiQgC2xOFZfIpV/Xz3n09J1A/2mqN0XRKZV0pxLl4/HOAPK8iUfbgX3Yx7OfmcNg2PyIPPb5tyKAkkLOvpXiu8knRoFXj+OKtjmLVcbl6ju1XgU9qkLnLXEOWSrF4IYDoFKnQ3EEE5lXWjjD5cEfbN7MZatVP6/C8ZpN/o7s10W3Q5NISCO9yq0oT6DMU2fDtGHy9XYMD6tAR+jcbqw0wx3LJbV6aI3juTN3KMjnEn+njESEMGL1ojHaRf1G+Ntg5hRWADj2+DM04hN9XAgMBAAECgfwaLXjFh+Q2Sns3MRey16x/XLdgLXBjYWagHOBmHttAychMk5b7ummZnDaDJX324xk+lopVyiwFwxs7BVrfDiw4PYlaaUBqDKN/ZB4Ml6iXPMcjysCB/3rubqC+bE33uqNN6LwhFOYsDxO2q6Pe44+vwDNrxAMbQWGkx0nfJexkFUYcqP3NVqBVMre8i/KgO4bsUxQIrHpPaylQ7Tcb4DqCfRxuCuDIZXikOxPdw4ZouV1BilHMwcJCLyUyxdoQDjvrKHpLe4gR0j76GY2E8ExvxVJTGuBcq5HTlm4bDzvIn3IT8tH6jkAiJJISGFxPh3G0jSbCbik9LFNXopECfnfu9z5VRCj34d0OF9c0Y6k+v2fhplSfK3n0DncAxSJ5tU7LtjETQzTdk5UXrsi7tdkkiRFPUT7xsMPFN5Lt/Mt4oJ4ikoO4ql9/lvjuIA5oY/4JgxYLhnn/R2wvy+gXEqG921Ee8gw2UG26BGIcFcdS0ZOzJI1FUbqfz7yMIwJ+a2iP2hpfqe99+W1Kjo1Zk/quz4RUjNMJ4o6eJYadFiQeh+jxR9l0mGSAGeJacAyjg5LOMbuGANKev9FVW7B90IGcEwAJofzIAFeEgiUE7xcSQZyO4vJHfXmaY7vXcZNwWnGwttseDFiFN8Pc8OuaxU1DFylL/npmf9F4MMk9An5Pxwt70SgXwFvjuV4ubtDkYbLcnQMRQxeedbOxgTegqSjrkyJMHq4T2lgzUp+XMxXRrTXn58T5g7e7YdKHz/raNhFRX1nqeMoL4ElCqNN2AgaRky45Wc1IFYEi++A9MCvanvnmShlj+jUh6BzGTjxGbFawUve7aSLmayu6FdUCfgiqnzDR//YORPPjRajK7vdpusEzxD64qIuHRmtwuSwYLf55ZT6O5UGGmm98PB/eZTMwnUKWBvH6DKUdlu2XCA/S5W0DC8PuSZT9esulgQhprCdvInhdZDdToIl7lp6rbt1dXwziEUKdweIGA8XMvOg+m7M6uc8GwU5+8Fu/YQJ+AKtig52wQwPsXGeFvW/sMTl6dZf9deq+S10gWonV8Erov25938aVEEhflFGh2yfRYN3ggX++f6zV4pKmu9336yJ9/mG6PEGw2t0s/r7lOleSB4gyeeoowTQYJivCMF21ciJl+JL6iRzAKagshbUC5gk+pc5fVVS7d3N+dLFF";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "";
	private static final int SDK_PAY_FLAG = 1;
	
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(ZfbPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(ZfbPayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(ZfbPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				
				finish();
				
				break;
			}
			default:
				break;
			}
		};
	};
	 ZFBseverbackBeans zfbbackbeans;
	 
		private Handler handlerzfb=new Handler(){
			
			public void handleMessage(android.os.Message msg) {
				dimissloading();
				if(msg.obj!=null){
					

					zfbbackbeans = (ZFBseverbackBeans) Utils.jsonToBean(msg.obj.toString(), ZFBseverbackBeans.class);
                     pay();
					
				}else{
					
					Toast.makeText(getApplicationContext(),"请求失败！", 1000).show();
					finish();
				}
				
			};
			
		};
		String p;
		String param;
		String pricezhe;
		String price;
		String signmd5;
		String czl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		p=intent.getStringExtra("p");
		price=intent.getStringExtra("pno");
		pricezhe=intent.getStringExtra("pz");
		czl=intent.getStringExtra("czl");
		showloading("");
		
		if(Utils.isEmpty(czl)){
		
		//String param="callbackUrl=http://127.0.0.1"+"&customerOrderId="+Utils.Get32ocdeStr()+"&orderType=2"+"&phoneNo="+phonenumedit.getText().toString()+"&scope=nation"+"&spec="+String.valueOf(Integer.valueOf(sjp)*100)+"&terminalName="+BaseApplication.loginbeans.getMsg().getUsName()+"&timeStamp="+Utils.get17YYMMDDSSS();
		AnsyPost.getkkSearch("http://114.55.26.121:8001/zfb/SjczServlet?flag=1&app_username=dianshang10&mobilenumber="+p+"&realprice="+price+"&disprice="+pricezhe, handlerzfb);
//		System.out.println("http://114.55.26.121:8001/zfb/SjczServlet?flag=1&app_username=dianshang10&mobilenumber="+p+"&realprice="+price+"&disprice="+pricezhe);
	    
		}
		else{
						
		AnsyPost.getkkSearch("http://114.55.26.121:8001/zfb/LlczServlet?flag=1&app_username=dianshang10&mobilenumber="+p+"&discount="+price+"&billsize="+pricezhe+"&czll="+czl, handlerzfb);
		System.out.println("http://114.55.26.121:8001/zfb/LlczServlet?flag=1&app_username=dianshang10&mobilenumber="+p+"&discount ="+price+"&billsize="+pricezhe+"&czll="+czl);
			
		}	
	   }

	
	    public void pay() {
//		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
//			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
//					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialoginterface, int i) {
//							//
//							finish();
//						}
//					}).show();
//			return;
//		}
		String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

		String sign = sign(orderInfo);
		
		
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + zfbbackbeans.getSign()+ "\"&" + getSignType();
		System.out.println(sign);
		System.out.println(payInfo);

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(ZfbPayActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
	
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + zfbbackbeans.getPartner()+ "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + zfbbackbeans.getSeller_id() + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + zfbbackbeans.getOut_trade_no() + "\"";
//		String[] spname = zfbbackbeans.getBody().split("-");
		// 商品名称
		orderInfo += "&subject=" + "\"" + zfbbackbeans.getSubject() + "\"";
//		orderInfo += "&subject=" + "\"" + "充值号码:"+spname[0] + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + zfbbackbeans.getBody() + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + zfbbackbeans.getTotal_fee() + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + zfbbackbeans.getNotify_url() + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		//orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}
	
	
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}
}
