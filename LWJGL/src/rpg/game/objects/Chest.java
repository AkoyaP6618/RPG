package rpg.game.objects;

import java.util.ArrayList;

import rpg.game.Game;
import rpg.game.armor.boots.SimpleBoots;
import rpg.game.items.Item;
import rpg.game.items.Item.ItemObject;
import rpg.game.items.Item.Tier;
import rpg.game.player.Inventory;


public class Chest extends GameObject{

	private ArrayList<Item> loot = new ArrayList();
	
	private boolean open = false;
	
	public Chest() {
		super(true,false, 32, 32, "res/objects/chest.png");
		Inventory.add(Item.get(ItemObject.smallManaPotion), loot);
		Inventory.add(Item.get(ItemObject.smallManaPotion), loot);
		Inventory.add(Item.get(ItemObject.smallHealthPotion), loot);
		Inventory.add(Item.get(ItemObject.smallHealthPotion), loot);
		Inventory.add(new SimpleBoots(1, 1, Tier.T2), loot);
	}
	
	public void use(){
		if(open){
			if(!loot.isEmpty()){
				loot();
			}else{
			open = false;
			setTexture("res/objects/chest.png");
			Game.UI.renderLootDialog = false;
			}
		}else{
			open = true;
			setTexture("res/objects/emptyChest.png");
			loot();
		}
	}
	

	public void loot(){
		if(loot.size() > 0){
			Game.UI.showLootDialog(loot);
		}
	}
	
	public boolean isOpen(){
		return open;
	}
	
}
