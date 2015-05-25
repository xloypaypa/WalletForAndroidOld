package logic.process.backup.reason;

import org.dom4j.Element;

import database.operator.TreeReasonOperator;
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
		TreeReasonOperator keeper=(TreeReasonOperator) data.getData("reason");
		Element all = detail.getExtra();
		for (int i=0;i<all.elements().size();i++){
			Element now = (Element) all.elements().get(i);
			if (now.getName().equals("have_kid_reason")) {
				ReasonTreeNodeType reason=(ReasonTreeNodeType) keeper.getItem(now.getText());
				rtra.setValue(reason.getName(), reason.getName());
				rtra.setValue(detail.getExtraMessage("past reason name"), 
						reason.getMin(), reason.getMax(), reason.getRank());
				rtra.run();
			}
		}
	}

}
