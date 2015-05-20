package logic.logicListener;

public abstract class LogicDoDetailListener implements LogicListener {
	
	protected String extraName,ans;
	
	public void setExtraName(String name){
		this.extraName=name;
	}
	
	public String getAns(){
		return this.ans;
	}

}
