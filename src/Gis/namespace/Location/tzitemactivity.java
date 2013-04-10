package Gis.namespace.Location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class tzitemactivity extends Activity {
	
	public  TextView etitle;
	public  TextView etime;
	public TextView emessage;
	public Button bg;
//	 DBConnection helper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tzitem);
//		View v = LayoutInflater.from(tzitemactivity.this).inflate(R.layout.tzitem, null);
		etitle=(TextView)this.findViewById(R.id.title);
		etime=(TextView)this.findViewById(R.id.time);
		emessage=(TextView)this.findViewById(R.id.message);
		bg=(Button)this.findViewById(R.id.button1);
		bg.setText("查看报告");
		Intent inten=getIntent();
		String title=inten.getStringExtra("title");
		String message=inten.getStringExtra("message");
		String time=inten.getStringExtra("time");
		final String zh=inten.getStringExtra("zh");
	     etime.setText(time);
	     etitle.setText(title);
        emessage.setText(message);
        emessage.getBackground().setAlpha(100);
        if(zh.length()<3)
        {
        	bg.setVisibility(View.INVISIBLE);
        }
       bg.setOnClickListener(new OnClickListener(){

		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 Intent intentwz =new Intent(tzitemactivity.this,TZBGActivity.class);
    		 intentwz.putExtra("bh", zh);
		     startActivity(intentwz);
		}});
 
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
			finish();
		
		}
		 
}
