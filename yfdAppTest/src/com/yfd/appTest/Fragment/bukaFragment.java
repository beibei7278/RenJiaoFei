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
public class bukaFragment extends Basefragment implements OnClickListener{
	View Mview;
	Dialog dialog;
	SelectPicPopupWindow menuWindow;
	Button tijiao,yyschose,save,kdbtn;	
	EditText phone,fwmm,name,contact,useradress,reason;	
	ImageView sfz,sff;
	List<String> listyys=new ArrayList<String>();
	List<String> listkd=new ArrayList<String>();
	String  BOUNDARY =  UUID.randomUUID().toString();  //边界标识   随机生成
	String PREFIX = "--" , LINE_END = "\r\n"; 
	String CONTENT_TYPE = "multipart/form-data";   //内容类型
	String path1,path2;
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
	private TextView datetxt;
	Button datechose;
	LinearLayout piclay;
	ImageView sfp;
	private String path3;
	SelectBirthday birth;
	public bukaFragment(Context con) {
		// TODO Auto-generated constructor stub
		this.con=con;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Mview=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_buka, null);
		con=getActivity();
		
		
		listyys.add("远特");		
		listyys.add("国美");
		listyys.add("迪加");
		listkd.add("顺丰");
		listkd.add("圆通");
		phone=(EditText)Mview.findViewById(R.id.bk_phone);
		fwmm=(EditText)Mview.findViewById(R.id.bk_mm);
		name=(EditText)Mview.findViewById(R.id.bk_name);
		contact=(EditText)Mview.findViewById(R.id.bk_userphone);
		useradress=(EditText)Mview.findViewById(R.id.bk_adress);
		reason=(EditText)Mview.findViewById(R.id.bk_reason);
		kdbtn=(Button)Mview.findViewById(R.id.bk_chosekd);
		datetxt=(TextView)Mview.findViewById(R.id.bk_datetxt);
		datechose=(Button)Mview.findViewById(R.id.bk_chosedate);
		yyschose=(Button)Mview.findViewById(R.id.bk_choseyys);
		save=(Button)Mview.findViewById(R.id.bk_save);
		piclay=(LinearLayout)Mview.findViewById(R.id.bk_laypic);
		sfp=(ImageView)Mview.findViewById(R.id.bksf_pas);
		sfz=(ImageView)Mview.findViewById(R.id.bk_sf_zheng);
		sff=(ImageView)Mview.findViewById(R.id.bk_sf_fan);
		yyschose.setText(listyys.get(0));
		kdbtn.setOnClickListener(this);
		yyschose.setOnClickListener(this);
		save.setOnClickListener(this);
		sfz.setOnClickListener(this);
		sff.setOnClickListener(this);
		sfp.setOnClickListener(this);
        datechose.setOnClickListener(this);
		yyschose.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(yyschose.getText().toString().equals("国美")){
					fwmm.setVisibility(View.GONE);
					datechose.setVisibility(View.GONE);
					datetxt.setVisibility(View.GONE);
					piclay.setVisibility(View.VISIBLE);
					reason.setVisibility(View.GONE);
					kdbtn.setText("顺丰");
					
				}else{
					
					kdbtn.setText("请选择");
					
				}
				
