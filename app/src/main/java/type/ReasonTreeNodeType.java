package type;

import java.text.DecimalFormat;
import java.util.Vector;

import org.dom4j.Element;

public class ReasonTreeNodeType extends ReasonType {
	int fatherPos;
	Vector <Integer> kidPos;
	
	String fatherName;
	int rank;
	double dayMin,dayMax;
	
	private Element fatherElement, rankElement, minElement, maxElement;
	
	public ReasonTreeNodeType(){
		buildElement();
		setFatherName("root");
		setRank(0);
		setMin(0); setMax(0);
		this.fatherPos=-1;
		this.kidPos=new Vector<Integer>();
	}
	
	public void setFatherPos(int pos){
		this.fatherPos=pos;
	}
	public int getFatherPos(){
		return this.fatherPos;
	}
	public void addKid(int kid){
		this.kidPos.addElement(kid);
	}
	public void clearKid(int kid){
		this.kidPos.removeAllElements();
	}
	public Vector <Integer> getKid(){
		return new Vector <Integer>(this.kidPos);
	}
	public void clearKid(){
		this.kidPos.removeAllElements();
	}
	
	public void setFatherName(String name){
		this.fatherName=new String(name);
		fatherElement.setText(name);
	}
	public void setRank(int rank){
		this.rank=rank;
		rankElement.setText(rank+"");
	}
	public void setMin(double value){
		DecimalFormat df = new DecimalFormat("0.00");
		this.dayMin=value;
		minElement.setText(df.format(value));
	}
	public void setMax(double value){
		DecimalFormat df = new DecimalFormat("0.00");
		this.dayMax=value;
		maxElement.setText(df.format(value));
	}
	public String getFather(){
		return new String(this.fatherName);
	}
	public int getRank(){
		return this.rank;
	}
	public double getMin(){
		return this.dayMin;
	}
	public double getMax(){
		return this.dayMax;
	}
	
	@Override
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		fatherElement = root.element("father");
		rankElement = root.element("rank");
		minElement = root.element("min");
		maxElement = root.element("max");
		
		fatherName = fatherElement.getText();
		rank = Integer.valueOf(rankElement.getText());
		dayMin = Double.valueOf(minElement.getText());
		dayMax = Double.valueOf(maxElement.getText());
	}
	
	private void buildElement() {
		fatherElement = root.addElement("father");
		rankElement = root.addElement("rank");
		minElement = root.addElement("min");
		maxElement = root.addElement("max");
	}
}
