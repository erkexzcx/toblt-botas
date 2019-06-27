package subroutines;

import core.Navigator;
import core.Player;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NewPMRoutine extends Routine {

	private static final Pattern MESSAGE_EXTRACT_PATTERN = Pattern.compile("</b>: (.+)<br>", Pattern.DOTALL); // Pattern.DOTALL means dot can match new lines as well.

	private static final String RANDOM_REPLIES[] = {
		"Atstok",
		"Atsipisk bl",
		"Neuzpisk",
		"Eik nx",
		"Durnas?",
		"Kas tau su galva negerai?",
		"Dx tu"
	};

	private Document doc;

	private final String linkToInbox;

	public NewPMRoutine(Player player) {
		super(player);
		linkToInbox = player.insertCredentials("http://tob.lt/meniu.php?{CREDENTIALS}&id=pm");
	}

	@Override
	public void perform() {

		// Generate random reply in case we need it
		String generatedRandomReply = RANDOM_REPLIES[new Random().nextInt(RANDOM_REPLIES.length)];

		// Then go to our inbox:
		doc = player.navigator().navigate(linkToInbox, Navigator.NAVIGATION_TYPE_REGULAR);

		// Select first message in inbox
		Element messageElement = doc.selectFirst(".game > div[align=\"center\"] > div[align=\"left\"] > div.got");
		if (messageElement == null) {
			player.sendMessage("Error occurred - unable to find PM message in PM inbox!");
			return;
		}

		// Understand if this is system message of user message:
		boolean systemMessage = messageElement.selectFirst("b:contains(@SISTEMA)") != null;
		if (systemMessage) {
			return;
		}

		// Get variables 'messageFrom' and 'message'.
		String messageFrom = messageElement.selectFirst("b > a").text();
		Matcher m = MESSAGE_EXTRACT_PATTERN.matcher(messageElement.html());
		if (!m.find()) {
			player.sendMessage("Something terrible happened while trying to reply to PM... fix your code!");
			return;
		}
		String message = m.group(1).trim();
		message = Jsoup.parse(message).text();

		// If message was sent by regular user - just ignore it...
//		if (!messageFrom.contains("*")) {
//			player.sendMessage("Ignoring message from " + messageFrom + "...");
//			return;
//		}
		// At this point, we need user interraction:
		player.sendMessage("New message from <b>" + messageFrom + "</b>: <i>" + message + "</i>\n\n2 min to reply. Type \"Nothing\" to ignore. <b>What do we say?</b>");
		String receivedReplyFromRealUser = player.getUserReply();
		if (receivedReplyFromRealUser == null) {
			// User did not provide a reply...... Sad, so let's give a random reply.
			player.sendMessage("Too late! Sending random reply: <i>" + generatedRandomReply + "</i>");
			sendReplyBack(generatedRandomReply, messageElement, false);
			player.sendMessage("Message sent!");
			return;
		}

		// If user provided "Nothing":
		if ("nothing".equals(receivedReplyFromRealUser.toLowerCase())) {
			// User don't want to reply :)
			return;
		}

		// Lastly, send actual user reply:
		sendReplyBack(receivedReplyFromRealUser, messageElement, false);
		player.sendMessage("Message sent!");

	}

	private void sendReplyBack(String reply, Element messageElement, boolean waitToType) {

		// We are in the inbox. Find reply url:
		Element replyUrlElement = messageElement.selectFirst("a:contains([Atsakyti])");
		if (replyUrlElement == null) {
			player.sendMessage("Unable to find [Atsakyti] element in inbox!");
			return;
		}
		String replyUrl = replyUrlElement.attr("abs:href");

		// Go to it:
		doc = player.navigator().navigate(replyUrl, Navigator.NAVIGATION_TYPE_REGULAR);

		// We are not "typing" the message: It takes few seconds :)
		// Sleep for 1 sec:
		if (waitToType) {
			try {
				int max = 15 * 1000;
				int min = 5 * 1000;
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt((max - min) + 1) + min);
			} catch (InterruptedException ex) {
				Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		// Now we need to perform POST request:
		Element element = doc.selectFirst("form[method=\"post\"][action*=\"id=siusti_pm\"]");
		if (element == null) {
			player.sendMessage("Unable to find form where I can reply to message!");
			return;
		}

		// And do next URL request :)
		doc = player.navigator().postRequest(
				element.attr("abs:action"),
				new String[][]{
					{"zinute", reply},
					{"null", "Siųsti"}
				}
		);
	}
}
