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
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
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
import Gis.namespace.Location.GISLocationActivity.WZBG;
import Gis.namespace.Location.jssbbdactivity.MyAdapter1;

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

@SuppressLint("SdCardPath")
public class wzsbbdactivity extends Activity {
	public static String path = null;
	private ProgressDialog dialog = null;
	String bb = null;
	Button btncarema;
	Button btnlocalhost;

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
	String qy = null;
	String hd = null;
	String ms = null;
	String yyfx = null;
	String jy = null;

	String xpsm = null;
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

	EditText expsm;
	String imagename;
	Button save;
	Button send;
	Spinner eqy;
	Spinner ehd;
	Button progress;
	EditText efkyj;
	ArrayList<String> listxp = new ArrayList<String>();
	ListView xplist;
	// SQLiteDatabase db;
	// DBConnection helper;
	String jtbh = null;
	String nf = null;
	File dataf;
	boolean issend = false;
	String fszt;
	CharSequence[] list1;
	CharSequence[] qylist;
	CharSequence[] hdlist;
	public static MyAdapter1 adapter;
	public static ArrayList<HashMap<String, String>> fillMaps;
	// DBConnection helper;
	LocationManager loc = null;
	Location GPSLocation = null;
	double x = 0;
	double y = 0;
	double locx;
	double locy;
	String gpstbzh;
	private Handler UPhandler;

	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			super.onCreate(savedInstanceState);
			UPhandler = new Handler();
			loc = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			View v = LayoutInflater.from(wzsbbdactivity.this).inflate(
					R.layout.main2, null);
			setContentView(v);
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
			TextView tv6 = (TextView) findViewById(R.id.xp);
			TextPaint tp6 = tv6.getPaint();
			tp6.setFakeBoldText(true);
			btncarema = (Button) findViewById(R.id.Carema);
			btnlocalhost = (Button) findViewById(R.id.localhost);

			esj = (Button) findViewById(R.id.esj);
			eycqkmc = (EditText) findViewById(R.id.eycqkmc);
			eyzcd = (EditText) findViewById(R.id.eyzcd);
			eycqkbw = (EditText) findViewById(R.id.eycqkbw);
			edlwz = (EditText) findViewById(R.id.edlwz);
			essxngc = (Spinner) findViewById(R.id.essxngc);
			etbzhfwl = (EditText) findViewById(R.id.etbzhfwl);
			etbzhfwr = (EditText) findViewById(R.id.etbzhfwr);
			egczhfwl = (EditText) findViewById(R.id.egczhfwl);
			egczhfwr = (EditText) findViewById(R.id.egczhfwr);
			eycdddmjgxs = (EditText) findViewById(R.id.eycdddmjgxs);
			ems = (EditText) findViewById(R.id.ems);
			eyyfx = (EditText) findViewById(R.id.eyyfx);
			ejy = (EditText) findViewById(R.id.ejy);
			xplist = (ListView) findViewById(R.id.xplist);
			ebh = (TextView) findViewById(R.id.ebh);
			eqy = (Spinner) findViewById(R.id.eqy);
			ehd = (Spinner) findViewById(R.id.ehd);
			efkyj = (EditText) findViewById(R.id.efkyj);
			save = (Button) findViewById(R.id.save);
			send = (Button) findViewById(R.id.send);
			progress = (Button) findViewById(R.id.pro);
			// ehd.getBackground().setAlpha(0);
			// eqy.getBackground().setAlpha(0);
			// essxngc.getBackground().setAlpha(0);
			list1 = new CharSequence[36];
			list1[0] = " ";
			list1[35] = "大坦沙岛北半岛段";
			list1[1] = "大坦沙岛市第一职工商学院至珠江大桥西侧工程";
			list1[2] = "大坦沙岛增加段";
			list1[3] = "东涌水闸至广州南海";
			list1[4] = "河南港务内一码头至洛溪大桥(第七期)";
			list1[5] = "前航道海心沙岛段一期";
			list1[6] = "澳口涌至石井水泥厂";
			list1[7] = "新风港码头至澳口涌(第八期)";
			list1[8] = "第五期";
			list1[9] = "第六期";
			list1[10] = "下市涌至省五矿码头";
			list1[11] = "洛溪大桥至海军码头";
			list1[12] = "海印公园";
			list1[13] = "中大码头";
			list1[14] = "航海俱乐部";
			list1[15] = "黄涌口至中大码头(第三期)";
			list1[16] = "海运局面码头至黄涌(第二期)";
			list1[17] = "南方面粉厂至原人民制革厂";
			list1[18] = "原人民制革厂至黄埔港段";
			list1[19] = "第四期";
			list1[20] = "第九期";
			list1[21] = "海心沙二期";
			list1[22] = "前航道珠岛宾馆西堤岸段";
			list1[23] = "第一期工程";
			list1[24] = "前航道省农资公司码头段";
			list1[25] = "前航道鱼珠段工程";
			list1[26] = "新洲水电公司预制场码头段工程";
			list1[27] = "前航右岸道中大码头至游艇会缺口段";
			list1[28] = "新中国造船厂缺口段";
			list1[29] = "二沙涌堤岸改造及绿化整治工程";
			list1[30] = "后航道马涌西水闸出口段工程";
			list1[31] = "后航道左岸新光大桥桥头公园段";
			list1[32] = "广州大学城浪损堤防修复一期工程";
			list1[33] = "洲头咀公园至永兴果品市场短维护工程";
			list1[34] = "洲头咀至江湾大桥段及人民桥至天字码头段";

