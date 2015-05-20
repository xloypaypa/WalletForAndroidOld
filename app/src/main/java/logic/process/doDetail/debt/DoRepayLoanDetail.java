package logic.process.doDetail.debt;

import logic.action.debt.withTime.RepayDebtWithTimeAction;
import logic.action.money.IncomeAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRepayLoanDetail extends AbstractDoDetail {
	
	public DoRepayLoanDetail() {
		super("repay loan");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("operator id");
		
		RepayDebtWithTimeAction rba=new RepayDebtWithTimeAction();
		rba.setValue(Integer.valueOf(detail.getExtraMessage("operator id")), detail.getValue());
		rba.setTime(detail.getTime());
		rba.run();
		
		IncomeAction ea=new IncomeAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.run();
	}

}
