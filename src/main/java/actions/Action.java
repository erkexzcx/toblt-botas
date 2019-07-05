package actions;

import core.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public abstract class Action {
	
	public static final int RES_SUCCESS = 0;
	public static final int RES_FAILURE = 1;
	public static final int RES_INVENTORY_FULL = 2;
	public static final int RES_MISSING_TOOL = 3;
	public static final int RES_LEVEL_TOO_LOW = 4;
	public static final int RES_OTHER_LEVELS_TOO_LOW = 5;
	public static final int RES_OUT_OF_MATERIALS = 6;

	protected Document doc;
	protected Bot bot;
	protected final String baseUrl;
	protected int res;

	public Action(Bot bot, String baseUrl) {
		this.bot = bot;
		this.baseUrl = bot.insertCredentials(baseUrl);
	}
	
	protected String getActionUrl() {
		Element el = doc.selectFirst("a[href*=\"&kd=\"]");
		if (el == null) {
			bot.stopActivity("Nepavyko rasti veiksmo nuorodos! Klase: " + this.getClass().getName());
			return null;
		}
		return el.attr("abs:href");
	}

	public int perform() {
		
		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		
		res = preChecks();
		if (res != RES_SUCCESS){
			return res;
		}
		
		doc = bot.navigator().navigate(getActionUrl(), Navigator.NAVIGATION_TYPE_ACTION);
		
		res = postChecks();
		if (res != RES_SUCCESS){
			return res;
		}
		
		return (isSuccessful() ? RES_SUCCESS : RES_FAILURE);
		
	}
	
	protected abstract int preChecks();
	
	protected abstract int postChecks();

	protected abstract boolean isSuccessful();

}
