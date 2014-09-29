package android.shopingList; 

import java.util.List; 

import android.content.Context; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.BaseAdapter; 
import android.widget.TextView; 

public class Task_Adapter extends BaseAdapter {     
    private Context mContext; 
    private List<Task> list_item; 
    public Task_Adapter(Context c,List<Task> obj) { 
        mContext = c; 
        list_item=obj; 
    } 

     
    public int getCount() { 
        return list_item.size(); 
    }   
    public Object getItem(int position){          
        return null;     
    }     
     
    public long getItemId(int position) {         
        return 0;     
    } 

    public View getView(int position, View convertView, ViewGroup parent) { 
        View v; 
        if(convertView==null){ 
            LayoutInflater inflater = LayoutInflater.from(mContext); 
            v = inflater.inflate(R.layout.task, null); 

            
            
            TextView TenCV = (TextView)v.findViewById(R.id.TenCV); 
            TenCV.setText(list_item.get(position).getTenCV()); 
            
            TextView Ngay = (TextView)v.findViewById(R.id.Ngay); 
            Ngay.setText(list_item.get(position).getNgay());
            
            TextView Gio = (TextView)v.findViewById(R.id.Gio); 
            Gio.setText(list_item.get(position).getGio());
            
            
            TextView DiaDiem = (TextView)v.findViewById(R.id.DiaDiem); 
            DiaDiem.setText(list_item.get(position).getDiaDiem());
            
            TextView GhiChu = (TextView)v.findViewById(R.id.GhiChu); 
            GhiChu.setText(list_item.get(position).getGhiChu());
        } 
        else 
        { 
            v = convertView; 
        } 

        return v; 
    } 
}