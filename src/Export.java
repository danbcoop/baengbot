import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Export {

	
	public static void run() {
		Convert.convertToCsv(Paths.get(Config.pathIn(),"A","PEP.dbf").toString());
		Convert.convertToCsv(Paths.get(Config.pathIn(),"A","MOD.dbf").toString());

		try {
			String pathMarMg="mar_mg.csv";
			String pathDc="dc_mg.csv";
			String pathPep="PEP.csv";
			String pathMod="MOD.csv";
			
			File myObjExport = new File(pathPep);
			File myObjDc = new File(pathDc);
			
			Scanner myReaderOrderDat = new Scanner(myObjDc);
			myReaderOrderDat.useDelimiter(System.lineSeparator());
			
			String line;
			String lineExp;
			boolean append = false;
			String path = Paths.get("dcorder.csv").toString();
			FileWriter myWriter = new FileWriter(path, append);
			myWriter.write(myReaderOrderDat.nextLine()+System.lineSeparator());
			
			
			while (myReaderOrderDat.hasNextLine()) {
				String regex = ",([^,]+),([^,]+),([^,]+),.+$";
				String repCode = "(\\d+)(\\D\\D)(\\d+)";
				String regExp = "([^;]+);([^;]+)";
				String qty = "";
				line = myReaderOrderDat.nextLine();
				String code = line.replaceFirst(regex, "$1");
				
				Scanner myReaderExport = new Scanner(myObjExport);
				myReaderExport.useDelimiter(System.lineSeparator());
				myReaderExport.nextLine();
				boolean found=false;
				while (!found && myReaderExport.hasNextLine()) {
					lineExp = myReaderExport.nextLine();
					String codeExp = lineExp.replaceFirst(regExp, "$1");
					String qtyExp = lineExp.replaceFirst(regExp, "$2");
					if (code.replaceFirst(repCode,"$2$1$3").equals(codeExp)) {
						qty = qtyExp;
						found = true;
					}
				}
				myWriter.write(qty + line+System.lineSeparator());
				myReaderExport.close();
				
			}
			myReaderOrderDat.close();
			myWriter.close();
			Convert.convertToXls(path);

			//////////////////////////////////////////////////////////MARVEL
			myObjExport = new File(pathMod);
			File myObjOrderDat = new File(pathMarMg);
			
			myReaderOrderDat = new Scanner(myObjOrderDat);
			myReaderOrderDat.useDelimiter(System.lineSeparator());
			
			path = Paths.get("marorder.csv").toString();
			myWriter = new FileWriter(path, append);
			myWriter.write(myReaderOrderDat.nextLine()+System.lineSeparator());
			
			while (myReaderOrderDat.hasNextLine()) {
				String regex = ",([^,]+),([^,]+),([^,]+),.+$";
				String regExp = "([^;]+);([^;]+)";
				String qty = "";
				line = myReaderOrderDat.nextLine();
				String code = line.replaceFirst(regex, "$1");
								
				Scanner myReaderExport = new Scanner(myObjExport);
				myReaderExport.useDelimiter(System.lineSeparator());
				myReaderExport.nextLine();
				boolean found=false;
				while (!found && myReaderExport.hasNextLine()) {
					lineExp = myReaderExport.nextLine();
					String codeExp = lineExp.replaceFirst(regExp, "$1");
					String qtyExp = lineExp.replaceFirst(regExp, "$2");
					if (code.equals(codeExp)) {
						qty = qtyExp;
						found = true;
					}
				}
				myWriter.write(qty + line+System.lineSeparator());
				myReaderExport.close();
				
			}
			myReaderOrderDat.close();
			myWriter.close();
			Convert.convertToXls(path);
			
		} catch (Exception e) {
			System.out.println("Es ist ein Fehler aufgetreten:\n");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Die Previewsbestellungen wurde in dcorder.xls und marorder.xls in den Ordner " + Config.pathOut() + " geschrieben.");

	}
}
