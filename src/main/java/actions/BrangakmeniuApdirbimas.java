package actions;

import actions.basicActions.UsingMaterialsAction;
import core.*;

public final class BrangakmeniuApdirbimas extends UsingMaterialsAction {

	public BrangakmeniuApdirbimas(Bot bot, Item item) {
		super(bot, item);
		
		// Change item ID required for baseUrl:
		baseUrl = bot.insertCredentials(getBaseUrl()) + item.getId().replace("NB", "AB");
	}

	@Override
	public boolean isNotEnoughMaterials() {
		return doc.html().contains("Neužtenka neapdirbtų akmenų!");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Apdirbta:");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Jūsų juvelyrikos lygis per žemas.");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return false;
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/dirbtuves.php?{CREDENTIALS}&id=bapd0&ka=";
	}

}
