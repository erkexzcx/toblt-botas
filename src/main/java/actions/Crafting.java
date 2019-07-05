package actions;

import core.*;

public class Crafting extends Action {

	public Crafting(Bot bot, Item item) {
		super(
				bot,
				"http://tob.lt/dirbtuves.php?{CREDENTIALS}&id=gaminu0&ka=" + item.getId()
		);
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Pagaminta:");
	}

	@Override
	protected int preChecks() {

		if (doc.html().contains("Neužtenka žaliavų!")) {
			return RES_OUT_OF_MATERIALS;
		}

		return RES_SUCCESS;

	}

	@Override
	protected int postChecks() {

		if (doc.html().contains("Jūsų kiti lygiai per žemi!")) {
			return RES_OTHER_LEVELS_TOO_LOW;
		}

		if (doc.html().contains("Jūsų crafting lygis per žemas.")) {
			return RES_LEVEL_TOO_LOW;
		}

		return RES_SUCCESS;

	}

}
