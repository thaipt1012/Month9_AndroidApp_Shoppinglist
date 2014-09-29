package android.shopingList;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.shopingList.data.CategoryTask;
import android.shopingList.data.ItemTask;
import android.shopingList.model.getDateTime;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDetail extends Activity
{
	TextView title,category,price,urltext,edit_price_invi,statusChecked;
	EditText quan;
	Button adjustQuan,dequan;
    ItemTask itemtask;
    CategoryTask catetask;
    ImageView icon,editbtn,savebtn,delbtn,back;
    RatingBar rate;
    private Gallery mGallery;
    Cursor cur;
    String[] url_imgs;
    AssetManager assets;
    LayoutInflater inflater;
    Dialog dia;
    CheckBox getitemcheckbox;
    LinearLayout customquanlin;
    
    public static int theid;
    public static int ischeck=0;
    public static int customquan=0;
    public static String todayTime="";
    
    public static final int SELECT_IMG = 1;
    
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		itemtask = new ItemTask(this);
		catetask = new CategoryTask(this);
		
		title=(TextView)findViewById(R.id.titleDetail);
		urltext = (TextView)findViewById(R.id.edit_item_url);
		statusChecked = (TextView)findViewById(R.id.statusCheckeddetail);
		edit_price_invi = (TextView)findViewById(R.id.edit_price_invi);
		category=(TextView)findViewById(R.id.cateDetail);
		price=(TextView)findViewById(R.id.priceDetail);
		quan=(EditText)findViewById(R.id.quanDetail);
		dequan=(Button)findViewById(R.id.decreaseQuanDetail);
		adjustQuan=(Button)findViewById(R.id.adjustQuanDetail);
		icon = (ImageView)findViewById(R.id.iconDetail);
		back = (ImageView)findViewById(R.id.backHomeDetail) ;
		rate = (RatingBar)findViewById(R.id.rateDetail);
		mGallery = (Gallery)findViewById(R.id.gallery);
		editbtn = (ImageView)findViewById(R.id.editItemDetail);
		savebtn = (ImageView)findViewById(R.id.saveItemDetail);
		delbtn =(ImageView)findViewById(R.id.deleteItemDetail);
		getitemcheckbox= (CheckBox)findViewById(R.id.checkItemDetail);
		customquanlin = (LinearLayout)findViewById(R.id.customquandetail);
		
		
		assets = getApplicationContext().getAssets();
		try
		{
		     url_imgs=assets.list("item_img");
		}
		catch(IOException e)
		{
			Toast.makeText(ShowDetail.this, e.toString(), 20).show();
		}
		
		try
		{
			Intent i = this.getIntent();
			Bundle bun = i.getExtras();
			if(bun!=null)
			theid = bun.getInt("idItem");
			
			cur=itemtask.getItem(theid);
			cur.moveToFirst();
			String url = cur.getString(6);
			url=url.replaceAll("\\s","");
			int ratenum = cur.getInt(5);
			urltext.setText(url);
			Typeface fotn1 = Typeface.createFromAsset(getAssets(),"myfonts/KGShadowOfTheDay.ttf" );
			ischeck = (cur.getInt(8));
			
			title.setText(cur.getString(1));
			title.setTypeface(fotn1);
			category.setText(ShowItems.cateName);
			quan.setText(""+cur.getInt(4));
			price.setText("Price: "+cur.getInt(3)+"đ");
			edit_price_invi.setText(String.valueOf(cur.getInt(3)));
			
			if(ischeck==1)
			{
				statusChecked.setText("Thay đổi số lượng:");
				getitemcheckbox.setChecked(true);
				customquanlin.setVisibility(View.VISIBLE);
				todayTime=getDateTime.getTime("dd-MM-yyyy");
			}
			else
			{
				statusChecked.setText("Thay Đổi số lượng:");
				getitemcheckbox.setChecked(false);
				customquanlin.setVisibility(View.INVISIBLE);
			}
			
			
			try
			{
			   Bitmap image = getBitmapFromAsset("item_img/"+url);
			   icon.setImageBitmap(image);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			rate.setRating(ratenum);
			
			adjustQuan.setOnClickListener(new View.OnClickListener() {
				
				
				public void onClick(View v) 
				{
					String xxx = quan.getText().toString();
					if(xxx.equals(""))
						xxx="0";
					int quanofItem = Integer.parseInt(xxx);
					quanofItem++;
					customquan = quanofItem;
					quan.setText(""+quanofItem);
				}
			});
          dequan.setOnClickListener(new View.OnClickListener() {
				
				
				public void onClick(View v) 
				{
					String xxx = quan.getText().toString();
					if(xxx.equals("") || xxx.equals("0"))
						xxx="1";
					int quanofItem = Integer.parseInt(xxx);
					if(quanofItem!=0)
					quanofItem--;
					customquan = quanofItem;
					quan.setText(""+quanofItem);
				}
			});
          
          //gallery
          Drawable border_img = getResources().getDrawable(R.drawable.border_img);
          border_img.setAlpha(200);
          Cursor gacur = itemtask.getImgs();
          GalleryAdapter gadapter = new GalleryAdapter(this,gacur);
          mGallery.setAdapter(gadapter);
          mGallery.setBackgroundDrawable(border_img);
        
		}
		catch(Exception e)
		{
			title.setText(e.toString());
		}
		icon.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v)
			{
				try
				{
				    dia = onCreateDialog(1);
					dia.show();
				}
				catch(Exception e)
				{
					Toast.makeText(ShowDetail.this, e.toString(), 200).show();
				}
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(ShowDetail.this,ShowItems.class) ;
				startActivity(i) ;
				
			}
		});
		editbtn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Dialog diax = onCreateDialog(2,title.getText().toString(),edit_price_invi.getText().toString());
				diax.show();
			}
		});
		
		savebtn.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				try
				{
				itemtask.fastUpdate(theid,
						title.getText().toString(),
						edit_price_invi.getText().toString(),
						urltext.getText().toString(),ischeck,customquan,todayTime);
				Toast.makeText(ShowDetail.this,"Thay đổi thành công!", 20).show();
				}
				catch(Exception e)
				{
					Toast.makeText(ShowDetail.this,e.toString(), 20).show();
				}
			}
		});
		
		delbtn.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				Dialog delornot = onCreateDialog(3,title.getText().toString());
				delornot.show();
			}
		});
	}
	
	private Bitmap getBitmapFromAsset(String strName) throws IOException
    {
        AssetManager assetManager = getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	
	public Dialog onCreateDialog(int id)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		GridView grid =(GridView)inflater.inflate(R.layout.grid_view, null);
		try
        {
			imageAdapter iadapter = new imageAdapter(ShowDetail.this,url_imgs,"item_img");
        	grid.setAdapter(iadapter);
        }
        catch(Exception e)
        {
        	Toast.makeText(ShowDetail.this, e.toString(), 200).show();
        }
        dialog.setView(grid);
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		return dialog.create();
	}
	
	public Dialog onCreateDialog(int id,String nametitle,String priceTile)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Tên mặt hàng:");
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.edit_item_popup, null);
		final EditText edittitle  = (EditText)v.findViewById(R.id.edit_title_popup);
		final EditText editprice  = (EditText)v.findViewById(R.id.edit_price_popup);
		edittitle.setText(nametitle);
		editprice.setText(priceTile);
		
		dialog.setView(v);
		dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		dialog.setPositiveButton("Đồng ý Thay Đổi", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				title.setText(edittitle.getText().toString());
				price.setText("Đơn Giá: "+editprice.getText().toString()+"đ");
				edit_price_invi.setText(editprice.getText().toString());
				return;
				//price.setText("Price"+cur.getInt(3)+"$");
			}
		});
		
		return dialog.create();
	}
	
	public Dialog onCreateDialog(int id,String nametitle)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Bạn chắc chắn muốn thay đổi :"+nametitle);
		dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		dialog.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				itemtask.deleteItem(theid);
				//onPause();
				Intent i =new Intent();
			}
		});
		
		return dialog.create();
	}
	
	protected void onPrepareDialog(int id, Dialog dialog) 
	{
		super.onPrepareDialog(id, dialog);
		return;
	}
	
	public void onItemSelected(AdapterView<?> parent, View v,
			int position, long id) 
	    {
		     Toast.makeText(this,url_imgs[position], 200).show();
		}
		
		public void onNothingSelected(AdapterView<?> parent) 
		{
		    
		}
		
		public void selectthis(View v)
		{
			ImageView i =(ImageView)v;
			String u = i.getTag().toString();
			urltext.setText(u);
			dia.hide();
			try
			{
			     icon.setImageBitmap(getBitmapFromAsset("item_img/"+u));
			}
			catch(IOException e)
			{
				Toast.makeText(this,e.toString(), 200).show();
			}
		}
		
		public void selectthisitem(View v)
		{
			CheckBox che = (CheckBox)v;
			if(che.isChecked())
			{
				customquanlin.setVisibility(View.VISIBLE);
				ischeck=1;
				String xxx = quan.getText().toString();
				if(xxx.equals("") || xxx.equals("0"))
					xxx = "1";
				customquan = Integer.parseInt(xxx);
				todayTime =getDateTime.getTime("dd-MM-yyyy");
			}
			else
			{
				customquanlin.setVisibility(View.INVISIBLE);
				ischeck =0;
				customquan = 0;
				todayTime= "";
			}
		}

		
		protected void onPause() 
		{
			super.onPause();
		}
		
		public void viewThisitem(View v)
		{
			LinearLayout lin = (LinearLayout)v;
			String xxx = lin.getTag().toString();
			theid = Integer.parseInt(xxx);
			Bundle bun = new Bundle();
			bun.putInt("idItem", theid);
			Intent i = new Intent(this,ShowDetail.class);
			startActivity(i);
		}
		
		
       
}
