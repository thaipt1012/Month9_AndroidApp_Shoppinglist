package android.shopingList.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class CategoryTask 
{
    private static final String INSERT=" insert into "+ShopCategoryTable.DATABASE_TABLE +" ("+
    ShopCategoryTable.CATE_NAME+" , " +
    ShopCategoryTable.CATE_URL+
    		" ) values(?,?)";
    
    public SQLiteDatabase db;
    public SQLiteStatement dbStatement;
    public Context context;
    
    public CategoryTask(Context context)
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
    
    public long insert(String name,String url)
    {
    	dbStatement.clearBindings();
    	dbStatement.bindString(1, name);
    	dbStatement.bindString(2, url);
    	return dbStatement.executeInsert();
    }
    
    public Cursor getAll()
    {
    	String query ="select * from "+ShopCategoryTable.DATABASE_TABLE;
    	Cursor c = db.rawQuery(query, null);
    	return c;
    }
    public void init()
	{
		Cursor cur = db.rawQuery("select * from "+ShopCategoryTable.DATABASE_TABLE, null);
		if(cur.getCount()==0)
		{
			insert("Rau xanh","rau.jpg");
			insert("Cac loai thit","thit.jpg");
		}
	}
    
    public List<String> getAllNameCate()
    {
    	List<String> names = new ArrayList<String>();
    	String query ="select cateName from "+ShopCategoryTable.DATABASE_TABLE;
    	Cursor c = db.rawQuery(query, null);
    	c.moveToFirst();
    	while(!c.isAfterLast())
    	{
    		names.add(c.getString(0));
    		c.moveToNext();
    	}
    	if(!c.isClosed())
    		c.close();
    	return names;
    }
    
    public String getNameCateById(int id)
    {
    	String query= "select "+ShopCategoryTable.CATE_NAME +" from "+ShopCategoryTable.DATABASE_TABLE+" where "+
    	ShopCategoryTable.CATE_ID +" = 0 limit 1";
    	Cursor c= db.rawQuery(query,null);
    	c.moveToFirst();
    	return c.getString(0);
    }
    
    public boolean notDuplicateName(String name)
    {
    	String query ="select "+ShopCategoryTable.CATE_NAME+" from "+ShopCategoryTable.DATABASE_TABLE
    	+" where "+ShopCategoryTable.CATE_NAME+" like ?";
    	Cursor c = db.rawQuery(query, new String[]{name});
    	if(c.getCount()>0)
    		return false;
    	return true;
    }
    
    public void delete(int id)
    {
    	db.delete(ShopCategoryTable.DATABASE_TABLE,
    			ShopCategoryTable.CATE_ID+" = ?",new String[]{String.valueOf(id)});
    }
    
    public void delete(String id)
    {
    	db.delete(ShopCategoryTable.DATABASE_TABLE,
    			ShopCategoryTable.CATE_ID+" = ?",new String[]{id});
    }
    
    public void upDate(int id,String name,String url)
    {
    	ContentValues values = new ContentValues();
    	values.put(ShopCategoryTable.CATE_NAME, name);
    	values.put(ShopCategoryTable.CATE_URL, url);
    	db.update(ShopCategoryTable.DATABASE_TABLE, values,ShopCategoryTable.CATE_ID+" = ?",
    			new String[]{String.valueOf(id)});
    }
    public int getIdByName(String name)
    {
    	String query = "select "+ShopCategoryTable.CATE_ID+" from "+ShopCategoryTable.DATABASE_TABLE+
    " where "+ShopCategoryTable.CATE_NAME+" like ?";
    	Cursor c = db.rawQuery(query, new String[]{name});
    	c.moveToFirst();
    	return c.getInt(0);
    }
}
