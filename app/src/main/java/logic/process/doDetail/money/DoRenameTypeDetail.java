package logic.process.doDetail.money;

import logic.action.money.RenameTypeAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRenameTypeDetail extends AbstractDoDetail {
	
	public DoRenameTypeDetail() {
		super("rename type");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("past type name");
		
		RenameTypeAction rta=new RenameTypeAction();
		rta.setValue(detail.getExtraMessage("past type name"), detail.getType());
		rta.run();
	}

}
