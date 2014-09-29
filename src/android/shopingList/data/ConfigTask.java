package android.shopingList.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class ConfigTask 
{
	private static final String INSERT ="insert into "+ConfigTable.DATABASE_TABLE +" ( "+
	ConfigTable.CONFIG_NAME+" ,"+
	ConfigTable.CONFIG_ISPOPUP+" ,"+
	ConfigTable.CONFIG_DEFAULT_QUAN+" , "+
	ConfigTable.CONFIG_HOST+" ) values(?,?,?,?)";
	
	private SQLiteDatabase db;
	private SQLiteStatement dbStatement;
	private Context context;
	
	public ConfigTask(Context c)
	{
		this.context=c;
		SQLiteOpenHelper openHelper = new OpenHelper(c);
		db = openHelper.getWritableDatabase();
		dbStatement = db.compileStatement(INSERT);
		init();
	}
	
	public long insert(String name,int ispop,int defaultquan,String host)
	{
		dbStatement.clearBindings();
		dbStatement.bindString(1, name);
		dbStatement.bindLong(2, ispop);
		dbStatement.bindLong(3, defaultquan);
		dbStatement.bindString(4, host);
		return dbStatement.executeInsert();
	}
	
	public void upDate(String name,int ispop,int defaultquan,String host)
	{
		ContentValues values = new ContentValues();
		values.put(ConfigTable.CONFIG_NAME, name);
		values.put(ConfigTable.CONFIG_ISPOPUP,ispop);
		values.put(ConfigTable.CONFIG_DEFAULT_QUAN,defaultquan);
		values.put(ConfigTable.CONFIG_HOST,host);
		db.update(ConfigTable.DATABASE_TABLE, values,"_id = ?", new String[]{String.valueOf(1)});
	}
	
	//public void upDate(String )
	
	public int isPopup()
	{
		String query= "select "+ConfigTable.CONFIG_ISPOPUP +" from "+ConfigTable.DATABASE_TABLE +" limit 1";
		Cursor cur = db.rawQuery(query, null);
		cur.moveToFirst();
		int ispop = cur.getInt(0);
		return ispop;
	}
	
	public String getName()
	{
		String query= "select "+ConfigTable.CONFIG_NAME +" from "+ConfigTable.DATABASE_TABLE +" limit 1";
		Cursor cur = db.rawQuery(query, null);
		cur.moveToFirst();
		String name = cur.getString(0);
		return name;
	}
	
	public String getHost()
	{
		String query= "select "+ConfigTable.CONFIG_HOST +" from "+ConfigTable.DATABASE_TABLE +" limit 1";
		Cursor cur = db.rawQuery(query, null);
		cur.moveToFirst();
		String host = cur.getString(0);
		return host;
	}
	
	public int getDefaultQuan()
	{
		String query= "select "+ConfigTable.CONFIG_DEFAULT_QUAN +" from "+ConfigTable.DATABASE_TABLE +" limit 1";
		Cursor cur = db.rawQuery(query, null);
		cur.moveToFirst();
		int ispop = cur.getInt(0);
		return ispop;
	}
	
	public void init()
	{
		Cursor cur = db.rawQuery("select * from "+ConfigTable.DATABASE_TABLE, null);
		if(cur.getCount()==0)
		{
			insert("Hà Kim Tùng",0,1,"");
		}
	}
	
	public Cursor getConfig()
	{
		Cursor cur = db.rawQuery("select * from "+ConfigTable.DATABASE_TABLE+" limit 1", null);
		return cur;
	}
	public int getCount()
	{
		Cursor cur = db.rawQuery("select * from "+ConfigTable.DATABASE_TABLE, null);
		return cur.getCount();
	}

}
