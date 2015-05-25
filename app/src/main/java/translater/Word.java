package translater;

public class Word {
	String word, ans;
	public Word(){
		word=new String();
		ans=new String();
	}
	public Word(String word){
		this.word=new String(word);
		this.ans=new String(word);
	}
	public Word(String word, String ans){
		this.word=new String(word);
		this.ans=new String(ans);
	}
	
	public void setWord(String word){
		this.word=new String(word);
	}
	public void setAns(String ans){
		this.ans=new String(ans);
	}
	
	public String getWord(){
		return word;
	}
	public String getAns(){
		return ans;
	}
}
