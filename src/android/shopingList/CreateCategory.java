package android.shopingList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.shopingList.GridInfoAdapter.ViewHolder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;

public class CreateCategory extends Activity{
	Gallery ds ;
	String []cate_img ;
	String []cate_name ;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.griditems) ;
		ds = (Gallery)findViewById(R.id.gvMain);
		
		List<GridInfo> dsmathang = new ArrayList<GridInfo>() ;
		cate_img= getResources().getStringArray(R.array.hinhanh) ;
		cate_name = getResources().getStringArray(R.array.tensanpham) ;
		for(int i = 0 ;i < cate_img.length ; i ++){
		try {
			dsmathang.add(new GridInfo(getBitmapFromAsset(cate_img[i]), cate_name[i])) ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		ds.setAdapter(new GridInfoAdapter(getApplicationContext(), dsmathang)) ;
		 ds.setOnItemClickListener(mItemClickListener);
	}
	
	private Bitmap getBitmapFromAsset(String strName) throws IOException
    {
        AssetManager assetManager = getAssets();

        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
	   private OnItemClickListener mItemClickListener = new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				ViewHolder holder = (ViewHolder)view.getTag();
				if(holder == null) {
					return;
				}
				String tensp = holder.tvName.getText()+"" ;
				Intent intent = new Intent() ;
				Bundle sent = new Bundle() ;
				
				sent.putString("test", tensp) ;
				intent.putExtras(sent) ;
				setResult(1,intent);
				finish();
			}
		};
	
}
