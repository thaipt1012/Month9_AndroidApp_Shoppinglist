package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ItemAdapter extends CursorAdapter
{
	private ThreadPoolExecutor executor;
	private final LayoutInflater inflate;
	Context context;

	public ItemAdapter(Context context, Cursor c) 
	{
		super(context, c);
		this.context=context;
		this.inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(5);
	}

	
	public void bindView(View view, Context context, Cursor cursor) 
	{
		creatView(view,cursor);
	}

	
	public View newView(Context context, Cursor cursor, ViewGroup parent) 
	{
		View listView = inflate.inflate(R.layout.item, null);
		creatView(listView,cursor);
		return listView;
	}
	
	public void creatView(View view , Cursor cur)
	{
		final int theid = cur.getInt(0);
		TextView titleText=(TextView)view.findViewById(R.id.titleItem);
		TextView priceText=(TextView)view.findViewById(R.id.priceItem);
		TextView quanText=(TextView)view.findViewById(R.id.quanItem);
		RatingBar rate=(RatingBar)view.findViewById(R.id.rateItem);
		
		ImageView icon = (ImageView)view.findViewById(R.id.iconItem);
		CheckBox check =(CheckBox)view.findViewById(R.id.isSelectedItem);
		
		view.setTag(theid);
		String url =cur.getString(6);
		url=url.replaceAll("\\s","");
		
		int ischeck=cur.getInt(8);
		int quanthisitem = cur.getInt(4);
		check.setChecked(ischeck==1 ? true : false);
		check.setTag(cur.getInt(0));
		
		
		titleText.setText(cur.getString(1));
		titleText.setTag(theid);
		view.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				Bundle bun = new Bundle();
				bun.putInt("idItem", theid);
				Intent i = new Intent(context,ShowDetail.class);
				i.putExtras(bun);
				context.startActivity(i);
			}
		});
		
		priceText.setText(String.valueOf(cur.getInt(3))+"đ");
		if(quanthisitem>0)
		quanText.setText("("+quanthisitem+")");
		
		rate.setRating(Float.valueOf(cur.getInt(5)));
		
		
		icon.setTag(url);
		downloadImageForView(url,icon);
	}
	
	private void downloadImageForView(final String imageUrl, ImageView imageView) 
	{
	      final Handler handler = new ImageHandler(imageUrl, imageView);
	      executor.execute(new Runnable() {
	         public void run() {
	            try {
	               Bitmap image = getBitmapFromAsset("item_img/"+imageUrl);
	               Bundle data = new Bundle();
	               data.putParcelable("image", image);
	               Message message = new Message();
	               message.setData(data);
	               handler.sendMessage(message);
	            } catch (Exception e) {
	               e.printStackTrace();
	            }
	         }
	      });
	}
	private Bitmap getBitmapFromAsset(String strName) throws IOException
    {
        AssetManager assetManager = context.getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

}
