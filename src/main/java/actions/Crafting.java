package actions;

import core.*;

public class Crafting extends ActionBase {

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NOT_ENOUGH_RESOURCES = 1;
	public static final int RESULT_LEVEL_TOO_LOW = 2;

	private static final String LANKAS_BASE_URL = "http://tob.lt/dirbtuves.php?{CREDENTIALS}&id=gaminu0&ka=";

	public Crafting(Bot bot, Item lankas) {
		super(
				bot,
				bot.insertCredentials(LANKAS_BASE_URL + lankas.getId())
		);
	}

	public int perform() {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		if (doc.html().contains("Neužtenka žaliavų!")) {
			return RESULT_NOT_ENOUGH_RESOURCES;
		}

		String nextUrl = getActionUrl();
		doc = bot.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

		if (doc.html().contains("Jūsų crafting lygis per žemas.")) {
			return RESULT_LEVEL_TOO_LOW;
		}

		// Not a foolproof, but just return SUCCESS for now:
		return RESULT_SUCCESS;

	}

}
