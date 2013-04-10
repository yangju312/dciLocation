package Gis.namespace.Location;

import java.io.File;
import java.util.ArrayList;
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

import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.TZ;
import Gis.namespace.Location.GISLocationActivity.User;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class tzactivity extends Activity {
	String tablename;
	String bh;
	int index;
	 String username;
	ListView listView;
	public  ArrayList<HashMap<String, String>> fillMaps ;
	public  HashMap<String, String> gettable ;
	public  HashMap<String, String> getid ;
	MyAdapter adapter1;
public static String title;
    File dataf;
    String url;
	public static String message;
	 private ProgressDialog pd;
	    private Handler UPhandler;
//	 DBConnection helper;
	@SuppressLint("SdCardPath")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		
		listView=(ListView)this.findViewById(R.id.list);
//		helper=new DBConnection(Resultactivity.this);
//		 List<HashMap<String, String>> fillMap = new ArrayList<HashMap<String, String>>();
		try
		{   UPhandler=new Handler();
			 dataf = new File("/sdcard/XClocalhost/zjdafh");
			 fillMaps=new ArrayList<HashMap<String, String>>();
			 getid=new HashMap<String, String>();
			 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
			 Cursor c1=db.query(SETIP.TABLE_NAME, new String[]{SETIP.IP,SETIP.DK}, null, null, null, null, null);
		     	c1.moveToFirst();
			   final String setip=c1.getString(0);
			   final String setdk=c1.getString(1);
			   c1.close();
			   Cursor c2=db.query(User.TABLE_NAME, new String[]{User.USER}, null, null, null, null, null);
			   c2.moveToFirst();
			    username=c2.getString(0);
			   c2.close();
			   pd = new ProgressDialog(tzactivity.this);
				pd.setTitle("正在加载");
				pd.setMessage("请稍后！");
				pd.setCanceledOnTouchOutside(false);
				pd.show();
				new Thread()
				{
					public void run(){
			   HttpClient httpclient = new DefaultHttpClient();
				 url="http://"+setip+":"+setdk+"/androidserver/servlet/TZServlet";
		       HttpPost httppost = new HttpPost(url);
		       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		            username=sbactivity.changeToUnicode(username);
					 nameValuePairs.add(new BasicNameValuePair("username", username)); 	
					 nameValuePairs.add(new BasicNameValuePair("isopen", "false")); 
					 HttpResponse response; 
					 try {
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
						response=httpclient.execute(httppost); 
						if (response.getStatusLine().getStatusCode() == 200) {  
			                // 获取返回的数据  
//			                String result = EntityUtils.toString(response.getEntity(), "UTF-8");  
			                HttpEntity resEntity = response.getEntity();
			        		if (resEntity != null) {
//			        			        System.out.println(EntityUtils.toString(resEntity,"utf-8"));
			        			  StringEntity my_entity=new StringEntity(EntityUtils.toString(resEntity), "utf-8");
			        			   String result=EntityUtils.toString(my_entity); 
			        			   JSONArray jsonArr = new JSONObject(result).getJSONArray("values");
			        			   for(int i=0;i<jsonArr.length();i++)
			        			   {
			        			   JSONObject jsonObj=(JSONObject)jsonArr.get(i);
			        			   gettable=new HashMap<String, String>();
			        			   String title=jsonObj.getString("sendtitle");
			  					 String sj=jsonObj.getString("sendtime");
			  					 String message=jsonObj.getString("sendmessage"); 
			  					 String zt=jsonObj.getString("zt"); 
			  					 String pid=jsonObj.getString("pid"); 
			  					 String zh=jsonObj.getString("zh"); 
			                      gettable.put("title", title);
			  					gettable.put("sj", sj);
			  					gettable.put("message", message);
								 gettable.put("zt", zt);
								 gettable.put("zh", zh);
								 fillMaps.add(gettable);
								 getid.put(i+"", pid);
			        			   }
			        		}
						}else {  
			                Log.i("HttpPost", "HttpPost方式请求失败");  
			            } 
						UPhandler.post(runnableUi);
						pd.cancel();
					 } catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							 Log.i("HttpPost", e.getMessage());  
						} 
					 UPhandler.post(runnableUi);
						pd.cancel();
					}}.start();
////		        String sql="select Max("+TZ.ID+") from "+TZ.TABLE_NAME;
//		        Cursor c =db.query(TZ.TABLE_NAME,  null, null,null,null,null,null);
//		        c.moveToFirst();
//		        for(int i=0;i<c.getCount();i++ )
//		        {
//		        	HashMap<String, String> map=new HashMap<String, String>();
//		        	map.put("id", c.getString(0));
//		        	map.put("title", c.getString(1));
//		        	map.put("message", c.getString(2));
//		        	map.put("url", c.getString(3));
//		        	fillMaps.add(map);
//		        	c.moveToNext();
//		        }
		 String[] from=new String[] {"zt","title", "sj","message"}; 
		 int[] to=new int[] { R.id.user_id, R.id.user_bh,R.id.user_gc,R.id.user_sj};
		 adapter1 = new MyAdapter(tzactivity.this,fillMaps,R.layout.listitem,from,to);
		  listView.setAdapter(adapter1);
		  this.listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				HashMap<String, String> map = (HashMap<String, String>) tzactivity.this.adapter1
						.getItem(arg2);
				 title = map.get("title");
				message = map.get("message");
                   String  time=map.get("sj");
                   String zt=map.get("zt");
                   String zh=map.get("zh");
                   TextView   tv=(TextView)arg1.findViewById(R.id.user_id);
                   HttpClient httpclient = new DefaultHttpClient();
                   HttpPost httppost = new HttpPost(url);
    		       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
    		           String p_id=getid.get(arg2+"");
    					 nameValuePairs.add(new BasicNameValuePair("pid", p_id)); 	
    					 nameValuePairs.add(new BasicNameValuePair("username", username)); 	
    					 nameValuePairs.add(new BasicNameValuePair("isopen", "true")); 
    					 HttpResponse response; 
    					 try {
    						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    					
    						response=httpclient.execute(httppost); 
    						if (response.getStatusLine().getStatusCode() == 200) {
//    							TextView tv=(TextView)arg1.findViewById(R.id.user_id);
    							if(!zt.equals("已读"))
    							{
    							tv.setText("已读");
    							Resources r = getBaseContext().getResources();  
    							Drawable d = r.getDrawable(R.drawable.bigback);
    							arg1.setBackgroundDrawable(null);
    							arg1.setBackgroundDrawable(d);
    							}
    						}else
    						{
//    							tv.setText("已读");
//    							arg1.setBackgroundColor(color.black);
    						}
    						
    						}catch (Exception e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    							 Log.i("HttpPost", e.getMessage());  
    						} 
                     Intent query = new Intent(tzactivity.this,tzitemactivity.class); 
                     query.putExtra("title", title);
                     query.putExtra("message", message);
                     query.putExtra("time", time);
                     query.putExtra("zh", zh);
     			    startActivity(query); 
     			    

			}
			  
		  });

		}catch(Exception e){
	        e.printStackTrace();
	        Log.i("HttpPost", e.getMessage());  
	    }
		if(ManageActivity.allActiviy.get("tzactivity")!=null)
		{
		ManageActivity.addActiviy("tzactivity", tzactivity.this);}
		}

	
	 Runnable   runnableUi=new  Runnable(){  
	        
	        public void run() {  
	            //更新界面  
	        	
	     adapter1.mItemList=fillMaps;
   	     adapter1.notifyDataSetChanged();
   	     
	        }  
	          
	    }; 
		@Override
		protected void onDestroy() {
			super.onDestroy();
			
		}
		@Override
		public void onBackPressed() {
		super.onBackPressed();
		if(ManageActivity.allActiviy.get("cdActivity")==null)
		{
			Intent intent = new Intent(tzactivity.this,cdActivity.class);
		startActivity(intent);}
		else
		{
//			ManageActivity.allActiviy.get("cdActivity");
		}
//		Intent intent = new Intent(tzactivity.this,cdActivity.class);
//		startActivity(intent);
		}
		@Override
		protected void onPause() {
			super.onPause();
		
		}

		@Override
		protected void onResume() {
			super.onResume();
		
		}
		   private class MyAdapter extends SimpleAdapter{
		        int count = 0;
		        private List<HashMap<String, String>> mItemList;
		        @SuppressWarnings("unchecked")
				public MyAdapter(Context context, List<? extends HashMap<String, String>> data,
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
		            String image  =  map.get("zt");
		            View view = super.getView(position, convertView, parent);
//		            TextView imageview = (TextView)view.findViewById(R.id.user_id);
		            if(image.equals("未读"))
		            {
		            	Resources r = getBaseContext().getResources();  
						Drawable d = r.getDrawable(R.drawable.tzitem);
						
//						view.setBackgroundDrawable(null);
						view.setBackgroundDrawable(d);
		            
		            }
		            else
		            {
		            	Resources r = getBaseContext().getResources();  
						Drawable d = r.getDrawable(R.drawable.bigback);
//						arg1.setBackgroundDrawable(null);
						view.setBackgroundDrawable(d);
//		            	view.setBackgroundColor(color.black);
		            }
//		           TextView imageview = (TextView)view.findViewById(R.id.user_id);
//		            TextView tv = (TextView)view.findViewById(R.id.tv);
//		            imageview.setBackgroundResource(image);
//		            tv.setText(""+position);
		            return view;
		        }


				private Object getBackground() {
					// TODO Auto-generated method stub
					return null;
				}
		    }
}
