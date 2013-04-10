package Gis.namespace.Location;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import Gis.namespace.Location.GISLocationActivity.DFBG;
import Gis.namespace.Location.GISLocationActivity.Image;
import Gis.namespace.Location.GISLocationActivity.SETIP;

import Gis.namespace.Location.GISLocationActivity.XJBH;
import Gis.namespace.Location.jssbbdactivity.MyAdapter1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class jswzactivity extends Activity {
	public static String path = null;

	String bb = null;

	String bh = null;
	String sj = null;
	String ycqkmc = null;
	String yzcd = null;
	String ycqkbw = null;
	String dlwz = null;
	String ssxngc = null;
	String tbzhfwl = null;
	String tbzhfwr = null;
	String gczhfwl = null;
	String gczhfwr = null;
	String ycdddmjgxs = null;
	String ms = null;
	String yyfx = null;
	String jy = null;
	String xpsm = null;
	String fkyj = null;
	TextView ebh;
	TextView esj;
	EditText eycqkmc;
	EditText eyzcd;
	EditText eycqkbw;
	EditText edlwz;
	EditText essxngc;
	EditText etbzhfwl;
	EditText etbzhfwr;
	EditText egczhfwl;
	EditText egczhfwr;
	EditText eycdddmjgxs;
	EditText ems;
	EditText eyyfx;
	OnClickListener l;
	OnClickListener xpl;
	EditText ejy;
	String id = null;
	public String setip;
	public String setdk;
	public static MyAdapter1 adapter;
	
	ArrayList<String> listxp = new ArrayList<String>();
	public static ArrayList<HashMap<String, String>> fillMaps;
//	public static ArrayList<HashMap<String, String>> xzfillMaps;
	EditText expsm;
	// Dictionary<String,String> listxp=new Hashtable<String,String>();
	File dataf;
	ListView xplist;
//	ListView xzxplist;
	TextView xz;
	EditText efkyj;
	String xzmc;
	int idex = 0;
	private ProgressDialog pBar;
	private ProgressDialog pd;
	private Handler UPhandler;
	HashMap<String, String> xzxpmc = new HashMap<String, String>();

	// String issend="false";
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = LayoutInflater.from(jswzactivity.this).inflate(R.layout.xcsb,
				null);
		setContentView(v);
		try {
			UPhandler = new Handler();
			ebh = (TextView) findViewById(R.id.ebh);
			esj = (TextView) findViewById(R.id.esj);
			eycqkmc = (EditText) findViewById(R.id.eycqkmc);
			eyzcd = (EditText) findViewById(R.id.eyzcd);
			eycqkbw = (EditText) findViewById(R.id.eycqkbw);
			edlwz = (EditText) findViewById(R.id.edlwz);
			essxngc = (EditText) findViewById(R.id.essxngc);
			etbzhfwl = (EditText) findViewById(R.id.etbzhfwl);
			etbzhfwr = (EditText) findViewById(R.id.etbzhfwr);
			egczhfwl = (EditText) findViewById(R.id.egczhfwl);
			egczhfwr = (EditText) findViewById(R.id.egczhfwr);
			eycdddmjgxs = (EditText) findViewById(R.id.eycdddmjgxs);
			ems = (EditText) findViewById(R.id.ems);
			eyyfx = (EditText) findViewById(R.id.eyyfx);
			ejy = (EditText) findViewById(R.id.ejy);
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
			efkyj = (EditText) findViewById(R.id.efkyj);
			xplist = (ListView) findViewById(R.id.xplist);
			
			expsm = (EditText) findViewById(R.id.expsm);
			fillMaps = new ArrayList<HashMap<String, String>>();
//			xzfillMaps = new ArrayList<HashMap<String, String>>();

			dataf = new File("/sdcard/XClocalhost/zjdafh");
			SQLiteDatabase xcdb = SQLiteDatabase.openOrCreateDatabase(dataf,
					null);

			Cursor c1 = xcdb.query(SETIP.TABLE_NAME, new String[] { SETIP.IP,
					SETIP.DK }, null, null, null, null, null);

			if (c1.getCount() > 0) {
				c1.moveToFirst();
				setip = c1.getString(0);
				setdk = c1.getString(1);
			}
			c1.close();
			xcdb.close();
			Intent i = getIntent();
			final String ssbh = i.getStringExtra("bh");
			File xzfile = new File("/sdcard/巡查系统下载图片/" + ssbh + "/");
			if (xzfile.isDirectory()) {
				File[] fileArray = xzfile.listFiles();
				if (null != fileArray && 0 != fileArray.length) {
					for (int ii = 0; ii < fileArray.length; ii++) {
						File f = fileArray[ii];
						String fn = f.getName();
						String fp = f.getPath();
//						HashMap<String, String> gettable = new HashMap<String, String>();
//						gettable.put("image", fp);
//
//						gettable.put("imagename", fn);
						listxp.add(fn);
//						xzfillMaps.add(gettable);
					}
				}
			}


			this.xplist.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					HashMap<String, String> map = (HashMap<String, String>) adapter
							.getItem(arg2);
					TextView xz = (TextView) arg1.findViewById(R.id.xz);
					 
					 String image = map.get("xpmc");
					if(xz.getText().toString().equals("已下载"))
					{
						Intent intent = new Intent(Intent.ACTION_VIEW);
						//Uri mUri = Uri.parse("file://" + picFile.getPath());Android3.0以后最好不要通过该方法，存在一些小Bug
						intent.setDataAndType(Uri.fromFile(new File("/sdcard/巡查系统下载图片/" + ssbh + "/"+image)), "image/*");
						startActivity(intent);
//					Intent intentwz = new Intent(jswzactivity.this,
//							imageactivity.class);
//					intentwz.putExtra("image", "/sdcard/巡查系统下载图片/" + ssbh + "/"+image);
//					startActivity(intentwz);
					}

				}

			});
			pd = new ProgressDialog(jswzactivity.this);
			pd.setTitle("正在加载");
			pd.setMessage("请稍后！");
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			new Thread() {
				public void run() {
					HttpClient httpclient = new DefaultHttpClient();
					final String url = "http://" + setip + ":" + setdk
							+ "/androidserver/servlet/SearchServlet";
					HttpPost httppost = new HttpPost(url);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							1);
					nameValuePairs.add(new BasicNameValuePair("lx", "bd"));
					nameValuePairs.add(new BasicNameValuePair("bh", ssbh));
					HttpResponse response;
					try {
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));
						httpclient.getParams().setParameter(
								CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
						// 请求超时
						httpclient.getParams().setParameter(
								CoreConnectionPNames.SO_TIMEOUT, 30000);
						response = httpclient.execute(httppost);
						if (response.getStatusLine().getStatusCode() == 200) {
							// 获取返回的数据

							HttpEntity resEntity = response.getEntity();
							if (resEntity != null) {
								StringEntity my_entity = new StringEntity(
										EntityUtils.toString(resEntity),
										"utf-8");
								String result = EntityUtils.toString(my_entity);
								JSONArray jsonArr = new JSONObject(result)
										.getJSONArray("result");
								JSONArray jsonxp = new JSONObject(result)
										.getJSONArray("xp");
								JSONObject jsonObj = (JSONObject) jsonArr
										.get(0);
								bh = jsonObj.getString("bh");
								sj = jsonObj.getString("sj");
								id = jsonObj.getInt("id") + "";
								ycqkmc = jsonObj.getString("ycqkmc");
								yzcd = jsonObj.getString("yzcd");
								ycqkbw = jsonObj.getString("ycqkbw");
								dlwz = jsonObj.getString("wz");
								ssxngc = jsonObj.getString("ssxmgc");
								tbzhfwl = jsonObj.getString("tzbhqdfw");
								tbzhfwr = jsonObj.getString("tzbhzdfw");
								gczhfwl = jsonObj.getString("gcbhqdfw");
								gczhfwr = jsonObj.getString("gcbhzdfw");
								ycdddmjgxs = jsonObj.getString("ycdddmjgxs");
								ms = jsonObj.getString("ms");
								yyfx = jsonObj.getString("yyfx");
								jy = jsonObj.getString("jy");
								xpsm = jsonObj.getString("xdzz");
								fkyj = jsonObj.getString("fkyj");

								for (int ii = 0; ii < jsonxp.length(); ii++) {
									JSONObject jsonObj1 = (JSONObject) jsonxp
											.get(ii);
									HashMap<String, String> gettable = new HashMap<String, String>();
									String dxzxpmc = jsonObj1.getString("xpmc");
									
									if (!listxp.contains(dxzxpmc)) {
										gettable.put("xpmc",
												jsonObj1.getString("xpmc"));
										gettable.put("xz", "下载" + (ii + 1));
										
										fillMaps.add(gettable);
										xzxpmc.put("下载" + (ii + 1),
												jsonObj1.getString("xpmc"));
									}
									else
									{gettable.put("xpmc",
											jsonObj1.getString("xpmc"));
									gettable.put("xz", "已下载" );
									
									fillMaps.add(gettable);
										
									}
								}
							}

						} else {
							Log.i("HttpPost", "HttpPost方式请求失败");
						}

					} catch (Exception e) {
						Log.i("HttpPost", e.getMessage());
					}
					UPhandler.post(runnableUi1);
					pd.cancel();
				}
			}.start();

		
			l = new OnClickListener() {
				//
				public void onClick(View v) {
					xz = (TextView) v;
					if(xz.getText().equals("已下载"))
					{
						Toast.makeText(jswzactivity.this, "此相片已下载！",
								Toast.LENGTH_SHORT).show();
						
						return;
					}
					xzmc = xzxpmc.get(xz.getText());
					xz.setBackgroundResource(R.drawable.login_bg);
					// TODO Auto-generated method stub
					File file = new File("/sdcard/巡查系统下载图片/" + bh + "/" + xzmc);
					if (file.exists()) {
						Toast.makeText(jswzactivity.this, "此相片已存在！",
								Toast.LENGTH_SHORT).show();
						xz.setBackgroundResource(R.drawable.background_login_div_bg);
						return;
					}
					try {
						String url = "http://" + setip + ":" + setdk
								+ "/androidserver/Image/" + id + "/" + xzmc;
						pBar = new ProgressDialog(jswzactivity.this);
						pBar.setTitle("正在下载图片");
						pBar.setMessage("请稍后！");
						pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						pBar.setCanceledOnTouchOutside(false);
						pBar.setProgress(0);
						downAppFile(url);
						xz.setBackgroundResource(R.drawable.background_login_div_bg);

					} catch (Exception e) {
						Log.i("result", e.toString());
					}

				}
			};
			

		} catch (Exception e) {
			Log.i("result", e.toString());
		}

	}

	Runnable runnableUi = new Runnable() {

		public void run() {
			// 更新界面
//			HashMap<String, String> gettable = new HashMap<String, String>();
//			gettable.put("image", "/sdcard/巡查系统下载图片/" + bh + "/" + xzmc);
//
//			gettable.put("imagename", xzmc);
//			listxp.add(xzmc);
			xz.setText("已下载");

//			HashMap<String, String> gettable1 = new HashMap<String, String>();
//			gettable1.put("xz", xz.getText().toString());
//
//			gettable1.put("xpmc", xzmc);
//			fillMaps.remove(gettable1);
//			adapter.mItemList = fillMaps;
//			adapter.notifyDataSetChanged();
//			setListViewHeightBasedOnChildren(xplist);
			
			Toast.makeText(jswzactivity.this, "下载完成！", Toast.LENGTH_SHORT)
					.show();
		}

	};
	Runnable runnableUi1 = new Runnable() {

		public void run() {
			ebh.setText(bh);
			esj.setText(sj);
			eycqkmc.setText(ycqkmc);
			eyzcd.setText(yzcd);
			eycqkbw.setText(ycqkbw);
			edlwz.setText(dlwz);
			essxngc.setText(ssxngc);
			etbzhfwl.setText(tbzhfwl);
			etbzhfwr.setText(tbzhfwr);
			egczhfwl.setText(gczhfwl);
			egczhfwr.setText(gczhfwr);
			eycdddmjgxs.setText(ycdddmjgxs);
			ems.setText(ms);
			eyyfx.setText(yyfx);
			ejy.setText(jy);
			expsm.setText(xpsm);
			efkyj.setText(fkyj);
			String[] from = new String[] { "xpmc", "xz"};
			int[] to = new int[] { R.id.xpmc, R.id.xz};
			adapter = new MyAdapter1(jswzactivity.this, fillMaps,
					R.layout.xzxplistitem, from, to);
			xplist.setAdapter(adapter);
			setListViewHeightBasedOnChildren(xplist);
//			adapter.mItemList = fillMaps;
//			adapter.notifyDataSetChanged();
//			setListViewHeightBasedOnChildren(xplist);
		}

	};

	protected void downAppFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
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
					if (is == null) {
						throw new RuntimeException("isStream is null");
					}
					File file2 = new File("/sdcard/巡查系统下载图片/");
					if (!file2.exists()) {
						file2.mkdirs();
					}
					File file1 = new File("/sdcard/巡查系统下载图片/" + bh + "/");
					if (!file1.exists()) {
						file1.mkdirs();
					}
					File file = new File("/sdcard/巡查系统下载图片/" + bh + "/" + xzmc);

					// int bytesAvailable = is.available();
					int ii = (int) (length / 1024);
					fileOutputStream = new FileOutputStream(file);

					byte[] buf = new byte[1024];
					int ch = -1;
					int j = 0;
					int ij = 0;
					do {
						j++;
						ij = j * 100 / ii;
						pBar.setProgress(ij);
						ch = is.read(buf);
						if (ch <= 0)
							break;
						fileOutputStream.write(buf, 0, ch);
					} while (true);
					is.close();
					fileOutputStream.close();
					pBar.setProgress(100);
					// pBar.setTitle("下载完成！");
					UPhandler.post(runnableUi);
					pBar.cancel();

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();

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
	public void onBackPressed() {
		super.onBackPressed();
//	finish();
	}
	@Override
	protected void onResume() {
		super.onResume();

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		try{
		MyAdapter1 listAdapter = (MyAdapter1) listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

//		View listItem = listAdapter.getView(i, null, listView);
////
//		listItem.measure(1, 0);
////
//		 totalHeight += listItem.getMeasuredHeight();
			totalHeight += 92.1;
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;

		// params.height = totalHeight + (listView.getDividerHeight() *
		// (listAdapter.getCount() - 1));

		// ((MarginLayoutParams)params).setMargins(10, 10, 10, 10);

		listView.setLayoutParams(params);}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	

	public class MyAdapter1 extends SimpleAdapter {
		int count = 0;
		private List<HashMap<String, String>> mItemList;

		@SuppressWarnings("unchecked")
		public MyAdapter1(Context context,
				List<? extends HashMap<String, String>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			mItemList = (List<HashMap<String, String>>) data;
			if (data == null) {
				count = 0;
			} else {
				count = data.size();
			}
			
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = super.getView(position, convertView, parent);
//			 HashMap<String ,String> map = mItemList.get(position);
//			 String xzs=map.get("xz");
//			 String xps=map.get("xp");
			TextView xz = (TextView) view.findViewById(R.id.xz);
			xz.setOnClickListener(l);
//			
//			ImageView xp=(ImageView)view.findViewById(R.id.xp);
//			if(xzs.equals("已下载"))
//			{
//				Bitmap bm=BitmapFactory.decodeFile(xps);
//			    xp.setImageBitmap(bm);
//			}
			
			return view;

		}
	}

}
