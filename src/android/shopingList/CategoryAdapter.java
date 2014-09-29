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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryAdapter extends CursorAdapter
{
	private ThreadPoolExecutor executor;
	private final LayoutInflater inflate;
	private final Context context;
	

	public CategoryAdapter(Context context, Cursor c) 
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
		View listView = inflate.inflate(R.layout.cateitem, null);
		listView.getBackground().setAlpha(170);
		creatView(listView,cursor);
		return listView;
	}
	
	public void creatView(final View view,Cursor c)
	{
		Typeface fotn1 = Typeface.createFromAsset(context.getAssets(),"myfonts/myfont1.ttf" );
        
		TextView title =(TextView)view.findViewById(R.id.titleCate);
		ImageView edit = (ImageView)view.findViewById(R.id.updateCate);
		ImageView icon =(ImageView)view.findViewById(R.id.iconCate);
		
		CheckBox checkit= (CheckBox)view.findViewById(R.id.checkItCate);
		title.setTypeface(fotn1);
		final long id =c.getLong(0);
		String url = c.getString(2);
		final String nameCate = c.getString(1);
		
		Bundle imgbun  = new Bundle();
		imgbun.putInt("idCate",(int)id);
		imgbun.putString("urlCate", url);
		edit.setTag(imgbun);
		view.setOnClickListener(new View.OnClickListener() 
		{
			
			
			public void onClick(View v) 
			{
				Bundle bun = new Bundle();
				bun.putInt("idCate",(int)id);
				bun.putString("nameCate",nameCate);
				Intent i = new Intent(context,ShowItems.class);
				i.putExtras(bun);
				context.startActivity(i);
			}
		});
		
		Bundle bun = new Bundle();
		bun.putInt("idCate",(int)id);
		bun.putString("nameCate", nameCate);
		//checkit
		checkit.setTag(bun);
		title.setTag(id);
		title.setText(nameCate);
		
		icon.setTag(url);
		downloadImageForView(url,icon);
	}
	
	private void downloadImageForView(final String imageUrl, ImageView imageView) 
	{
	      final Handler handler = new ImageHandler(imageUrl, imageView);
	      executor.execute(new Runnable() {
	         public void run() {
	            try 
	            {
	               Bitmap image = getBitmapFromAsset("cate_img/"+imageUrl);
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
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) 
	{

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
		.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}


	
	

}
