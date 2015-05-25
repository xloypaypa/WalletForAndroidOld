package logic.action.detail;

import java.text.SimpleDateFormat;

import database.operator.DebtOperator;
import type.DebtType;

public class DetailSaveDebtAction extends WriteDetailAction {
	
	public void saveDetail(String id){
		DebtType debt=getDebt(id);
		saveBaseMessage(debt);
	}
	
	protected void saveBaseMessage(DebtType debt) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		detail.addExtra("past debt id", debt.getID()+"");
		detail.addExtra("past debt creditor", debt.getCreditor());
		detail.addExtra("past debt type", debt.getDebtType());
		detail.addExtra("past debt deadline", sdf.format(debt.getDeadline()));
		detail.addExtra("past debt update", sdf.format(debt.getLastUpdateTime()));
		detail.addExtra("past debt starting", sdf.format(debt.getStartingTime()));
		detail.addExtra("past debt rate", debt.getRate().getRate()+"");
		detail.addExtra("past debt rate type", debt.getRate().getType());
		detail.addExtra("past debt value", debt.getValue()+"");
	}

	protected DebtType getDebt(String id){
		DebtOperator keeper=(DebtOperator) data.getData("debt");
		DebtType debt=(DebtType) keeper.getItem(id);
		return debt;
	}

}

