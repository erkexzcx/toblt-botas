package actions;

import actions.basicActions.UsingMaterialsAction;
import core.*;

public final class AmatuPotingas extends UsingMaterialsAction {

	public AmatuPotingas(Bot bot, Item item) {
		super(bot, item);
		
		//override base URL:
		baseUrl = bot.insertCredentials(getBaseUrl()) + item.getId().replace("PA", "");
	}

	@Override
	public boolean isNotEnoughMaterials() {
		return doc.html().contains("Nepakanka reikiamų grybų!");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Pagaminta:");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Jūsų potingo lygis per žemas");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return false;
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/namai.php?{CREDENTIALS}&id=amatupotion02&ka=";
	}

}
