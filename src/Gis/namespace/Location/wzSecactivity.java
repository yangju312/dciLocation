package Gis.namespace.Location;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;



import Gis.namespace.Location.GISLocationActivity.DFBG;
import Gis.namespace.Location.GISLocationActivity.Image;
import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.WZBG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class wzSecactivity extends Activity{
public static	String path=null;
private ProgressDialog dialog=null;

	Button btncarema;
	Button btnlocalhost;
	Button btndel1;
	Button btnyl1;
	Button btndel2;
	Button btnyl2;
	Button btndel3;
	Button btnyl3;
	Button btndel4;
	Button btnyl4;
	Button btndel5;
	Button btnyl5;
    String bh=null;
    String sj=null;
    String ycqkmc=null;
    String yzcd=null;
    String ycqkbw=null;
    String dlwz=null;
    String ssxngc=null;
    String tbzhfwl=null;
    String tbzhfwr=null;
    String gczhfwl=null;
    String gczhfwr=null;
    String ycdddmjgxs=null;
    String ms=null;
    String yyfx=null;
    String jy=null;
    String xp1=null;
    String xp2=null;
    String xp3=null;
    String xp4=null;
    String xp5=null;
    String xpsm=null;
    TextView ebh;
    Button esj;
    EditText eycqkmc;
    EditText eyzcd;
    EditText eycqkbw;
    EditText edlwz;
    Spinner essxngc;
    EditText etbzhfwl;
    EditText etbzhfwr;
    EditText egczhfwl;
    EditText egczhfwr;
    EditText eycdddmjgxs;
    EditText ems;
    EditText eyyfx;
    EditText ejy;
    TextView exp1;
    TextView exp2;
    TextView exp3;
    TextView exp4;
    TextView exp5;
    TextView txp1;
    TextView txp2;
    TextView txp3;
    TextView  txp4;
    TextView txp5;
    EditText expsm;
    EditText efkyj;
    Dictionary<String,String> listxp=new   Hashtable<String,String>();
//    SQLiteDatabase db;
//    DBConnection helper;
   String imagename;
    File dataf;
    String fkyj=null;
    String  zt=null;
    CharSequence[] list1;
    Button save;
   	Button send;
   	Button progress;
	//DBConnection helper;
	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 try
	     {
			 View v = LayoutInflater.from(wzSecactivity.this).inflate(R.layout.main2, null);
		 setContentView(v);
//		setContentView(R.layout.main2);
		//helper = new DBConnection(Secactivity.this);
		
//		helper=new DBConnection(Secactivity.this);
//		 db = SQLiteDatabase.openOrCreateDatabase(LoginActivity.dataf, null); 
       //db.delete(UserSchema.TABLE_NAME, null, null);
		 TextView tv = (TextView) findViewById(R.id.bgzt);
			TextPaint tp = tv.getPaint();
			tp.setFakeBoldText(true);
			TextView tv1 = (TextView) findViewById(R.id.wz);
			TextPaint tp1 = tv1.getPaint();
			tp1.setFakeBoldText(true);
			TextView tv2 = (TextView) findViewById(R.id.ms);
			TextPaint tp2 = tv2.getPaint();
			tp2.setFakeBoldText(true);
			TextView tv3 = (TextView) findViewById(R.id.yyfx);
			TextPaint tp3 = tv3.getPaint();
			tp3.setFakeBoldText(true);
			TextView tv4 = (TextView) findViewById(R.id.jy);
			TextPaint tp4 = tv4.getPaint();
			tp4.setFakeBoldText(true);
			TextView tv5 = (TextView) findViewById(R.id.fkyj);
			TextPaint tp5 = tv5.getPaint();
			tp5.setFakeBoldText(true);
			TextView tv6 = (TextView) findViewById(R.id.xp);
			TextPaint tp6 = tv6.getPaint();
			tp6.setFakeBoldText(true);
		btncarema=(Button) findViewById(R.id.Carema);
		btnlocalhost=(Button) findViewById(R.id.localhost);
		btndel1=(Button) findViewById(R.id.del1);
		btnyl1=(Button) findViewById(R.id.yl1);
		btndel2=(Button) findViewById(R.id.del2);
		btnyl2=(Button) findViewById(R.id.yl2);
		btndel3=(Button) findViewById(R.id.del3);
		btnyl3=(Button) findViewById(R.id.yl3);
		btndel4=(Button) findViewById(R.id.del4);
		btnyl4=(Button) findViewById(R.id.yl4);
		btndel5=(Button) findViewById(R.id.del5);
		btnyl5=(Button) findViewById(R.id.yl5);
		 ebh=(TextView) findViewById(R.id.ebh);
	    esj=(Button) findViewById(R.id.esj);
	     eycqkmc=(EditText) findViewById(R.id.eycqkmc);
	     eyzcd=(EditText) findViewById(R.id.eyzcd);
	     eycqkbw=(EditText) findViewById(R.id.eycqkbw);
	     edlwz=(EditText) findViewById(R.id.edlwz);
	     essxngc=(Spinner) findViewById(R.id.essxngc);
	     etbzhfwl=(EditText) findViewById(R.id.etbzhfwl);
	     etbzhfwr=(EditText) findViewById(R.id.etbzhfwr);
	     egczhfwl=(EditText) findViewById(R.id.egczhfwl);
	     egczhfwr=(EditText) findViewById(R.id.egczhfwr);
	     eycdddmjgxs=(EditText) findViewById(R.id.eycdddmjgxs);
	    ems=(EditText) findViewById(R.id.ems);
	     eyyfx=(EditText) findViewById(R.id.eyyfx);
	     ejy=(EditText) findViewById(R.id.ejy);
	     exp1=(TextView) findViewById(R.id.exp1);
	     exp2=(TextView) findViewById(R.id.exp2);
	     exp3=(TextView) findViewById(R.id.exp3);
	     exp4=(TextView) findViewById(R.id.exp4);
	     exp5=(TextView) findViewById(R.id.exp5);
	     txp1=(TextView) findViewById(R.id.xp1);
	     txp2=(TextView) findViewById(R.id.xp2);
	     txp3=(TextView) findViewById(R.id.xp3);
	     txp4=(TextView) findViewById(R.id.xp4);
	     txp5=(TextView) findViewById(R.id.xp5);
	     efkyj=(EditText) findViewById(R.id.efkyj);
	     save=(Button) findViewById(R.id.save);
			send=(Button) findViewById(R.id.send);
			progress=(Button) findViewById(R.id.pro);
	     list1 = new CharSequence[35];
		 list1[0]="大坦沙岛北半岛段";list1 = new CharSequence[36];
			list1[0]=" ";
			 list1[35]="大坦沙岛北半岛段";
		 list1[1]="大坦沙岛市第一职工商学院至珠江大桥西侧工程";
		 list1[2]="大坦沙岛增加段";
		 list1[3]="东涌水闸至广州南海";
		 list1[4]="河南港务内一码头至洛溪大桥(第七期)";
		 list1[5]="前航道海心沙岛段一期";
		 list1[6]="澳口涌至石井水泥厂";
		 list1[7]="新风港码头至澳口涌(第八期)";
		 list1[8]="第五期";
		 list1[9]="第六期";
		 list1[10]="下市涌至省五矿码头";
		 list1[11]="洛溪大桥至海军码头";
		 list1[12]="海印公园";
		 list1[13]="中大码头";
		 list1[14]="航海俱乐部";
		 list1[15]="黄涌口至中大码头(第三期)";
		 list1[16]="海运局面码头至黄涌(第二期)";
		 list1[17]="南方面粉厂至原人民制革厂";
		 list1[18]="原人民制革厂至黄埔港段";
		 list1[19]="第四期";
		 list1[20]="第九期";
		 list1[21]="海心沙二期";
		 list1[22]="前航道珠岛宾馆西堤岸段";
		 list1[23]="第一期工程";
		 list1[24]="前航道省农资公司码头段";
		 list1[25]="前航道鱼珠段工程";
		 list1[26]="新洲水电公司预制场码头段工程";
		 list1[27]="前航右岸道中大码头至游艇会缺口段";
		 list1[28]="新中国造船厂缺口段";
		 list1[29]="二沙涌堤岸改造及绿化整治工程";
		 list1[30]="后航道马涌西水闸出口段工程";
		 list1[31]="后航道左岸新光大桥桥头公园段";
		 list1[32]="广州大学城浪损堤防修复一期工程";
		 list1[33]="洲头咀公园至永兴果品市场短维护工程";
		 list1[34]="洲头咀至江湾大桥段及人民桥至天字码头段";
		 
		 essxngc.setAdapter(new ArrayAdapter<CharSequence>(this,
			 android.R.layout.simple_spinner_item, list1));
	     dataf = new File("/sdcard/XClocalhost/zjdafh");
	     expsm=(EditText) findViewById(R.id.expsm);
	     Intent i = getIntent(); 
	     String ssbh=i.getStringExtra("bh");
	      fkyj=i.getStringExtra("fkyj");
         zt=i.getStringExtra("fszt");
	    	 SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
	    	 String whereClause1=DFBG.BH+"='"+ssbh+"'";
//	    	 String  sqll="select "+DFBG.BH+","+DFBG.TIME+","+DFBG.YCQKMC+","+DFBG.YZCD+","+
//		    		  DFBG.YCQKBW+","+DFBG.DLWZ+","+DFBG.SSGC+","+DFBG.TBZHFWL+","+DFBG.TBZHFWR+","+DFBG.GCZHFWL+","+DFBG.GCZHFWR+","+
//		    		  DFBG.YCDDDMJGXS+","+DFBG.MS+","+DFBG.YYFX+","+DFBG.JY +","+DFBG.XPSM+" from "+DFBG.TABLENAME;
//		     Cursor cur = db.rawQuery(sqll, null);
	    	 Cursor   cur=db.query(WZBG.TABLE_NAME, new String[]{DFBG.BH,DFBG.TIME,DFBG.YCQKMC,DFBG.YZCD,
		    		  DFBG.YCQKBW,DFBG.DLWZ,DFBG.SSGC,DFBG.TBZHFWL,DFBG.TBZHFWR,DFBG.GCZHFWL,DFBG.GCZHFWR,
		    		  DFBG.YCDDDMJGXS,DFBG.MS,DFBG.YYFX,DFBG.JY ,DFBG.XPSM},whereClause1, null, null, null, null);
		     int cut= cur.getCount();
		     cur.moveToFirst();
//		      for(int ii=0;ii<cut;ii++ )
//		     {
//		    	 String sss= cur.getString(0);
//		    	 if(sss.equals(ssbh+"\r\n"))
//		    	 {
//		    	  cur.moveToNext();
		     if(cur.getCount()>0)
		     {
		      ebh.setText(cur.getString(0));
		      esj.setText(cur.getString(1));
		      eyzcd.setText(cur.getString(3));
		      eycqkmc.setText(cur.getString(2));
		      eycqkbw.setText(cur.getString(4));
		      edlwz.setText(cur.getString(5));
		      essxngc.setSelection(getindex(list1,cur.getString(6)));
		      etbzhfwl.setText(cur.getString(7));
		      etbzhfwr.setText(cur.getString(8));
		      egczhfwl.setText(cur.getString(9));
		      egczhfwr.setText(cur.getString(10));
		      eycdddmjgxs.setText(cur.getString(11));
		      ems.setText(cur.getString(12));
		      eyyfx.setText(cur.getString(13));
		      ejy.setText(cur.getString(14));
		      expsm.setText(cur.getString(15));
//		      String s=cur.getString(16);
//		     if( fkyj==null)
//		     {
//		    	 fkyj=s;
//		     }
		      efkyj.setText(fkyj);
//		    	 }
//		    	 cur.moveToNext(); 
		     }
		      Cursor c=null;
		     
		    	  c = db.query(Image.TABLE_NAME, new String[]{Image.XP,Image.BH}, whereClause1, null, null, null, null) ;
		    
		      c.moveToFirst();
//		      int kk=0;
		      for(int k=0;k<c.getCount();k++)
		      {

		    	  if(k==0)
		    	  {
		    		  exp1.setText(c.getString(0));
		    		  xp1=c.getString(0);
	                	listxp.put("1", c.getString(0));

		    	  }
		    	  else if(k==1)
		    	  {
		    		  exp2.setText(c.getString(0));
		    		  xp2=c.getString(0);
	                	listxp.put("2", c.getString(0));

		    	  } 
		    	  else if(k==2)
		    	  {
		    		  exp3.setText(c.getString(0));
		    		  xp3=c.getString(0);
	                	listxp.put("3", c.getString(0));

		    	  } 
		    	  else if(k==3)
		    	  {
		    		  exp4.setText(c.getString(0));
		    		  xp4=c.getString(0);
	                	listxp.put("4", c.getString(0));

		    	  } 
		    	  else if(k==4)
		    	  {
		    		  exp5.setText(c.getString(0));
		    		  xp5=c.getString(0);
	                	listxp.put("5", c.getString(0));

		    	  } 
		    		c.moveToNext();  
		      }
		      db.close();
		      cur.close();
		      c.close();
		      esj.setOnClickListener(new View.OnClickListener(){
		            public void onClick(View v) {
		            	onCreateDialog(esj).show();
		            }
		        });
		      btncarema.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						 Date date = new Date(System.currentTimeMillis());  
				         SimpleDateFormat dateFormat = new SimpleDateFormat(  
				                 "_yyyyMMdd-HHmm");
				         String name="zjdf"+dateFormat.format(date) + ".jpg";
				         File file = new File("/mnt/sdcard/珠江巡查相册/");  
				         file.mkdirs();// 创建文件夹  
				         imagename = "/mnt/sdcard/珠江巡查相册/"+name; 
				         File file1=new File(imagename);
				         Uri u=Uri.fromFile(file1);
				         
				         intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				          
				         intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
						startActivityForResult(intent,1);      
					}
				});
		      btnlocalhost.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				  Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);  
                  getImage.addCategory(Intent.CATEGORY_OPENABLE);  
                  getImage.setType("image/jpeg");  
                  startActivityForResult(getImage, 2);  
			}
		});
		
		btnyl4.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				 path= exp4.getText().toString(); 
				 if(path==""){
						return;}
				 else{
					 File f=new File(path);
					 if(!f.exists())
					 {
						 MessageBox.createAlertDialog("提示", "此相片不存在，可能已删除！", "返回", wzSecactivity.this).show();
						 return;
					 }
//						Intent intent = new Intent(wzSecactivity.this,imageactivity.class);
//						intent.putExtra("path", path);
//						startActivity(intent);
				 }
			}
		});
		
		btndel1.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				  String s="1";
				 if(isnull(s))
				 {
					 xp1=null;
					 exp1.setText("");
				     listxp.remove(s);

				 }
			}
		});
		btndel2.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				  String s="2";
				 if(isnull(s))
				 {
					 xp2=null;
					 exp2.setText("");
				     listxp.remove(s);

				 }
			}
		});
		btndel3.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				  String s="3";
				 if(isnull(s))
				 {
					 xp3=null;
					 exp3.setText("");
				     listxp.remove(s);

				 }
			}
		});
		btndel4.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				  String s="4";
				 if(isnull(s))
				 {
					 xp4=null;
					 exp4.setText("");
				     listxp.remove(s);

				 }
			}
		});
		btndel5.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				  String s="5";
				 if(isnull(s))
				 {
					 xp5=null;
					 exp5.setText("");
				     listxp.remove(s);

				 }
			}
		});
		save.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doclick(1);
			}});
		send.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doclick(2);
			}});
		progress.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			wzSecactivity.this.finish();
			}});
		 if(ManageActivity.allActiviy.get("Secactivity")!=null)
			{
		 ManageActivity.addActiviy("Secactivity",wzSecactivity.this);
			}
		}
	     catch (Exception e) {
        	 Log.i("result", e.toString());
         }

	}
	
	
	

	@SuppressLint({ "SdCardPath", "SdCardPath" })
	@Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        // TODO Auto-generated method stub  
	        super.onActivityResult(requestCode, resultCode, data); 
	        
	            String sdStatus = Environment.getExternalStorageState();  
	            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
	                Log.i("TestFile",  
	                        "SD card is not avaiable/writeable right now.");  
	                return;  
	            } 
	            if(requestCode==1)
		        {
	            	if (resultCode != Activity.RESULT_OK)  
    	            return;  
	           
	            try {  
//	            	
	                if(xp1==null||xp1=="")
	                {
	                	xp1=imagename;
	                	exp1.setText(imagename);
	                	listxp.put("1", imagename);	
	                }
	                else
	                {
	                	if(xp2==null||xp2=="")
	                    {
	                	xp2=imagename;
	                	exp2.setText(imagename);
	                	listxp.put("2", imagename);	
	                      }
	                	 else
	 	                {
	 	                	if(xp3==null||xp3=="")
	 	                    {
	 	                	xp3=imagename;
	 	                	exp3.setText(imagename);
	 	                	listxp.put("3", imagename);	

	 	                      }
	 	                	 else
	 		                {
	 		                	if(xp4==null||xp4=="")
	 		                    {
	 		                	xp4=imagename;
	 		                	exp4.setText(imagename);
	 		                	listxp.put("4", imagename);	
	 		                      }
	 		                	 else
	 			                {
	 			                	if(xp5==null||xp5=="")
	 			                    {
	 			                	xp5=imagename;
	 			                	exp5.setText(imagename);
	 			                	listxp.put("5", imagename);	
	 			                      }
	 			                	else
	 			                	{
	 			                		Toast.makeText(this, "五张相片已满！", Toast.LENGTH_LONG).show();
	 			                	}
	 			                	
	 			                }
	 		                	
	 		                }
	 	                	
	 	                }
	                	
	                }
	                
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            } finally {  
  
	            }  
		        }
	            if(requestCode==2)
	            {
	            	if (resultCode != RESULT_OK)  
	    	            return;  
	    	     Uri u=data.getData();
	    	     String fileName=u.getPath();
           	  String g=fileName.substring(1, 4);
           	  if(!g.equals("mnt"))
           	  {
	            String[] proj = {MediaStore.Images.Media.DATA};
	            Cursor cursor = managedQuery(u, proj, null, null, null); 
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	             fileName = cursor.getString(column_index);
           	  }
           	 Enumeration<String> en = listxp.keys();
      	      while(en.hasMoreElements()){
      	    	  String ele = listxp.get((String) en.nextElement());
      	    	  if(ele.equals(fileName))
      	    	  {
      	    		  MessageBox.createAlertDialog("提示", "此相片已存在相片列表，请重新选择！", "确定", this).show();
      	    		  return;
      	    	  }
      	      
      	      }
//	            	String fileName=uri.substring(0);
	            	
	            	 if(xp1==null||xp1=="")
		                {
		                	xp1=fileName;
		                	exp1.setText(fileName);
		                	listxp.put("1", fileName);	
		                	exp1.setVisibility(View.VISIBLE);
			           	     txp1.setVisibility(View.VISIBLE);
			           	     btndel1.setVisibility(View.VISIBLE);
			           	     btnyl1.setVisibility(View.VISIBLE);
		                }
		                else
		                {
		                	if(xp2==null||xp2=="")
		                    {
		                	xp2=fileName;
		                	exp2.setText(fileName);
		                	listxp.put("2", fileName);	
		                	exp2.setVisibility(View.VISIBLE);
			           	     txp2.setVisibility(View.VISIBLE);
			           	     btndel2.setVisibility(View.VISIBLE);
			           	     btnyl2.setVisibility(View.VISIBLE);
		                      }
		                	 else
		 	                {
		 	                	if(xp3==null||xp3=="")
		 	                    {
		 	                	xp3=fileName;
		 	                	exp3.setText(fileName);
		 	                	listxp.put("3", fileName);
		 	                	exp3.setVisibility(View.VISIBLE);
		 		           	     txp3.setVisibility(View.VISIBLE);
		 		           	     btndel3.setVisibility(View.VISIBLE);
		 		           	     btnyl3.setVisibility(View.VISIBLE);
		 	                      }
		 	                	 else
		 		                {
		 		                	if(xp4==null||xp4=="")
		 		                    {
		 		                	xp4=fileName;
		 		                	exp4.setText(fileName);
		 		                	listxp.put("4", fileName);
		 		                	exp4.setVisibility(View.VISIBLE);
		 			           	     txp4.setVisibility(View.VISIBLE);
		 			           	     btndel4.setVisibility(View.VISIBLE);
		 			           	     btnyl4.setVisibility(View.VISIBLE);
		 		                      }
		 		                	 else
		 			                {
		 			                	if(xp5==null||xp5=="")
		 			                    {
		 			                	xp5=fileName;
		 			                	exp5.setText(fileName);
		 			                	listxp.put("5", fileName);
		 			                	exp5.setVisibility(View.VISIBLE);
		 				           	     txp5.setVisibility(View.VISIBLE);
		 				           	     btndel5.setVisibility(View.VISIBLE);
		 				           	     btnyl5.setVisibility(View.VISIBLE);
		 			                      }
		 			                	else
		 			                	{
		 			                		Toast.makeText(this, "五张相片已满！", Toast.LENGTH_LONG).show();
		 			                	}
		 			                	
		 			                }
		 		                	
		 		                }
		 	                	
		 	                }
		                }
	            }
	            if(ManageActivity.allActiviy.get("Secactivity")!=null)
	    		{
	            ManageActivity.addActiviy("Secactivity", wzSecactivity.this);}
	        }  
	      

   public boolean doclick(int item) {
	   
	   
		bh=ebh.getText().toString();
		  sj=esj.getText().toString();
       ycqkmc=eycqkmc.getText().toString();
       yzcd=eyzcd.getText().toString();
       ycqkbw=eycqkbw.getText().toString();
       dlwz=edlwz.getText().toString();
       ssxngc=essxngc.getSelectedItem().toString();
       tbzhfwl=etbzhfwl.getText().toString();
       tbzhfwr=etbzhfwr.getText().toString();
       gczhfwl=egczhfwl.getText().toString();
       gczhfwr=egczhfwr.getText().toString();
       ycdddmjgxs=eycdddmjgxs.getText().toString();
       ms=ems.getText().toString();
       yyfx=eyyfx.getText().toString();
       jy=ejy.getText().toString();
       xp1=exp1.getText().toString();
       xp2=exp2.getText().toString();
       xp3=exp3.getText().toString();
       xp4=exp4.getText().toString();
       xp5=exp5.getText().toString();
       xpsm=expsm.getText().toString();
       //       String surl=ConfigManager.getInstance().getString("DFBGServlet");
       if(bh==null||bh==""||bh.equals(""))
       {
       Toast.makeText(this, "编号不能为空！", Toast.LENGTH_LONG).show();
       return false;
       }
      
       SQLiteDatabase  db =SQLiteDatabase.openOrCreateDatabase(dataf, null); 
       switch (item) {

       case 1:
    	   try{
    		 
    		   if(zt.equals("已办结"))
	              {
	            	  MessageBox.createAlertDialog("提示", "此报告已办结！", "返回", this).show();
	            	  break;
	              }
         
		   ContentValues values = new ContentValues();
		   
//			values.put(DFBG.BH,bh);
			values.put(DFBG.TIME,sj);
			values.put(DFBG.YCQKMC,ycqkmc);
			values.put(DFBG.YZCD,yzcd);
			values.put(DFBG.YCQKBW,ycqkbw);
			values.put(DFBG.DLWZ,dlwz);
			values.put(DFBG.SSGC,ssxngc);
			values.put(DFBG.TBZHFWL,tbzhfwl);
			values.put(DFBG.TBZHFWR,tbzhfwr);
			values.put(DFBG.GCZHFWL,gczhfwl);
			values.put(DFBG.GCZHFWR,gczhfwr);
			values.put(DFBG.YCDDDMJGXS,ycdddmjgxs);
			values.put(DFBG.MS,ms);
			values.put(DFBG.YYFX,yyfx);
			values.put(DFBG.JY,jy);
//			values.put(DFBG.XDY,LoginActivity.UserName);
			
			values.put(DFBG.XPSM,xpsm);
			if(listxp.size()>0)
			{
//				SQLiteDatabase db= SQLiteDatabase.openOrCreateDatabase(LoginActivity.dataf, null); 
				String where=Image.BH+"='"+bh+"'";
				db.delete(Image.TABLE_NAME, where, null);
			
			}
			 Enumeration<String> en = listxp.keys();
      	      while(en.hasMoreElements()){
//      	    	db= SQLiteDatabase.openOrCreateDatabase(LoginActivity.dataf, null); 
                String ele = listxp.get((String) en.nextElement());
                ContentValues imagevalues = new ContentValues();
                imagevalues.put(Image.BH, bh);
    			imagevalues.put(Image.XP, ele);
    			 db.insert(Image.TABLE_NAME, null, imagevalues);
    			
                }
				String whereClause=DFBG.BH+"='"+bh+"'";
				
			    db.update(WZBG.TABLE_NAME, values, whereClause, null);
    	   }
       catch (Exception e) {
      	 Log.i("result", e.toString());
       }finally
       {
    	   db.close();
       }
           break;

       case  2:
              { if(sj==null||sj==""||sj.equals(""))
              {
                  Toast.makeText(this, "时间不能为空！", Toast.LENGTH_LONG).show();
                  return false;
                  }
//                  if(ycqkmc==null||ycqkmc==""||ycqkmc.equals(""))
//                  {
//                  Toast.makeText(this, "异常情况名称不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(yzcd==null||yzcd==""||yzcd.equals(""))
//                  {
//                  Toast.makeText(this, "严重程度不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(ycqkbw==null||ycqkbw==""||ycqkbw.equals(""))
//                  {
//                  Toast.makeText(this, "异常情况部位不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
                  if(dlwz==null||dlwz==""||dlwz.equals(""))
                  {
                  Toast.makeText(this, "地理位置不能为空！", Toast.LENGTH_LONG).show();
                  return false;
                  }
//                  if(ssxngc==null||ssxngc==""||ssxngc.equals(""))
//                  {
//                  Toast.makeText(this, "所属项目工程不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(tbzhfwl==null||tbzhfwl==""||tbzhfwl.equals(""))
//                  {
//                  Toast.makeText(this, "统编桩号起始范围不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(tbzhfwr==null||tbzhfwr==""||tbzhfwr.equals(""))
//                  {
//                  Toast.makeText(this, "统编桩号终止范围不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(ycdddmjgxs==null||ycdddmjgxs==""||ycdddmjgxs.equals(""))
//                  {
//                  Toast.makeText(this, "异常堤段断面结构形式不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(ms==null||ms==""||ms.equals(""))
//                  {
//                  Toast.makeText(this, "描述不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(yyfx==null||yyfx==""||yyfx.equals(""))
//                  {
//                  Toast.makeText(this, "原因分析不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(jy==null||jy==""||jy.equals(""))
//                  {
//                  Toast.makeText(this, "建议不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }
//                  if(xpsm==null||xpsm==""||xpsm.equals(""))
//                  {
//                  Toast.makeText(this, "巡堤组长不能为空！", Toast.LENGTH_LONG).show();
//                  return false;
//                  }

    	   if(zt.equals("已反馈"))
    	   {
    	    	  
//    			
    	         HandlerThread handlerThread1=new HandlerThread("handler_thread");
    	         handlerThread1.start();
    	         
    	         MyHandler handler1=new MyHandler(handlerThread1.getLooper());
    	         Message msg=handler1.obtainMessage();
    	       handler1.sendMessage(msg);
    	       dialog=new ProgressDialog(wzSecactivity.this);
    	       dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    	       dialog.setTitle("正在发送...");
    	       dialog.setMessage("请稍候");
    	        dialog.show();     
    	           }
    	              else if(zt.equals("已办结"))
    	              {
    	            	  MessageBox.createAlertDialog("提示", "此报告已办结！", "返回", this).show();
    	              }
    	   
    	              else if(zt.equals("待反馈"))
    	              {
    	            	  MessageBox.createAlertDialog("提示", "此报告无反馈意见！", "返回", this).show();
    	              }
           }
           break;
       }
       return false;

   }


