import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class OrderList {
	public TreeMap<String, Order> orders;
	private  boolean isStatus;
	static final String filenameStatus = "status.csv";
	static final String filenameReorder = "reoder.csv";

	public OrderList(boolean isStatus) {
		this.isStatus = isStatus;
		this.orders = new TreeMap<String, Order>();
		if (isStatus) {
			try {
				String path = Paths.get(filenameStatus).toString();
				File myObj = new File(path);
				Scanner myReader = new Scanner(myObj);
				myReader.useDelimiter(System.lineSeparator());
				String regex = "(\\d+);(.+);(.+);\\[(.+)\\]";
				String regexCust = "([BB\\d]+)/(^/+)/(^/+)/(R\\[.*\\])/(.*)(,\\s)*";
				regexCust = "\\s*(\\w+)/(\\w+)/([\\d-]+)/R\\[(.*)\\]/(.*)";
				//$1: qty; $2 code; $3 title; $4 customers
				while (myReader.hasNext()) {
					String line = myReader.next();
//					System.out.println("Line: " + line);
					String code = line.replaceFirst(regex, "$2");
					String title = line.replaceFirst(regex, "$3");
					Order o = new Order(code, title);
					String customerStr = line.replaceFirst(regex, "$4");
					String[] customers = customerStr.split(",");
					for (int i = 0; i < customers.length; i++)
					{
//						System.out.println("Code: " +code + "\n Title: "+title+ "\n custStr: "+ customerStr);
						String kundennr = customers[i].replaceFirst(regexCust, "$1");
						String type = customers[i].replaceFirst(regexCust, "$2");
						String orderDate = customers[i].replaceFirst(regexCust, "$3");
						String queryDate = customers[i].replaceFirst(regexCust, "$4");
						String comment = customers[i].replaceFirst(regexCust, "$5");
						Customer customer = new Customer(kundennr,type,orderDate,queryDate,comment);
						try {
							o.add(customer);
						} catch (Exception e) {
							System.out.println(e);
						}
					
					}
					orders.put(o.getCode(),o);
										
				}
				myReader.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public Set<Map.Entry<String,Order>> entrySet() {
		return this.orders.entrySet();
	}

	public void writeOrder() {
		if (isStatus) {
			boolean append = false;
			try {
				FileWriter myWriter = new FileWriter(filenameStatus,append);
				for (Map.Entry<String, Order> c : orders.entrySet()) {
					myWriter.write(c.getValue().toString()); 
				} 
				myWriter.close();
				System.out.println("Die Bestellungen wurden für unsere Übersicht in " + filenameStatus + " gespeichert.");
			} catch (IOException e) {
				System.out.println("Fehler beim Schreiben in " + filenameStatus + ".");
				e.printStackTrace();
			}
		} else {
			boolean append = true;
			try {
				FileWriter myWriter = new FileWriter(filenameReorder,append);
				for (Map.Entry<String, Order> c : orders.entrySet()) {
					Order o = c.getValue();
					String s = o.qty() + ";" + o.getCode() + ";" + o.getTitle() + ";"+System.lineSeparator();
					myWriter.write(s);
				} 
				myWriter.close();
				System.out.println("Die Reorder wurde in " + filenameReorder + " gespeichert.");
			} catch (IOException e) {
				System.out.println("Fehler beim Schreiben in " + filenameReorder + ".");
				e.printStackTrace();
			}
		}
	}
}
