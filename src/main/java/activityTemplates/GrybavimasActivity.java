package activityTemplates;

import actions.*;
import core.*;

public class GrybavimasActivity extends ActivityBase {

	private final Grybavimas grybavimas;
	private final Item itemToSell;

	public GrybavimasActivity(Bot bot, Grybavimas grybavimas, Item itemToSell) {
		super(bot);
		this.grybavimas = grybavimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (grybavimas.perform()) {
				case Grybavimas.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("My woodcutting level is too low... :(");
					stopFlag = true;
					break;
				case Grybavimas.RESULT_INVENTORY_FULL:
					bot.shop().sell(itemToSell);
					break;
			}

		}
	}

}
