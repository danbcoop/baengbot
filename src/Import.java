//import java.util.regex.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.nio.file.Files;

public class Import {

	static String pathDia = "";
	static String pathDC = "";
	static String pathMar = "";

	static private boolean files() {
		File file = new File(Config.pathIn());
		String[] filenames = file.list();
		Scanner in = UI.in;
		int s = -1;
		int i = 0;
		int[] is = {1,2,3,4,5,6,7,8,9};
		int ii=0;
		System.out.println("Es wurden die folgenden möglichen Dateien in " +Config.pathIn()+" gefunden:");
		for (String f:filenames) {

			if (f.indexOf(".xlsx")!=-1) {

				System.out.println((is[ii]) + ")\t\t" + f);
				is[ii]=i;
				ii++;
			}
			i++;
		}

		System.out.println("\nWähle die Diamond-Datei:");
		while (s==-1) {
			System.out.println("Bitte eine Zahl eingeben. (0 für Abbruch)");
			try {
				s = in.nextInt();
			} catch (Exception e) {}
			if (s==0) {
				return false;
			}

		}		
		pathDia = Paths.get(filenames[is[s-1]]).toString();
		if (!Convert.convertToCsv(Paths.get(Config.pathIn(),pathDia).toString())) {
			return false;
		}

		pathDia = pathDia.substring(0, pathDia.indexOf(".x")) + ".csv";
		s=-1;

		System.out.println("\nWähle die DC-Datei:");
		while (s==-1) {
			System.out.println("Bitte eine Zahl eingeben. (0 für Abbruch)");
			try {
				s = in.nextInt();
			} catch (Exception e) {}
			if (s==0) {
				return false;
			}
		}		
		pathDC = Paths.get(filenames[is[s-1]]).toString();
		if (!Convert.convertToCsv(Paths.get(Config.pathIn(),pathDC).toString())) {
			return false;
		}
		pathDC = pathDC.substring(0, pathDC.indexOf(".x")) + ".csv";
		s=-1;
		System.out.println("\nWähle die Marvel-Datei:");
		while (s==-1) {
			System.out.println("Bitte eine Zahl eingeben. (0 für Abbruch)");
			try {
				s = in.nextInt();
			} catch (Exception e) {}
			if (s==0) {
				return false;
			}

		}		
		pathMar = Paths.get(filenames[is[s-1]]).toString();
		if (!Convert.convertToCsv(Paths.get(Config.pathIn(),pathMar).toString())) {
			return false;
		}
		pathMar = pathMar.substring(0, pathMar.indexOf(".x")) + ".csv";
		s=-1;

		return true;
	}
	public static void run() {
		if(files()) {
			int i = 1;
			try {

				File myObj = new File(pathDia);
				Scanner myReader = new Scanner(myObj);
				myReader.useDelimiter(System.lineSeparator());
				String line;

				boolean DONTAPPEND = false;
				boolean APPEND = true;
				String path = Paths.get("import.csv").toString();
				FileWriter myWriter = new FileWriter(path, DONTAPPEND);
				ColNum cn = new ColNum(myReader.nextLine());
				String month="";
				myWriter.write("Code,Price,Title,Issue,Distributor"+System.lineSeparator());
				while (myReader.hasNextLine()) {
					line = myReader.nextLine();//.replaceAll(",",".");
					String[] cols = line.split(";");
					String regexname = "([^#]+)(?:(?:\\s#)|(?:\\sVOL\\s))(\\d+).*";
					String code = cols[cn.code];
					String price = cols[cn.price];
					String name = cols[cn.title];
					//String issue = "";
					if (name.indexOf(" #") != -1 || name.indexOf(" VOL ") != -1) {
						//issue = name.replaceFirst(regexname, "$2");
						name = name.replaceFirst(regexname, "$1");
					}
					int indexCvr = cols[cn.title].indexOf("CVR ");
					if (indexCvr != -1) name = name+" "+cols[cn.title].substring(indexCvr,indexCvr+5);
					myWriter.write("\"" + code + "\",\"" + price + "\",\"" + name + "\",\"" + cols[cn.issue] + "\",\"DIA\"" + System.lineSeparator());
					i++;
					month = code.substring(0, 5);

				}
				myReader.close();

				////////////////////////////////////////////////////////// DC
				path = Paths.get("dc_mg.csv").toString();
				FileWriter myWriterDC = new FileWriter(path, DONTAPPEND);


				myObj = new File(pathDC);
				myReader = new Scanner(myObj);
				//myReader.useDelimiter(System.lineSeparator());
				myReader.useDelimiter("\n");
				line = myReader.nextLine();
				cn = new ColNum(line);
				myWriterDC.write("Qty,MgCode,Code,Price,Title,Issue"+System.lineSeparator());
				while (myReader.hasNextLine()) {
					line = myReader.nextLine();//.replaceAll(",",".");
					String[] cols = line.split(";");
					String regexname = "([^#]+)(?:(?:\\s#)|(?:\\sVOL\\s))(\\d+).*";
					String code = cols[cn.code];
					String price = cols[cn.price];
					String name = cols[cn.title];
					String issue = "";
					if (name.indexOf(" #") != -1 || name.indexOf(" VOL ") != -1) {
						issue = name.replaceFirst(regexname, "$2");
						name = name.replaceFirst(regexname, "$1");
					}
					int indexCvr = cols[cn.title].indexOf("CVR ");
					if (indexCvr != -1) name = name+" "+ cols[cn.title].substring(indexCvr,indexCvr+5);

					String repPattern = "(\\d+)(\\D\\D)(\\d+)";
					String mgCode = code.replaceAll(repPattern, "$2$1$3");

					myWriter.write("\"" + mgCode + "\",\"" + price + "\",\"" + name + "\",\"" + issue + "\",\"PEP\"" + System.lineSeparator());
					myWriterDC.write(",\"" + month+i+ "\",\"" + code + "\",\"" + price + "\",\"" + cols[cn.title] + "\",\"" + issue + "\"" + System.lineSeparator());
					i++;
				}

				myReader.close();
				myWriterDC.close();

				//////////////////////////////////////////////////////////MAR
				path = Paths.get("mar_mg.csv").toString();
				FileWriter myWriterMAR = new FileWriter(path, DONTAPPEND);

				path = Paths.get("mar_mg").toString();
				FileWriter myWriterMARMG = new FileWriter(path, APPEND);
				myObj = new File(pathMar);
				myReader = new Scanner(myObj);
				myReader.useDelimiter(System.lineSeparator());
				line = myReader.nextLine();
				cn = new ColNum(line);
				myWriterMAR.write("Qty,MgCode,Code,Price,Title,Issue"+System.lineSeparator());
				while (myReader.hasNextLine()) {
					line = myReader.nextLine();//.replaceAll(",",".");
					String[] cols = line.split(";");
					if (cols.length<Math.max(cn.issue,cn.price)) continue;
					String code = cols[cn.code];
					String price = cols[cn.price];
					String name = cols[cn.title].toUpperCase();
					String issue = cols[cn.issue];

					myWriter.write("\""+month+i + "\",\"" + price + "\",\"" + name.substring(0, name.length()<50?name.length():50) + "\",\"" + issue + "\",\"MOD\"" + System.lineSeparator());
					myWriterMAR.write(",\"" + month+i+ "\",\"'" + code + "\",\"" + price + "\",\"" + name + "\",\"" + issue + "\"" + System.lineSeparator());
					myWriterMARMG.write(";" + month+i+ ";" + code + ";" + price + ";" + name + ";" + issue + System.lineSeparator());
					i++;
				}
				myWriter.close();
				myWriterMAR.close();
				myWriterMARMG.close();
				myReader.close();

				Convert.convertToXls("mar_mg.csv");
				Convert.convertToXls("dc_mg.csv");
				Convert.convertToDbf("import.csv");

				Files.delete(Paths.get(pathDia));
				Files.delete(Paths.get(pathDC));
				Files.delete(Paths.get(pathMar));

			} catch (Exception e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}

		} else {
			System.out.println("Import abgebrochen.");
		}
	}



}

