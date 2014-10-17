package com.sjtu.netcenter.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sjtu.netcenter.httpclient.Client;

public class Datalist extends ListActivity{
	private String pacname; 
	private String top;
	private String mposition;
	private int idnum;
	private Boolean ready=false;
	private Client client=new Client();
	private String[][] title;
	private TextView toptitle;
	private List<Map<String, Object>> dataList=new ArrayList<Map<String, Object>>();
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datalist);
        getpac();
        new Thread(){ 
            @Override 
            public void run() 
            { 
            	title=client.GetDataList(idnum,pacname);
            	ready=true;            	
            } 
        }.start(); 
     
        while(!ready){			
	    	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}    
        init();
        dataList.clear();
        Map<String, Object> map = new HashMap<String, Object>();
		for(int i=0;i<idnum;i++){
			map = new HashMap<String, Object>();
            map.put("title", title[i][1]);
            map.put("tag", title[i][0]);
            map.put("id", title[i][0]);
            dataList.add(map);
		}		
    	SimpleAdapter simpleAdapter = new SimpleAdapter(this, dataList,
        		R.layout.vlist, new String[] {"title", "tag",},
        		new int[] { R.id.title,R.id.tag });
        setListAdapter(simpleAdapter);
	}
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Map<String, Object> map = dataList.get(position);  
			Intent intent = new Intent();   
			intent.setClass(Datalist.this, ResourceList.class);//��������Ŀ��   
			Bundle bundle = new Bundle();                           //����Bundle����   
			bundle.putString("name",map.get("title").toString());  //װ������   
			bundle.putString("num", mposition);
			bundle.putString("id",map.get("id").toString());
			bundle.putString("title", map.get("title").toString());
			intent.putExtras(bundle);                                //��Bundle����Intent����   
			startActivity(intent);                                     //��ʼ�л� 
		
	}
	private void init(){
		toptitle=(TextView)findViewById(R.id.top);
		toptitle.setText(top);
		toptitle.setTextColor(Color.RED);
	}
	private void getpac(){
		Intent intent = this.getIntent();        //��ȡ���е�intent����   
		Bundle bundle = intent.getExtras();    //��ȡintent�����bundle����   
		pacname = bundle.getString("name");    //��ȡBundle������ַ��� 
		idnum=bundle.getInt("idnum");
		top=bundle.getString("title");
		mposition=bundle.getString("num");
	}
}
