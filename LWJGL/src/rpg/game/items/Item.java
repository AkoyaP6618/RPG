package rpg.game.items;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rpg.game.player.Inventory;

public abstract class Item {

	private String name;
	private String description;
	protected String texturePath;
	private Texture texture;
	protected Tier tier;
	protected int requiredLvl;
	private int sellPrice;
	private int buyPrice;
	private final int size = 32;

	private int stack = 1;
	private int maxStacks = 99;
	protected boolean stackable = true;
	protected boolean isArmor = false;
	protected boolean isSkill = false;
	
	/*
	 * 	T1 = gewoehnlich 	(weiss)
	 * 	T2 = selten			(gruen)
	 * 	T3 = fantastisch	(blau)
	 *  T4 = episch			(lila)
	 *  T5 = orange			(orange)
	 */
	public static enum Tier{
		T1,T2,T3,T4,T5
	}
	
	public static enum ItemObject{
		smallHealthPotion, smallManaPotion, smallHealthPotionPlus, smallManaPotionPlus,
		mediumHeathPotion, mediumManaPotion, mediumHealthPotionPlus, mediumManaPotionPlus,
		largeHealthPotion, largeManaPotion, largeHealthPotionPlus, largeManaPotionPlus
	}
	
	
	public Item(String name, Tier tier, int neededLvl, int sellPrice, int buyPrice,String texture){
		this.name = name;
		this.tier = tier;
		this.requiredLvl = neededLvl;
		this.sellPrice = sellPrice;
		this.buyPrice = buyPrice;
		setTexture(texture);
	}
	
	public static Item get(ItemObject object){
		
		Item item = null;
		
		switch(object){
		case smallHealthPotion: item = new HealthPotion("kleiner Heiltrank", 100, "res/items/P_Red02.png"); 
		break;
		case smallManaPotion: item = new ManaPotion("kleiner Manatrank", 100, "res/items/P_Blue02.png"); 
		break;
		}
		return item;
	}
	
	public void render(int x,int y){
		texture.bind(); // or GL11.glBind(texture.getTextureID());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(x+size, y);
		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(x+size, y+size);
		GL11.glTexCoord2f(0,0);
		GL11.glVertex2f(x, y+size);
		GL11.glEnd();
	}
	
	public void use(){
		
	};
	
	public void setTexture(String texturePath){
		this.texturePath = texturePath;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(texturePath));
			} catch (IOException e) {
				e.printStackTrace();
		}  
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public boolean canAdd(){
		if(maxStacks-stack > 0) return true;
		return false;
	}
	
	public void add(){
		stack++;
	}
	
	public void remove(){
		stack--;
	}
	
	public void destroy(){
		Inventory.remove(this);
	}
	
	public int getStacks(){
		return stack;
	}
	
	public String getName(){
		return name;
	}
	
	public void setStacks(int i){
		stack = i;
	}
	
	public boolean isArmor(){
		return isArmor;
	}
	
	public int getRequiredLvl(){
		return requiredLvl;
	}
	
	public boolean isStackable(){
		return stackable;
	}
	
	public Tier getTier(){
		return tier;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String desc){
		description = desc;
	}
}
