package activityTemplates;

import actions.*;
import actions.exceptions.*;
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

			try {
				kirtimas.perform();
			} catch (InventoryFullException ex) {
				craftItems();
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + kirtimas.getClass().getName());
			} catch (MissingToolException ex) {
				bot.stopActivity(this.getClass().getName() + " does not have required tool to perform this action!");
			}

		}
	}

	private void craftItems() {
		while (!stopFlag) {

			try {
				crafting.perform();
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + crafting.getClass().getName());
			}catch (NotEnoughMaterialsException ex) {
				bot.shop().sell(itemToSell);
				break;
			}

		}
	}

}
