package activityTemplates;

import actions.Kovojimas;
import actions.Slayer;
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
			slayer.startQuest();
			remaining = slayer.enemiesLeft();
		}

		while (!stopFlag) {
			kovojimas.perform();
			
			if (--remaining == 0) {
				slayer.startQuest(); // "Uzduotis atlikta!"
				slayer.startQuest(); // "Uzduotis priskirta!"
				remaining = slayer.enemiesLeft();
			}
		}

	}

}
