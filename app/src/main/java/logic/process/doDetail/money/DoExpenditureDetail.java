package logic.process.doDetail.money;

import database.operator.UserPublicData;
import logic.action.money.ExpenditureAction;
import logic.action.reason.ReasonExpenditureAction;
import logic.action.reason.TreeReasonExpenditureAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoExpenditureDetail extends AbstractDoDetail {
	
	public DoExpenditureDetail() {
		super("expenditure");
	}

	@Override
	public void solveDetail() {
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.run();
		
		ReasonExpenditureAction rea;
		if (UserPublicData.getUserType().equals("tree")){
			rea=new TreeReasonExpenditureAction();
		}else{
			rea=new ReasonExpenditureAction();
		}
		rea.setValue(detail.getReason(), detail.getValue());
		rea.run();
	}

}
