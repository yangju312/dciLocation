package Gis.namespace.Location;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.androidpn.client.ServiceManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import Gis.namespace.Location.GpsTask.GpsData;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

//import com.Dci.location.Updata.MainActivity.DBConnection;
//import com.Dci.location.Updata.MainActivity.UserSchema;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
//import com.esri.arcgis.android.samples.viewshed.R;

//import com.esri.android.map.GraphicsLayer;

import com.esri.android.map.ags.ArcGISLocalTiledLayer;
import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.map.event.OnZoomListener;
//import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
//import com.esri.android.map.event.OnSingleTapListener;
//import com.esri.android.map.event.OnStatusChangedListener;
//import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;

import com.updateapp.GetUpdateInfo;

//import com.example.android.db01.R;
//import com.esri.core.symbol.SimpleFillSymbol;
//import com.esri.core.symbol.SimpleMarkerSymbol;
//import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;


public class GISLocationActivity extends Activity {
	boolean iszoom = true;
	
	OnClickListener onc;
	static MapView mMapView;
	TableLayout layout;
	LocationManager loc = null;
	Location GPSLocation=null;
	Location NETLocation=null;
	GraphicsLayer gLayer;
	GraphicsLayer gpsLayer;
	GraphicsLayer cxdwLayer;
	GraphicsLayer ptLayer;
	GraphicsLayer pskLayer;
//	Button btn;
	Button ed;
	String lastbh="";
	private ProgressDialog pd;
	Thread LocationThread;
	Thread addlayerThread;
	Handler addlayerhandler;
	boolean isruning=true;
	Handler handler;
	private RadioButton fbNews;
	private RadioButton fbQuestion;
	private RadioButton fbTweet;
	private RadioButton fbactive;
//	public static DBConnection helper;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	public interface User {
		String TABLE_NAME = "DL"; // Table Name
		String ID = "_id"; // ID
		String USER = "user"; // User Name
		String PASS = "password"; 
		String USERNAME="username";
		String USERID="userid";
		
		// Mail Address
	}
	public interface UserSchema {
		String TABLE_NAME = "zg"; // Table Name
		String ID = "_id"; // ID
		String Time = "time"; // User Name
		String LAT = "x"; 
		String LON = "y";
		// Mail Address
	}
	public interface TZ {
		String TABLE_NAME = "tz"; // Table Name
		String ID = "id"; // ID
		String TITLE = "title"; // User Name
		String MESSAGE = "message"; 
		String URL = "url";
		// Mail Address
	}
	public interface TBZH {
		String TABLE_NAME = "tbzh"; // Table Name
		String ID = "_id"; // ID
		String ZH = "zh"; // User Name
		String X = "x"; 
		String Y = "Y";
		String ISDM = "isdm";
		// Mail Address
	}
	public interface XJBH {
		String TABLE_NAME = "xjbh"; // Table Name
		String ID = "_id"; // ID
		String NF = "year"; 
		String BH = "bh"; 
		
		// Mail Address
	}
	public interface WZBH {
		String TABLE_NAME = "xjbh"; // Table Name
		String ID = "_id"; // ID
		String NF = "year"; 
		String BH = "bh"; 
		
		// Mail Address
	}
	 public interface Image {
		    String ID = "_id";
	    	String TABLE_NAME = "image"; 
	    	String TABLENAME="xpfj";
	    	String  BH ="bh"; 
	    	String  XP ="xp";        
			
	    }
	 public interface WZXP {
		    String ID = "_id";
	    	String TABLE_NAME = "image"; 
	    	
	    	String  BH ="bh"; 
	    	String  XP ="xp";        

			
	    }
	 public interface  SETIP{
		    String ID = "_id";
	    	String TABLE_NAME = "setip"; 
	    	String  IP ="ip"; 
	    	String DK ="dk";        

			
	    }
	 public interface WGBG {
			String ID = "_id";
			String TABLE_NAME = "wgbg"; 
			String XP="xp";
			String BZ="bz";
			String BH ="bh";        
			String SJ ="time";    
			String FSZT="fszt";
			// Mail Address
		}
		public interface DFBG {
			String ID = "_id";
			String TABLE_NAME = "dfbg"; 
			
			String  BH ="bh";        
			String  TIME ="time";    
			String  YCQKMC="ycqkmc";     
			String  YZCD ="yzcd";      
			String  YCQKBW ="ycqkbw";    
			String  DLWZ="dlwz";      
			String SSGC ="ssgc";      
			String TBZHFWL ="tbzhfwl";  
			String TBZHFWR ="tbzhfwr";   
			String GCZHFWL="gczhfwl";    
			String GCZHFWR ="gczhfwr";   
			String YCDDDMJGXS="ycdddmjgxs"; 
			String MS  ="ms";       
			String  YYFX  ="yyfx";    
			String JY   ="jy";      
			String X="x";
			String Y="y";
			String QY="qy";
			String HD="hd";
			String XPSM="xdzz";
			String FKYJ="fkyj";
			String FSZT="fszt";
			// Mail Address
		}
		public interface WZBG {
			String TABLE_NAME="xjbg";
		}
		ImageButton button1;
		 ImageButton button2;
		 double zhx=0;
		 double zhy=0;
		 String tbzh="";
		 ImageButton btndw;
		 ImageButton btncxdw;
//		 ImageButton btncd;
//		 ImageButton btnzxj;
		 ProgressDialog  dialog;
		 Callout callout;
		  boolean isf=true;
		  File dataf;
		  File tbzhf;
		  String titletext ;
		  String setip;
		   String setdk;
		   boolean isdw=false;
