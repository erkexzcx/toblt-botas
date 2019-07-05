package activityTemplates;

import actions.*;
import core.*;

public class KasimasLydimasKaldinimasActivity extends ActivityBase {

	private final Kasimas kasimas;
	private final Lydimas lydimas;
	private final Kaldinimas kaldinimas;
	private final Item itemToSell;

	public KasimasLydimasKaldinimasActivity(Bot bot, Kasimas kasimas, Lydimas lydimas, Kaldinimas kaldinimas, Item itemToSell) {
		super(bot);
		this.kasimas = kasimas;
		this.lydimas = lydimas;
		this.kaldinimas = kaldinimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kasimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					meltItems();
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

	private void meltItems() {
		while (!stopFlag) {

			switch (lydimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_OUT_OF_MATERIALS:
					kaldintiItems();
					return;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(lydimas);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(lydimas);
					break;
				default:
					resFailure(lydimas);
					break;
			}

		}
	}

	private void kaldintiItems() {
		while (!stopFlag) {

			switch (kaldinimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_OUT_OF_MATERIALS:
					bot.shop().sellEverythingByCategory("neapdirbtas brangakmenis");
					bot.shop().sell(itemToSell);
					return;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(kaldinimas);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(kaldinimas);
					break;
				default:
					resFailure(kaldinimas);
					break;
			}

		}
	}

}
