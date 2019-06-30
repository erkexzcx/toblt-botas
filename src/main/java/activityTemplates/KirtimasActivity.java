package activityTemplates;

import actions.*;
import actions.exceptions.*;
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

			try {
				kirtimas.perform();
			} catch (InventoryFullException ex) {
				bot.shop().sell(itemToSell);
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + this.getClass().getName());
			} catch (MissingToolException ex) {
				bot.stopActivity(this.getClass().getName() + " does not have required tool to perform this action!");
			}

		}
	}

}
