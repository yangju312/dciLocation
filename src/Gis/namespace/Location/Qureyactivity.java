package Gis.namespace.Location;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import Gis.namespace.Location.GISLocationActivity.DFBG;
import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.User;
import Gis.namespace.Location.GISLocationActivity.WZBG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class Qureyactivity extends Activity {

	Spinner spbg;
	Spinner eqy;
	Spinner ehd;
	Spinner excy;
	CharSequence[] qylist;
	CharSequence[] hdlist;
	CharSequence[] xcylist;
	File dataf;
	// DBConnection helper;
	Button databtn1 = null;
	Button databtn2 = null;
	Button btncx = null;
	public static ArrayList<HashMap<String, String>> fillMaps;
	public static HashMap<String, String> gettable;
	public static HashMap<String, String> xcid;
	Calendar c = null;
	int index = 0;
	View v;
	public String setip;
	public String setdk;
	 private ProgressDialog pBar;
	    private ProgressDialog pd;
	    private Handler UPhandler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query);
		UPhandler=new Handler();
		spbg = (Spinner) findViewById(R.id.spinner1);
//		spbg.getBackground().setAlpha(0);
		eqy = (Spinner) findViewById(R.id.spinner2);
//		eqy.getBackground().setAlpha(0);
		ehd = (Spinner) findViewById(R.id.xchd);
		excy = (Spinner) findViewById(R.id.spinnerxcy);
