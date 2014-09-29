package android.shopingList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class listCateDelAdapter extends ArrayAdapter<String>
{
	public Context context;
	LayoutInflater inflate;
	public String[] ids;
	public String names[];
	public listCateDelAdapter(Context context,String[] names,String ids[]) 
	{
		super(context,R.layout.listview_delete,R.id.title_listview_delete,names);
		this.context=context;
		inflate = (LayoutInflater)context.getSystemService
	      (Context.LAYOUT_INFLATER_SERVICE);
		this.ids=ids;
		this.names=names;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = super.getView(position, convertView, parent);
		TextView titlec = (TextView) v.findViewById(R.id.title_listview_delete);
		CheckBox checkb = (CheckBox) v.findViewById(R.id.checkItCate);
		titlec.setText(names[position]);
		checkb.setChecked(true);
		checkb.setTag(ids[position]);
		return v;
	}
}
