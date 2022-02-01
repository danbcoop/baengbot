import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.MatchResult;

public final class Config {

  private static String begin = "LUNAR";
  private static String delay_press = "5";
  private static String delay_release = "3";
	private static String path_in = Paths.get("").toAbsolutePath().getParent().getParent().toString();
  private static String path_out = path_in; 
  private static String soffice = "C:\\Program Files\\LibreOffice 5\\program"
  private String patternOption = RegexHelper.option();

  public Config() {
   	try {
		  File cfgFile        = new File(Paths.get("config.cfg").toString());
		  Scanner cfgScanner  = new Scanner(cfgFile);
      cfgScanner.useDelimiter(System.lineSeparator());
      while (cfgScanner.hasNextLine()) {
        if (cfgScanner.hasNext(RegexHelper.option())) {
          cfgScanner.next(RegexHelper.option());
          MatchResult res = cfgScanner.match();
          String option = res.group(1);
          switch (option) {
            case "begin"
              -> begin = res.group(2);
            case "delay_press" 
              -> delay_press = res.group(2);
            case "delay_release"
              -> delay_release = res.group(2);
            case "path_in"
              -> path_in = res.group(2);
            case "path_out"
              -> path_out = res.group(2);
            case "path_soffice.exe"
              -> soffice = res.group(2);
          }
        }
        cfgScanner.nextLine();
		  }
		  cfgScanner.close();
		} catch (Exception e) {
      System.out.println("Fehler beim Lesen der Config Datei: " + e);
    }
 }

  public static String begin() {
    return begin;
  }

  public static int delayPress() {
    return Integer.parseInt(delay_press);
  }


  public static int delayRelease() {
    return Integer.parseInt(delay_release);
  }

  public static String pathIn() {
    return path_in;
  }

  public static String pathOut() {
    return path_out;
  }

  public static String soffice() {
    return soffice;
  }
}
