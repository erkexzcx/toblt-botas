package actions;

import core.Bot;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class ActionBase {

	public static final String CREDENTIALS_TEMPLATE = "{CREDENTIALS}";

	protected Document doc;
	protected Bot bot;
	protected final String baseUrl;

	public ActionBase(Bot bot, String baseUrl) {
		this.bot = bot;
		this.baseUrl = baseUrl;
	}

	protected String getActionUrl() {
		Element el = doc.selectFirst("a[href*=\"&kd=\"]");
		if (el == null) {
			System.exit(1);
			return null;
		}
		return el.attr("abs:href");
	}
}
