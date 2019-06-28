package core;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MainBase {
	
	public static void main(String args[]){
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
		
		// Set up db connection:
		Database db = new Database();
		
		// Update static objects in Player class:
		Player.setConfig(tb, db);
		
		// Run bot:
		System.out.println("Program started!");
		new Main().setup(db);
	}
	
}
