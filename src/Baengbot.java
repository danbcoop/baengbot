import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.*;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.*;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.*;

import java.awt.event.KeyEvent;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

public class Baengbot{
	static Robot robot;
	static String pdfContent;
	static String pdfRaw;
	int[] windowPos = {-1,-1};

	// Constructor
	public Baengbot() throws AWTException, IOException, TikaException {
		robot = new Robot();
		readPdf();
	}

	public boolean run() {
		adjustCodes();

		if (init()) {
			System.out.println("Starte Eingabe.");

			//Skip non-previews items
			String startString = Config.begin();

			pdfContent = pdfContent.substring(pdfContent.indexOf(startString));
			//Creating a matcher object

			String regex = "(DC|[A-Z]{3})[0-9DC]{6,8}";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(pdfContent);

			String code;

			while(matcher.find()) {
				code = matcher.group();
				for(int i = 0;i<5;i++) {
					robot.keyPress(KeyEvent.VK_LEFT);
					robot.delay(50);
					robot.keyRelease(KeyEvent.VK_LEFT);
					robot.keyRelease(KeyEvent.VK_LEFT);
				}
				type(code);
				robot.delay(50);
				type("J");
				robot.delay(50);
				type("c");
			
			}
			type("Fertig");
			return true;
		} else {
			return false;
		}

	}

	private boolean init() {
		robot.delay(2000);
		if (!findWindow()) {
			System.out.println("ModernG-Fenster nicht gefunden! Bitte erneut versuchen.");
			return false;
		}
		robot.setAutoDelay(40);
		robot.setAutoWaitForIdle(true);
		robot.delay(500);
		robot.mouseMove(windowPos[0], windowPos[1]);
		robot.delay(500);
		leftClick();
		return true;
	}

	private void adjustCodes() {
		String repPattern = "(\\d{1})(\\d{3})(DC)(\\d+)";
		pdfContent = pdfContent.replaceAll(repPattern, "$3$1$2$4");
		pdfContent = pdfContent.replaceAll("UCS", "Z");
		//Marvel Codes nach LUT ersetzen
		repPattern = "([79]\\d{16})";	
		String path = Paths.get("mar_mg.csv").toString();
		Pattern r = Pattern.compile(repPattern);
		Matcher m = r.matcher(pdfContent);
		while (m.find()) {
			try {
				System.out.println(m.group());
				File myObj = new File(path);
				Scanner myReader = new Scanner(myObj);
				myReader.useDelimiter(System.lineSeparator());
				String regex = ";([^;]+);([^;]+);.*$";

				while (myReader.hasNextLine()) {
					String line = myReader.nextLine();
					String mg = line.replaceFirst(regex, "$1");
					String mar = line.replaceFirst(regex,"$2");
					if (m.group().equals(mar)) {
						pdfContent.replaceFirst(repPattern, mg);
					}
				}
				myReader.close();
			}
			catch (Exception e) {
				System.out.println(e);
				System.out.println(path + " wurde nicht gefunden.");
			}
		}
	}

