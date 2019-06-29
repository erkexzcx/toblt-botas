package subroutines;

import core.*;

abstract class Routine {

	protected final Bot bot;

	public Routine(Bot bot) {
		this.bot = bot;
	}

	public abstract void perform();
}
