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
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					bot.shop().sell(itemToSell);
					break;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(grybavimas);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(grybavimas);
					break;
				default:
					resFailure(grybavimas);
					break;
			}

		}
	}

}
