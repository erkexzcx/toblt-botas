package activityTemplates;

import actions.*;
import core.*;

public class GrybavimasAmatuPotingasActivity extends ActivityBase {

	private final Grybavimas grybavimas;
	private final AmatuPotingas amatuPotingas;
	private final Item itemToSell;

	public GrybavimasAmatuPotingasActivity(Bot bot, Grybavimas grybavimas, AmatuPotingas amatuPotingas, Item itemToSell) {
		super(bot);
		this.grybavimas = grybavimas;
		this.itemToSell = itemToSell;
		this.amatuPotingas = amatuPotingas;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			int res = grybavimas.perform();
			switch (res) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					makePotions();
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

	private void makePotions() {
		while (!stopFlag) {
			
			switch (amatuPotingas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_OUT_OF_MATERIALS:
					bot.shop().sell(itemToSell);
					return;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(amatuPotingas);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(amatuPotingas);
					break;
				default:
					resFailure(amatuPotingas);
					break;
			}

		}
	}

}
