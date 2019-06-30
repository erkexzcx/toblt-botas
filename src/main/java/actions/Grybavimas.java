package actions;

import actions.basicActions.PickingNoToolAction;
import core.*;

public class Grybavimas extends PickingNoToolAction {

	public Grybavimas(Bot bot, Item item) {
		super(bot, item);
	}

	@Override
	public boolean isInventoryFull() {
		return doc.html().contains("Jūsų inventorius jau pilnas!");
	}

	@Override
	public boolean isLevelTooLow() {
		return doc.html().contains("Jūsų grybavimo lygis per žemas.");
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Grybas paimtas:");
	}

	@Override
	public boolean isOtherLevelsTooLow() {
		return false; // TODO
	}

	@Override
	public String getBaseUrl() {
		return "http://tob.lt/miskas.php?{CREDENTIALS}&id=renkugrybus0&ka=";
	}

}
