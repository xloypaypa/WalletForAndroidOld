package logic.process.backup.debt;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import database.operator.DebtOperator;
import type.DebtType;
import logic.process.backup.AbstractBackup;

public class RepayDebtBackup extends AbstractBackup {

	public RepayDebtBackup(String processName) {
		super(processName);
	}

	@Override
	public void backup() {
		String id=detail.getExtraMessage("past debt id");
		DebtType debt=new DebtType();
		debt.setCreditor(detail.getExtraMessage("past debt creditor"));
		
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			debt.setDeadline(sdf.parse(detail.getExtraMessage("past debt deadline")));
			debt.setLastUpdateTime(sdf.parse(detail.getExtraMessage("past debt update")));
			debt.setStartingTime(sdf.parse(detail.getExtraMessage("past debt starting")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		debt.setDebtType(detail.getExtraMessage("past debt type"));
		debt.setID(Integer.valueOf(detail.getExtraMessage("past debt id")));
		debt.setRate(detail.getExtraMessage("past debt rate type"), Double.valueOf(detail.getExtraMessage("past debt rate")));
		debt.setValue(Double.valueOf(detail.getExtraMessage("past debt value")));
		
		DebtOperator keeper=(DebtOperator) data.getData("debt");
		if (keeper.itemExist(id)){
			keeper.update(id, debt);
		}else{
			keeper.add(debt);
		}
		
	}

}
