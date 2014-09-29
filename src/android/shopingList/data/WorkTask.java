package android.shopingList.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.shopingList.model.Work;


public class WorkTask 
{
    private static final String INSERT ="insert into "+WorkTable.DATABASE_TABLE+" ( "+
	    WorkTable.WORK_NAME+" , "+
	    WorkTable.WORK_TIME+" , "+
	    
	    WorkTable.WORK_CHECK+
	    		") values(?,?,?);";
    
    
    public SQLiteDatabase db;
    public SQLiteStatement dbStatement;
    public Context context;
    
    public WorkTask(Context context)
    {
    	this.context=context;
    	SQLiteOpenHelper openHelper = new OpenHelper(this.context);
    	db = openHelper.getWritableDatabase();
    	dbStatement=db.compileStatement(INSERT);
    	init();
    }
    
    public void close()
    {
    	db.close();
    }
    
    public long insertItem(Work item)
    {
    	dbStatement.clearBindings();
    	dbStatement.bindString(1, item.getContent());
    	dbStatement.bindString(2, item.getTime());
    	dbStatement.bindLong(3, item.getIsChecked());
    	return dbStatement.executeInsert();
    }
    
    public Cursor getAll()
    {
    	String query ="select * from "+WorkTable.DATABASE_TABLE;
    	Cursor c = db.rawQuery(query, null);
    	return c;
    }
    public void init()
	{
		Cursor cur = db.rawQuery("select * from "+WorkTable.DATABASE_TABLE, null);
		if(cur.getCount()==0)
		{
			Work item1 =new Work("An sang","07:30",0);
			Work item2 =new Work("Di cho","10:30",0);
			
			insertItem(item1);
			insertItem(item2);
			
		}
	}  
    public void delete(int id)
    {
    	db.delete(WorkTable.DATABASE_TABLE,
    			WorkTable.WORK_ID+" = ?",new String[]{String.valueOf(id)});
    }
    public void delete(String id){
    	db.delete(WorkTable.DATABASE_TABLE, WorkTable.WORK_ID +"= ? ", new String[]{id}) ;
    }
}
