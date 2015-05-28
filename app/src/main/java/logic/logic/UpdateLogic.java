package logic.logic;

import logic.ListenerManager;
import logic.action.detail.UserUpdateAction;
import logic.check.UserUpdateCheck;
import logic.event.NormalEvent;

public class UpdateLogic extends ListenerManager {
	
	public void update() {
		NormalEvent event = new NormalEvent("update");
		
		UserUpdateCheck userUpdateCheck = new UserUpdateCheck();
		UserUpdateAction userUpdateAction = new UserUpdateAction();
		
		event.addCheck(userUpdateCheck);
		event.addCheck(userUpdateAction);
		event.runCheck();
	}

}