			qylist = new CharSequence[10];
			qylist[0] = "天河区";
			qylist[1] = "越秀区";
			qylist[2] = "荔湾区";
			qylist[3] = "海珠区";
			qylist[4] = "白云区";
			qylist[5] = "黄埔区";
			qylist[6] = "番禺区";
			qylist[7] = "花都区";
			qylist[8] = "南沙区";
			qylist[9] = "萝岗区";

			hdlist = new CharSequence[12];
			hdlist[0] = "后航道后右";
			hdlist[1] = "后航道后左";
			hdlist[2] = "前航道二沙右";
			hdlist[3] = "前航道二沙左";
			hdlist[4] = "前航道前右";
			hdlist[5] = "前航道前左";
			hdlist[6] = "前航道海心沙";
			hdlist[7] = "西航道坦东";
			hdlist[8] = "西航道坦西";
			hdlist[9] = "西航道西右";
			hdlist[10] = "西航道西左";
			hdlist[11] = "西航道金沙洲";
			fillMaps = new ArrayList<HashMap<String, String>>();
			essxngc.setAdapter(new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item, list1));
			eqy.setAdapter(new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item, qylist));
			ehd.setAdapter(new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item, hdlist));
			dataf = new File("/sdcard/XClocalhost/zjdafh");
			expsm = (EditText) findViewById(R.id.expsm);
			Intent i = getIntent();
			String ssbh = i.getStringExtra("bh");
			fszt = i.getStringExtra("fszt");
			String fkyj = i.getStringExtra("fkyj");
			SQLiteDatabase db = SQLiteDatabase
					.openOrCreateDatabase(dataf, null);
			String whereClause1 = "bh='" + ssbh + "'";
			Cursor cur = null;
			cur = db.query(WZBG.TABLE_NAME, new String[] { DFBG.BH, DFBG.TIME,
					DFBG.YCQKMC, DFBG.YZCD, DFBG.YCQKBW, DFBG.DLWZ, DFBG.SSGC,
					DFBG.TBZHFWL, DFBG.TBZHFWR, DFBG.GCZHFWL, DFBG.GCZHFWR,
					DFBG.YCDDDMJGXS, DFBG.MS, DFBG.YYFX, DFBG.JY, DFBG.XPSM,
					DFBG.QY, DFBG.HD }, whereClause1, null, null, null, null);
			cur.moveToFirst();
			if (cur.getCount() > 0) {
				ebh.setText(cur.getString(0));
				esj.setText(cur.getString(1));
				eyzcd.setText(cur.getString(3));
				eycqkmc.setText(cur.getString(2));
				eycqkbw.setText(cur.getString(4));
				edlwz.setText(cur.getString(5));
				gpstbzh = cur.getString(7);
				essxngc.setSelection(getindex(list1, cur.getString(6)));
				etbzhfwl.setText(cur.getString(7));
				etbzhfwr.setText(cur.getString(8));
				egczhfwl.setText(cur.getString(9));
				egczhfwr.setText(cur.getString(10));
				eycdddmjgxs.setText(cur.getString(11));
				ems.setText(cur.getString(12));
				eyyfx.setText(cur.getString(13));
				ejy.setText(cur.getString(14));
				expsm.setText(cur.getString(15));
				eqy.setSelection(getindex(qylist, cur.getString(16)));
				ehd.setSelection(getindex(hdlist, cur.getString(17)));
				efkyj.setText(fkyj);
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

			String[] from = new String[] {  "imagename" };
			int[] to = new int[] {  R.id.xpmc };
			adapter = new MyAdapter1(wzsbbdactivity.this, fillMaps,
					R.layout.xplistitem, from, to);
			xplist.setAdapter(adapter);
			setListViewHeightBasedOnChildren(xplist);
			if (gpstbzh == null) {
				loc = (LocationManager) this
						.getSystemService(Context.LOCATION_SERVICE);
				GPSLocation = loc
						.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (GPSLocation == null) {
					GPSLocation = loc
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
				if (GPSLocation != null) {
					Point ptLatLon = new Point(GPSLocation.getLongitude(),
							GPSLocation.getLatitude());
					// 经纬度坐标
					SpatialReference sr4326 = SpatialReference.create(4326);
					SpatialReference sr2383 = SpatialReference.create(2383);
					;
					Point centerPt = (Point) GeometryEngine.project(ptLatLon,
							sr4326, sr2383);
					locx = centerPt.getX();
					locy = centerPt.getY();
					File tbzhf = new File("/sdcard/XClocalhost/tbzh");
					String where = TBZH.X + ">'" + (locx - 100) + "' and "
							+ TBZH.X + "<'" + (locx + 100) + "' and " + TBZH.Y
							+ ">'" + (locy - 100) + "' and " + TBZH.Y + "<'"
							+ (locy + 100) + "'";
					SQLiteDatabase ptdb = SQLiteDatabase.openOrCreateDatabase(
							tbzhf, null);
					Cursor ptc = ptdb.query(TBZH.TABLE_NAME, new String[] {
							TBZH.ZH, "x", "y" }, where, null, null, null, null);
					double jl = 0;
					String zjtbzh = " ";
					ptc.moveToFirst();
					for (int zji = 0; zji < ptc.getCount(); zji++) {
						if (jl == 0) {
							jl = (locx - Double.valueOf(ptc.getString(1)))
									* (locx - Double.valueOf(ptc.getString(1)))
									+ (locy - Double.valueOf(ptc.getString(2)))
									* (locy - Double.valueOf(ptc.getString(2)));
							zjtbzh = ptc.getString(0);
						} else {
							double jl1 = (locx - Double.valueOf(ptc
									.getString(1)))
									* (locx - Double.valueOf(ptc.getString(1)))
									+ (locy - Double.valueOf(ptc.getString(2)))
									* (locy - Double.valueOf(ptc.getString(2)));
							if (jl1 < jl) {
								jl = jl1;
								zjtbzh = ptc.getString(0);
							}
						}
						ptc.moveToNext();
					}
					etbzhfwl.setText(zjtbzh);
				}

			}
			this.xplist.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					HashMap<String, String> map = (HashMap<String, String>) adapter
							.getItem(arg2);

					String image = map.get("image");

					Intent intent = new Intent(Intent.ACTION_VIEW);

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
							TextView tex = new TextView(wzsbbdactivity.this);
							tex.setText(image);
							tex.setTextSize(15);

							// tex.setTextColor(R.color.txtcc);
							tex.setGravity(Gravity.CENTER);
							AlertDialog.Builder builder = new AlertDialog.Builder(
									wzsbbdactivity.this);
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
					// TODO Auto-generated method stub
					// sbactivity.doitem(bh, "待反馈", bb,
					// qy + "," + hd + "," + dlwz, sj);
					wzsbbdactivity.this.finish();
				}
			});
			if (ManageActivity.allActiviy.get("jssbbdactivity") == null) {
				ManageActivity
						.addActiviy("jssbbdactivity", wzsbbdactivity.this);
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
			GPSLocation = loc
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (GPSLocation != null) {
				Point ptLatLon = new Point(GPSLocation.getLongitude(),
						GPSLocation.getLatitude());
				// 经纬度坐标
				SpatialReference sr4326 = SpatialReference.create(4326);
				SpatialReference sr2383 = SpatialReference.create(2383);
				// 坐标转换

				Point centerPt = (Point) GeometryEngine.project(ptLatLon,
						sr4326, sr2383);
				x = centerPt.getX();
				y = centerPt.getY();
			}

			bh = ebh.getText().toString();
			sj = esj.getText().toString();
			ycqkmc = eycqkmc.getText().toString();
			yzcd = eyzcd.getText().toString();
			ycqkbw = eycqkbw.getText().toString();
			dlwz = edlwz.getText().toString();
			ssxngc = essxngc.getSelectedItem().toString();
			tbzhfwl = etbzhfwl.getText().toString();
			tbzhfwr = etbzhfwr.getText().toString();
			gczhfwl = egczhfwl.getText().toString();
			gczhfwr = egczhfwr.getText().toString();
			ycdddmjgxs = eycdddmjgxs.getText().toString();
			ms = ems.getText().toString();
			yyfx = eyyfx.getText().toString();
			jy = ejy.getText().toString();

			xpsm = expsm.getText().toString();
			qy = eqy.getSelectedItem().toString();
			hd = ehd.getSelectedItem().toString();
			// String surl=ConfigManager.getInstance().getString("DFBGServlet");
			if (bh == null || bh == "" || bh.equals("")) {
				Toast.makeText(this, "编号不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			switch (item) {

			case 1:
				try {
					if (fszt.equals("已发送")) {
						MessageBox.createAlertDialog("提示", "此编号已发送过！", "返回",
								this).show();
						return false;
					}

					SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
							dataf, null);

					ContentValues values = new ContentValues();

					values.put(DFBG.BH, bh);
					values.put(DFBG.TIME, sj);
					values.put(DFBG.YCQKMC, ycqkmc);
					values.put(DFBG.YZCD, yzcd);
					values.put(DFBG.YCQKBW, ycqkbw);
					values.put(DFBG.DLWZ, dlwz);
					values.put(DFBG.SSGC, ssxngc);
					values.put(DFBG.TBZHFWL, tbzhfwl);
					values.put(DFBG.TBZHFWR, tbzhfwr);
					values.put(DFBG.GCZHFWL, gczhfwl);
					values.put(DFBG.GCZHFWR, gczhfwr);
					values.put(DFBG.YCDDDMJGXS, ycdddmjgxs);
					values.put(DFBG.MS, ms);
					values.put(DFBG.YYFX, yyfx);
					values.put(DFBG.JY, jy);
					// values.put(DFBG.XDY,LoginActivity.UserName);

					values.put(DFBG.XPSM, xpsm);
					values.put(DFBG.QY, qy);
					values.put(DFBG.HD, hd);
					values.put(DFBG.X, x);
					values.put(DFBG.Y, y);

					String whereClause = DFBG.BH + "='" + bh + "'";

					db.update(WZBG.TABLE_NAME, values, whereClause, null);

					if (listxp.size() > 0) {
						// SQLiteDatabase db=
						// SQLiteDatabase.openOrCreateDatabase(LoginActivity.dataf,
						// null);
						String where = Image.BH + "='" + bh + "'";
						db.delete(Image.TABLE_NAME, where, null);

					}
					for (int i = 0; i < fillMaps.size(); i++) {
						HashMap<String, String> gettable = fillMaps.get(i);
						// db=
						// SQLiteDatabase.openOrCreateDatabase(LoginActivity.dataf,
						// null);
						String ele = gettable.get("image");
						ContentValues imagevalues = new ContentValues();
						imagevalues.put(Image.BH, bh);
						imagevalues.put(Image.XP, ele);
						db.insert(Image.TABLE_NAME, null, imagevalues);

					}
					db.close();
					Toast.makeText(wzsbbdactivity.this, "保存成功！",
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
				// if(ycqkmc==null||ycqkmc==""||ycqkmc.equals(""))
				// {
				// Toast.makeText(this, "异常情况名称不能为空！",
				// Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(yzcd==null||yzcd==""||yzcd.equals(""))
				// {
				// Toast.makeText(this, "严重程度不能为空！", Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(ycqkbw==null||ycqkbw==""||ycqkbw.equals(""))
				// {
				// Toast.makeText(this, "异常情况部位不能为空！",
				// Toast.LENGTH_LONG).show();
				// return false;
				// }
				if (dlwz == null || dlwz == "" || dlwz.equals("")) {
					Toast.makeText(this, "地理位置不能为空！", Toast.LENGTH_LONG).show();
					return false;
				}
				// if(ssxngc==null||ssxngc==""||ssxngc.equals(""))
				// {
				// Toast.makeText(this, "所属项目工程不能为空！",
				// Toast.LENGTH_LONG).show();
				// return false;
				// }
				if (tbzhfwl == null || tbzhfwl == "" || tbzhfwl.equals("")) {
					Toast.makeText(this, "统编桩号不能为空！", Toast.LENGTH_LONG).show();
					return false;
				}
				// if(tbzhfwr==null||tbzhfwr==""||tbzhfwr.equals(""))
				// {
				// Toast.makeText(this, "统编桩号终止范围不能为空！",
				// Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(ycdddmjgxs==null||ycdddmjgxs==""||ycdddmjgxs.equals(""))
				// {
				// Toast.makeText(this, "异常堤段断面结构形式不能为空！",
				// Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(ms==null||ms==""||ms.equals(""))
				// {
				// Toast.makeText(this, "描述不能为空！", Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(yyfx==null||yyfx==""||yyfx.equals(""))
				// {
				// Toast.makeText(this, "原因分析不能为空！", Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(jy==null||jy==""||jy.equals(""))
				// {
				// Toast.makeText(this, "建议不能为空！", Toast.LENGTH_LONG).show();
				// return false;
				// }
				// if(xpsm==null||xpsm==""||xpsm.equals(""))
				// {
				// Toast.makeText(this, "巡堤组长不能为空！", Toast.LENGTH_LONG).show();
				// return false;
				// }

				if (fszt.equals("未发送")) {

					HandlerThread handlerThread1 = new HandlerThread(
							"handler_thread");
					handlerThread1.start();

					MyHandler handler1 = new MyHandler(
							handlerThread1.getLooper());
					Message msg = handler1.obtainMessage();
					handler1.sendMessage(msg);
					dialog = new ProgressDialog(wzsbbdactivity.this);
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
		// File f=new File(path);
		// OutputStream out=new FileOutputStream(f);
		// byte[] bb =null;
		// out.write(bb);
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
		// if (issend) {
		// sbactivity.doitem(bh, "待反馈", bb, qy + "," + hd + "," + dlwz, sj);
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

	public int getindex(CharSequence[] l, String g) {
		for (int i = 0; i < l.length; i++) {
			if (l[i].equals(g)) {
				return i;
			}
		}
		return 0;
	}

	Runnable runnableUi1 = new Runnable() {

		public void run() {
			ebh.setText(bb);
			sbactivity.doitem(bh, "待反馈", bb, qy + "," + hd + "," + dlwz, sj);
			Toast.makeText(wzsbbdactivity.this, "发送成功！", Toast.LENGTH_SHORT)
					.show();
		}

	};
	Runnable runnableUi = new Runnable() {

		public void run() {
		
			Toast.makeText(wzsbbdactivity.this, "发送失败！", Toast.LENGTH_SHORT)
					.show();
		}

	};
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
						+ "/androidserver/servlet/JSWZServlet";
				Map<String, String> par = new HashMap<String, String>();

				Cursor cc = db.query(User.TABLE_NAME, new String[] {
						User.USERNAME, User.USERID }, null, null, null, null,
						null);
				cc.moveToFirst();
				String name = cc.getString(0);
				int userid = cc.getInt(1);
				cc.close();
				par.put("isfirst", "true");
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
				par.put("userid", userid + "");
				par.put("xdy", name);
				par.put("qy", qy);
				par.put("hd", hd);
				// par.put("xp3", xp3.substring(xp3.lastIndexOf('/')+1));
				// par.put("xp4",xp4.substring(xp4.lastIndexOf('/')+1));
				// par.put("xp5", xp5.substring(xp5.lastIndexOf('/')+1));
				par.put("xpsm", xpsm);
				par.put("x", x + "");
				par.put("y", y + "");
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
					dialog.setProgress(95);
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
						String s = changeToWord(new String(b.toString()
								.getBytes(), "utf-8"));
						Log.i("result", "Response内容" + s);
						// bb="巡检"+s.substring(s.lastIndexOf(" "));
						bb = s;
						Log.i("result", "Response内容" + bb);

						fszt = "已发送";
						ContentValues values = new ContentValues();

						values.put(DFBG.BH, bb);
						values.put(DFBG.TIME, sj);
						values.put(DFBG.YCQKMC, ycqkmc);
						values.put(DFBG.YZCD, yzcd);
						values.put(DFBG.YCQKBW, ycqkbw);
						values.put(DFBG.DLWZ, dlwz);
						values.put(DFBG.SSGC, ssxngc);
						values.put(DFBG.TBZHFWL, tbzhfwl);
						values.put(DFBG.TBZHFWR, tbzhfwr);
						values.put(DFBG.GCZHFWL, gczhfwl);
						values.put(DFBG.GCZHFWR, gczhfwr);
						values.put(DFBG.YCDDDMJGXS, ycdddmjgxs);
						values.put(DFBG.MS, ms);
						values.put(DFBG.YYFX, yyfx);
						values.put(DFBG.JY, jy);
						values.put(DFBG.QY, qy);
						values.put(DFBG.HD, hd);
						// values.put(DFBG.XDY,LoginActivity.UserName);
						values.put(DFBG.X, x);
						values.put(DFBG.Y, y);
						values.put(DFBG.XPSM, xpsm);
						values.put(DFBG.FSZT, 1);
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
						String whereClause = DFBG.BH + " ='" + bh + "'";
						// db.delete(Image.TABLE_NAME, whereClause, null);
						db.update(WZBG.TABLE_NAME, values, whereClause, null);
						con.disconnect();
						// // db=
						// SQLiteDatabase.openOrCreateDatabase(LoginActivity.dataf,
						// null);
						// // db.insert(DFBG.TABLENAME, null, values);
						// if(nf!=null)
						// {
						// ContentValues xjbh = new ContentValues();
						// xjbh.put(XJBH.NF, nf);
						// xjbh.put(XJBH.BH, jtbh);
						// db.insert(XJBH.TABLE_NAME, null, xjbh);
						// }
						db.close();
						issend = true;


						dialog.setProgress(100);
						UPhandler.post(runnableUi1);
					}
					else
					{
						UPhandler.post(runnableUi);
					}
				} catch (Exception e) {
					Log.i("result", e.toString());
					UPhandler.post(runnableUi);
				}
				//

			}
			
			dialog.cancel();

		}

	}

	public void setListViewHeightBasedOnChildren(ListView listView) {

		MyAdapter1 listAdapter = (MyAdapter1) listView.getAdapter();

		if (listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItem = listAdapter.getView(i, null, listView);

			listItem.measure(0, 1);

			// totalHeight += listItem.getMeasuredHeight();
			totalHeight += 90;
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight;

		// params.height = totalHeight + (listView.getDividerHeight() *
		// (listAdapter.getCount() - 1));

		// ((MarginLayoutParams)params).setMargins(10, 10, 10, 10);

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
//				Bitmap smallbitmap = Bitmap.createScaledBitmap(bitmap, 6, 6,
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
