package core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageMagickCmd;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import static subroutines.AntiBotRoutine.COLORS;

public class CaptchaUtils {

	public static String readColorTextFromCaptcha(File img) throws IOException, TesseractException, InterruptedException {

		/*
		YOU MUST USE THIS BEFORE STARTING THE PROGRAM, OTHERWISE TESS4J WON'T WORK!!!
		
		export LC_ALL=C
		 */
		ITesseract tesseract = new Tesseract();
		tesseract.setPageSegMode(8);
		tesseract.setDatapath(Main.TESSDATA_PATH);
		tesseract.setLanguage("lit");

		String result = tesseract.doOCR(img).toLowerCase().replaceAll("\\s+", "");

		if (result.equals("nieko")) {
			return null; // Sometimes this happens... Means we are too late!
		}

		for (String color[] : COLORS) {
			if (result.equals(color[0])) {
				return result;
			}
		}

		// predefined text in COLORS[n][0] array not found, so return null:
		return null;
	}

	public static String readNumbersFromCaptcha(File img) {
		try {
			ITesseract tesseract = new Tesseract();
			tesseract.setTessVariable("tessedit_char_whitelist", "0123456789");
			tesseract.setTessVariable("load_system_dawg", "false");
			tesseract.setTessVariable("load_freq_dawg", "false");
			tesseract.setPageSegMode(8);
			tesseract.setDatapath(Main.TESSDATA_PATH);
			tesseract.setLanguage("lit");

			String result = tesseract.doOCR(img).replaceAll("\\s", "");
			return result;
		} catch (TesseractException ex) {
			Logger.getLogger(CaptchaUtils.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	/**
	 * Processes image with imagemagick so it can nicely be read by tesseract
	 * OCR.
	 *
	 * TODO - What about Windows OS?
	 *
	 * @param img - Original file that needs to be processed.
	 * @return processed img in temporary dir
	 */
	public static File processImage(File img) {
		try {
			File newimg = File.createTempFile("toblt", ".png");

			ImageMagickCmd cmd = new ImageMagickCmd("convert");
			IMOperation op = new IMOperation();

			op.addImage(img.getAbsolutePath());
			op.strip().resample(300).colorspace("gray").autoLevel().threshold(35000).type("bilevel").depth(8).trim();
			op.addImage(newimg.getAbsolutePath());

			try {
				cmd.run(op);
			} catch (IM4JavaException | InterruptedException e) {
				/* 100% failure rate, but image gets processed as expected */
			}

			return newimg;
		} catch (IOException ex) {
			Logger.getLogger(CaptchaUtils.class.getName()).log(Level.SEVERE, null, ex);
			/* 100% failure rate, but image gets processed as expected */
		}
		return null;
	}

	/**
	 * Downloads a file to temporary location and returns it.
	 *
	 * @param url
	 * @param ext
	 * @return
	 */
	public static File downloadTemporaryFile(String url, String ext) {
		// Open a URL Stream
		Connection.Response resultImageResponse;
		try {
			resultImageResponse = Jsoup
					.connect(url)
					.timeout(30 * 1000)
					.userAgent("Mozilla")
					.ignoreContentType(true)
					.execute();

			File tmpFile = File.createTempFile("toblt", ext);
			FileOutputStream out = new FileOutputStream(tmpFile);
			out.write(resultImageResponse.bodyAsBytes());
			out.close();

			return tmpFile;
		} catch (IOException ex) {
			Logger.getLogger(CaptchaUtils.class.getName()).log(Level.SEVERE, null, ex);
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException ex1) {
				Logger.getLogger(Navigator.class.getName()).log(Level.SEVERE, null, ex1);
				return downloadTemporaryFile(url, ext);
			}
		}

		return null;
	}

}
