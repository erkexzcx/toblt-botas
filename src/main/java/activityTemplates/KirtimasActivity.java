package activityTemplates;

import actions.*;
import core.*;

public class KirtimasActivity extends ActivityBase {

	private final Kirtimas kirtimas;
	private final Item itemToSell;

	public KirtimasActivity(Bot bot, Kirtimas kirtimas, Item itemToSell) {
		super(bot);
		this.kirtimas = kirtimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kirtimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					bot.shop().sell(itemToSell);
					break;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(kirtimas);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(kirtimas);
					break;
				case Action.RES_MISSING_TOOL:
					resMissingTool(kirtimas);
					break;
				default:
					resFailure(kirtimas);
					break;
			}

		}
	}

}
