package activityTemplates;

import actions.Kovojimas;
import core.Bot;

public class KovojimasActivity extends ActivityBase {

	private final Kovojimas kovojimas;

	public KovojimasActivity(Bot bot, Kovojimas kovojimas) {
		super(bot);
		this.kovojimas = kovojimas;
	}

	@Override
	protected void startActivity() {

		while (!stopFlag) {
			kovojimas.perform();
		}

	}

}
