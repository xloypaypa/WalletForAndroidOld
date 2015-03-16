package type;

import java.text.*;
import java.util.*;

public class DetailType extends Type implements TypeInterface {
	Date time;
	String event,reason,type;
	double value;
	
	public DetailType() {
		time = new Date();
		event = new String();
		reason = new String();
		type = new String();
		value = 0;
	}
	public DetailType(DetailType other){
		time = new Date();
		time.setTime(other.getTime().getTime());
		event = new String(other.getEvent());
		reason = new String(other.getReason());
		type = new String(other.getType());
		value = other.getValue();
	}
	
	public void setTime(Date date){
		time.setTime(date.getTime());
	}
	public void setEvent(String event){
		this.event = event;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public void setType(String type){
		this.type = type;
	}
	public void setValue(double value){
		this.value = value;
	}
	public Date getTime(){
		Date ans=new Date();
		ans.setTime(this.time.getTime());
		return ans;
	}
	public String getEvent(){
		return new String(this.event);
	}
	public String getReason(){
		return new String(this.reason);
	}
	public String getType(){
		return new String(this.type);
	}
	public double getValue(){
		return this.value;
	}

	@Override
	public String format() {
		String ans=new String();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat df = new DecimalFormat("0.00");
		
		ans+="[detail time]\r\n";
		ans+=sdf.format(time.getTime())+"\r\n";
		ans+="[detail event]\r\n";
		ans+=event+"\r\n";
		ans+="[detail value]\r\n";
		ans+=df.format(value)+"\r\n";
		ans+="[detail type]\r\n";
		ans+=type+"\r\n";
		ans+="[detail reason]\r\n";
		ans+=reason+"\r\n";
		ans+=super.format();
		return ans;
	}

	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="detail type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}

	@Override
	public int getTypeNumber() {
		return 5+super.getTypeNumber();
	}

	@Override
	public void solveTypeMessage(Vector<String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if (title.equals("[detail time]")){
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					time.setTime(sdf.parse(body).getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if (title.equals("[detail event]")){
				event=body;
			}else if (title.equals("[detail value]")){
				value = Double.valueOf(body);
			}else if (title.equals("[detail type]")){
				type = body;
			}else if (title.equals("[detail reason]")){
				reason = body;
			}
		}
		super.solveTypeMessage(message);
	}
}
