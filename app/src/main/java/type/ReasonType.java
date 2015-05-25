package type;

import java.text.DecimalFormat;

import org.dom4j.Element;

public class ReasonType extends Type {
	
	String name;
	double income,expenditure;
	
	private Element nameElement, incomeElement, expenditureElement;
	
	public ReasonType(){
		buildElement();
		setName("");
		setIncome(0);
		setExpenditure(0);
	}
	
	public ReasonType(String name){
		buildElement();
		setName(name);
		setIncome(0);
		setExpenditure(0);
	}
	
	public ReasonType(String name, double income, double expenditure){
		buildElement();
		setName(name);
		setIncome(income);
		setExpenditure(expenditure);
	}
	
	public void setName(String name){
		this.name=new String(name);
		nameElement.setText(name);
		setTypeId(name);
	}
	
	public void setIncome(double income){
		DecimalFormat df = new DecimalFormat("0.00");
		this.income=income;
		incomeElement.setText(df.format(income));
	}
	
	public void setExpenditure(double expenditure){
		DecimalFormat df = new DecimalFormat("0.00");
		this.expenditure=expenditure;
		expenditureElement.setText(df.format(expenditure));
	}
	
	public String getName(){
		return new String(this.name);
	}
	
	public double getIncome(){
		return this.income;
	}
	
	public double getExpenditure(){
		return this.expenditure;
	}
	
	public void update() {
		setExpenditure(0);
		setIncome(0);
	}

	@Override
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		nameElement = root.element("name");
		incomeElement = root.element("income");
		expenditureElement = root.element("expenditure");
		
		name = nameElement.getText();
		income = Double.valueOf(incomeElement.getText());
		expenditure = Double.valueOf(expenditureElement.getText());
	}
	
	private void buildElement() {
		nameElement = root.addElement("name");
		incomeElement = root.addElement("income");
		expenditureElement = root.addElement("expenditure");
	}
	
}
