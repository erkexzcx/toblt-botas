package actions;

import core.Player;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class ActionBase {

	public static final String CREDENTIALS_TEMPLATE = "{CREDENTIALS}";

	protected Document doc;
	protected Player player;
	protected final String baseUrl;

	public ActionBase(Player player, String baseUrl) {
		this.player = player;
		this.baseUrl = baseUrl;
	}

	protected String getActionUrl() {
		Element el = doc.selectFirst("a[href*=\"&kd=\"]");
		if (el == null) {
			System.out.println("---------------------------------------------");
			System.out.println(doc.html());
			System.out.println("---------------------------------------------");
			player.sendMessage("Checkmate!");
			System.exit(1);
			return null;
		}
		return el.attr("abs:href");
	}
}
