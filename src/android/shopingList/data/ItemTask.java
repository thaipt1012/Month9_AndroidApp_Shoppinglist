package android.shopingList.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.shopingList.model.ShopItem;


public class ItemTask 
{
    private static final String INSERT ="insert into "+ShopItemTable.DATABASE_TABLE+" ( "+
    ShopItemTable.ITEM_NAME+" , "+ShopItemTable.ITEM_DES+" , "+
    ShopItemTable.ITEM_PRICE+" , "+ShopItemTable.ITEM_QUANTITY+" , "+
    ShopItemTable.ITEM_RATE+" , "+ShopItemTable.ITEM_URL+" , "
    +ShopItemTable.ITEM_CATE+" ," +
    ShopItemTable.ITEM_CHECK+" , "+
    ShopItemTable.ITEM_TIMECHECK+
    		") values(?,?,?,?,?,?,?,?,?);";
    
    
    public SQLiteDatabase db;
    public SQLiteStatement dbStatement;
    public Context context;
    
    public ItemTask(Context context)
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
    
    public long insertItem(ShopItem item)
    {
    	dbStatement.clearBindings();
    	dbStatement.bindString(1, item.getName());
    	dbStatement.bindString(2, item.getDes());
    	dbStatement.bindString(3, item.getPrice());
    	dbStatement.bindString(4, item.getQuan());
    	dbStatement.bindString(5, item.getRate());
    	dbStatement.bindString(6, item.getUrl());
    	dbStatement.bindLong(7, item.getCate());
    	dbStatement.bindLong(8,item.getIsCheck());
    	dbStatement.bindString(9, item.getTimeCheck());
    	//dbStatement.bind
    	return dbStatement.executeInsert();
    }
    
    public void deleteItem(long id)
    {
    	db.delete(ShopItemTable.DATABASE_TABLE,ShopItemTable.ITEM_ID+" = ?",new String[]{String.valueOf(id)} );
    }
    
    public void upDate(ShopItem item)
    {
    	ContentValues values = new ContentValues();
    	values.put(ShopItemTable.ITEM_NAME, item.getName());
    	values.put(ShopItemTable.ITEM_DES, item.getDes());
    	values.put(ShopItemTable.ITEM_PRICE, item.getPrice());
    	values.put(ShopItemTable.ITEM_QUANTITY, item.getQuan());
    	values.put(ShopItemTable.ITEM_RATE, item.getRate());
    	values.put(ShopItemTable.ITEM_URL, item.getUrl());
    	values.put(ShopItemTable.ITEM_CATE, item.getCate());
    	values.put(ShopItemTable.ITEM_CHECK, item.getIsCheck());
    	values.put(ShopItemTable.ITEM_TIMECHECK, item.getTimeCheck());
    	
    	db.update(ShopItemTable.DATABASE_TABLE, values, ShopItemTable.ITEM_ID+" = ?",
    			new String[]{String.valueOf(item.getId())});
    }
    
    public Cursor getAllItemByCate(long id)
    {
    	if(id==-1)
    	{
    		String query = "select * from "+ShopItemTable.DATABASE_TABLE;
        	Cursor c =db.rawQuery(query, null);
        	return c;
    	}
    	String query = "select * from "+ShopItemTable.DATABASE_TABLE +" where "+ShopItemTable.ITEM_CATE+
    	" = ? ";
    	Cursor c =db.rawQuery(query, new String[]{String.valueOf(id)});
    	return c;
    }
    
    public Cursor getItem(long id)
    {
    	String query = "select * from "+ShopItemTable.DATABASE_TABLE +" where "+ShopItemTable.ITEM_ID+
    	" = ?";
    	Cursor c =db.rawQuery(query, new String[]{String.valueOf(id)});
    	return c;
    }
    //public List<>
    public void findItem()
    {
    	
    }
    
    public void selectItem(int id,String thedate)
    {
    	ContentValues values = new ContentValues();
    	values.put(ShopItemTable.ITEM_CHECK, 1);
    	values.put(ShopItemTable.ITEM_TIMECHECK,thedate);
    	db.update(ShopItemTable.DATABASE_TABLE, values, ShopItemTable.ITEM_ID+" = ?",
    			new String[]{String.valueOf(id)});
    }
    public void unselectItem(int id)
    {
    	ContentValues values = new ContentValues();
    	values.put(ShopItemTable.ITEM_CHECK, 0);
    	values.put(ShopItemTable.ITEM_QUANTITY,0);
    	db.update(ShopItemTable.DATABASE_TABLE, values, ShopItemTable.ITEM_ID+" = ?",
    			new String[]{String.valueOf(id)});
    }
    
