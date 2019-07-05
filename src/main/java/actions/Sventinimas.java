package actions;

import core.*;

public class Sventinimas extends Action {

	public Sventinimas(Bot bot) {
		super(
				bot,
				"http://tob.lt/baznycia.php?{CREDENTIALS}&id="
		);
	}

	@Override
	public boolean isSuccessful() {
		return doc.html().contains("Vanduo pašventintas");
	}

	@Override
	protected int preChecks() {

		if (doc.html().contains("Neturite vandens!")) {
			return RES_OUT_OF_MATERIALS;
		}

		return RES_SUCCESS;

	}

	@Override
	protected int postChecks() {

		return RES_SUCCESS;

	}

}
