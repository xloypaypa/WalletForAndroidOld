package type;

import org.dom4j.Element;

public class IDType extends Type {
	
	protected String id;
	protected Element idElement;
	
	public IDType() {
		id=new String();
		idElement=root.addElement("id");
	}
	
	public String getTypeID(){
		return this.id;
	}
	
	public void setTypeId(String id) {
		this.id = id;
		idElement.setText(id);
	}

	@Override
	public void solveTypeMessage(Element message) {
		super.solveTypeMessage(message);
		idElement=message.element("id");
		id=idElement.getText();
	}
}
