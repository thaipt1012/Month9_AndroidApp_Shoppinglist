package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class imageAdapter extends ArrayAdapter
{
	Context context;
	LayoutInflater inflate;
	String [] urls ;
	private ThreadPoolExecutor executor;
	public String path;
	
	public imageAdapter(Context context,String[] urls,String path)
	{
	    super(context,R.layout.image_spin,urls);
		inflate = (LayoutInflater)context.getSystemService
	      (Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
		this.path = path;
		this.urls=urls;
		executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(10);
	}

	
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView imageView;
        if (convertView == null) 
        {
            imageView = (ImageView)inflate.inflate(R.layout.image_view, null);
            imageView.setLayoutParams(new GridView.LayoutParams(105,105));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else 
        {
            imageView = (ImageView) convertView;
        }
        imageView.setTag(urls[position]);
        downloadImageForView(urls[position],imageView);
        return imageView;
	}
	
	private void downloadImageForView(final String imageUrl, ImageView imageView) 
	{
	      final Handler handler = new ImageHandler(imageUrl, imageView);
	      executor.execute(new Runnable() {
	         public void run() {
	            try 
	            {
	               Bitmap image = getBitmapFromAsset(path+"/"+imageUrl);
	               Bundle data = new Bundle();
	               data.putParcelable("image", image);
	               Message message = new Message();
	               message.setData(data);
	               handler.sendMessage(message);
	            } catch (Exception e) 
	            {
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
