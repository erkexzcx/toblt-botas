package actions.basicActions;

import actions.exceptions.*;
import core.*;

public abstract class UsingMaterialsAction extends ActionBase {

	public UsingMaterialsAction(Bot bot, Item item) {
		super(bot, item);
	}
	
	public void perform() throws NotEnoughMaterialsException, LevelTooLowException, OtherLevelsTooLowException, ResultFailException {
		doc = bot.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		
		if (isNotEnoughMaterials()) { throw new NotEnoughMaterialsException(); }
		
		doc = bot.navigator().navigate(actionUrl(), Navigator.NAVIGATION_TYPE_ACTION);
		
		if (isSuccessful()){ return; }
		if (isLevelTooLow()) { throw new LevelTooLowException(); }
		if (isOtherLevelsTooLow()) { throw new OtherLevelsTooLowException(); }
		
		throw new ResultFailException();
		
	}
	
	public abstract boolean isNotEnoughMaterials();
	public abstract boolean isLevelTooLow();
	public abstract boolean isOtherLevelsTooLow();
	
}
