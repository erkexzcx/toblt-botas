package core;

import activityTemplates.Activity;
import misc.*;

public class Bot {

	private final String nick;
	private final String pass;
	private final Shop shop;
	private final Inventory inventory;
	private final Player player;
	private static TelegramBot telegramBot;
	private static Database database;
	private Activity activity = null;

	// Each bot also contains its own Navigator.
	private final Navigator navigator;

	public Bot(String nick, String pass) {
		this.nick = nick;
		this.pass = pass;

		navigator = new Navigator(this);
		shop = new Shop(this);
		inventory = new Inventory(this);
		player = new Player(this);
	}

	public static void setConfig(TelegramBot tb, Database db) {
		Bot.telegramBot = tb;
		Bot.database = db;
	}

	public Navigator navigator() { return navigator; }
	public Shop shop(){ return shop; }
	public Inventory inventory(){ return inventory; }
	public Database database() { return database; }
	public TelegramBot telegramBot() { return telegramBot; }
	public Player player() { return player; }

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
	
	public Bot setActivity(Activity activity){
		this.activity = activity;
		return this;
	}
	
	public Bot startActivity(){
		activity.startThread();
		return this;
	}
	
	public void stopActivity(String reason){
		sendMessage("Bot is stopping: " + reason);
		stopActivity();
	}
	
	public void stopActivity(){
		activity.stopThread();
	}

}
