package logic.event;

import java.util.Vector;

import logic.Logic;

public class NormalEvent extends Logic {
	
	String eventName;
	
	Vector<AbstractSteep> check=new Vector<>();
	
	public NormalEvent(String eventName) {
		this.eventName=eventName;
	}
	
	public void addCheck(AbstractSteep check){
		this.check.addElement(check);
	}
	
	public void cleanCheck(){
		this.check.removeAllElements();
	}
	
	public AbstractSteep getCheck(String name){
		for (int i=0;i<check.size();i++){
			if (check.get(i).isThisCheck(name)) return check.get(i);
		}
		return null;
	}
	
	public boolean runCheck(){
		for (int i=0;i<check.size();i++){
			if (!check.get(i).action()) return false;
		}
		return true;
	}
	
	public boolean isThisEvent(String name){
		if (name.equals(eventName)) return true;
		else return false;
	}
	
	public void doEvent() {
		for (int i=0;i<check.size();i++) {
			if (!check.get(i).action()) return ;
		}
	}
}
