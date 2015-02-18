package type;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DebtType extends Type implements TypeInterface {
	protected int debtID;
	protected String creditor;
	protected double value;
	protected Calendar deadline,startingTime,lastUpdateTime;
	protected RateType rate;
	public DebtType(){
		this.debtID=-1;
		this.creditor=new String("null");
		this.value=0;
		this.deadline=null;
		this.rate=new RateType();
		this.startingTime=Calendar.getInstance();
		this.lastUpdateTime=Calendar.getInstance();
	}
	public DebtType(String debtee,double val){
		this.debtID=-1;
		this.creditor=new String(debtee);
		this.value=val; this.rate=new RateType();
		this.deadline=null;
		this.startingTime=Calendar.getInstance();
		this.lastUpdateTime=Calendar.getInstance();
	}
	public DebtType(String debtee,double val,Calendar deadline){
		this.debtID=-1;
		this.creditor=new String(debtee);
		this.value=val; this.rate=new RateType();
		this.deadline=deadline;
		this.startingTime=Calendar.getInstance();
		this.lastUpdateTime=Calendar.getInstance();
	}
	public DebtType(String debtee,double val,Calendar deadline,String type,double rate){
		this.debtID=-1;
		this.creditor=new String(debtee);
		this.value=val; this.rate=new RateType(type, rate);
		this.deadline=deadline;
		this.startingTime=Calendar.getInstance();
		this.lastUpdateTime=Calendar.getInstance();
	}
	
	public void setRate(String type,double rate){
		this.rate=new RateType(type, rate);
	}
	public void setDeadline(Calendar deadline){
		this.deadline=deadline;
	}
	public void setValue(double val){
		this.value=val;
	}
	public void setCreditor(String s){
		this.creditor=s;
	}
	public void setID(int id){
		this.debtID=id;
	}
	public void updateLastUpdateTime(){
		this.lastUpdateTime=Calendar.getInstance();;
	}
	public void setLastUpdateTime(Calendar time){
		this.lastUpdateTime=time;
	}
	public void setStartingTime(Calendar val){
		this.startingTime=val;
	}
	public String getCreditor(){
		return this.creditor;
	}
	public RateType getRate(){
		return this.rate;
	}
	public double getValue(){
		return this.value;
	}
	public Calendar getDeadline(){
		return this.deadline;
	}
	public Calendar getStartingTime(){
		return this.startingTime;
	}
	public Calendar getLastUpdateTime(){
		return this.lastUpdateTime;
	}
	public int getID(){
		return this.debtID;
	}
	
	public boolean beExceed(){ 
		Calendar now=Calendar.getInstance();
		
		if (now.after(deadline)) return true;
		else return false;
	}
	public void repayment(double val){
		Calendar now=Calendar.getInstance();
		this.value=this.value*rate.finalRate(this.lastUpdateTime, now)-val;
		this.updateLastUpdateTime();
	}
	public void repayment(double val,Calendar time){
		this.value=this.value*rate.finalRate(this.lastUpdateTime, time)-val;
		this.setLastUpdateTime(time);
	}
	public double getMaxRepay(){
		this.updateValue();
		return this.value;
	}
	public void updateValue(){
		Calendar now=Calendar.getInstance();
		this.value=this.value*rate.finalRate(this.lastUpdateTime, now);
		this.updateLastUpdateTime();
	}
	
	@Override
	public String format(){
		String ans=new String();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df2 = new DecimalFormat("0.00");
		ans="[debt id]\r\n"+this.debtID+"\r\n";
		ans+="[debt creditor]\r\n"+this.creditor+"\r\n";
		ans+="[debt value]\r\n"+df2.format(this.value)+"\r\n";
		ans+="[debt starting time]\r\n"+df.format(this.startingTime.getTime())+"\r\n";
		ans+="[debt last update time]\r\n"+df.format(this.lastUpdateTime.getTime())+"\r\n";
		ans+="[debt deadline]\r\n"+df.format(this.deadline.getTime())+"\r\n";
		ans+=this.rate.format();
		ans+=super.format();
		return ans;
	}
	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="debt type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}
	@Override
	public int getTypeNumber() {
		return 6+this.rate.getTypeNumber()+super.getTypeNumber();
	}
	@Override
	public void solveTypeMessage(Vector <String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if (title.equals("[debt id]")){
				this.debtID=Integer.valueOf(body);
			}else if (title.equals("[debt creditor]")){
				this.creditor=body;
			}else if (title.equals("[debt value]")){
				this.value=Double.valueOf(body);
			}else if (title.equals("[debt starting time]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date();
					this.startingTime=Calendar.getInstance();
					date=sdf.parse(body);
					this.startingTime.setTime(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[debt last update time]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date();
					this.lastUpdateTime=Calendar.getInstance();
					date=sdf.parse(body);
					this.lastUpdateTime.setTime(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[debt deadline]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date();
					this.deadline=Calendar.getInstance();
					date=sdf.parse(body);
					this.deadline.setTime(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		this.rate.solveTypeMessage(message);
		super.solveTypeMessage(message);
	}
}
