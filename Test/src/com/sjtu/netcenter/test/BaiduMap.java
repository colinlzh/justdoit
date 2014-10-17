package com.sjtu.netcenter.test;




import java.util.List;

import net.sf.ezmorph.object.ObjectListMorpher;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaiduMap extends Activity {
	private MapView mMapView;
	private Marker mMarkerA;
	private Marker mMarkerB;
	private Marker mMarkerC;
	private Marker mMarkerD;
	private String timedata;
	private int linenum;
	private String[] line;
	private InfoWindow mInfoWindow;
	BitmapDescriptor bdA;
	com.baidu.mapapi.map.BaiduMap mBaiduMap = null;
	private LatLng ptCenter=new LatLng(36.5,120.5);
	 @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);   
	        //在使用SDK各组件之前初始化context信息，传入ApplicationContext  
	        //注意该方法要再setContentView方法之前实现  
	        getInt();
	        SDKInitializer.initialize(getApplicationContext());  
	        mMapView = new MapView(this, new BaiduMapOptions());//这都是初始化
	        setContentView(R.layout.baidumap);  
	        mMapView = (MapView) findViewById(R.id.bmapView); 	//这都是初始化
	        mBaiduMap = mMapView.getMap();
	        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(5);	//设置初始缩放	
	        for(int i=1;i<linenum;i++){
	        	if(!line[(i-1)*12+4].equals(line[i*12+4])||!(line[(i-1)*12+3].equals(line[i*12+3]))){
	        	ptCenter=new LatLng(Double.parseDouble(line[i*12+4]),Double.parseDouble(line[i*12+3]));
	        mBaiduMap.addOverlay(new MarkerOptions().position(ptCenter)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.icon_marka)).perspective(true));//添加标志ptcener为坐标，iconmarka为标志样式
	        	}
	        }
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter));
			mBaiduMap.animateMapStatus(u);
			// 初始化搜索模块，注册事件监听
			bdA = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_marka);
			initOverlay();
			mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				public boolean onMarkerClick(final Marker marker) {
	                  
					TextView button = new TextView(getApplicationContext());
					button.setBackgroundResource(R.drawable.popup);
					OnInfoWindowClickListener listener = null;
					button.setText(timedata.concat("\n").concat(line[6]).concat("        ").concat(line[7]).concat("      ").concat(line[8]).concat("     ").concat(line[9]).concat("\n").concat(line[18]).concat("   ").concat(line[19]).concat("      ").concat(line[20]).concat("      ").concat(line[21]));
					button.setTextColor(Color.RED);
					button.setTextSize(15);
					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {
							mBaiduMap.hideInfoWindow();
							}
					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -50, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
	                //设置详细信息布局为可见  
					Toast.makeText(BaiduMap.this, "点什么店", Toast.LENGTH_SHORT).show();
					return true;
					
				}
			});
			mBaiduMap.setOnMapClickListener(new OnMapClickListener()  
	        {  
	  
	            @Override  
	            public void onMapClick(LatLng arg0)  
	            {  
	                mBaiduMap.hideInfoWindow();  
	  
	            }

				@Override
				public boolean onMapPoiClick(MapPoi arg0) {
					// TODO Auto-generated method stub
					return false;
				}  
	        });  
	    }  
	 
	    @Override  
	    protected void onDestroy() {  
	        super.onDestroy();  
	        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
	        mMapView.onDestroy();  
			// 回收 bitmap 资源
			bdA.recycle();
	    }  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
	        mMapView.onResume();  
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
	        mMapView.onPause();  
	        }
	    public void initOverlay() {
	    }
	    
	    private void getInt(){
			Intent intent = this.getIntent();        //获取已有的intent对象   
			Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象   
			timedata = bundle.getString("time");    //获取Bundle里面的字符串 
			linenum=bundle.getInt("linenum");
			line=bundle.getStringArray("content");
			//line[1]城市名，line[2]站，line[3]long,line[4]lat,
		}
}
