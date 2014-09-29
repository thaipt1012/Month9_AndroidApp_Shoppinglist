package android.shopingList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer; 
import java.util.TimerTask;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.shopingList.data.WorkTask;
import android.shopingList.model.Work;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class taskWork extends ListActivity { 
	private SharedPreferences pref ;
	Timer newTimer ;
	Timer timer ;
	Integer h ;
	Integer s ;
	EditText hourEdit, workEnter ,minuteEdit;
	
    //Các hằng dùng cho tạo Option Menu
    private static final int DELETE_WORK = Menu.FIRST;
    private static final int ABOUT = Menu.FIRST + 2;
    LayoutInflater inflater ;
    WorkTask work;
	ListWorkAdapter arrayAdapter;
	ListView list ;
	LinearLayout lin ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);
        lin = (LinearLayout)findViewById(R.id.bodywork) ;
        lin.getBackground().setAlpha(190);
        list =getListView() ;
        list.getBackground().setAlpha(190);
        work = new WorkTask(this);
        Cursor c =work.getAll();
        arrayAdapter = new ListWorkAdapter(this, 
               c);
        list.setAdapter(arrayAdapter);
        list.setItemsCanFocus(false) ;
        
        workEnter = (EditText) findViewById(R.id.work_enter);
        hourEdit = (EditText) findViewById(R.id.hour_edit);
        minuteEdit = (EditText) findViewById(R.id.minute_edit);
        pref = getSharedPreferences("myPref", MODE_PRIVATE);
        
        
        final Button button = (Button) findViewById(R.id.button);
        
        //Tạo list view cho danh sách công việc
       
        
        OnClickListener add = new OnClickListener() {
        	
            public void onClick(View v) {
            	
            	if (workEnter.getText().toString().equals("") ||
                        hourEdit.getText().toString().equals("") ||
                        minuteEdit.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(taskWork.this);
                    builder.setTitle("Bạn chưa nhập đủ thông tin");
                    builder.setMessage("Hãy nhập đầy đủ thông tin");
                    builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub                            
                        }                        
                    });
                    builder.show();
                }
                else if(Integer.parseInt(hourEdit.getText().toString()+"")>24
            	||(Integer.parseInt(minuteEdit.getText().toString()+"")>60 )){
                	AlertDialog.Builder builder = new AlertDialog.Builder(taskWork.this);
                    builder.setTitle("Bạn dã nhập sai thời gian");
                    builder.setMessage("Hãy nhập lại");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub                            
                        }                        
                    });
                    builder.show();
                	
                }
                else {
                	 String timeContent ;
                    String workContent = workEnter.getText().toString();
                    String hour = hourEdit.getText().toString() ;
                    String minute = minuteEdit.getText().toString() ;
                    String h=round(hour) ;
                    String s = round(minute) ;
                  
                    timeContent = h+":"+s ;
                    Work newWork = new Work(workContent, timeContent, 0);
                    work.insertItem(newWork) ;
                    arrayAdapter.notifyDataSetChanged();
                    resetList();
                    workEnter.setText("");
                    hourEdit.setText("");
                    minuteEdit.setText("");
                }
            }
            
        };
        
        button.setOnClickListener(add);   
        
    }
    private String round(String time){
    	String t ;
    	if(time.length()==2){
    		t =time ;
    	}
    	else 
    		t = "0"+time ;
    	return t  ;
    }
    public void resetList(){
     Cursor c = work.getAll() ;
     ListWorkAdapter adapter = new ListWorkAdapter(this, c) ;
     list.setAdapter(adapter) ;
     
    }
    //Tạo Option Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);        
        menu.add(0, DELETE_WORK, 0,"Xóa công việc" ).setIcon(android.R.drawable.ic_delete);        
        menu.add(0, ABOUT, 0,"Nhắc nhở" ).setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }
    
    //Xử lý sự kiện khi các option trong Option Menu được lựa chọn
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {            
            case DELETE_WORK: {
                deleteCheckedWork();
                break;
            }            
            case ABOUT: {
            	
				for(int i = 0 ;i< list.getChildCount() ; i ++){
			        LinearLayout lin = (LinearLayout)list.getChildAt(i);
					CheckBox xxx = (CheckBox)lin.getChildAt(0);
					if(xxx.isChecked()){
						Bundle bun = (Bundle)xxx.getTag() ;
						String time = bun.getString("time") ;
						String name = bun.getString("name");
						SharedPreferences.Editor editor = pref.edit();
			            editor.putString("time", time) ;
			            editor.putString("name", name) ;
			            editor.commit() ;
			            Intent Alarm = new Intent(getBaseContext(),myService.class) ;
			            startService(Alarm) ;
			            
			           }
			    }
						
            	
                break;
            }
        }
        
        return true;
       }
    
  		

    private void deleteCheckedWork() {
    	try
		{
			boolean isshowpopup=false;
			List<String> idworks = new ArrayList<String>();
			List<String> nameworks = new ArrayList<String>();
			String[] listidcate,listnameworks;
			try
			{
			for(int i=0;i<list.getChildCount();i++)
			{
				LinearLayout lin = (LinearLayout)list.getChildAt(i);
				
				CheckBox xxx = (CheckBox)lin.getChildAt(0);
				if(xxx.isChecked())
				{
					isshowpopup=true;
					Bundle mybun =(Bundle)xxx.getTag();
					String nameCate = mybun.getString("name");
					int idcate = mybun.getInt("id");
					idworks.add(""+idcate);
					nameworks.add(nameCate);
				}
			}
			}
			catch(Exception e)
			{
				Toast.makeText(taskWork.this,e.toString(), 300).show();
			}
			if(isshowpopup)
			{
				listidcate = idworks.toArray(new String[idworks.size()]);
				listnameworks = nameworks.toArray(new String[nameworks.size()]);
				Dialog dialog = onCreateDialog(DELETE_WORK,listnameworks,listidcate);
				dialog.show();
			}
			else
				Toast.makeText(taskWork.this,"Ban chưa chọn gì cả ", 200).show();
		}
		catch(Exception e)
		{
			Toast.makeText(taskWork.this,e.toString(), 200).show();
		}
    }
    public Dialog onCreateDialog(int id,final String[] names,final String[] ids)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Bạn chắc chắn muốn xóa :");
		inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View v = inflater.inflate(R.layout.delete_cates,null);
		final ListView delete_list = (ListView)v.findViewById(R.id.delete_cate_list);
		listCateDelAdapter del_adapter=new listCateDelAdapter(taskWork.this,names,ids);
		delete_list.setAdapter(del_adapter);
		dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				return;
			}
		});
		dialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener() 
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
						work.delete(ids[i]);
						Toast.makeText(taskWork.this,names[i]+" đã xóa",200).show();
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
}