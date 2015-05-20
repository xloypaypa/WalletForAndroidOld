package translater.translater;

import java.util.Vector;

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
			ans+=t.getAllMessage()+"\r\n";
		}
		return ans;
	}
	
	public static void solve(Vector <String> file){
		Vector <String> message=new Vector<String>();
		for (int i=0;i<file.size();i++){
			if (file.get(i).equals("[end]")){
				Type type=new Type();
				type.solveTypeMessage(message);
				if (wordExist(type.getExtra().get(0).getTitle())){
					changeWord(type.getExtra().get(0).getTitle(), type.getExtra().get(0).getMessage());
				}else{
					words.addElement(new Word(type.getExtra().get(0).getTitle(), type.getExtra().get(0).getMessage()));
				}
			}else if (file.get(i).equals("[begin]")){
				message=new Vector<String>();
			}else{
				message.add(file.get(i));
			}
		}
	}
}
