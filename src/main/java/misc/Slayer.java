package misc;

import core.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;

public class Slayer {

	public static final int RES_SUCCESS = 0;
	public static final int RES_FAILURE = 1;
	public static final int RES_ALREADY_IN_PROGRESS = 2;
	public static final int RES_LEVEL_TOO_LOW = 3;

	public static final String KILL_1_10 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=1";
	public static final String KILL_11_30 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=2";
	public static final String KILL_31_50 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=3";
	public static final String KILL_51_90 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=4";
	public static final String KILL_91_150 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=5";
	public static final String KILL_151_200 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=6";
	public static final String KILL_201_260 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=7";
	public static final String KILL_261_350 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=8";

	private static final Pattern EXTRACT_REMAINING_COUNT_PATTERN = Pattern.compile("Dar turite nužudyti:\\s+<b>(\\d+)</b>", Pattern.MULTILINE);

	private static final String BASE_URL = "http://tob.lt/slayer.php?{CREDENTIALS}&id=";

	private final String baseUrl;
	private final String questUrl;
	private final Bot bot;
	private Document doc;

	public Slayer(Bot bot, String what) {
		this.bot = bot;
		this.baseUrl = bot.insertCredentials(BASE_URL);
		questUrl = baseUrl + what;

	}

	public int startQuest() {

		// Load twice to prevent random messages for the user in the game:
		bot.navigator().navigate(questUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		doc = bot.navigator().navigate(questUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		if (doc.html().contains("Jums sėkmingai paskirta užduotis!")) {
			return RES_SUCCESS;
		}
		if (doc.html().contains("Jums jau išrasyta užduotis, prašome ją įvykdyti arba atšaukti.")) {
			return RES_ALREADY_IN_PROGRESS;
		}
		if (doc.html().contains("Jūsų slayer lygis per žemas!")) {
			return RES_LEVEL_TOO_LOW;
		}
		return RES_FAILURE;

	}

	public int enemiesLeft() {

		// This also updates doc:
		if (!isInProgress()) {
			return 0;
		}

		Matcher m = EXTRACT_REMAINING_COUNT_PATTERN.matcher(doc.html());
		if (!m.find()) {
			bot.stopActivity("Regex in class " + this.getClass().getName() + " doesn't work. Fix it!");
		}
		String count = m.group(1);
		return Integer.parseInt(count);
	}

	private boolean isInProgress() {
		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		return doc.html().contains("Jūs turite užduotį!");
	}

}
