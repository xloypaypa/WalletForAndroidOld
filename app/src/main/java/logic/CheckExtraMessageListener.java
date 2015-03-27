package logic;

import type.DetailType;

public abstract class CheckExtraMessageListener implements LogicListener {
	DetailType detail;
	String check;
	
	public void setDetail(DetailType detail){
		this.detail=detail;
	}
	
	public void setCheck(String check){
		this.check=check;
	}
	
	public DetailType getDetail(){
		return this.detail;
	}
	
	public String getCheck(){
		return this.check;
	}
}
