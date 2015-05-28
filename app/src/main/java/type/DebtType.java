package type;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.dom4j.Element;

public class DebtType extends IDType {
	protected int debtID;
	protected String creditor,debtType;
	protected double value;
	protected Date deadline,startingTime,lastUpdateTime;
	protected RateType rate;
	
	private Element debtIDElement;
	private Element creditorElement, debtTypeElement;
	private Element valueElement;
	private Element deadlineElement, startingTimeElement, lastUpdateElement;
	
	public DebtType(){
		createElemet();
		setID(-1);
		setCreditor("null");
		setDebtType("borrow");
		setValue(0);
		setDeadline(new Date());
		this.rate=new RateType(this);
		setStartingTime(new Date());
		setLastUpdateTime(new Date());
	}
	public DebtType(String creditor,double val){
		createElemet();
		setID(-1);
		setCreditor(creditor);
		setDebtType("borrow");
		setValue(val);
		setDeadline(new Date());
		this.rate=new RateType(this);
		setStartingTime(new Date());
		setLastUpdateTime(new Date());
	}
	public DebtType(String creditor,double val,Date deadline){
		createElemet();
		setID(-1);
		setCreditor(creditor);
		setDebtType("borrow");
		setValue(val);
		setDeadline(deadline);
		this.rate=new RateType(this);
		setStartingTime(new Date());
		setLastUpdateTime(new Date());
	}
	public DebtType(String creditor,double val,Date deadline,String type,double rate){
		createElemet();
		setID(-1);
		setCreditor(creditor);
		setDebtType("borrow");
		setValue(val);
		setDeadline(deadline);
		this.rate=new RateType(this,type, rate);
		setStartingTime(new Date());
		setLastUpdateTime(new Date());
	}
	
	public void setRate(String type,double rate){
		this.rate=new RateType(this, type, rate);
	}
	public void setDeadline(Date deadline){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.deadline.setTime(deadline.getTime());
		deadlineElement.setText(sdf.format(deadline));
	}
	public void setValue(double val){
		DecimalFormat df = new DecimalFormat("0.00");
		this.value=val;
		valueElement.setText(df.format(value));
	}
	public void setCreditor(String s){
		this.creditor=s;
		creditorElement.setText(creditor);
	}
	public void setDebtType(String type){
		this.debtType=type;
		debtTypeElement.setText(debtType);
	}
	public void setID(int id){
		this.debtID=id;
		debtIDElement.setText(debtID+"");
		setTypeId(id+"");
	}
	public void updateLastUpdateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.lastUpdateTime=new Date();
		lastUpdateElement.setText(sdf.format(lastUpdateTime));
	}
	public void setLastUpdateTime(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.lastUpdateTime.setTime(time.getTime());
		lastUpdateElement.setText(sdf.format(lastUpdateTime));
	}
	public void setStartingTime(Date val){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.startingTime.setTime(val.getTime());
		startingTimeElement.setText(sdf.format(startingTime));
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
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		debtIDElement = message.element("debt_id");
		creditorElement = message.element("creditor");
		debtTypeElement = message.element("debt_type");
		valueElement = message.element("value");
		deadlineElement = message.element("deadline");
		startingTimeElement = message.element("starting_time");
		lastUpdateElement = message.element("last_update_time");
		
		debtID = Integer.valueOf(debtIDElement.getText());
		creditor = creditorElement.getText();
		debtType = debtTypeElement.getText();
		value = Double.valueOf(valueElement.getText());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			deadline = sdf.parse(deadlineElement.getText());
			startingTime = sdf.parse(startingTimeElement.getText());
			lastUpdateTime = sdf.parse(lastUpdateElement.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		rate.loadData();
	}
	
	private void createElemet() {
		debtIDElement = root.addElement("debt_id");
		creditorElement = root.addElement("creditor");
		debtTypeElement = root.addElement("debt_type");
		valueElement = root.addElement("value");
		deadlineElement = root.addElement("deadline");
		startingTimeElement = root.addElement("starting_time");
		lastUpdateElement = root.addElement("last_update_time");
		
		deadline = new Date();
		startingTime = new Date();
		lastUpdateTime = new Date();
	}
}
