package android.shopingList;

import android.graphics.Bitmap;

public class GridInfo {
	private Bitmap mIcon;
	private String mName;

	public GridInfo(Bitmap icon, String name) {
		mIcon = icon;
		mName = name;
	}

	public void setIcon(Bitmap icon) {
		mIcon = icon;
	}
	public Bitmap getIcon() {
		return mIcon;
	}

	public void setName(String name) {
		mName = name;
	}
	public String getName() {
		return mName;
	}
}
