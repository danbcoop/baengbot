import java.util.Scanner;


public class UI {

	private static boolean bbCreated = false;
	public static Scanner in = new Scanner(System.in);

	public UI() {}

	public static void clearConsole() {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				new ProcessBuilder("clear").inheritIO().start().waitFor();
		} catch (Exception e) {
			System.out.println("Der Prozess \"clear\"/\"cls\" konnte nicht aufgerufen werden: " + e);
		}
	}

	public static boolean isPrevCode(String code) {
		String s = code.replaceFirst(RegexHelper.previews(), "");
		return s.equals("") ? true : false; 
	}

	public static void menuItem(int i, String s)	{
		System.out.println("\t"+i+") "+s);
	}

	public static void mainMenu()	{
		String mainMenu = "-----------BÄNGBOT Hauptmenü-----------";
		System.out.println(mainMenu);
		menuItem(1,"Lieferschein eintippen");
		menuItem(2,"Import");
		menuItem(3,"Export");
		System.out.println("");
		menuItem(0,"Ende");
		System.out.println("\n\n");
		System.out.print("Eingabe: ");
	}

	static public int selectType() { 
		try {
			System.out.println("Starte PDF-Parser. Bitte warten.");
			Baengbot baengbot = new Baengbot();
			bbCreated = true;
			ComicList cs = new ComicList();

			if (baengbot.run()) {
				clearConsole();
				cs = Baengbot.readComics(cs);
				cs.writeComics();
			}

			System.out.println("\n");

		}catch(Exception e){
			System.out.println("Eintippen fehlgeschlagen: " + e);
		}
		return Integer.MAX_VALUE;
	}

	static public int selectExport() { 
		Export.run();
		return Integer.MAX_VALUE;
	}

	static public int selectImport() { 
		Import.run();
		return Integer.MAX_VALUE;
	}



	public static void main(String[] args) {
		//String prevRegex = "([A-Z]{3})\\d{6,8}";
		clearConsole();
		new Config();	// initialize Config
		int selection = Integer.MAX_VALUE;

		while (selection!=0) {
			mainMenu();
			try {
				selection = in.nextInt();
			} catch (Exception e) {
				System.out.println("Bitte eine Zahl eingaben, um eine der Optionen  zu wählen");
			}

			if (selection == 1) {
				selection = selectType();
			}
			if (selection == 2) {
				selection = selectImport();
			}
			if (selection == 3) {
				selection = selectExport();
			}
		}

		in.close();
		clearConsole();
		System.out.println("BÄNGBOT beendet!");
		if (bbCreated) System.exit(0);
		return;
	}
}

