package core;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends org.telegram.telegrambots.bots.TelegramLongPollingBot {

	private String lastMessage = null;

	public synchronized String getLastMessage() {
		lastMessage = null; // make sure we start from null value
		int timeout = 120; //120 seconds
		for (int i = 0; i < timeout; i++) {

			if (lastMessage != null) {
				return lastMessage.trim();
			}

			try {
				TimeUnit.SECONDS.sleep(1); // Sleep for 1 sec
			} catch (InterruptedException ex) {
				Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

		// User did not respond after certain amount of time - return null:
		return null;
	}

	@Override
	public String getBotToken() {
		return Main.TELEGRAM_BOT_API;
	}

	public String getChatId() {
		return Main.TELEGRAM_CHAT_ID;
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			lastMessage = update.getMessage().getText();
			System.out.println("TELEGRAM: " + lastMessage);
		}
	}

	@Override
	public void onUpdatesReceived(List<Update> list) {
		list.forEach((update) -> {
			onUpdateReceived(update);
		});
	}

	@Override
	public String getBotUsername() {
		return Main.TELEGRAM_BOT_NAME;
	}

	public void sendMessage(String text) {
		SendMessage message = new SendMessage()
				.setChatId(getChatId())
				.setParseMode("html")
				.setText(text);
		try {
			execute(message);
		} catch (TelegramApiException e) {
		}
	}

}
