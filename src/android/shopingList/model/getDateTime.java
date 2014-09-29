package android.shopingList.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class getDateTime 
{
	public static String getTime(String formatString)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sim =new SimpleDateFormat(formatString);
		return sim.format(cal.getTime());
	}
	 /*public static void  main(String arg[]) {
	     System.out.println(DateUtils.now("dd MMMMM yyyy"));
	     System.out.println(DateUtils.now("yyyyMMdd"));
	     System.out.println(DateUtils.now("dd.MM.yy"));
	     System.out.println(DateUtils.now("MM/dd/yy"));
	     System.out.println(DateUtils.now("yyyy.MM.dd G 'at' hh:mm:ss z"));
	     System.out.println(DateUtils.now("EEE, MMM d, ''yy"));
	     System.out.println(DateUtils.now("h:mm a"));
	     System.out.println(DateUtils.now("H:mm:ss:SSS"));
	     System.out.println(DateUtils.now("K:mm a,z"));
	     System.out.println(DateUtils.now("yyyy.MMMMM.dd GGG hh:mm aaa"));
	  }*/
}
