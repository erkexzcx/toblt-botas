package activityTemplates;

import actions.*;
import actions.Action;
import core.*;

public class KasimasActivity extends ActivityBase {

	private final Kasimas kasimas;
	private final Item itemToSell;

	public KasimasActivity(Bot bot, Kasimas kasimas, Item itemToSell) {
		super(bot);
		this.kasimas = kasimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {
			
			switch (kasimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					bot.shop().sellEverythingByCategory("neapdirbtas brangakmenis");
					bot.shop().sell(itemToSell);
					break;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(kasimas);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(kasimas);
					break;
				case Action.RES_MISSING_TOOL:
					resMissingTool(kasimas);
					break;
				default:
					resFailure(kasimas);
					break;
			}

		}
	}

}
