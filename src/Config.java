import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

public final class Config {

  private Config() {}

  public static String readConfig(String option) {
  String optionValue = "";
   	try {
		  String path       = Paths.get("config.cfg").toString();
		  File myObj        = new File(path);
		  Scanner myReader  = new Scanner(myObj);
		  myReader.useDelimiter(System.lineSeparator());
		  while (myReader.hasNext()) {
			  String line = myReader.next();
        // if "option" is found
			  if (line.indexOf(option)!=-1) {
				  line = line.replaceAll("\\s",""); // get rid of white spaces
          if (line.indexOf("#")<1) // if option is not commented out
            optionValue = line.replaceFirst(".+=(.+)[#]*","$1");
			  }
		  }
		  myReader.close();
		} catch (Exception e) {
      System.out.println("Fehler beim Lesen der Config Datei: " + e);
    }
    return optionValue;
}
  public static String begin() {
    String s = readConfig("begin"); 
    return s.equals("")? "LUNAR" : s;
  }

  public static String delayPress() {
    String s = readConfig("delay_press");
    return s.equals("")? "5" : s;
  }


  public static String delayRelease() {
    String s = readConfig("delay_release");
    return s.equals("")? "6" : s;
  }

  public static String pathIn() {
    String s = readConfig("path_in");
    return s;
  }

  public static String pathOut() {
    String s = readConfig("path_out");
    return s;
  }
}
