package activityTemplates;

import core.Navigator;
import core.Player;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import subroutines.NewPMRoutine;

public abstract class ActivityBase extends Thread implements Activity {

	protected final Player player;
	protected Document doc;

	protected boolean stopFlag = false;

	public ActivityBase(Player player) {
		this.player = player;
	}

	protected void checkNewPm() {
		boolean newPm = doc.selectFirst("a[href$=\"&id=pm\"]:contains(Yra naujų PM)") != null;
		if (newPm) {
			new NewPMRoutine(player).perform();
		}
	}

	@Override
	public void stopThread() {
		stopFlag = true;
	}

	@Override
	public void startThread() {
		this.start();
	}

	@Override
	public void run() {
		startActivity();
	}

	protected abstract void startActivity();

	protected void sell(String sellUrl) {

		// First we go to that URL:
		doc = player.navigator().navigate(sellUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// Find POST request details:
		Element el = doc.selectFirst("input[name=\"kiekis\"][type=\"hidden\"]");
		if (el == null) {
			player.sendMessage("Unable to find max amount of items to sell. Fix your code!");
			return;
		}
		String formUrl = el.parent().attr("abs:action");
		String amount = el.attr("value");

		// Perform POST request:
		doc = player.navigator().postRequest(
				formUrl,
				new String[][]{
					{"kiekis", amount},
					{"null", "Parduoti"}
				}
		);

	}

}
