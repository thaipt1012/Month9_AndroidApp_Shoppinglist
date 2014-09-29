package android.shopingList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Work extends Activity{
	CheckBox check ;
	TextView work ;
	TextView time ;
	SharedPreferences prefs  ;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.work_item);
		
		LinearLayout v  = (LinearLayout)findViewById(R.id.lin);
		v.setBackgroundResource(R.drawable.bgshowall) ;
		LinearLayout v1  = (LinearLayout)findViewById(R.id.lin);
		v1.setBackgroundResource(R.drawable.border_list);
		v1.getBackground().setAlpha(180) ;
		prefs = getSharedPreferences("myPref", MODE_PRIVATE);
		check = (CheckBox)findViewById(R.id.check_work) ;
		work = (TextView)findViewById(R.id.work_content) ;
		work.setTextSize(16);
		time = (TextView)findViewById(R.id.time_content) ;
		check.setVisibility(View.GONE) ;
		work.setText("Nội dung:\n"+prefs.getString("name", ""));
		time.setText("Thời gian thực hiện:"+prefs.getString("time", ""));
	}
}
