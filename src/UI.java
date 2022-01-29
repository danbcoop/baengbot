import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.util.*;

public class UI {
	//	private final static boolean isLinux = System.getProperty("os.name").contains("Windows")? true : false;
	private final static boolean isWindows = System.getProperty("os.name").contains("Windows")? true : false;

	public UsrInt() {

	}

	//	public static ProcessBuilder consoleClearer()	{
	//	    try {
	//	    	String clear;
	//	    	if (isWindows) {
	//	            clear = "cls";
	//	        } else {
	//	            clear="clear";
	//	        }
	//	    	ProcessBuilder builder = new ProcessBuilder(clear);
	//	    	builder.redirectErrorStream(true);
	//	    	builder.inheritIO();
	//	    	return builder;
	//	        
	//	    } catch ( Exception e) {
	//	        //  Handle any exceptions.
	//	    	return null;
	//	    }
	//	}
	//	
	public static void clearConsole() {
		try {
			if (isWindows) 
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				new ProcessBuilder("clear").inheritIO().start().waitFor();
		} catch (Exception e) {

		}
	}
	
	public static boolean isPrevCode(String code) {
		String s = code.replaceFirst("([A-Z]{3}\\d{6})\\w*","");
		return s.equals("") ? true : false; 
	}

	public static void menuItem(int i, String s)	{
		System.out.println("\t"+i+") "+s);
	}

	public static void mainMenu()	{
		String mainMenu = "-----------BÄNGBOT Hauptmenü-----------";
		System.out.println(mainMenu);
		menuItem(1,"Lieferschein eintippen");
		menuItem(2,"Reorder anlegen");
		menuItem(3,"Reklamation");
		menuItem(4,"Import");
		menuItem(5,"Export");
		System.out.println("");
		menuItem(0,"Ende");
		System.out.println("\n\n");
		System.out.print("Eingabe: ");
	}

