package logic.process.doDetail;

import database.operator.DetailKeeper;
import logic.logicListener.LogicDoDetailListener;
import logic.process.AbstractProcess;

public abstract class AbstractDoDetail extends AbstractProcess {
	
	protected static LogicDoDetailListener listener=new LogicDoDetailListener() {
		@Override
		public void UIAction() {}
	};

	public AbstractDoDetail(String processName) {
		super(processName);
	}

	@Override
	public void process() {
		this.solveDetail();
		
		DetailKeeper keeper=(DetailKeeper) data.getData("detail");
		keeper.add(detail);
	}
	
	public static void setListener(LogicDoDetailListener listener){
		AbstractDoDetail.listener=listener;
	}
	
	public void sureExtraExist(String extra){
		if (!detail.extraExist(extra)){
			listener.setExtraName(extra);
			listener.UIAction();
			detail.addExtra(extra, listener.getAns());
		}
	}
	
	public abstract void solveDetail();

}
