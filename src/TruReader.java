import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.LinkedList;

public class TruReader {
	static public String[][] search (String s) {

		LinkedList<String> titles = new LinkedList<String>();
		LinkedList<String> codes = new LinkedList<String>();
		File file = new File(".");
		String[] filenames = file.list();
		String filename = "";
		for (String f:filenames) {
			if (f.indexOf("truall.txt")!=-1) {
				filename = f;
				break;
			}
		}
		System.out.println(filename);
		try {
			String path = Paths.get(filename).toString();
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			myReader.useDelimiter(System.lineSeparator());
			String regex = ".+([A-Z]{3}\\d{6}[A-Z]{0,1})\\s+(.*"+s+".*)(\\s\\d+\\.\\d+).*";
			int i = 1;
			while (myReader.hasNext()) {
				
				String line = myReader.next();
				if (line.indexOf(s)!=-1) {
					codes.add(line.replaceFirst(regex,"$1").replaceFirst("(\\S+)\\s+", "$1"));
					titles.add(line.replaceFirst(regex,"$2").replaceFirst("(\\S+)\\s+((\\r\\n)|\\n|\\r)", "$1"));
					System.out.println(i+") "+line.replaceFirst(regex,"$2").replaceFirst("(\\S+)\\s+((\\r\\n)|\\n|\\r)", "$1")
							+ "\t(" + line.replaceFirst(regex,"$1").replaceFirst("(\\S+)\\s+", "$1") + ")\t$"
							+ line.replaceFirst(regex,"$3"));
					i++;
				}
//				String name = line.replaceFirst(regex, "$2");
//				int iss = Integer.parseInt(line.replaceFirst(regex, "$2"));
//				String code = line.replaceFirst(regex, "$3");
//				String delivery = line.replaceFirst(regex, "$4");
//				Comic comic = new Comic(name, iss,code, delivery);
//				comics.put(name, comic);
			}
			myReader.close();
			
		} catch (Exception e) {
			
		}
		
		String[][] res = new String[codes.size()][2];
		for (int i = 0; i < codes.size(); i++) {
			res[i][0] = codes.get(i);
			res[i][1] = titles.get(i);
		}
		return res;
	}

}
