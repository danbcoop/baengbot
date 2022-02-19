import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class ComicList {
	public TreeMap<String, Comic> comics;
	static final String filename = "comics.csv";
	
	public ComicList() {
		this.comics = new TreeMap<String, Comic>();
		
		try {
			String path = Paths.get(filename).toString();
			File myObj = new File(path);
			Scanner myReader = new Scanner(myObj);
			myReader.useDelimiter(System.lineSeparator());
			String regex = "(.+);(\\d+);(.+);(.+)";
			
			while (myReader.hasNext()) {
				String line = myReader.next();
				String name = line.replaceFirst(regex, "$1");
				int iss = Integer.parseInt(line.replaceFirst(regex, "$2"));
				String code = line.replaceFirst(regex, "$3");
				String delivery = line.replaceFirst(regex, "$4");
				Comic comic = new Comic(name, iss,code, delivery);
				comics.put(name, comic);
			}
			myReader.close();
			
		} catch (Exception e) {
			
		}
		
	}
	
	public Set<Map.Entry<String,Comic>> entrySet() {
		return this.comics.entrySet();
	}
	
	public void writeComics() {
		boolean append = false;
		try {
	         FileWriter myWriter = new FileWriter(filename,append);
	         for (Map.Entry<String, Comic> c : comics.entrySet()) {
				 myWriter.write(c.getValue().toString()); 
			 } 
	         myWriter.close();
	         System.out.println("Neue Comics wurden in " + filename + " gespeichert.");
	      } catch (IOException e) {
	         System.out.println("Fehler beim Schreiben in " + filename + ".");
	         e.printStackTrace();
	      }
	}
}
