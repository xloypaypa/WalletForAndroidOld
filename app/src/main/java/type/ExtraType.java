package type;

import java.util.Vector;

public class ExtraType implements TypeInterface {
	String title,message;
	public ExtraType(){
		this.title=new String();
		this.message=new String();
	}
	public ExtraType(String title, String message){
		this.title=new String(title);
		this.message=new String(message);
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public void setMessage(String message){
		this.message=message;
	}
	public String getTitle(){
		return new String(this.title);
	}
	public String getMessage(){
		return new String(this.message);
	}

	@Override
	public String format() {
		String ans=new String();
		ans+="[extra title]\r\n";
		ans+=this.title+"\r\n";
		ans+="[extra message]\r\n";
		ans+=this.message+"\r\n";
		return ans;
	}

	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="extra type\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}

	@Override
	public int getTypeNumber() {
		return 2;
	}

	@Override
	public void solveTypeMessage(Vector<String> message) {
		for (int i=0;i<message.size();i+=2){
			if (message.get(i).equals("[extra title]")){
				this.title=message.get(i+1);
			}else if (message.get(i).equals("extra message")){
				this.message=message.get(i+1);
			}
		}
	}
	@Override
	public String getAllMessage() {
		return this.format();
	}
	
}