//         SQLiteDatabase db;
		  ServiceManager serviceManager;
		  public static Context servicecon;
		  ImageView more;
		  
		  private ProgressDialog pBar;
			private String downPath = null;
			private String appName = "tbzh";
			private String appVersion = "version1.json";
			private int newVerCode = 0;
	/** Called when the activity is first created. */
	@SuppressLint("SdCardPath")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(ManageActivity.allActiviy.get("LoginActivity")!=null)
		{
		ManageActivity.ColseActivity("LoginActivity");
		}
		try{
			setContentView(R.layout.main);
			servicecon=GISLocationActivity.this;
//			Intent intent1 =new Intent("org.androidpn.client.NotificationService");
//			Intent intent =new Intent("zjdflocationservice");
//			servicecon.startService(intent1);
//			servicecon.startService(intent);
			serviceManager = new ServiceManager(GISLocationActivity.this);
		        serviceManager.setNotificationIcon(R.drawable.i_demographics);
		        serviceManager.startService();
		mMapView = (MapView) findViewById(R.id.map);
		 button1=(ImageButton)findViewById(R.id.button1);
//	        button1.setImageResource(R.drawable.jian);
	        button1.setVisibility(View.VISIBLE);
	        button1.getBackground().setAlpha(255);
	        button2=(ImageButton)findViewById(R.id.button2);
//	        button2.setImageResource(R.drawable.jia);
	        button2.setVisibility(View.VISIBLE);
	        button2.getBackground().setAlpha(255);
	       btndw= (ImageButton)findViewById(R.id.dw);
	       btncxdw=(ImageButton)findViewById(R.id.cxdw);
	       more=(ImageView)findViewById(R.id.main_footbar_setting);
//	       btncd= (ImageButton)findViewById(R.id.cd);
//	       btnzxj=(ImageButton)findViewById(R.id.zxj);
//	       btncd.setImageResource(android.R.drawable.ic_menu_manage);
//	       btnzxj.setImageResource(android.R.drawable.ic_menu_camera);
//	     
//	       btncd.setImageResource(R.drawable.cd1);
//	       
//	       Resources r = getBaseContext().getResources();  
//			Drawable d = r.getDrawable(android.R.drawable.ic_menu_manage); 
//			d.setAlpha(0);
//			
//			BitmapDrawable bd = (BitmapDrawable) d;
//			bd.setAlpha(0);
//			Bitmap bm = bd.getBitmap();
//		
//			bm.extractAlpha();
//	       BufferedOutputStream bos =null;
//	       String fileName = "/sdcard/xc/menu2.png"; 
//	       bos= new BufferedOutputStream(new FileOutputStream(fileName)); 
//	       bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
	       
//	       btnzxj.setImageResource(R.drawable.btnzx);
	       btndw.setImageResource(R.drawable.btndw);
//	        btndw.setVisibility(View.VISIBLE);
      btncxdw.setImageResource(R.drawable.btncxdw);
//        Resources r = getBaseContext().getResources();
//       Drawable b=r.getDrawable(R.drawable.btncxdw);
//       btncxdw.setBackgroundDrawable(b);
	        btncxdw.setVisibility(View.VISIBLE);
	        ed=(Button)findViewById(R.id.tbzh);
	        RelativeLayout rlMain;  
//	        
//	        rlMain= (RelativeLayout) findViewById(R.id.cm);//布局  
//	        btndw.getBackground().setAlpha(100);
//	        rlMain.getBackground().setAlpha(100);
//	        btncxdw.getBackground().setAlpha(100);
//	        btncd.getBackground().setAlpha(100);
//	        btnzxj.getBackground().setAlpha(100);
//	        ed.getBackground().setAlpha(100);
	        btndw.setBackgroundColor(Color.TRANSPARENT);
	        btncxdw.setBackgroundColor(Color.TRANSPARENT);
//	        btncd.setBackgroundColor(Color.TRANSPARENT);
//	        btnzxj.setBackgroundColor(Color.TRANSPARENT);
//	       ed.setBackgroundColor(Color.TRANSPARENT);
	        //设置背景  
//	        rlMain.setBackgroundColor(Color.TRANSPARENT);  
	        
	        
	        fbactive      = (RadioButton)findViewById(R.id.main_footbar_news);
	        fbNews    = (RadioButton)findViewById(R.id.main_footbar_question);
	        fbQuestion    = (RadioButton)findViewById(R.id.main_footbar_tweet);
	        fbTweet		 = (RadioButton)findViewById(R.id.main_footbar_active);
	    	fbactive.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					fbNews.setChecked(false);
		    		fbQuestion.setChecked(false);
		    		fbTweet.setChecked(false);
		    		fbactive.setChecked(true);
		    		Intent query = new Intent(GISLocationActivity.this,jssbactivity.class);  
				    startActivity(query);
				}
	    		
	    	});
	    	fbNews.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					fbNews.setChecked(true);
		    		fbQuestion.setChecked(false);
		    		fbTweet.setChecked(false);
		    		fbactive.setChecked(false);
		    		Intent query = new Intent(GISLocationActivity.this,sbactivity.class);  
				    startActivity(query);
				}
	    		
	    	});
	    	fbQuestion.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					fbNews.setChecked(false);
		    		fbQuestion.setChecked(true);
		    		fbTweet.setChecked(false);
		    		fbactive.setChecked(false);
		    		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
					 Date date = new Date(System.currentTimeMillis());  
			         SimpleDateFormat dateFormat = new SimpleDateFormat(  
			                 "_yyyyMMdd-HHmmss");
			         String name="xjdf"+dateFormat.format(date) + ".jpg";
			         File file = new File("/mnt/sdcard/珠江巡查相册/");  
			         file.mkdirs();// 创建文件夹  
			         String fileName = "/mnt/sdcard/珠江巡查相册/"+name; 
			         File file1=new File(fileName);
			         Uri u=Uri.fromFile(file1);
			         
			         intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
			          
			         intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
					startActivityForResult(intent,1);  
				}
	    		
	    	});
	    	fbTweet.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					// TODO Auto-generated method stub
					fbNews.setChecked(false);
		    		fbQuestion.setChecked(false);
		    		fbTweet.setChecked(true);
		    		fbactive.setChecked(false);
		    		Intent query = new Intent(GISLocationActivity.this,cdActivity.class);  
				    
