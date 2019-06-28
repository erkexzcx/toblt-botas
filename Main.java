package core;

import activityTemplates.*;
import actions.*;

public class Main {

	public static final String TESSDATA_PATH     = ""; // Tesseract data path (e.g. "/usr/share/tessdata")
	public static final String TELEGRAM_BOT_API  = ""; // Telegram bot API key
	public static final String TELEGRAM_CHAT_ID  = ""; // Your and your Telegram bot chat_id
	public static final String TELEGRAM_BOT_NAME = ""; // Telegram bot name (e.g. your bot is '@mySuperBot' so it's going to be 'mySuperBot')

	public void setup(Database db) {
		//----------------------------------------------------------------------
		
		Player p1 = new Player("nick", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		
		Kovojimas k = new Kovojimas(p1, Kovojimas.PRIESAS_ZIURKE);
		new KovojimasActivity(p1, k).startThread();
		
		//----------------------------------------------------------------------
	}
}