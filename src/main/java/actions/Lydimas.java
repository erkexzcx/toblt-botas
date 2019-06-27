package actions;

import core.*;

public class Lydimas extends ActionBase {

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_NOT_ENOUGH_RESOURCES = 1;
	public static final int RESULT_LEVEL_TOO_LOW = 2;

	public static final String LYDITI_BASE_URL = "http://tob.lt/kasimas_kalve.php?{CREDENTIALS}&id=lydau0&ka=";

	public Lydimas(Player player, Item ruda) {
		super(
				player,
				player.insertCredentials(LYDITI_BASE_URL + ruda.getId())
		);
	}

	public int perform() {

		doc = player.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		if (doc.html().contains("Nepakanka žaliavų!")) {
			return RESULT_NOT_ENOUGH_RESOURCES;
		}

		String nextUrl = getActionUrl();
		doc = player.navigator().navigate(nextUrl, Navigator.NAVIGATION_TYPE_ACTION);

		if (doc.html().contains("Kalvininkavimo lygis per žemas!")) {
			return RESULT_LEVEL_TOO_LOW;
		}

		// Not a foolproof, but just return SUCCESS for now:
		return RESULT_SUCCESS;

	}

}
