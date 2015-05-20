package logic.process.doDetail.money;

import logic.action.money.AddTypeAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoAddTypeDetail extends AbstractDoDetail {
	
	public DoAddTypeDetail() {
		super("add type");
	}

	@Override
	public void solveDetail() {
		AddTypeAction ata=new AddTypeAction();
		ata.setName(detail.getType());
		ata.run();
	}

}
