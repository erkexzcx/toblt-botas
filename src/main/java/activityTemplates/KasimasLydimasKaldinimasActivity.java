package activityTemplates;

import misc.Shop;
import actions.*;
import core.*;

public class KasimasLydimasKaldinimasActivity extends ActivityBase {
	
	private final Kasimas kasimas;
	private final Lydimas lydimas;
	private final Kaldinimas kaldinimas;
	private final Item itemToSell;
	private final Shop shop;
	
	public KasimasLydimasKaldinimasActivity(Bot bot, Kasimas kasimas, Lydimas lydimas, Kaldinimas kaldinimas, Item itemToSell) {
		super(bot);
		this.kasimas = kasimas;
		this.lydimas = lydimas;
		this.kaldinimas = kaldinimas;
		this.itemToSell = itemToSell;
		
		shop = new Shop(bot);
	}
	
	@Override
	protected void startActivity() {
		while (!stopFlag) {
			
			switch (kasimas.perform()) {
				case Kasimas.RESULT_NO_PICKAXE:
					bot.sendMessage("Don't have required pickaxe... :(");
					stopFlag = true;
					break;
				case Kasimas.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("Mining level is too low... :(");
					stopFlag = true;
					break;
				case Kasimas.RESULT_INVENTORY_FULL:
					bot.shop().sellEverythingByCategory("neapdirbtas brangakmenis");
					meltItems();
					break;
			}
			
		}
	}
	
	private void meltItems() {
		while (!stopFlag) {
			
			switch (lydimas.perform()) {
				case Lydimas.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("Kalvininkavimas level is too low... :(");
					stopFlag = true;
					break;
				case Lydimas.RESULT_NOT_ENOUGH_RESOURCES:
					kaldintiItems();
					return;
			}
			
		}
	}
	
	private void kaldintiItems() {
		while (!stopFlag) {
			
			switch (kaldinimas.perform()) {
				case Kaldinimas.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("Kalvininkavimas level is too low... :(");
					stopFlag = true;
					break;
				case Kaldinimas.RESULT_NOT_ENOUGH_RESOURCES:
					shop.sell(itemToSell);
					return;
			}
			
		}
	}
	
}
