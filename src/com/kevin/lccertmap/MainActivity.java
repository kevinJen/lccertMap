package com.kevin.lccertmap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MainActivity extends Activity {
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	Spinner mapSpinner=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init("05wz7r96pUKmnjLGH3vQVNi9", null);  
		//注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.activity_main);
		mapSpinner=(Spinner)findViewById(R.id.mapTypeSpinner);
		ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.mapTypeItems, android.R.layout.simple_spinner_item);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mapSpinner.setAdapter(arrayAdapter);
		mapSpinner.setOnItemSelectedListener(new SelectedMapTypeListener());
		mMapView=(MapView)findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		
		//mMapView.setSatellite(true);  
		//设置启用内置的缩放控件
		MapController mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		//34.2229, 108.8876
		//GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		
		GeoPoint point =new GeoPoint((int)(34.2229* 1E6),(int)(108.8876* 1E6));
		//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(12);//设置地图zoom级别
	}
	@Override
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapMan!=null){
	                mBMapMan.destroy();
	                mBMapMan=null;
	        }
	        super.onDestroy();
	}
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapMan!=null){
                mBMapMan.stop();
	        }
	        super.onPause();
	}
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapMan!=null){
	                mBMapMan.start();
	        }
        super.onResume();
	}
	class SelectedMapTypeListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String flag=arg0.getItemAtPosition(arg2).toString();

			
			if("Satellite".equalsIgnoreCase(flag)){
				mMapView.setSatellite(true);
				mMapView.setTraffic(false);
				System.out.println("卫星地图");
			}else if("Traffic".equalsIgnoreCase(flag)){
				mMapView.setTraffic(true);
				mMapView.setSatellite(false);
				System.out.println("交通图");
			}else{
				mMapView.setTraffic(false);
				mMapView.setSatellite(false);
				System.out.println("普通地图");
			}
			
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}


}
