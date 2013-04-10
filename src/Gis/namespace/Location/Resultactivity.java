package Gis.namespace.Location;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import Gis.namespace.Location.GISLocationActivity.DFBG;
import Gis.namespace.Location.GISLocationActivity.WZBG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Resultactivity extends Activity {
	String tablename;
	String bh;
	int index;
	ListView listView;
	MyAdapter adapter1;
	File dataf;

	// DBConnection helper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		listView = (ListView) this.findViewById(R.id.list);
		try {
			String[] from = new String[] { "name", "bh", "sj", "wz" };
			int[] to = new int[] { R.id.user_id, R.id.user_bh, R.id.user_gc,
					R.id.user_sj };
			adapter1 = new MyAdapter(Resultactivity.this,
					Qureyactivity.fillMaps, R.layout.listitem, from, to);
			listView.setAdapter(adapter1);
			this.listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					@SuppressWarnings("unchecked")
					HashMap<String, String> map = (HashMap<String, String>) Resultactivity.this.adapter1
							.getItem(arg2);
					index = arg2;
					// String id = map.get("id");
					bh = map.get("bh");
					String fbh = bh.substring(0, 2);
					if (fbh.equals("WG")) {
						Intent intentwz = new Intent(Resultactivity.this,
								LsWgbgActivity.class);
						intentwz.putExtra("bh", bh);
						startActivity(intentwz);
					} else {
						Intent intentwz = new Intent(Resultactivity.this,
								jswzactivity.class);
						intentwz.putExtra("bh", bh);
						startActivity(intentwz);
					}
				}

			});
			this.listView
					.setOnItemLongClickListener(new OnItemLongClickListener() {
						public boolean onItemLongClick(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							// TODO Auto-generated method stub

							// HashMap<String, String> map = (HashMap<String,
							// String>) Resultactivity.this.adapter1
							// .getItem(arg2);
							// index=arg2;
							// String id = map.get("id");
							// bh=map.get("bh");
							// tablename=Qureyactivity.gettable.get(id);
							// if(tablename.equals(DFBG.TABLE_NAME)||tablename.equals(DFBG.TABLE_NAME)||tablename.equals(DFBG.TABLE_NAME))
							// {
							// listView.setOnCreateContextMenuListener(new
							// OnCreateContextMenuListener() {
							// public void onCreateContextMenu(ContextMenu arg0,
							// View arg1, ContextMenuInfo arg2) {
							// // arg0.add(0,1,Menu.NONE,"��");
							// arg0.add(0,1,Menu.NONE,"ɾ��");
							// // TODO Auto-generated method stub
							//
							// }
							// });
							// }

							return false;
						}

					});
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("HttpPost", e.getMessage());
		}
		if (ManageActivity.allActiviy.get("Resultactivity") == null) {
			ManageActivity.addActiviy("Resultactivity", Resultactivity.this);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem aItem) {
		try {
			// switch (aItem.getItemId()) {
			// case 1:
			//
			// SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataf,
			// null);
			// String where="bh='"+bh+"'";
			// db.delete(tablename, where, null);
			// Qureyactivity.fillMaps.remove(index);
			// adapter1.mItemList=Qureyactivity.fillMaps;
			// adapter1.notifyDataSetChanged();
			// db.close();
			// break;
			// }
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("HttpPost", e.getMessage());
		}
		return false;
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

	private class MyAdapter extends SimpleAdapter {
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

		//
		// @Override
		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		// HashMap<String ,String> map = mItemList.get(position);
		// String image = map.get(""+position);
		// View view = super.getView(position, convertView, parent);
		// ImageView imageview = (ImageView)view.findViewById(R.id.imageview);
		// TextView tv = (TextView)view.findViewById(R.id.tv);
		// imageview.setBackgroundResource(image);
		// tv.setText(""+position);
		// return view;
		// }
	}
}
