package activityTemplates;

import actions.*;
import actions.exceptions.InventoryFullException;
import actions.exceptions.LevelTooLowException;
import actions.exceptions.MissingToolException;
import actions.exceptions.OtherLevelsTooLowException;
import actions.exceptions.ResultFailException;
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
			
			try {
				kasimas.perform();
			} catch (InventoryFullException ex) {
				bot.shop().sellEverythingByCategory("neapdirbtas brangakmenis");
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
