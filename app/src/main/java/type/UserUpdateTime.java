package type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.dom4j.Element;

public class UserUpdateTime extends Type {
	
	String type;
	Date nextTime;
	
	private Element typeElement, nextTimeElement;
	
	public UserUpdateTime(){
		buildElemet();
		setType("month");
		Calendar c=Calendar.getInstance();
		c.set(1917, 11, 27);
		setNextTime(c.getTime());
		this.setMonth(1);
	}
	
	public void setMonth(int day){
		this.removeExtra("month");
		this.removeExtra("day");
		setType("month");
		this.addExtra("day", day+"");
	}
	
	public void setYear(int month,int day){
		this.removeExtra("month");
		this.removeExtra("day");
		setType("year");
		this.addExtra("month", month+"");
		this.addExtra("day", day+"");
		
	}
	
	public boolean needUpdate(Date last){
		if (last.after(nextTime)) return true;
		return false;
	}
	
	public Date getNextTime(Date last){
		if (type=="month"){
			Calendar ans=Calendar.getInstance();
			int nextDay=Integer.valueOf(this.getExtraMessage("day"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				ans.setTime(sdf.parse(sdf.format(last)));
				ans.set(Calendar.DAY_OF_MONTH, nextDay);
				if (ans.getTime().after(last)){
					nextTime=ans.getTime();
					return this.nextTime;
				}
				
				int month=ans.get(Calendar.MONTH)+1;
				if (month==12){
					ans.set(Calendar.YEAR, ans.get(Calendar.YEAR)+1);
					ans.set(Calendar.MONTH, 0);
				}else{
					ans.set(Calendar.MONTH, month);
				}
				nextTime=ans.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else if (type=="year"){
			Calendar ans=Calendar.getInstance();
			int nextDay=Integer.valueOf(this.getExtraMessage("day"));
			int nextMonth=Integer.valueOf(this.getExtraMessage("month"));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				ans.setTime(sdf.parse(sdf.format(last)));
				ans.set(Calendar.DAY_OF_MONTH, nextDay);
				ans.set(Calendar.MONTH, nextMonth-1);
				
				if (ans.getTime().after(last)){
					setNextTime(ans.getTime());
					return this.nextTime;
				}
				
				ans.set(Calendar.YEAR, ans.get(Calendar.YEAR)+1);
				setNextTime(ans.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return this.nextTime;
	}
	
	public Date getNextTime(){
		return this.nextTime;
	}
	
	@Override
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		typeElement = root.element("type");
		nextTimeElement = root.element("next_time");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		type = typeElement.getText();
		try {
			nextTime = sdf.parse(nextTimeElement.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	protected void setType(String type) {
		this.type = type;
		typeElement.setText(type);
	}
	
	protected void setNextTime(Date nextTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.nextTime = nextTime;
		nextTimeElement.setText(sdf.format(nextTime));
	}
	
	private void buildElemet() {
		typeElement = root.addElement("type");
		nextTimeElement = root.addElement("next_time");
	}
}
