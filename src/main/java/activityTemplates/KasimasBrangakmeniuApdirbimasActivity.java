package activityTemplates;

import actions.*;
import core.*;

public class KasimasBrangakmeniuApdirbimasActivity extends ActivityBase {

	private final Kasimas kasimas;
	private final Item itemToSell;
	private final BrangakmeniuApdirbimas[] brangakmeniuApdirbimas;

	public KasimasBrangakmeniuApdirbimasActivity(Bot bot, Kasimas kasimas, BrangakmeniuApdirbimas[] brangakmeniuApdirbimas, Item itemToSell) {
		super(bot);
		this.kasimas = kasimas;
		this.brangakmeniuApdirbimas = brangakmeniuApdirbimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kasimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					bot.shop().sell(itemToSell);
					processGems();
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

	private void processGems() {

		for (BrangakmeniuApdirbimas b : brangakmeniuApdirbimas) {
			while (!stopFlag) {

				switch (b.perform()) {
					case Action.RES_SUCCESS:
						break;
					case Action.RES_OUT_OF_MATERIALS:
						bot.shop().sell(itemToSell);
						return;
					case Action.RES_OTHER_LEVELS_TOO_LOW:
						resOtherLevelsTooLow(b);
						break;
					case Action.RES_LEVEL_TOO_LOW:
						resLevelTooLow(b);
						break;
					default:
						resFailure(b);
						break;
				}

			}

		}

	}

}
