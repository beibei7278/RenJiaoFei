package com.yfd.appTest.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Activity.Basefragment;
import com.yfd.appTest.Activity.ImagechoseActivity;
import com.yfd.appTest.Beans.FormFile;
import com.yfd.appTest.Beans.SaoMiaobckBeans;
import com.yfd.appTest.CustomView.SelectBirthday;
import com.yfd.appTest.CustomView.SelectPicPopupWindow;
import com.yfd.appTest.Utils.UploadMore;
import com.yfd.appTest.Utils.Utils;
import com.yfd.appTest.adapter.YYsadapter;

@SuppressLint("ValidFragment")
public class GuohuFragment extends Basefragment implements OnClickListener{
	View Mview;
	Dialog dialog;
	SelectPicPopupWindow menuWindow;
	Button tijiao,yyschose,save;	
	EditText phone,fwmm,reason;	
	ImageView sfz,sff;
	ImageView sfzx,sffx;
	ImageView newzheng,oldzheng;
	List<String> listyys=new ArrayList<String>();
	String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
	String PREFIX = "--" , LINE_END = "\r\n"; 
	String CONTENT_TYPE = "multipart/form-data";   //内容类型
	String path1,path2,path3,path4;
	RelativeLayout newandold;
	private SaoMiaobckBeans loginbeansmsg;
	private Context con;
	private Handler handlersave=new Handler(){
		
		

		public void handleMessage(android.os.Message msg) {
			dialog.dismiss();
			if(msg.obj!=null){
				
				loginbeansmsg = (SaoMiaobckBeans) Utils.jsonToBean(msg.obj.toString(), SaoMiaobckBeans.class);
					Toast.makeText(con,loginbeansmsg.getMsg(), 1000).show();
				
			}else{
				
				Toast.makeText(getActivity(), "请求失败，请重试~", 1000).show();
				
			}
			
		};
		
	};
    //为弹出窗口实现监听类
    private OnClickListener  itemsOnClick = new OnClickListener(){

		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			case R.id.btn_take_photo:
				
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
				  
				if(BaseApplication.FromImg==1){
				
	                startActivityForResult(intent,1); 
				}
                if(BaseApplication.FromImg==2){
	            startActivityForResult(intent, 2);		
				}
                if(BaseApplication.FromImg==3){
	            startActivityForResult(intent, 3); 
                }
                if(BaseApplication.FromImg==4){
    	            startActivityForResult(intent, 3); 
                    }
                
                if(BaseApplication.FromImg==5){
    	            startActivityForResult(intent, 5); 
                    }
                if(BaseApplication.FromImg==6){
    	            startActivityForResult(intent, 6); 
                    }
				break;
			case R.id.btn_pick_photo:		
				
				startActivityForResult (new Intent(con, ImagechoseActivity.class), 1);  
				
				break;
			default:
				break;
			}
			
				
		}
    	
    };
	private Map<String, String> params;
	private FormFile[] formfile;
	private Button datechose;
	TextView datetxt;
	SelectBirthday birth;
	private String path5;
	private String path6;
	LinearLayout four;
	public GuohuFragment(Context con) {
		// TODO Auto-generated constructor stub
		this.con=con;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Mview=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_guohu, null);
		con=getActivity();
		
		
		listyys.add("远特");		
		listyys.add("蜗牛");
		listyys.add("迪加");
		listyys.add("国美");
		
		newandold=(RelativeLayout)Mview.findViewById(R.id.newandoldpic);
		four=(LinearLayout)Mview.findViewById(R.id.four);
		
		
		phone=(EditText)Mview.findViewById(R.id.gh_phone);
		fwmm=(EditText)Mview.findViewById(R.id.gh_mm);
        datetxt=(TextView)Mview.findViewById(R.id.gh_datetxt);
		reason=(EditText)Mview.findViewById(R.id.gh_reason);
	
		datechose=(Button)Mview.findViewById(R.id.bk_chosedate);
		yyschose=(Button)Mview.findViewById(R.id.bk_choseyys);
		yyschose.setText(listyys.get(0));
		save=(Button)Mview.findViewById(R.id.bk_save);
		
		
		sfz=(ImageView)Mview.findViewById(R.id.gh_sf_zheng);
		sff=(ImageView)Mview.findViewById(R.id.gh_sf_fan);

		sfzx=(ImageView)Mview.findViewById(R.id.gh_sf_xzheng);
		sffx=(ImageView)Mview.findViewById(R.id.gh_sf_xfan);
		
		newzheng=(ImageView)Mview.findViewById(R.id.gh_sf_newzheng);
		oldzheng=(ImageView)Mview.findViewById(R.id.gh_sf_oldzheng);
		
			
		newzheng.setOnClickListener(this);
		oldzheng.setOnClickListener(this);
		
		datechose.setOnClickListener(this);
		yyschose.setOnClickListener(this);
		save.setOnClickListener(this);
		sfzx.setOnClickListener(this);
		sffx.setOnClickListener(this);
		sfz.setOnClickListener(this);
		sff.setOnClickListener(this);
		
		yyschose.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
				if(yyschose.getText().toString().equals("蜗牛")){
					
					reason.setVisibility(View.GONE);
					fwmm.setVisibility(View.VISIBLE);
					datechose.setVisibility(View.GONE);
					datetxt.setVisibility(View.GONE);
					newandold.setVisibility(View.GONE);
					four.setVisibility(View.GONE);
				}
               if(yyschose.getText().toString().equals("远特")){
					
					reason.setVisibility(View.VISIBLE);
					fwmm.setVisibility(View.VISIBLE);
					datechose.setVisibility(View.VISIBLE);
					datetxt.setVisibility(View.VISIBLE);
					newandold.setVisibility(View.GONE);
					four.setVisibility(View.GONE);
				}
                if(yyschose.getText().toString().equals("迪加")){
	
	                reason.setVisibility(View.VISIBLE);
	                fwmm.setVisibility(View.GONE);
	                datechose.setVisibility(View.GONE);
					datetxt.setVisibility(View.GONE);
					newandold.setVisibility(View.GONE);
					four.setVisibility(View.GONE);
                }
                
                if(yyschose.getText().toString().equals("国美")){
                	
	                reason.setVisibility(View.GONE);
	                fwmm.setVisibility(View.GONE);
	                datechose.setVisibility(View.GONE);
					datetxt.setVisibility(View.GONE);
					newandold.setVisibility(View.VISIBLE);
					four.setVisibility(View.VISIBLE);
}
                
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return Mview;
	}
	
	public void save(){
		
		
		if(yyschose.getText().toString().equals("远特")){
		
		if(datechose.getText().equals("请选择")){
			
			Toast.makeText(getActivity(), "请选择入网时间", 1000).show();
			return;
			
		}
		
		}
		
		if(Utils.isEmpty(phone.getText().toString())){
			
			Toast.makeText(getActivity(), "输入手机号", 1000).show();
			return;
		}
		
		
		if(yyschose.getText().toString().equals("远特")||yyschose.getText().toString().equals("蜗牛"))
		{	
       if(Utils.isEmpty(fwmm.getText().toString())){
			
			Toast.makeText(getActivity(), "输入服务密码", 1000).show();
			return;
		}
		}

//if(yyschose.getText().toString().equals("请选择")){
//	
//	Toast.makeText(getActivity(), "选择运营商", 1000).show();
//	return;
//}


	
	if(yyschose.getText().toString().equals("远特")||yyschose.getText().toString().equals("迪加")){
	
		if(Utils.isEmpty(reason.getText().toString())){
			
			Toast.makeText(getActivity(), "填写通话记录或原因", 1000).show();
			return;
		}
	
	}
    

if(Utils.isEmpty(path1)){
	
	Toast.makeText(getActivity(), "旧机主身份证正面照片", 1000).show();
	return;
}
if(Utils.isEmpty(path2)){
	
	Toast.makeText(getActivity(), "旧机主身份证反面照片", 1000).show();
	return;
}
if(Utils.isEmpty(path3)){
	
	Toast.makeText(getActivity(), "新机主身份证正面照片", 1000).show();
	return;
}
if(Utils.isEmpty(path4)){
	
	Toast.makeText(getActivity(), "新机主身份证反面照片", 1000).show();
	return;
}


if(yyschose.getText().toString().equals("国美")){
	
	if(Utils.isEmpty(path5)){
		
		Toast.makeText(getActivity(), "新机主手持身份证正面照片", 1000).show();
		return;
	}
     if(Utils.isEmpty(path6)){
		
		Toast.makeText(getActivity(), "旧机主手持身份证给正面照片", 1000).show();
		return;
	}
}

params=new HashMap<String, String>();
if(yyschose.getText().toString().equals("国美")){
	
	FormFile  file1=new FormFile(Utils.getlocalSysDatetime(), new File(path1), "newFrontPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file2=new FormFile(Utils.getlocalSysDatetime(), new File(path2), "newBackPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file3=new FormFile(Utils.getlocalSysDatetime(), new File(path3), "oldFrontPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file4=new FormFile(Utils.getlocalSysDatetime(), new File(path4), "oldBackPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file5=new FormFile(Utils.getlocalSysDatetime(), new File(path5), "IDFrontByself", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file6=new FormFile(Utils.getlocalSysDatetime(), new File(path6), "OldIDFrontByself", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	formfile=new FormFile[6];

	formfile[0]=file1;
	formfile[1]=file2;
	formfile[2]=file3;
	formfile[3]=file4;
	formfile[4]=file5;
	formfile[5]=file6;
	
	
}else{
	
	FormFile  file1=new FormFile(Utils.getlocalSysDatetime(), new File(path1), "newFrontPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file2=new FormFile(Utils.getlocalSysDatetime(), new File(path2), "newBackPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file3=new FormFile(Utils.getlocalSysDatetime(), new File(path3), "oldFrontPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	FormFile  file4=new FormFile(Utils.getlocalSysDatetime(), new File(path4), "oldBackPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
	formfile=new FormFile[4];

	formfile[0]=file1;
	formfile[1]=file2;
	formfile[2]=file3;
	formfile[3]=file4;	
	
	
}


params.put("carrierOp", yyschose.getText().toString());
params.put("ftPhone", phone.getText().toString());
params.put("serviceType", "过户");

if(yyschose.getText().toString().equals("远特")){
	
	params.put("networkTime", datechose.getText().toString());
	
}else{
	
	params.put("networkTime", "");
	
}


params.put("servicePass", fwmm.getText().toString());
params.put("outCallLog", reason.getText().toString());
params.put("Ftmemo", reason.getText().toString());



dialog=createLoadingDialog(con, "");
dialog.show();

Runnable networkTask = new Runnable() {  
	   
    @Override  
    public void run() {  
        // TODO  
        // 在这里进行 http request.网络请求相关操作  
        try {
         
     		UploadMore.post(BaseApplication.GUOHU_URL, params, formfile, handlersave);
     	} catch (Exception e) {
     		// TODO Auto-generated catch block
     		e.printStackTrace();
     	}  
    }  
};  

if(Utils.isNetworkAvailable(getActivity())){
new Thread(networkTask).start();
}	else{
	
	Toast.makeText(con, "网络连接异常，请重试~", 1000).show();
}

	}
	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		
		case R.id.bk_chosedate:
			birth=new SelectBirthday(getActivity(), datechose);
			birth.showAtLocation(getActivity().findViewById(R.id.ghroot),
					Gravity.BOTTOM, 0, 0);
			break;
		
case R.id.bk_choseyys:
	initPopWindow(listyys, yyschose);
			break;
			
case R.id.bk_save:
			save();
			break;

			
case R.id.gh_sf_zheng:
	BaseApplication.FromImg=1;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.gh_sf_zheng), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
	
case R.id.gh_sf_fan:
	BaseApplication.FromImg=2;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.gh_sf_fan), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
case R.id.gh_sf_xzheng:
	BaseApplication.FromImg=3;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.gh_sf_zheng), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
	
case R.id.gh_sf_xfan:
	BaseApplication.FromImg=4;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.gh_sf_fan), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
	
case R.id.gh_sf_newzheng:
	BaseApplication.FromImg=5;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.gh_sf_newzheng), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
	
case R.id.gh_sf_oldzheng:
	BaseApplication.FromImg=6;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.gh_sf_oldzheng), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
		
		default:
			break;
		}
		
	}
	
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
    	
    	switch (resultCode) {
  
			
        case 1:
        	path1=data.getExtras().getString("path1");
			//Toast.makeText(getActivity(), path1, 1000).show();
			
			sfz.setImageBitmap(Utils.getLoacalBitmap(path1));	//设置Bitmap
			
			break;
			
        case 2:
        	path2=data.getExtras().getString("path1");
			//Toast.makeText(getApplicationContext(), cpath1, 1000).show();
			
			 sff.setImageBitmap(Utils.getLoacalBitmap(path2));	//设置Bitmap
			
			break;
        case 3:
        	path3=data.getExtras().getString("path1");
			//Toast.makeText(getActivity(), path1, 1000).show();
			
			sfzx.setImageBitmap(Utils.getLoacalBitmap(path3));	//设置Bitmap
			
			break;
			
        case 4:
        	path4=data.getExtras().getString("path1");
			//Toast.makeText(getApplicationContext(), cpath1, 1000).show();
			
			 sffx.setImageBitmap(Utils.getLoacalBitmap(path4));	//设置Bitmap
			
			break;
			
        case 5:
        	path5=data.getExtras().getString("path1");
			//Toast.makeText(getApplicationContext(), cpath1, 1000).show();
			
			 newzheng.setImageBitmap(Utils.getLoacalBitmap(path5));	//设置Bitmap
			
			break;
        case 6:
        	path6=data.getExtras().getString("path1");
			//Toast.makeText(getApplicationContext(), cpath1, 1000).show();
			
			 oldzheng.setImageBitmap(Utils.getLoacalBitmap(path6));	//设置Bitmap
			
			break;
 case Activity.RESULT_OK:
			if(BaseApplication.FromImg==1){
				
				path1=setImage(sfz, data);
				
			}
if(BaseApplication.FromImg==2){
	
	path2=setImage(sff, data);
	
}
if(BaseApplication.FromImg==3){
	
	path3=setImage(sfzx, data);
	
}
if(BaseApplication.FromImg==4){

path4=setImage(sffx, data);

}   
if(BaseApplication.FromImg==5){

path5=setImage(newzheng, data);

}   
if(BaseApplication.FromImg==6){

path6=setImage(oldzheng, data);

}   
			break;
 

		default:
			break;
		}
    	
    	
    }
	
	 public  void initPopWindow(final List<String> list,final Button button){  
	       
	        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popwindowlist, null);  
	       // contentView.setBackgroundColor(color.light_gray);	          
	        final PopupWindow popupWindow = new PopupWindow(button, 300, LayoutParams.WRAP_CONTENT,true);  
	        popupWindow.setContentView(contentView); 
	        popupWindow.setFocusable(true);  
	        popupWindow.setOutsideTouchable(true); 
	        ListView listView = (ListView) contentView.findViewById(R.id.listpop);
	        
	        listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					button.setText(list.get(arg2));
					popupWindow.dismiss();
				}
			});
	       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);  
	        listView.setAdapter(new YYsadapter(con,list));  
	          
	        popupWindow.setFocusable(true);  
	        popupWindow.showAsDropDown(button);  
	    } 
	 
	  public String  setImage(ImageView imgview,Intent data){
			
		   String sdStatus = Environment.getExternalStorageState();  
	       if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
	           Log.i("TestFile",  
	                   "SD card is not avaiable/writeable right now.");  
	           return null;  
	       }  
	       String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
	     
	       Bundle bundle = data.getExtras();  
	       Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  
	     
	       FileOutputStream b = null;  
	      //???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？  
	       File file = new File("/sdcard/myImage/");  
	       if(file!=null){
	   		file.mkdirs();// 创建文件夹
	   		}
	       String fileName = "/sdcard/myImage/"+name;  

	       try {  
	           b = new FileOutputStream(fileName);  
	           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
	       } catch (FileNotFoundException e) {  
	           e.printStackTrace();  
	       } finally {  
	           try {  
	               b.flush();  
	               b.close();  
	           } catch (IOException e) {  
	               e.printStackTrace();  
	           }  
	       }  
	       imgview.setImageBitmap(bitmap);// 将图片显示在ImageView里  
	       
		return fileName;
	   } 
}
