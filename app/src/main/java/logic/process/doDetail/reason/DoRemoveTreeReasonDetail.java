package logic.process.doDetail.reason;

import logic.action.reason.RemoveTreeReasonAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRemoveTreeReasonDetail extends AbstractDoDetail {
	
	public DoRemoveTreeReasonDetail() {
		super("remove tree reason");
	}

	@Override
	public void solveDetail() {
		RemoveTreeReasonAction rtra=new RemoveTreeReasonAction();
		rtra.setValue(detail.getReason());
		rtra.run();
	}

}
