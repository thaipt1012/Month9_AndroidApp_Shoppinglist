package android.shopingList.data;

import android.database.sqlite.SQLiteDatabase;

public class WorkTable 
{
	public static final String WORK_ID ="_id";
	public static final String WORK_NAME ="workContent";
	public static final String WORK_TIME ="timeContent";
	public static final String WORK_CHECK = "checkContent" ;
	
	public static final String DATABASE_TABLE ="workTable";
	public static final String CREAT_TABLE="create table "+WorkTable.DATABASE_TABLE+" ( "+
	WorkTable.WORK_ID+" integer primary key autoincrement ,"+
	WorkTable.WORK_NAME+" text not null ," +
	WorkTable.WORK_TIME+" text not null ," +
	WorkTable.WORK_CHECK+" integer not null default 0 "+
	
			");";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREAT_TABLE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("drop table if exists "+DATABASE_TABLE);
		WorkTable.onCreate(db);
	} 
}
