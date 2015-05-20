package logic.logic;

import java.util.Date;

import database.operator.IDDataKeeper;
import logic.Logic;
import logic.action.detail.DetailSaveMoneyAction;
import logic.action.detail.WriteDetailAction;
import logic.action.money.AddTypeAction;
import logic.action.money.RemoveTypeAction;
import logic.action.money.RenameTypeAction;
import logic.check.ExistCheck;
import logic.check.NameCheck;
import logic.event.NormalEvent;

public class TypeLogic extends Logic {
	
	public void addType(String type){
		NormalEvent event=new NormalEvent("add type");
		
		AddTypeAction ata=new AddTypeAction();
		ata.setName(type);
		
		NameCheck nc=new NameCheck();
		nc.setName(type);
		event.addCheck(nc);
		
		ExistCheck ec=new ExistCheck();
		ec.setKeeper((IDDataKeeper) data.getData("money"));
		ec.setID(type);
		event.addCheck(ec);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "add type", type, 0, "");
		event.addCheck(detail);
		event.addCheck(ata);
		
		event.doEvent();
	}
	
	public void renameType(String type, String name){
		NormalEvent event=new NormalEvent("rename type");
		
		RenameTypeAction rta=new RenameTypeAction();
		rta.setValue(type, name);
		
		NameCheck nc=new NameCheck();
		nc.setName(name);
		event.addCheck(nc);
		
		ExistCheck ec=new ExistCheck();
		ec.setID(name);
		ec.setKeeper((IDDataKeeper) data.getData("money"));
		event.addCheck(ec);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "rename type", name, 0, "");
		detail.addExtra("past type name", type);
		event.addCheck(detail);
		event.addCheck(rta);
		
		event.doEvent();
	}
	
	public void removeType(String type){
		NormalEvent event=new NormalEvent("remove type");
		
		RemoveTypeAction rta=new RemoveTypeAction();
		rta.setType(type);
		
		DetailSaveMoneyAction detail=new DetailSaveMoneyAction();
		detail.setValue(new Date(), "remove type", type, 0, "");
		detail.saveMoney(type);
		event.addCheck(detail);
		
		event.addCheck(rta);
		
		event.doEvent();
		
	}
}
