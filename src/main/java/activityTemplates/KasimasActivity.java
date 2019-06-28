package activityTemplates;

import misc.Shop;
import actions.*;
import core.*;

public class KasimasActivity extends ActivityBase {

	private final Kasimas kasimas;
	private final Item itemToSell;
	private final Shop shop;

	public KasimasActivity(Player player, Kasimas kasimas, Item itemToSell) {
		super(player);
		this.kasimas = kasimas;
		this.itemToSell = itemToSell;

		shop = new Shop(player);
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kasimas.perform()) {
				case Kasimas.RESULT_NO_PICKAXE:
					player.sendMessage("Don't have required pickaxe... :(");
					stopFlag = true;
					break;
				case Kasimas.RESULT_LEVEL_TOO_LOW:
					player.sendMessage("Mining level is too low... :(");
					stopFlag = true;
					break;
				case Kasimas.RESULT_INVENTORY_FULL:
					shop.sell(itemToSell);
					break;
			}

		}
	}

}
