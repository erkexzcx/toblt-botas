package activityTemplates;

import actions.*;
import core.*;

public class KirtimasCraftingasActivity extends ActivityBase {

	private final Kirtimas kirtimas;
	private final Crafting crafting;
	private final Item itemToSell;

	public KirtimasCraftingasActivity(Bot bot, Kirtimas kirtimas, Crafting crafting, Item itemToSell) {
		super(bot);
		this.kirtimas = kirtimas;
		this.crafting = crafting;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (kirtimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					craftItems();
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

	private void craftItems() {
		while (!stopFlag) {

			switch (crafting.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_OUT_OF_MATERIALS:
					bot.shop().sell(itemToSell);
					return;
				case Action.RES_OTHER_LEVELS_TOO_LOW:
					resOtherLevelsTooLow(crafting);
					break;
				case Action.RES_LEVEL_TOO_LOW:
					resLevelTooLow(crafting);
					break;
				default:
					resFailure(crafting);
					break;
			}

		}
	}

}
