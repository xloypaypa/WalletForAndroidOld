package logic.logic;

import logic.ListenerManager;
import database.operator.UserPublicData;
import translater.Strings;
import type.UserMessage;
import encryptionAlgorithm.MD5;

public class UserLogic extends ListenerManager {
	
	public static boolean login(String name,String pass){
		if (UserPublicData.checkUser(name, pass)){
			UserPublicData.login(name, pass);
			setOKMessage();
			return true;
		}else{
			message.setErrorFlag(true);
			message.setMessage(Strings.getWord("user name or pass word wrong."));
			message.UIAction();
			return false;
		}
	}
	
	public static boolean register(String name,String pass,String repeat,String reason){
		if (name.equals("temp")){
			message.setErrorFlag(true);
			message.setMessage(Strings.getWord("This user is a system used."));
			message.UIAction();
			return false;
		}else if (name.length()==0){
			setNeedInputError("user name");
			message.UIAction();
			return false;
		}else if (pass.length()==0){
			setNeedInputError("password");
			message.UIAction();
			return false;
		}else if (!repeat.equals(pass)){
			message.setErrorFlag(true);
			message.setMessage(Strings.getWord("Two password not same."));
			message.UIAction();
			return false;
		}else if (UserPublicData.userExist(name)){
			setExistentError("user");
			message.UIAction();
			return false;
		}else{
			UserMessage user=new UserMessage();
			user.setName(name);
			user.setPassWord(MD5.encode(pass));
			user.setReason(reason);
			UserPublicData.addUser(user);
			setOKMessage();
			return true;
		}
	}
}
