package activityTemplates;

import misc.Shop;
import actions.*;
import core.*;

public class KirtimasCraftingasActivity extends ActivityBase {

	private final Kirtimas kirtimas;
	private final Crafting crafting;
	private final Item itemToSell;
	private final Shop shop;

	public KirtimasCraftingasActivity(Bot bot, Kirtimas kirtimas, Crafting crafting, Item itemToSell) {
		super(bot);
		this.kirtimas = kirtimas;
		this.crafting = crafting;
		this.itemToSell = itemToSell;

		shop = new Shop(bot);
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kirtimas.perform()) {
				case Kirtimas.RESULT_NO_AXE:
					bot.sendMessage("I don't have required axe... :(");
					stopFlag = true;
					break;
				case Kirtimas.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("My woodcutting level is too low... :(");
					stopFlag = true;
					break;
				case Kirtimas.RESULT_INVENTORY_FULL:
					craftItems();
					break;
			}

		}
	}

	private void craftItems() {
		while (!stopFlag) {

			switch (crafting.perform()) {
				case Crafting.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("My crafting level is too low... :(");
					stopFlag = true;
					break;
				case Crafting.RESULT_NOT_ENOUGH_RESOURCES:
					shop.sell(itemToSell);
					return;
			}

		}
	}

}
