package activityTemplates;

import misc.Shop;
import actions.*;
import core.*;

public class KirtimasActivity extends ActivityBase {

	private final Kirtimas kirtimas;
	private final Item itemToSell;
	private final Shop shop;

	public KirtimasActivity(Player player, Kirtimas kirtimas, Item itemToSell) {
		super(player);
		this.kirtimas = kirtimas;
		this.itemToSell = itemToSell;

		shop = new Shop(player);
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kirtimas.perform()) {
				case Kirtimas.RESULT_NO_AXE:
					player.sendMessage("I don't have required axe... :(");
					stopFlag = true;
					break;
				case Kirtimas.RESULT_LEVEL_TOO_LOW:
					player.sendMessage("My woodcutting level is too low... :(");
					stopFlag = true;
					break;
				case Kirtimas.RESULT_INVENTORY_FULL:
					shop.sell(itemToSell);
					break;
			}

		}
	}

}
