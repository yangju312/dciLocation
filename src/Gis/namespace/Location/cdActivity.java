package Gis.namespace.Location;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.androidpn.client.ServiceManager;

import com.updateapp.CurrentVersion;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
@SuppressLint("SdCardPath")
public class cdActivity extends Activity {
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	ImageView imageView5;
	ImageView imageView6;
	ImageView imageView7;
	ImageView imageView8;
	ImageView imageView9;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.menu);
		
//		ManageActivity.addActiviy("cdActivity", cdActivity.this);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView3 = (ImageView) findViewById(R.id.imageView3);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		imageView6 = (ImageView) findViewById(R.id.imageView6);
		imageView7 = (ImageView) findViewById(R.id.imageView7);
		imageView8 = (ImageView) findViewById(R.id.imageView8);
		imageView9=(ImageView) findViewById(R.id.wgbg);
//		imageView6.setAlpha(0);
		imageView6.setBackgroundColor(Color.TRANSPARENT);
//		imageView1.setAdjustViewBounds(true);
//		imageView1.setMaxHeight(100);
//		imageView1.setMaxWidth(100);
//		imageView2.setAdjustViewBounds(true);
//		imageView2.setMaxHeight(100);
//		imageView2.setMaxWidth(100);
//		imageView3.setAdjustViewBounds(true);
//		imageView3.setMaxHeight(100);
//		imageView3.setMaxWidth(100);
//		imageView4.setAdjustViewBounds(true);
//		imageView4.setMaxHeight(100);
//		imageView4.setMaxWidth(100);
//		imageView5.setAdjustViewBounds(true);
//		imageView5.setMaxHeight(100);
//		imageView5.setMaxWidth(100);
//		imageView6.setAdjustViewBounds(true);
//		imageView6.setMaxHeight(100);
//		imageView6.setMaxWidth(100);
//		imageView7.setAdjustViewBounds(true);
//		imageView7.setMaxHeight(100);
//		imageView7.setMaxWidth(100);
//		imageView8.setAdjustViewBounds(true);
//		imageView8.setMaxHeight(100);
//		imageView8.setMaxWidth(100);
		imageView1.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent query = new Intent(cdActivity.this,sbactivity.class);  
			    startActivity(query);
			}});
		imageView2.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent query = new Intent(cdActivity.this,Qureyactivity.class);  
			    startActivity(query);
				
			}});
		imageView3.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 Date date = new Date(System.currentTimeMillis());  
		         SimpleDateFormat dateFormat = new SimpleDateFormat(  
		                 "_yyyyMMdd-HHmmss");
		         String name="zjdf"+dateFormat.format(date) + ".jpg";
		         File file = new File("/mnt/sdcard/珠江巡查相册/");  
		         file.mkdirs();// 创建文件夹  
		         String fileName = "/mnt/sdcard/珠江巡查相册/"+name; 
		         File file1=new File(fileName);
		         Uri u=Uri.fromFile(file1);
		         
		         intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		          
		         intent.putExtra(MediaStore.EXTRA_OUTPUT, u); 
				startActivityForResult(intent,1);  
			}});
		imageView4.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ServiceManager.viewNotificationSettings(cdActivity.this);
			}});
		imageView5.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ManageActivity.allActiviy.get("GISLocationActivity")==null)
				{
					Intent query = new Intent(cdActivity.this,GISLocationActivity.class);  
				    startActivity(query);
				    cdActivity.this.finish();
					}
				else
				{
					 cdActivity.this.finish();
				}
				
			}});
		imageView6.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent query = new Intent(cdActivity.this,tzactivity.class);  
			    startActivity(query);
			}});
		imageView8.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent query = new Intent(cdActivity.this,jssbactivity.class);  
			    startActivity(query);
			}});
		imageView9.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent query = new Intent(cdActivity.this,WgbglistActivity.class);  
			    startActivity(query);
			}});
		imageView7.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				View v = LayoutInflater.from(cdActivity.this).inflate(R.layout.gyxt, null);
				TextView tv=(TextView)v.findViewById(R.id.input3);
				tv.setText(CurrentVersion.getVerName(cdActivity.this));
				 AlertDialog.Builder builder = new AlertDialog.Builder(cdActivity.this);
			        builder.setTitle("系统相关").setIcon(null).setView(v).setNegativeButton("返回", null);
			                
			        builder.show();
			}});
		if(ManageActivity.allActiviy.get("cdActivity")==null)
		{
		ManageActivity.addActiviy("cdActivity", cdActivity.this);}
		
	}
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
////         byte[] bb = data.getByteArrayExtra("pic"); 
////         Bitmap bitmap =null;
////         if (bb == null) { return;}
////         bitmap= BitmapFactory.decodeByteArray(bb, 0, bb.length);// 获取相机返回的数据，并转换为Bitmap图片格式  
////         FileOutputStream b = null;  
//         
//         Bundle bundle = data.getExtras();
//			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
//			BufferedOutputStream bos =null;
//
//        //???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？  
//         File file = new File("/mnt/sdcard/珠江巡查相册/");  
//         file.mkdirs();// 创建文件夹  
//         String fileName = "/mnt/sdcard/珠江巡查相册/"+name; 
////         File file1 = new File(fileName); 
////         file1.mkdir();
//         try {  
//         	bos= new BufferedOutputStream(new FileOutputStream(fileName)); 
//				//b = new FileOutputStream(fileName);
//				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 把数据写入文件
//
//         }catch(Exception e){
//
//        	 e.printStackTrace();
//        	 Log.i("result", e.getMessage()); 
//
//        	 }
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
	@Override
	protected void onStop() {
		super.onStop();
//		finish();
		
	}

}