//				    Intent query = new Intent(GISLocationActivity.this,CameraPreview.class);  
				    startActivity(query);
		    		
				}
	    		
	    	});
	    	more.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					View v = LayoutInflater.from(GISLocationActivity.this).inflate(R.layout.more, null);
					ImageView cx=(ImageView)v.findViewById(R.id.imageView8);
					ImageView sz=(ImageView)v.findViewById(R.id.imageView1);
					ImageView tc=(ImageView)v.findViewById(R.id.imageView2);
					cx.setImageResource(android.R.drawable.ic_menu_info_details);
					sz.setImageResource(android.R.drawable.ic_menu_set_as);
					tc.setImageResource(android.R.drawable.star_off);
					RelativeLayout cxd=(RelativeLayout)v.findViewById(R.id.LinearLayout1);
					RelativeLayout szd=(RelativeLayout)v.findViewById(R.id.LinearLayout2);
					RelativeLayout tcd=(RelativeLayout)v.findViewById(R.id.LinearLayout3);
					cxd.setOnClickListener(new OnClickListener(){

						public void onClick(View arg0) {
							// TODO Auto-generated method stub
						   	Intent query = new Intent(GISLocationActivity.this,Qureyactivity.class);  
						    startActivity(query); 
						}
						 
					 });
					szd.setOnClickListener(new OnClickListener(){

						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							ServiceManager.viewNotificationSettings(GISLocationActivity.this);
						}
						 
					 });
					tcd.setOnClickListener(new OnClickListener(){

						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							TextView tex=new TextView(GISLocationActivity.this);
						    tex.setText("是否退出本系统!");
						    tex.setTextSize(30);
						    Resources r1 = getBaseContext().getResources();  
							Drawable d1 = r1.getDrawable(R.drawable.eback);
							tex.setBackgroundDrawable(d1);
							tex.setTextColor(R.color.txtcc);
						    tex.setGravity(Gravity.CENTER);
						    	   AlertDialog.Builder builder = new AlertDialog.Builder(GISLocationActivity.this);
						    builder.setTitle("退出").setIcon(

							        android.R.drawable.ic_delete).setView(tex)
						            .setNegativeButton("取消", null);
						    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						        
								public void onClick(DialogInterface dialog, int which) {
//						       GISLocationActivity.this.onBackPressed();
//									dialog.cancel();
//						        	ManageActivity.ColseallActivity();
									System.exit(0);
									android.os.Process.killProcess(android.os.Process.myPid());

						        }
						    });
						    builder.show();
						}
						 
					 });
					 AlertDialog.Builder builder = new AlertDialog.Builder(GISLocationActivity.this);
				        builder.setView(v);
				      
				        builder.show();
				}});
	        dataf = new File("/sdcard/XClocalhost/zjdafh");
	        tbzhf = new File("/sdcard/XClocalhost/tbzh");
	        SQLiteDatabase db1=	SQLiteDatabase.openOrCreateDatabase(dataf, null); 
	        Cursor cc=db1.query(DFBG.TABLE_NAME,null, null,null,null,null,null);
	       if( cc.getColumnCount()<21)
	       {
	    	   String ad1="ALTER TABLE "+DFBG.TABLE_NAME+" ADD qy text";
	    	   String ad2="ALTER TABLE  "+DFBG.TABLE_NAME+" ADD hd text";
	    	   String ad3="ALTER TABLE "+DFBG.TABLE_NAME+" ADD x numeric";
	    	   String ad4="ALTER TABLE  "+DFBG.TABLE_NAME+" ADD y numeric";
	    	   db1.execSQL(ad1);
	    	   db1.execSQL(ad2);
	    	   db1.execSQL(ad3);
	    	   db1.execSQL(ad4);
	    	   
	       }
	       Cursor cc1=db1.query(WZBG.TABLE_NAME,null, null,null,null,null,null);
	       if( cc.getColumnCount()<21)
	       {
	    	   String ad1="ALTER TABLE "+WZBG.TABLE_NAME+" ADD qy text";
	    	   String ad2="ALTER TABLE  "+WZBG.TABLE_NAME+" ADD hd text";
	    	   String ad3="ALTER TABLE "+WZBG.TABLE_NAME+" ADD x numeric";
	    	   String ad4="ALTER TABLE  "+WZBG.TABLE_NAME+" ADD y numeric";
	    	   db1.execSQL(ad1);
	    	   db1.execSQL(ad2);
	    	   db1.execSQL(ad3);
	    	   db1.execSQL(ad4);
	    	   
	       }
	       cc.close();
	       cc1.close();
	        Cursor c1 = db1.query(SETIP.TABLE_NAME,  new String[]{SETIP.IP,SETIP.DK}, null,null,null,null,null);
	 	   
	 	   if(c1.getCount()>0)
	 	   {
	 		  
	 		   c1.moveToFirst();
	 		   setip=c1.getString(0);
	 		   setdk=c1.getString(1);
	 	   }
	 	   c1.close();
	 	   db1.close();
	        downPath="http://"+setip+":"+setdk
					+"/androidserver/";
			try {
	        	if(isNetworkAvailable(this) == false){
	        		
	        	}else{
	        		checkToUpdate();
	        	}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				
			}

	        ed.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final EditText inputServer = new EditText(GISLocationActivity.this);
					inputServer.setText(tbzh);
			        AlertDialog.Builder builder = new AlertDialog.Builder(GISLocationActivity.this);
			        builder.setTitle("请输入统编桩号").setView(inputServer)
			                .setNegativeButton("取消", null);
			        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			            public void onClick(DialogInterface dialog, int which) {
			            	try
			            	{
			            	tbzh=inputServer.getText().toString();
			            String	where =TBZH.ZH+"='"+tbzh+"'";
			            	SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(tbzhf, null); 
			       	     Cursor cur = null;
			       	      cur=db.query(TBZH.TABLE_NAME, new String[]{TBZH.X,TBZH.Y
			       	    		  },where, null, null, null, null);
			       	      if(cur.getCount()>0)
			       	      {cur.moveToFirst();
			       	      String xx=cur.getString(0);
			       	    	 zhx=Double.parseDouble(xx);
			       	    	Log.i("result",zhx+""); 
			       	    	zhy=Double.parseDouble(cur.getString(1));
			       	    	ed.setText(inputServer.getText().toString());
			       	      }
			       	      
			       	      else{
			       	    	 MessageBox.createAlertDialog("提示", "没有此桩号，请重新输入！", "返回", GISLocationActivity.this).show();
			       	      }
			       	   db.close();
			       	      cur.close();
			            	}
			            	catch(Exception e){

			   	          	 e.printStackTrace();
			   	          	 Log.i("result", e.getMessage()); 
			   	         
			   	          	 }
			             }
			        });
			        builder.show();
				}});
	      
	        loc = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//			if(!loc.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//	            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
//	            //返回开启GPS导航设置界面
//	            Intent intent11 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
//	            startActivityForResult(intent11,0); 
//	            return;
//	        }
			 boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER );
			  if(!gpsEnabled){
//				  Settings.Secure.setLocationProviderEnabled( getContentResolver(), LocationManager.GPS_PROVIDER, true);
				  Intent gpsIntent = new Intent(); 
				    gpsIntent.setClassName("com.android.settings", 
				            "com.android.settings.widget.SettingsAppWidgetProvider"); 
				    gpsIntent.addCategory("android.intent.category.ALTERNATIVE"); 
				    gpsIntent.setData(Uri.parse("custom:3")); 
				    try { 
				        PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send(); 
				    } 
				    catch (CanceledException e) { 
//				        e.printStackTrace(); 
				    }
				  }
			final SQLiteDatabase	db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
		
			 Cursor c=db.query(SETIP.TABLE_NAME, new String[]{SETIP.IP,SETIP.DK}, null, null, null, null, null);
		     	c.moveToFirst();
			   setip=c.getString(0);
			  setdk=c.getString(1);
			  
			   c.close();
			ArcGISLocalTiledLayer local;
			local = new ArcGISLocalTiledLayer("file:///mnt/sdcard/XClocalhost/fb/Layers");
			ArcGISLocalTiledLayer zhlocal;
//			zhlocal = new ArcGISLocalTiledLayer("file:///mnt/sdcard/XClocalhost/zh/Layers");
			zhlocal = new ArcGISLocalTiledLayer("file:///mnt/sdcard/XClocalhost/newzh/zh/Layers");
//        String mapurl=ConfigManager.getInstance().getString("Mapurl");
//		final ArcGISDynamicMapServiceLayer dlayer = new ArcGISDynamicMapServiceLayer(
//				mapurl);
		mMapView.setExtent(local.getExtent());
		mMapView.addLayer(local);
		mMapView.addLayer(zhlocal);
		gLayer = new GraphicsLayer();
		gpsLayer = new GraphicsLayer();
		cxdwLayer = new GraphicsLayer();
		ptLayer = new GraphicsLayer();
		pskLayer = new GraphicsLayer();
		gLayer.setName("tbzh");
		gpsLayer.setName("gps");
		mMapView.addLayer(gLayer);
		mMapView.addLayer(gpsLayer);
		mMapView.addLayer(cxdwLayer);
		mMapView.addLayer(ptLayer);
	mMapView.addLayer(pskLayer);
