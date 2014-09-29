package android.shopingList.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper 
{
	public Context context ;
	public static final String DATABASE_NAME ="myShop.db";

	public OpenHelper(Context context) 
	{
		super(context,DATABASE_NAME, null, 1);
	}
	
	@Override
	public synchronized void close() {
		super.close();
	}
	
	public void onOpen(final SQLiteDatabase db) 
	{
		super.onOpen(db);
		//db.execSQL("alter table "+ConfigTable.DATABASE_TABLE+" add column _idxx integer default 0");
		//db.execSQL(myItemTable.CREATE_TABLE);
		//db.execSQL("DROP TABLE "+ConfigTable.DATABASE_TABLE);
		//db.execSQL(ConfigTable.CREATE_TABLE);
	      if (!db.isReadOnly()) {
	         // versions of SQLite older than 3.6.19 don't support foreign keys
	         // and neither do any version compiled with SQLITE_OMIT_FOREIGN_KEY
	         // http://www.sqlite.org/foreignkeys.html#fk_enable
	         // 
	         // make sure foreign key support is turned on if it's there (should be already, just a double-checker)          
	         db.execSQL("PRAGMA foreign_keys=ON;");

	         // then we check to make sure they're on 
	         // (if this returns no data they aren't even available, so we shouldn't even TRY to use them)
	         Cursor c = db.rawQuery("PRAGMA foreign_keys", null);
	         if (c.moveToFirst()) {
	            int result = c.getInt(0);
	            Log.i("MyStudent", "SQLite foreign key support (1 is on, 0 is off): " + result);
	         } else {
	            // could use this approach in onCreate, and not rely on foreign keys it not available, etc.
	            Log.i("MyStudent", "SQLite foreign key support NOT AVAILABLE");
	            // if you had to here you could fall back to triggers
	         }
	         if (!c.isClosed()) {
	            c.close();
	         }
	      }
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		ShopItemTable.onCreate(db);
		ShopCategoryTable.onCreate(db);
		myItemTable.onCreate(db);
		ConfigTable.onCreate(db);
		WorkTable.onCreate(db) ;
		//db.execSQL("alter table "+ShopItemTable.DATABASE_TABLE+" add column ischecked integer not null");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		ShopItemTable.onUpgrade(db, oldVersion, newVersion);
		ShopCategoryTable.onUpgrade(db, oldVersion, newVersion);
		ConfigTable.onUpgrade(db, oldVersion, newVersion);
		WorkTable.onUpgrade(db, oldVersion, newVersion);
	}

}
