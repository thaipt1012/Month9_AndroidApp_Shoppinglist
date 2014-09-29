package android.shopingList.model;

public class Work {
    private String workContent;
    private String timeContent;
    private int isChecked;
    
    public Work(String workContent, String timeContent,int ischeck) {
    	
        this.workContent = workContent;
        this.timeContent = timeContent;
        isChecked = ischeck;
    }
    
    public String getContent() {
        return workContent;
    }
    
    public String getTime() {
        return timeContent;
    }
    public void setContent(String name){
    	workContent = name ;
    }
    public void setTime(String time){
    	this.timeContent = time ;
    }
    public void setChecked(int isChecked) {
        this.isChecked = isChecked;
    }
    public int getIsChecked() {
        return isChecked;
    }
}