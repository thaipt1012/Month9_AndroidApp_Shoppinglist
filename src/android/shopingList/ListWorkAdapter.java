package android.shopingList;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListWorkAdapter extends CursorAdapter{
	private final LayoutInflater inflate;
	private final Context context;
	public ListWorkAdapter(Context context, Cursor c) {
		super(context, c);
		this.context=context;
		inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		creatView(view,cursor);
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View listView = inflate.inflate(R.layout.work_item, null);
		creatView(listView,cursor);
		return listView;
	}
	public void creatView(final View view,Cursor c)
	{
		Typeface fotn1 = Typeface.createFromAsset(context.getAssets(),"myfonts/IMPRISHA.TTF" );
		TextView work =(TextView)view.findViewById(R.id.work_content);
		TextView time = (TextView)view.findViewById(R.id.time_content) ;
		CheckBox check = (CheckBox)view.findViewById(R.id.check_work) ;
		int ischeck=c.getInt(3);
		String nameWork  = c.getString(1) ;
		String timeWork = c.getString(2) ;
		check.setChecked(ischeck==1 ? true : false);
		check.setTag(c.getInt(0));
		final long id  = c.getLong(0);
		work.setText(c.getString(1));
		work.setTypeface(fotn1) ;
		time.setText( c.getString(2));
		Bundle bun  = new Bundle();
		bun.putInt("id",(int)id);
		bun.putString("name", "Công việc "+nameWork);
		bun.putString("time", timeWork) ;
		//checkit
		check.setTag(bun);
		view.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Toast.makeText(context, "dfff", 200).show() ;
				
			}
		});
		//time.setTypeface(fotn1) ;
	}
   
}
