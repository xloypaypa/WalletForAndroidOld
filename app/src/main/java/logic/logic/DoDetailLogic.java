package logic.logic;

import java.util.Vector;

import database.operator.DetailKeeper;
import database.operator.UserPublicData;
import type.DetailType;
import type.Type;
import logic.ListenerManager;
import logic.process.doDetail.debt.DoAddBorrowingDetail;
import logic.process.doDetail.debt.DoAddLoanDetail;
import logic.process.doDetail.debt.DoRepayBorrowingDetail;
import logic.process.doDetail.debt.DoRepayLoanDetail;
import logic.process.doDetail.money.DoAddTypeDetail;
import logic.process.doDetail.money.DoExpenditureDetail;
import logic.process.doDetail.money.DoIncomeDetail;
import logic.process.doDetail.money.DoRemoveTypeDetail;
import logic.process.doDetail.money.DoRenameTypeDetail;
import logic.process.doDetail.money.DoTransferDetail;
import logic.process.doDetail.reason.DoAddReasonDetail;
import logic.process.doDetail.reason.DoAddTreeReasonDetail;
import logic.process.doDetail.reason.DoRemoveReasonDetail;
import logic.process.doDetail.reason.DoRemoveTreeReasonDetail;
import logic.process.doDetail.reason.DoRenameReasonDetail;
import logic.process.doDetail.reason.DoRenameTreeReasonDetail;

public class DoDetailLogic extends ProcessLogic {
	
	public DoDetailLogic() {
		this.addProcess(new DoAddBorrowingDetail());
		this.addProcess(new DoAddLoanDetail());
		this.addProcess(new DoRepayBorrowingDetail());
		this.addProcess(new DoRepayLoanDetail());
		this.addProcess(new DoAddTypeDetail());
		this.addProcess(new DoExpenditureDetail());
		this.addProcess(new DoIncomeDetail());
		this.addProcess(new DoRemoveTypeDetail());
		this.addProcess(new DoRenameTypeDetail());
		this.addProcess(new DoTransferDetail());
		if (UserPublicData.getUserType().equals("tree")){
			this.addProcess(new DoAddTreeReasonDetail());
			this.addProcess(new DoRemoveTreeReasonDetail());
			this.addProcess(new DoRenameTreeReasonDetail());
		}else{
			this.addProcess(new DoAddReasonDetail());
			this.addProcess(new DoRemoveReasonDetail());
			this.addProcess(new DoRenameReasonDetail());
		}
	}
	
	public void setPath(String path){
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.load(path);
		doDetail(new Vector<>(keeper.getAllItem()));
	}
	
	public void doDetail(Vector <Type> detail){
		data.cleanAllData();
		
		for (int i=0;i<detail.size();i++){
			DetailType now=(DetailType) detail.get(i);
			boolean flag=false;
			for (int j=0;j<allProcess.size();j++){
				if (allProcess.get(j).isThisProcess(now.getEvent())){
					allProcess.get(j).setDetail(now);
					allProcess.get(j).process();
					flag=true;
				}
			}
			if (!flag){
				ListenerManager.setErrorMessage("Event not found error.");
				ListenerManager.UIAction();
			}
		}
	}

}
