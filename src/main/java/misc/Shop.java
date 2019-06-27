package misc;

import core.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Shop {

	private static final String INVENTORY_BASE_URL = "http://tob.lt/zaisti.php?{CREDENTIALS}&id=inventory&page=1";
	private static final String QUICK_SHOP_SELL_URL = "http://tob.lt/parda.php?{CREDENTIALS}&id=universali2&psl=1&ka=";
	private static final Pattern EXTRACT_ITEMID_PATTERN = Pattern.compile("&ka=(\\w+)");

	private final Player player;
	private Document doc;
	private final String inventoryUrl;
	private final String quickShopUrlIncomplete;

	public Shop(Player player) {
		this.player = player;
		inventoryUrl = player.insertCredentials(INVENTORY_BASE_URL);
		quickShopUrlIncomplete = player.insertCredentials(QUICK_SHOP_SELL_URL);
	}

	public void sell(Item item) {

		if (item == null) {
			return; // Nothing to sell - not enough details
		}

		if (item.getSellUrl() == null) {
			// Gotta sell in quick shop, since we can't sell it in regular shop (e.g. keys):
			sellItemQuick(item);
		} else {
			// Sell in regular shop - more cash.
			sellItemStandard(item);
		}

	}

	public void sellEverything(Item[] exceptItems) {

		// Go to inventory:
		doc = player.navigator().navigate(inventoryUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// Find list of items we are going to sell. Use remove button and then extract each item ID:
		Elements elements = doc.select("a[href*=\"&id=drop&\"]");
		for (Element el : elements) {

			Matcher m = EXTRACT_ITEMID_PATTERN.matcher(el.attr("abs:href"));
			if (!m.find()) {
				player.sendMessage("Regex doesn't work... fix your code!");
				return;
			}
			String id = m.group(1);

			// Check if not in our given exceptItems list
			boolean inList = false;
			for (Item tmp : exceptItems) {
				if (tmp.getId().equals(id)) {
					inList = true;
					break;
				}
			}

			if (inList) {
				continue;
			}

			Item item = player.database().getItemById(id);
			sell(item);
		}

	}

	private void sellItemQuick(Item item) {
		String sellUrl = quickShopUrlIncomplete + item.getId();
		doc = player.navigator().navigate(sellUrl, Navigator.NAVIGATION_TYPE_REGULAR);
	}

	private void sellItemStandard(Item item) {
		// First we go to that URL:
		String sellUrl = player.insertCredentials(item.getSellUrl());
		doc = player.navigator().navigate(sellUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// Find POST request details:
		Element el = doc.selectFirst("input[name=\"kiekis\"][type=\"hidden\"]");
		String formUrl = el.parent().attr("abs:action");
		String amount = el.attr("value");

		if (Integer.parseInt(amount) == 0) {
			return;
		}

		// Perform POST request:
		doc = player.navigator().postRequest(
				formUrl,
				new String[][]{
					{"kiekis", amount},
					{"null", "Parduoti"}
				}
		);
	}

}
