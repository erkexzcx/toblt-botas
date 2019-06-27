package activityTemplates;

import misc.Shop;
import core.*;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Element;

public class BomzavimasActivity extends ActivityBase {

	private final String baseUrl;
	private final Item[] doNotSellList;
	private final Shop shop;

	private static final Pattern EXTRACT_DETAILS_PATTERN = Pattern.compile("Yra šiukšlyne:\\s+<b>(\\d+)</b>\\s+<br> Laisvos vietos inventoriuje:\\s+<b>(\\d+)</b>", Pattern.MULTILINE);
	private static final String TRASH_BASE_URL = "http://tob.lt/zaisti.php?{CREDENTIALS}&id=siuksl";

	public BomzavimasActivity(Player player, Item[] doNotSellList) {
		super(player);
		this.doNotSellList = doNotSellList;
		baseUrl = player.insertCredentials(TRASH_BASE_URL);

		shop = new Shop(player);
	}

	@Override
	protected void startActivity() {

		while (!stopFlag) {

			doc = player.navigator().navigate(baseUrl, Navigator.NAVIGATION_TYPE_REGULAR);

			// Find URL to first item in trash:
			Element el = doc.selectFirst("a[href*=\"id=imtsl&ka=\"]");
			if (el == null) {
				continue; // Nothing to pick from trash. Let's refresh and try again.
			}
			String trashUrl = el.attr("abs:href");

			// Go to your found item:
			doc = player.navigator().navigate(trashUrl, Navigator.NAVIGATION_TYPE_REGULAR);

			// Let's parse some numbers so we know how much we can pick. :)
			Matcher m = EXTRACT_DETAILS_PATTERN.matcher(doc.html());
			if (!m.find()) {
				player.sendMessage("Regex doesn't work... fix your code!");
				break;
			}
			int maxToPick = Integer.parseInt(m.group(1));
			int spaceInInventory = Integer.parseInt(m.group(2));

			// Perform some calculations:
			int amountToTake = (maxToPick > spaceInInventory ? spaceInInventory : maxToPick);
			if (spaceInInventory == 0) {
				shop.sellEverything(doNotSellList);
				continue;
			}

			// Captcha:
			File capthaImageFile = CaptchaUtils.downloadTemporaryFile(doc.selectFirst("img[src=\"ca.php\"]").attr("abs:src"), ".png");
			File processedCaptchaImageFile = CaptchaUtils.processImage(capthaImageFile);
			String numbersFromCaptcha = CaptchaUtils.readNumbersFromCaptcha(processedCaptchaImageFile);

			// Now submit it:
			doc = player.navigator().postRequest(
					trashUrl.replace("&id=imtsl&", "&id=imtsl2&"),
					new String[][]{
						{"kiekis", String.format("%d", amountToTake)},
						{"ko", numbersFromCaptcha},
						{"null", "Imti"}
					}
			);

		}

		player.sendMessage("Stopped! 👎");
	}

}
