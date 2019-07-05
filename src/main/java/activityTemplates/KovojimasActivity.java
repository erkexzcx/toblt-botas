package activityTemplates;

import actions.*;
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

			int res = kovojimas.perform();
			switch (res) {
				case Action.RES_SUCCESS:
					break;
				default:
					resFailure(kovojimas);
					break;
			}

		}
	}

}
