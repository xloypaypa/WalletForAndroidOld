package logic.logicListener;

public abstract class LogicMessageListener implements LogicListener {
	
	protected boolean errorFlag;
	protected String message;
	
	public void setMessage(String message){
		this.message=message;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setErrorFlag(boolean flag){
		this.errorFlag=flag;
	}
	
	public boolean getErrorFlag(){
		return this.errorFlag;
	}
}
