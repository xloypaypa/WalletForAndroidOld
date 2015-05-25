package type;

import java.text.DecimalFormat;
import java.util.*;

import org.dom4j.Element;

public class RateType {
	
	protected DebtType debtCallBack;
	
	protected String type;
	protected double rate;
	private Element typeElement, rateElement;
	
	public RateType(DebtType debtType){
		this.debtCallBack=debtType;
		buildElemet();
		setType("null");
		setRate(0);
	}
	public RateType(DebtType debtType,double rate){
		this.debtCallBack=debtType;
		buildElemet();
		setType("null");
		setRate(rate);
	}
	public RateType(DebtType debtType,String type,double rate){
		this.debtCallBack=debtType;
		buildElemet();
		setType(type);
		setRate(rate);
	}
	
	public void setType(String type){
		this.type=type;
		typeElement.setText(type);
	}
	public void setRate(double rate){
		DecimalFormat df = new DecimalFormat("0.00");
		this.rate=rate;
		rateElement.setText(df.format(rate));
		
	}
	public String getType(){
		if (type==null) return "null";
		return new String(type);
	}
	public double getRate(){
		return rate;
	}
	
	public void loadData() {
		type = typeElement.getText();
		rate = Double.valueOf(rateElement.getText());
	}
	
	public double finalRate(Date st,Date tt){
		if (type==null||type.equals("null")){
			double ans=1+rate;
			rate=0;
			return ans;
		}
		int sy,ey,sm,em;
		int dy,dm;
		Calendar startingTime,terminalTime;
		startingTime=Calendar.getInstance(); startingTime.setTime(st);
		terminalTime=Calendar.getInstance(); terminalTime.setTime(tt);
		sy=startingTime.get(Calendar.YEAR);
		ey=terminalTime.get(Calendar.YEAR);
		sm=startingTime.get(Calendar.MONTH);
		em=terminalTime.get(Calendar.MONTH);
		dy=ey-sy;
		dm=em-sm;
		
		if (type.equals("year")){
			return Math.pow(1+rate, dy);
		}else if (type.equals("month")){
			if (dy>0) dm+=12*dy;
			return Math.pow(1+rate, dm);
		}
		return 1;
	}
	
	private void buildElemet() {
		typeElement = debtCallBack.getRoot().addElement("rate_type");
		rateElement = debtCallBack.getRoot().addElement("rate");
	}
}
