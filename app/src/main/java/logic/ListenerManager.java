package logic;

import java.text.DecimalFormat;

import translater.Strings;
import logic.logicListener.LogicMessageListener;

public class ListenerManager {
	protected static LogicMessageListener message=new LogicMessageListener() {
		@Override
		public void UIAction() {}
	};
	
	public static void setListenser(LogicMessageListener message){
		ListenerManager.message=message;
	}
	
	public static void setErrorMessage(String msg){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord(msg));
	}
	
	public static void setLeftHalfBracketError(){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord("Please don't use '['!"));
	}
	
	public static void setExistentError(String name){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord("This ")+name+Strings.getWord(" have exist."));
	}
	
	public static void setNeedInputError(String name){
		message.setErrorFlag(true);
		message.setMessage(Strings.getWord("Please input ")+name+".");
	}
	
	public static void setDebtRepayLimitError(double value){
		message.setErrorFlag(true);
		DecimalFormat df=new DecimalFormat("0.00");
		message.setMessage(Strings.getWord("This debt onlu need ")+df.format(value)+".");
	}
	
	public static void setOKMessage(){
		message.setErrorFlag(false);
		message.setMessage(Strings.getWord("ok."));
	}
	
	public static void UIAction(){
		message.UIAction();
	}
	
}
