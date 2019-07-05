package activityTemplates;

import actions.*;
import misc.Slayer;
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
			switch (slayer.startQuest()) {
				case Slayer.RES_SUCCESS:
					break;
				case Slayer.RES_ALREADY_IN_PROGRESS:
					bot.stopActivity("Slayer quest is already in progress, but we attempted to start it. Fix your code!");
					break;
				case Slayer.RES_LEVEL_TOO_LOW:
					bot.stopActivity(slayer.getClass().getSimpleName() + " level is too low!");
					break;
				default:
					resFailure(slayer);
					break;
			}
			remaining = slayer.enemiesLeft();
		}

		while (!stopFlag) {

			int res = kovojimas.perform();
			switch (res) {
				case Action.RES_SUCCESS:
					break;
				default:
					resFailure(kovojimas);
					break;
			}

			if (--remaining == 0) {
				switch (slayer.startQuest()) {
					case Slayer.RES_SUCCESS:
						break;
					case Slayer.RES_ALREADY_IN_PROGRESS:
						bot.stopActivity("Slayer quest is already in progress, but we attempted to start it. Fix your code!");
						break;
					case Slayer.RES_LEVEL_TOO_LOW:
						bot.stopActivity(slayer.getClass().getSimpleName() + " level is too low!");
						break;
					default:
						resFailure(slayer);
						break;
				}
				remaining = slayer.enemiesLeft();
			}
		}

	}

}
