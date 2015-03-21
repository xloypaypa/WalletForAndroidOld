package type;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class ReasonTreeNodeType extends ReasonType {
	int fatherPos;
	Vector <Integer> kidPos;
	
	String fatherName;
	int rank;
	double dayMin,dayMax;
	Date lastUpdateTime;
	
	public ReasonTreeNodeType(){
		super();
		this.fatherName=new String("root");
		this.rank=0;
		this.dayMin=0; this.dayMax=0;
		this.lastUpdateTime=new Date();
		this.fatherPos=-1;
		this.kidPos=new Vector<Integer>();
	}
	
	public void setFatherPos(int pos){
		this.fatherPos=pos;
	}
	public int getFatherPos(){
		return this.fatherPos;
	}
	public void addKid(int kid){
		this.kidPos.addElement(kid);
	}
	public void clearKid(int kid){
		this.kidPos.removeAllElements();
	}
	public Vector <Integer> getKid(){
		return new Vector <Integer>(this.kidPos);
	}
	
	public void setFatherName(String name){
		this.fatherName=new String(name);
	}
	public void setRank(int rank){
		this.rank=rank;
	}
	public void setMin(double value){
		this.dayMin=value;
	}
	public void setMax(double value){
		this.dayMax=value;
	}
	public void setUpdateTime(Date time){
		this.lastUpdateTime.setTime(time.getTime());
	}
	public String getFather(){
		return new String(this.fatherName);
	}
	public int getRank(){
		return this.rank;
	}
	public double getMin(){
		return this.dayMin;
	}
	public double getMax(){
		return this.dayMax;
	}
	public Date getUpdateTime(){
		Date ans=new Date();
		ans.setTime(this.lastUpdateTime.getTime());
		return ans;
	}
	
	public void update(){
		Calendar now=Calendar.getInstance();
		Calendar last=Calendar.getInstance();
		now.setTime(new Date()); last.setTime(lastUpdateTime);
		if(now.get(Calendar.YEAR)!=last.get(Calendar.YEAR)){
			this.expenditure=0;
			this.lastUpdateTime.setTime(now.getTime().getTime());
		}else if(now.get(Calendar.MONTH)!=last.get(Calendar.MONTH)){
			this.expenditure=0;
			this.lastUpdateTime.setTime(now.getTime().getTime());
		}
	}
	
	@Override
	public String format() {
		String ans=new String();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df2 = new DecimalFormat("0.00");
		ans+="[node father name]\r\n"+this.fatherName+"\r\n";
		ans+="[node last update time]\r\n"+df.format(this.lastUpdateTime.getTime())+"\r\n";
		ans+="[node rank]\r\n"+this.rank+"\r\n";
		ans+="[node min]\r\n"+df2.format(this.dayMin)+"\r\n";
		ans+="[node max]\r\n"+df2.format(this.dayMax)+"\r\n";
		ans+=super.format();
		return ans;
	}
	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="reason tree node type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}
	@Override
	public int getTypeNumber() {
		return 8+super.getTypeNumber();
	}
	@Override
	public void solveTypeMessage(Vector<String> message) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if (title.equals("[node father name]")){
				this.fatherName=body;
			}else if (title.equals("[node last update time]")){
				try {
					this.lastUpdateTime.setTime(sdf.parse(body).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[node rank]")){
				this.rank=Integer.valueOf(body);
			}else if (title.equals("[node min]")){
				this.dayMin=Double.valueOf(body);
			}else if (title.equals("[node max]")){
				this.dayMax=Double.valueOf(body);
			}
		}
		super.solveTypeMessage(message);
	}
}
