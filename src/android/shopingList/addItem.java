package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.shopingList.data.CategoryTask;
import android.shopingList.data.ItemTask;
import android.shopingList.model.ShopItem;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class addItem extends Activity implements AdapterView.OnItemSelectedListener
{
	EditText nameItem,priceItem,quanItem,rateItem,desItem,urlItem;
	Spinner spincate;
	Button cancel, addmore;
	TextView header;
	ItemTask itemtask;
	CategoryTask catetask;
	public static String namecate;
	public static int idcate;
	AssetManager assets;
	String[] url_imgs;
	ImageView icon;
	Dialog dia;
	LayoutInflater inflater;
	String[] catenames;
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.additem);
		itemtask = new ItemTask(this);
		catetask = new CategoryTask(this);
		
		header=(TextView)findViewById(R.id.header);
		nameItem = (EditText)findViewById(R.id.nameItem);
		priceItem = (EditText)findViewById(R.id.priceItem);
		quanItem = (EditText)findViewById(R.id.quanItem);
		rateItem = (EditText)findViewById(R.id.rateItem);
		urlItem = (EditText)findViewById(R.id.imgItem);
		desItem = (EditText)findViewById(R.id.desItem);
		addmore=(Button)findViewById(R.id.addMoreItem);
		cancel =(Button)findViewById(R.id.cancel);
		icon = (ImageView)findViewById(R.id.selectImgAdd1);
		icon.setImageResource(R.drawable.noimage);
		spincate = (Spinner)findViewById(R.id.selectCate2);
		spincate.setOnItemSelectedListener(this);
		
		List<String> allcates = catetask.getAllNameCate();
		catenames = allcates.toArray(new String[allcates.size()]);
	    ArrayAdapter<?> spinadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,catenames);
	    spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spincate.setAdapter(spinadapter);
	    
		
		assets = getApplicationContext().getAssets();
        try
        {
        	url_imgs=assets.list("item_img");
        }
		catch(IOException e)
		{
			
		}
		
		
		Intent i = this.getIntent();
		Bundle bun = i.getExtras();
		namecate = bun.getString("nameCate");
		idcate   = bun.getInt("idCate");
		if(idcate==-1)
			idcate=1;
		spincate.setSelection(findPos(namecate));
		if(namecate.equals(""))
		{
		    Toast.makeText(this,"Không có gì",Toast.LENGTH_SHORT).show();
		}
		
		addmore.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				try
				{
			     ShopItem item = new ShopItem(nameItem.getText().toString(),
			    		 desItem.getText().toString(),
			    		 priceItem.getText().toString(),
			    		 quanItem.getText().toString(),
			    		 rateItem.getText().toString(),
			    		 urlItem.getText().toString(),
			    		 Long.valueOf(idcate),
			    		 0,""
			    		 );
			     itemtask.insertItem(item);
			     Bundle bun = new Bundle();
			     Intent i = new Intent(addItem.this,ShowItems.class);
			     bun.putInt("idCate", idcate);
			     bun.putString("nameCate",namecate);
			     i.putExtras(bun);
			     startActivity(i);
				}
				catch(Exception e)
				{
					desItem.setText(e.toString());
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish() ;
			}
		});
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
					Toast.makeText(addItem.this, e.toString(), 200).show();
				}
			}
		});
		
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
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		GridView grid =(GridView)inflater.inflate(R.layout.grid_view, null);
		try
        {
			imageAdapter iadapter = new imageAdapter(addItem.this,url_imgs,"item_img");
        	grid.setAdapter(iadapter);
        }
        catch(Exception e)
        {
        	Toast.makeText(addItem.this, e.toString(), 200).show();
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
		urlItem.setText(u);
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
	
	public void onItemSelected(AdapterView<?> parent,View v,int position,long id)
    {
		idcate = catetask.getIdByName(catenames[position]);
    }

	
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		
	}
	
	public int findPos(String name)
	{
		for(int i=0;i<catenames.length;i++)
		{
			if(catenames[i].equals(name))
				return i;
		}
		return 0;
	}
	
	
    
}
