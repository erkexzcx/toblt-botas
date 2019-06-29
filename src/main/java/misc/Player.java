package misc;

import core.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;

public class Player {

	private static final String ABOUT_PLAYER_URL = "http://tob.lt/zaisti.php?{CREDENTIALS}&id=apie&ka=";

	private final String aboutPlayerUrl;
	private final Bot bot;
	private Document doc;

	private int slayer = 0;
	private int kasimas = 0;
	private int kalvininkavimas = 0;
	private int zvejyba = 0;
	private int medkirtyste = 0;
	private int crafting = 0;
	private int medziokle = 0;
	private int kepimas = 0;
	private int juvelyrika = 0;
	private int sodininkyste = 0;
	private int potingas = 0;
	private int grybavimas = 0;
	private int uogavimas = 0;

	private static final Pattern EXTRACT_SLAYER = Pattern.compile("<b>Slayer</b>: (\\d+)");
	private static final Pattern EXTRACT_KASIMAS = Pattern.compile("<b>Kasimas</b>: (\\d+)");
	private static final Pattern EXTRACT_KALVININKAVIMAS = Pattern.compile("<b>Kalvininkavimas</b>: (\\d+)");
	private static final Pattern EXTRACT_ZVEJYBA = Pattern.compile("<b>Žvejyba</b>: (\\d+)");
	private static final Pattern EXTRACT_MEDKIRTYSTE = Pattern.compile("<b>Medkirtystė</b>: (\\d+)");
	private static final Pattern EXTRACT_CRAFTING = Pattern.compile("<b>Crafting</b>: (\\d+)");
	private static final Pattern EXTRACT_MEDZIOKLE = Pattern.compile("<b>Medžioklė</b>: (\\d+)");
	private static final Pattern EXTRACT_KEPIMAS = Pattern.compile("<b>Kepimas</b>: (\\d+)");
	private static final Pattern EXTRACT_JUVELYRIKA = Pattern.compile("<b>Juvelyrika</b>: (\\d+)");
	private static final Pattern EXTRACT_SODININKYSTE = Pattern.compile("<b>Sodininkystė</b>: (\\d+)");
	private static final Pattern EXTRACT_POTINGAS = Pattern.compile("<b>Potingas</b>: (\\d+)");
	private static final Pattern EXTRACT_GRYBAVIMAS = Pattern.compile("<b>Grybavimas</b>: (\\d+)");
	private static final Pattern EXTRACT_UOGAVIMAS = Pattern.compile("<b>Uogavimas</b>: (\\d+)");

	public Player(Bot bot) {
		this.bot = bot;
		aboutPlayerUrl = bot.insertCredentials(ABOUT_PLAYER_URL) + bot;
	}

	public Player refresh() {

		// Go to inventory:
		doc = bot.navigator().navigate(aboutPlayerUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// Parse data:
		Matcher m;
		
		m = EXTRACT_SLAYER.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		slayer = Integer.parseInt(m.group(1));
		
		m = EXTRACT_KASIMAS.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		kasimas = Integer.parseInt(m.group(1));
		
		m = EXTRACT_KALVININKAVIMAS.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		kalvininkavimas = Integer.parseInt(m.group(1));
		
		m = EXTRACT_ZVEJYBA.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		zvejyba = Integer.parseInt(m.group(1));
		
		m = EXTRACT_MEDKIRTYSTE.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		medkirtyste = Integer.parseInt(m.group(1));
		
		m = EXTRACT_CRAFTING.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		crafting = Integer.parseInt(m.group(1));
		
		m = EXTRACT_MEDZIOKLE.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		medziokle = Integer.parseInt(m.group(1));
		
		m = EXTRACT_KEPIMAS.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		kepimas = Integer.parseInt(m.group(1));
		
		m = EXTRACT_JUVELYRIKA.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		juvelyrika = Integer.parseInt(m.group(1));
		
		m = EXTRACT_SODININKYSTE.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		sodininkyste = Integer.parseInt(m.group(1));
		
		m = EXTRACT_POTINGAS.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		potingas = Integer.parseInt(m.group(1));
		
		m = EXTRACT_GRYBAVIMAS.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		grybavimas = Integer.parseInt(m.group(1));
		
		m = EXTRACT_UOGAVIMAS.matcher(doc.html());
		if (!m.find()) {
			bot.sendMessage("Regex doesn't work... fix your code!");
			return null;
		}
		uogavimas = Integer.parseInt(m.group(1));

		return this;
	}

	public int getSlayerLevel() { return slayer; }
	public int getKasimasLevel() { return kasimas; }
	public int getKalvininkavimasLevel() { return kalvininkavimas; }
	public int getZvejybaLevel() { return zvejyba; }
	public int getMedkirtysteLevel() { return medkirtyste;	}
	public int getCraftingLevel() {	return crafting; }
	public int getMedziokleLevel() { return medziokle; }
	public int getKepimasLevel() { return kepimas; }	
	public int getJuvelyrikaLevel() { return juvelyrika; }
	public int getSodininkysteLevel() {	return sodininkyste; }
	public int getPotingasLevel() { return potingas; }
	public int getGrybavimasLevel() { return grybavimas; }
	public int getUogavimasLevel() { return uogavimas; }

}
