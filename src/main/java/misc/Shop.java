package misc;

import core.*;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Shop {

	private static final String QUICK_SHOP_SELL_URL = "http://tob.lt/parda.php?{CREDENTIALS}&id=universali2&psl=1&ka=";
	private static final String PLAYER_MARKET_SUBMIT_URL = "http://tob.lt/aukcijonas.php?{CREDENTIALS}&id=dedu";

	private final Bot bot;
	private Document doc;
	private final String quickShopUrlIncomplete;
	private final String playerMarketSubmitUrl;

	public Shop(Bot bot) {
		this.bot = bot;
		quickShopUrlIncomplete = bot.insertCredentials(QUICK_SHOP_SELL_URL);
		playerMarketSubmitUrl = bot.insertCredentials(PLAYER_MARKET_SUBMIT_URL);
	}

	public void sell(Item item) {

		if (!item.isSellable() || item.getSellUrl() == null) {
			// Gotta sell in quick shop, since we can't sell it in regular shop (e.g. keys):
			sellItemQuick(item);
		} else {
			// Sell in regular shop - more cash.
			sellItemStandard(item);
		}

	}

	public void sellEverything(Item[] exceptItems) {
		List<Item> items = bot.inventory().getAll();
		for (Item itm : items) {

			// Check if not in our given exceptItems list
			boolean inList = false;
			for (Item tmp : exceptItems) {
				if (tmp.getId().equals(itm.getId())) {
					inList = true;
					break;
				}
			}
			if (inList) {
				continue;
			}
			sell(itm);

		}
	}

	private void sellItemQuick(Item item) {
		String sellUrl = quickShopUrlIncomplete + item.getId();
		doc = bot.navigator().navigate(sellUrl, Navigator.NAVIGATION_TYPE_REGULAR);
	}

	private void sellItemStandard(Item item) {
		// First we go to that URL:
		String sellUrl = bot.insertCredentials(item.getSellUrl());
		doc = bot.navigator().navigate(sellUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// Find POST request details:
		Element el = doc.selectFirst("input[name=\"kiekis\"][type=\"hidden\"]");
		String formUrl = el.parent().attr("abs:action");
		String amount = el.attr("value");

		if (Integer.parseInt(amount) == 0) {
			return;
		}

		// Perform POST request:
		doc = bot.navigator().postRequest(
				formUrl,
				new String[][]{
					{"kiekis", amount},
					{"null", "Parduoti"}
				}
		);
	}

	public void sellEverythingByCategory(String category) {
		List<Item> items = bot.inventory().getAll();
		for (Item item : items) {
			if (!item.getCategory().equals(category)) {
				continue;
			}
			sell(item);
		}
	}

	public void putIntoPlayerMarket(Item item, String pricePerItem) {
		List<Item> items = bot.inventory().getAll();
		item.setCount(0);
		for (Item itm : items) {
			if (itm.getId().equals(item.getId())) {
				item.setCount(itm.getCount());
				break;
			}
		}
		if (item.getCount() == 0) {
			bot.sendMessage("Nothing to sell, but we were suppossed to sell " + item + ". Fix your code!");
			return; // Nothing to sell - error.
		}
		
		// TODO - there is strange feature in game when you have more items in
		// your inventory - you can't put anything into player market. This needs
		// additional check or player will never put anything into inventory.

		doc = bot.navigator().postRequest(
				playerMarketSubmitUrl,
				new String[][]{
					{"kan", item.getId()},
					{"page", String.format("%d", item.getCount())},
					{"kj", pricePerItem},
					{"val", "10"},
					{"kam", ""},
					{"null", "Dėti"}
				}
		);

	}

}
