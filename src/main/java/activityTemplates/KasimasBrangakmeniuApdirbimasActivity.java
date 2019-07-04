package activityTemplates;

import actions.*;
import actions.exceptions.*;
import core.*;

public class KasimasBrangakmeniuApdirbimasActivity extends ActivityBase {

	private final Kasimas kasimas;
	private final Item itemToSell;
	private final BrangakmeniuApdirbimas[] brangakmeniuApdirbimas;

	public KasimasBrangakmeniuApdirbimasActivity(Bot bot, Kasimas kasimas, BrangakmeniuApdirbimas[] brangakmeniuApdirbimas, Item itemToSell) {
		super(bot);
		this.kasimas = kasimas;
		this.brangakmeniuApdirbimas = brangakmeniuApdirbimas;
		this.itemToSell = itemToSell;
	}

	@Override
	protected void startActivity() {
		while (!stopFlag) {
			
			try {
				kasimas.perform();
			} catch (InventoryFullException ex) {
				bot.shop().sell(itemToSell);
				processGems();
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

	private void processGems() {
		
		for (BrangakmeniuApdirbimas b : brangakmeniuApdirbimas){
			while(!stopFlag){
				
				try {
					b.perform();
				} catch (NotEnoughMaterialsException ex) {
					String tmp = b.getItem().getId().replace("NB", "AB");
					Item itm = bot.database().getItemById(tmp);
					bot.shop().sell(itm);
					break; // Exit while loop, but stay in for loop
				} catch (LevelTooLowException ex) {
					bot.stopActivity(b.getClass().getName() + " level is too low!");
				} catch (OtherLevelsTooLowException ex) {
					// TODO
				} catch (ResultFailException ex) {
					bot.stopActivity("Unable to confirm successful action: " + b.getClass().getName());
				}
				
			}
			
		}
		
	}

}
