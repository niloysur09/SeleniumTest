package utils;

import java.io.File;

import com.asprise.ocr.Ocr;

public class PerformOCR {
	public static String getTextFromImage (String filePath) {
		Ocr.setUp();
		Ocr ocr = new Ocr();
		ocr.startEngine("eng", Ocr.SPEED_FASTEST);
		String imageText = ocr.recognize(new File[] {new File(filePath)},
				  Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
		ocr.stopEngine();
		String result = imageText.replaceAll("\\s","");
		return result;
		
	}

}
