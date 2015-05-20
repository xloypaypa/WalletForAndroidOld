package logic.process.doDetail.reason;

import logic.action.reason.RemoveReasonAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRemoveReasonDetail extends AbstractDoDetail {
	
	public DoRemoveReasonDetail() {
		super("remove reason");
	}

	@Override
	public void solveDetail() {
		RemoveReasonAction rra=new RemoveReasonAction();
		rra.setValue(detail.getReason());
		rra.run();
	}

}