//		SQLiteDatabase	ptdb = SQLiteDatabase.openOrCreateDatabase(tbzhf, null); 
//		 Cursor ptc=ptdb.query("dfpt", new String[]{"tbzh","x","y"}, null, null, null, null, null);
//		 ptc.moveToFirst();
//		 for(int pti=0;pti<ptc.getCount();pti++)
//		 {
//			 Point ptpoint = new Point(Double.parseDouble(ptc.getString(1)),
//    	    			Double.parseDouble(ptc.getString(2)));
//    	    	
//    	    	Map<String, Object> map =new HashMap<String,  Object>();
//    	    	map.put("tbzh", ptc.getString(0));
//    	    	Graphic ptg = new Graphic(ptpoint,
//						null, map,null);
//    	    	ptLayer.addGraphic(ptg);
//    	    	ptc.moveToNext();
//		 }
//		ptc.close();
//		ptdb.close();
		mMapView.setOnFocusChangeListener(new OnFocusChangeListener(){

			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}});
		mMapView.setOnPanListener(new OnPanListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void postPointerMove(float fromx, float fromy, float tox,
					float toy) {
				// TODO Auto-generated method stub
				
			}

			public void postPointerUp(float fromx, float fromy, float tox,
					float toy) {
				double scale=mMapView.getScale();
				ptLayer.removeAll();
				pskLayer.removeAll();
				if(scale<20000)
				{
					try{
					
					 Geometry geo =mMapView.getExtent().copy();
					 Envelope env = new Envelope();
                     geo.queryEnvelope(env);
					String where=TBZH.X+">'"+env.getXMin()+
							"' and "+TBZH.X+"<'"+env.getXMax()+
							"' and "+TBZH.Y+">'"+env.getYMin()+
							"' and "+TBZH.Y+"<'"+env.getYMax()+"'";
					SQLiteDatabase	ptdb = SQLiteDatabase.openOrCreateDatabase(tbzhf, null); 
					 Cursor ptc=ptdb.query("dfpt", new String[]{"tbzh","x","y"}, where, null, null, null, null);
					 ptc.moveToFirst();
					 for(int pti=0;pti<ptc.getCount();pti++)
					 {
						 Point ptpoint = new Point(Double.parseDouble(ptc.getString(1)),
			    	    			Double.parseDouble(ptc.getString(2)));
			    	    	
			    	    	Map<String, Object> map =new HashMap<String,  Object>();
			    	    	map.put("tbzh", ptc.getString(0));
			    	    	Resources r = getBaseContext().getResources();  
							Drawable d = r.getDrawable(R.drawable.pt1);
						
//							d.setAlpha(0);
							
							PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
							
			    	    	Graphic ptg = new Graphic(ptpoint,
			    	    			picture, map,null);
			    	    	ptLayer.addGraphic(ptg);
			    	    	ptc.moveToNext();
					 }
//					 String where1=TBZH.Y+">'"+env.getXMin()+
//								"' and "+TBZH.Y+"<'"+env.getXMax()+
//								"' and "+TBZH.X+">'"+env.getYMin()+
//								"' and "+TBZH.X+"<'"+env.getYMax()+"'";
//					 Cursor psk=ptdb.query("pt", new String[]{"tbzh","x","y"}, where1, null, null, null, null);
//					 psk.moveToFirst();
//					 for(int pti=0;pti<psk.getCount();pti++)
//					 {
//						 Point ptpoint = new Point(Double.parseDouble(psk.getString(2)),
//			    	    			Double.parseDouble(psk.getString(1)));
//			    	    	
//			    	    	Map<String, Object> map =new HashMap<String,  Object>();
//			    	    	map.put("tbzh", psk.getString(0));
//			    	    	Resources r = getBaseContext().getResources();  
//							Drawable d = r.getDrawable(R.drawable.pskt);
//						
////							d.setAlpha(0);
//							
//							PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
//							
//			    	    	Graphic ptg = new Graphic(ptpoint,
//			    	    			picture, map,null);
//			    	    	pskLayer.addGraphic(ptg);
//			    	    	psk.moveToNext();
//					 }
//					 psk.close();
					ptc.close();
					ptdb.close();
		       	    
				}catch (Exception e) {
					// TODO Auto-generated catch block
					 Log.i("result", e.getMessage()); 
						e.printStackTrace();
					} 
				}
				// TODO Auto-generated method stub
				
			}

			public void prePointerMove(float fromx, float fromy, float tox,
					float toy) {
				// TODO Auto-generated method stub
				
			}

			public void prePointerUp(float fromx, float fromy, float tox,
					float toy) {
				// TODO Auto-generated method stub
				
			}});
		mMapView.setOnZoomListener( new OnZoomListener(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void postAction(float pivotX, float pivotY, double factor) {
				double scale=mMapView.getScale();
				ptLayer.removeAll();
				pskLayer.removeAll();
				if(scale<20000)
				{
					try{
					
					 Geometry geo =mMapView.getExtent().copy();
					 Envelope env = new Envelope();
                     geo.queryEnvelope(env);
					String where=TBZH.X+">'"+env.getXMin()+
							"' and "+TBZH.X+"<'"+env.getXMax()+
							"' and "+TBZH.Y+">'"+env.getYMin()+
							"' and "+TBZH.Y+"<'"+env.getYMax()+"'";
					SQLiteDatabase	ptdb = SQLiteDatabase.openOrCreateDatabase(tbzhf, null); 
					 Cursor ptc=ptdb.query("dfpt", new String[]{"tbzh","x","y"}, where, null, null, null, null);
					 ptc.moveToFirst();
					 for(int pti=0;pti<ptc.getCount();pti++)
					 {
						 Point ptpoint = new Point(Double.parseDouble(ptc.getString(1)),
			    	    			Double.parseDouble(ptc.getString(2)));
			    	    	
			    	    	Map<String, Object> map =new HashMap<String,  Object>();
			    	    	map.put("tbzh", ptc.getString(0));
			    	    	Resources r = getBaseContext().getResources();  
							Drawable d = r.getDrawable(R.drawable.pt1);
//							d.setAlpha(0);
							
							PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
			    	    	Graphic ptg = new Graphic(ptpoint,
			    	    			picture, map,null);
			    	    	ptLayer.addGraphic(ptg);
			    	    	ptc.moveToNext();
					 }
//					 String where1=TBZH.Y+">'"+env.getXMin()+
//								"' and "+TBZH.Y+"<'"+env.getXMax()+
//								"' and "+TBZH.X+">'"+env.getYMin()+
//								"' and "+TBZH.X+"<'"+env.getYMax()+"'";
//					 Cursor psk=ptdb.query("pt", new String[]{"tbzh","x","y"}, where1, null, null, null, null);
//					 psk.moveToFirst();
//					 for(int pti=0;pti<psk.getCount();pti++)
//					 {
//						 Point ptpoint = new Point(Double.parseDouble(psk.getString(2)),
//			    	    			Double.parseDouble(psk.getString(1)));
//			    	    	
//			    	    	Map<String, Object> map =new HashMap<String,  Object>();
//			    	    	map.put("tbzh", psk.getString(0));
//			    	    	Resources r = getBaseContext().getResources();  
//							Drawable d = r.getDrawable(R.drawable.pskt);
//						
////							d.setAlpha(0);
//							
//							PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
//							
//			    	    	Graphic ptg = new Graphic(ptpoint,
//			    	    			picture, map,null);
//			    	    	pskLayer.addGraphic(ptg);
//			    	    	psk.moveToNext();
//					 }
//					 psk.close();
					ptc.close();
					ptdb.close();
		       	    
				}catch (Exception e) {
					// TODO Auto-generated catch block
					 Log.i("result", e.getMessage()); 
						e.printStackTrace();
					} 
				}
			}

			public void preAction(float pivotX, float pivotY, double factor) {
				// TODO Auto-generated method stub
				
			}});
		mMapView.setOnSingleTapListener(new OnSingleTapListener(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int m_Char = 65;
			public void onSingleTap(float x, float y) {
				try
				{
				// TODO Auto-generated method stub
				 if (!mMapView.isLoaded()) {
		                return;
		            }
				 if (ptLayer != null && ptLayer.isInitialized() && ptLayer.isVisible()) {
		                Graphic result = null;
		              int tc=1;
		                // 检索当前 光标点（手指按压位置）的附近的 graphic对象
		                result = GetGraphicsFromLayer(x, y, ptLayer);
		                if(result==null)
		                {
		                result=GetGraphicsFromLayer(x, y, cxdwLayer);
		                tc=2;}
		                if(result==null)
		                {
		                result=GetGraphicsFromLayer(x, y, pskLayer);
		                tc=3;}
		                if (result != null) {
		                    // 获得附加特别的属性
		                    String msgTag = (String) result
		                            .getAttributeValue("tbzh");
		                    callout=mMapView.getCallout();
		                     
		                    if(msgTag.equals(titletext))
		                    {
		                    	callout.hide();
		                    }
		                    else
		                    {
		                    Point pointVar = (Point) result.getGeometry();
		                    // 显示提示
		                    int TITLE_ID = 1;
		                  
	                       if(tc==3)
	                       {
	                    	   View  v = LayoutInflater.from(GISLocationActivity.this).inflate(R.layout.pskcallout, null);
			    				 TextView	tbzh =(TextView)v.findViewById(R.id.tbzh);
			    				 TextView	wz =(TextView)v.findViewById(R.id.wz);
			    				 TextView	hd =(TextView)v.findViewById(R.id.hd);
			    				 TextView	gj =(TextView)v.findViewById(R.id.gj);
			    				 TextView	ddjl =(TextView)v.findViewById(R.id.ddjl);
			    				 TextView	bz =(TextView)v.findViewById(R.id.bz);
			    				 SQLiteDatabase	ptdb = SQLiteDatabase.openOrCreateDatabase(tbzhf, null);
			    				 String where="tbzh='"+msgTag+"'";
			    				 Cursor psk=ptdb.query("psk", new String[]{"wz","hd","gj","ddjl","bz"}, where, null, null, null, null);
		    	                 if(psk.getCount()>0)
		    	                 {
		    	                	 psk.moveToFirst();
		    	                	 tbzh.setText(msgTag);
		    	                	 wz.setText(psk.getString(0));
		    	                	 hd.setText(psk.getString(1));
		    	                	 gj.setText(psk.getString(2));
		    	                	 ddjl.setText(psk.getDouble(3)+"");
		    	                	 bz.setText(psk.getString(4));
		    	                	 
		    	                 }
		    	                 titletext=msgTag;
		    	                 ptdb.close();
		    	                 psk.close();
		    	                 callout.setMaxHeight(280);
				    				callout.setMaxWidth(240);
		    	                 callout.show(pointVar,v);
	                       }
	                       else
	                       {
		    				 
	                    	   View  v = LayoutInflater.from(GISLocationActivity.this).inflate(R.layout.callout, null);
		    				 TextView	titleView =(TextView)v.findViewById(R.id.countyname);
		    				titleView.setId(TITLE_ID);

		    				// titleView.setText(title);
		    				titleView.setTextColor(Color.RED);
		    				titleView.setTextSize(15);
		    				titleView.setText(msgTag);
		    				titletext=msgTag;
//		    			
		    				callout.setMaxHeight(45);
		    				callout.setMaxWidth(120);
		    				callout.show(pointVar,v);
	                       }
		    				
		    			
		    				
		                    }

		                }// end if
		            }// end if
				}
				 catch (Exception e) {
						// TODO Auto-generated catch block
						 Log.i("result", e.getMessage()); 
//							e.printStackTrace();
						} 
		           
			}
				});
		

		db.close();
