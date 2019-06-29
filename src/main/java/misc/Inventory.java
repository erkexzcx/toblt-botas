package misc;

import core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Inventory {

	private static final String INVENTORY_BASE_URL = "http://tob.lt/zaisti.php?{CREDENTIALS}&id=inventory&page=1";
	private static final Pattern EXTRACT_ITEMID_PATTERN = Pattern.compile("&ka=(\\w+)");
	private static final Pattern EXTRACT_ITEM_COUNT_PATTERN = Pattern.compile("Kiekis:\\s+<b>(\\d+)</b>", Pattern.MULTILINE);

	private final Bot bot;
	private Document doc;
	private final String inventoryUrl;

	public Inventory(Bot bot) {
		this.bot = bot;
		inventoryUrl = bot.insertCredentials(INVENTORY_BASE_URL);
	}

	public List<Item> getAll() {
		
		// Store items here:
		List<Item> items = new ArrayList<>();

		// Go to inventory:
		doc = bot.navigator().navigate(inventoryUrl, Navigator.NAVIGATION_TYPE_REGULAR);
		
		// For each "container":
		Elements containers = doc.select("div.send");
		for (Element container : containers){
			
			// Extract Item ID:
			Element el = container.selectFirst("a[href*=\"&id=drop&\"]");
			Matcher m = EXTRACT_ITEMID_PATTERN.matcher(el.attr("abs:href"));
			if (!m.find()) {
				bot.sendMessage("Regex doesn't work... fix your code!");
				System.exit(1);
			}
			String id = m.group(1);
			
			// Create Item object:
			Item itm = bot.database().getItemById(id);

			// Also insert item's count to the object:
			m = EXTRACT_ITEM_COUNT_PATTERN.matcher(container.html());
			if (!m.find()) {
				bot.sendMessage("Regex doesn't work... fix your code!");
				System.exit(1);
			}
			String countText = m.group(1);
			int count = Integer.parseInt(countText);
			
			// Append count info to Item:
			itm.setCount(count);
			
			// Put item to list:
			items.add(itm);
		}
		return items;
	}

}
