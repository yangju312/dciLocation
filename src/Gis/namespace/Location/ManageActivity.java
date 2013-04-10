/**
 * 管理所有的Activity以便完全退出程序和得到Activity的实例
 */
package Gis.namespace.Location;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.util.Log;

public class ManageActivity {
	public static Dictionary<String, Activity> allActiviy=new Hashtable<String,Activity>();
	
	public static void addActiviy(String name,Activity activity){
		allActiviy.put(name, activity);
	}
	
	public static Activity getActivity(String name){
		return (Activity)allActiviy.get(name);
	}
	public static void ColseallActivity(){
		try
		{
		 Enumeration<String> en = allActiviy.keys();
		 while(en.hasMoreElements()){
    	    	
               allActiviy.get((String) en.nextElement()).finish();
//               allActiviy.remove((String) en.nextElement());
             
              }
		}
		catch (Exception e) {
			Log.e("", e.getMessage());
		}
	}
    public static void ColseActivity(String name){
    	allActiviy.get(name).finish();
    	 allActiviy.remove(name);
	}
}
