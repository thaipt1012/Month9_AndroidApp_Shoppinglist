package android.shopingList;

import java.util.List;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.shopingList.data.CategoryTask;
import android.shopingList.data.ConfigTask;
import android.shopingList.data.ItemTask;
import android.shopingList.model.getDateTime;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShowItems extends ListActivity implements TextWatcher, AdapterView.OnItemSelectedListener
{
	public static int staticID;
	public static String cateName;
	Button addbtn,seabtn,showmyItem;
	AutoCompleteTextView seafield;
	ListView thelist;
	ItemTask itemtask;
	CategoryTask catetask;
	ConfigTask contask;
	Spinner spincate;
	int id;
	String namecate;
	Cursor c;
	public static final int ARE_YOU_SURE = 1;
	public static final int CUSTOM_QUAN=2;
	public static int priceItem;
	String[] allnameitems;
	String[] catenames;
	List<String> alls;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showallitems);
		
		addbtn = (Button)findViewById(R.id.addButton);
		seabtn = (Button)findViewById(R.id.searchButton);
		showmyItem= (Button)findViewById(R.id.myItemChecked);
		seafield = (AutoCompleteTextView)findViewById(R.id.searchField);
		spincate = (Spinner)findViewById(R.id.selectCate1);
		seafield.addTextChangedListener(this);
		thelist = getListView();
		thelist.getBackground().setAlpha(190);
		thelist.setItemsCanFocus(false);
		
		contask = new ConfigTask(this);
		itemtask = new ItemTask(this);
		catetask= new CategoryTask(this);
		
		spincate.setOnItemSelectedListener(this);
		
		
		Intent i = this.getIntent();
		Bundle bun = i.getExtras();
		
		
		if(bun!=null)
		{
			id = bun.getInt("idCate");
			namecate=bun.getString("nameCate");
			staticID=id;
			cateName=namecate;
			getAll();
			alls = itemtask.getAllNameItems(String.valueOf(staticID));
		
			allnameitems = alls.toArray(new String[alls.size()]);
			seafield.setAdapter(new ArrayAdapter<String>(this,
					R.layout.auto_com_textview,
					allnameitems));
			List<String> allcates = catetask.getAllNameCate();
			allcates.add(0,"Tất cả sản phẩm");
			catenames = allcates.toArray(new String[allcates.size()]);
		    ArrayAdapter<String> spinadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,catenames);
		    spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spincate.setAdapter(spinadapter);
		    spincate.setSelection(findPos(cateName));
			
		}else
		{
			id=staticID;
			namecate=cateName;
			getAll();
		}
		
		addbtn.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				try
				{
					Bundle bun = new Bundle();
					bun.putInt("idCate",staticID);
					bun.putString("nameCate", cateName);
					Intent i = new Intent(ShowItems.this,addItem.class);
					i.putExtras(bun);
					startActivity(i);
				}
				catch(Exception e)
				{
					seafield.setText(e.toString());
				}
			}
		});
		
		showmyItem.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				Intent i = new Intent( ShowItems.this,myItemChecked.class);
				startActivity(i);
			}
		});
		
		seabtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				seafield.setText(null) ;
				getAll();
			}
		});
		
		
	}
    
	public void getAll()
	{
		c=itemtask.getAllItemByCate(staticID);
		ItemAdapter adapter = new ItemAdapter(this,c);
		thelist.setAdapter(adapter);
	}
	
	public void toggleSelect(View v)
	{
		LinearLayout lin = (LinearLayout)v.getParent().getParent();
		RelativeLayout rela = (RelativeLayout)lin.getChildAt(0);
		CheckBox check = (CheckBox)rela.getChildAt(3);
		TextView title = (TextView)rela.getChildAt(1);
		TextView quantext =(TextView)rela.getChildAt(2);
		String idItem = check.getTag().toString();
		int theidItem = Integer.parseInt(idItem);
		if(check.isChecked())
		{
			String thedate=getDateTime.getTime("dd-MM-yyyy");
			itemtask.selectItem(theidItem,thedate);
			//int thequan;
			/*if((thequan=contask.getDefaultQuan())>0)
			{
				quantext.setText("("+thequan+")");
				itemtask.updateQuan(thequan, theidItem);
				Toast.makeText(this,"Giá Trị mặc định: "+thequan,200).show();
			}*/
			/*else
			{
				//Dialog dialog= onCreateDialog(title.getText().toString(),theidItem,quantext);
				//dialog.show();
			}*/
		}
		else
		{
			
			if(contask.isPopup()==0)
			{
				itemtask.unselectItem(theidItem);
				quantext.setText("");
			}
			else
			{
			     Dialog dialog =onCreateDialog(ARE_YOU_SURE,title.getText().toString(),theidItem,check,quantext);
			     dialog.show();
			}
		}
		
	}
	
	public Dialog onCreateDialog(int id,String name,final int itemid,final CheckBox c,final TextView quanti)
	{
		switch(id)
		{
		case ARE_YOU_SURE:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle(name);
			dialog.setMessage("Bạn có chắc không chọn sản phẩm này ?");
			dialog.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					itemtask.unselectItem(itemid);
					quanti.setText("");
					return;
				}
			});
			
			dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) 
				{
					c.setChecked(true);
					return;
				}
			});
			return dialog.create();
		}	
		return null;
	}
	public Dialog onCreateDialog(String name,final int itemid,final TextView quan)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(name);
		dialog.setMessage("Số lượng sản phẩm cần mua :");
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		final View v =inflater.inflate(R.layout.customquantity, null);
		final EditText quanfield =(EditText)v.findViewById(R.id.fieldCusQuan);
		dialog.setView(v);
	
		dialog.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				String soluong = quanfield.getText().toString();
				quan.setText("("+soluong+")");
				int thequan = Integer.parseInt(soluong);
				itemtask.updateQuan(thequan, itemid);
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

	
	protected void onPause() 
	{
		super.onPause();
		itemtask.close();
		c.close();
	}

	
	protected void onStop() 
	{
		super.onStop();
		itemtask.close();
		c.close();
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.itemmenu, menu);
		return true;
	}

	
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		Intent i ;
		switch(item.getItemId())
		{
		case R.id.myItemChecked:
			i = new Intent(ShowItems.this,myItemChecked.class);
			startActivity(i);
			break;
		case R.id.addItem:
			Bundle bun = new Bundle();
			bun.putInt("idCate", id);
			bun.putString("nameCate", namecate);
			i = new Intent(ShowItems.this,addItem.class);
			i.putExtras(bun);
			startActivity(i);
			break;
			//i = new Intent(Mainmenu.this,);
		case R.id.mainmenu:
			i = new Intent(ShowItems.this,Mainmenu.class);
			startActivity(i);
			break;
		case R.id.goCategory:
			i = new Intent(ShowItems.this,ShopingListActivity.class);
			startActivity(i);
			break;
		default :
			//i = new Intent(Mainmenu.this,ShopingListActivity.class);
			break;
		}
		
		return true;
	}
	
	public void showdetail(View v)
	{
		LinearLayout lin = (LinearLayout)v.getParent().getParent();
		RelativeLayout rela = (RelativeLayout)lin.getChildAt(0);
		TextView title = (TextView)rela.getChildAt(1);
		String xxx= title.getTag().toString();
		int theid = Integer.parseInt(xxx);
		Toast.makeText(this, "Đây là: "+theid,Toast.LENGTH_SHORT).show();
	}

	
	protected void onRestart() 
	{
		super.onRestart();
		itemtask = new ItemTask(this);
		getAll();
	}

	
	protected void onResume() 
	{
		super.onResume();
	}

	
	protected void onStart() 
	{
		super.onStart();
	}
	
	public void onTextChanged(CharSequence s, int start, int before,int count) 
	{
	
	}
	
	public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
	// needed for interface, but not used
	}
	
	public void afterTextChanged(Editable s) 
	{
		c = itemtask.findItemByName(seafield.getText().toString());
		if(c.getCount()>0)
		{
			ItemAdapter adapter = new ItemAdapter(this,c);
			thelist.setAdapter(adapter);
		}
	}
	
	public void onItemSelected(AdapterView<?> parent,View v,int position,long id)
    {
		if(position!=0)
		{
			
		staticID = catetask.getIdByName(catenames[position]);
		cateName=catenames[position];
		getAll();
		alls = itemtask.getAllNameItems(String.valueOf(staticID));
		allnameitems = alls.toArray(new String[alls.size()]);
		seafield.setAdapter(new ArrayAdapter<String>(this,
				R.layout.auto_com_textview,
				allnameitems));
		}
		else
		{
			staticID = -1;
			cateName=catenames[1];
			c = itemtask.getAllItemByCate(-1);
			ItemAdapter adapter = new ItemAdapter(this,c);
			thelist.setAdapter(adapter);
			alls = itemtask.getAllNameItems("");
			allnameitems = alls.toArray(new String[alls.size()]);
			seafield.setAdapter(new ArrayAdapter<String>(this,
					R.layout.auto_com_textview,
					allnameitems));
		}
    }

	
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		// TODO Auto-generated method stub
		
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
