package activityTemplates;

import core.Bot;
import org.jsoup.nodes.Document;
import subroutines.NewPMRoutine;

public abstract class ActivityBase extends Thread implements Activity {

	protected final Bot bot;
	protected Document doc;

	protected volatile boolean stopFlag = false;

	public ActivityBase(Bot bot) {
		this.bot = bot;
	}

	protected void checkNewPm() {
		boolean newPm = doc.selectFirst("a[href$=\"&id=pm\"]:contains(Yra naujų PM)") != null;
		if (newPm) {
			new NewPMRoutine(bot).perform();
		}
	}

	@Override
	public void stopThread() {
		stopFlag = true;
	}

	@Override
	public void startThread() {
		this.start();
	}

	@Override
	public void run() {
		startActivity();
	}

	protected abstract void startActivity();
	
	protected void resOtherLevelsTooLow(Object obj){
		bot.stopActivity("Bot's other levels are too low to perform " + obj.getClass().getSimpleName() + "! Bot ends...");
	}
	
	protected void resLevelTooLow(Object obj){
		bot.stopActivity("Bot's " + obj.getClass().getSimpleName() + " level is too low! Bot ends...");
	}
	
	protected void resMissingTool(Object obj){
		bot.stopActivity("Bot's is missing tool for " + obj.getClass().getSimpleName() + " activity! Bot ends...");
	}
	
	protected void resFailure(Object obj){
		bot.stopActivity("Bot's ended up with failure during " + obj.getClass().getSimpleName() + " activity! Fix your code and try again!");
	}

}
