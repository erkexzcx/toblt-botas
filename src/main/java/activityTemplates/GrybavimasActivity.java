package activityTemplates;

import actions.*;
import actions.exceptions.*;
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

			try {
				grybavimas.perform();
			} catch (InventoryFullException ex) {
				bot.shop().sell(itemToSell);
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + this.getClass().getName());
			}

		}
	}

}
