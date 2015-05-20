package logic.process.doDetail.debt;

import logic.action.debt.withTime.RepayDebtWithTimeAction;
import logic.action.money.ExpenditureAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRepayBorrowingDetail extends AbstractDoDetail {
	
	public DoRepayBorrowingDetail() {
		super("repay borrowing");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("operator id");
		
		RepayDebtWithTimeAction rba=new RepayDebtWithTimeAction();
		rba.setValue(Integer.valueOf(detail.getExtraMessage("operator id")), detail.getValue());
		rba.setTime(detail.getTime());
		rba.run();
		
		ExpenditureAction ea=new ExpenditureAction();
		ea.setValue(detail.getType(), detail.getValue());
		ea.run();
	}

}
