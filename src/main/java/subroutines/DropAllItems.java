package subroutines;

import core.Navigator;
import core.Bot;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DropAllItems extends Routine {

	private final String inventoryUrl;
	private static final Pattern EXTRACT_AMOUNT_PATTERN = Pattern.compile("Kiekis:\\s+<b>(\\d+)</b>", Pattern.MULTILINE);

	public DropAllItems(Bot bot) {
		super(bot);
		inventoryUrl = bot.insertCredentials("http://tob.lt/zaisti.php?{CREDENTIALS}&id=inventory&page=1");
	}

	@Override
	public void perform() {

		// Go to inventory:
		Document doc = bot.navigator().navigate(inventoryUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// Select all elements that we are going to use:
		Elements elements = doc.select("div.send");
		elements.forEach((e) -> {
			// Generate link from element attributes:
			String dropUrl2 = e.selectFirst("a:contains((Išmesti))").attr("abs:href").replaceAll("&id=drop&", "&id=drop2&");

			// Get droppable amount:
			Matcher m = EXTRACT_AMOUNT_PATTERN.matcher(e.html());
			if (!m.find()) {
				bot.sendMessage("Regex doesn't work in kovojimas activity... fix your code!");
				System.exit(1);
			}
			int droppableAmount = Integer.parseInt(m.group(1));

			// Drop it:
			bot.navigator().postRequest(
					dropUrl2,
					new String[][]{
						{"kiekis", String.format("%d", droppableAmount)},
						{"null", "Išmesti+visus"}
					}
			);
		});

	}

}
