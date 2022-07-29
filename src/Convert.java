import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.Files;


public class Convert {
	public Convert() {
	}

	public static boolean convertToCsv(String fn) {
		try {
			System.out.println("Konvertiere "+fn);
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder(Config.soffice(), "--convert-to","csv:Text - txt - csv (StarCalc): 59,,11,1,,,true,false", fn).inheritIO().start().waitFor();
//				new ProcessBuilder(Config.soffice(), "--convert-to","csv:Text - txt - csv (StarCalc): 44,34,ANSI,1,,true,false", fn).inheritIO().start().waitFor();
			// Filter arguments: 
			// Delimiter = 59(';'), 
			// wrap Strings = 34 ('"' needed, because of evil Symbols in Lunar order files),
			// Format = ANSI,
			// Start at line = 1,
			// format columns differenty = empty,
			// for more see https://wiki.openoffice.org/wiki/Documentation/DevGuide/Spreadsheets/Filter_Options#Filter_Options_for_the_CSV_Filter
			else
				new ProcessBuilder("/usr/bin/soffice", "--convert-to","csv:Text - txt - csv (StarCalc): 59,,11,1,,,true,false", fn).inheritIO().start().waitFor();
		} catch (Exception e) {
			System.out.println("Das Konvertieren von " + fn +  " ist fehlgeschlagen: " + e);
			return false;
		}
		return true;
	}

	public static boolean convertToXls(String fn) {
		String fnXls = fn.substring(0,fn.indexOf(".")) + ".xls";
		System.out.println("Konvertiere "+fn);
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder(Config.soffice(), "--convert-to", "xls:MS Excel 97", fn).inheritIO().start().waitFor();
//				new ProcessBuilder(Config.soffice(), "--convert-to", "xls:Text - txt - csv (StarCalc): 44,,ANSI,1,1/2/2/2/3/2/4/2,true,false", fn+"2.xls").inheritIO().start().waitFor();
				// Filter arguments: 
				// Delimiter = 59(';'), 
				// wrap Strings = empty (dont wrap strings),
				// Format = ANSI,
				// Start at line = 1,
				// format columns differenty = empty,
				// for more see https://wiki.openoffice.org/wiki/Documentation/DevGuide/Spreadsheets/Filter_Options#Filter_Options_for_the_CSV_Filter
				
				Files.move(Paths.get(fnXls), Paths.get(Config.pathOut(), fnXls), REPLACE_EXISTING);
				
			} else {
		        new ProcessBuilder("/usr/bin/soffice", "--convert-to", "xls:MS Excel 97", fn).inheritIO().start().waitFor();
				Files.move(Paths.get(fnXls), Paths.get(Config.pathOut(), fnXls), REPLACE_EXISTING);
			}
		} catch (Exception e) {
			System.out.println("Das Konvertieren von " + fn +  " ist fehlgeschlagen: " + e);
			return false;
		}
		return true;
	}

	public static boolean convertToDbf(String fn) {
		String fnDbf = fn.substring(0,fn.indexOf(".")) + ".dbf";
		System.out.println("Konvertiere "+fn);
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder(Config.soffice(), "--convert-to", "dbf:dBase" ,fn).inheritIO().start().waitFor();
				System.out.println("move" + " "+fnDbf+" " + Config.pathOut());
				Files.move(Paths.get(fnDbf), Paths.get(Config.pathOut(),fnDbf), REPLACE_EXISTING);
			} else {
				new ProcessBuilder("/usr/bin/soffice", "--convert-to", "dbf:MS Excel 97" ,fn).inheritIO().start().waitFor();
				Files.move(Paths.get(fnDbf), Paths.get(Config.pathOut(),fnDbf), REPLACE_EXISTING);
			}
		} catch (Exception e) {
			System.out.println("Das Konvertieren von " + fn +  " ist fehlgeschlagen: " + e);
			return false;
		}
		return true;
	}

}

