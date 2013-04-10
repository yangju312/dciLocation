package Gis.namespace.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.updateapp.CurrentVersion;
import com.updateapp.GetUpdateInfo;

import Gis.namespace.Location.GISLocationActivity.SETIP;
import Gis.namespace.Location.GISLocationActivity.User;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public String userInfo;
	EditText accountEt, passwordEt;
	CheckBox cb;
	boolean isture = false;
	private static final String TAG = "Update";
	public String UserName;
	public String Userpassword;
	public String Name;
	public int userid;
	public String Lxfs;
	public int id;
	public String setip;
	public String setdk;
	String changed;
	public File dataf;
	private ProgressDialog pd;
	boolean isshow = false;
	private ProgressDialog pBar;
	private String downPath = null;
	private String appName = "dciLocation.apk";
	private String appVersion = "version.json";
	private int newVerCode = 0;
	String APKPATH = "/mnt/sdcard/珠江巡查安装文件/";
	private String newVerName = "";
	private Handler handler = new Handler();
	private Handler UPhandler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		cb = (CheckBox) findViewById(R.id.checkbox);
		accountEt = (EditText) findViewById(R.id.et_account);
		UPhandler = new Handler();
		passwordEt = (EditText) findViewById(R.id.et_password);
		Button btnLogin = (Button) findViewById(R.id.btn_login);
		Button btnfwq = (Button) findViewById(R.id.sip);
	btnfwq.setVisibility(View.INVISIBLE);
		// Button btntx=(Button) findViewById(R.id.stx);
		TextView tv = (TextView) findViewById(R.id.xmmcmc);
		tv.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
		try {

			// tv.setText("广州市珠江堤防巡查系统");

			dataf = new File("/sdcard/XClocalhost/zjdafh");
			SQLiteDatabase db = SQLiteDatabase
					.openOrCreateDatabase(dataf, null);
			// helper=new DBConnection(LoginActivity.this);
			// SQLiteDatabase db = helper.getWritableDatabase();
			Cursor c = db.query(User.TABLE_NAME, null, null, null, null, null,
					null);
			if (c.getCount() > 0) {
				c.moveToFirst();
				accountEt.setText(c.getString(1));
				passwordEt.setText(c.getString(2));
			}

			Cursor c1 = db.query(SETIP.TABLE_NAME, new String[] { SETIP.IP,
					SETIP.DK }, null, null, null, null, null);

			if (c1.getCount() > 0) {
				isture = true;
				c1.moveToFirst();
				setip = c1.getString(0);
				setdk = c1.getString(1);
			} else {
				setip = "192.168.110.32";
				setdk = "8080";
				ContentValues values = new ContentValues();
				values.put(SETIP.IP, setip);
				values.put(SETIP.DK, setdk);

				db.insert(SETIP.TABLE_NAME, null, values);

			}
			db.close();
			c.close();
			c1.close();
			downPath = "http://" + setip + ":" + setdk + "/androidserver/";
			try {
				if (isNetworkAvailable(this) == false) {

				} else {
					checkToUpdate();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			// Intent intent =new
			// Intent(LoginActivity.this,GISLocationActivity.class);
			// startActivity(intent);
			btnLogin.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {

					if (accountEt.getText().toString().equals("输入帐号")
							|| passwordEt.getText().toString().equals("")
							|| accountEt.getText().toString().equals("")) {
						Toast.makeText(LoginActivity.this, "账号或密码不能为空！",
								Toast.LENGTH_SHORT).show();
					} else {
						if (isNetworkAvailable(LoginActivity.this) == false) {
							Toast.makeText(LoginActivity.this, "无网络连接，请连接网络！",
									Toast.LENGTH_SHORT).show();
							return;
						}
						ContentValues values = new ContentValues();
						values.put(User.USER, accountEt.getText().toString());
						SQLiteDatabase db = SQLiteDatabase
								.openOrCreateDatabase(dataf, null);
						db.delete(User.TABLE_NAME, null, null);
						if (cb.isChecked()) {
							values.put(User.PASS, passwordEt.getText()
									.toString());
							db.insert(User.TABLE_NAME, null, values);
						} else {
							db.insert(User.TABLE_NAME, null, values);
						}
						db.close();
						login(accountEt.getText().toString(), passwordEt
								.getText().toString());
						// if(login(accountEt.getText().toString(),
						// passwordEt.getText().toString()))
						// {
						//
						// UserName=accountEt.getText().toString();
						// Userpassword=passwordEt.getText().toString();
						// // ServiceManager serviceManager = new
						// ServiceManager(LoginActivity.this);
						// //
						// serviceManager.setNotificationIcon(R.drawable.i_demographics);
						// // serviceManager.startService();
						// if(setip!=null&&setip!="")
						// {
						//
						// Intent intent =new
						// Intent(LoginActivity.this,GISLocationActivity.class);
						// startActivity(intent);
						// }
						// else
						// {
						// Toast.makeText(LoginActivity.this, "没设置服务器，请设置！！",
						// Toast.LENGTH_SHORT).show();
						// }
						// }
						// else
						// {if(isshow)
						// {
						// Toast.makeText(LoginActivity.this, "账号或密码不正确！",
						// Toast.LENGTH_SHORT).show();
						// }
						// }
					}
				}
			});

			btnfwq.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					// final EditText inputServer = new
					// EditText(LoginActivity.this);

					View v = LayoutInflater.from(LoginActivity.this).inflate(
							R.layout.set, null);
					final EditText ipEt = (EditText) v.findViewById(R.id.ip);
					final EditText dkEt = (EditText) v.findViewById(R.id.duank);
					ipEt.setText(setip);
					dkEt.setText(setdk);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							LoginActivity.this);
					builder.setTitle("服务器设置").setIcon(null).setView(v)
							.setNegativeButton("取消", null);
					builder.setPositiveButton("设置",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

									setip = ipEt.getText().toString();
									setdk = dkEt.getText().toString();
									SQLiteDatabase db = SQLiteDatabase
											.openOrCreateDatabase(dataf, null);
									ContentValues values = new ContentValues();
									values.put(SETIP.IP, setip);
									values.put(SETIP.DK, setdk);
									db.delete(SETIP.TABLE_NAME, null, null);
									db.insert(SETIP.TABLE_NAME, null, values);
									db.close();
								}
							});
					builder.show();
				}
			});
			// btntx.setOnClickListener(new OnClickListener(){
			// public void onClick(View arg0) {
			// ServiceManager.viewNotificationSettings(LoginActivity.this);
			// }});

			ManageActivity.addActiviy("LoginActivity", LoginActivity.this);
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("result", e.getMessage());
		}

	}

	private static boolean isNetworkAvailable(Context context) {
		// TODO Auto-generated method stub
		try {

			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
			return (netWorkInfo != null && netWorkInfo.isAvailable());// 妫�祴缃戠粶鏄惁鍙敤
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	private void checkToUpdate() throws NameNotFoundException {
		// TODO Auto-generated method stub
		if (getServerVersion()) {
			int currentCode = CurrentVersion.getVerCode(this);
			if (newVerCode > currentCode) {// Current Version is old
											// 寮瑰嚭鏇存柊鎻愮ず瀵硅瘽妗�
				showUpdateDialog();
			}
		}
	}

	// show Update Dialog

	private void showUpdateDialog() throws NameNotFoundException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("当前版本：");
		sb.append(CurrentVersion.getVerName(this));
		sb.append("\n");
		sb.append("VerCode:");
		sb.append(CurrentVersion.getVerCode(this));
		sb.append("\n");
		sb.append("发现新版本：");
		sb.append(newVerName);
		sb.append("\n");
		sb.append("NewVerCode:");
		sb.append(newVerCode);
		sb.append("\n");
		sb.append("更新内容:");
		sb.append(changed);
		sb.append("\n");
		sb.append("是否更新");
		Dialog dialog = new AlertDialog.Builder(LoginActivity.this)
				.setTitle("软件更新")
				.setMessage(sb.toString())
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						showProgressBar();// 鏇存柊褰撳墠鐗堟湰
					}
				})
				.setNegativeButton("暂不更新",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						}).create();
		dialog.show();
	}

	protected void showProgressBar() {
		// TODO Auto-generated method stub
		pBar = new ProgressDialog(LoginActivity.this);
		pBar.setTitle("正在下载");
		pBar.setMessage("请稍后！");
		pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pBar.setProgress(0);
		pBar.setCanceledOnTouchOutside(false);
		downAppFile(downPath + appName);
	}

	// Get ServerVersion from GetUpdateInfo.getUpdateVerJSON
	private boolean getServerVersion() {
		// TODO Auto-generated method stub
		try {
			String newVerJSON = GetUpdateInfo.getUpdataVerJSON(downPath
					+ appVersion);
			JSONArray jsonArray = new JSONArray(newVerJSON);
			if (jsonArray.length() > 0) {
				JSONObject obj = jsonArray.getJSONObject(0);
				try {
					newVerCode = Integer.parseInt(obj.getString("verCode"));
					newVerName = obj.getString("verName");
					changed = obj.getString("changed");
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
					newVerCode = -1;
					newVerName = "";
					return false;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
		return true;
	}

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
					File file1 = new File(APKPATH);
					if (!file1.exists()) {
						file1.mkdirs();
					}
					File file2 = new File(APKPATH, appName);

					if (file2.exists()) {
						file2.delete();
					}
					File file = new File(APKPATH, appName);
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
					haveDownLoad();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	// cancel progressBar and start new App
	protected void haveDownLoad() {
		// TODO Auto-generated method stub
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				// 寮瑰嚭璀﹀憡妗�鎻愮ず鏄惁瀹夎鏂扮殑鐗堟湰
//				Dialog installDialog = new AlertDialog.Builder(
//						LoginActivity.this)
//						.setTitle("下载完成")
//						.setMessage("是否安装新更新！")
//						.setPositiveButton("确定",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//										// TODO Auto-generated method stub
										installNewApk();
										// finish();
//									}
//								})
//						.setNegativeButton("取消",
//								new DialogInterface.OnClickListener() {
//									public void onClick(DialogInterface dialog,
//											int which) {
//										// TODO Auto-generated method stub
//										// finish();
//									}
//								}).create();
//				installDialog.show();
			}
		});
	}

	// 瀹夎鏂扮殑搴旂敤
	protected void installNewApk() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(APKPATH, appName)),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	private Handler handler1 = new Handler() {
		@Override
		// 当有消息发送出来的时候就执行Handler的这个方法
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 只要执行到这里就关闭对话框
			pd.dismiss();
		}
	};
	boolean rturn = false;

	public boolean login(final String a, final String p) {
		pd = new ProgressDialog(LoginActivity.this);
		pd.setTitle("正在登录");
		pd.setMessage("请稍后！");
		pd.setCanceledOnTouchOutside(false);
		pd.show();

		new Thread() {
			public void run() {

				HttpClient httpclient = new DefaultHttpClient();
				String url = "http://" + setip + ":" + setdk
						+ "/androidserver/servlet/LogServlet";
				// String
				// url=ConfigManager.getInstance().getString("LogServlet");
				HttpPost httppost = new HttpPost(url);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						4);
				nameValuePairs.add(new BasicNameValuePair("user", a));
				nameValuePairs.add(new BasicNameValuePair("password", p));
				HttpResponse response;
				try {
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					 httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					 60000);
					 //请求超时
					 httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					 60000);
					response = httpclient.execute(httppost);
					if (response.getStatusLine().getStatusCode() == 200) {
						// 获取返回的数据

						HttpEntity resEntity = response.getEntity();
						if (resEntity != null) {
							// System.out.println(EntityUtils.toString(resEntity,"utf-8"));
							StringEntity my_entity = new StringEntity(
									EntityUtils.toString(resEntity), "utf-8");
							String result = EntityUtils.toString(my_entity);
							JSONArray jsonArr = new JSONObject(result)
									.getJSONArray("result");
							JSONObject jsonObj = (JSONObject) jsonArr.get(0);

							Boolean istrue = jsonObj.getBoolean("succ");
							if (istrue) {
								Name = jsonObj.getString("name");
								id = jsonObj.getInt("id");
								// Lxfs=jsonObj.getString("lxfs");
								ContentValues values = new ContentValues();
								values.put(User.USERNAME, Name);
								values.put(User.USERID, id);
								String where = User.USER + "='" + a + "'";
								SQLiteDatabase db = SQLiteDatabase
										.openOrCreateDatabase(dataf, null);
								db.update(User.TABLE_NAME, values, where, null);
								db.close();

								rturn = true;
							} else {
								isshow = true;
							}
							Log.i("HttpPost", "HttpPost方式请求成功，返回数据如下：");
							Log.i("result", result);
						}

					} else {
						Log.i("HttpPost", "HttpPost方式请求失败");
					}
					// pd.cancel();
				} catch (Exception e) {
					// e.printStackTrace();
					Log.i("HttpPost", e.getMessage());
					// try
					// {
					// pd.setMessage("请求超时！！");
					// }catch(Exception eee){Log.i("HttpPost",
					// eee.getMessage());}
					// Toast.makeText(LoginActivity.this, "请求超时！",
					// Toast.LENGTH_SHORT).show();
					// pd.cancel();
					UPhandler.post(runnableUi1);
				}

				UPhandler.post(runnableUi);
				pd.cancel();
			}
		}.start();

		return rturn;
	}

	Runnable runnableUi = new Runnable() {

		public void run() {
			// 更新界面
			if (rturn) {
				Intent intent = new Intent(LoginActivity.this,
						GISLocationActivity.class);
				startActivity(intent);
			} else {
				if (isshow) {
					// pd.setMessage("账号或密码不正确！");
					Toast.makeText(LoginActivity.this, "账号或密码不正确！",
							Toast.LENGTH_SHORT).show();
				}
			}
		}

	};
	Runnable runnableUi1 = new Runnable() {

		public void run() {
			// 更新界面
			Toast.makeText(LoginActivity.this, "请求超时！", Toast.LENGTH_SHORT)
					.show();
			// pd.setMessage("账号或密码不正确！");

		}

	};

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		// Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
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

	@Override
	protected void onStop() {
		super.onStop();
		// finish();

	}

}
