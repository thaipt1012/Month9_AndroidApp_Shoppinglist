package android.shopingList; 

public class Task {
	String id;
	String TenCV;
	String Gio;
	String Ngay;
	String DiaDiem;
	String GhiChu;
	
	//------------------------------
    public Task(String id,String TenCV){ 
        this.id=id; 
        this.TenCV=TenCV; 
    } 
     
    public void setId(String id){ 
        this.id=id; 
    } 
    public String getId(){ 
        return(id); 
    } 
    
	public String getTenCV(){
		return this.TenCV;
	}
	public void setTen(String Ten){
		this.TenCV = Ten;
	}
	
	//-----------------------------
	public String getGio(){
		return this.Gio;
	}
	public void setGio(String Gio){
		this.Gio = Gio;
	}
	
	//--------------------------------
	public String getNgay(){
		return this.Ngay;
	}
	public void setNgay(String Ngay){
		this.Ngay= Ngay;
	}
	
	//------------------------------
	public String getDiaDiem(){
		return this.DiaDiem;
	}
	public void setDiaDiem(String DiaDiem){
		this.DiaDiem = DiaDiem;
	}
	
	//-----------------------------
	public String getGhiChu(){
		return this.GhiChu;
	}
	public void setGhiChu(String GhiChu){
		this.GhiChu = GhiChu;
	}
	
	//--------------------------------
	public String toString(){
		return this.getTenCV();
	}
}