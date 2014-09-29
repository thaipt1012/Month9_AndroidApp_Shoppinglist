package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.content.Context;
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
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryAdapter extends CursorAdapter
{
	private ThreadPoolExecutor executor;
	private final LayoutInflater inflate;
	Context context;
	Cursor cursor;
	public GalleryAdapter(Context context, Cursor c) 
	{
		super(context, c);
		this.context=context;
		this.inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(5);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) 
	{
		creatView(view,cursor);
	}
	public View newView(Context context, Cursor cursor, ViewGroup parent) 
	{
		View listView = inflate.inflate(R.layout.gallery, null);
		creatView(listView,cursor);
		return listView;
	}
	
	public void creatView(View view , Cursor cur)
	{
	     int theid = cur.getInt(0);
	     String url = cur.getString(2);
	     String nameitem = cur.getString(1);
	     
	     view.getTag(theid);
	     ImageView icon = (ImageView)view.findViewById(R.id.iconGallery);
	     TextView title = (TextView)view.findViewById(R.id.titleGallery);
	     title.setText(nameitem);
	     icon.setTag(url);
	     view.setTag(theid);
	     
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
