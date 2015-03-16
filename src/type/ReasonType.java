package type;

import java.text.DecimalFormat;
import java.util.Vector;

public class ReasonType extends Type implements TypeInterface {
	String name;
	double income,expenditure;
	
	public ReasonType(){
		this.name=new String();
		this.income=this.expenditure=0;
	}
	
	public ReasonType(String name){
		this.name=new String(name);
		this.income=this.expenditure=0;
	}
	
	public ReasonType(String name, double income, double expenditure){
		this.name=new String(name);
		this.income=income;
		this.expenditure=expenditure;
	}
	
	public void setName(String name){
		this.name=new String(name);
	}
	
	public void setIncome(double income){
		this.income=income;
	}
	
	public void setExpenditure(double expenditure){
		this.expenditure=expenditure;
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

	@Override
	public String format() {
		String ans=new String();
		DecimalFormat df = new DecimalFormat("0.00");
		ans+="[reason name]\r\n";
		ans+=this.name+"\r\n";
		ans+="[reason income]\r\n";
		ans+=df.format(this.income)+"\r\n";
		ans+="[reason expenditure]\r\n";
		ans+=df.format(this.expenditure)+"\r\n";
		return ans;
	}

	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="reason type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}

	@Override
	public int getTypeNumber() {
		return 3+super.getTypeNumber();
	}

	@Override
	public void solveTypeMessage(Vector<String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if(title.equals("[reason name]")){
				this.name=body;
			}else if (title.equals("[reason income]")){
				this.income=Double.valueOf(body);
			}else if (title.equals("[reason expenditure]")){
				this.expenditure=Double.valueOf(body);
			}
		}
		super.solveTypeMessage(message);
	}
	
}
