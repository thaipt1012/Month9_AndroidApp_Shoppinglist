package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.shopingList.data.CategoryTask;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

import android.widget.TextView;
import android.widget.Toast;

public class ShopingListActivity extends ListActivity
{
	Button addbtn;
	Button updatebtn;
	ImageView deletebtn,toggleslide;
	EditText titleText,urlText;
	ImageView handleslide,icon;
	ListView thelist;
	CategoryTask task;
	TextView list_cates;
	String[] url_imgs;
	SelectImg_Adapter img_adapter;
	SlidingDrawer slidingdrawer;
	public static final int ARE_YOU_SURE = 1;
	public static int currentCate =0;
	public static int sliding_state=0;
	LayoutInflater inflater;
	AssetManager assets;
	Dialog dia;
	
	
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addbtn=(Button)findViewById(R.id.addCate);
        updatebtn=(Button)findViewById(R.id.updateCate);
        deletebtn = (ImageView)findViewById(R.id.deleteornot);
        toggleslide = (ImageView)findViewById(R.id.toggleslide);
        titleText=(EditText)findViewById(R.id.addCateTitle);
        list_cates = (TextView)findViewById(R.id.list_cates);
        urlText=(EditText)findViewById(R.id.addCateImg);
        slidingdrawer = (SlidingDrawer)findViewById(R.id.drawer);
        handleslide = (ImageView)findViewById(R.id.handle);
        
        icon = (ImageView)findViewById(R.id.selectImgCate1);
        icon.setImageResource(R.drawable.noimage);
       
        assets = getApplicationContext().getAssets();
        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try
        {
        	url_imgs=assets.list("cate_img");
        }
        catch(IOException e)
        {
        	urlText.setText(e.toString());
        }
        catch(Exception e)
        {
        	urlText.setText(e.toString());
        }
        
