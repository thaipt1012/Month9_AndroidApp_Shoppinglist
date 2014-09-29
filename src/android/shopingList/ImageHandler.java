package android.shopingList;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageHandler extends Handler 
{

   private String url;

   private ImageView imageView;

   public ImageHandler(String url, ImageView imageView) {
      this.url=url;
      this.imageView = imageView;
   }

   @Override
   public void handleMessage(Message msg) {
      String forURLS = (String) imageView.getTag();
      if (!forURLS.equals(this.url)) {
         return;
      }
      Bitmap image = msg.getData().getParcelable("image");
      imageView.setImageBitmap(image);
   }
}