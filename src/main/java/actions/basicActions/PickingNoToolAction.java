package actions.basicActions;

import actions.exceptions.*;
import core.*;

public abstract class PickingNoToolAction extends ActionBase {

	public PickingNoToolAction(Bot bot, Item item) {
		super(bot, item);
	}
	
	public void perform() throws LevelTooLowException, InventoryFullException, OtherLevelsTooLowException, ResultFailException {
		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		doc = bot.navigator().navigate(actionUrl(), Navigator.NAVIGATION_TYPE_ACTION);
		
		if (isSuccessful()){ return; }
		if (isLevelTooLow()) { throw new LevelTooLowException(); }
		if (isInventoryFull()) { throw new InventoryFullException(); }
		if (isOtherLevelsTooLow()) { throw new OtherLevelsTooLowException(); }
		
		throw new ResultFailException();
		
	}
	
	public abstract boolean isInventoryFull();
	public abstract boolean isLevelTooLow();
	public abstract boolean isOtherLevelsTooLow();
	
}
