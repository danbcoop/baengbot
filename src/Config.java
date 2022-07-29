import java.io.File;
//import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.io.FileWriter;

public final class Config {

	private static String begin = "LUNAR";
	private static String delay_press = "15";
	private static String delay_release = "5";
	private static String path_in = Paths.get("").toAbsolutePath().getParent().getParent().toString();
	private static String path_out = path_in; 
	private static String soffice = "C:\\Program Files\\LibreOffice\\program\\soffice";
	private static String dcCode = "Code";
	private static String dcTitle = "Title";
	private static String dcIssue = "IssueNumber";
	private static String dcPrice = "Retail";
	private static String marCode = "MainIdentifier";
	private static String marTitle = "Title";
	private static String marIssue = "SeriesNumber";
	private static String marPrice = "PriceUSD";
	private static String diaCode = "DIAMD_NO";
	private static String diaTitle = "FULL_TITLE";
	private static String diaIssue = "ISSUE_NO";
	private static String diaPrice = "PRICE";


	public Config() {
		try {
			File cfgFile        = new File(Paths.get("config.cfg").toString());
			Scanner cfgScanner  = new Scanner(cfgFile);
			cfgScanner.useDelimiter(System.lineSeparator());
			while (cfgScanner.hasNextLine()) {

				if (cfgScanner.hasNext(RegexHelper.option)) {
					cfgScanner.next(RegexHelper.option);
					MatchResult res = cfgScanner.match();
					String option = res.group(1);
					switch (option) {
					case "begin":
						begin = res.group(2);
						break;
					case "delay_press":
						delay_press = res.group(2);
						break;
					case "delay_release":
						delay_release = res.group(2);
						break;
					case "path_in":
						path_in = Paths.get(res.group(2)).toString();
						break;
					case "path_out":
						path_out = Paths.get(res.group(2)).toString();
						break;
					case "path_soffice":
						soffice = Paths.get(res.group(2)).toString();
						break;
					case "dc_code":
						dcCode = res.group(2);
						break;
					case "dc_issue":
						dcIssue = res.group(2);
						break;
					case "dc_title":
						dcTitle = res.group(2);
						break;
					case "dc_price":
						dcPrice = res.group(2);
						break;
					case "dia_code":
						diaCode = res.group(2);
						break;
					case "dia_issue":
						diaIssue = res.group(2);
						break;
					case "dia_title":
						diaTitle = res.group(2);
						break;
					case "dia_price":
						diaPrice = res.group(2);
						break;          
					case "mar_code":
						marCode = res.group(2);
						break;
					case "mar_issue":
						marIssue = res.group(2);
						break;
					case "mar_title":
						marTitle = res.group(2);
						break;
					case "mar_price":
						marPrice = res.group(2);
						break;
					}
				}
				cfgScanner.nextLine();
			}
			cfgScanner.close();
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen der Config Datei: " + e);
			if (e instanceof java.io.FileNotFoundException) {
				System.out.println("Schreibe neue Config.");
				try {write();}
				catch(Exception ee) {
					System.out.println("Fehler beim Schreiben der Config Datei: " + e);
				}
			}
		}
	}

	public void write() throws Exception{
		File obj = new File("config.cfg");
		FileWriter cfg = new FileWriter(obj);
		cfg.write("#Einstellungen mit Raute (#) sind auskommentiert,"+System.lineSeparator());
		cfg.write("#begin\t= " + begin+"\t#gibt an, ab wo der Lieferschein eingetippt wird."+System.lineSeparator());
		cfg.write("#delay_press\t= "+delay_press+"#ms"+System.lineSeparator());
		cfg.write("#delay_release\t= "+delay_release+"#ms"+System.lineSeparator());
		cfg.write("#path_on\t= "+path_in+System.lineSeparator());
		cfg.write("#path_out\t= "+path_out+System.lineSeparator());
		cfg.write("#path_soffice\t= "+soffice+System.lineSeparator());
		cfg.write(System.lineSeparator()+
				"#Die folgenden Optionen definieren die Spalten der Orderdateien für den Import"+System.lineSeparator());
		cfg.write("#dc_code\t= " + dcCode+System.lineSeparator());
		cfg.write("#dc_title\t= "+ dcTitle+System.lineSeparator());
		cfg.write("#dc_issue\t= "+ dcIssue+System.lineSeparator());
		cfg.write("#dc_price\t= "+dcPrice+System.lineSeparator());
		cfg.write("#mar_code\t= " + marCode+System.lineSeparator());
		cfg.write("#mar_title\t= "+ marTitle+System.lineSeparator());
		cfg.write("#mar_issue\t= "+ marIssue+System.lineSeparator());
		cfg.write("#mar_price\t= "+marPrice+System.lineSeparator());
		cfg.write("#dia_code\t= " + diaCode+System.lineSeparator());
		cfg.write("#dia_title\t= "+ diaTitle+System.lineSeparator());
		cfg.write("#dia_issue\t= "+ diaIssue+System.lineSeparator());
		cfg.write("#dia_price\t= "+diaPrice+System.lineSeparator());
		cfg.close();



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

	public static String dcCode() {
		return dcCode;
	}

	public static String dcTitle() {
		return dcTitle;
	}

	public static String dcIssue() {
		return dcIssue;
	}
	public static String dcPrice() {
		return dcPrice;
	}

	public static String marCode() {
		return marCode;
	}

	public static String marTitle() {
		return marTitle;
	}

	public static String marIssue() {
		return marIssue;
	}
	public static String marPrice() {
		return marPrice;
	}

	public static String diaCode() {
		return diaCode;
	}

	public static String diaTitle() {
		return diaTitle;
	}

	public static String diaIssue() {
		return diaIssue;
	}
	public static String diaPrice() {
		return diaPrice;
	}

}

