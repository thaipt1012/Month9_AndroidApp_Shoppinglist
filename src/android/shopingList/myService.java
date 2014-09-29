package android.shopingList;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class myService extends Service {
	
	private SharedPreferences prefs;
	
	String ring;
	static final int UPDATE = 5000;
	private Timer timer;
	private String time ;
	private MediaPlayer mPlayer;

	private Timer newTimer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.stop();
		}
		if (newTimer != null ) {
			newTimer.purge();
			newTimer.cancel();
		}
		if (timer != null ) {
			timer.purge();
			timer.cancel();
		}
		Toast.makeText(this, "Tắt báo thức", Toast.LENGTH_SHORT).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		prefs = getSharedPreferences("myPref", MODE_PRIVATE);
		 
		// TODO Auto-generated method stub
		
		super.onStartCommand(intent, flags, startId);
		timer = new Timer();

		//playMediaAlarm();
		doSomeThingRepeat();
		
		Toast.makeText(this, "đặt báo thức", Toast.LENGTH_SHORT).show();
		return startId;
	}
	
	//lap lai sau 5 giay
	private void doSomeThingRepeat() {
		
		timer.scheduleAtFixedRate(new TimerTask() {
			int mHourNow = 0, mMinuteNow = 0;
			@Override
			public void run() {
				
				final Calendar c = Calendar.getInstance();
				mHourNow = c.get(Calendar.HOUR_OF_DAY);
				mMinuteNow= c.get(Calendar.MINUTE);
				time = prefs.getString("time", "");
				String mHour = time.substring(0, 2);
				String mMinute = time.substring(3, 5) ;
				
				
				int mh = Integer.parseInt(mHour);
				int mm = Integer.parseInt(mMinute);
					
				if (mHourNow == mh && mMinuteNow == mm) {
					playMediaAlarm();
				}
					Log.d("Myservice-doSomeThingRepeat", mHourNow +"h"+ mMinuteNow + "&&" +mHour +"h" + mMinute);
				
			}
		}, 0, UPDATE);
	}
	
	//ham choi nhac
	private void playMediaAlarm() {
		
		if (timer != null ) {
			timer.purge();
			timer.cancel();
			Log.d("My service", "playMediaAlarm: timer.cancel() ");
		}
		
		playAgain();
		
		
	}
	private void playAgain() {
		newTimer = new Timer();
		newTimer.scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
				createNotification();
				// TODO Auto-generated method stub	
				ring = "belle" ;
				mPlayer = MediaPlayer.create(getBaseContext(), R.raw.alarm);
				if (mPlayer.isPlaying() == false) {
					if (ring.compareTo("belle") == 0) {		
						mPlayer = MediaPlayer.create(getBaseContext(), R.raw.alarm);
					} else {
						mPlayer = MediaPlayer.create(getBaseContext(), R.raw.moringalarm);
					}
					mPlayer.start();
				}
			}
			
			
		}, 0, 60000);
	}
	// tao notification
	 	private void createNotification() {
	 		
					NotificationManager notificationManager = (NotificationManager) 
	 						getSystemService(NOTIFICATION_SERVICE);
	 			Notification notification = new Notification(R.drawable.icon,
	 					"Nhắc nhở", System.currentTimeMillis());
	 			// Hide the notification after its selected
	 			notification.flags |= Notification.FLAG_AUTO_CANCEL;
	 			Intent intent = new Intent(this, NotificationReceiver.class);
	 			PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
	 			notification.setLatestEventInfo(this, "Bạn có công việc cần làm",
	 					"Đó là:"+prefs.getString("name", ""), activity);
	 			notification.number += 7;
	 			notificationManager.notify(0, notification);
	 			notification.defaults |= Notification.DEFAULT_VIBRATE ;
				}
}

