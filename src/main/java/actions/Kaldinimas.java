package actions;

import core.*;

public class Kaldinimas extends Action {

	public Kaldinimas(Bot bot, Item item) {
		super(
				bot,
				"http://tob.lt/kasimas_kalve.php?{CREDENTIALS}&id=kaldinti2&ka=" + item.getId()
		);
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Nukalta:");
	}

	@Override
	protected int preChecks() {

		if (doc.html().contains("Nepakanka žaliavų!")) {
			return RES_OUT_OF_MATERIALS;
		}

		return RES_SUCCESS;

	}

	@Override
	protected int postChecks() {

		if (doc.html().contains("Jūsų kiti lygiai per žemi!")) {
			return RES_OTHER_LEVELS_TOO_LOW;
		}

		if (doc.html().contains("Kalvininkavimo lygis per žemas!")) {
			return RES_LEVEL_TOO_LOW;
		}

		return RES_SUCCESS;

	}

}
