package logic.process.doDetail.money;

import logic.action.money.TransferAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoTransferDetail extends AbstractDoDetail {
	
	public DoTransferDetail() {
		super("transfer");
	}

	@Override
	public void solveDetail() {
		this.sureExtraExist("from type");
		
		TransferAction ta=new TransferAction();
		ta.setValue(detail.getExtraMessage("from type"), detail.getType(), detail.getValue());
		ta.run();
	}

}