	public static void readPdf() {
		File file = new File(".");
		String[] filenames = file.list();
		String filename = "";
		Scanner in = UI.in;
		for (String f:filenames) {
			if (f.indexOf(".pdf")!=-1) {
				System.out.println(f + " einlesen? (j/n)");
				String jn = "";
				while ( !(jn.equals("j") || jn.equals("n")) ) {
					System.out.println("Bitte \"j\" (ja) oder \"n\" (nein) eingeben.");
					try {
						jn = in.next();
					} catch (Exception e) {}
					
				}
				if (jn.equals("j")) {
					filename = f;
					break;
				}
			}
		}

		if (filename.equals("")) {
			System.out.println("Es wurde kein Lieferschein ausgewählt.");
			return;
		}
		BodyContentHandler handler = new BodyContentHandler();
		try {
			Metadata metadata = new Metadata();
			FileInputStream inputstream = new FileInputStream(new File(filename));
			ParseContext pcontext = new ParseContext();
			//parsing the document using PDF parser
			PDFParser pdfparser = new PDFParser(); 
			pdfparser.parse(inputstream, handler, metadata, pcontext);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		//getting the content of the document
		pdfRaw = handler.toString();
		pdfContent = pdfRaw;
		try {
			System.out.println("Lieferschein eingelesen.");
		} catch (Exception e) {
			System.out.println("Lieferschein konnte nicht geladen werden: " + e);
		}
	}

	private void leftClick() {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(200);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		robot.delay(200);
	}

	@SuppressWarnings("unused")
	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int i = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private boolean findWindow() {
		Rectangle rect;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		int y_lim=height;
		Color pixelColor;
		rect = new Rectangle(1, 1, width, height);
		BufferedImage imgCapture = robot.createScreenCapture(rect);
		for (int x = 640/2; x < width-640; x+=640) {
			for (int y = 400/8; y < height-400; y+=400/8) {
				pixelColor = new Color(imgCapture.getRGB(x, y));
				if (pixelColor.getRed() == 0 && pixelColor.getGreen() == 0 && pixelColor.getBlue() == 170) {
					int x2 = x, y2=y;
					while (pixelColor.getBlue() == 170) {
						x2--;
						pixelColor = new Color(imgCapture.getRGB(x2, y2));
					
					}
					x2++;
					pixelColor = new Color(imgCapture.getRGB(x2, y2));
					
					while (y2>0) {
 						if(new Color(imgCapture.getRGB(x2, y2)).getBlue()==170)
							y_lim=y2;
						y2--;
					}
					y2=y_lim;
					pixelColor = new Color(imgCapture.getRGB(x2+100, y2));
					
					if (pixelColor.getRed() == 0 && pixelColor.getGreen() == 0 && pixelColor.getBlue() == 170) {
						this.windowPos[0] = x2;
						this.windowPos[1] = y2;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void type(String s) {
		byte[] bytes = s.getBytes();
		for (byte b : bytes) {
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123)
				code = code - 32;
			robot.keyPress(code);
			robot.delay(Config.delayPress());
			robot.keyRelease(code);
			robot.keyRelease(code);
			robot.delay(Config.delayRelease());
		}
	}

	static ComicList readComics(ComicList cs) {

		String comics = pdfRaw.substring(pdfRaw.indexOf(Config.begin())); //Skip non-previews items
		String regex = RegexHelper.rawComic;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(comics);
		Comic cOld;

		while(matcher.find()) {
			//String comic = matcher.group();
			//String code = comic.replaceFirst(regex, "$1");
			//String name = comic.replaceFirst(regex, "$2");
			//int issue = Integer.parseInt(comic.replaceFirst(regex, "$3"));
			String code = matcher.group(1);
			String name = matcher.group(2);
			int issue = Integer.parseInt(matcher.group(3));
			Comic c = new Comic(name, issue, code);
			cOld = cs.comics.put(name, c);
			if (cOld!=null) {
				if (c.getIssue()-cOld.getIssue()>1) 
					System.out.println("Warnung: " + cOld + "->" + c);
			}
		}
		regex = RegexHelper.rawComicVariant;
		pattern = Pattern.compile(regex);
		matcher = pattern.matcher(comics);

		while(matcher.find()) {
			String comic = matcher.group();
			String code = comic.replaceFirst(regex, "$1");
			String name = comic.replaceFirst(regex, "$2");
			String issue = comic.replaceFirst(regex, "$3");
			String addition = comic.replaceFirst(regex, "$4");
			name = name + addition;
			Comic c = new Comic(name, Integer.parseInt(issue), code);
			cOld = cs.comics.put(name, c);
			if (cOld!=null) {
				if (c.getIssue()-cOld.getIssue()>1) 
					System.out.println("Warnung: " + cOld + "->" + c);
			}
		}
		return cs;
	}

}



