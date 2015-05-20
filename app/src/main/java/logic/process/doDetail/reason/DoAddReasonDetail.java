package logic.process.doDetail.reason;

import logic.action.reason.AddReasonAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoAddReasonDetail extends AbstractDoDetail {
	
	public DoAddReasonDetail() {
		super("add reason");
	}

	@Override
	public void solveDetail() {
		AddReasonAction ara=new AddReasonAction();
		ara.setValue(detail.getReason());
		ara.run();
	}

}
