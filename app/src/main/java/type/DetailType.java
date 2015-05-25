package type;

import java.text.*;
import java.util.*;

import org.dom4j.Element;

public class DetailType extends Type {
	Date time;
	String event,reason,type;
	double value;
	
	private Element timeElement;
	private Element eventElement, reasonElement, typeElement;
	private Element valueElement;
	
	public DetailType() {
		buildElement();
		time=new Date();
		setTime(new Date());
		setEvent("");
		setReason("");
		setType("");
		setValue(0);
	}
	public DetailType(DetailType other){
		buildElement();
		time = new Date();
		setTime(other.getTime());
		setEvent(other.getEvent());
		setReason(other.getReason());
		setType(other.getType());
		setValue(other.getValue());
	}
	
	public void setTime(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		time.setTime(date.getTime());
		timeElement.setText(sdf.format(date));
	}
	public void setEvent(String event){
		this.event = event;
		eventElement.setText(event);
	}
	public void setReason(String reason){
		this.reason = reason;
		reasonElement.setText(reason);
	}
	public void setType(String type){
		this.type = type;
		typeElement.setText(type);
	}
	public void setValue(double value){
		DecimalFormat df = new DecimalFormat("0.00");
		this.value = value;
		valueElement.setText(df.format(value));
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
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		timeElement = message.element("time");
		eventElement = message.element("event");
		reasonElement = message.element("reason");
		typeElement = message.element("type");
		valueElement = message.element("value");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			time = sdf.parse(timeElement.getText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		event = eventElement.getText();
		reason = reasonElement.getText();
		type = typeElement.getText();
		value= Double.valueOf(valueElement.getText());
	}
	
	private void buildElement() {
		timeElement = root.addElement("time");
		eventElement = root.addElement("event");
		reasonElement = root.addElement("reason");
		typeElement = root.addElement("type");
		valueElement = root.addElement("value");
	}
}
