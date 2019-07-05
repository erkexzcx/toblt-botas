package actions;

import core.*;

public final class AmatuPotingas extends Action {

	public AmatuPotingas(Bot bot, Item item) {
		super(
				bot,
				"http://tob.lt/namai.php?{CREDENTIALS}&id=amatupotion02&ka=" + item.getId().replace("PA", "")
		);
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Pagaminta:");
	}

	@Override
	protected int preChecks() {

		if (doc.html().contains("Nepakanka reikiamų grybų!")) {
			return RES_OUT_OF_MATERIALS;
		}

		return RES_SUCCESS;

	}

	@Override
	protected int postChecks() {

		if (doc.html().contains("Jūsų kiti lygiai per žemi!")) {
			return RES_OTHER_LEVELS_TOO_LOW;
		}

		if (doc.html().contains("Jūsų potingo lygis per žemas")) {
			return RES_LEVEL_TOO_LOW;
		}

		return RES_SUCCESS;

	}

}
