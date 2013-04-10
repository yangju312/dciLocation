package Gis.namespace.Location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SmsServiceBootReceiver extends BroadcastReceiver {
public static	Context context1;
	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		context1=context;
		 Intent intent =new Intent("org.androidpn.client.NotificationService");
         context.startService(intent);
         Intent intent1 =new Intent("zjdflocationservice");
         context.startService(intent1);
	}

}
