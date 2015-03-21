package type;

import java.text.DecimalFormat;
import java.util.*;

public class RateType implements TypeInterface {
	protected String type;
	protected double rate;
	public RateType(){
		type=null;
		rate=0;
	}
	public RateType(double rate){
		type=null;
		this.rate=rate;
	}
	public RateType(String type,double rate){
		this.type=new String(type);
		this.rate=rate;
	}
	
	public void setType(String type){
		this.type=type;
	}
	public void setRate(double rate){
		this.rate=rate;
	}
	public String getType(){
		if (type==null) return "null";
		return new String(type);
	}
	public double getRate(){
		return rate;
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
	
	@Override
	public String format() {
		String ans=new String();
		DecimalFormat df = new DecimalFormat("0.00");
		ans+="[rate type]\r\n";
		ans+=this.type+"\r\n";
		ans+="[rate value]\r\n";
		ans+=df.format(this.rate)+"\r\n";
		return ans;
	}
	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="rate type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}
	@Override
	public int getTypeNumber() {
		return 2;
	}
	@Override
	public void solveTypeMessage(Vector <String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if (title.equals("[rate type]")){
				this.type=body;
			}else if (title.equals("[rate value]")){
				this.rate=Double.valueOf(body);
			}
		}
	}
	@Override
	public String getAllMessage() {
		return this.getTypeMessage()+this.format()+"[end]\r\n";
	}
}
