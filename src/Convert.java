public class Convert {
  public Convert() {
}

	public static boolean convertToCsv(String fn) {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder(Config.soffice, "--convert-to","csv:Text - txt - csv (StarCalc): 59,34,ANSI,1,,0,false,true,true", f).inheritIO().start().waitFor();
        // Filter arguments: 
        // Delimiter = 59(';'), 
        // wrap Strings = 34 ('"' needed, because of evil Symbols in Lunar order files),
        // Format = ANSI,
        // Start at line = 1,
        // format columns differenty = empty,
        // for more see https://wiki.openoffice.org/wiki/Documentation/DevGuide/Spreadsheets/Filter_Options#Filter_Options_for_the_CSV_Filter
			else
        new ProcessBuilder("/usr/bin/soffice", "--convert-to","csv:Text - txt - csv (StarCalc): 59,34,ANSI,1,,0,false,true,true", fn).inheritIO().start().waitFor();
		} catch (Exception e) {
      System.out.println("Das Konvertieren von " + fn +  " ist fehlgeschlagen: " + e);
      return false;
		}
    return true;
	}

	public static boolean convertToXls(String fn) {
    String fnXls = fn.substring(0,fn.indexOf(".")-1) + ".xls";
    String fnXlsNew = fn.substring(0,fn.indexOf(".")-1) + "_bearbeitet.xls";
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder(Config.soffice, "--convert-to ", "xls:Text - txt - csv (StarCalc):59,,ANSI,1,,0,false,true", fn).inheritIO().start().waitFor();
        // Filter arguments: 
        // Delimiter = 59(';'), 
        // wrap Strings = empty (dont wrap strings),
        // Format = ANSI,
        // Start at line = 1,
        // format columns differenty = empty,
        // for more see https://wiki.openoffice.org/wiki/Documentation/DevGuide/Spreadsheets/Filter_Options#Filter_Options_for_the_CSV_Filter
				new ProcessBuilder(move, fnXls, Paths.get(Config.pathOut(),fnXlsNew).toString()).inheritIO().start().waitFor();
      } else {
        new ProcessBuilder("/usr/bin/soffice", "--convert-to ", "xls:Text - txt - csv (StarCalc):59,,ANSI,1,,0,false,true", fn).inheritIO().start().waitFor();
				new ProcessBuilder(mv, fnXls, Paths.get(Config.pathOut(),fnXlsNew).toString).inheritIO().start().waitFor();
      }
		} catch (Exception e) {
      System.out.println("Das Konvertieren von " + fn +  " ist fehlgeschlagen: " + e);
      return false;
		}
    return true;
  }

}