    public void updateQuan(int thequan,int id)
    {
    	ContentValues values = new ContentValues();
    	values.put(ShopItemTable.ITEM_QUANTITY, thequan);
    	db.update(ShopItemTable.DATABASE_TABLE, values,ShopItemTable.ITEM_ID+" = ?",
    			new String[]{String.valueOf(id)});
    }
    
    public Cursor getCheckedItem(String customDate)
    {
    	if(customDate.equals(""))
    	{
    	    String query = "select * from "+ShopItemTable.DATABASE_TABLE +" where "+ShopItemTable.ITEM_CHECK+" = ?";
    	    Cursor c = db.rawQuery(query, new String[]{String.valueOf(1)});
    	    return c;
    	   // Toast.makeText(context,query, 300).show();
    	}
    	else
    	{
    		String query ="select * from "+ShopItemTable.DATABASE_TABLE +" where "+ShopItemTable.ITEM_CHECK+" = ? and "+
    		ShopItemTable.ITEM_TIMECHECK+ " like ?";
    		
    		Cursor c = db.rawQuery(query, new String[]{String.valueOf(1),customDate});
    		return c;
    	}
    }
    public Cursor getCheckedItem(int id)
    {
    	Cursor c;
    	if(id==0)
    		c = getCheckedItem("");
    	else
    	{
    		String query = "select * from "+ShopItemTable.DATABASE_TABLE +" where "+ShopItemTable.ITEM_CHECK+" = ? and "+
    		ShopItemTable.ITEM_CATE+ " = ?";
    		id--;
    		c=db.rawQuery(query, new String[]{String.valueOf(1),String.valueOf(id)});
    	}
    	return c;
    	
    	
    }
    public void init()
	{
		Cursor cur = db.rawQuery("select * from "+ShopItemTable.DATABASE_TABLE, null);
		if(cur.getCount()==0)
		{
			ShopItem item1 =new ShopItem("Ca chep ngon","","5000","1","3","thitca.jpg",2,0,"");
			ShopItem item2 =new ShopItem("Thit ga","","7000","1","3","thitga.jpg",2,0,"");
			ShopItem item3 =new ShopItem("Thit lon","","15000","1","3","thitlon.jpg",2,0,"");
			ShopItem item4 =new ShopItem("Rau cai","","5000","1","3","raucai.jpg",1,0,"");
			ShopItem item5 =new ShopItem("Rau muong","","2000","1","4","raumuong.jpg",1,0,"");
			insertItem(item1);
			insertItem(item2);
			insertItem(item3);
			insertItem(item4);
			insertItem(item5);
		}
	}
    public int getTotal(String date,String cateName)
    {
    	int total =0;
    	String query;
    	if(date.equals("") && cateName.equals(""))
    	{
	    	query = "select sum("+ShopItemTable.ITEM_QUANTITY+"*"+ShopItemTable.ITEM_PRICE+") from "+
	    	ShopItemTable.DATABASE_TABLE+
	    	" where "+ShopItemTable.ITEM_CHECK + " = ?";
	    	Cursor cur = db.rawQuery(query, new String[]{String.valueOf(1)});
	    	cur.moveToFirst();
	    	total = cur.getInt(0);
    	}
    	else
    	if(cateName.equals("") && !date.equals(""))
    	{
    		query = "select sum("+ShopItemTable.ITEM_QUANTITY+"*"+ShopItemTable.ITEM_PRICE+") from "+
    		ShopItemTable.DATABASE_TABLE+" where "+ShopItemTable.ITEM_CHECK +" = '1' and "+
    		ShopItemTable.ITEM_TIMECHECK+" = ?";
    		Cursor cur = db.rawQuery(query, new String[]{date});
	    	cur.moveToFirst();
	    	total = cur.getInt(0);
    	}
    	else
    	if(!cateName.equals("") && date.equals(""))
    	{
    		query ="select sum( "+ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_QUANTITY+"*"+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_PRICE+" ) from "+
    		ShopItemTable.DATABASE_TABLE+" , "+ShopCategoryTable.DATABASE_TABLE+" where "+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CATE+ " = "+
    		ShopCategoryTable.DATABASE_TABLE+"."+ShopCategoryTable.CATE_ID+" and "+
    		ShopCategoryTable.DATABASE_TABLE+"."+ShopCategoryTable.CATE_NAME+ " like ? and "+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CHECK+" = '1'";
    		Cursor cur = db.rawQuery(query, new String[]{cateName});
	    	cur.moveToFirst();
	    	total = cur.getInt(0);
    	}
    	else
    	{
    		//query = "select sum("+ShopItemTable.ITEM_QUANTITY+"*"+ShopItemTable.ITEM_PRICE+") from "+
    		query ="select sum( "+ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_QUANTITY+"*"+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_PRICE+" ) from "+
    		ShopItemTable.DATABASE_TABLE+" , "+ShopCategoryTable.DATABASE_TABLE+" where "+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CATE+ " = "+
    		ShopCategoryTable.DATABASE_TABLE+"."+ShopCategoryTable.CATE_ID+" and "+
    		ShopCategoryTable.DATABASE_TABLE+"."+ShopCategoryTable.CATE_NAME+ " like ? and "+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CHECK+" = '1' and "+
    		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_TIMECHECK+" = ? ";
    		Cursor cur = db.rawQuery(query, new String[]{cateName,date});
	    	cur.moveToFirst();
	    	total = cur.getInt(0);
    	}
    	
    	return total;
    }
    
