package com.sjtu.netcenter.test;

import com.sjtu.netcenter.httpclient.Client;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main extends Activity{
	private Button ok;
	private EditText user;
	private EditText code;
	private TextView show;
	private String string="";
	private Client client=new Client();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Thread(){ 
            @Override 
            public void run() 
            { 
            } 
        }.start(); 
        init();
    }   

    private void init() {
    	ok=(Button)findViewById(R.id.ok);
    	user=(EditText)findViewById(R.id.userin);
    	code=(EditText)findViewById(R.id.codein);
    	show=(TextView)findViewById(R.id.show);
    	show.setText("登陆问题据CKAN的小伙伴红轮说他们也不知道怎么弄，暂且搁置");
    	ok.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {	
				Intent intent=new Intent();		
				intent.setClass(Main.this, PackageList.class);
				startActivity(intent);
			}
		});
    }
}
