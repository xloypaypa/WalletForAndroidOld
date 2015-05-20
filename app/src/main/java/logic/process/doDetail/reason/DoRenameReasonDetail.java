package logic.process.doDetail.reason;

import logic.action.reason.RenameReasonAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRenameReasonDetail extends AbstractDoDetail {
	
	public DoRenameReasonDetail() {
		super("rename reason");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("past name");
		
		RenameReasonAction rra=new RenameReasonAction();
		rra.setValue(detail.getExtraMessage("past name"), detail.getReason());
		rra.run();
	}

}
