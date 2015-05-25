package type;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Type implements TypeInterface {
	
	protected Element root;
	
	protected String id;
	protected Element idElement;
	
	public Type() {
		root=DocumentHelper.createElement("type");
		
		id=new String();
		idElement=root.addElement("id");
		
		root.addElement("extra");
	}
	
	public void addExtra(String title,String message){
		title = title.replace(' ', '_');
		
		root.element("extra").addElement(title).setText(message);
	}
	
	public boolean extraExist(String title){
		title = title.replace(' ', '_');
		return root.element("extra").element(title) != null;
	}
	
	public void removeExtra(String title){
		title = title.replace(' ', '_');
		if (!extraExist(title)) return ;
		root.element("extra").remove(root.element("extra").element(title));
	}
	
	public String getExtraMessage(String title){
		title = title.replace(' ', '_');
		if (!extraExist(title)) return new String();
		return root.element("extra").element(title).getText();
	}
	
	public String getTypeID(){
		return this.id;
	}
	
	public void setTypeId(String id) {
		this.id = id;
		idElement.setText(id);
	}
	
	public Element getRoot() {
		return root;
	}
	
	public Element getExtra() {
		return root.element("extra");
	}

	@Override
	public String format() {
		return root.asXML()+"\r\n";
	}

	@Override
	public void solveTypeMessage(Element message) {
		this.root = message;
		idElement=message.element("id");
		id=idElement.getText();
	}
}
