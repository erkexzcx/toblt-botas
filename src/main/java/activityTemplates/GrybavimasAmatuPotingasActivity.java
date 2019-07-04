package activityTemplates;

import actions.*;
import actions.exceptions.*;
import core.*;

public class GrybavimasAmatuPotingasActivity extends ActivityBase {

	private final Grybavimas grybavimas;
	private final AmatuPotingas amatuPotingas;
	private final Item itemToSell;

	public GrybavimasAmatuPotingasActivity(Bot bot, Grybavimas grybavimas, AmatuPotingas amatuPotingas, Item itemToSell) {
		super(bot);
		this.grybavimas = grybavimas;
		this.itemToSell = itemToSell;
		this.amatuPotingas = amatuPotingas;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {

			try {
				grybavimas.perform();
			} catch (InventoryFullException ex) {
				makePotions();
			} catch (LevelTooLowException ex) {
				bot.stopActivity(this.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + this.getClass().getName());
			}

		}
	}

	private void makePotions() {
		while (!stopFlag) {

			try {
				amatuPotingas.perform();
			} catch (LevelTooLowException ex) {
				bot.stopActivity(amatuPotingas.getClass().getName() + " level is too low!");
			} catch (OtherLevelsTooLowException ex) {
				// TODO
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + amatuPotingas.getClass().getName());
			} catch (NotEnoughMaterialsException ex) {
				bot.shop().sell(itemToSell);
				break;
			}

		}
	}

}
