package logic.logic;

import database.operator.DetailKeeper;
import database.operator.UserPublicData;
import type.DetailType;
import logic.ListenerManager;
import logic.process.backup.debt.AddBorrowingBackup;
import logic.process.backup.debt.AddLoanBackup;
import logic.process.backup.debt.RepayBorrowingBackup;
import logic.process.backup.debt.RepayLoanBackup;
import logic.process.backup.money.AddTypeBackup;
import logic.process.backup.money.ExpenditureBackup;
import logic.process.backup.money.IncomeBackup;
import logic.process.backup.money.RemoveTypeBackup;
import logic.process.backup.money.RenameTypeBackup;
import logic.process.backup.money.TransferBackup;
import logic.process.backup.reason.AddReasonBackup;
import logic.process.backup.reason.AddTreeReasonBackup;
import logic.process.backup.reason.RemoveReasonBackup;
import logic.process.backup.reason.RemoveTreeReasonBackup;
import logic.process.backup.reason.RenameReasonBackup;
import logic.process.backup.reason.RenameTreeReasonBackup;

public class BackupLogic extends ProcessLogic {
	
	public BackupLogic() {
		this.addProcess(new AddBorrowingBackup());
		this.addProcess(new AddLoanBackup());
		
		this.addProcess(new RepayBorrowingBackup());
		this.addProcess(new RepayLoanBackup());
		
		this.addProcess(new ExpenditureBackup());
		this.addProcess(new IncomeBackup());
		this.addProcess(new TransferBackup());
		
		this.addProcess(new AddTypeBackup());
		this.addProcess(new RemoveTypeBackup());
		this.addProcess(new RenameTypeBackup());
		
		if (UserPublicData.getUserType().equals("tree")){
			this.addProcess(new AddTreeReasonBackup());
			this.addProcess(new RemoveTreeReasonBackup());
			this.addProcess(new RenameTreeReasonBackup());
		}else{
			this.addProcess(new AddReasonBackup());
			this.addProcess(new RemoveReasonBackup());
			this.addProcess(new RenameReasonBackup());
		}	
	}
	
	public void runProcess(){
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		DetailType last=keeper.getLastDetail();
		boolean flag=false;
		for (int i=0;i<allProcess.size();i++){
			if (allProcess.get(i).isThisProcess(last.getEvent())){
				allProcess.get(i).setDetail(last);
				allProcess.get(i).process();
				flag=true;
			}
		}
		if (!flag){
			ListenerManager.setErrorMessage("Last detail can't be removed.");
			ListenerManager.UIAction();
		}
	}

}
