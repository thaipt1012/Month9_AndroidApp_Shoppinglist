package android.shopingList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Mainmenu extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		Intent i ;
		switch(item.getItemId())
		{
		case R.id.goShoping:
			i = new Intent(Mainmenu.this,ShopingListActivity.class);
			startActivity(i);
			break;
		case R.id.myTask:
			i = new Intent(Mainmenu.this,TaskActivity.class);
			startActivity(i);
			break;
			//i = new Intent(Mainmenu.this,);
		case R.id.config:
			i = new Intent(Mainmenu.this,Config.class);
			startActivity(i);
			break;
		default :
			i = new Intent(Mainmenu.this,ShopingListActivity.class);
		}
		
		return true;
	}
	
	
   
}
