package type;

import java.util.Vector;

public class UserMessage extends Type implements TypeInterface {
	String userName, passWord;
	public UserMessage(){
		this.userName=new String();
		this.passWord=new String();
	}
	public UserMessage(String userName, String passWord){
		this.userName=new String(userName);
		this.passWord=new String(passWord);
	}
	
	public void setName(String name){
		this.userName=new String(name);
	}
	public void setPassWord(String pass){
		this.passWord=new String(pass);
	}
	public String getName(){
		return this.userName;
	}
	public String getPassWord(){
		return this.passWord;
	}
	
	@Override
	public String format() {
		String ans=new String();
		ans+="[user name]\r\n";
		ans+=this.userName+"\r\n";
		ans+="[pass word]\r\n";
		ans+=this.passWord+"\r\n";
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
		return 2+super.getTypeNumber();
	}
	@Override
	public void solveTypeMessage(Vector <String> message) {
		for (int i=0;i<message.size();i+=2){
			String title=message.get(i);
			String body=message.get(i+1);
			if(title.equals("[user name]")){
				this.userName=body;
			}else if (title.equals("[pass word]")){
				this.passWord=body;
			}
		}
		super.solveTypeMessage(message);
	}
}
