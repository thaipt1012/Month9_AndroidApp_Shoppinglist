package android.shopingList.data;

import android.database.sqlite.SQLiteDatabase;

public class ShopItemTable 
{
	public static final String ITEM_ID ="_id";
	public static final String ITEM_NAME ="itemName";
	public static final String ITEM_DES ="itemDescription";
	public static final String ITEM_PRICE="itemPrice";
	public static final String ITEM_QUANTITY="itemQuantity";
	public static final String ITEM_RATE ="itemRate";
	public static final String ITEM_URL="itemURL";
	public static final String ITEM_CATE="itemCategory";
	public static final String ITEM_CHECK= "ischecked";
	public static final String ITEM_TIMECHECK ="timeCheck";
	
	public static final String DATABASE_TABLE ="shopingItem";
	
	public static final String CREAT_TABLE="create table "+ShopItemTable.DATABASE_TABLE+" ( "+
	ShopItemTable.ITEM_ID+" integer primary key autoincrement ,"+
	ShopItemTable.ITEM_NAME+" text not null ,"+
	ShopItemTable.ITEM_DES+" text not null,"+
	ShopItemTable.ITEM_PRICE+" text not null,"+
	ShopItemTable.ITEM_QUANTITY+" integer not null,"+
	ShopItemTable.ITEM_RATE+" integer not null,"+
	ShopItemTable.ITEM_URL+" text not null,"+
	ShopItemTable.ITEM_CATE+" integer not null," +
	ShopItemTable.ITEM_CHECK+" integer not null default 0 , "+
	ShopItemTable.ITEM_TIMECHECK+" text not null"+
			" );";
	
	/*public ShopItemTable(SQLiteDatabase db)
	{
		//db.execSQL(CREAT_TABLE);
	}*/
	public static void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREAT_TABLE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists "+DATABASE_TABLE);
		ShopItemTable.onCreate(db);
	} 
}
