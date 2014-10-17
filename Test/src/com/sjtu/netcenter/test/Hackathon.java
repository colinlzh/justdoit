package com.sjtu.netcenter.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sjtu.netcenter.httpclient.ChartView;
import com.sjtu.netcenter.httpclient.Client;

public class Hackathon extends ListActivity{
	private Button back;
	private Button baidu;
	private Button go;
	private String PacName;
	private String PacTitle;
	private String num;
	private String[] line;
	private String[] qq;
	private String id;
	private int linenum;
	private String content="";
	private TextView text;
	private Client client=new Client();
	private boolean ready=false;
	private List<Map<String, Object>> dataList=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resourcelist);
		getpac();
        new Thread(){ 
            @Override 
            public void run() 
            { 
            	content=client.Getcontent(PacName, num,id);
            	ready=true;
            } 
        }.start();
        while(!ready){			
	    	try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}       
        init();
        StringPro();
        group();
	}
	private void init(){
		back=(Button)findViewById(R.id.resourcelist_back);
		baidu=(Button)findViewById(R.id.baidugo);
		go=(Button)findViewById(R.id.go);
		text=(TextView)findViewById(R.id.res);
		//text.setText("点击标题按钮显示LISTVIEW");
		back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
				Intent intent=new Intent();
				intent.setClass(Hackathon.this, PackageList.class);
				startActivity(intent);
			    
			}
		});
		go.setText(PacTitle);
		go.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {	
				if(!content.equals("false")){
					dataList.clear();
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i=0;i<line.length;i++){
					map = new HashMap<String, Object>();
		           if(linenum>0) map.put("tex1", qq[i*linenum+0]);
		           if(linenum>1) map.put("tex2", qq[i*linenum+1]);
		           if(linenum>2) map.put("tex3", qq[i*linenum+2]);
		           if(linenum>3)  map.put("tex4", qq[i*linenum+3]);
		           if(linenum>4) map.put("tex5", qq[i*linenum+4]);
		            dataList.add(map);
				}		
				SimpleAdapter simpleAdapter = new SimpleAdapter(Hackathon.this, dataList,
		        		R.layout.hacklist, new String[] {"tex1", "tex2","tex3", "tex4","tex5"},
		        		new int[] { R.id.tex1,R.id.tex2,R.id.tex3,R.id.tex4,R.id.tex5});
		        setListAdapter(simpleAdapter);}
				else {
					text.setText("找不到该数据集内的数据");
					text.setTextSize(30);
				}
			}});
		baidu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				Bundle bundle = new Bundle();                           //创建Bundle对象   
				bundle.putString("time",qq[5]);     //装入数据   
				bundle.putInt("linenum", line.length);
				bundle.putStringArray("content", qq);
				intent.putExtras(bundle);
				intent.setClass(Hackathon.this, BaiduMap.class);
				startActivity(intent);
			}
		});
	}
	private void getpac(){
		Intent intent = this.getIntent();        //获取已有的intent对象   
		Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象   
		PacName = bundle.getString("name");    //获取Bundle里面的字符串 
		PacTitle = bundle.getString("title");    //获取Bundle里面的字符串 
		num=bundle.getString("num");
		id=bundle.getString("id");
	}
	private void group(){
		ChartView myView=new ChartView(this);
		myView.SetInfo(
		        new String[]{"7-11","7-12","7-13","7-14","7-15","7-16","7-17"},   //X轴刻度
		        new String[]{"","50","100","150","200","250"},   //Y轴刻度
		        new String[]{"15","23","10","36","45","40","12"},  //数据
		        "图标的标题"
		); 
		//qq[5]为日期qq[4]经qq[3]纬
	}
	private void StringPro(){
		if(!content.equals("false")){
			line=content.split("\n");				
			linenum=line[0].split(",").length;//列数，即属性个数
			qq=content.replace("\n", ",").split(",");
			qq[2]=" co2";
			qq[3]="TEMP.";
			for(int i=0;i<line.length;i++){				
			}
		}
	}
}
