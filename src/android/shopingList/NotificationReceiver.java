package android.shopingList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotificationReceiver extends Activity {
	Button congviec ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		congviec = (Button)findViewById(R.id.search) ;
		congviec.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				/*Intent i = new Intent(getApplicationContext(),Work.class) ;
				startActivity(i) ;*/
				stopService(new Intent(getBaseContext(), myService.class));
				Intent intent = new Intent(getBaseContext(), myService.class);
				startService(intent);
				finish();
				onPause();
				onStop();
				onDestroy();
				
			}
		});
	}
}