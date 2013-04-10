package Gis.namespace.Location;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import Gis.namespace.Location.GISLocationActivity.DFBG;
import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.User;
import Gis.namespace.Location.GISLocationActivity.WZBG;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


@SuppressLint("SdCardPath")
public class sbactivity extends Activity {
	
	
	String bh;
	int index;
	 Button databtn1 = null;
	 ListView listView1;
	 File dataf;
	
	 public static TextView tv;
	 public static TextView ebh;
	 public static TextView exmgc;
		public static View listv;
		public static  MyAdapter1 adapter;
	 public static ArrayList<HashMap<String, String>> fillMaps ;
		public static HashMap<String, String> gettable ;
		public static HashMap<String, String> getfkyj ;
		public Handler UPhandler;
		private ProgressDialog pd;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try{
			setContentView(R.layout.jssb);
			UPhandler=new Handler();
			fillMaps=new ArrayList<HashMap<String, String>>();
			getfkyj=new HashMap<String, String>();
		dataf = new File("/sdcard/XClocalhost/zjdafh");
		 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
		databtn1 = (Button) findViewById(R.id.xjbg);
		listView1=(ListView)findViewById(R.id.sblist);
		String sql="select  "+DFBG.BH+","+DFBG.DLWZ+","+DFBG.QY+","+DFBG.HD+","+DFBG.TIME+","+DFBG.FSZT+"  from "+WZBG.TABLE_NAME +" where "+DFBG.FSZT+"=0  order by bh asc";
	    Cursor c=db.rawQuery(sql, null);
		c.moveToFirst();
		for(int i=0;i<c.getCount();i++)
		{
			gettable=new HashMap<String, String>();
			String bh=c.getString(0);
			String ssgc=c.getString(2)+","+c.getString(3)+","+c.getString(1);
			String time=c.getString(4);
			
			String zt=null;
			
				zt="未发送";
		
			gettable.put("fszt", zt);
			gettable.put("bh", bh);
			gettable.put("ssgc", ssgc);
			gettable.put("time", time);
			
			fillMaps.add(gettable);
			c.moveToNext();
		}
		 Cursor c3=db.query(SETIP.TABLE_NAME, new String[]{SETIP.IP,SETIP.DK}, null, null, null, null, null);
	     	c3.moveToFirst();
		   final String setip=c3.getString(0);
		   final String setdk=c3.getString(1);
		   Cursor c1=db.query(User.TABLE_NAME, new String[]{User.USERID}, null, null, null, null, null);
		   c1.moveToFirst();
		   final String username=c1.getInt(0)+"";
		   c3.close();
		   c1.close();
		   db.close();
		   pd = new ProgressDialog(sbactivity.this);
			pd.setTitle("正在加载");
			pd.setMessage("请稍后！");
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			new Thread()
			{
				public void run(){
					 HttpClient httpclient = new DefaultHttpClient();
					  String url="http://"+setip+":"+setdk+"/androidserver/servlet/WZBDServlet";
			       HttpPost httppost = new HttpPost(url);
			       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//			            username=changeToUnicode(username);
						 nameValuePairs.add(new BasicNameValuePair("username", username)); 			
						 HttpResponse response; 
						 try {
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						
							response=httpclient.execute(httppost); 
							if (response.getStatusLine().getStatusCode() == 200) {  
				                // 获取返回的数据  
//				                String result = EntityUtils.toString(response.getEntity(), "UTF-8");  
				                HttpEntity resEntity = response.getEntity();
				        		if (resEntity != null) {
//				        			        System.out.println(EntityUtils.toString(resEntity,"utf-8"));
				        			  StringEntity my_entity=new StringEntity(EntityUtils.toString(resEntity), "utf-8");
				        			   String result=EntityUtils.toString(my_entity); 
				        			   JSONArray jsonArr = new JSONObject(result).getJSONArray("values");
				        			   for(int i=0;i<jsonArr.length();i++)
				        			   {
				        			   JSONObject jsonObj=(JSONObject)jsonArr.get(i);
				        			   gettable=new HashMap<String, String>();
				        			   String bh=jsonObj.getString("bh");
				  					 String sj=jsonObj.getString("sj");
				  					 String ssxmgc=jsonObj.getString("ssxmgc"); 
				  					 String zt=jsonObj.getString("zt"); 
				  					 if(zt.equals("待反馈"))
				  					 {
				  						getfkyj.put(bh, " "); 
				  					 }
				  					 else
				  					 {
				  						String fkyj=jsonObj.getString("fkyj"); 
				  						getfkyj.put(bh, fkyj);
				  					 }
				  					gettable.put("bh", bh);
				  					gettable.put("time", sj);
				  					gettable.put("ssgc", ssxmgc);
									 gettable.put("fszt", zt);
									 fillMaps.add(gettable);
				        			   }
				        		}
							}else {  
				                Log.i("HttpPost", "HttpPost方式请求失败");  
				            } 
						 } catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								 Log.i("HttpPost", e.getMessage());  
							} 
						 UPhandler.post(runnableUi);
						 pd.cancel();
				}
				}.start();
		  
