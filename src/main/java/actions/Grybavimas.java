package actions;

import core.*;

public class Grybavimas extends ActionBase {

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_INVENTORY_FULL = 1;
	public static final int RESULT_LEVEL_TOO_LOW = 2;

	private static final String GRYBAUTI_BASE_URL = "http://tob.lt/miskas.php?{CREDENTIALS}&id=renkugrybus0&ka=";;

	public Grybavimas(Bot bot, Item grybas) {
		super(
				bot,
				bot.insertCredentials(GRYBAUTI_BASE_URL + grybas.getId())
		);
	}

	public int perform() {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		String nextUrl = getActionUrl();
		doc = bot.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

		if (doc.html().contains("Jūsų grybavimo lygis per žemas.")) {
			return RESULT_LEVEL_TOO_LOW;
		}
		if (doc.html().contains("Jūsų inventorius jau pilnas!")) {
			return RESULT_INVENTORY_FULL;
		}

		// Not a foolproof, but just return SUCCESS for now:
		return RESULT_SUCCESS;

	}

}
