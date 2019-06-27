package actions;

import core.*;

public class Kovojimas extends ActionBase {

	public static final String PRIESAS_ZIURKE = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=0&psl=0";

	public Kovojimas(Player player, String priesas) {
		super(
				player,
				player.insertCredentials(priesas)
		);
	}

	public void perform() {

		doc = player.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		String nextUrl = getActionUrl();
		doc = player.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

	}

}
