package com.sjtu.netcenter.httpclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sjtu.netcenter.test.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	 private Context context;    
	 private LayoutInflater layoutInflater;
	 private List<HashMap<String, Object>> list;
	 //构造方法，参数list传递的就是这一组数据的信息
	 public MyAdapter(Context context, ArrayList<HashMap<String, Object>> lstImageItem)
	 {
	 this.context = context;         
	 layoutInflater = LayoutInflater.from(context);         
	 this.list = lstImageItem;}
	@Override
	public int getCount() {
		return this.list!=null? this.list.size(): 0 ;
	}

	@Override
	public Object getItem(int position) {
		 return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		  return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 ViewHolder hd = null;
	    //    if (convertView == null) {
	            hd = new ViewHolder();
	            convertView = layoutInflater.inflate(R.layout.night_item, null);
	            hd.mCountTv = (TextView) convertView
	                    .findViewById(R.id.ItemText);
	//        } else {
	 //           hd = (ViewHolder) convertView.getTag();
	 //       }
		TextView tv1 = (TextView)convertView.findViewById(R.id.ItemText);
		tv1.setText(list.get(position).get("ItemText").toString());
		return convertView;
	}
	class ViewHolder {
        TextView mCountTv;
    }

}
