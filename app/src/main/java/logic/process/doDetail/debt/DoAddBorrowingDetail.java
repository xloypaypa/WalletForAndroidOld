package logic.process.doDetail.debt;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import logic.action.debt.withTime.AddBorrowingWithTimeAction;
import logic.action.money.IncomeAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoAddBorrowingDetail extends AbstractDoDetail {
	
	public DoAddBorrowingDetail() {
		super("add borrowing");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("debt id");
		this.sureExtraExist("operator creditor");
		this.sureExtraExist("operator deadline");
		this.sureExtraExist("operator rate");
		this.sureExtraExist("operator rate type");
		
		AddBorrowingWithTimeAction aba=new AddBorrowingWithTimeAction();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			aba.setValue(Integer.valueOf(detail.getExtraMessage("debt id")),
					detail.getExtraMessage("operator creditor"),
					detail.getValue(),
					sdf.parse(detail.getExtraMessage("operator deadline")),
					Double.valueOf(detail.getExtraMessage("operator rate")),
					detail.getExtraMessage("operator rate type"));
			aba.setTime(detail.getTime());
			aba.run();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		IncomeAction ia=new IncomeAction();
		ia.setValue(detail.getType(), detail.getValue());
		ia.run();
	}

}
