package activityTemplates;

import actions.Kovojimas;
import actions.exceptions.ResultFailException;
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
			
			try {
				kovojimas.perform();
			} catch (ResultFailException ex) {
				bot.stopActivity("Unable to confirm successful action: " + kovojimas.getClass().getName());
			}
			
		}
	}

}
