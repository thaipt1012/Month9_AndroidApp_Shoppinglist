package android.shopingList.data;

import android.database.sqlite.SQLiteDatabase;

public class myItemTable 
{
    public static final String MYITEM_ID="_id";
    public static final String MYITEM_DATE="date";
    public static final String DATABASE_TABLE ="myItem";
    
    public static final String CREATE_TABLE ="create table "+DATABASE_TABLE +" ("+
    MYITEM_ID +" integer ,"+
    MYITEM_DATE +" text not null );";
    
    public static  void onCreate(SQLiteDatabase db)
    {
    	db.execSQL(CREATE_TABLE);
    }
    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists "+DATABASE_TABLE);
		myItemTable.onCreate(db);
	} 
}
