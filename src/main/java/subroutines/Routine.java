package subroutines;

import core.*;

abstract class Routine {

	protected final Player player;

	public Routine(Player player) {
		this.player = player;
	}

	public abstract void perform();
}
