package actions;

import core.*;

public class Kasimas extends ActionBase {

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NO_PICKAXE = 1;
	public static final int RESULT_INVENTORY_FULL = 2;
	public static final int RESULT_LEVEL_TOO_LOW = 3;

	public static final String KASTI_BASE_URL = "http://tob.lt/kasimas_kalve.php?{CREDENTIALS}&id=mininu0&ka=";

	public Kasimas(Bot bot, Item ruda) {
		super(
				bot,
				bot.insertCredentials(KASTI_BASE_URL + ruda.getId())
		);
	}

	public int perform() {

		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		String nextUrl = getActionUrl();
		doc = bot.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

		if (doc.html().contains("Neturite reikalingo kirtiklio!")) {
			return RESULT_NO_PICKAXE;
		}
		if (doc.html().contains("Jūsų kasimo lygis per žemas")) {
			return RESULT_LEVEL_TOO_LOW;
		}
		if (doc.html().contains("Jūsų inventorius jau pilnas!")) {
			return RESULT_INVENTORY_FULL;
		}

		// Not a foolproof, but just return SUCCESS for now:
		return RESULT_SUCCESS;

	}

}
