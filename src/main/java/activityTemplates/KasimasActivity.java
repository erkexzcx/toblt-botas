package activityTemplates;

import actions.*;
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
				case Kasimas.RESULT_NO_PICKAXE:
					bot.sendMessage("Don't have required pickaxe... :(");
					stopFlag = true;
					break;
				case Kasimas.RESULT_LEVEL_TOO_LOW:
					bot.sendMessage("Mining level is too low... :(");
					stopFlag = true;
					break;
				case Kasimas.RESULT_INVENTORY_FULL:
					bot.shop().sellEverythingByCategory("neapdirbtas brangakmenis");
					bot.shop().sell(itemToSell);
					break;
			}

		}
	}

}
