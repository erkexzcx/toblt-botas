package actions;

import core.*;

public class Grybavimas extends Action {

	public Grybavimas(Bot bot, Item item) {
		super(
				bot,
				"http://tob.lt/miskas.php?{CREDENTIALS}&id=renkugrybus0&ka=" + item.getId()
		);
	}

	@Override
	protected int preChecks() {
		return RES_SUCCESS;
	}

	@Override
	protected int postChecks() {

		if (doc.html().contains("Jūsų kiti lygiai per žemi!")) {
			return RES_OTHER_LEVELS_TOO_LOW;
		}

		if (doc.html().contains("Jūsų grybavimo lygis per žemas.")) {
			return RES_LEVEL_TOO_LOW;
		}

		if (doc.html().contains("Jūsų inventorius jau pilnas!")) {
			return RES_INVENTORY_FULL;
		}

		return RES_SUCCESS;

	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Grybas paimtas:");
	}

}
