package android.shopingList.data;

import android.database.sqlite.SQLiteDatabase;

public class ShopCategoryTable 
{
	public static final String CATE_ID ="_id";
	public static final String CATE_NAME ="cateName";
	public static final String CATE_URL ="cateUrl";
	
	public static final String DATABASE_TABLE ="shopingCate";
	public static final String CREAT_TABLE="create table "+ShopCategoryTable.DATABASE_TABLE+" ( "+
	ShopCategoryTable.CATE_ID+" integer primary key autoincrement ,"+
	ShopCategoryTable.CATE_NAME+" text not null ," +
	ShopCategoryTable.CATE_URL+" text not null " +
	
			");";
	
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREAT_TABLE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		db.execSQL("drop table if exists "+DATABASE_TABLE);
		ShopCategoryTable.onCreate(db);
	} 
}
