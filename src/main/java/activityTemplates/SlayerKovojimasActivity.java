package activityTemplates;

import actions.Kovojimas;
import actions.Slayer;
import actions.exceptions.*;
import core.Bot;

public class SlayerKovojimasActivity extends ActivityBase {

	private final Kovojimas kovojimas;
	private final Slayer slayer;

	public SlayerKovojimasActivity(Bot bot, Slayer slayer, Kovojimas kovojimas) {
		super(bot);
		this.kovojimas = kovojimas;
		this.slayer = slayer;
	}

	@Override
	protected void startActivity() {

		int remaining = slayer.enemiesLeft();
		if (remaining == 0) {
			try {
				slayer.startQuest();
			} catch (SlayerAlreadyInProgressException ex) {
				bot.stopActivity("Slayer quest is already in progress, but we attempted to start it. Fix code!");
			} catch (LevelTooLowException ex) {
				bot.stopActivity(slayer.getClass().getName() + " level is too low!");
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + slayer.getClass().getName());
			}
			remaining = slayer.enemiesLeft();
		}

		while (!stopFlag) {

			try {
				kovojimas.perform();
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + kovojimas.getClass().getName());
			}

			if (--remaining == 0) {
				try {
					slayer.startQuest();
				} catch (SlayerAlreadyInProgressException ex) {
					bot.stopActivity("Slayer quest is already in progress, but we attempted to start it. Fix code!");
				} catch (LevelTooLowException ex) {
					bot.stopActivity(slayer.getClass().getName() + " level is too low!");
				} catch (ResultFailException ex) {
					bot.stopActivity("Unable to confirm successful action: " + slayer.getClass().getName());
				}
				remaining = slayer.enemiesLeft();
			}
		}

	}

}
