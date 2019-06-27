package subroutines;

import core.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.sourceforge.tess4j.TesseractException;
import org.im4java.core.IM4JavaException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/* 
 * This is anti bot routine.
 */
public class AntiBotRoutine extends Routine {

	public static final String COLORS[][] = { /* DO NOT EDIT */
		{"raudona", "00b2bd826148fac618dd782570dde345"},
		{"melyna", "06a957d0d6eddd395a2bff5210294950"},
		{"oranzine", "422bd9e530718e8c2f7ecfab43762f28"},
		{"rozine", "f03b55e7ea057ddd1f6c6ab1335f7183"},
		{"zalia", "b51e5d380e02ecb9922ec8d5494be7a9"},
		{"juoda", "61d8c65d185686d3af8419292d37667b"},
		{"geltona", "ce5118cb6325b96831eb91714815b8ef"},
		{"violetine", "d0da1f6f707eeda3641430a6e94ee91c"},
		{"ruda", "32f793aa2620d2be93d2573ccff75b75"}
	};

	private final Document doc;

	/**
	 * @param doc Must be the document containing detected anti bot check
	 * screen.
	 * @param player Player object.
	 */
	public AntiBotRoutine(Document doc, Player player) {
		super(player);
		this.doc = doc;
	}

	@Override
	public void perform() {

		/*
		Code does not look clear at all, but this is what we are doing:
		1. We get the URL of the captcha (we need to read a simple word from it).
		2. We try to read the word from captcha.
		3. We loop through each clickable image, download and extract MD5.
		4. We compare extracted image's MD5 with required color's mapped MD5.
		5. If colors matches - we click on that image. Anti bot check done. :3
		 */
		// URL of the captcha. It contains text of the image that we need to click:
		String captchaImageUrl = doc.selectFirst("img[src^=\"spalva.php?\"]").attr("abs:src");

		// Then get text from the image file. Note that it will be null if it was impossible to read for any issue:
		String colorText = null;
		try {
			// Attempt to extract the color text from that image:
			colorText = getColorText(captchaImageUrl);
		} catch (IOException | TesseractException | InterruptedException | IM4JavaException ex) {
			Logger.getLogger(AntiBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
		// Here we check if it's null. We might intentionally set it to null (means it might not be due to exception):
		if (colorText == null) {
			player.sendMessage("Anti bot routine failed (while trying to read color's text). Clicking on the first image in the list...");
			player.navigator().navigateUnsafe(doc.selectFirst("img[src^=\"antibotimg.php?\"]").parent().attr("abs:href"));
			return;
		}

		// Now we now what color we are looking for, so let's find out wanted color's MD5:
		String colorTextMD5 = null;
		for (String colorPair[] : COLORS) {
			if (colorPair[0].equals(colorText)) {
				colorTextMD5 = colorPair[1];
				break;
			}
			// We are not checking if colorTextMD5 == null?
		}

		// Go through all given color image files and click on the right one:
		Elements imagesToClick = doc.select("img[src^=\"antibotimg.php?\"]");
		for (Element img : imagesToClick) {

			// Extract source (URL) of the image:
			String imageToClickUrl = img.attr("abs:src");

			// Download that image using extracted source (URL):
			File imageToClickFile = CaptchaUtils.downloadTemporaryFile(imageToClickUrl, ".png");

			// Get MD5 hash of that image file:
			String imageToClickMD5 = "";
			try {
				imageToClickMD5 = getMd5Hash(imageToClickFile);
			} catch (FileNotFoundException | NoSuchAlgorithmException ex) {
				Logger.getLogger(AntiBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(AntiBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
			}
			imageToClickFile.delete(); // delete temporary file

			Document result;
			if (imageToClickMD5.equals(colorTextMD5)) {
				String confirmedImageToClickUrl = img.parent().attr("abs:href");
				result = player.navigator().navigateUnsafe(confirmedImageToClickUrl);
				if (result.selectFirst(":contains(Galite žaisti toliau.)") == null) {
					player.sendMessage("Anti bot routine failed (Incorrect color clicked!)");
					break;
				}
				return;
			}
		}

		player.sendMessage("Anti bot routine failed (Unable to find correct color)");
	}

	/**
	 * This method allows us to download the captcha image, containing text of
	 * required image. The thing is - we can download that image as many times
	 * as we want and every time the new downloaded image (captcha) will be
	 * differently generated. This is useful if we are unable to read text from
	 * the first attempt, so we can try over and over again, until text is
	 * clear.
	 *
	 * @param captchaImageUrl the url to captcha image file
	 * @return text from captcha image
	 * @throws IOException
	 * @throws TesseractException
	 * @throws InterruptedException
	 */
	private String getColorText(String captchaImageUrl) throws IOException, TesseractException, InterruptedException, IM4JavaException {
		// How many times do we re-download re-generated image with requierd color's text?
		int MAX_CAPTCHATEXT_DOWNLOADS = 15;

		for (int i = 0; i < MAX_CAPTCHATEXT_DOWNLOADS; i++) {

			// Download that image to a temporary file:
			File captchaFile = CaptchaUtils.downloadTemporaryFile(captchaImageUrl, ".png");

			// Process the image file with imagemagick library:
			File processedCaptchaImage = CaptchaUtils.processImage(captchaFile);

			// Attempt to read the text from downloaded captcha image file:
			String result = CaptchaUtils.readColorTextFromCaptcha(processedCaptchaImage);

			// Delete both temporary files:
			captchaFile.delete();
			processedCaptchaImage.delete();

			if (result == null) {
				continue;
			}
			return result;
		}
		return null;
	}

	public static String getMd5Hash(File file) throws IOException, FileNotFoundException, NoSuchAlgorithmException {
		byte[] b = createChecksum(file);
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	private static byte[] createChecksum(File file) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		InputStream fis = new FileInputStream(file);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}
}
