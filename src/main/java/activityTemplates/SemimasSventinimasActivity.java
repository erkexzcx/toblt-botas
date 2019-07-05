package activityTemplates;

import actions.*;
import core.*;

public class SemimasSventinimasActivity extends ActivityBase {

	private final Semimas semimas;
	private final Sventinimas sventinimas;
	private final Item itemToSell;

	public SemimasSventinimasActivity(Bot bot, Semimas semimas, Sventinimas sventinimas) {
		super(bot);
		this.semimas = semimas;
		this.sventinimas = sventinimas;

		this.itemToSell = bot.database().getItemById("VA2");
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			switch (semimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_INVENTORY_FULL:
					holyWater();
					break;
				default:
					resFailure(semimas);
					break;
			}

		}
	}

	private void holyWater() {
		while (!stopFlag) {

			switch (sventinimas.perform()) {
				case Action.RES_SUCCESS:
					break;
				case Action.RES_OUT_OF_MATERIALS:
					bot.shop().putIntoPlayerMarket(itemToSell, "80000");
					return;
				default:
					resFailure(sventinimas);
					break;
			}

		}
	}

}