//		cur.close();
		

		mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void onStatusChanged(Object source, STATUS status) {
				// TODO Auto-generated method stub
				  if (status.equals(STATUS.LAYER_LOADED)) {
					  try{
					  Location	GLocation=loc.getLastKnownLocation(LocationManager.GPS_PROVIDER );
					  if(GLocation==null) return;
						Point ptLatLon = new Point(GLocation.getLongitude(),
								GLocation.getLatitude());
						// 经纬度坐标
						SpatialReference sr4326 = SpatialReference.create(4326);
						// 坐标转换
						Point centerPt = (Point) GeometryEngine.project(
								ptLatLon, sr4326,
								mMapView.getSpatialReference());
						
						gpsLayer.removeAll();
//						gLayer.addGraphic(g);
						mMapView.zoomToScale(centerPt, 10000);
					  } catch (Exception e) {
							// TODO Auto-generated catch block
							 Log.i("result", e.getMessage()); 
								e.printStackTrace();
							} 
	                }
			}
			
		});



		button2.setOnClickListener(new
				OnClickListener() {
			public
			void
			onClick(View v) {
				GISLocationActivity.this.mMapView.zoomin();
			}
			});
		button1.setOnClickListener(new
				OnClickListener() {
			public
			void
			onClick(View v) {
				GISLocationActivity.this.mMapView.zoomout();
			}
			});


		btndw.setOnClickListener(new
				OnClickListener() {
			@SuppressWarnings("unchecked")
			public
			void
			onClick(View v) {
				try{
					isdw=true;
				 GpsTask gpstask = new GpsTask(GISLocationActivity.this,
		                    new GpsTaskCallBack() {

		                        public void gpsConnectedTimeOut() {
		                        	try{
		                        	Location GLocation=loc.getLastKnownLocation(LocationManager.NETWORK_PROVIDER );
		        					Point ptLatLon = new Point(GLocation.getLongitude(),
		        							GLocation.getLatitude());
		        					// 经纬度坐标
		        					SpatialReference sr4326 = SpatialReference.create(4326);
		        					// 坐标转换
		        					SpatialReference sr2383 = SpatialReference.create(2383);
		        					Point centerPt = (Point) GeometryEngine.project(
		        							ptLatLon, sr4326,
		        							sr2383);
		        					Point pt=new Point(centerPt.getX()-116.647,centerPt.getY()-11.425);
		        					Resources r = getBaseContext().getResources();  
		        					Drawable d = r.getDrawable(R.drawable.dwjt);
		        	                 PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
		        					
		        					Graphic g = new Graphic(pt,
		        							picture);
		        					gpsLayer.removeAll();
		        					gpsLayer.addGraphic(g);
		        					mMapView.zoomToScale(pt, 3500);
		        					Log.i("result","NETWORK_PROVIDER" ); 
		                        	}catch (Exception e) {
//		            					// TODO Auto-generated catch block
		                        		Toast.makeText(GISLocationActivity.this, "请等待GPS得到位置信息！", Toast.LENGTH_SHORT).show();
		            				} 
		                        }

		                        public void gpsConnected(GpsData gpsdata) {
		                        	try
		                        	{
		                        	Point ptLatLon = new Point(gpsdata.getLongitude(),
		                        			gpsdata.getLatitude());
		        					// 经纬度坐标
		        					SpatialReference sr4326 = SpatialReference.create(4326);
		        					// 坐标转换
		        					SpatialReference sr2383 = SpatialReference.create(2383);
		        					Point centerPt = (Point) GeometryEngine.project(
		        							ptLatLon, sr4326,
		        							sr2383);
		        					Point pt=new Point(centerPt.getX()-116.647,centerPt.getY()-11.425);
		        					Resources r = getBaseContext().getResources();  
		        					Drawable d = r.getDrawable(R.drawable.dwjt);
		        	                 PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
		        					
		        					Graphic g = new Graphic(pt,
		        							picture);
		        					gpsLayer.removeAll();
		        					gpsLayer.addGraphic(g);
		        					mMapView.zoomToScale(pt, 3500);
		        					Log.i("result","GPS" ); 
		                        	}catch (Exception e) {
//		            					// TODO Auto-generated catch block
//		                        		Toast.makeText(GISLocationActivity.this, "请等待GPS得到位置信息！", Toast.LENGTH_SHORT).show();
		            				} 
		                        }

		                    }, 3000);
		            gpstask.execute();
				}catch (Exception e) {
//					// TODO Auto-generated catch block
					Toast.makeText(GISLocationActivity.this, "请等待GPS得到位置信息！", Toast.LENGTH_SHORT).show();
				} 
//				Location	GLocation=loc.getLastKnownLocation(LocationManager.GPS_PROVIDER );
//				if(GLocation==null)
//					{
//					GLocation=loc.getLastKnownLocation(LocationManager.NETWORK_PROVIDER );
//					Point ptLatLon = new Point(GLocation.getLongitude(),
//							GLocation.getLatitude());
//					// 经纬度坐标
//					SpatialReference sr4326 = SpatialReference.create(4326);
//					// 坐标转换
//					Point centerPt = (Point) GeometryEngine.project(
//							ptLatLon, sr4326,
//							mMapView.getSpatialReference());
//					Resources r = getBaseContext().getResources();  
//					Drawable d = r.getDrawable(R.drawable.dwjt);
//	                 PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
//					
//					Graphic g = new Graphic(centerPt,
//							picture);
//					gpsLayer.removeAll();
//					gpsLayer.addGraphic(g);
//					mMapView.zoomToScale(centerPt, 11550);
//					
//					return;}
//				Point ptLatLon = new Point(GLocation.getLongitude(),
//						GLocation.getLatitude());
//				// 经纬度坐标
//				SpatialReference sr4326 = SpatialReference.create(4326);
//				// 坐标转换
//				Point centerPt = (Point) GeometryEngine.project(
//						ptLatLon, sr4326,
//						mMapView.getSpatialReference());
//				Resources r = getBaseContext().getResources();  
//				Drawable d = r.getDrawable(R.drawable.dwjt);
//                 PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
//				
//				Graphic g = new Graphic(centerPt,
//						picture);
//				gpsLayer.removeAll();
//				gpsLayer.addGraphic(g);
//				mMapView.zoomToScale(centerPt, 11550);
//				
//				Log.i("result", centerPt.getX()+"--"+centerPt.getY()+"--"+mMapView.getScale()+mMapView.getSpatialReference().getID()); 
					
				
				
			}
			});
		loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, 0,
				new LocationListener() {

					public void onLocationChanged(Location arg0) {
						// TODO Auto-generated method stub
						if(isdw)
						{
						Point ptLatLon = new Point(arg0.getLongitude(),
								arg0.getLatitude());
						// 经纬度坐标
						SpatialReference sr4326 = SpatialReference.create(4326);
						// 坐标转换
						SpatialReference sr2383 = SpatialReference.create(2383);
						Point centerPt = (Point) GeometryEngine.project(
								ptLatLon, sr4326,
								sr2383);
						Point pt=new Point(centerPt.getX()-116.647,centerPt.getY()-11.425);
						Resources r = getBaseContext().getResources();  
						Drawable d = r.getDrawable(R.drawable.dwjt);
		                 PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
						
						Graphic g = new Graphic(pt,
								picture);
						gpsLayer.removeAll();
						gpsLayer.addGraphic(g);
						}
					}

					public void onProviderDisabled(String arg0) {
						// TODO Auto-generated method stub
						
					}

					public void onProviderEnabled(String arg0) {
						// TODO Auto-generated method stub
						
					}

					public void onStatusChanged(String arg0, int arg1,
							Bundle arg2) {
						// TODO Auto-generated method stub
						
					}});

		btncxdw.setOnClickListener(new
				OnClickListener() {
			public
			void
			onClick(View v) {
				if(zhx==0)
				{
					 MessageBox.createAlertDialog("提示", "请输入统编桩号！", "返回", GISLocationActivity.this).show();
				}
				else
				{
					try
					{Point ptLatLon = new Point(zhx,
						zhy);
				Resources r = getBaseContext().getResources();  
				Drawable d = r.getDrawable(R.drawable.tbzhtb);
				d.setAlpha(0);
				
				PictureMarkerSymbol picture=new PictureMarkerSymbol(d);
				Map<String, Object> map1 =new HashMap<String,  Object>();
       	    	map1.put("tbzh", tbzh);
				Graphic g = new Graphic(ptLatLon,
						picture,map1,null);
				gLayer.removeAll();
				cxdwLayer.removeAll();
				cxdwLayer.addGraphic(g);

				mMapView.zoomToScale(ptLatLon, 2500);
					 int TITLE_ID = 1;
				
				 callout=mMapView.getCallout();
//				
				// create TextView for the title
				View v1 = LayoutInflater.from(GISLocationActivity.this).inflate(R.layout.callout, null);
//				View vv=v1.findViewById(R.id.rel);
				TextView	titleView =(TextView)v1.findViewById(R.id.countyname);
//				TextView titleView = new TextView(GISLocationActivity.this);
				titleView.setId(TITLE_ID);

				// titleView.setText(title);
				titleView.setTextColor(Color.RED);
				titleView.setTextSize(15);
				titleView.setText(tbzh);
				
				titletext=tbzh;
//				
				callout.setMaxHeight(45);
				callout.setMaxWidth(120);
				callout.show(ptLatLon,v1);
				
				 
				  
		       	   
						
				} catch (Exception e) {
//					// TODO Auto-generated catch block
					
				} 

				}
			}
			});
