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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.shopingList.data.CategoryTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class EditCate extends Activity
{
	EditText urlimg,titlecate;
	ImageView icon;
	Button doneEdit;
	CategoryTask catetask;
	String[] url_imgs;
	AssetManager assets;
	String namecate;
	int idCate;
	String urlCate;
	Dialog dia;
	LayoutInflater inflater;
	String path = "cate_img";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_cate);
		urlimg = (EditText)findViewById(R.id.editCateImgUrl);
		titlecate = (EditText)findViewById(R.id.editCateTitle);
		doneEdit = (Button)findViewById(R.id.editCate);
		icon = (ImageView)findViewById(R.id.editselectImgCate1);
		catetask = new CategoryTask(this);
		
		Intent i = this.getIntent();
		Bundle bun = i.getExtras();
		namecate = bun.getString("nameCate");
		idCate = bun.getInt("idCate");
		urlCate = bun.getString("urlCate");
		assets = getApplicationContext().getAssets();
		
		urlimg.setText(urlCate);
		titlecate.setText(namecate);
		try
        {
        	url_imgs=assets.list("cate_img");
        	
        }
		catch(IOException e)
		{
			
		}
		
		try
		{
		    icon.setImageBitmap(getBitmapFromAsset("cate_img/"+urlCate));
		}
		catch(IOException e)
		{
			
		}
		
		doneEdit.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) 
			{			
				catetask.upDate(idCate, titlecate.getText().toString(),
						urlimg.getText().toString());
				Intent i =new Intent(EditCate.this,ShopingListActivity.class);
				startActivity(i);
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
					Toast.makeText(EditCate.this, e.toString(), 200).show();
				}
			}
		});
	}
	public Dialog onCreateDialog(int id)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		GridView grid =(GridView)inflater.inflate(R.layout.grid_view, null);
		try
        {
			imageAdapter iadapter = new imageAdapter(EditCate.this,url_imgs,path);
        	grid.setAdapter(iadapter);
        }
        catch(Exception e)
        {
        	Toast.makeText(EditCate.this, e.toString(), 200).show();
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
	
	protected void onPrepareDialog(int id, Dialog dialog) 
	{
		super.onPrepareDialog(id, dialog);
		return;
	}
	
	public Bitmap getBitmapFromAsset(String strName) throws IOException
    {
        AssetManager assetManager = getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	
	public void selectthis(View v)
	{
		ImageView i =(ImageView)v;
		String u = i.getTag().toString();
		urlimg.setText(u);
		dia.hide();
		try
		{
		     icon.setImageBitmap(getBitmapFromAsset(path+"/"+u));
		}
		catch(IOException e)
		{
			Toast.makeText(this,e.toString(), 200).show();
		}
	}

}
