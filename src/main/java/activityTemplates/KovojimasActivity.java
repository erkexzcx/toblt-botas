package activityTemplates;

import actions.Kovojimas;
import core.Player;

public class KovojimasActivity extends ActivityBase {

	private final Kovojimas kovojimas;

	public KovojimasActivity(Player player, Kovojimas kovojimas) {
		super(player);
		this.kovojimas = kovojimas;
	}

	@Override
	protected void startActivity() {

		while (!stopFlag) {
			kovojimas.perform();
		}

	}

}
