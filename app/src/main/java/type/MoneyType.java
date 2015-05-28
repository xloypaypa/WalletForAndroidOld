package type;

import java.text.DecimalFormat;

import org.dom4j.Element;

public class MoneyType extends IDType {
	
	String type;
	double value;
	
	private Element typeElement, valueElement;
	
	public MoneyType(){
		buildElement();
		setType("");
		setValue(0);
	}
	public MoneyType(String type,double value){
		buildElement();
		setType(type);
		setValue(value);
	}
	
	public void setType(String type){
		this.type=type;
		typeElement.setText(type);
		setTypeId(type);
	}
	public void setValue(double value){
		DecimalFormat df = new DecimalFormat("0.00");
		this.value=value;
		valueElement.setText(df.format(value));
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
		typeElement = message.element("type");
		valueElement = message.element("value");
		
		type = typeElement.getText();
		value = Double.valueOf(valueElement.getText());
	}
	
	private void buildElement() {
		typeElement = root.addElement("type");
		valueElement = root.addElement("value");
	}
}
