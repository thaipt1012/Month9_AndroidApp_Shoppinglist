/*package android.shopingList; 

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TaskAdd extends Activity{
	EditText editTextTenCV;
	EditText editTextThgianTH;
	EditText editTextNgayTH;
	EditText editTextDiaDiem;
	EditText editTextGhiChu;
	CheckBox checkBoxNhacNho;
	Button buttonThem;
	Button buttonHuy;
	Button buttonQuayLai;
	Button buttonXem;
	String str1;
	String str2;
	String str3;
	String str4;
	String str5;
	
	
    Task_DB_Adapter mDb; 
    List<Task> array; 
    
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.task_add) ;
		
		
		final ListView lv=(ListView)findViewById(R.id.listView1); 
		
		
		buttonThem = (Button) findViewById (R.id.buttonThem);
		buttonThem.setOnClickListener(onThem);
		
		buttonHuy = (Button) findViewById (R.id.buttonHuy);
		buttonHuy.setOnClickListener(onHuy);
		
		buttonQuayLai = (Button) findViewById (R.id.buttonQuayLai);
		buttonQuayLai.setOnClickListener(onQuayLai);
		

		
		
		
		buttonXem = (Button) findViewById (R.id.Xem);
		buttonXem.setOnClickListener(new OnClickListener() { 
            
            public void onClick(View v) { 
                // TODO Auto-generated method stub 
                mDb.openDB(); 
                array=new ArrayList<Task>(); 
                Cursor mCursor = mDb.getAllStudent(); 
                if (mCursor.moveToFirst()) 
                { 
                    do 
                    { 
                        array.add(new Task(mCursor.getString(1),mCursor.getString(2))); 
                        //Toast.makeText(getBaseContext(), "id: " + mCursor.getString(0) + ", name: " + mCursor.getString(1), Toast.LENGTH_SHORT).show(); 
                          
                    } while (mCursor.moveToNext()); 
                } 
                 
                Task_Adapter adapter=new Task_Adapter(getApplicationContext(), array); 
                lv.setAdapter(adapter); 
            } 
        }); 
	}//End of onCreate
	
	
	//Khi click nut "Them"
	public View.OnClickListener onThem = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Editable txt;
			str1 = "";
			str2 = "";
			str3 = "";
			str4 = "";
			str5 = "";
			
			editTextTenCV = (EditText) findViewById (R.id.editTextTenCV);
			txt = editTextTenCV.getText();
			str1 = txt.toString();
			
			editTextThgianTH = (EditText) findViewById (R.id.editTextThgianTH);
			txt = editTextThgianTH.getText();
			str2 = txt.toString();
			
			editTextNgayTH = (EditText) findViewById (R.id.editTextNgayTH);
			txt = editTextNgayTH.getText();
			str3 = txt.toString();
			
			editTextDiaDiem = (EditText) findViewById (R.id.editTextDiaDiem);
			txt = editTextDiaDiem.getText();
			str4 = txt.toString();
			
			editTextGhiChu = (EditText) findViewById (R.id.editTextGhiChu);
			txt = editTextGhiChu.getText();
			str5 = txt.toString();
			
			checkBoxNhacNho = (CheckBox) findViewById (R.id.checkBoxNhacNho);

			
			//Them cong viec vao CSDL
			mDb.openDB(); 
            mDb.insert(str1,str2); 
		}
	};
	
	//Khi click nut "Huy"
	public View.OnClickListener onHuy = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			editTextTenCV.setText("");
			editTextThgianTH.setText("");
			editTextNgayTH.setText("");
			editTextDiaDiem.setText("");
			editTextGhiChu.setText("");
		}
	};
	
	//Khi click nut "QuayLai"
	public View.OnClickListener onQuayLai = new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	};
}
*/


package android.shopingList; 

import java.util.ArrayList; 
import java.util.List; 

import android.app.Activity; 
import android.database.Cursor; 
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.ListView; 
import android.widget.Toast; 

public class TaskAdd extends Activity { 
    /** Called when the activity is first created. */ 
    Task_DB_Adapter mDb; 
    List<Task> array; 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.task_add); 
        final EditText ed1=(EditText)findViewById(R.id.editTextTenCV); 
        final EditText ed2=(EditText)findViewById(R.id.editTextNgayTH); 
        final ListView lv=(ListView)findViewById(R.id.listView1); 
        Button bt1=(Button)findViewById(R.id.buttonThem); 
        Button bt2=(Button)findViewById(R.id.Xem); 
         
         
        mDb=new Task_DB_Adapter(this); 
        View.OnClickListener bt1OnClick=new OnClickListener() { 
             
            public void onClick(View arg0) { 
                // TODO Auto-generated method stub 
                mDb.openDB(); 
                mDb.insert(ed1.getText().toString(),ed2.getText().toString()); 
            } 
        }; 
        bt1.setOnClickListener(bt1OnClick); 
        bt2.setOnClickListener(new OnClickListener() { 
             
            public void onClick(View v) { 
                // TODO Auto-generated method stub 
                mDb.openDB(); 
                array=new ArrayList<Task>(); 
                Cursor mCursor = mDb.getAllStudent(); 
                if (mCursor.moveToFirst()) 
                { 
                    do 
                    {
                        array.add(new Task(mCursor.getString(1),mCursor.getString(2)));
                          
                    } while (mCursor.moveToNext()); 
                } 
                 
                Task_Adapter adapter=new Task_Adapter(getApplicationContext(), array); 
                lv.setAdapter(adapter); 
            } 
        }); 
         
    } 
}