//		 onc=new OnClickListener(){
//
//			@SuppressWarnings("unchecked")
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				// TODO Auto-generated method stub
//				 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(tbzhf, null); 
//				String  where1=TBZH.ZH+"='"+titletext+"'";
//				 Cursor c=db.query(TBZH.TABLE_NAME, new String[]{TBZH.ISDM}, where1, null, null, null, null);
//				// create TextView for the title
//				 int  isdm=0;
//				if(c.getCount()>0)
//				{
//					c.moveToFirst();
//				  isdm=c.getInt(0);
//				}
//				db.close();
//				c.close();
//				if(isdm==0)
//				{
//					 MessageBox.createAlertDialog("提示", "此桩号没有横断面！", "返回", GISLocationActivity.this).show();
//				}
//				else
//				{
//				HttpClient httpclient = new DefaultHttpClient();
//				
//				  String url="http://"+setip+":"+setdk+"/androidserver/servlet/tbzhServlet";
//	              HttpPost httppost = new HttpPost(url);
////	              httppost.addHeader("Content-Type","text/html");
////	              httppost.addHeader("charset",HTTP.UTF_8);
//				 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//		    String 	 titletext1=changeToUnicode(titletext);
//				 nameValuePairs.add(new BasicNameValuePair("tbzh", titletext1)); 			
//				 HttpResponse response; 
//				 try {
//					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//				
//					response=httpclient.execute(httppost); 
//					if (response.getStatusLine().getStatusCode() == 200) {  
//		                // 获取返回的数据  
////		                String result = EntityUtils.toString(response.getEntity(), "UTF-8");  
//		                HttpEntity resEntity = response.getEntity();
//		        		if (resEntity != null) {
////		        			        System.out.println(EntityUtils.toString(resEntity,"utf-8"));
//		        			  StringEntity my_entity=new StringEntity(EntityUtils.toString(resEntity), "utf-8");
//		        			   String result=EntityUtils.toString(my_entity); 
//		        			   JSONArray jsonArr = new JSONObject(result).getJSONArray("values");
//		        			   JSONObject jsonObj=(JSONObject)jsonArr.get(0);
//		        			   ScrollView sc=new ScrollView(GISLocationActivity.this);
//		        			 
//		        			  TableLayout tlayout = new TableLayout(GISLocationActivity.this);
////		        			  tlayout.setColumnShrinkable(3, false);
////		        			  tlayout.setScrollContainer(true);
//		        			  Resources r1 = getBaseContext().getResources();  
//								Drawable d1 = r1.getDrawable(R.drawable.eback);
//		        			  tlayout.setBackgroundDrawable(d1);
//							Iterator<String> iterator=jsonObj.keys();
//							while (iterator.hasNext())
//							{
//								String column=iterator.next();
//								 TableRow tr1 =loadtable(column,jsonObj.get(column).toString());
//								 tlayout.addView(tr1,new TableLayout.LayoutParams(
//			        	                    LayoutParams.FILL_PARENT, 
//			        	                    LayoutParams.WRAP_CONTENT));
//							}
//		        			  sc.addView(tlayout);
//		        			  AlertDialog.Builder builder = new AlertDialog.Builder(GISLocationActivity.this);
//		      		        builder.setTitle("详细属性").setIcon(null).setView(sc)
//		      		                .setNegativeButton("返回", null);
//		      		      builder.show();
//		        		}
//
//		            } else {  
//		                Log.i("HttpPost", "HttpPost方式请求失败");  
//		            }  
//
//				}  catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} 
//				}
//			}};

			

		      
			
		}
		 catch (Exception e) {
				// TODO Auto-generated catch block
			 Log.i("result", e.getMessage()); 
				e.printStackTrace();
			} 
		if(ManageActivity.allActiviy.get("GISLocationActivity")==null)
		{
		ManageActivity.addActiviy("GISLocationActivity", GISLocationActivity.this);
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
	 private void checkToUpdate() throws NameNotFoundException {
			// TODO Auto-generated method stub
			if(getServerVersion()){
				if(!tbzhf.exists())
				{
					showProgressBar();
				}
				else
				{
				  SQLiteDatabase db1=	SQLiteDatabase.openOrCreateDatabase(tbzhf, null); 
			        Cursor c1 = db1.query("ver",  new String[]{"vercode"}, null,null,null,null,null);
			 	   c1.moveToFirst();
				int currentCode = c1.getInt(0);
				db1.close();
				c1.close();
				if(newVerCode > currentCode)
				{//Current Version is old
					//寮瑰嚭鏇存柊鎻愮ず瀵硅瘽妗�	
					
					showProgressBar();
				}
				}
				
			}
		}
//	
		protected void showProgressBar() {
			// TODO Auto-generated method stub
			pBar = new ProgressDialog(GISLocationActivity.this);
			pBar.setTitle("正在更新桩号数据");
			pBar.setMessage("请稍后！");
			pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pBar.setProgress(0);
			pBar.setCanceledOnTouchOutside(false);
			downAppFile(downPath + appName);
		}
		//Get ServerVersion from GetUpdateInfo.getUpdateVerJSON
		private boolean getServerVersion() {
			// TODO Auto-generated method stub
			try{
				String newVerJSON = GetUpdateInfo.getUpdataVerJSON(downPath + appVersion);
				JSONArray jsonArray = new JSONArray(newVerJSON);
				if(jsonArray.length() > 0){
					JSONObject obj = jsonArray.getJSONObject(0);
					try{
						newVerCode = Integer.parseInt(obj.getString("verCode"));
				
					}catch(Exception e){
						Log.e("TAG", e.getMessage());
						newVerCode = -1;
						
						return false;
					}
				}
			}catch(Exception e){
				Log.e("TAG", e.getMessage());
				return false;
			}
			return true;
		}
		protected void downAppFile(final String url) {
			pBar.show();
			new Thread(){
				public void run(){
					HttpClient client = new DefaultHttpClient();
					HttpGet get = new HttpGet(url);
					HttpResponse response;
					try {
						response = client.execute(get);
						HttpEntity entity = response.getEntity();
						long length = entity.getContentLength();
						Log.isLoggable("DownTag", (int) length);
						InputStream is = entity.getContent();
						FileOutputStream fileOutputStream = null;
						if(is == null){
							throw new RuntimeException("isStream is null");
						}
						File file = new File("/sdcard/XClocalhost/tbzh");
						if(file.exists())
						{
							file.delete();
						}
//						int bytesAvailable = is.available();
						 int ii=(int) (length/1024);
						fileOutputStream = new FileOutputStream(file);
						
						byte[] buf = new byte[1024];
						int ch = -1;
						int j=0;
						int ij=0;
						do{
							j++;
							ij=j*100/ii;
							pBar.setProgress(ij);
							ch = is.read(buf);
							if(ch <= 0)break;
							fileOutputStream.write(buf, 0, ch);
						}while(true);
						is.close();
						fileOutputStream.close();
						pBar.setProgress(100);
					   pBar.cancel();
						}catch(ClientProtocolException e){
							e.printStackTrace();
							}catch(IOException e){
							e.printStackTrace();
							}
					}
			}.start();
		}
	 private static final int TWO_MINUTES = 1000 * 1 * 2;
	protected boolean isBetterLocation(Location location,
    		Location currentBestLocation)
    		{
    		if (currentBestLocation == null)
    		{
    		// A new location is always better than no location
    		return true;
    		}


    		// Check whether the new location fix is newer or older
    		long timeDelta = location.getTime() - currentBestLocation.getTime();
    		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
    		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
    		boolean isNewer = timeDelta > 0;


    		// If it's been more than two minutes since the current location, use
    		// the new location
    		// because the user has likely moved
    		if (isSignificantlyNewer)
    		{
    		return true;
    		// If the new location is more than two minutes older, it must be
    		// worse
    		}
    		else if (isSignificantlyOlder)
    		{
    		return false;
    		}


    		// Check whether the new location fix is more or less accurate
    		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
    		.getAccuracy());
    		boolean isLessAccurate = accuracyDelta > 0;
    		boolean isMoreAccurate = accuracyDelta < 0;
    		boolean isSignificantlyLessAccurate = accuracyDelta > 200;


    		// Check if the old and new location are from the same provider
    		boolean isFromSameProvider = isSameProvider(location.getProvider(),
    		currentBestLocation.getProvider());


    		// Determine location quality using a combination of timeliness and
    		// accuracy
    		if (isMoreAccurate)
    		{
    		return true;
    		}
    		else if (isNewer && !isLessAccurate)
    		{
    		return true;
    		}
    		else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
    		{
    		return true;
    		}
    		return false;
    		}
	 private boolean isSameProvider(String provider1, String provider2)
	    {
	    if (provider1 == null)
	    {
	    return provider2 == null;
	    }
	    return provider1.equals(provider2);
	    }
	 @SuppressLint("SdCardPath")
	@Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
		 
		 super.onActivityResult(requestCode, resultCode, data); 
	        
         String sdStatus = Environment.getExternalStorageState();  
         if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
             Log.i("TestFile",  
                     "SD card is not avaiable/writeable right now.");  
             return;  
         } 
        
         	if (resultCode != Activity.RESULT_OK)  
	            return;  
