package logic.process.doDetail.money;

import database.operator.UserPublicData;
import logic.action.money.IncomeAction;
import logic.action.reason.ReasonIncomeAction;
import logic.action.reason.TreeReasonIncomeAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoIncomeDetail extends AbstractDoDetail {
	
	public DoIncomeDetail() {
		super("income");
	}

	@Override
	public void solveDetail() {
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.run();
		
		ReasonIncomeAction ria;
		if (UserPublicData.getUserType().equals("tree")){
			ria=new TreeReasonIncomeAction();
		}else{
			ria=new ReasonIncomeAction();
		}
		ria.setValue(detail.getReason(), detail.getValue());
		ria.run();
	}

}
