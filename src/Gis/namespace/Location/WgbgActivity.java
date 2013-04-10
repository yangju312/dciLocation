package Gis.namespace.Location;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

import Gis.namespace.Location.GISLocationActivity.DFBG;
import Gis.namespace.Location.GISLocationActivity.Image;
import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.TBZH;
import Gis.namespace.Location.GISLocationActivity.User;
import Gis.namespace.Location.GISLocationActivity.WGBG;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

@SuppressLint({ "SdCardPath", "ResourceAsColor" })
public class WgbgActivity extends Activity {
	public static String path = null;
	private ProgressDialog dialog = null;
	String bb = null;
	Button btncarema;
	Button btnlocalhost;
	String bh = null;
	String sj = null;
	String bz = null;
	TextView ebh;
	Button esj;
	EditText ebz;
	ListView xplist;
	ArrayList<String> listxp = new ArrayList<String>();
	File dataf;
	boolean issend = false;
	String fszt;
	Button save;
	Button send;
	Button progress;
	private Handler UPhandler;
	public static MyAdapter1 adapter;
	public static ArrayList<HashMap<String, String>> fillMaps;
	String imagename;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View v = LayoutInflater.from(WgbgActivity.this).inflate(R.layout.wgbglay,
				null);
		setContentView(v);

