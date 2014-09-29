package android.shopingList.model;

public class ShopItem 
{
	long id;
	String name;
	String des;
	String quan;
	String price;
	String rate;
	String url;
	long cate;
	int isCheck;
	String timeCheck;
	
	public ShopItem(String name,String des,String price,String quan,String rate,String url
			,long cate,int check,String thedate)
	{
		this.name=name;
		this.des=des;
		this.price=price;
		this.quan=quan;
		this.rate=rate;
		this.url=url;
		this.cate=cate;
		this.isCheck=check;
		this.timeCheck=thedate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getQuan() {
		return quan;
	}
	public void setQuan(String quan) {
		this.quan = quan;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getCate() {
		return cate;
	}
	public void setCate(long cate) {
		this.cate = cate;
	}
	public int getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(int isCheck) {
		this.isCheck = isCheck;
	}
	public String getTimeCheck() {
		return timeCheck;
	}
	public void setTimeCheck(String timeCheck) {
		this.timeCheck = timeCheck;
	}
	
	
	
	
	

}
