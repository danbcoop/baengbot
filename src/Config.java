public class Config {
  public Config() {}

  public string begin() {
    String begin = "";
   	try {
		  String path = Paths.get("config.cfg").toString();
		  File myObj = new File(path);
		  Scanner myReader = new Scanner(myObj);
		  myReader.useDelimiter(System.lineSeparator());
		  while (myReader.hasNext()) {
			  String line = myReader.next();
			  if (line.indexOf("begin")!=-1) {
				  startString = line.replaceFirst(".+=(.+)","$1");
			  }
		  }
		  myReader.close();
		
		} catch (Exception e) {
      System.out.println("Fehler beim Lesen der Config Datei: " + e;
    }
	
    return begin;
  }
}
