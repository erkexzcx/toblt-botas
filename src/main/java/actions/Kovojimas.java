package actions;

import actions.exceptions.ResultFailException;
import core.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Kovojimas {

	public static final String PRIESAS_ZIURKE = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=0&psl=0";

	private Document doc;
	private Bot bot;
	private final String baseUrl;

	public Kovojimas(Bot bot, String enemy) {
		baseUrl = bot.insertCredentials(PRIESAS_ZIURKE) + enemy;
	}

	public void perform() throws ResultFailException {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		doc = bot.navigator().navigate(actionUrl(), Navigator.NAVIGATION_TYPE_ACTION);
		
		if(isSuccessful()){
			return;
		}
		
		throw new ResultFailException();

	}

	private boolean isSuccessful() {
		return doc.html().contains("gynėjui lieka 0 gyvybės.");
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
