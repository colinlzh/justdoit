package com.sjtu.netcenter.httpclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONStringer;

public class Client {
	String string="";
	String[] title=null;
	String success="";
	private String idnum;
	public String down(String pacname){
		String ten="";
		int len=0;
	        		HttpClient httpclient = new DefaultHttpClient();  
	                // 创建Get方法实例     
	                HttpPost httpgets = new HttpPost("http://202.121.178.242/datastore/dump/"+GetResourceId(pacname));
	                HttpResponse res = null;
	        		try {
	        			res = httpclient.execute(httpgets);
	        		} catch (ClientProtocolException e) {
	        			e.printStackTrace();
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}    //执行请求
	                HttpEntity entity = res.getEntity();    
	        		try {
	        			string = EntityUtils.toString(entity,"UTF-8");
	        		} catch (ParseException e) {
	        			e.printStackTrace();
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}	
	        		String[] title=string.split("\n");
	        		if(title.length>10) len=10;
	        		else len=title.length;
	        		for(int i=0;i<len;i++){
	        			ten=ten.concat(title[i]).concat("\n");
	        		}
       // Toast.makeText(this, string+"shit", Toast.LENGTH_SHORT).show();
        return ten;
	}
	public String[][] GetPackList() {
		HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpGet httpgets = new HttpGet("http://202.121.178.242/api/3/action/package_list");	
        httpgets.addHeader("Authorization","1953544d-d697-4834-9a12-3b8637a1ff2b");
        HttpResponse res = null;
		try {
			res = httpclient.execute(httpgets);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    //执行请求
        HttpEntity entity = res.getEntity();    
		try {
			string = EntityUtils.toString(entity,"UTF-8");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		//对字符串开始修改
				
        title=string.substring(string.lastIndexOf("result")+9,string.length()).split(",");//取出Result里的内容
    	String[][] tt=new String[title.length][4];
        for(int i=0;i<title.length;i++){
        	if(i!=title.length-1){
        	title[i]=title[i].substring(2, title[i].length()-1);}
        	else title[i]=title[i].substring(2, title[i].length()-3);    
        	tt[i][0]=title[i];//将数据集的名称提取出来
        	//tt[i][1]=Getdatanote(title[i]);以下提取数据集的说明
        	httpgets = new HttpGet("http://202.121.178.242/api/3/action/package_show?id="+title[i]);	
            httpgets.addHeader("Authorization","1953544d-d697-4834-9a12-3b8637a1ff2b");
    		try {
    			res = httpclient.execute(httpgets);
    		} catch (ClientProtocolException e) {
    			System.out.println("shit");
    			e.printStackTrace();
    		} catch (IOException e) {
    			System.out.println("shit");
    			e.printStackTrace();
    		}    //执行请求
            entity = res.getEntity();    
            string = null;
    		try {
    			string = EntityUtils.toString(entity);
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		//存在组织的时候title存在与字符串的不同地方
    		String temp=string.substring(string.lastIndexOf("\"revision_id\"")-10,string.lastIndexOf("\"revision_id\""));
    		if(!temp.contains("}")){
        		tt[i][2]=decodeUnicode(string.substring(string.lastIndexOf("\"title\"")+10,string.lastIndexOf("\"revision_id\"")-3));    			
    		}
    		else 
    			tt[i][2]=decodeUnicode(string.substring(string.indexOf("\"title\"")+10,string.lastIndexOf("\"extras\"")-3));
    		tt[i][3]=string.substring(string.indexOf("\"num_resources\"")+17,string.indexOf("\"tags\"")-2);
    		temp=string.substring(string.indexOf("\"notes\""),string.length());
    		tt[i][1]=decodeUnicode(temp.substring(temp.indexOf("\"notes\"")+10,temp.indexOf("\", \"")));

        }
       // String string = URLEncoder.encode(EntityUtils.toString(entity),"UTF-8");
      //关闭连接  
        httpclient.getConnectionManager().shutdown();   
        return tt;
	}
	public String GetResourceId(String Packagename) {
		HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpGet httpgets = new HttpGet("http://202.121.178.242/api/3/action/package_show?id="+Packagename);	
        httpgets.addHeader("Authorization","1953544d-d697-4834-9a12-3b8637a1ff2b");
        HttpResponse res = null;
		try {
			res = httpclient.execute(httpgets);
		} catch (ClientProtocolException e) {
			System.out.println("shit");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("shit");
			e.printStackTrace();
		}    //执行请求
        HttpEntity entity = res.getEntity();    
        String string = null;
		try {
			string = EntityUtils.toString(entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		string=string.substring(string.indexOf("[{"),string.indexOf("}]"));
		//对字符串开始修改
        string=string.substring(string.indexOf("\"id\"")+7,string.indexOf("size")-4);
       // String string = URLEncoder.encode(EntityUtils.toString(entity),"UTF-8");
      //关闭连接 **/
        httpclient.getConnectionManager().shutdown();    
        return string;
	}
	public String Getdatanote(String Packagename) {
		HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpGet httpgets = new HttpGet("http://202.121.178.242/api/3/action/package_show?id="+Packagename);	
        httpgets.addHeader("Authorization","1953544d-d697-4834-9a12-3b8637a1ff2b");
        HttpResponse res = null;
		try {
			res = httpclient.execute(httpgets);
		} catch (ClientProtocolException e) {
			System.out.println("shit");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("shit");
			e.printStackTrace();
		}    //执行请求
        HttpEntity entity = res.getEntity();    
        String string = null;
		try {
			string = EntityUtils.toString(entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		string=string.substring(string.indexOf("\"notes\"")+10,string.indexOf("\"owner_org\"")-3);
		try {
			EncodingUtils.getString(string.getBytes("GB2312"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	/**	try {
			string = URLEncoder.encode(string,"GB");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/
		return decodeUnicode(string);
	}
	public static String decodeUnicode(String theString) {      		   
	    char aChar;      	   
	     int len = theString.length();      	   
	    StringBuffer outBuffer = new StringBuffer(len);      	   
	    for (int x = 0; x < len;) {      	   
	     aChar = theString.charAt(x++);      	   
	     if (aChar == '\\') {      	   
	      aChar = theString.charAt(x++);      	   
	      if (aChar == 'u') {      	   
	       // Read the xxxx      	   
	       int value = 0;      	   
	       for (int i = 0; i < 4; i++) {      	   
	        aChar = theString.charAt(x++);      	   
	        switch (aChar) {      	   
	        case '0':      	   
	        case '1':      	   
	        case '2':      	   
	        case '3':      	   
	       case '4':      	   
	        case '5':      	   
	         case '6':      
	          case '7':      
	          case '8':      
	          case '9':      
	           value = (value << 4) + aChar - '0';      
	           break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	           break;      
	          default:      
	           throw new IllegalArgumentException(      
	             "Malformed   \\uxxxx   encoding.");      
	          }      	   
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      	   
	         else if (aChar == 'n')      	   
	          aChar = '\n';      	   
	         else if (aChar == 'f')      	   
	          aChar = '\f';      	   
	         outBuffer.append(aChar);      	   
	        }      	   
	       } else     	   
	       outBuffer.append(aChar);      	   
	      }      	   
	      return outBuffer.toString();      	   
	     }   
	public String Getcontent(String pacname,String limit,String id) {
		String qq=null;
		String all="";
		String all2="";
		JSONObject jasonObject=null;
		String[] title=null;
		Map map=null;
		HttpClient httpclient = new DefaultHttpClient();  
        // 创建Get方法实例     
        HttpPost httpgets = new HttpPost("http://202.121.178.242/api/3/action/datastore_search");	
        try {
        	if(id.equals("0")) httpgets.setEntity(new StringEntity(Jason(GetResourceId(pacname), limit)));
        	else httpgets.setEntity(new StringEntity(Jason(id, limit)));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        httpgets.addHeader("Authorization","1953544d-d697-4834-9a12-3b8637a1ff2b");
        HttpResponse res = null;
		try {
			res = httpclient.execute(httpgets);
		} catch (ClientProtocolException e) {
			System.out.println("shit");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("shit");
			e.printStackTrace();
		}    //执行请求
        HttpEntity entity = res.getEntity();    
        String string = null;
		try {
			string = EntityUtils.toString(entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		success=string.substring(string.indexOf("\"success\"")+10,string.indexOf("\"success\"")+16);
		if(success.contains("true")){
		string=string.substring(string.indexOf("\"records\"")+12,string.indexOf("\"_links\"")-3);
		if(string.length()>5){
		//获取记录里的具体数字
		String[] tt=string.split("\\}\\,");//将若干个Jason分割开以便转化
		/**以下获取数据的各个标题并对其进行一定排序**/
		jasonObject = JSONObject.fromObject(tt[tt.length-1]); 
		map = (Map)jasonObject;//String中含有大量括号、引号为方便处理，这里借助jason格式最终将其转化为Map
		Set<String> key = map.keySet();
		qq=key.toString();//获得第一行的标题
		qq=qq.replace("[", "").replace("]","").replace(", ", ",");
		title=qq.split(",");//获取map的key值
		//因CKAN上给出的数据的列较为混乱，因此为了显示更为良好再次进行调序
		for(int i=0;i<title.length;i++){
			if(title[i].equals("_id")) {all2=title[i];title[i]=title[0];title[0]=all2;}
			if(title[i].equals("city")) {all2=title[i];title[i]=title[1];title[1]=all2;}
			if(title[i].equals("station")) {all2=title[i];title[i]=title[2];title[2]=all2;}
			if(title[i].equals("longitude")) {all2=title[i];title[i]=title[3];title[3]=all2;}
			if(title[i].equals("latitude")) {all2=title[i];title[i]=title[4];title[4]=all2;}
			//if(title[i].equals("datetime")) {all2=title[i];title[i]=title[5];title[5]=all2;}	
		}
		for(int j=0;j<tt.length;j++){
			if(j!=tt.length-1)tt[j]=tt[j].concat("}");//这里会丢失一些大括号所以填上
			jasonObject = JSONObject.fromObject(tt[j]); 
			map = (Map)jasonObject;//String中含有大量括号、引号为方便处理，这里借助jason格式最终将其转化为Map					
			for(int i=0;i<title.length;i++){
				if(i!=title.length-1)all=all.concat(map.get(title[i]).toString()).concat(",");
				else all=all.concat(map.get(title[i]).toString());
			}
			all=all.concat("\n");		
		}
		qq="";
		for(int i=0;i<title.length;i++){
			if(i!=title.length-1)qq=qq.concat(title[i]).concat(",");
			else qq=qq.concat(title[i]);
		}
		all=qq.concat("\n").concat(all);
		System.out.println(all);
		for(int l=0;l<title.length;l++) System.out.print(title[l]);
		//System.out.println(tt[2]);
		}
		else all="false";
		}		
		else if(success.contains("false")){
			all="false";
		}
		return all;
	}
	private String Jason(String id,String limit){
		JSONStringer js = new JSONStringer();
        try {
            js.object();
            js.key("resource_id").value(id);                                                                                             
            js.key("limit").value(limit); 
            js.key("sort").value("_id desc");             
            js.endObject();
        } catch (JSONException e) {
            //e.printStackTrace();
            return null;
        }
        return js.toString().replace("true", "True");
	}
	public String[][] GetDataList(int idnum,String pacname){
		String[][] tt=new String[idnum][3];
		HttpResponse res = null;
		HttpClient httpclient = new DefaultHttpClient(); 
		HttpGet httpgets = new HttpGet("http://202.121.178.242/api/3/action/package_show?id="+pacname);	
        httpgets.addHeader("Authorization","1953544d-d697-4834-9a12-3b8637a1ff2b");
		try {
			res = httpclient.execute(httpgets);
		} catch (ClientProtocolException e) {
			System.out.println("shit");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("shit");
			e.printStackTrace();
		}    //执行请求
		HttpEntity entity = res.getEntity();    
        string = null;
		try {
			string = EntityUtils.toString(entity);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		string=string.substring(string.indexOf("[{"),string.indexOf("}]"));
		//对字符串开始修改
		
        tt[0][0]=string.substring(string.indexOf("\"id\"")+7,string.indexOf("size")-4);
        tt[0][1]=decodeUnicode(string.substring(string.indexOf("\"name\"")+9,string.indexOf("\"created\"")-3));
        tt[0][2]=decodeUnicode(string.substring(string.indexOf("\"description\"")+16,string.indexOf("\"format\"")-3));
        for(int i=1;i<Integer.valueOf(idnum);i++){
        string=string.substring(string.indexOf("\"created\"")+5);
        tt[i][0]=string.substring(string.indexOf("\"id\"")+7,string.indexOf("size")-4);
        tt[i][1]=decodeUnicode(string.substring(string.indexOf("\"name\"")+9,string.indexOf("\"created\"")-3));
        tt[i][2]=decodeUnicode(string.substring(string.indexOf("\"description\"")+16,string.indexOf("\"format\"")-3));
       // String string = URLEncoder.encode(EntityUtils.toString(entity),"UTF-8");
        }
		return tt;		
	}
}
