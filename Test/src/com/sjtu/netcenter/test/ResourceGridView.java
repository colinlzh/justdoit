package com.sjtu.netcenter.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sjtu.netcenter.httpclient.Client;

public class ResourceGridView extends ListActivity{
	private Button back;
	private Button baidu;
	private Button go;
	private String PacName;
	private String num;
	private String[] line;
	private String[] qq;
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
            	content=client.Getcontent(PacName, num,"0");
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
	}
	private void init(){
		back=(Button)findViewById(R.id.resourcelist_back);
		baidu=(Button)findViewById(R.id.baidugo);
		go=(Button)findViewById(R.id.go);
		text=(TextView)findViewById(R.id.res);
		text.setText("������ⰴť��ʾLISTVIEW");
		back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				/**String[] line=content.split("\n");				
				String[] shit=line[0].split(",");
				String[] qq=content.replace("\n", ",").split(",");
				gridview.setNumColumns(shit.length);
			      //���ɶ�̬���飬����ת������  
				ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
			      for(int i=0;i<qq.length;i++)  
			      {  
			        HashMap<String, Object> map = new HashMap<String, Object>();  
			    map.put("ItemText", qq[i]);//�������ItemText  
			        lstImageItem.add(map);  
			      }  
			      //������������ImageItem <====> ��̬�����Ԫ�أ�����һһ��Ӧ  
			     SimpleAdapter saImageItems = new SimpleAdapter(ResourceList.this, //ûʲô����  
			                                                lstImageItem,//������Դ   
			                                                R.layout.night_item,//night_item��XMLʵ��  	                                                  
			                                                //��̬������ImageItem��Ӧ������          
			                                                new String[] {"ItemText"},   	                                                  
			                                                //ImageItem��XML�ļ������һ��ImageView,����TextView ID  
			                                                new int[] {R.id.ItemText});  
			      
			      //��Ӳ�����ʾ  
			      gridview.setAdapter(new MyAdapter(ResourceList.this, lstImageItem));  **/
				Intent intent=new Intent();
				intent.setClass(ResourceGridView.this, PackageList.class);
				startActivity(intent);
			    
			}
		});
		go.setText(PacName);
		go.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {	
				if(!content.equals("false")){
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i=0;i<line.length;i++){
					map = new HashMap<String, Object>();
		           if(linenum>0) map.put("tex1", qq[i*linenum+0]);
		           if(linenum>1) map.put("tex2", qq[i*linenum+1]);
		           if(linenum>2) map.put("tex3", qq[i*linenum+2]);
		           if(linenum>3)  map.put("tex4", qq[i*linenum+3]);
		           if(linenum>4) map.put("tex5", qq[i*linenum+4]);
		           if(linenum>5)  map.put("tex6", qq[i*linenum+5]);
		           if(linenum>6)  map.put("tex7", qq[i*linenum+6]);
		           if(linenum>7) map.put("tex8", qq[i*linenum+7]);
		           if(linenum>8)  map.put("tex9", qq[i*linenum+8]);
		           if(linenum>9)  map.put("tex10", qq[i*linenum+9]);
		           if(linenum>10)  map.put("tex11", qq[i*linenum+10]);
		           if(linenum>11)  map.put("tex12", qq[i*linenum+11]);
		            dataList.add(map);
				}		
				SimpleAdapter simpleAdapter = new SimpleAdapter(ResourceGridView.this, dataList,
		        		R.layout.wlist, new String[] {"tex1", "tex2","tex3", "tex4","tex5", "tex6","tex7", "tex8","tex9", "tex10","tex11", "tex12",},
		        		new int[] { R.id.tex1,R.id.tex2,R.id.tex3,R.id.tex4,R.id.tex5,R.id.tex6,R.id.tex7,R.id.tex8,R.id.tex9,R.id.tex10,R.id.tex11,R.id.tex12, });
		        setListAdapter(simpleAdapter);}
				else {
					text.setText("�Ҳ��������ݼ��ڵ�����");
					text.setTextSize(30);
				}
			}});
		baidu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				Bundle bundle = new Bundle();                           //����Bundle����   
				bundle.putString("time",qq[17]);     //װ������   
				//bundle.putString("lat",);
				intent.putExtras(bundle);
				intent.setClass(ResourceGridView.this, BaiduMap.class);
				startActivity(intent);
			}
		});
	}
	private void getpac(){
		Intent intent = this.getIntent();        //��ȡ���е�intent����   
		Bundle bundle = intent.getExtras();    //��ȡintent�����bundle����   
		PacName = bundle.getString("something");    //��ȡBundle������ַ��� 
		num=bundle.getString("num");
	}
	private void group(){
		 //qq[5]Ϊ����qq[4]��qq[3]γ
	}
	private void StringPro(){
		if(!content.equals("false")){
			line=content.split("\n");				
			linenum=line[0].split(",").length;//�����������Ը���
			qq=content.replace("\n", ",").split(",");
			for(int i=0;i<line.length;i++){				
			}
		}
	}
}