//		ehd.getBackground().setAlpha(0);
		dataf = new File("/sdcard/XClocalhost/zjdafh");
		SQLiteDatabase xcdb = SQLiteDatabase.openOrCreateDatabase(dataf, null);
		databtn1 = (Button) findViewById(R.id.databtn1);
		databtn2 = (Button) findViewById(R.id.databtn2);
		btncx = (Button) findViewById(R.id.cx);
		try {
			Cursor c1 = xcdb.query(SETIP.TABLE_NAME, new String[] { SETIP.IP,
					SETIP.DK }, null, null, null, null, null);

			if (c1.getCount() > 0) {
				c1.moveToFirst();
				setip = c1.getString(0);
				setdk = c1.getString(1);
			}
			c1.close();
			xcdb.close();
			CharSequence[] list1 = new CharSequence[4];
			list1[0] = "���б���";
			list1[1] = "�ճ�Ѳ�鱨��";
			list1[2] = "Υ�½��豨��";
			list1[3] = "�깤����";
			qylist = new CharSequence[13];
			qylist[0] = "��������";
			qylist[1] = "�����";
			qylist[2] = "Խ����";
			qylist[3] = "������";
			qylist[4] = "������";
			qylist[12] = "������";
			qylist[5] = "������";
			qylist[6] = "��خ��";
			qylist[7] = "������";
			qylist[8] = "��ɳ��";
			qylist[9] = "�ܸ���";
			qylist[10] = "������";
			qylist[11] = "�ӻ���";
			hdlist = new CharSequence[13];
			hdlist[0] = "���к���";
			hdlist[1] = "�󺽵�����";
			hdlist[2] = "�󺽵�����";
			hdlist[3] = "ǰ������ɳ��";
			hdlist[4] = "ǰ������ɳ��";
			hdlist[6] = "ǰ����ǰ��";
			hdlist[5] = "ǰ����ǰ��";
			hdlist[7] = "ǰ��������ɳ";
			hdlist[9] = "������̹��";
			hdlist[8] = "������̹��";
			hdlist[11] = "����������";
			hdlist[10] = "����������";
			hdlist[12] = "��������ɳ��";

			eqy.setAdapter(new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item, qylist));
			ehd.setAdapter(new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item, hdlist));
			spbg.setAdapter(new ArrayAdapter<CharSequence>(this,
					android.R.layout.simple_spinner_item, list1));
			
			final String url = "http://" + setip + ":" + setdk
					+ "/androidserver/servlet/SearchServlet";
			pd = new ProgressDialog(Qureyactivity.this);
			pd.setTitle("���ڼ���");
			pd.setMessage("���Ժ�");
			pd.setCanceledOnTouchOutside(false);
			pd.show();
			new Thread()
			{
				public void run(){
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("lx", "user"));
					HttpResponse response;
					try {
						httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						httpclient.getParams().setParameter(
								CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
						// ����ʱ
						httpclient.getParams().setParameter(
								CoreConnectionPNames.SO_TIMEOUT, 30000);
						response = httpclient.execute(httppost);
						if (response.getStatusLine().getStatusCode() == 200) {
							// ��ȡ���ص�����

							HttpEntity resEntity = response.getEntity();
							if (resEntity != null) {
								StringEntity my_entity = new StringEntity(
										EntityUtils.toString(resEntity), "utf-8");
								String result = EntityUtils.toString(my_entity);
								JSONArray jsonArr = new JSONObject(result)
										.getJSONArray("result");
								xcylist = new CharSequence[jsonArr.length()+1];
								xcylist[0]="����Ѳ��Ա";
								xcid=new HashMap<String, String>();
								for (int i = 0; i < jsonArr.length(); i++) {
									JSONObject jsonObj = (JSONObject) jsonArr.get(i);
									String name=jsonObj.getString("name");
									int id=jsonObj.getInt("id");
									xcylist[i+1]=name;
									xcid.put(name, id+"");
								}

							}

						} else {
							Log.i("HttpPost", "HttpPost��ʽ����ʧ��");
						}

					} catch (Exception e) {
						Log.i("HttpPost", e.getMessage());
					}
					UPhandler.post(runnableUi);
					pd.cancel();
				}}.start();
			
			
			databtn1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					onCreateDialog(databtn1).show();
				}
			});
			databtn2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					onCreateDialog(databtn2).show();
				}
			});

			btncx.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					SimpleDateFormat Format = new SimpleDateFormat(
							"yyyy��MM��dd��");
					index = 0;
					String xcy=excy.getSelectedItem().toString();
					String qy = eqy.getSelectedItem().toString();
					String hd = ehd.getSelectedItem().toString();
					final String bg = spbg.getSelectedItem().toString();
					String sdate = databtn1.getText().toString();
					String edate = databtn2.getText().toString();
					// ArrayList<HashMap<String, String>> fillMaps = new
					// ArrayList<HashMap<String, String>>();
					fillMaps = new ArrayList<HashMap<String, String>>();
					
					try {
						final String where = getwhere(xcy,qy, hd, sdate, edate);
						final String wherewg = getwherewg(xcy,qy, hd, sdate, edate);
						if (fillMaps.size() > 0) {
							fillMaps.clear();
						}
						
						if (!sdate.equals("ѡ��ʱ��") && !edate.equals("ѡ��ʱ��")) {
							if (Format.parse(sdate).after(Format.parse(edate))) {
								Toast.makeText(Qureyactivity.this,
										"����ʱ�䲻��С�ڿ�ʼʱ�䣡", Toast.LENGTH_SHORT)
										.show();
								return;
							}
						}
//						if (bg.equals("���б���")) {
//							doquery1(where, fillMaps);
//							doquery2(where, fillMaps);
//						} else if (bg.equals("�ճ�Ѳ�鱨��")) {
//							doquery1(where, fillMaps);
//						} else if (bg.equals("Υ�½��豨��")) {
//							doquery2(where, fillMaps);
//						} else if (bg.equals("�깤����")) {
//
//						}
						pBar = new ProgressDialog(Qureyactivity.this);
						pBar.setTitle("���ڼ���");
						pBar.setMessage("���Ժ�");
						pBar.setCanceledOnTouchOutside(false);
						pBar.show();
						new Thread()
						{
							public void run(){
								try{
						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(url);
						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
						nameValuePairs.add(new BasicNameValuePair("lx", "xc"));
						nameValuePairs.add(new BasicNameValuePair("bglx",changeToUnicode(bg)));
						nameValuePairs.add(new BasicNameValuePair("where", changeToUnicode(where)));
						nameValuePairs.add(new BasicNameValuePair("wherewg", changeToUnicode(wherewg)));
						HttpResponse response;
						
							httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
							httpclient.getParams().setParameter(
									CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
							// ����ʱ
							httpclient.getParams().setParameter(
									CoreConnectionPNames.SO_TIMEOUT, 30000);
							response = httpclient.execute(httppost);
							if (response.getStatusLine().getStatusCode() == 200) {
								// ��ȡ���ص�����

								HttpEntity resEntity = response.getEntity();
								if (resEntity != null) {
									StringEntity my_entity = new StringEntity(
											EntityUtils.toString(resEntity), "utf-8");
									String result = EntityUtils.toString(my_entity);
									JSONArray jsonArr = new JSONObject(result)
											.getJSONArray("result");
									
								
									for (int i = 0; i < jsonArr.length(); i++) {
										JSONObject jsonObj = (JSONObject) jsonArr.get(i);
										gettable = new HashMap<String, String>();
										gettable.put("name", jsonObj.getString("name"));
										gettable.put("bh", jsonObj.getString("bh"));
										gettable.put("sj", jsonObj.getString("sj"));
										gettable.put("wz", jsonObj.getString("wz"));
										fillMaps.add(gettable);
									}

								}

							} else {
								Log.i("HttpPost", "HttpPost��ʽ����ʧ��");
							}
							Intent intentwz = new Intent(Qureyactivity.this,
									Resultactivity.class);
							startActivity(intentwz);
							pBar.cancel();
								} catch (Exception e) {
									e.printStackTrace();
									Log.i("HttpPost", e.getMessage());
								}
							}}.start();
						
					} catch (Exception e) {
						e.printStackTrace();
						Log.i("HttpPost", e.getMessage());
					}
						
					
					
				}
						
			});
			if (ManageActivity.allActiviy.get("Qureyactivity") != null) {
				ManageActivity.addActiviy("Qureyactivity", Qureyactivity.this);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("HttpPost", e.getMessage());
		}

	}
	Runnable   runnableUi=new  Runnable(){  
        
        public void run() {  
            //���½���  
        	excy.setAdapter(new ArrayAdapter<CharSequence>(Qureyactivity.this,
					android.R.layout.simple_spinner_item, xcylist));
        }  
          
    };  
	protected Dialog onCreateDialog(final Button databtn1) {
		Dialog dialog = null;

		c = Calendar.getInstance();
		dialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					public void onDateSet(DatePicker dp, int year, int month,
							int dayOfMonth) {
						databtn1.setText(year + "��" + gets(month + 1) + "��"
								+ gets(dayOfMonth) + "��");
					}
				}, c.get(Calendar.YEAR), // �������
				c.get(Calendar.MONTH), // �����·�
				c.get(Calendar.DAY_OF_MONTH));// ��������

		return dialog;
	}

	public String gets(int d) {
		if (d < 10) {
			return "0" + d;
		}
		return d + "";

	}
	public String gets(String date) {
		
		return date.substring(0, 4)+"-"+date.substring(5, 7)+"-"+date.substring(8, 10);

	}
	public void doquery1(String whereClause,
			List<HashMap<String, String>> fillMaps) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null);

		Cursor c2 = db.query(DFBG.TABLE_NAME, new String[] { DFBG.BH, DFBG.QY,
				DFBG.HD, DFBG.DLWZ, DFBG.TIME }, whereClause, null, null, null,
				null);

		putstring(c2, fillMaps, DFBG.TABLE_NAME);
		db.close();
		c2.close();
	}

	public void doquery2(String whereClause,
			List<HashMap<String, String>> fillMaps) {
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf, null);
		Cursor c1 = db.query(WZBG.TABLE_NAME, new String[] { DFBG.BH, DFBG.QY,
				DFBG.HD, DFBG.DLWZ, DFBG.TIME }, whereClause, null, null, null,
				null);
		putstring(c1, fillMaps, WZBG.TABLE_NAME);
		db.close();
		c1.close();

	}

	public String getwhere(String xcy,String qy, String hd, String sdate, String edate) {
		String where = null;
		if (sdate.equals("ѡ��ʱ��")) {
			sdate = "2012��01��11��";
		}
		if (edate.equals("ѡ��ʱ��")) {
			edate = "2099��12��30��";
		}
		where = "to_char(sj,'yyyy-MM-dd')" + ">='" + gets(sdate) + "' and  " + "to_char(sj,'yyyy-MM-dd')" + "<='"
				+ gets(edate) + "' ";
		if(!xcy.equals("����Ѳ��Ա"))
		{
			where=where+" and " + "userid"+ "=" + Integer.parseInt(xcid.get(xcy)) +" ";
		}
		if (!qy.equals("��������")) {
			where = where + " and " + DFBG.QY + "='" + qy + "'";
		}
		if (!hd.equals("���к���")) {
			where = where + " and " + DFBG.HD + "='" + hd + "'";
		}
		return where;
	}
	public String getwherewg(String xcy,String qy, String hd, String sdate, String edate) {
		String where = null;
		if (sdate.equals("ѡ��ʱ��")) {
			sdate = "2012��01��11��";
		}
		if (edate.equals("ѡ��ʱ��")) {
			edate = "2099��12��30��";
		}
		where = "to_char(sj,'yyyy-MM-dd')" + ">='" + gets(sdate) + "' and  " + "to_char(sj,'yyyy-MM-dd')" + "<='"
				+ gets(edate) + "' ";
		if(!xcy.equals("����Ѳ��Ա"))
		{
			where=where+" and " + "userid"+ "=" + Integer.parseInt(xcid.get(xcy)) +" ";
		}
		return where;
	}
	public void putstring(Cursor c1, List<HashMap<String, String>> fillMaps,
			String table) {
		c1.moveToFirst();

		for (int i = 0; i < c1.getCount(); i++) {
			index++;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", "" + index);
			map.put("bh", c1.getString(0));
			map.put("gc",
					c1.getString(1) + "," + c1.getString(2) + ","
							+ c1.getString(3));
			map.put("sj", c1.getString(4));
			fillMaps.add(map);
			c1.moveToNext();
			gettable.put(index + "", table);

		}
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
	// public CharSequence[] getlist(List<String> l)
	// {
	// CharSequence[] lis= new CharSequence[l.size()+1];
	// lis[0]="���й���";
	// for (int i = 0; i < l.size(); i++) {
	// lis[i+1] = l.get(i);
	//
	// }
	// return lis;
	//
	// }
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
}
