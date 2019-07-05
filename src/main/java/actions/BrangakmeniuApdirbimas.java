package actions;

import core.*;

public final class BrangakmeniuApdirbimas extends Action {

	public BrangakmeniuApdirbimas(Bot bot, Item item) {
		super(
				bot,
				"http://tob.lt/dirbtuves.php?{CREDENTIALS}&id=bapd0&ka=" + item.getId().replace("NB", "AB")
		);
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Apdirbta:");
	}

	@Override
	protected int preChecks() {

		if (doc.html().contains("Neužtenka neapdirbtų akmenų!")) {
			return RES_OUT_OF_MATERIALS;
		}

		return RES_SUCCESS;

	}

	@Override
	protected int postChecks() {

		if (doc.html().contains("Jūsų kiti lygiai per žemi!")) {
			return RES_OTHER_LEVELS_TOO_LOW;
		}

		if (doc.html().contains("Jūsų juvelyrikos lygis per žemas.")) {
			return RES_LEVEL_TOO_LOW;
		}

		return RES_SUCCESS;

	}

}