	if(yyschose.getText().toString().equals("远特")){
		fwmm.setVisibility(View.VISIBLE);
		datechose.setVisibility(View.VISIBLE);
		datetxt.setVisibility(View.VISIBLE);
		piclay.setVisibility(View.GONE);
		reason.setVisibility(View.VISIBLE);

				}
	if(yyschose.getText().toString().equals("迪加")){
		fwmm.setVisibility(View.GONE);
		datechose.setVisibility(View.GONE);
		datetxt.setVisibility(View.GONE);
		piclay.setVisibility(View.GONE);
		reason.setVisibility(View.GONE);
		
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
				
				
				Toast.makeText(getActivity(), "选择入网时间", 1000).show();
				return;
				
			}
			
		}
		
		
		
		
		if(Utils.isEmpty(phone.getText().toString())){
			
			Toast.makeText(getActivity(), "输入手机号", 1000).show();
			return;
		}
		if(yyschose.getText().toString().equals("远特")){
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
if(Utils.isEmpty(name.getText().toString())){
	
	Toast.makeText(getActivity(), "输入收件人", 1000).show();
	return;
}
if(Utils.isEmpty(contact.getText().toString())){
	       
	Toast.makeText(getActivity(), "输入联系方式", 1000).show();
	return;
}
if(Utils.isEmpty(useradress.getText().toString())){
	
	Toast.makeText(getActivity(), "输入收件地址", 1000).show();
	return;
}
if(kdbtn.getText().toString().equals("请选择")){
	
	Toast.makeText(getActivity(), "选择快递类型", 1000).show();
	return;
}
if(yyschose.getText().toString().equals("远特")){
if(Utils.isEmpty(reason.getText().toString())){
	
	Toast.makeText(getActivity(), "填写通话记录或原因", 1000).show();
	return;
}
}
if(Utils.isEmpty(path1)){
	
	Toast.makeText(getActivity(), "身份证正面照片", 1000).show();
	return;
}
if(Utils.isEmpty(path2)){
	
	Toast.makeText(getActivity(), "身份证反面照片", 1000).show();
	return;
}

if(yyschose.getText().toString().equals("国美")){

if(Utils.isEmpty(path3)){
	
	Toast.makeText(getActivity(), "手持身份证照片", 1000).show();
	return;
}
}
params=new HashMap<String, String>();

if(yyschose.getText().toString().equals("国美")){
	
	FormFile  file1=new FormFile(Utils.getlocalSysDatetime(), new File(path1), "newFrontPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
FormFile  file2=new FormFile(Utils.getlocalSysDatetime(), new File(path2), "newBackPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
FormFile  file3=new FormFile(Utils.getlocalSysDatetime(), new File(path3), "IDFrontByself", CONTENT_TYPE + ";boundary=" + BOUNDARY);
formfile=new FormFile[3];

formfile[0]=file1;
formfile[1]=file2;
formfile[2]=file3;	
}
else{
	

FormFile  file1=new FormFile(Utils.getlocalSysDatetime(), new File(path1), "newFrontPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
FormFile  file2=new FormFile(Utils.getlocalSysDatetime(), new File(path2), "newBackPic", CONTENT_TYPE + ";boundary=" + BOUNDARY);
formfile=new FormFile[2];

formfile[0]=file1;
formfile[1]=file2;

	
}



params.put("carrierOp", yyschose.getText().toString());
params.put("ftPhone", phone.getText().toString());
params.put("serviceType", "补卡");

if(datechose.getText().toString().equals("远特")){
	
	params.put("networkTime", datechose.getText().toString());
	
}else{
	
	params.put("networkTime", "");
	
}


params.put("servicePass", fwmm.getText().toString());
params.put("outCallLog", reason.getText().toString());
params.put("deliveryAddress", useradress.getText().toString());
params.put("addressee", name.getText().toString());
params.put("contactPhone", contact.getText().toString());
params.put("expressType", kdbtn.getText().toString());
params.put("Ftmemo", kdbtn.getText().toString());


dialog=createLoadingDialog(con, "");
dialog.show();

Runnable networkTask = new Runnable() {  
	   
    @Override  
    public void run() {  
        // TODO  
        // 在这里进行 http request.网络请求相关操作  
        try {
         
     		UploadMore.post(BaseApplication.BUKA_URL, params, formfile, handlersave);
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
			birth.showAtLocation(getActivity().findViewById(R.id.bkroot),
					Gravity.BOTTOM, 0, 0);
					break;
case R.id.bk_choseyys:
	initPopWindow(listyys, yyschose);
			break;
			
case R.id.bk_save:
			save();
			break;
case R.id.bk_chosekd:
	initPopWindow(listkd, kdbtn);
	break;
			
case R.id.bk_sf_zheng:
	BaseApplication.FromImg=1;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.bk_choseyys), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
	
case R.id.bk_sf_fan:
	BaseApplication.FromImg=2;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.bk_choseyys), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
	break;
	
case R.id.bksf_pas:
	BaseApplication.FromImg=3;
	menuWindow = new SelectPicPopupWindow((Activity)con, itemsOnClick);
	menuWindow.showAtLocation(Mview.findViewById(R.id.bk_choseyys), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
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
			//Toast.makeText(getApplicationContext(), cpath1, 1000).show();
			
			 sfp.setImageBitmap(Utils.getLoacalBitmap(path3));	//设置Bitmap
			
			break;
			
 case Activity.RESULT_OK:
			if(BaseApplication.FromImg==1){
				
				path1=setImage(sfz, data);
				
			}
if(BaseApplication.FromImg==2){
	
	path2=setImage(sff, data);
	
}
if(BaseApplication.FromImg==3){
	
	path3=setImage(sfp, data);
	
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
