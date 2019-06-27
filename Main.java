package core;

import activityTemplates.*;
import actions.*;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

	// CHANGE these values:
	public static final String TESSDATA_PATH     = ""; // Tesseract data path
	public static final String TELEGRAM_BOT_API  = ""; // Telegram bot API key
	public static final String TELEGRAM_CHAT_ID  = ""; // Your and your Telegram bot chat_id
	public static final String TELEGRAM_BOT_NAME = ""; // Telegram bot name (e.g. your bot is '@mySuperBot' so it's going to be 'mySuperBot')
	
	// Do NOT change these:
	private static Database db = new Database();

	public static void main(String args[]) {
		init();
		
		Player p1 = new Player("", "");
		// Write your code here...
		
	}
	
	private static void init(){
		// Disable logging:
		Set<String> loggers = new HashSet<>(Arrays.asList("org.apache.http", "groovyx.net.http"));
		for (String log : loggers) {
			Logger logger = (Logger) LoggerFactory.getLogger(log);
			logger.setLevel(Level.INFO);
			logger.setAdditive(false);
		}
		
		// Set up Telegram Bot:
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();
		TelegramBot telegramBot = new TelegramBot();
		try {
			botsApi.registerBot(telegramBot);
		} catch (TelegramApiException e) {
		}
		TelegramBot tb = telegramBot;
		
		// Update static objects in Player class:
		Player.setConfig(tb, db);
	}
}