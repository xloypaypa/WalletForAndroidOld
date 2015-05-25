package type;

import org.dom4j.Element;

public class UserMessage extends Type {
	
	String userName, passWord, userReason;
	
	private Element usernameElement, passwordElement, reasonElement;
	
	public UserMessage(){
		buildElement();
		setName("");
		setPassWord("");
		setReason("normal");
	}
	public UserMessage(String userName, String passWord, String userReason){
		buildElement();
		setName(userName);
		setPassWord(passWord);
		setReason(userReason);
	}
	
	public void setName(String name){
		this.userName=new String(name);
		usernameElement.setText(name);
		setTypeId(name);
	}
	public void setPassWord(String pass){
		this.passWord=new String(pass);
		passwordElement.setText(pass);
	}
	public void setReason(String reason){
		this.userReason=new String(reason);
		reasonElement.setText(reason);
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
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		usernameElement = root.element("username");
		passwordElement = root.element("password");
		reasonElement = root.element("reason");
		
		userName = usernameElement.getText();
		passWord = passwordElement.getText();
		userReason = reasonElement.getText();
	}
	
	private void buildElement() {
		usernameElement = root.addElement("username");
		passwordElement = root.addElement("password");
		reasonElement = root.addElement("reason");
	}
}