public Bitmap getimage(String path) throws IOException
{  
//	File f=new File(path);
//	OutputStream out=new FileOutputStream(f);
//	 byte[] bb =null;
//	out.write(bb);
	Bitmap bit=BitmapFactory.decodeFile(path);
	return bit;
	}

public Boolean isnull(String i) 
{ 
  Enumeration<String> en = listxp.keys();
  while(en.hasMoreElements()){
      String ele = (String) en.nextElement();
      if(ele==i)
      {
    	  return true; 
      }
   }
return false;
}
Calendar c = null;
protected Dialog onCreateDialog(final Button databtn1 ) {
    Dialog dialog = null;
   

        c = Calendar.getInstance();
        dialog = new DatePickerDialog(
            this,
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker dp, int year,int month, int dayOfMonth) {
                	databtn1.setText( year + "年" + (month+1) + "月" + gets(dayOfMonth) + "日");
                }
            }, 
            c.get(Calendar.YEAR), // 传入年份
            c.get(Calendar.MONTH), // 传入月份
            c.get(Calendar.DAY_OF_MONTH) );// 传入天数

    return dialog;
}
public int getindex(CharSequence[] l,String g)
{
	for(int i=0;i<l.length;i++)
	{
		if(l[i].equals(g))
		{
			return i;
		}
	}
	return 0;}
