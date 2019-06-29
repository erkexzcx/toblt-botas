package actions;

import core.*;

public class Kovojimas extends ActionBase {

	public static final String PRIESAS_ZIURKE = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=0&psl=0";

	public Kovojimas(Bot bot, String priesas) {
		super(
				bot,
				bot.insertCredentials(priesas)
		);
	}

	public void perform() {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		String nextUrl = getActionUrl();
		doc = bot.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

	}

}
