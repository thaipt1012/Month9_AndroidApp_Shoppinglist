package android.shopingList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TaskActivity extends ListActivity{
	Button add ;
	Button DelTask;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.task_main) ;
		
	
		//======Khi click nut "Them mot cong viec"=========
		add = (Button)findViewById(R.id.buttonAddTask) ;
		add.setOnClickListener(onAddTask);
		
		
        //====Khi click nut "Xoa cong viec da chon"=========
		DelTask =  (Button )findViewById(R.id.buttonDelTask);
        DelTask.setOnClickListener(onDelTask);
        
	}//End of onCreate
	
	
	
	
	
	
	
	
	
    //======Khi click nut "Them mot cong viec"=========
    private View.OnClickListener onAddTask= new View.OnClickListener()
    {
		public void onClick(View v)
		{
			Intent addTask = new Intent(getApplicationContext(),TaskAdd.class) ;
			startActivity(addTask);
		}
	};
	
	
	//======Khi click nut "Xoa cong viec da chon"=========
    private View.OnClickListener onDelTask= new View.OnClickListener()
    {
		public void onClick(View v)
		{
			//Neu tick = true
			//Thi xoa khoi CSDL
		}
	};
	
	//-----sua mot cong viec da co------
	public void SuaTask ()
	{
		//bat activity AddTask len voi du lieu o cac editText la cac du lieu cua Task duoc chon
		//Khi click nut Huy : set lai thong tin cac editText nhu tren
		//Khi click nut Them : sua cac thong tin ve Task duoc chon trong CSDL
		//Khi click nut QuayLai : finish()
		
	}
	
}