public String gets(int d)
{
	 if(d<10)
	 {return "0"+d;}
	return d+"";
		 
}
   @Override
   public void onOptionsMenuClosed(Menu menu) {
//       Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
//       Toast.makeText(this,
//               "选项菜单显示之前onPrepareOptionsMenu方法会被调用，你可以用此方法来根据打当时的情况调整菜单",
//               Toast.LENGTH_LONG).show();

       // 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用

       return true;

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
	   class MyHandler extends Handler {
		     
		     public MyHandler(Looper looper){
		      super(looper);
		     }
		     @Override
		  public void handleMessage(Message msg) {
		   // TODO Auto-generated method stub
		   super.handleMessage(msg);
		   {   SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null); 
			 Cursor c=db.query(SETIP.TABLE_NAME, new String[]{SETIP.IP,SETIP.DK}, null, null, null, null, null);
		     	c.moveToFirst();
			   String setip=c.getString(0);
			   String setdk=c.getString(1);
			  
			   c.close();
			   String surl="http://"+setip+":"+setdk+"/androidserver/servlet/JSWZServlet";
	    	   Map<String, String> par=new HashMap<String, String>();
	    	   par.put("isfirst", "false");
	    	  par.put("bh", bh);
	    	   par.put("sj", sj);
	    	   par.put("ycqkmc", ycqkmc);
	    	   par.put("yzcd", yzcd);
	    	   par.put("ycqkbw", ycqkbw);
	    	   par.put("dlwz", dlwz);
	    	   par.put("ssxngc", ssxngc);
	    	   par.put("tbzhfwl", tbzhfwl);
	    	   par.put("tbzhfwr", tbzhfwr);
	    	   par.put("gczhfwl", gczhfwl);
	    	   par.put("gczhfwr", gczhfwr);
	    	   par.put("ycdddmjgxs", ycdddmjgxs);
	    	   par.put("ms", ms);
	    	   par.put("yyfx", yyfx);
	    	   par.put("jy", jy);
//	    	   par.put("xdy", LoginActivity.UserName);
//	    	   par.put("xp1", xp1.substring(xp1.lastIndexOf('/')+1));
//	    	   par.put("xp2",  xp2.substring(xp2.lastIndexOf('/')+1));
//	    	   par.put("xp3", xp3.substring(xp3.lastIndexOf('/')+1));
//	    	   par.put("xp4",xp4.substring(xp4.lastIndexOf('/')+1));
//	    	   par.put("xp5", xp5.substring(xp5.lastIndexOf('/')+1));
	    	   par.put("xpsm", xpsm);
	    	   par.put("isnew", 3+"");

			 String end = "\r\n";
				
	         String twoHyphens = "--";

	         String boundary = "*****";

	         try {

	        	 URL url = new URL(surl);


	             HttpURLConnection con = (HttpURLConnection) url.openConnection();
	             con.addRequestProperty("Accept-Charset", "GB2312;");
	            
	             /* 允许Input、Output，不使用Cache */

	             con.setDoInput(true);

	             con.setDoOutput(true);

	             con.setUseCaches(false);

	             // 设置http连接属性

	             con.setRequestMethod("POST");

	             con.setRequestProperty("Connection", "Keep-Alive");

	             con.setRequestProperty("Charset", "UTF-8");

	             con.setRequestProperty("Content-Type",

	                     "multipart/form-data;boundary=" + boundary);

	             DataOutputStream ds = new DataOutputStream(con.getOutputStream());

	             ds.writeBytes(twoHyphens + boundary + end);
	             StringBuilder sb=new StringBuilder(); 
	             for(Entry<String, String> entry :par.entrySet()) {//构建表单字段内容 

	            	sb.append(twoHyphens); 

	            	sb.append(boundary); 

	            	sb.append("\r\n"); 

	            	sb.append("Content-Disposition: form-data; name=\""+entry.getKey()+"\"\r\n\r\n"); 

	            	sb.append(entry.getValue()); 

	            	sb.append("\r\n"); 

	            	} 
	             
	             ds.write(sb.toString().getBytes());
	             if(listxp.size()<1)
	             {
	             dialog.setProgress(90);}
	             else
	             {
	            	 dialog.setProgress(5); 
	             }
	             int size=listxp.size();
	             int i1=0;
	             Enumeration<String> en = listxp.keys();
	       	      while(en.hasMoreElements()){
	       	    	  i1++;
	                 String ele = listxp.get((String) en.nextElement());
	                 int index =ele.lastIndexOf('/');
	                 String newName=ele.substring(index+1);
	                 ds.writeBytes(twoHyphens + boundary + end);
	                 ds.writeBytes("Content-Disposition: form-data; "
	                		 
	                     + "name=\"file1\";filename=\"" + newName + "\"" + end);
	 ds.writeBytes("Content-Type: application/octet-stream; charset=UTF-8" + end);
	                 ds.writeBytes(end);
	                 FileInputStream fStream = new FileInputStream(ele);
                     int bytesAvailable = fStream.available();
	                 /* 设置每次写入1024bytes */

	                 int bufferSize = 1024;
                      int ii=bytesAvailable/bufferSize;
	                 byte[] buffer = new byte[bufferSize];

	                 int length = 0;

	                 /* 从文件读取数据至缓冲区 */
                      int  iii=0;
	                 while ((length = fStream.read(buffer)) != -1) {

	                     /* 将资料写入DataOutputStream中 */
                         iii++;
                       int i4=(i1-1)*90/size+ (iii*90)/(size*ii);
                       Log.i("result", i4+"");
                       dialog.setProgress(5+i4);
	                     ds.write(buffer, 0, length);

	                 }

	                 ds.writeBytes(end);

	                 fStream.close();
	                 }  
	              ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	       	      ds.flush();
	       	   dialog.setProgress(95);
	       	   ds.close();
	       	   /* 取得Response内容 */
	       	   int kode=con.getResponseCode();
	       	 Log.i("result", con.getResponseCode()+"");
	        if(kode==200)
	         {
	       	    InputStream is = con.getInputStream();

	       	    int ch;

	       	    StringBuffer b = new StringBuffer();

	       	    while ((ch = is.read()) != -1) {

	       	        b.append((char) ch);

	       	    }
//	       	    String s=new String(b.toString().getBytes(),"utf-8");
//	       	     Log.i("result", "Response内容"+s);
//	       	    bb="巡检"+s.substring(s.lastIndexOf(" "));
//	       	    Log.i("result", "Response内容"+bb);
	       	    
	       	 zt="已办结";
	           ContentValues values = new ContentValues();
			  	
				values.put(DFBG.BH,bh);
				values.put(DFBG.TIME,sj);
				values.put(DFBG.YCQKMC,ycqkmc);
				values.put(DFBG.YZCD,yzcd);
				values.put(DFBG.YCQKBW,ycqkbw);
				values.put(DFBG.DLWZ,dlwz);
				values.put(DFBG.SSGC,ssxngc);
				values.put(DFBG.TBZHFWL,tbzhfwl);
				values.put(DFBG.TBZHFWR,tbzhfwr);
				values.put(DFBG.GCZHFWL,gczhfwl);
				values.put(DFBG.GCZHFWR,gczhfwr);
				values.put(DFBG.YCDDDMJGXS,ycdddmjgxs);
				values.put(DFBG.MS,ms);
				values.put(DFBG.YYFX,yyfx);
				values.put(DFBG.JY,jy);
//				values.put(DFBG.XDY,LoginActivity.UserName);
			    
				values.put(DFBG.XPSM,xpsm);
				values.put(DFBG.FKYJ, fkyj);
				values.put(DFBG.FSZT , 2);
				
				 String whereClause1="bh='"+bh+"'";
				 db.delete(Image.TABLE_NAME, whereClause1, null);
				 Enumeration<String> en1 = listxp.keys();
	     	      while(en1.hasMoreElements()){
	     	    	
	               String ele = listxp.get((String) en1.nextElement());
	               ContentValues imagevalues = new ContentValues();
	               imagevalues.put(Image.BH, bh);
	   			imagevalues.put(Image.XP, ele);
	   			 db.insert(Image.TABLE_NAME, null, imagevalues);
	   			
	               }
	     	     dialog.setProgress(98);
	       	 con.disconnect();
	       	

	       	 db.update(WZBG.TABLE_NAME,  values,whereClause1,null);
	       	

	       	 db.close();
	     	sbactivity.dosetback2();
	       	dialog.setProgress(100);
	       	 }//       	   MessageBox.createAlertDialog("提示", "发送成功！", "返回", this).show();
	         }catch (Exception e) {
	        	 Log.i("result", e.toString());
	         }
//			
			       
	           }
		   dialog.cancel();
		   
		  }
		     
		    }

}
