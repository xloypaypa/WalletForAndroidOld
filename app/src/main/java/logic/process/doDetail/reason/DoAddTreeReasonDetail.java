package logic.process.doDetail.reason;

import logic.action.reason.AddTreeReasonAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoAddTreeReasonDetail extends AbstractDoDetail {
	
	public DoAddTreeReasonDetail() {
		super("add tree reason");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("operator father");
		this.sureExtraExist("operator min");
		this.sureExtraExist("operator max");
		this.sureExtraExist("operator rank");
		
		AddTreeReasonAction atra=new AddTreeReasonAction();
		atra.setValue(detail.getReason());
		atra.setValue(detail.getExtraMessage("operator father"), 
				Double.valueOf(detail.getExtraMessage("operator min")), 
				Double.valueOf(detail.getExtraMessage("operator max")), 
				Integer.valueOf(detail.getExtraMessage("operator rank")));
		atra.run();
	}

}
