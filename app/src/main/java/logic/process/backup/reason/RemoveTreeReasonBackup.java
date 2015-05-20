package logic.process.backup.reason;

import java.util.Vector;

import database.operator.TreeReasonKeeper;
import type.ExtraType;
import type.ReasonTreeNodeType;
import logic.action.reason.AddTreeReasonAction;
import logic.action.reason.RenameTreeReasonAction;
import logic.action.reason.TreeReasonExpenditureAction;
import logic.action.reason.TreeReasonIncomeAction;
import logic.process.backup.AbstractBackup;

public class RemoveTreeReasonBackup extends AbstractBackup {
	
	public RemoveTreeReasonBackup() {
		super("remove tree reason");
	}

	@Override
	public void backup() {
		AddTreeReasonAction atra=new AddTreeReasonAction();
		atra.setValue(detail.getReason());
		atra.setValue(detail.getExtraMessage("past reason father"),
				Double.valueOf(detail.getExtraMessage("past reason min")), 
				Double.valueOf(detail.getExtraMessage("past reason max")), 
				Integer.valueOf(detail.getExtraMessage("past reason rank")));
		atra.run();
		
		TreeReasonExpenditureAction trea=new TreeReasonExpenditureAction();
		trea.setValue(detail.getReason(), Double.valueOf(detail.getExtraMessage("past reason expenditure")));
		trea.run();
		
		TreeReasonIncomeAction tria=new TreeReasonIncomeAction();
		tria.setValue(detail.getReason(), Double.valueOf(detail.getExtraMessage("past reason income")));
		tria.run();
		
		RenameTreeReasonAction rtra=new RenameTreeReasonAction();
		TreeReasonKeeper keeper=(TreeReasonKeeper) data.getData("reason");
		Vector <ExtraType> all=detail.getExtra();
		for (int i=0;i<all.size();i++){
			if (all.get(i).getTitle().equals("have kid reason")){
				ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(all.get(i).getMessage());
				rtra.setValue(reason.getName(), reason.getName());
				rtra.setValue(detail.getExtraMessage("past reason name"), 
						reason.getMin(), reason.getMax(), reason.getRank());
				rtra.run();
			}
		}
	}

}
