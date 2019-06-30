package actions;

import actions.basicActions.PickingToolAction;
import core.*;

public class Kirtimas extends PickingToolAction {

	public Kirtimas(Bot bot, Item item) {
		super(bot, item);
	}

	@Override
	public boolean isInventoryFull() {
		return doc.html().contains("Jūsų inventorius jau pilnas!");
	}

	@Override
	public boolean isMissingTool() {
		return doc.html().contains("Neturite reikiamo kirvio.");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Nukirsta:");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Jūsų medkirčio lygis per žemas.");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return false;
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/miskas.php?{CREDENTIALS}&id=kertu&ka=";
	}

}