//         Date date = new Date(System.currentTimeMillis());  
//         SimpleDateFormat dateFormat = new SimpleDateFormat(  
//                 "_yyyyMMdd-HHmm");
//         String name="xc"+dateFormat.format(date) + ".jpg";
//         //	            Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
//
//        
//         Bundle bundle = data.getExtras();
//			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//			BufferedOutputStream bos =null;
//			
//       
//         File file = new File("/mnt/sdcard/珠江巡查相册/");  
//         file.mkdirs();// 创建文件夹  
//         String fileName = "/mnt/sdcard/珠江巡查相册/"+name; 
////         File file1 = new File(fileName); 
////         file1.mkdir();
//         try {  
//         	bos= new BufferedOutputStream(new FileOutputStream(fileName)); 
//				//b = new FileOutputStream(fileName);
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);// 把数据写入文件
//
//         }catch(Exception e){
//
//        	 e.printStackTrace();
//        	 Log.i("result", e.getMessage()); 
//
//        	 }
	 }
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        /*
	         * 
	         * add()方法的四个参数，依次是：
	         * 
	         * 1、组别，如果不分组的话就写Menu.NONE,
	         * 
	         * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
	         * 
	         * 3、顺序，那个菜单现在在前面由这个参数的大小决定
	         * 
	         * 4、文本，菜单的显示文本
	         */

