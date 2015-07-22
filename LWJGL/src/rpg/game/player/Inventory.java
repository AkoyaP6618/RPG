package rpg.game.player;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import rpg.game.Font;
import rpg.game.Game;
import rpg.game.items.Item;

public class Inventory {

	private static final int space = 90;
	private static ArrayList<Item> inventory = new ArrayList();
	
	public static void add(Item i){
		for (Item item : inventory) {
			if(i.getClass() ==item.getClass() && item.canAdd()){
				item.add();
				return;
			}
		}

		if(inventory.size() < space){
			inventory.add(i);
		}else{
			System.out.println("INVENTAR IS VOLL DU SPACKN");
		}
	}
	
	public static void remove(Item i){
		boolean foundItem = false;
		int j = 0;
		int index = 0;
		
		int stacks = 1000;
		
		// get item with lowest stacks
		for (Item item : inventory) {
			if(i.getClass() ==item.getClass() && item.getStacks() < stacks){
				stacks = item.getStacks();
				index = j;
			}
			j++;
		}
		
		Item item = inventory.get(index);

		if(item.getStacks() > 1){
			item.remove();
		}else if(item.getStacks() == 1){
			inventory.remove(index);
		}
	}
	
	public static Item get(int index){
		if(inventory.size() > 0 && index < inventory.size()) return inventory.get(index);
		return null;
	}
	
	public static void render(int x, int y){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/4-5, y + Game.SCREEN_HEIGHT/5-5);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH*3/4+5, y + Game.SCREEN_HEIGHT/5-5);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH*3/4+5, y + Game.SCREEN_HEIGHT*4/5+5);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/4-5, y + Game.SCREEN_HEIGHT*4/5+5);
		GL11.glEnd();
		
		GL11.glColor3f(0.3f, 0.3f, 0.3f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/4, y + Game.SCREEN_HEIGHT/5);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH*3/4, y + Game.SCREEN_HEIGHT/5);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH*3/4, y + Game.SCREEN_HEIGHT*4/5);
		GL11.glVertex2f(x + Game.SCREEN_WIDTH/4, y + Game.SCREEN_HEIGHT*4/5);
		GL11.glEnd();
		
		int size = 32;
		int index = 0; 
		
		for (int j = 0; j <= 8; j++){
		for(int i = 0; i <=9 ; i++){
			GL11.glColor3f(0.5f, 0.5f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/4 + i*4 + i*size + size/2, y + Game.SCREEN_HEIGHT*4/5 - 25 - j*4 - j*size);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/4 + i*4 + i*size + size*3/2, y + Game.SCREEN_HEIGHT*4/5-25 - j*4 - j*size);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/4 + i*4 + i*size + size*3/2, y + Game.SCREEN_HEIGHT*4/5-25- j*4 -j*size - size);
			GL11.glVertex2f(x + Game.SCREEN_WIDTH/4 + i*4 + i*size + size/2, y + Game.SCREEN_HEIGHT*4/5-25- j*4 - j*size - size);
			GL11.glEnd();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1, 1, 1);
			
			int itemX = x + Game.SCREEN_WIDTH/4 + i*4 + i*size + size/2;
			int itemY = y + Game.SCREEN_HEIGHT*4/5 - 25 - j*4 - j*size - size;
			
			if(Inventory.get(index) != null){
				Inventory.get(index).render(x + Game.SCREEN_WIDTH/4 + i*4 + i*size + size/2, y + Game.SCREEN_HEIGHT*4/5 - 25 - j*4 - j*size - size);
				String stacks = Integer.toString(Inventory.get(index).getStacks());
				Font.render(stacks, itemX + 32 - 8*stacks.length(), itemY);
			}
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			index++;
		}}
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		Font.render("Inventory", x + Game.SCREEN_WIDTH/2 - 9*4, y + Game.SCREEN_HEIGHT*4/5-16);
	}
	
	public static void processInput(int button, int mouseX, int mouseY){
		int size = 32;
		int k = 0; 
		int index = -1;
		
		for (int j = 0; j <= 8; j++){
		for(int i = 0; i <=9 ; i++){
			
			int itemX = Game.PLAYER.getCameraX() + Game.SCREEN_WIDTH/4 + i*4 + i*size + size/2;
			int itemY = Game.PLAYER.getCameraY() + Game.SCREEN_HEIGHT*4/5 - 25 - j*4 - j*size - size;

			if(	mouseX > itemX
				&&mouseX < itemX + size
				&&mouseY > itemY
				&&mouseY < itemY + size
				&&inventory.size() > k
				&&inventory.get(k) != null){
				index = k;
				break;
			}
			
			k++;
		}}
		
		if(index >= 0){
			Item item = inventory.get(index);
			if(button == 1){
				item.use();
			}
		}
		
	}
	
}
