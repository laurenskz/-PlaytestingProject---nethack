package A.B;


import java.util.ArrayList;

public class BottomBar{
	
	private ArrayList<String> messages;
	
	public BottomBar(){
		messages = new ArrayList<String>();
		
		
		messages.add("");
		messages.add("");
		messages.add("");
		messages.add("");
		
	}
	
	public void addMessage(String text){
		messages.add(0, text);
	}
	
	public String getMessage(int index){
		return messages.get(index);
	}
	
}