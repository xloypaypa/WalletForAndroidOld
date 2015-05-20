package logic.process.doDetail.money;
import logic.action.money.RemoveTypeAction;
import logic.process.doDetail.AbstractDoDetail;

public class DoRemoveTypeDetail extends AbstractDoDetail {
	
	public DoRemoveTypeDetail() {
		super("remove type");
	}

	@Override
	public void solveDetail() {
		RemoveTypeAction rta=new RemoveTypeAction();
		rta.setType(detail.getType());
		rta.run();
	}

}
