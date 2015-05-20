package logic.process.doDetail.reason;

import logic.action.reason.RenameTreeReasonAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRenameTreeReasonDetail extends AbstractDoDetail {
	
	public DoRenameTreeReasonDetail() {
		super("rename tree reason");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("operator name");
		this.sureExtraExist("operator father");
		this.sureExtraExist("operator min");
		this.sureExtraExist("operator max");
		this.sureExtraExist("operator rank");
		
		RenameTreeReasonAction rtra=new RenameTreeReasonAction();
		rtra.setValue(detail.getExtraMessage("operator past"), detail.getReason());
		rtra.setValue(detail.getExtraMessage("operator father"), 
				Double.valueOf(detail.getExtraMessage("operator min")), 
				Double.valueOf(detail.getExtraMessage("operator max")), 
				Integer.valueOf(detail.getExtraMessage("operator rank")));
		rtra.run();
	}

}