    public Cursor getImgs()
    {
    	String query = "select "+ShopItemTable.ITEM_ID+" , "+ShopItemTable.ITEM_NAME+" , "+
    	ShopItemTable.ITEM_URL+" from "+ShopItemTable.DATABASE_TABLE;
    	Cursor cur = db.rawQuery(query, null);
    	return cur;
    }
    
    public Cursor getItemByCateName(String name)
    {  
        	String query = "select * from "+ShopItemTable.DATABASE_TABLE+" , "
        	+ShopCategoryTable.DATABASE_TABLE
        	+" where "+ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CATE+
        	" = "+ShopCategoryTable.DATABASE_TABLE+"."+
        	ShopCategoryTable.CATE_ID+" and "+ShopCategoryTable.DATABASE_TABLE+"."
        	+ShopCategoryTable.CATE_NAME+" like ? and "+ShopItemTable.DATABASE_TABLE+"."+
        	ShopItemTable.ITEM_CHECK+" = 1"
        	;
        	Cursor c =db.rawQuery(query, new String[]{name});
        	return c;
    }
    
    public Cursor getCheckedItem(String cateName,String date)
    {
    	String query;
    	Cursor cur;
    	if(cateName.equals("") && date.equals(""))
    	{
    	    return getCheckedItem("");
    	}
    	else
    	if(cateName.equals("") && !date.equals(""))
    	{
    		return getCheckedItem(date);
    	}
    	else
    	if (!cateName.equals("") && date.equals(""))
    	{
    		return getItemByCateName(cateName);
    	}
    	query="select * from "+
		ShopItemTable.DATABASE_TABLE+" , "+ShopCategoryTable.DATABASE_TABLE+" where "+
		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CATE+ " = "+
		ShopCategoryTable.DATABASE_TABLE+"."+ShopCategoryTable.CATE_ID+" and "+
		ShopCategoryTable.DATABASE_TABLE+"."+ShopCategoryTable.CATE_NAME+ " like ? and "+
		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_CHECK+" = '1' and "+
		ShopItemTable.DATABASE_TABLE+"."+ShopItemTable.ITEM_TIMECHECK+" = ? ";
		cur = db.rawQuery(query, new String[]{cateName,date});
    	cur.moveToFirst();
    	return cur;
    }
    
    public Cursor findItemByName(String name)
    {
    	String query ="select * from "+ShopItemTable.DATABASE_TABLE+" where "+
    	ShopItemTable.ITEM_NAME+" like ?";
    	Cursor cur = db.rawQuery(query, new String[]{name});
    	return cur;
    }
    
    //public Cursor findItemByName(String name)
    
    public List<String> getAllNameItems(String id)
    {
    	List<String> allnames = new ArrayList<String>();
    	Cursor c;
    	String query;
    	if(id.equals(""))
    	{
    		query = "select "+ShopItemTable.ITEM_NAME+" from "+ShopItemTable.DATABASE_TABLE;
    		c = db.rawQuery(query, null);
    	}
    	else
    	{
    		query = "select "+ShopItemTable.ITEM_NAME+" from "+ShopItemTable.DATABASE_TABLE+
    		" where "+ShopItemTable.ITEM_CATE+" = ?";
    		c = db.rawQuery(query, new String[]{id});
    	}
    	c.moveToFirst();
    	while(!c.isAfterLast())
    	{
    		allnames.add(c.getString(0));
    		c.moveToNext();
    	}
    	if(!c.isClosed())
    	{
    		c.close();
    	}
    	return allnames;
    }
    
    public void fastUpdate(int id,String name,String price,String iurl,int ischeck,int quan,String daygetitem)
    {
    	ContentValues values = new ContentValues();
    	values.put(ShopItemTable.ITEM_NAME, name);
    	values.put(ShopItemTable.ITEM_PRICE, price);
    	values.put(ShopItemTable.ITEM_URL, iurl);
    	values.put(ShopItemTable.ITEM_CHECK, ischeck);
    	values.put(ShopItemTable.ITEM_QUANTITY, quan);
    	values.put(ShopItemTable.ITEM_TIMECHECK, daygetitem);
    	db.update(ShopItemTable.DATABASE_TABLE, values,
    			ShopItemTable.ITEM_ID+" = ?", new String[]{String.valueOf(id)});
    }

    
   
}
