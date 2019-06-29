package core;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import subroutines.*;

public class Navigator {

	public Navigator(Bot bot) {
		this.bot = bot;
	}

	private final Bot bot;

	private Document doc = new Document("");

	public static final int NAVIGATION_TYPE_REGULAR = 0;
	public static final int NAVIGATION_TYPE_ACTION = 1;
	private static final int MINIMUM_WAITING_TIME = 650; // In ms

	/* We will be setting timestamps in these variables. System will need to
	wait until it otherwise we will get "click too fast" in game. */
	private long futureActionTimestamp = 0;
	private long futureNavigationTimestamp = 0;

	/**
	 * Call this function AFTER navigation. It remembers the timestamp when
	 * navigation has been performed, so next step's minimum wait time will be
	 * calculated.
	 */
	private void updateTimestamps() {

		// Get current timestamp in ms:
		long currentTimestamp = System.currentTimeMillis();

		int msToWait = 0;
		// Parse time needed to wait:
		try {
			msToWait = Integer.parseInt(doc.selectFirst("#countdown").html()) * 1000; //website shows as seconds, but we convert to miliseconds.
		} catch (NumberFormatException | NullPointerException e) {
		}

		// Calculate future timestamps to wait for:
		futureActionTimestamp = (msToWait < MINIMUM_WAITING_TIME ? currentTimestamp + MINIMUM_WAITING_TIME : currentTimestamp + msToWait);
		futureNavigationTimestamp = currentTimestamp + MINIMUM_WAITING_TIME;
	}

	/**
	 * Call this function BEFORE navigating. It wait certain amount of time so
	 * navigation won't show "clicked too fast" errors.
	 */
	private void wait(int waitType) {

		// Calculate the amount of MS that we need to wait:
		long sleepTime;
		if (waitType == NAVIGATION_TYPE_ACTION) {
			sleepTime = futureActionTimestamp - System.currentTimeMillis(); // Action navigation.
		} else {
			sleepTime = futureNavigationTimestamp - System.currentTimeMillis(); // Regular navigation.
		}

		// If that amount is equal or less than 0 (miliseconds), then we don't need to wait at all:
		if (sleepTime <= 0) {
			return;
		}

		// And finally sleep for that calculated amount of time:
		try {
			TimeUnit.MILLISECONDS.sleep(sleepTime);
		} catch (InterruptedException ex) {
			Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public Document navigate(String url, int navigationType) {
		wait(navigationType);
		try {
			doc = Jsoup.connect(url).timeout(30 * 1000).userAgent("Mozilla").get();
		} catch (IOException ex) {
			sleepSeconds(30);
			return navigate(url, navigationType);
		}
		updateTimestamps();

		if (checkAccountUnavailable()) {
			bot.sendMessage("Account is not playable!");
			System.exit(1); // TODO
		} else if (checkTooFast()) {
			sleepSeconds(30);
			return navigate(url, navigationType);
		} else if (checkOtherLevelsTooLow()) {
			bot.sendMessage("Bot's other levels are too low to do current action!");
			System.exit(1); // TODO
		} else if (checkAntiBot()) {
			new AntiBotRoutine(doc, bot).perform();
			return navigate(url, navigationType);
		} else if (checkNewPm()) {
			Document tmpdoc = doc.clone();
			new NewPMRoutine(bot).perform();
			doc = tmpdoc;
		}

		return doc;
	}

	public Document postRequest(String url, String params[][]) {
		wait(NAVIGATION_TYPE_REGULAR);
		// And do next URL request :)
		Connection conn = Jsoup
				.connect(url)
				.timeout(30 * 1000)
				.userAgent("Mozilla");

		for (String p[] : params) {
			conn.data(p[0], p[1]);
		}

		try {
			doc = conn.post();
		} catch (IOException ex) {
			sleepSeconds(30);
			return postRequest(url, params);
		}
		updateTimestamps();

		if (checkAntiFlood()) {
			sleepSeconds(5);
			return postRequest(url, params);
		}

		return doc;
	}

	public Document navigateUnsafe(String url) {
		wait(NAVIGATION_TYPE_REGULAR);
		try {
			doc = Jsoup.connect(url).timeout(30 * 1000).userAgent("Mozilla").get();
		} catch (IOException ex) {
			sleepSeconds(30);
			return navigateUnsafe(url);
		}
		updateTimestamps();
		return doc;
	}

	public Document navigateForPm(String url) {
		wait(NAVIGATION_TYPE_REGULAR);
		try {
			doc = Jsoup.connect(url).timeout(30 * 1000).userAgent("Mozilla").get();
		} catch (IOException ex) {
			sleepSeconds(30);
			return navigateUnsafe(url);
		}
		updateTimestamps();

		if (checkAccountUnavailable()) {
			bot.sendMessage("Account is not playable!");
			System.exit(1); // TODO
		} else if (checkTooFast()) {
			sleepSeconds(30);
			return navigateForPm(url);
		} else if (checkOtherLevelsTooLow()) {
			bot.sendMessage("Bot's other levels are too low to do current action!");
			System.exit(1); // TODO
		} else if (checkAntiBot()) {
			new AntiBotRoutine(doc, bot).perform();
			return navigateForPm(url);
		}

		return doc;
	}

	//==========================================================================
	private boolean checkAccountUnavailable() {
		return doc.selectFirst("b:contains(Neteisingas slaptažodis!)") != null
				|| doc.selectFirst("b:contains(Blogi duomenys!)") != null
				|| doc.html().contains("Arba jūsų IP adresas užblokuotas");
	}

	private boolean checkTooFast() {
		return doc.selectFirst(":contains(NUORODAS REIKIA SPAUSTI TIK VIENĄ KARTĄ!)") != null
				|| doc.selectFirst(":contains(Jūs pavargęs, bandykite vėl po keleto sekundžių..)") != null;
	}

	private boolean checkAntiBot() {
		return doc.selectFirst("div.antr:contains(Apsauga nuo botų)") != null;
	}

	protected boolean checkNewPm() {
		return doc.selectFirst("a[href$=\"&id=pm\"]:contains(Yra naujų PM)") != null;
	}

	protected boolean checkAntiFlood() {
		return doc.html().contains("Antiflood! Bandykite už kelių sekundžių.");
	}

	protected boolean checkOtherLevelsTooLow() {
		return doc.html().contains("Jūsų kiti lygiai per žemi!");
	}

	private void sleepSeconds(int s) {
		try {
			TimeUnit.SECONDS.sleep(s);
		} catch (InterruptedException ex1) {
			Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex1);
		}
	}

}
