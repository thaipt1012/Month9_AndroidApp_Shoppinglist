package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import android.app.Activity;
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
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SelectImg_Adapter extends ArrayAdapter<String>
{
	private String[] urls;
	private ThreadPoolExecutor executor;
	private Context context;
	private String path;
	TextView name_img;
	LayoutInflater inflater;
	Bitmap image;
	ImageView icon;
	
	public SelectImg_Adapter(Context context,String[] urlx,String path)
	{
		super(context,R.layout.select_img,R.id.name_select_img,urlx);
		this.context=context;
		this.urls=urlx;
		this.path=path;
		executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(10);
		inflater = (LayoutInflater)context.getSystemService
	      (Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		return getSpinView(position, convertView, parent);
	}
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) 
	{
			return getCustomView(position, convertView, parent);
    }
	
	public View getCustomView(int position, View convertView, ViewGroup parent) 
	{      
		    View listItem;
		    final ViewHolder row;
		    final String url = urls[position];
		    if(convertView==null)
		    {
		    	//convertView = super.getView(position, convertView, parent);
		    	convertView = inflater.inflate(R.layout.select_img, null);
		        icon =(ImageView)convertView.findViewById(R.id.icon_select_img);
				name_img = (TextView)convertView.findViewById(R.id.name_select_img);
				name_img.setText(url);
				icon.setTag(url);
				downloadImageForView(url,icon);
		    	/*row = new ViewHolder();
		    	row.title=(TextView)convertView.findViewById(R.id.name_select_img);
		    	row.icon=(ImageView)convertView.findViewById(R.id.icon_select_img);
		    	convertView.setTag(row);*/
				/*new Thread()
				{
					public void run()
					{
						try
						{
						    image = getBitmapFromAsset(path+"/"+url);
						    icon.setImageBitmap(image);
						}
						catch(IOException e)
						{
							name_img.setText(e.toString());
						}
					}
				}.start();*/
				
		    }
		    else
		    {
		    	//row = (ViewHolder)convertView.getTag();
		    }
		    //row.title.setText(urls[position]);
		    //row.icon.setTag(url);
		    //downloadImageForView(url, row.icon);
		    
			return convertView;
	}
	public View getSpinView(int position, View convertView, ViewGroup parent)
	{
		if(convertView==null)
			convertView=inflater.inflate(R.layout.image_spin, null);
		ImageView icon =(ImageView)convertView.findViewById(R.id.icon_image_spin);
		final String url = urls[position];
		
		icon.setTag(url);
		downloadImageForView(url,icon);
	
		return icon;
	}
	private void downloadImageForView(final String imageUrl, ImageView imageView) 
	{
	      final Handler handler = new ImageHandler(imageUrl, imageView);
	      executor.execute(new Runnable() {
	         public void run() {
	            try 
	            {
	               Bitmap image = getBitmapFromAsset(path+"/"+imageUrl);
	               if(image==null)
	               name_img.setText(path+"/"+imageUrl);
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
	
	public class ViewHolder
	{
		public TextView title;
		public ImageView icon;
	}

}
