package actions;

import actions.basicActions.PickingToolAction;
import core.*;

public class Kasimas extends PickingToolAction {

	public Kasimas(Bot bot, Item item) {
		super(bot, item);
	}

	@Override
	public boolean isInventoryFull() {
		return doc.html().contains("Jūsų inventorius jau pilnas!");
	}

	@Override
	public boolean isMissingTool() {
		return doc.html().contains("Neturite reikalingo kirtiklio!");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Iškasta:");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Jūsų kasimo lygis per žemas");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return doc.html().contains("");
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/kasimas_kalve.php?{CREDENTIALS}&id=mininu0&ka=";
	}

}
