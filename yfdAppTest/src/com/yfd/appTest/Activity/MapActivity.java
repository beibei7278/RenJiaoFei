package com.yfd.appTest.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yfd.appTest.Activity.R;
import com.yfd.appTest.Beans.Mapbackbeans;
import com.yfd.appTest.Utils.AnsyPost;
import com.yfd.appTest.Utils.Utils;

public class MapActivity extends Activity {
	
	ImageView back;
	private List<Mapbackbeans> list=new ArrayList<Mapbackbeans>();
	private MapView mMap;
	Handler handler=new Handler(){
		
		

		public void handleMessage(android.os.Message msg) {
			
			
			
			
			if(msg.obj!=null){
				
				System.out.println(msg.obj.toString());

                 Gson gson=new Gson();
				 list = gson.fromJson(msg.obj.toString(), new TypeToken<List<Mapbackbeans>>(){}.getType());
				
			
				
				if(list.size()>0){
					
					for(int i=0;i<list.size();i++){
						
						if(list.get(i).getLatitude()!=null&&list.get(i).getLongitude()!=null){
						if(!Utils.isEmpty(list.get(i).getLatitude())&&!Utils.isEmpty(list.get(i).getLongitude())){
							if(list.get(i).getLatitude().equals("null")||list.get(i).getLongitude().equals("null")){
								
							}else{
								
									if(!BaseApplication.loginbeans.getMsg().getUsName().equals(list.get(i).getUsName())){
							
							
						//����Maker����  
						LatLng point = new LatLng(Double.valueOf(list.get(i).getLatitude()),Double.valueOf(list.get(i).getLongitude()));  
						//����Markerͼ��  
						BitmapDescriptor bitmap = BitmapDescriptorFactory  
						    .fromResource(R.drawable.weizhi1);  
						//����MarkerOption�������ڵ�ͼ�����Marker  
						OverlayOptions option = new MarkerOptions()  
						    .position(point)  
						    .icon(bitmap);  
						//�ڵ�ͼ�����Marker������ʾ  
						mBaiduMap.addOverlay(option);
						}
							
						
						}
						}
						}
					}
					
				}
				
				
				
			}else{
				
				
				
			}
			
		};
		
	};
	private BaiduMap mBaiduMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		
		back=(ImageView)findViewById(R.id.down_back);
		mMap=(MapView)findViewById(R.id.bmapView);
		mBaiduMap = mMap.getMap();
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		//����Maker����  
		LatLng point2 = new LatLng(Double.valueOf(BaseApplication.lat), Double.valueOf(BaseApplication.lon));  
		//����Markerͼ��  
		BitmapDescriptor bitmap2 = BitmapDescriptorFactory  
		    .fromResource(R.drawable.weizhi2);  
		//����MarkerOption�������ڵ�ͼ�����Marker  
		OverlayOptions option2 = new MarkerOptions()  
		    .position(point2)  
		    .icon(bitmap2);  
		//�ڵ�ͼ�����Marker������ʾ  

		mBaiduMap.addOverlay(option2);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(point2).zoom(10).build()));
		
		
		AnsyPost.getArrylistpost(BaseApplication.MAPS_URL, handler);
		
	}
}
