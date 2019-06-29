package actions;

import core.*;

public class Kirtimas extends ActionBase {

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NO_AXE = 1;
	public static final int RESULT_INVENTORY_FULL = 2;
	public static final int RESULT_LEVEL_TOO_LOW = 3;

	private static final String KIRSTI_BASE_URL = "http://tob.lt/miskas.php?{CREDENTIALS}&id=kertu&ka=";

	public Kirtimas(Bot bot, Item medis) {
		super(
				bot,
				bot.insertCredentials(KIRSTI_BASE_URL + medis.getId())
		);
	}

	public int perform() {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		String nextUrl = getActionUrl();
		doc = bot.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

		if (doc.html().contains("Neturite reikiamo kirvio.")) {
			return RESULT_NO_AXE;
		}
		if (doc.html().contains("Jūsų medkirčio lygis per žemas.")) {
			return RESULT_LEVEL_TOO_LOW;
		}
		if (doc.html().contains("Jūsų inventorius jau pilnas!")) {
			return RESULT_INVENTORY_FULL;
		}

		// Not a foolproof, but just return SUCCESS for now:
		return RESULT_SUCCESS;

	}

}
