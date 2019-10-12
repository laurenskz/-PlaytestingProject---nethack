import java.awt.Color;

public class ItemTile extends Tile{
	
	private Item item;
	
	public ItemTile(Item item, int x, int y){
		super(x, y);
		super.setImage(this.processImage(item));
		super.setColor(this.processColor(item));
		this.item = item;
	}
	
	private String processImage(Item item){
		if(item instanceof Gold)
			return "$";
		else if(item instanceof Sword)
			return "/";
		else if(item instanceof Bow)
			return ")";
		else if(item instanceof HealthPotion)
			return "+";
		else
			return "E";
	}
	
	private Color processColor(Item item){
		if(item instanceof Gold)
			return new Color(255, 230, 0);
		else if(item instanceof Sword)
			return new Color(144, 144, 144);
		else if(item instanceof Bow)
			return new Color(146, 68, 0);
		else if(item instanceof HealthPotion)
			return new Color(249, 45, 14);
		else
			return Color.green;
	}
	
	public Item getItem(){
		return item;
	}
	
	public String toString(){
		return item.toString();
	}
}