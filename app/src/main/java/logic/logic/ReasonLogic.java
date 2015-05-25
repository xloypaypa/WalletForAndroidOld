package logic.logic;

import java.util.Date;

import database.operator.IDDataOperator;
import logic.Logic;
import logic.action.detail.DetailSaveReasonAction;
import logic.action.detail.WriteDetailAction;
import logic.action.reason.AddReasonAction;
import logic.action.reason.RemoveReasonAction;
import logic.action.reason.RenameReasonAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.NormalEvent;

public class ReasonLogic extends Logic {
	
	public void addReason(String name){
		NormalEvent event=new NormalEvent("add reason");
		
		AddReasonAction ara=new AddReasonAction();
		ara.setValue(name);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataOperator) data.getData("reason"));
		ec.setID(name);
		
		
		event.addCheck(ec);
		event.addCheck(nc);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "add reason", "", 0, name);
		
		event.addCheck(detail);
		event.addCheck(ara);
		
		event.doEvent();
	}
	
	public void renameReason(String past,String name){
		NormalEvent event=new NormalEvent("rename reason");
		
		RenameReasonAction rra=new RenameReasonAction();
		rra.setValue(past, name);
		
		NameCheck nc=new NameCheck();
		nc.setName(past);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataOperator) data.getData("reason"));
		ec.setID(name);
		
		event.addCheck(ec);
		event.addCheck(nc);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "rename reason", "", 0, name);
		detail.addExtra("past name", past);
		event.addCheck(detail);
		event.addCheck(rra);
		
		event.doEvent();
	}
	
	public void removeReason(String name){
		NormalEvent event=new NormalEvent("remove reason");
		
		RemoveReasonAction rra=new RemoveReasonAction();
		rra.setValue(name);
		
		DetailSaveReasonAction detail=new DetailSaveReasonAction();
		detail.setValue(new Date(), "remove reason", "", 0, name);
		detail.saveReason(name);
		event.addCheck(detail);
		
		event.addCheck(rra);
		
		event.doEvent();
	}

}
