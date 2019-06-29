package actions;

import core.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Slayer extends ActionBase {

	public static final int RESULT_SUCCESS = 0;
	public static final int RESULT_LEVEL_TOO_LOW = 1;
	public static final int RESULT_ALREADY_STARTED = 2;

	public static final String KILL_1_10 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=1";
	public static final String KILL_11_30 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=2";
	public static final String KILL_31_50 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=3";
	public static final String KILL_51_90 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=4";
	public static final String KILL_91_150 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=5";
	public static final String KILL_151_200 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=6";
	public static final String KILL_201_260 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=7";
	public static final String KILL_261_350 = "http://tob.lt/slayer.php?{CREDENTIALS}&id=task&nr=8";

	private static final Pattern EXTRACT_REMAINING_COUNT_PATTERN = Pattern.compile("Dar turite nužudyti:\\s+<b>(\\d+)</b>", Pattern.MULTILINE);

	private final String what;

	public Slayer(Bot bot, String what) {
		super(
				bot,
				bot.insertCredentials("http://tob.lt/slayer.php?{CREDENTIALS}&id=")
		);
		this.what = bot.insertCredentials(what);
	}

	public int startQuest() {

		doc = bot.navigator().navigate(what, Navigator.NAVIGATION_TYPE_REGULAR);

		String html = doc.html();
		if (html.contains("Jums jau išrasyta užduotis, prašome ją įvykdyti arba atšaukti.")) {
			bot.sendMessage("Slayer has already been started! Fix it first.");
			System.exit(1);
		}
		if (html.contains("Jūsų slayer lygis per žemas!")) {
			return RESULT_LEVEL_TOO_LOW;
		}

		return RESULT_SUCCESS;

	}

	private boolean isInProgress() {
		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		return doc.html().contains("Jūs turite užduotį!");
	}

	public int enemiesLeft() {

		// This also updates doc:
		if (!isInProgress()) {
			return 0;
		}

		Matcher m = EXTRACT_REMAINING_COUNT_PATTERN.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			System.exit(1);
		}
		String count = m.group(1);
		return Integer.parseInt(count);
	}

}
