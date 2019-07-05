package actions;

import core.*;

public class Kovojimas extends Action {

	public static final String PRIESAS_ZIURKE_1 = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=0&psl=0";
	public static final String PRIESAS_DRAKONAS_150 = "http://tob.lt/kova.php?{CREDENTIALS}&id=kova0&vs=64&psl=7";

	public Kovojimas(Bot bot, String baseUrl) {
		super(
				bot,
				bot.insertCredentials(baseUrl)
		);
	}

	@Override
	protected int preChecks() {
		return RES_SUCCESS;
	}

	@Override
	protected int postChecks() {
		return RES_SUCCESS;
	}

	@Override
	protected boolean isSuccessful() {
		return doc.html().contains("Nukovėte");
	}

}
