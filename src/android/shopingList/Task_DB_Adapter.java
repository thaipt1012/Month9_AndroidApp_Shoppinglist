package android.shopingList; 

import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteDatabase.CursorFactory; 
import android.database.sqlite.SQLiteOpenHelper; 
import android.util.Log; 


public class Task_DB_Adapter { 
    public static final String SID = "studentId"; 
    public static final String NUMBER="number"; 
    public static final String SNAME = "name"; 
     
      
    public static final String DB_NAME = "CSDL"; 
    public static final String DB_TABLE = "Student"; 
    public static final int DB_VERSION = 2; 
     
    private final Context mContext; 
    private SQLiteDatabase mDB; 
    private DBHelper mDBHelper; 
     
    public Task_DB_Adapter(Context c){ 
        mContext=c; 
    } 
    private static class DBHelper extends SQLiteOpenHelper 
    { 
  
        public DBHelper(Context context, String name, CursorFactory factory, 
                int version) { 
            super(context, name, factory, version); 
            // TODO Auto-generated constructor stub 
        } 
  
        @Override 
        public void onCreate(SQLiteDatabase db) { 
            // TODO Auto-generated method stub 
            try 
            { 
                db.execSQL("CREATE TABLE Student(number integer PRIMARY KEY autoincrement, studentID text,name text);"); 
            } 
            catch(SQLException ex) 
            { 
                ex.printStackTrace(); 
            } 
        } 
  
        @Override 
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
            // TODO Auto-generated method stub 
            Log.i("DBAdapter", "Updating database..."); 
            db.execSQL("DROP TABLE IF EXISTS Student"); 
            onCreate(db); 
        } 
    } 
      
    public Task_DB_Adapter openDB() 
    { 
        mDBHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION); 
        mDB = mDBHelper.getWritableDatabase(); 
        return this; 
    } 
      
    public void closeDB() 
    { 
        mDBHelper.close(); 
    } 
      
    public long insert(String _studentId, String _name) 
    { 
        ContentValues cv = new ContentValues(); 
        cv.put(SID,_studentId); 
        cv.put(SNAME, _name); 
        return mDB.insert(DB_TABLE, null, cv); 
    } 
      
/*    public boolean edit(String _number,String _id, String _name) 
    { 
        ContentValues cv = new ContentValues(); 
        cv.put(NUMBER,_number); 
        cv.put(SID, _id); 
        cv.put(SNAME, _name); 
        return mDB.update(DB_TABLE, cv, NUMBER + "=" + _number, null) > 0; 
    } 
      
    public boolean remove(int _number) 
    { 
        return mDB.delete(DB_TABLE, SID + "=" + _number, null) > 0; 
    } */
      
    public Cursor getAllStudent() 
    { 
        return mDB.query(DB_TABLE, new String[]{NUMBER,SID, SNAME}, null, null, null, null, null); 
    } 
      
    public Cursor getStudentById(int _number) 
    { 
        Cursor mCursor = mDB.query(true, DB_TABLE, new String[]{NUMBER,SID, SNAME}, NUMBER + "=" + _number, null, null, null, null, null); 
        if(mCursor != null) 
        { 
            mCursor.moveToFirst(); 
        } 
          
        return mCursor; 
    } 
}  