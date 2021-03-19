package A.B;


import java.util.ArrayList;

public class Dictonary{
	
	private ArrayList<String> names;
	
	public Dictonary(){
		names = new ArrayList<String>();
	}
	
	public void addName(String name){
		names.add(name);
	}
	
	public String getRandName(){
		return names.get((int)(Math.random() * names.size()));
	}
	
	
}