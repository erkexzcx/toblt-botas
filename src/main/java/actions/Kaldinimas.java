package actions;

import actions.basicActions.UsingMaterialsAction;
import core.*;

public class Kaldinimas extends UsingMaterialsAction {

	public Kaldinimas(Bot bot, Item item) {
		super(bot, item);
	}

	@Override
	public boolean isNotEnoughMaterials() {
		return doc.html().contains("Nepakanka žaliavų!");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Nukalta:");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Kalvininkavimo lygis per žemas!");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return false;
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/kasimas_kalve.php?{CREDENTIALS}&id=kaldinti2&ka=";
	}

}
