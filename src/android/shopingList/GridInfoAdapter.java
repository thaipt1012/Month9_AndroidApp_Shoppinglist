package android.shopingList;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class GridInfoAdapter extends BaseAdapter {
	private Context mContext;
	private List<GridInfo> mListAppInfo;
	int mGalleryItemBackground;
	public GridInfoAdapter(Context context, List<GridInfo> list) {
		mContext = context;
		mListAppInfo = list;
		
		TypedArray attr = mContext.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        attr.recycle();
	}

	
	public int getCount() {
		return mListAppInfo.size();
	}

	
	public Object getItem(int position) {
		return mListAppInfo.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}


	 public View getView(int position, View convertView, ViewGroup parent) {
		GridInfo entry = mListAppInfo.get(position);
		ViewHolder holder = null;

		if(convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.grid, null);
			holder = new ViewHolder();
			holder.ivIcon = (ImageView)convertView.findViewById(R.id.ivIcon);
			holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}

		holder.ivIcon.setImageBitmap(entry.getIcon());
		holder.ivIcon.setScaleType(ImageView.ScaleType.FIT_XY);
		holder.ivIcon.setBackgroundResource(mGalleryItemBackground);
		holder.tvName.setText(entry.getName());
		return convertView;
	}

	static class ViewHolder {
		ImageView ivIcon;
		TextView tvName;
	}
}
