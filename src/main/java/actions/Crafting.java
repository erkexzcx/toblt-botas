package actions;

import actions.basicActions.UsingMaterialsAction;
import core.*;

public class Crafting extends UsingMaterialsAction {

	public Crafting(Bot bot, Item item) {
		super(bot, item);
	}

	@Override
	public boolean isNotEnoughMaterials() {
		return doc.html().contains("Neužtenka žaliavų!");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Pagaminta:");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Jūsų crafting lygis per žemas.");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return false;
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/dirbtuves.php?{CREDENTIALS}&id=gaminu0&ka=";
	}

}
