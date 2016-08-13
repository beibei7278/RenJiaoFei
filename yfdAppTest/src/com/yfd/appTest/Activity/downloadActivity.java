package com.yfd.appTest.Activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import net.sourceforge.simcpux.Constants;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Utils.Util;

public class downloadActivity extends Activity {
	
	ImageView back;
	TextView sharewx,shareqq,sharefrend;
	private IWXAPI api;
	public  Tencent mTencent;
	ArrayList<String> picurl=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_download);
		api=WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
		 mTencent = Tencent.createInstance("1105234997", this);
		api.registerApp(Constants.APP_ID);
		back=(ImageView)findViewById(R.id.down_back);
		sharewx=(TextView)findViewById(R.id.sharewx);
		shareqq=(TextView)findViewById(R.id.shareqq);
		sharefrend=(TextView)findViewById(R.id.sharefrend);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		sharewx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShareWX();
			}
		});
		
		shareqq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				shareToQzone();
			}
		});
sharefrend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShareWXF();
			}
		});
	}

	public void ShareWX(){
		
		
		sendReq(this,"移动充值，流量充值尽在‘任缴费’，欢迎使用下载",BitmapFactory.decodeResource(getResources(), R.drawable.ewm));
		
//		WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = "http://www.baidu.com";
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
//        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
//        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ewm);
//        msg.thumbData = Util.bmpToByteArray(thumb, true);
//         
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene =SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
		}
	
	public void ShareWXF(){
		
		sendReqs(this,"移动充值，流量充值尽在‘任缴费’，欢迎使用下载",BitmapFactory.decodeResource(getResources(), R.drawable.ewm));
		
//		String text="移动充值，流量充值尽在‘任缴费’，欢迎使用下载";
//		
//		 WXTextObject textObject = new WXTextObject();
//		 
//		  textObject.text = text;
//		 
//		 
//		 
//		  // 用WXTextObject对象初始化一个WXMediaMessage对象
//		 
//		  WXMediaMessage msg = new WXMediaMessage();
//		 
//		  msg.mediaObject = textObject;
//		 
//		  msg.description = textObject.text;
//		 
//		 
//		 
//		  // 构造一个Req
//		 
//		  SendMessageToWX.Req req = new SendMessageToWX.Req();
//		 
//		  req.message = msg;
//		 
//		  req.scene=req.WXSceneSession;
//		  
//		  req.transaction = String.valueOf(System.currentTimeMillis());// 唯一字段，标识一个请求
//		 
//		  api.sendReq(req);
		
	}

	private String buildTransaction(final String type) {  
	      return (type == null) ? String.valueOf(System.currentTimeMillis())  
	            :type + System.currentTimeMillis();  
	   } 
	
	private void shareToQzone () {
	
	       
	   picurl.add("http://222.73.22.122:8083/hyfdsta/appupdate/android/ewm.png");
		//分享类型
		    final Bundle params = new Bundle();
		    params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		    params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "任缴费");//必填
		    params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "话费，流量充值，开卡补卡过户");//选填
		    params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://222.73.22.122:8083/hyfdsta/LoginController/updateApp.html");//必填
		    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,picurl);
		    mTencent.shareToQzone(this, params, qZoneShareListener);
		}
	
	public void sendReq(Context context, String text, Bitmap bmp) {
        String url = "http://222.73.22.122:8083/hyfdsta/LoginController/updateApp.html";//收到分享的好友点击信息会跳转到这个地址去
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                localWXWebpageObject);
        localWXMediaMessage.title = text;//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
        localWXMediaMessage.description = text;
        localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene=localReq.WXSceneTimeline;
        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        api.sendReq(localReq);
    }

	public void sendReqs(Context context, String text, Bitmap bmp) {
        String url = "http://222.73.22.122:8083/hyfdsta/LoginController/updateApp.html";//收到分享的pengyouquan点击信息会跳转到这个地址去
        WXWebpageObject localWXWebpageObject = new WXWebpageObject();
        localWXWebpageObject.webpageUrl = url;
        WXMediaMessage localWXMediaMessage = new WXMediaMessage(
                localWXWebpageObject);
        localWXMediaMessage.title = text;//不能太长，否则微信会提示出错。不过博主没验证过具体能输入多长。
        localWXMediaMessage.description = text;
        localWXMediaMessage.thumbData = getBitmapBytes(bmp, false);
        SendMessageToWX.Req localReq = new SendMessageToWX.Req();
        localReq.transaction = System.currentTimeMillis() + "";
        localReq.message = localWXMediaMessage;
        localReq.scene=localReq.WXSceneSession;
        IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, true);
        api.sendReq(localReq);
    }
	
// 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0,80, 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
//                F.out(e);
            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }
	
	
    IUiListener qZoneShareListener = new IUiListener() {

        @Override
        public void onCancel() {
            Util.toastMessage(downloadActivity.this, "onCancel:test ");
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            Util.toastMessage(downloadActivity.this, "onError: " + e.errorMessage, "e");
        }

		@Override
		public void onComplete(Object response) {
			// TODO Auto-generated method stub
			 Util.toastMessage(downloadActivity.this, "onComplete: " + response.toString());
		}

    };
}
