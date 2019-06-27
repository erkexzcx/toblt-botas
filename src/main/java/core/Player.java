package core;

public class Player {

	private final String nick;
	private final String pass;
	private static TelegramBot telegramBot;
	private static Database database;

	// Each player also contains its own Navigator.
	private final Navigator navigator;

	public Player(String nick, String pass) {
		this.nick = nick;
		this.pass = pass;

		navigator = new Navigator(this);
	}

	public static void setConfig(TelegramBot tb, Database db) {
		Player.telegramBot = tb;
		Player.database = db;
	}

	public Navigator navigator() {
		return navigator;
	}

	public Database database() {
		return database;
	}

	public synchronized String getUserReply() {
		return telegramBot.getLastMessage();
	}

	public synchronized void sendMessage(String message) {
		telegramBot.sendMessage("<b>" + nick + "</b>: " + message);
		System.out.println("<b>" + nick + "</b>: " + message);
	}

	public String insertCredentials(String templateUrl) {
		return templateUrl.replace("{CREDENTIALS}", "nick=" + nick + "&pass=" + pass);
	}

	@Override
	public String toString() {
		return nick;
	}

}