				 String[] from=new String[] {"fszt","bh","time", "ssgc"}; 
				 int[] to=new int[] { R.id.user_id, R.id.user_bh,R.id.user_sj,R.id.user_gc};
		 adapter = new MyAdapter1(sbactivity.this,fillMaps,R.layout.listitem,from,to);
		  listView1.setAdapter(adapter);
		  
		  this.listView1.setOnItemClickListener(new OnItemClickListener(){

				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
				
//		                View v=	arg0.getChildAt(0);
					 tv=(TextView)arg1.findViewById(R.id.user_id);
					 ebh=(TextView)arg1.findViewById(R.id.user_bh);
					 exmgc=(TextView)arg1.findViewById(R.id.user_gc);
					   listv=arg1;
					HashMap<String, String> map = (HashMap<String, String>) adapter
							.getItem(arg2);

							bh=map.get("bh");
                            String fszt=map.get("fszt");
                            String fk=" ";
                            String bh2=bh.substring(0, 2);
							 if(bh2.equals("WZ"))
							 {
								 fk=getfkyj.get(bh);
							 }
//							 {
				        		 Intent intentwz =new Intent(sbactivity.this,wzsbbdactivity.class);
				        		 intentwz.putExtra("bh", bh);
				        		 intentwz.putExtra("fszt", fszt);
				        		 intentwz.putExtra("fkyj",fk);
							     startActivity(intentwz);
				        		
//							 }
//							 else
//							 {
//								 Intent intentwz =new Intent(sbactivity.this,wzSecactivity.class);
//				        		 intentwz.putExtra("bh", bh);
//				        		 intentwz.putExtra("fszt", fszt);
//				        		 intentwz.putExtra("fkyj", getfkyj.get(bh));
//							     startActivity(intentwz);
//							 }
				        	
				}
				  
			  });
		  this.listView1.setOnItemLongClickListener(new OnItemLongClickListener(){
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, String> map = (HashMap<String, String>) adapter
							.getItem(arg2);
					index=arg2;
//							String id = map.get("id");
							 bh=map.get("bh");
							 String bh2=bh.substring(0, 1);
							 if(bh2.equals("违"))
							 {
//							tablename=Qureyactivity.gettable.get(id);
//							if(tablename.equals(DFBG.TABLE_NAME)||tablename.equals(DFBG.TABLE_NAME)||tablename.equals(DFBG.TABLE_NAME))
//							{
								listView1.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {       
								public void onCreateContextMenu(ContextMenu arg0,
											View arg1, ContextMenuInfo arg2) {
//									arg0.add(0,1,Menu.NONE,"打开");
									arg0.add(0,1,Menu.NONE,"删除");	
										// TODO Auto-generated method stub
										
									}       
						         });       
//							}
							 }
							return false;
				}
				
			});
		  databtn1.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try
				{
				String sql="select max(bh) from xjbg where  bh like '%违%'";
				 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
				 Cursor c=db.rawQuery(sql, null);
				 c.moveToFirst();
				 String newbh;
			
				 String lbh=c.getString(0);
				 Date date = new Date(System.currentTimeMillis());
			     SimpleDateFormat Forma = new SimpleDateFormat(  
		                 "yyyyMMdd-HHmmss");
			   
			     String time=Forma.format(date);
			    
			    String  nf=time.substring(2,4);
				 if(lbh==null)
				 {
					 newbh ="违"+nf+"-1";
				 }
				 else
				 {
					String sl= lbh.substring(4);
				 int i=Integer.parseInt(sl)+1;
				  newbh="违"+nf+"-"+i+"";
				 }
				 
				 SimpleDateFormat Format = new SimpleDateFormat(  
		                 "yyyy年MM月dd日");
			     String sj=Format.format(date);
				 ContentValues values = new ContentValues();
				   
					values.put(DFBG.BH,newbh);
					values.put(DFBG.FSZT,0);
					values.put(DFBG.TIME,sj);
					db.insert(WZBG.TABLE_NAME, null, values);
					db.close();
					c.close();
					gettable=new HashMap<String, String>();
					gettable.put("fszt", "未发送");
					gettable.put("bh", newbh);
					gettable.put("ssgc", " ");
					gettable.put("time", sj);
					fillMaps.add(gettable);
		        	 adapter.mItemList=fillMaps;
		        	 adapter.notifyDataSetChanged();
				 Intent intentwz =new Intent(sbactivity.this,wzsbbdactivity.class);
				 intentwz.putExtra("bh", newbh);
				 intentwz.putExtra("fszt", "未发送");
				 startActivity(intentwz);
				}catch(Exception e){
			        e.printStackTrace();
			        Log.i("HttpPost", e.getMessage());  
			    }
				
			}
			  
		  });
		
		
		 if(ManageActivity.allActiviy.get("sbactivity")!=null)
			{
		 ManageActivity.addActiviy("sbactivity", sbactivity.this);
			}
		}catch(Exception e){
	        e.printStackTrace();
	        Log.i("HttpPost", e.getMessage());  
	    }
		
		}
	 Runnable   runnableUi=new  Runnable(){  
	        
	        public void run() {  
	            //更新界面  
	        	adapter.mItemList=fillMaps;
	        	 adapter.notifyDataSetChanged();	        }  
	          
	    };  
	public static void dosetback1(String bh,String ssmc)
	{
		tv.setText("待反馈");
		ebh.setText(bh);
		exmgc.setText(ssmc);
		listv.setBackgroundColor(color.black);
	}
	public static void dosetback2()
	{
		tv.setText("已办结");
		listv.setBackgroundColor(color.black);
	}
	public static void additem(String fszt,String bh,String ssgc,String time)
	{
		HashMap<String, String> newitem=new  HashMap<String, String>();
		newitem.put("fszt", fszt);
		newitem.put("bh", bh);
		newitem.put("ssgc", ssgc);
		newitem.put("time", time);
		fillMaps.add(newitem);
		adapter.mItemList=fillMaps;
   	    adapter.notifyDataSetChanged();
//   	 listView.refreshDrawableState();
	}
	public static void doitem(String bh,String fszt,String bb,String ssgc,String time)
	{
		HashMap<String, String> newitem1=new  HashMap<String, String>();
	
	for(int i=0;i<fillMaps.size();i++)
	{
		newitem1=fillMaps.get(i);
		if(newitem1.get("bh").equals(bh))
		{
			fillMaps.remove(i);
			break;
		}
	}
		HashMap<String, String> newitem=new  HashMap<String, String>();
		newitem.put("fszt", fszt);
		newitem.put("bh", bb);
		newitem.put("ssgc", ssgc);
		newitem.put("time", time);
		fillMaps.add(newitem);
		
		adapter.mItemList=fillMaps;
   	    adapter.notifyDataSetChanged();
//   	 listView.refreshDrawableState();
	}
	public static void delitem(String bh)
	{
		HashMap<String, String> newitem=new  HashMap<String, String>();
		for(int i=0;i<fillMaps.size();i++)
		{
			newitem=fillMaps.get(i);
			if(newitem.get("bh").equals(bh))
			{
				fillMaps.remove(i);
				break;
			}
		}
		
		
		adapter.mItemList=fillMaps;
   	    adapter.notifyDataSetChanged();
//   	 listView.refreshDrawableState();
	}
	  public static String changeToUnicode(String str){
	    	StringBuffer strBuff = new StringBuffer();
	    	for(int i=0;i<str.length();i++){
	    	String temp = Integer.toHexString(str.charAt(i));
	    	if(temp.length()!=4){
	    	temp = "00"+temp;
	    	}
	    	if(temp.equals("00d")){
	    	temp = "0"+temp;
	    	}
	    	if(temp.equals("00a")){
	    	temp = "0"+temp;
	    	}
	    	strBuff.append(temp.substring(0, temp.length()-2));
	    	strBuff.append(temp.substring(temp.length()-2, temp.length()));
	    	}
	    	String returnData = strBuff.toString();
	    	return returnData;
	    	}
	  @Override       
	    public boolean onContextItemSelected(MenuItem aItem) {  
		 try
		 {
	         switch (aItem.getItemId()) {       
	         case 1:
	        	
	        	 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
	        	 String where="bh='"+bh+"'";
	        	 db.delete(WZBG.TABLE_NAME, where, null);
	        	fillMaps.remove(index);
	        	 adapter.mItemList=fillMaps;
	        	 adapter.notifyDataSetChanged();
	        	 db.close();
	        	 break;
	         }    
		 }catch(Exception e){
		        e.printStackTrace();
		        Log.i("HttpPost", e.getMessage());  
		    }
	         return false;       
	    }  
		@Override
		protected void onDestroy() {
			super.onDestroy();
			
		}

		@Override
		protected void onPause() {
			super.onPause();
		
		}

		@Override
		protected void onResume() {
			super.onResume();
		
		}
		 public class MyAdapter1 extends SimpleAdapter{
		        int count = 0;
		        private List<HashMap<String, String>> mItemList;
		        @SuppressWarnings("unchecked")
				public MyAdapter1(Context context, List<? extends HashMap<String, String>> data,
		                int resource, String[] from, int[] to) {
		            super(context, data, resource, from, to);
		            mItemList = (List<HashMap<String, String>>) data;
		            if(data == null){
		                count = 0;
		            }else{
		                count = data.size();
		            }
		        }
		       
		        @Override
		        public View getView(int position, View convertView, ViewGroup parent) {
		            HashMap<String ,String> map = mItemList.get(position);
		            String image  =  map.get("fszt");
		            View view = super.getView(position, convertView, parent);
//		            TextView imageview = (TextView)view.findViewById(R.id.user_id);
		            if(image.equals("已反馈"))
		            {
//		            	view.setBackgroundColor(Color.RED);
//		            	view.getBackground().setAlpha(25);
		            
		            }
		            else if(image.equals("未反馈"))
		            {
//		            	view.setBackgroundColor(color.black);
		            }
		            else
		            {
//		            	view.setBackgroundColor(Color.RED);
//		            	view.getBackground().setAlpha(25);
		            }
//		           TextView imageview = (TextView)view.findViewById(R.id.user_id);
//		            TextView tv = (TextView)view.findViewById(R.id.tv);
//		            imageview.setBackgroundResource(image);
//		            tv.setText(""+position);
		            return view;
		        }
		    }
}
