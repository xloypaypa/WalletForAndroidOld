package type;

import java.util.Vector;

public class UserMessage extends Type implements TypeInterface {
	String userName, passWord, userReason;
	public UserMessage(){
		this.userName=new String();
		this.id=userName;
		this.passWord=new String();
		this.userReason=new String("normal");
	}
	public UserMessage(String userName, String passWord, String userReason){
		this.userName=new String(userName);
		this.id=userName;
		this.passWord=new String(passWord);
		this.userReason=new String(userReason);
	}
	
	public void setName(String name){
		this.userName=new String(name);
		this.id=userName;
	}
	public void setPassWord(String pass){
		this.passWord=new String(pass);
	}
	public void setReason(String reason){
		this.userReason=new String(reason);
	}
	public String getName(){
		return new String(this.userName);
	}
	public String getPassWord(){
		return new String(this.passWord);
	}
	public String getReason(){
		return new String(this.userReason);
	}
	
	@Override
	public String format() {
		String ans=new String();
		ans+="[user name]\r\n";
		ans+=this.userName+"\r\n";
		ans+="[pass word]\r\n";
		ans+=this.passWord+"\r\n";
		ans+="[user reason type]\r\n";
		ans+=this.userReason+"\r\n";
		ans+=super.format();
		return ans;
	}
	@Override
	public String getTypeMessage() {
		String ans=new String();
		ans+="[begin]\r\n";
		ans+="[type name]\r\n";
		ans+="user message\r\n";
		ans+="[type item]\r\n";
		ans+=this.getTypeNumber()+"\r\n";
		return ans;
	}
	@Override
	public int getTypeNumber() {
		return 3+super.getTypeNumber();
	}
	@Override
	public void solveTypeMessage(Vector <String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if(title.equals("[user name]")){
				this.userName=body;
				this.id=userName;
			}else if (title.equals("[pass word]")){
				this.passWord=body;
			}else if (title.equals("[user reason type]")){
				this.userReason=body;
			}
		}
		super.solveTypeMessage(message);
	}
}
