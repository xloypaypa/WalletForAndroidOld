package type;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Type implements TypeInterface {
	
	protected Element root;
	
	public Type() {
		root=DocumentHelper.createElement("type");
		root.addElement("extra");
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
	
	public void addExtra(String title,String message){
		title = title.replace(' ', '_');
		
		root.element("extra").addElement(title).setText(message);
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
	}

}
