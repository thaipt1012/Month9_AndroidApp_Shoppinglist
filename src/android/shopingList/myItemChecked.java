package android.shopingList;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.shopingList.data.CategoryTask;
import android.shopingList.data.ItemTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class myItemChecked extends ListActivity implements AdapterView.OnItemSelectedListener
{
	public static int staticID;
	public static String cateName;
	Cursor c;
	ListView thelist;
	Button selectDate,pickDate,gocustom;
	LinearLayout selectdiv;
	EditText pickDateField;
	TextView totalMoney,temCate;
	private int curMonth,curDay,curYear;
	static final int DATE_DIALOG_ID = 0;
	ItemTask itemtask;
	ItemAdapter adapter;
	Spinner spin;
	CategoryTask catetask;
	String[] catenames;
	List<String> alls;
	String[] allnameitems;
	
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
        new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, 
                                  int monthOfYear, int dayOfMonth) 
            {
                curYear = year;
                curMonth = monthOfYear;
                curDay = dayOfMonth;
                updateDisplay();
            }
        };
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemchecked);
		selectDate= (Button)findViewById(R.id.selectDate);
		gocustom = (Button)findViewById(R.id.goCustomChecked);
	    selectdiv = (LinearLayout)findViewById(R.id.selectDiv);
	    pickDateField =(EditText)findViewById(R.id.pickDateField);
	    spin = (Spinner)findViewById(R.id.selectCate);
	    totalMoney = (TextView)findViewById(R.id.totalMoney);
	    temCate = (TextView)findViewById(R.id.tempolaryCate);
	    spin.setOnItemSelectedListener(this);
	    itemtask = new ItemTask(this);
	    catetask = new CategoryTask(this);
	    totalMoney.setText("Tổng số tiền :"+itemtask.getTotal("","")+"đ");
	  
	    
	    List<String> allcates = catetask.getAllNameCate();
	    allcates.add(0,"Tất cả sản phẩm");
	    
	    catenames = allcates.toArray(new String[allcates.size()]);
	    ArrayAdapter<String> spinadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,catenames);
	    spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spin.setAdapter(spinadapter);
	    
	    
	    
	    
	    thelist=getListView();
	    Cursor c = itemtask.getCheckedItem("");
	    adapter =new ItemAdapter(this,c);
	    thelist.setAdapter(adapter);
	    thelist.getBackground().setAlpha(199);
	    
	    //Drawable draw_img = 
	    
	    
	    selectDate.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View v) 
			{
				showDialog(DATE_DIALOG_ID);
			}
		});
	    
	    gocustom.setOnClickListener(new View.OnClickListener() 
	    {
			public void onClick(View v)
			{
				Cursor c;
				String xxxnamecate = temCate.getText().toString();
				String thedate = pickDateField.getText().toString();
				if(thedate.length()==8)
					thedate="0"+thedate;
				c = itemtask.getCheckedItem(xxxnamecate, thedate);
				totalMoney.setText("Tổng số tiền:"+itemtask.getTotal(thedate,xxxnamecate)+"đ");
				adapter =new ItemAdapter(myItemChecked.this,c);
				thelist.setAdapter(adapter);
			}
		});
	    final Calendar cal = Calendar.getInstance();
	    curDay = cal.get(Calendar.DAY_OF_MONTH);
	    curMonth = cal.get(Calendar.MONTH);
	    curYear = cal.get(Calendar.YEAR);
	    
	    String curdays,curmonths;
		int nowmonth = curMonth +1;
		if(curDay<10)
			curdays = "0"+curDay;
		else
			curdays = ""+curDay;
		if(curMonth<10)
			curmonths="0"+nowmonth;
		else
			curmonths = ""+nowmonth;
	    pickDateField.setText(curdays+"-"+curmonths+"-"+curYear);
	}
	protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
        case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(curYear, curMonth, curDay);
                break;
        }
    }   
	
	public Dialog onCreateDialog(int id)
	{
		switch(DATE_DIALOG_ID)
		{
		case DATE_DIALOG_ID:
		   return new DatePickerDialog(this,mDateSetListener,curYear,curMonth,curDay);
		}
		return null;
	}
	//----------------------------------------------------->>>>>>>>>>>>>>>>>  111.
	public void updateDisplay()
	{
		
		String curdays,curmonths;
		int nowmonth = curMonth +1;
		if(curDay<10)
			curdays = "0"+curDay;
		else
			curdays = ""+curDay;
		if(curMonth<10)
			curmonths="0"+nowmonth;
		else
			curmonths = ""+nowmonth;
		pickDateField.setText(new StringBuilder(
				).append(curdays).append("-")
				.append(curmonths).append("-")
				.append(curYear)
		);
		
		
	}
	//----------------------------------------------------->>>>>>>>>>>>>>>>>  222.
	
	public void onItemSelected(AdapterView<?> parent,View v,int position,long id)
    {
		if(position!=0)
		{
			
		staticID = catetask.getIdByName(catenames[position]);
		cateName=catenames[position];
		getAll();
		alls = itemtask.getAllNameItems(String.valueOf(staticID));
		allnameitems = alls.toArray(new String[alls.size()]);
		
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
			
		}
    }

	public void getAll()
	{
		c=itemtask.getAllItemByCate(staticID);
		ItemAdapter adapter = new ItemAdapter(this,c);
		thelist.setAdapter(adapter);
	}
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		
	}
	
	public void toggleSelect(View v)
	{
		
	}
	
	protected void onRestart() 
	{
		super.onRestart();
		Cursor c = itemtask.getCheckedItem("");
	    adapter =new ItemAdapter(this,c);
	    thelist.setAdapter(adapter);
	    totalMoney.setText("Total is :"+itemtask.getTotal("","")+"$");
	}
	
	
	
    
}
