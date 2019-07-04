package actions;

import actions.exceptions.ResultFailException;
import core.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Kovojimas {

	public static final String PRIESAS_ZIURKE = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=0&psl=0";
	public static final String PRIESAS_DRAKONAS_150 = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=64&psl=7";

	private Document doc;
	private final Bot bot;
	private final String baseUrl;

	public Kovojimas(Bot bot, String enemy) {
		this.bot = bot;
		this.baseUrl = bot.insertCredentials(enemy);
	}

	public void perform() throws ResultFailException {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		doc = bot.navigator().navigate(actionUrl(), Navigator.NAVIGATION_TYPE_ACTION);
		
		if(!isSuccessful()){
			throw new ResultFailException();
		}

	}

	private boolean isSuccessful() {
		return doc.html().contains("Nukovėte");
	}

	private String actionUrl() {
		Element el = doc.selectFirst("a[href*=\"&kd=\"]");
		if (el == null) {
			bot.stopActivity("Nepavyko rasti veiksmo nuorodos! Klase: " + this.getClass().getName());
			return null;
		}
		return el.attr("abs:href");
	}

}
