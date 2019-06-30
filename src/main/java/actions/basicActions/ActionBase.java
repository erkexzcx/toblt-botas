package actions.basicActions;

import core.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class ActionBase {

	protected Document doc;
	protected Bot bot;
	protected final String baseUrl;

	public ActionBase(Bot bot, Item item) {
		this.bot = bot;
		this.baseUrl = bot.insertCredentials(getBaseUrl()) + item.getId();
	}

	protected String actionUrl() {
		Element el = doc.selectFirst("a[href*=\"&kd=\"]");
		if (el == null) {
			bot.stopActivity("Nepavyko rasti veiksmo nuorodos! Klase: " + this.getClass().getName());
			return null;
		}
		return el.attr("abs:href");
	}
	
	public abstract boolean isSuccessful();
	public abstract String getBaseUrl();
}
