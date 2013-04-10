package Gis.namespace.Location;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import Gis.namespace.Location.GISLocationActivity.WGBG;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class WgbglistActivity extends Activity {

	String bh;
	int index;
	Button databtn1 = null;
	ListView listView;
	File dataf;

	public static TextView tv;
	public static TextView ebh;
	public static TextView exmgc;
	public static View listv;
	public static MyAdapter adapter1;
	public static List<HashMap<String, String>> fillMaps;
	public static HashMap<String, String> gettable;

	public Handler UPhandler;
	private ProgressDialog pd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			setContentView(R.layout.jssb);
			UPhandler = new Handler();
			fillMaps = new ArrayList<HashMap<String, String>>();

			dataf = new File("/sdcard/XClocalhost/zjdafh");
			SQLiteDatabase db = SQLiteDatabase
					.openOrCreateDatabase(dataf, null);
			String creatsql = "CREATE TABLE IF NOT EXISTS  " + WGBG.TABLE_NAME
					+ " (" + WGBG.ID + " INTEGER primary key autoincrement, "
					+ WGBG.BH + "  text, " + WGBG.BZ + "  text, " + WGBG.SJ
					+ "  text, " + WGBG.XP + "  text, " + WGBG.FSZT + "  text "
					+ ")";
			db.execSQL(creatsql);
			databtn1 = (Button) findViewById(R.id.xjbg);
			listView = (ListView) this.findViewById(R.id.sblist);
			String sql = "select  " + WGBG.BH + "," + WGBG.SJ + "  from "
					+ WGBG.TABLE_NAME + " where " + WGBG.FSZT
					+ "=0  order by bh asc";
			Cursor c = db.rawQuery(sql, null);
			c.moveToFirst();
			for (int i = 0; i < c.getCount(); i++) {
				gettable = new HashMap<String, String>();
				String bh = c.getString(0);
				String time = c.getString(1);
				String zt = "未发送";
				gettable.put("fszt", zt);
				gettable.put("bh", bh);
				gettable.put("time", time);
				fillMaps.add(gettable);
				c.moveToNext();
			}
			Cursor c3 = db.query(SETIP.TABLE_NAME, new String[] { SETIP.IP,
					SETIP.DK }, null, null, null, null, null);
			c3.moveToFirst();
			final String setip = c3.getString(0);
			final String setdk = c3.getString(1);
			Cursor c1 = db.query(User.TABLE_NAME, new String[] { User.USERID },
					null, null, null, null, null);
			c1.moveToFirst();
			final String username = c1.getInt(0) + "";
			c3.close();
			c1.close();
			db.close();
			pd = new ProgressDialog(WgbglistActivity.this);
			pd.setTitle("正在加载");
			pd.setMessage("请稍后！");
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			new Thread() {
				public void run() {
					HttpClient httpclient = new DefaultHttpClient();
					String url = "http://" + setip + ":" + setdk
							+ "/androidserver/servlet/WGBGServlet";
					HttpPost httppost = new HttpPost(url);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							1);
					// username=changeToUnicode(username);
					nameValuePairs.add(new BasicNameValuePair("userid",
							username));
					HttpResponse response;
					try {
						httppost.setEntity(new UrlEncodedFormEntity(
								nameValuePairs));

						response = httpclient.execute(httppost);
						if (response.getStatusLine().getStatusCode() == 200) {
						
							HttpEntity resEntity = response.getEntity();
							if (resEntity != null) {
								// System.out.println(EntityUtils.toString(resEntity,"utf-8"));
								StringEntity my_entity = new StringEntity(
										EntityUtils.toString(resEntity),
										"utf-8");
								String result = EntityUtils.toString(my_entity);
								JSONArray jsonArr = new JSONObject(result)
										.getJSONArray("values");
								for (int i = 0; i < jsonArr.length(); i++) {
									JSONObject jsonObj = (JSONObject) jsonArr
											.get(i);
									gettable = new HashMap<String, String>();
									String bh = jsonObj.getString("bh");
									String sj = jsonObj.getString("sj");
									gettable.put("bh", bh);
									gettable.put("time", sj);
									gettable.put("fszt", "已发送");
									fillMaps.add(gettable);
								}
							}
						} else {
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

			listView.setOnItemClickListener(new OnItemClickListener() {

				@SuppressWarnings("unchecked")
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					// View v= arg0.getChildAt(0);
					tv = (TextView) arg1.findViewById(R.id.user_id);
					ebh = (TextView) arg1.findViewById(R.id.user_bh);
					exmgc = (TextView) arg1.findViewById(R.id.user_gc);
					listv = arg1;
					HashMap<String, String> map = (HashMap<String, String>) WgbglistActivity.adapter1
							.getItem(arg2);

					bh = map.get("bh");
					String fszt = map.get("fszt");
					Intent intentwz = new Intent(WgbglistActivity.this,
							WgbgActivity.class);
					intentwz.putExtra("bh", bh);
					intentwz.putExtra("fszt", fszt);
					startActivity(intentwz);
				}

			});
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, String> map = (HashMap<String, String>) WgbglistActivity.adapter1
							.getItem(arg2);
					index = arg2;
					bh = map.get("bh");
					String fszt = map.get("fszt");
					if (fszt.equals("未发送")) {
						listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
							public void onCreateContextMenu(ContextMenu arg0,
									View arg1, ContextMenuInfo arg2) {
								// arg0.add(0,1,Menu.NONE,"打开");
								arg0.add(0, 1, Menu.NONE, "删除");
								// TODO Auto-generated method stub

							}
						});
						// }
					}
					return false;
				}

			});
			databtn1.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					try {
						String sql = "select max(bh) from dfbg where  bh like '%完%'";
						SQLiteDatabase db = SQLiteDatabase
								.openOrCreateDatabase(dataf, null);
						Cursor c = db.rawQuery(sql, null);
						c.moveToFirst();
						String newbh;
						String lbh = c.getString(0);
						Date date = new Date(System.currentTimeMillis());
						SimpleDateFormat Forma = new SimpleDateFormat(
								"yyyyMMdd-HHmmss");

						String time = Forma.format(date);

						String nf = time.substring(2, 4);
						if (lbh == null) {
							newbh = "完" + nf + "-1";
						} else {

							int i = Integer.parseInt(lbh.substring(4)) + 1;
							newbh = "完" + nf + "-" + i + "";
						}

						SimpleDateFormat Format = new SimpleDateFormat(
								"yyyy年MM月dd日");
						String sj = Format.format(date);
						ContentValues values = new ContentValues();

						values.put(WGBG.BH, newbh);
						values.put(WGBG.FSZT, 0);
						values.put(WGBG.SJ, sj);
						db.insert(WGBG.TABLE_NAME, null, values);
						db.close();
						c.close();
						gettable = new HashMap<String, String>();
						gettable.put("fszt", "未发送");
						gettable.put("bh", newbh);
						
						gettable.put("time", sj);
						fillMaps.add(gettable);
						adapter1.mItemList = fillMaps;
						adapter1.notifyDataSetChanged();
						Intent intentwz = new Intent(WgbglistActivity.this,
								WgbgActivity.class);
						intentwz.putExtra("bh", newbh);
						intentwz.putExtra("fszt", "未发送");
						startActivity(intentwz);
					} catch (Exception e) {
						e.printStackTrace();
						Log.i("HttpPost", e.getMessage());
					}

				}

			});
			if (ManageActivity.allActiviy.get("jssbactivity") != null) {
				ManageActivity
						.addActiviy("jssbactivity", WgbglistActivity.this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("HttpPost", e.getMessage());
		}

	}

	Runnable runnableUi = new Runnable() {

		public void run() {
			// 更新界面
			String[] from = new String[] { "fszt", "bh", "time" };
			int[] to = new int[] { R.id.user_id, R.id.user_bh, R.id.user_gc };
			adapter1 = new MyAdapter(WgbglistActivity.this, fillMaps,
					R.layout.listitem, from, to);
			listView.setAdapter(adapter1);
		}

	};

	
	public static void doitem(String bh, String fszt, String bb,
			String time) {
		HashMap<String, String> newitem1 = new HashMap<String, String>();

		for (int i = 0; i < fillMaps.size(); i++) {
			newitem1 = fillMaps.get(i);
			if (newitem1.get("bh").equals(bh)) {
				fillMaps.remove(i);
				break;
			}
		}
		HashMap<String, String> newitem = new HashMap<String, String>();
		newitem.put("fszt", fszt);
		newitem.put("bh", bb);
		
		newitem.put("time", time);
		fillMaps.add(newitem);

		adapter1.mItemList = fillMaps;
		adapter1.notifyDataSetChanged();
		// listView.refreshDrawableState();
	}

	@Override
	public boolean onContextItemSelected(MenuItem aItem) {
		try {
			switch (aItem.getItemId()) {
			case 1:

				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf,
						null);
				String where = "bh='" + bh + "'";
				db.delete(WGBG.TABLE_NAME, where, null);
				fillMaps.remove(index);
				adapter1.mItemList = fillMaps;
				adapter1.notifyDataSetChanged();
				db.close();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("HttpPost", e.getMessage());
		}
		return false;
	}

	public static void additem(String fszt, String bh, String time) {
		HashMap<String, String> newitem = new HashMap<String, String>();
		newitem.put("fszt", fszt);
		newitem.put("bh", bh);
		newitem.put("time", time);
		fillMaps.add(newitem);
		adapter1.mItemList = fillMaps;
		adapter1.notifyDataSetChanged();
		// listView.refreshDrawableState();
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
	protected void onStart() {
		super.onStart();

	}

	public static String changeToUnicode(String str) {
		StringBuffer strBuff = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String temp = Integer.toHexString(str.charAt(i));
			if (temp.length() != 4) {
				temp = "00" + temp;
			}
			if (temp.equals("00d")) {
				temp = "0" + temp;
			}
			if (temp.equals("00a")) {
				temp = "0" + temp;
			}
			strBuff.append(temp.substring(0, temp.length() - 2));
			strBuff.append(temp.substring(temp.length() - 2, temp.length()));
		}
		String returnData = strBuff.toString();
		return returnData;
	}

	public class MyAdapter extends SimpleAdapter {
		int count = 0;
		private List<HashMap<String, String>> mItemList;

		@SuppressWarnings("unchecked")
		public MyAdapter(Context context,
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
			HashMap<String, String> map = mItemList.get(position);
			String image = map.get("fszt");
			View view = super.getView(position, convertView, parent);

			return view;
		}
	}
}
