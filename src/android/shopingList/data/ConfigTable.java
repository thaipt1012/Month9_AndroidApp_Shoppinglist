package android.shopingList.data;

import android.database.sqlite.SQLiteDatabase;

public class ConfigTable 
{
	public static final String CONFIG_ID ="_id";
	public static final String CONFIG_NAME ="name";
	public static final String CONFIG_ISPOPUP = "isAlert";
	public static final String CONFIG_DEFAULT_QUAN ="defaultquan";
	public static final String CONFIG_HOST="hostimg";
	
	public static final String DATABASE_TABLE ="configtable";
	
	public static final String CREATE_TABLE ="create table "+DATABASE_TABLE +" ("+
	CONFIG_ID+" integer primary key autoincrement ,"+
	CONFIG_NAME +" text not null , "+
	CONFIG_ISPOPUP+" integer , "+
	CONFIG_DEFAULT_QUAN+" integer , "+
	CONFIG_HOST+" text not null" +
			");";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_TABLE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists "+DATABASE_TABLE);
		ConfigTable.onCreate(db);
	} 

}
