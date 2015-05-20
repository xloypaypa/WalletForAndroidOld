package type;

import java.text.DecimalFormat;
import java.util.Vector;

public class MoneyType extends Type implements TypeInterface {
	String type;
	double value;
	public MoneyType(){
		type=new String();
		this.id=type;
		value=0;
	}
	public MoneyType(String type,double value){
		this.type=new String(type);
		this.id=type;
		this.value=value;
	}
	
	public void setType(String type){
		this.type=type;
		this.id=type;
	}
	public void setValue(double value){
		this.value=value;
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
		DecimalFormat df = new DecimalFormat("0.00");
		ans+="[money type]\r\n";
		ans+=this.type+"\r\n";
		ans+="[money value]\r\n";
		ans+=df.format(this.value)+"\r\n";
		ans+=super.format();
		return ans;
	}
	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="money type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}
	@Override
	public int getTypeNumber() {
		return 2+super.getTypeNumber();
	}
	@Override
	public void solveTypeMessage(Vector <String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if(title.equals("[money type]")){
				this.type=body;
				this.id=type;
			}else if (title.equals("[money value]")){
				this.value=Double.valueOf(body);
			}
		}
		super.solveTypeMessage(message);
	}
}
