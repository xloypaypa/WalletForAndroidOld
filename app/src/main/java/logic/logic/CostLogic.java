package logic.logic;

import java.util.Date;

import database.operator.UserPublicData;
import logic.ListenerManager;
import logic.action.detail.WriteDetailAction;
import logic.action.money.ExpenditureAction;
import logic.action.money.IncomeAction;
import logic.action.money.TransferAction;
import logic.action.reason.ReasonExpenditureAction;
import logic.action.reason.ReasonIncomeAction;
import logic.action.reason.TreeReasonExpenditureAction;
import logic.action.reason.TreeReasonIncomeAction;
import logic.check.MoneyEnoughCheck;
import logic.check.TransferSameTypeCheck;
import logic.check.TreeReasonBudgetCheck;
import logic.check.ValueSignCheck;
import logic.event.NormalEvent;

public class CostLogic extends ListenerManager {
	
	public void income(String type,double value,String reason){
		NormalEvent event=new NormalEvent("income");
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "income", type, value, reason);
		event.addCheck(detail);
		
		ValueSignCheck vc=new ValueSignCheck();
		vc.setValue(value);
		event.addCheck(vc);
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(type, value);
		event.addCheck(ia);
		
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			TreeReasonIncomeAction tria=new TreeReasonIncomeAction();
			tria.setValue(reason, value);
			event.addCheck(tria);
		}else{
			ReasonIncomeAction ria=new ReasonIncomeAction();
			ria.setValue(reason, value);
			event.addCheck(ria);
		}
		
		event.doEvent();
	}
	
	public void expenditure(String type,double value,String reason){
		NormalEvent event=new NormalEvent("expenditure");
		
		ValueSignCheck vc=new ValueSignCheck();
		vc.setValue(value);
		event.addCheck(vc);
		
		MoneyEnoughCheck mec=new MoneyEnoughCheck();
		mec.setValue(type, value);
		event.addCheck(mec);
		
		if (UserPublicData.getUserReason(UserPublicData.getNowUser()).equals("tree")){
			TreeReasonExpenditureAction trea=new TreeReasonExpenditureAction();
			trea.setValue(reason, value);
			
			TreeReasonBudgetCheck trbc=new TreeReasonBudgetCheck();
			trbc.setValue(reason, value);
			
			event.addCheck(trbc);
			event.addCheck(trea);
		}else{
			ReasonExpenditureAction rea=new ReasonExpenditureAction();
			rea.setValue(reason, value);
			event.addCheck(rea);
		}
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "expenditure", type, value, reason);
		event.addCheck(detail);
		
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(type, value);
		event.addCheck(ea);
		
		event.doEvent();
	}
	
	public void transfer(String from,String to,double value){
		NormalEvent event=new NormalEvent("transfer");
		
		ValueSignCheck vc=new ValueSignCheck();
		vc.setValue(value);
		event.addCheck(vc);
		
		TransferSameTypeCheck tstc=new TransferSameTypeCheck();
		tstc.setValue(from, to);
		event.addCheck(tstc);
		
		MoneyEnoughCheck mec=new MoneyEnoughCheck();
		mec.setValue(from, value);
		event.addCheck(mec);
		
		WriteDetailAction detail=new WriteDetailAction();
		detail.setValue(new Date(), "transfer", to, value, "");
		detail.addExtra("from type", from);
		event.addCheck(detail);
		
		TransferAction ta=new TransferAction();
		ta.setValue(from, to, value);
		event.addCheck(ta);
		
		event.doEvent();
	}
	
}
