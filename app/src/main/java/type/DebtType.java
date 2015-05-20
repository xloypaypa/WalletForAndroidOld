package type;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DebtType extends Type implements TypeInterface {
	protected int debtID;
	protected String creditor,debtType;
	protected double value;
	protected Date deadline,startingTime,lastUpdateTime;
	protected RateType rate;
	
	public DebtType(){
		this.debtID=-1;
		this.id="-1";
		this.creditor=new String("null");
		this.debtType="borrow";
		this.value=0;
		this.deadline=new Date();
		this.rate=new RateType();
		this.startingTime=new Date();
		this.lastUpdateTime=new Date();
	}
	public DebtType(String debtee,double val){
		this.debtID=-1;
		this.id="-1";
		this.creditor=new String(debtee);
		this.debtType="borrow";
		this.value=val; this.rate=new RateType();
		this.deadline=new Date();
		this.startingTime=new Date();
		this.lastUpdateTime=new Date();
	}
	public DebtType(String debtee,double val,Date deadline){
		this.debtID=-1;
		this.id="-1";
		this.creditor=new String(debtee);
		this.debtType="borrow";
		this.value=val; this.rate=new RateType();
		this.deadline=new Date();
		this.deadline.setTime(deadline.getTime());
		this.startingTime=new Date();
		this.lastUpdateTime=new Date();
	}
	public DebtType(String debtee,double val,Date deadline,String type,double rate){
		this.debtID=-1;
		this.id="-1";
		this.creditor=new String(debtee);
		this.debtType="borrow";
		this.value=val; this.rate=new RateType(type, rate);
		this.deadline=new Date();
		this.deadline.setTime(deadline.getTime());
		this.startingTime=new Date();
		this.lastUpdateTime=new Date();
	}
	
	public void setRate(String type,double rate){
		this.rate=new RateType(type, rate);
	}
	public void setDeadline(Date deadline){
		this.deadline.setTime(deadline.getTime());
	}
	public void setValue(double val){
		this.value=val;
	}
	public void setCreditor(String s){
		this.creditor=s;
	}
	public void setDebtType(String type){
		this.debtType=type;
	}
	public void setID(int id){
		this.debtID=id;
		this.id=id+"";
	}
	public void updateLastUpdateTime(){
		this.lastUpdateTime=new Date();
	}
	public void setLastUpdateTime(Date time){
		this.lastUpdateTime.setTime(time.getTime());
	}
	public void setStartingTime(Date val){
		this.startingTime.setTime(val.getTime());
	}
	public String getCreditor(){
		return this.creditor;
	}
	public String getDebtType(){
		return this.debtType;
	}
	public RateType getRate(){
		return this.rate;
	}
	public double getValue(){
		return this.value;
	}
	public Date getDeadline(){
		Date ans=new Date();
		ans.setTime(this.deadline.getTime());
		return ans;
	}
	public Date getStartingTime(){
		Date ans=new Date();
		ans.setTime(this.startingTime.getTime());
		return ans;
	}
	public Date getLastUpdateTime(){
		Date ans=new Date();
		ans.setTime(this.lastUpdateTime.getTime());
		return ans;
	}
	public int getID(){
		return this.debtID;
	}
	
	public boolean beExceed(){ 
		Date now=new Date();
		
		if (now.after(deadline)) return true;
		else return false;
	}
	public void repayment(double val){
		this.value=this.value*rate.finalRate(this.lastUpdateTime, new Date())-val;
		this.updateLastUpdateTime();
	}
	public void repayment(double val,Date time){
		this.value=this.value*rate.finalRate(this.lastUpdateTime, time)-val;
		this.setLastUpdateTime(time);
	}
	public double getMaxRepay(){
		this.updateValue();
		return this.value;
	}
	public void updateValue(){
		this.value=this.value*rate.finalRate(this.lastUpdateTime, new Date());
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
		ans+="[debt type]\r\n"+this.debtType+"\r\n";
		ans+="[debt starting time]\r\n"+df.format(this.startingTime.getTime())+"\r\n";
		ans+="[debt last update time]\r\n"+df.format(this.lastUpdateTime.getTime())+"\r\n";
		ans+="[debt deadline]\r\n"+df.format(this.deadline.getTime())+"\r\n";
		ans+=this.rate.format();
		ans+=super.format();
		return ans;
	}
	@Override
	public String getTypeMessage() {
		return super.getTypeMessage("debt type");
	}
	@Override
	public int getTypeNumber() {
		return 7+this.rate.getTypeNumber()+super.getTypeNumber();
	}
	@Override
	public void solveTypeMessage(Vector <String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if (title.equals("[debt id]")){
				this.debtID=Integer.valueOf(body);
				this.id=""+this.debtID;
			}else if (title.equals("[debt creditor]")){
				this.creditor=body;
			}else if (title.equals("[debt value]")){
				this.value=Double.valueOf(body);
			}else if (title.equals("[debt starting time]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date();
					date=sdf.parse(body);
					this.startingTime.setTime(date.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[debt last update time]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date();
					date=sdf.parse(body);
					this.lastUpdateTime.setTime(date.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[debt deadline]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date=new Date();
					date=sdf.parse(body);
					this.deadline.setTime(date.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[debt type]")){
				this.debtType=body;
			}
		}
		this.rate.solveTypeMessage(message);
		super.solveTypeMessage(message);
	}
}
