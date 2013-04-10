package Gis.namespace.Location;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.androidpn.client.NotificationService;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;



import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.User;
import Gis.namespace.Location.GISLocationActivity.UserSchema;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class zjdflocationservice extends Service {
	 String username=null;
	LocationManager loc = null;
	Location GPSLocation=null;
	Location NETLocation=null;
	Thread LocationThread;
	Handler addlayerhandler;
	boolean isruning=true;
	Handler handler;
	String setip=null;
	String setdk=null;
	File dataf;
	int isl=1;
	Uri uri = Uri.parse("content://telephony/carriers");   
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	
	  @SuppressLint("SdCardPath")
	@Override
	    public void onCreate() {
		  try{
			  dataf = new File("/sdcard/XClocalhost/zjdafh");
			    SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
				
				 Cursor c=db.query(SETIP.TABLE_NAME, new String[]{SETIP.IP,SETIP.DK}, null, null, null, null, null);
			     	c.moveToFirst();
				   setip=c.getString(0);
				  setdk=c.getString(1);
				  
				   c.close();
				   Cursor c1=db.query(User.TABLE_NAME, new String[]{User.USERID}, null, null, null, null, null);
              	   c1.moveToFirst();
              	   username=c1.getInt(0)+"";
              	   c1.close();
              	 db.close();
			  boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER );
			  if(!gpsEnabled){
//				  Settings.Secure.setLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER, true);
				  Intent gpsIntent = new Intent(); 
				    gpsIntent.setClassName("com.android.settings", 
				            "com.android.settings.widget.SettingsAppWidgetProvider"); 
				    gpsIntent.addCategory("android.intent.category.ALTERNATIVE"); 
				    gpsIntent.setData(Uri.parse("custom:3")); 
				    try { 
				        PendingIntent.getBroadcast(zjdflocationservice.this, 0, gpsIntent, 0).send(); 
				    } 
				    catch (CanceledException e) { 
				        e.printStackTrace(); 
				    }
				  }
			  }catch (Exception e) {
					// TODO Auto-generated catch block
				 Log.i("result", e.getMessage()); 
					
				} 
			  try
			  {
//				  if(isNetworkAvailable(this) == false){
					  
					  toggleMobileData(zjdflocationservice.this,true);
//		        	}

			  }catch (Exception e) {
					// TODO Auto-generated catch block
				 Log.i("result", e.getMessage()); 
					
				} 
			 
				  loc = (LocationManager) zjdflocationservice.this.getSystemService(Context.LOCATION_SERVICE);
				loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 0,
						new LocationListener() {

							public void onLocationChanged(Location location) {
								// TODO Auto-generated method stub
//							GPSLocation=location;
								
								
							}

							public void onProviderDisabled(String provider) {
								// TODO Auto-generated method stub

							}

							public void onProviderEnabled(String provider) {
								// TODO Auto-generated method stub

							}

							public void onStatusChanged(String provider, int status,
									Bundle extras) {
								// TODO Auto-generated method stub

							}

						});
			handler =new  Handler() {
			@Override
          public void handleMessage(Message msg) {
              switch (msg.what) {
              case 0:
            	  try
            	  {
            		  
            		  try{
            			  boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER );
            			  if(!gpsEnabled){
//            				  Settings.Secure.setLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER, true);
            				  Intent gpsIntent = new Intent(); 
            				    gpsIntent.setClassName("com.android.settings", 
            				            "com.android.settings.widget.SettingsAppWidgetProvider"); 
            				    gpsIntent.addCategory("android.intent.category.ALTERNATIVE"); 
            				    gpsIntent.setData(Uri.parse("custom:3")); 
            				    try { 
            				        PendingIntent.getBroadcast(zjdflocationservice.this, 0, gpsIntent, 0).send(); 
            				    } 
            				    catch (CanceledException e) { 
            				        e.printStackTrace(); 
            				    }
            				  }
            			 
            			  }catch (Exception e) {
            					// TODO Auto-generated catch block
            				 Log.i("result", e.getMessage()); 
            					
            				} 
            			  try
            			  {
            				  if(isNetworkAvailable(zjdflocationservice.this) == false){
            					  
            					  
            				toggleMobileData(zjdflocationservice.this,true);
            				Intent mService = new Intent(zjdflocationservice.this, NotificationService.class);
            				 stopService(mService );  //停止服务

//            				Intent mService1 = new Intent(zjdflocationservice.this, NotificationService.class);
//            				 startService(mService1 );
//           				Intent intent1 =new Intent("org.androidpn.client.NotificationService");
//           				zjdflocationservice.this.startService(intent1);

           		        	}

            			  }catch (Exception e) {
            					// TODO Auto-generated catch block
            				 Log.i("result", e.getMessage()); 
            					
            				} 
            			  try{
              	GPSLocation = loc.getLastKnownLocation( LocationManager.GPS_PROVIDER );
              	if(GPSLocation==null) return;
              	Point ptLatLon = new Point(GPSLocation.getLongitude(),
              			GPSLocation.getLatitude());
				// 经纬度坐标
				SpatialReference sr4326 = SpatialReference.create(4326);
				SpatialReference sr2383 = SpatialReference.create(2383);
				// 坐标转换
//				ArcGISLocalTiledLayer local;
//				local = new ArcGISLocalTiledLayer("file:///mnt/sdcard/80/Layers");
				
				Point centerPt = (Point) GeometryEngine.project(
						ptLatLon, sr4326,
						sr2383);

//				ContentValues values = new ContentValues();
//				values.put(UserSchema.Time,
//						sdf.format(new Date(GPSLocation.getTime())));
//				values.put(UserSchema.LAT, centerPt.getX());
//				values.put(UserSchema.LON, centerPt.getY());
				
            			 
				HttpClient httpclient = new DefaultHttpClient();
				
				  String url="http://"+setip+":"+setdk+"/androidserver/servlet/TestServlet";
                     HttpPost httppost = new HttpPost(url);
                    
				 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3); 
				 nameValuePairs.add(new BasicNameValuePair("userid",username)); 
				 nameValuePairs.add(new BasicNameValuePair("x", centerPt.getX()+"")); 
				 nameValuePairs.add(new BasicNameValuePair("y", centerPt.getY()+""));
				 HttpResponse response; 
				 try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					response=httpclient.execute(httppost); 
					if (response.getStatusLine().getStatusCode() == 200) {  
		                // 获取返回的数据  
		                String result = EntityUtils.toString(response.getEntity(), "UTF-8");  
		                Log.i("HttpPost", "HttpPost方式请求成功，返回数据如下：");  
		                Log.i("result", result); 
//		                Toast.makeText(GISLocationActivity.this, result,Toast.LENGTH_LONG).show();
		            } else {  
		                Log.i("HttpPost", "HttpPost方式请求失败");  
		            }  
					 //Toast.makeText(GISLocationActivity.this, response.toString(),Toast.LENGTH_LONG).show();
//					 Log.v("response text", response.);     
					 
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            	  }catch (Exception e) {
//    				// TODO Auto-generated catch block
//    				e.printStackTrace();
    			}   }catch (Exception e) {
  					// TODO Auto-generated catch block
  				 Log.i("result", e.getMessage()); 
  					
  				} break;
    			
              case 1:
            	  break;
              case 2:
            	  isruning=false;
					 Intent intent1 =new Intent("org.androidpn.client.NotificationService");
					  Intent intent =new Intent("zjdflocationservice");
				        GISLocationActivity.getcon().stopService(intent);
				        GISLocationActivity.getcon().stopService(intent1);
            	  break;
              }
          }
		};
		LocationThread= new Thread(new Runnable() {
			 public void run() {
				 while(isruning)
				 {
					 
					try {
						
						Thread.currentThread();
						Thread.sleep(120000);
						 Message msg = new Message();
						 Date date = new Date(System.currentTimeMillis()); 
						 SimpleDateFormat dateFormat = new SimpleDateFormat(  
				                 "yyyyMMdd-HHmm");
						 int nowtime=Integer.parseInt(dateFormat.format(date).substring(9));
						 if((nowtime>=830&&nowtime<1200)||(nowtime>=1400&&nowtime<1800))
						 {
	                        msg.what = 0;
	                        handler.sendMessage(msg);
						 }
						 else if((nowtime>=1200&&nowtime<1400)||(nowtime>=700&&nowtime<900))
						 {
							 msg.what = 1;
		                        handler.sendMessage(msg);
						 }
						 else
						 {
							 msg.what = 1;
		                        handler.sendMessage(msg);
						 }
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				 }
				 
			 }
		});
		LocationThread.start();
	  }
	  @Override
	  public void onStart(Intent intent, int startId)
	  {}
	  
	  @Override
	    public void onDestroy() {
	        Log.d("locationservice", "onDestroy()...");
//	        stop();
	    }

	    @Override
	    public IBinder onBind(Intent intent) {
	        Log.d("locationservice", "onBind()...");
	        return null;
	    }

	    @Override
	    public void onRebind(Intent intent) {
	        Log.d("locationservice", "onRebind()...");
	    }

	    @Override
	    public boolean onUnbind(Intent intent) {
	        Log.d("locationservice", "onUnbind()...");
	        return true;
	    }
	    private void toggleMobileData(Context context, boolean enabled) {
	    	  ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

	    	  Class<?> conMgrClass = null; // ConnectivityManager类
	    	  Field iConMgrField = null; // ConnectivityManager类中的字段
	    	  Object iConMgr = null; // IConnectivityManager类的引用
	    	  Class<?> iConMgrClass = null; // IConnectivityManager类
	    	  Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法

	    	  try {
	    	   // 取得ConnectivityManager类
	    	   conMgrClass = Class.forName(conMgr.getClass().getName());
	    	   // 取得ConnectivityManager类中的对象mService
	    	   iConMgrField = conMgrClass.getDeclaredField("mService");
	    	   // 设置mService可访问
	    	   iConMgrField.setAccessible(true);
	    	   // 取得mService的实例化类IConnectivityManager
	    	   iConMgr = iConMgrField.get(conMgr);
	    	   // 取得IConnectivityManager类
	    	   iConMgrClass = Class.forName(iConMgr.getClass().getName());
	    	   // 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
	    	   setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
	    	   // 设置setMobileDataEnabled方法可访问
	    	   setMobileDataEnabledMethod.setAccessible(true);
	    	   // 调用setMobileDataEnabled方法
	    	   setMobileDataEnabledMethod.invoke(iConMgr, enabled);
	    	  } catch (ClassNotFoundException e) {
	    	   e.printStackTrace();
	    	  } catch (NoSuchFieldException e) {
	    	   e.printStackTrace();
	    	  } catch (SecurityException e) {
	    	   e.printStackTrace();
	    	  } catch (NoSuchMethodException e) {
	    	   e.printStackTrace();
	    	  } catch (IllegalArgumentException e) {
	    	   e.printStackTrace();
	    	  } catch (IllegalAccessException e) {
	    	   e.printStackTrace();
	    	  } catch (InvocationTargetException e) {
	    	   e.printStackTrace();
	    	  }
	    	 }
	    private static boolean isNetworkAvailable(Context context) {
			// TODO Auto-generated method stub
	    	try{
	    	
	    		ConnectivityManager cm = (ConnectivityManager)context
	    				.getSystemService(Context.CONNECTIVITY_SERVICE);
	    		NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
	    		return (netWorkInfo != null && netWorkInfo.isAvailable());//妫�祴缃戠粶鏄惁鍙敤
	    	}catch(Exception e){
	    		e.printStackTrace();
	                 
	    		return false;
	    	}
		}
	   
}
