package com.sjtu.netcenter.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sjtu.netcenter.httpclient.Client;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PackageList extends ListActivity{
	private List<Map<String, Object>> dataList=new ArrayList<Map<String, Object>>();
	private Button show;
	private TextView text;
	private Spinner spin;
	private String[][] title;
	private String[] notes;
	private String mposition="10";
	private String string;
	private Boolean ready=false;
	private String [] mStringArray;
	private ArrayAdapter<String> mAdapter ;
	private Client client=new Client();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packagelist);
        new Thread(){ 
            @Override 
            public void run() 
            { 
            	title=client.GetPackList();
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
		for(int i=0;i<title.length;i++){
			map = new HashMap<String, Object>();
            map.put("title", title[i][2]);
            map.put("tag", title[i][1]);
            map.put("name", title[i][0]);
            map.put("idnum", title[i][3]);
            dataList.add(map);
		}		
    	SimpleAdapter simpleAdapter = new SimpleAdapter(this, dataList,
        		R.layout.vlist, new String[] {"title", "tag",},
        		new int[] { R.id.title,R.id.tag });
        setListAdapter(simpleAdapter);
	}
	private void init(){
		show=(Button)findViewById(R.id.show);
		text=(TextView)findViewById(R.id.itemnum);
		spin=(Spinner)findViewById(R.id.picker);
		mStringArray=getResources().getStringArray(R.array.test_string_array);
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mStringArray);
         
        //���������б���
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(mAdapter);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				mposition=mStringArray[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				mposition="10";
			}
		});
		show.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {				
			}
		});
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Map<String, Object> map = dataList.get(position);  
		int idnum=Integer.valueOf((String) map.get("idnum"));
		if(idnum<2){
			Intent intent = new Intent();   
			if(!map.get("name").toString().equals("ha")){
				intent.setClass(PackageList.this, ResourceList.class);  } 
			else intent.setClass(PackageList.this, Hackathon.class);//��������Ŀ��   
			Bundle bundle = new Bundle();                           //����Bundle����   
			bundle.putString("name",map.get("name").toString());  //װ������   
			bundle.putString("num", mposition);
			bundle.putString("title", map.get("title").toString());
			bundle.putString("id","0");
			intent.putExtras(bundle);                                //��Bundle����Intent����   
			startActivity(intent);                                     //��ʼ�л� 
		}
		else{
			Intent intent = new Intent(); 
			intent.setClass(PackageList.this, Datalist.class);
			Bundle bundle = new Bundle();                           //����Bundle����   
			bundle.putString("name",map.get("name").toString());  //װ������ 
			bundle.putString("title", map.get("title").toString());
			bundle.putInt("idnum", idnum);
			bundle.putString("num", mposition);
			intent.putExtras(bundle); 
			startActivity(intent);			
		}
	}
}