	public static void main(String[] args) {

		String prevRegex = "([A-Z]{3})\\d{6,8}";
		boolean createdBB = false;
		clearConsole();
		
		int selection = 69;
		Scanner in = new Scanner(System.in);
		while (selection!=0) {
			mainMenu();
			try {
				selection = in.nextInt();
			} catch (Exception e) {
				System.out.println("Bitte eine Zahl eingaben, um eine der Optionen  zu wählen");
			}

			if (selection == 1) {
				selection = 69;
				try {
					System.out.println("Starte PDF-Parser. Bitte warten.");
					Baengbot baengbot = new Baengbot();
					createdBB=true;
					ComicList cs = new ComicList();
					String pdf = Baengbot.readPdf();
					clearConsole();
					cs = Baengbot.readComics(cs, pdf);
					cs.writeComics();
					System.out.println("ModernG-Fenster wird gesucht...");
					baengbot.run(pdf);
					System.out.println("\n");
					mainMenu();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
			if (selection == 4) {
				selection = 69;
			
				Export.run();
				selection = 69;
			}
			
			if (selection == 2) {
				clearConsole();
				OrderList reorders = new OrderList(false);
				OrderList orders = new OrderList(true);
				while(selection == 2) {
					try {
						System.out.println("Wie soll die neue Bestellung angelegt werden?");
						menuItem(1,"Titelsuche in True-List");
						menuItem(2,"Titelsuche in Lunar-Liste");
						menuItem(3,"Titelsuche über Produktcode");
						System.out.println("");
						menuItem(0,"Zurück");
						System.out.print("Eingabe: ");
						int subSelection = in.nextInt();
						String code="";
						String title="";
						
						if (subSelection==0) {
							selection = 69;
							clearConsole();
							break;
						}

						if (subSelection == 1) {
							
								in.nextLine();
								System.out.println("Bitte Titel eingeben. (Beenden mit \"0\")");

								System.out.print("Eingabe: ");

								String s = in.nextLine().toUpperCase();
								if (s.equals("0")) {
									subSelection = 69;
									continue;
								}
								String[][] res = TruReader.search(s);
								System.out.println("Bitte Titel wählen oder abbrechen (0).");
								System.out.print("Eingabe: ");
								int i = in.nextInt()-1;
								if (i!=-1) {
									System.out.println("-> "+res[i][0]+" "+res[i][1]+".");
									title = res[i][1];
									code = res[i][0];
								} else {
									continue;
								}

							
						}

						if (subSelection == 3) {
							System.out.println("Bitte Code eingeben. (Beenden mit \"0\")");
							System.out.print("Eingabe: ");
							code = in.next().toUpperCase();
							if (code.equals("0")) {
								subSelection = 69;
								continue;
							}
							try {
								System.out.println("Suche...");
								Pattern pattern = Pattern.compile(prevRegex);
								Matcher matcher = pattern.matcher(code);

								if(matcher.find()) {
//									System.out.println("Titel: " + Webscraper.searchCode(code));
								}
								System.out.println("Titel: " + Webscraper.searchCode(code));
								title = Webscraper.searchCode(code);
							} catch (Exception e) {
								System.out.println("Suche erfolglos.");
							}
						}
						System.out.println("Bitte Kundennummer eingeben. (Beenden mit \"0\")");
						System.out.print("Eingabe: ");
						String kundennr = in.next();
						if (kundennr.equals("0")) {
							subSelection = 69;
							continue;
						}
						System.out.println("Bitte Bestellart eingeben. (B/KB/NA) (Beenden mit \"0\")");
						System.out.print("Eingabe: ");
						String type = in.next();
						if (type.equals("0")) {
							subSelection = 69;
							continue;
						}
						Order order = new Order (code.toUpperCase(),title);
						Order reOrder = new Order (code.toUpperCase(),title);
						
						Customer customer = new Customer(kundennr,type);
						System.out.println(customer);
						try {
							order.add(customer);
							reOrder.add(customer);
						} catch (Exception e) {
							System.out.println(e);
						}

						Order oldOrder = orders.orders.put(order.getCode(),order);
						if (oldOrder!=null) {
							oldOrder.add(customer);
							orders.orders.put(order.getCode(),oldOrder);
						}
						oldOrder = reorders.orders.put(reOrder.getCode(),reOrder);
						if (oldOrder!=null) {
							oldOrder.add(customer);
							reorders.orders.put(order.getCode(),oldOrder);
						}

						System.out.println("Bestellung angelegt: " + order);
						
						Set mapset = orders.entrySet();
						System.out.println("Checking value");
						System.out.println("Entry set values: "+mapset);


					} catch (Exception e) {

					}
				}
				orders.writeOrder();
				reorders.writeOrder();
			}

			if (selection == 3) {
				selection = 69;
				System.out.println("Bitte wähle eine Reklamationsart!");
				menuItem(1,"Reklamation zur aktuellen Lieferung");
				menuItem(2,"Reklamiere alle Bestellungen, die älter als 3 Wochen sind.");
				System.out.println("");
				menuItem(0,"Zurück");
				int subSelection = in.nextInt();
				if (subSelection==0) {
						selection = 69;
						clearConsole();
						break;
				}
				
				while (subSelection == 1) {
					System.out.println("Bitte Itemcode eingeben. (Abbrechen mit \"0\")");
					System.out.print("Eingabe: ");
					String code = in.next();
					if (code.equals("0")) {
						subSelection = 69;
						clearConsole();
						break;
					}
					String title ="";
					if (isPrevCode(code)) {
						try {
							System.out.println("Suche auf previewsworld.com...");
							Pattern pattern = Pattern.compile(prevRegex);
							Matcher matcher = pattern.matcher(code);

							if(matcher.find()) {
								System.out.println("Titel: " + Webscraper.searchCode(code));
							}
							title = Webscraper.searchCode(code);
						} catch (Exception e) {
							System.out.println("Code nicht gefunden!");
							System.out.println("Bitten den Titel manuell eingeben:");
							System.out.print("Eingabe: ");
							title = in.next();
						}
					} else {
						System.out.println("nicht prevcode");
					}
					System.out.println("Bitten die Kundennummer eingeben. (Abbrechen mit \"0\")");
					String kundennr = in.next();
					if (kundennr.equals("0")) {
						subSelection = 69;
						break;
					}
					
					
					
				}
					
				


			}
			

		}
		in.close();
		clearConsole();
		System.out.println("BÄNGBOT beendet!");
		if (createdBB) System.exit(0);
		return;
	}
}
