package actions;

import actions.exceptions.*;
import core.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;

public class Slayer {

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

	private final String questUrl;
	private final Bot bot;
	private Document doc;

	public Slayer(Bot bot, String what) {
		this.bot = bot;
		questUrl = bot.insertCredentials(BASE_URL) + what;
		
	}

	public void startQuest() throws SlayerAlreadyInProgressException, LevelTooLowException, ResultFailException {
		
		// Load twice to prevent random messages for the user in the game:
		bot.navigator().navigate(questUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		doc = bot.navigator().navigate(questUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		if (doc.html().contains("Jums sėkmingai paskirta užduotis!")) {
			return;
		}
		
		if (doc.html().contains("Jums jau išrasyta užduotis, prašome ją įvykdyti arba atšaukti.")) {
			throw new SlayerAlreadyInProgressException();
		}
		if (doc.html().contains("Jūsų slayer lygis per žemas!")) {
			throw new LevelTooLowException();
		}
		throw new ResultFailException();

	}

	private boolean isInProgress() {
		doc = bot.navigator().navigate(questUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		return doc.html().contains("Jums jau išrasyta užduotis");
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

}
