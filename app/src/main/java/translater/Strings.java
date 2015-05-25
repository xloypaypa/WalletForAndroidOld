package translater;

import java.util.Vector;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import type.Type;

public class Strings {
	public static Vector <Word> words=new Vector<Word>();
	
	public static boolean wordExist(String word){
		for (int i=0;i<words.size();i++){
			if (words.get(i).getWord().equals(word)) return true;
		}
		return false;
	}
	
	public static String getWord(String word){
		for (int i=0;i<words.size();i++){
			if (words.get(i).getWord().equals(word)) return words.get(i).getAns();
		}
		return word;
	}
	
	public static void changeWord(String word, String ans){
		for (int i=0;i<words.size();i++){
			if (words.get(i).getWord().equals(word)){
				words.get(i).setAns(ans);
			}
		}
	}
	
	public static String getTranlate(){
		String ans=new String();
		for (int i=0;i<words.size();i++){
			Word now=words.get(i);
			Type t=new Type();
			t.addExtra(now.getWord(), now.getAns());
			ans+=t.format()+"\r\n";
		}
		return ans;
	}
	
	public static void solve(Vector <String> file){
		String all = new String();
		for (int i=0;i<file.size();i++) {
			all += file.get(i);
		}
		try {
			Element element = DocumentHelper.parseText(all).getRootElement();
			for (int i=0;i<element.elements().size();i++) {
				Element now = (Element) element.elements().get(i);
				if (wordExist(now.getName())) {
					changeWord(now.getName(), now.getText());
				}else{
					words.addElement(new Word(now.getName(), now.getText()));
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
