package activityTemplates;

import actions.*;
import actions.exceptions.*;
import core.*;

public class KasimasLydimasActivity extends ActivityBase {

	private final Kasimas kasimas;
	private final Lydimas lydimas;
	private final Item itemToSell;

	public KasimasLydimasActivity(Bot bot, Kasimas kasimas, Lydimas lydimas, Item itemToSell) {
		super(bot);
		this.kasimas = kasimas;
		this.lydimas = lydimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			try {
				kasimas.perform();
			} catch (InventoryFullException ex) {
				bot.shop().sellEverythingByCategory("neapdirbtas brangakmenis");
				meltItems();
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + kasimas.getClass().getName());
			} catch (MissingToolException ex) {
				bot.stopActivity(this.getClass().getName() + " does not have required tool to perform this action!");
			}

		}
	}

	private void meltItems() {
		while (!stopFlag) {

			try {
				lydimas.perform();
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + lydimas.getClass().getName());
			}catch (NotEnoughMaterialsException ex) {
				bot.shop().sell(itemToSell);
				break;
			}

		}
	}

}
