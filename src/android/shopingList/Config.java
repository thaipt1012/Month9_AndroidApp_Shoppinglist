package android.shopingList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.shopingList.data.ConfigTask;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Config extends Activity
{
	TextView title;
	ConfigTask contask;
	EditText yourname,defaultquan,hostimg;
	CheckBox ispopup;
	Button cancel,ok;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		yourname = (EditText)findViewById(R.id.nameConf);
		defaultquan = (EditText)findViewById(R.id.deauftQuanConf);
		hostimg =(EditText)findViewById(R.id.hostConf);
		ispopup = (CheckBox)findViewById(R.id.isPopup);
		cancel = (Button)findViewById(R.id.cancelchangeConf);
		ok= (Button)findViewById(R.id.changeConf);
		
		contask = new ConfigTask(this);
		Cursor cur = contask.getConfig();
		cur.moveToFirst();
		
		yourname.setText(cur.getString(1));
		defaultquan.setText(cur.getString(3));
		hostimg.setText(cur.getString(4));
		ispopup.setChecked(cur.getInt(2)==1 ? true:false);
		
		cancel.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				Intent i = new Intent(Config.this,Mainmenu.class);
				startActivity(i);
			}
		});
		
		ok.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				int id = (ispopup.isChecked() ? 1:0 );
				String xxx = defaultquan.getText().toString();
				int quan = Integer.valueOf(xxx);
				contask.upDate(yourname.getText().toString(),
						id,quan , hostimg.getText().toString());
				Toast.makeText(Config.this,"YOUR CONFIGURATION IS CHANGED ! ",100).show();
			}
		});
	}
}