//	        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "菜单").setIcon(
//
//	        android.R.drawable.ic_menu_manage);
//
//
//
//	        menu.add(Menu.NONE, Menu.FIRST + 3, 3, "违章上报").setIcon(
//
//	        android.R.drawable.ic_menu_add);
//
//	        menu.add(Menu.NONE, Menu.FIRST + 2, 2, "巡查上报").setIcon(
//
//	        android.R.drawable.ic_menu_add);
//
//	        menu.add(Menu.NONE, Menu.FIRST + 4, 4, "历史查询").setIcon(
//
//	        android.R.drawable.ic_menu_info_details);
//
//	        menu.add(Menu.NONE, Menu.FIRST + 5,5, "系统设置").setIcon(
//
//	        android.R.drawable.ic_menu_set_as);
//	        menu.add(Menu.NONE, Menu.FIRST + 6,6, "退出系统").setIcon(
//
//	    	        android.R.drawable.star_off);

	        return true;

	    }

	  
		
		@Override
	    public boolean onOptionsItemSelected(MenuItem item) {
//	        switch (item.getItemId()) {
//
//	        case Menu.FIRST + 1:
//
//	           Intent intentwz =new Intent(GISLocationActivity.this,cdActivity.class);
//			     startActivity(intentwz);
//
//	            break;
//
//	        case Menu.FIRST + 2:
//	        	Intent intent =new Intent(GISLocationActivity.this,jssbactivity.class);
//			     startActivity(intent);
////	            Toast.makeText(this, "保存菜单被点击了", Toast.LENGTH_LONG).show();
//
//	            break;
//
//	        case Menu.FIRST + 3:
//
//	        	Intent intent1 =new Intent(GISLocationActivity.this,sbactivity.class);
//		     startActivity(intent1);
//	            break;
//
//	        case Menu.FIRST + 4:
//
//	        	Intent query = new Intent(GISLocationActivity.this,Qureyactivity.class);  
//			    startActivity(query); 
//
//	            break;
//
//	        case Menu.FIRST + 5:
//
//	        	ServiceManager.viewNotificationSettings(GISLocationActivity.this);
//
//	            break;
//
//	        case Menu.FIRST + 6:
//	        	TextView tex=new TextView(GISLocationActivity.this);
//	        tex.setText("是否退出本系统!");
//	        tex.setTextSize(30);
//	        Resources r1 = getBaseContext().getResources();  
//	    	Drawable d1 = r1.getDrawable(R.drawable.eback);
//	    	tex.setBackgroundDrawable(d1);
//	        
//	        tex.setTextColor(R.color.txtcc);
//	        tex.setGravity(Gravity.CENTER);
//	        	   AlertDialog.Builder builder = new AlertDialog.Builder(GISLocationActivity.this);
//	        builder.setTitle("退出").setIcon(
//
//	    	        android.R.drawable.ic_delete).setView(tex)
//	                .setNegativeButton("取消", null);
//	        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//	            
//				public void onClick(DialogInterface dialog, int which) {
////					GISLocationActivity.this.onBackPressed();
//	            	ManageActivity.ColseallActivity();
//	            }
//	        });
//	        builder.show();
//	        
//
//	            break;
//
//	        }

	        return false;

	    }

	    @Override
	    public void onOptionsMenuClosed(Menu menu) {
//	        Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();
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
	    public boolean onPrepareOptionsMenu(Menu menu) {
//	        Toast.makeText(this,
//	                "选项菜单显示之前onPrepareOptionsMenu方法会被调用，你可以用此方法来根据打当时的情况调整菜单",
//	                Toast.LENGTH_LONG).show();

	        // 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用

	        return true;

	    }
	    Button btngettzbh;
		 TextView tex;
//	 public View   loadView(String bh)
//	 {
//		 View v= LayoutInflater.from(GISLocationActivity.this).inflate(R.layout.callout, null);
//		 btngettzbh=(Button)findViewById(R.id.gettzbh);
//		 TextView tex=(TextView)findViewById(R.id.countyname);
//		 tex.setText(bh);
//		return v;
//		 
//	 }
	 private Graphic GetGraphicsFromLayer(double xScreen, double yScreen,
	            GraphicsLayer layer) {
	        Graphic result = null;
	        try {
	            int[] idsArr = layer.getGraphicIDs();
	            double x = xScreen;
	            double y = yScreen;
	            for (int i = 0; i < idsArr.length; i++) {
	                Graphic gpVar = layer.getGraphic(idsArr[i]);
	                if (gpVar != null) {
	                    Point pointVar = (Point) gpVar.getGeometry();
	                    pointVar = mMapView.toScreenPoint(pointVar);
	                    double x1 = pointVar.getX();
	                    double y1 = pointVar.getY();
	                    if (Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1)) <= 25) {
	                        result = gpVar;
	                        break;
	                    }
	                }
	            }
	        } catch (Exception e) {
	            return null;
	        }
	        return result;
	    }
	 
	
	 public TableRow   loadtable(String zd,String vuale)
	 {
		 if(vuale.equals("null")||vuale.equals(null))
	 {
		 vuale=" ";
	 }
		 TableRow tr = new TableRow(GISLocationActivity.this);
         tr.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT, 
                        LayoutParams.WRAP_CONTENT));
         TextView tex1=new TextView(GISLocationActivity.this);
         TextView tex2=new TextView(GISLocationActivity.this);
         tex1.setText(zd);
        
         tex2.setText(vuale);
         tex1.setTextSize(15);
         tex2.setTextSize(15);
         tex1.setTextColor(Color.BLACK);
         tex2.setTextColor(Color.BLACK);
         tr.addView(tex1);
         tr.addView(tex2);
		return tr;
		 
	 }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		isruning=false;
		LocationThread.stop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.pause();
	}
	

	@Override
	public void onBackPressed() {
//	super.onBackPressed();
	TextView tex=new TextView(GISLocationActivity.this);
    tex.setText("是否退出本系统!");
    tex.setTextSize(30);
    Resources r1 = getBaseContext().getResources();  
	Drawable d1 = r1.getDrawable(R.drawable.eback);
	tex.setBackgroundDrawable(d1);
	tex.setTextColor(R.color.txtcc);
    tex.setGravity(Gravity.CENTER);
    	   AlertDialog.Builder builder = new AlertDialog.Builder(GISLocationActivity.this);
    builder.setTitle("退出").setIcon(

	        android.R.drawable.ic_delete).setView(tex)
            .setNegativeButton("取消", null);
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

        
		public void onClick(DialogInterface dialog, int which) {
//       GISLocationActivity.this.onBackPressed();
			System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
//        	ManageActivity.ColseallActivity();
        }
    });
    builder.show();
	}
	public static Context getcon()
	{  
		return servicecon;
		}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.unpause();
		fbNews.setChecked(false);
		fbQuestion.setChecked(false);
		fbTweet.setChecked(false);
		fbactive.setChecked(false);
	}
	 

}