		try {

			UPhandler = new Handler();

			btncarema = (Button) findViewById(R.id.Carema);
			btnlocalhost = (Button) findViewById(R.id.localhost);
			xplist = (ListView) findViewById(R.id.xplist);
			ebz = (EditText) findViewById(R.id.ejy);
			save = (Button) findViewById(R.id.save);
			send = (Button) findViewById(R.id.send);
			progress = (Button) findViewById(R.id.pro);
			ebh = (TextView) findViewById(R.id.ebh);
			esj = (Button) findViewById(R.id.esj);
			dataf = new File("/sdcard/XClocalhost/zjdafh");
			fillMaps = new ArrayList<HashMap<String, String>>();
			Intent i = getIntent();
			String ssbh = i.getStringExtra("bh");
			fszt = i.getStringExtra("fszt");
			SQLiteDatabase db = SQLiteDatabase
					.openOrCreateDatabase(dataf, null);
			String whereClause1 = "bh='" + ssbh + "'";
			Cursor cur = null;
			cur = db.query(WGBG.TABLE_NAME, new String[] { WGBG.BH, WGBG.SJ,
					WGBG.BZ }, whereClause1, null, null, null, null);
			cur.moveToFirst();
			if (cur.getCount() > 0) {
				ebh.setText(cur.getString(0));
				esj.setText(cur.getString(1));
				ebz.setText(cur.getString(2));

			}
			Cursor c = null;

			c = db.query(Image.TABLE_NAME, new String[] { Image.XP },
					whereClause1, null, null, null, null);

			c.moveToFirst();
			for (int k = 0; k < c.getCount(); k++) {
				HashMap<String, String> gettable = new HashMap<String, String>();
				gettable.put("image", c.getString(0));
				int index = c.getString(0).lastIndexOf("/");
				String imname = c.getString(0).substring(index + 1);
				gettable.put("imagename", imname);
				listxp.add(imname);
				fillMaps.add(gettable);
				c.moveToNext();
			}
			db.close();
			cur.close();
			c.close();

			String[] from = new String[] { "imagename" };
			int[] to = new int[] {  R.id.xpmc };
			adapter = new MyAdapter1(WgbgActivity.this, fillMaps,
					R.layout.xplistitem, from, to);
			xplist.setAdapter(adapter);
			setListViewHeightBasedOnChildren(xplist);

			esj.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					onCreateDialog(esj).show();
				}
			});
			btncarema.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Date date = new Date(System.currentTimeMillis());
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"_yyyyMMdd-HHmmss");
					String name = "zjdf" + dateFormat.format(date) + ".jpg";
					File file = new File("/mnt/sdcard/珠江巡查相册/");
					file.mkdirs();// 创建文件夹
					imagename = "/mnt/sdcard/珠江巡查相册/" + name;
					File file1 = new File(imagename);
					Uri u = Uri.fromFile(file1);

					intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);

					intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
					startActivityForResult(intent, 1);
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
			this.xplist.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					HashMap<String, String> map = (HashMap<String, String>) adapter
							.getItem(arg2);
					String image = map.get("image");
					Intent intent = new Intent(Intent.ACTION_VIEW);
					// Uri mUri = Uri.parse("file://" +
					// picFile.getPath());Android3.0以后最好不要通过该方法，存在一些小Bug
					intent.setDataAndType(Uri.fromFile(new File(image)),
							"image/*");
					startActivity(intent);
				}

			});
			this.xplist
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						public boolean onItemLongClick(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub
							@SuppressWarnings("unchecked")
							HashMap<String, String> map = (HashMap<String, String>) adapter
									.getItem(arg2);
							final int index = arg2;
							// String imagename=map.get("imagename");
							String image = "是否删除照片 " + map.get("imagename")
									+ "!";
							TextView tex = new TextView(WgbgActivity.this);
							tex.setText(image);
							tex.setTextSize(15);

							// tex.setTextColor(R.color.txtcc);
							tex.setGravity(Gravity.CENTER);
							AlertDialog.Builder builder = new AlertDialog.Builder(
									WgbgActivity.this);
							builder.setTitle("相片删除").setIcon(

							android.R.drawable.ic_delete).setView(tex)
									.setNegativeButton("返回", null);
							builder.setPositiveButton("删除",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											fillMaps.remove(index);
											listxp.remove(index);
											adapter.mItemList = fillMaps;
											adapter.notifyDataSetChanged();
											setListViewHeightBasedOnChildren(xplist);

										}
									});
							builder.show();
							return false;
						}

					});

			save.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					doclick(1);
				}
			});
			send.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					doclick(2);
				}
			});
			progress.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
				
					WgbgActivity.this.finish();
				}
			});
			if (ManageActivity.allActiviy.get("jssbbdactivity") == null) {
				ManageActivity.addActiviy("jssbbdactivity", WgbgActivity.this);
			}
		} catch (Exception e) {
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
			Log.i("TestFile", "SD card is not avaiable/writeable right now.");
			return;
		}
		if (requestCode == 1) {
			if (resultCode != Activity.RESULT_OK)
				return;

			try {
				HashMap<String, String> gettable = new HashMap<String, String>();
				gettable.put("image", imagename);
				int index = imagename.lastIndexOf("/");
				String imname = imagename.substring(index + 1);
				gettable.put("imagename", imname);
				fillMaps.add(gettable);
				listxp.add(imname);
				adapter.mItemList = fillMaps;
				adapter.notifyDataSetChanged();
				// listxp.a
				setListViewHeightBasedOnChildren(xplist);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		if (requestCode == 2) {
			if (resultCode != RESULT_OK)
				return;

			Uri u = data.getData();
			String fileName = u.getPath();
			String g = fileName.substring(1, 4);
			if (!g.equals("mnt")) {
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(u, proj, null, null, null);
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				fileName = cursor.getString(column_index);
			}
			int index = fileName.lastIndexOf("/");
			String imname = fileName.substring(index + 1);

			if (listxp.contains(imname)) {

				MessageBox.createAlertDialog("提示", "此相片已存在相片列表，请重新选择！", "确定",
						this).show();
				return;

			}
			HashMap<String, String> gettable = new HashMap<String, String>();
			gettable.put("image", fileName);
			// int index=fileName.lastIndexOf("/");
			// String imname=fileName.substring(index+1);
			gettable.put("imagename", imname);
			fillMaps.add(gettable);
			listxp.add(imname);
			adapter.mItemList = fillMaps;
			adapter.notifyDataSetChanged();
			setListViewHeightBasedOnChildren(xplist);

		}
	}

	public boolean doclick(int item) {
		try {

			bh = ebh.getText().toString();
			sj = esj.getText().toString();
			bz = ebz.getText().toString();

			if (bh == null || bh == "" || bh.equals("")) {
				Toast.makeText(this, "编号不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			switch (item) {

			case 1:
				try {
					// String g= bh.substring(0, 2);
					if (fszt.equals("已发送")) {
						MessageBox.createAlertDialog("提示", "此编号已发送过！", "返回",
								this).show();
						return false;
					}

					SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
							dataf, null);
					ContentValues values = new ContentValues();

					values.put(WGBG.BH, bh);
					values.put(WGBG.SJ, sj);
					values.put(WGBG.BZ, bz);
					String whereClause = DFBG.BH + "='" + bh + "'";

					db.update(WGBG.TABLE_NAME, values, whereClause, null);

					if (listxp.size() > 0) {
						
						String where = Image.BH + "='" + bh + "'";
						db.delete(Image.TABLE_NAME, where, null);

					}

					for (int i = 0; i < fillMaps.size(); i++) {
						HashMap<String, String> gettable = fillMaps.get(i);
						String ele = gettable.get("image");
						ContentValues imagevalues = new ContentValues();
						imagevalues.put(Image.BH, bh);
						imagevalues.put(Image.XP, ele);
						db.insert(Image.TABLE_NAME, null, imagevalues);
					}
					db.close();
					Toast.makeText(WgbgActivity.this, "保存成功！",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Log.i("result", e.toString());
				}
				break;

			case 2: {
				if (sj == null || sj == "" || sj.equals("")) {
					Toast.makeText(this, "时间不能为空！", Toast.LENGTH_LONG).show();
					return false;
				}
				if (fszt.equals("未发送")) {

					HandlerThread handlerThread1 = new HandlerThread(
							"handler_thread");
					handlerThread1.start();

					MyHandler handler1 = new MyHandler(
							handlerThread1.getLooper());
					Message msg = handler1.obtainMessage();
					handler1.sendMessage(msg);
					dialog = new ProgressDialog(WgbgActivity.this);
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setTitle("正在发送...");
					dialog.setMessage("请稍候");
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
				} else {
					MessageBox.createAlertDialog("提示", "此编号已发送过！", "返回", this)
							.show();
				}

			}
				break;
			}
		} catch (Exception e) {
			Log.i("result", e.toString());
		}
		return false;

	}

	public Bitmap getimage(String path) throws IOException {
		Bitmap bit = BitmapFactory.decodeFile(path);
		return bit;
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// Toast.makeText(this,
		// "选项菜单显示之前onPrepareOptionsMenu方法会被调用，你可以用此方法来根据打当时的情况调整菜单",
		// Toast.LENGTH_LONG).show();

		// 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用

		return true;

	}

	Calendar c = null;

	protected Dialog onCreateDialog(final Button databtn1) {
		Dialog dialog = null;

		c = Calendar.getInstance();
		dialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month,
							int dayOfMonth) {
						databtn1.setText(year + "年" + gets(month + 1) + "月"
								+ gets(dayOfMonth) + "日");
					}
				}, c.get(Calendar.YEAR), // 传入年份
				c.get(Calendar.MONTH), // 传入月份
				c.get(Calendar.DAY_OF_MONTH));// 传入天数

		return dialog;
	}

	public String gets(int d) {
		if (d < 10) {
			return "0" + d;
		}
		return d + "";

	}

	@Override
	public void onBackPressed() {
		// if(issend)
		// {
		// jssbactivity.doitem(bh,"待反馈", bb, qy+","+hd+","+dlwz, sj);
		// }
		super.onBackPressed();
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

	public ArrayList<String> getlist(CharSequence[] list1) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < list1.length; i++) {
			list.add((String) list1[i]);
		}
		return list;
	}

	public int getindex(CharSequence[] l, String g) {
		for (int i = 0; i < l.length; i++) {
			if (l[i].equals(g)) {
				return i;
			}
		}
		return 0;
	}

	public static String changeToWord(String str) {
		String retData = null;
		String tempStr = new String(str);
		String[] chStr = new String[str.length() / 4];
		for (int i = 0; i < str.length(); i++) {
			if (i % 4 == 3) {
				chStr[i / 4] = new String(tempStr.substring(0, 4));
				tempStr = tempStr.substring(4, tempStr.length());
			}
		}
		char[] retChar = new char[chStr.length];
		for (int i = 0; i < chStr.length; i++) {
			retChar[i] = (char) Integer.parseInt(chStr[i], 16);
		}
		retData = String.valueOf(retChar, 0, retChar.length);
		return retData;
	}

	class MyHandler extends Handler {

		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			{
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf,
						null);
				Cursor c = db.query(SETIP.TABLE_NAME, new String[] { SETIP.IP,
						SETIP.DK }, null, null, null, null, null);
				c.moveToFirst();
				String setip = c.getString(0);
				String setdk = c.getString(1);

				c.close();

				String surl = "http://" + setip + ":" + setdk
						+ "/androidserver/servlet/WGBGBDServlet";
				Map<String, String> par = new HashMap<String, String>();

				Cursor cc = db.query(User.TABLE_NAME, new String[] {
						User.USERNAME, User.USERID }, null, null, null, null,
						null);
				cc.moveToFirst();
				String name = cc.getString(0);
				int userid = cc.getInt(1);
				cc.close();
			
				par.put("sj", sj);
				par.put("bz", bz);
				par.put("userid", userid+"");
				par.put("xdy", name);
				String end = "\r\n";

				String twoHyphens = "--";

				String boundary = "*****";

				try {

					URL url = new URL(surl);

					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
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

					DataOutputStream ds = new DataOutputStream(
							con.getOutputStream());

					ds.writeBytes(twoHyphens + boundary + end);
					StringBuilder sb = new StringBuilder();
					for (Entry<String, String> entry : par.entrySet()) {// 构建表单字段内容

						sb.append(twoHyphens);

						sb.append(boundary);

						sb.append("\r\n");

						sb.append("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"\r\n\r\n");

						sb.append(entry.getValue());

						sb.append("\r\n");

					}

					ds.write(sb.toString().getBytes());
					if (listxp.size() < 1) {
						dialog.setProgress(90);
					} else {
						dialog.setProgress(5);
					}
					int size = listxp.size();
					int i1 = 0;

					for (int i = 0; i < fillMaps.size(); i++) {
						HashMap<String, String> gettable = fillMaps.get(i);

						String ele = gettable.get("image");
						int index = ele.lastIndexOf('/');
						String newName = ele.substring(index + 1);
						ds.writeBytes(twoHyphens + boundary + end);
						ds.writeBytes("Content-Disposition: form-data; "

						+ "name=\"file1\";filename=\"" + newName + "\"" + end);
						ds.writeBytes("Content-Type: application/octet-stream; charset=UTF-8"
								+ end);
						ds.writeBytes(end);
						FileInputStream fStream = new FileInputStream(ele);

						/* 设置每次写入1024bytes */
						int bytesAvailable = fStream.available();
						/* 设置每次写入1024bytes */

						int bufferSize = 1024;
						int ii = bytesAvailable / bufferSize;

						byte[] buffer = new byte[bufferSize];

						int length = 0;

						/* 从文件读取数据至缓冲区 */
						int iii = 0;
						while ((length = fStream.read(buffer)) != -1) {

							/* 将资料写入DataOutputStream中 */

							ds.write(buffer, 0, length);
							iii++;
							int i4 = (i1 - 1) * 90 / size + (iii * 90)
									/ (size * ii);

							dialog.setProgress(5 + i4);
						}

						ds.writeBytes(end);

						fStream.close();
					}
					ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
					ds.flush();
					dialog.setProgress(80);
					ds.close();
					/* 取得Response内容 */
					int kode = con.getResponseCode();
					Log.i("result", con.getResponseCode() + "");
					if (kode == 200) {
						InputStream is = con.getInputStream();

						int ch;

						StringBuffer b = new StringBuffer();

						while ((ch = is.read()) != -1) {

							b.append((char) ch);

						}
						String s = new String(b.toString().getBytes(), "utf-8");
						Log.i("result", "Response内容" + s);
						// bb="巡检"+s.substring(s.lastIndexOf(" "));
						bb = changeToWord(s);
						Log.i("result", "Response内容" + bb);

						fszt = "已发送";
						ContentValues values = new ContentValues();

						values.put(WGBG.BH, bb);
						values.put(WGBG.SJ, sj);
						values.put(WGBG.BZ, bz);
						values.put(WGBG.FSZT, 1);
						// int fszt=1;

						for (int i = 0; i < fillMaps.size(); i++) {
							HashMap<String, String> gettable = fillMaps.get(i);

							String ele = gettable.get("image");
							ContentValues imagevalues = new ContentValues();
							imagevalues.put(Image.BH, bb);
							imagevalues.put(Image.XP, ele);
							db.insert(Image.TABLE_NAME, null, imagevalues);
							//
						}
						dialog.setProgress(98);
						// ContentValues upvalues = new ContentValues();
						// upvalues.put(DFBG.FSZT, 1);
						String whereClause = WGBG.BH + " ='" + bh + "'";
						db.delete(Image.TABLE_NAME, whereClause, null);
						db.update(WGBG.TABLE_NAME, values, whereClause, null);
						con.disconnect();
						db.close();
						issend = true;
						dialog.setProgress(100);
						UPhandler.post(runnableUi1);
					}
					else
					{UPhandler.post(runnableUi);}
				} catch (Exception e) {
					Log.i("result", e.toString());
					UPhandler.post(runnableUi);
				}
				//

			}
			
			dialog.cancel();

		}

	}

	Runnable runnableUi = new Runnable() {

		public void run() {
		
			Toast.makeText(WgbgActivity.this, "发送失败！", Toast.LENGTH_SHORT)
					.show();
		}

	};

	Runnable runnableUi1 = new Runnable() {

		public void run() {
			ebh.setText(bb);
			WgbglistActivity.doitem( bh,"已发送",bb, sj);
			Toast.makeText(WgbgActivity.this, "发送成功！", Toast.LENGTH_SHORT)
					.show();
		}

	};

	public void setListViewHeightBasedOnChildren(ListView listView) {

		MyAdapter1 listAdapter = (MyAdapter1) listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			totalHeight += 92;
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;

		listView.setLayoutParams(params);

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

//			HashMap<String, String> map = mItemList.get(position);
//			String image = map.get("image");
//			String imagename = map.get("imagename");
			View view = super.getView(position, convertView, parent);
//			try {
//
//				ImageView xp = (ImageView) view.findViewById(R.id.xp);
//				TextView mc = (TextView) view.findViewById(R.id.xpmc);
//				Bitmap bitmap = BitmapFactory.decodeFile(image);
//				Bitmap smallbitmap = Bitmap.createScaledBitmap(bitmap, 60, 60,
//						true);
//				xp.setImageBitmap(smallbitmap);
//				mc.setText(imagename);
//			} catch (Exception e) {
//				Log.i("result", e.toString());
//			}
			return view;

		}
	}
}