        thelist = getListView();
       
        
        task = new CategoryTask(this);
        Cursor c =task.getAll();
        CategoryAdapter adapter =new CategoryAdapter(this,c);
        thelist.setAdapter(adapter);
        thelist.getBackground().setAlpha(100);
        addbtn.setOnClickListener(new View.OnClickListener() 
        {
		    public void onClick(View v) 
			{
				String ttitle=titleText.getText().toString();
				String turl=urlText.getText().toString();
				if(task.notDuplicateName(ttitle))
				{
				task.insert(ttitle, turl);
				resetList();
				slidingdrawer.animateToggle();
				}
				else
					Toast.makeText(ShopingListActivity.this,"Sản phẩm đã có trong Shop :"+ttitle,100).show();
			}
		});
        try
        {
        deletebtn.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				try
				{
					boolean isshowpopup=false;
					List<String> idcates = new ArrayList<String>();
					List<String> namecates = new ArrayList<String>();
					String[] listidcate,listnamecates;
					try
					{
					for(int i=0;i<thelist.getChildCount();i++)
					{
						LinearLayout lin = (LinearLayout)thelist.getChildAt(i);
						RelativeLayout rela = (RelativeLayout)lin.getChildAt(0);
						CheckBox xxx = (CheckBox)rela.getChildAt(3);
						if(xxx.isChecked())
						{
							isshowpopup=true;
							Bundle mybun =(Bundle)xxx.getTag();
							String ditnhau = mybun.getString("nameCate");
							int idcate = mybun.getInt("idCate");
							idcates.add(""+idcate);
							namecates.add(ditnhau);
						}
					}
					}
					catch(Exception e)
					{
						Toast.makeText(ShopingListActivity.this,e.toString(), 300).show();
					}
					if(isshowpopup)
					{
						listidcate = idcates.toArray(new String[idcates.size()]);
						listnamecates = namecates.toArray(new String[namecates.size()]);
						Dialog dialog = onCreateDialog(ARE_YOU_SURE,listnamecates,listidcate);
						dialog.show();
					}
					else
						Toast.makeText(ShopingListActivity.this,"Không có mặt hang nào được chọn", 200).show();
				}
				catch(Exception e)
				{
					Toast.makeText(ShopingListActivity.this,e.toString(), 200).show();
				}
			}
		});
        }
        catch(Exception e)
        {
        	Toast.makeText(this, e.toString(),100).show();
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
					Toast.makeText(ShopingListActivity.this, e.toString(), 200).show();
				}
			}
		});
        
        toggleslide.setOnClickListener(new View.OnClickListener() 
        {
			public void onClick(View v) 
			{
				if(slidingdrawer.isOpened())
				{
					slidingdrawer.close();
				}
				else
				{
					slidingdrawer.open();
				}
			}
		});
    }
    
    
    protected void onListItemClick(ListView l, View v, int position, long id) 
    {
    	try
    	{
        	TextView text=(TextView)v.findViewById(R.id.titleCate);
        	
    		
    		Bundle bun = new Bundle();
        	bun.putInt("idCate", position);
        	bun.putString("nameCate",text.getText().toString());
	    	Intent i = new Intent(this,ShowItems.class);
	    	i.putExtras(bun);
	    	startActivity(i);
    	}
    	catch(Exception e)
    	{
    		titleText.setText(e.toString());
    	}
	}
    
    
    public void resetList()
    {
    	Cursor c =task.getAll();
        CategoryAdapter adapter =new CategoryAdapter(this,c);
        thelist.setAdapter(adapter);
    }
	
	public void editCate(View v)
	{
		RelativeLayout rela = (RelativeLayout)v.getParent();
		ImageView edit = (ImageView)rela.getChildAt(2);
		TextView titleofcate =(TextView)rela.getChildAt(1);
		Bundle bun = (Bundle)edit.getTag();
		bun.putString("nameCate",titleofcate.getText().toString());
		Intent i = new Intent(this,EditCate.class);
		i.putExtras(bun);
		startActivity(i);
	}
	
	public Dialog onCreateDialog(int id,final String[] names,final String[] ids)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Bạn có chắc muốn xóa :");
		//inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View v = inflater.inflate(R.layout.delete_cates,null);
		final ListView delete_list = (ListView)v.findViewById(R.id.delete_cate_list);
		listCateDelAdapter del_adapter=new listCateDelAdapter(ShopingListActivity.this,names,ids);
		delete_list.setAdapter(del_adapter);
		dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		dialog.setPositiveButton("Chắc Chắn", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				boolean isreload = false;
				for(int i=0;i<delete_list.getChildCount();i++)
				{
					RelativeLayout rela = (RelativeLayout)delete_list.getChildAt(i);
					CheckBox delete_or_not = (CheckBox)rela.getChildAt(2);
					if(delete_or_not.isChecked())
					{
						task.delete(ids[i]);
						Toast.makeText(ShopingListActivity.this,names[i]+" is removed",200).show();
						isreload=true;
					}
				}
				if(isreload)
					resetList();
				return;
			}
		});
		dialog.setView(v);
		return dialog.create();
	}
	
	private Bitmap getBitmapFromAsset(String strName) throws IOException
    {
        InputStream istr = assets.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	
	public Dialog onCreateDialog(int id)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		
		GridView grid =(GridView)inflater.inflate(R.layout.grid_view, null);
		try
        {
			imageAdapter iadapter = new imageAdapter(ShopingListActivity.this,url_imgs,"cate_img");
        	grid.setAdapter(iadapter);
        }
        catch(Exception e)
        {
        	Toast.makeText(ShopingListActivity.this, e.toString(), 200).show();
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
	
	public void selectthis(View v)
	{
		ImageView i =(ImageView)v;
		String u = i.getTag().toString();
		urlText.setText(u);
		dia.hide();
		try
		{    
		     icon.setImageBitmap(getBitmapFromAsset("cate_img/"+u));
		}
		catch(IOException e)
		{
			Toast.makeText(this,e.toString(), 200).show();
		}
	}
}