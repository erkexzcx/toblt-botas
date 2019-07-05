package actions;

import core.*;
import org.jsoup.nodes.Element;

public class Semimas extends Action {

	public Semimas(Bot bot) {
		super(
				bot,
				"http://tob.lt/zvejoti.php?{CREDENTIALS}&id="
		);
	}

	@Override
	protected String getActionUrl() {
		// Need slight adjustement:
		Element el = doc.selectFirst("a[href*=\"&kd=\"]:contains(Semti vandenį)");
		if (el == null) {
			bot.stopActivity("Nepavyko rasti veiksmo nuorodos! Klase: " + this.getClass().getName());
			return null;
		}
		return el.attr("abs:href");
	}

	@Override
	protected int preChecks() {
		return RES_SUCCESS;
	}

	@Override
	protected int postChecks() {
		
		if (doc.html().contains("Jūsų inventorius jau pilnas!")) {
			return RES_INVENTORY_FULL;
		}

		return RES_SUCCESS;

	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Pasėmėte vandens");
	}

}
