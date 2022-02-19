//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import java.util.LinkedList;;

public class Order {
	private String code;
	private String title;
	private LinkedList<Customer> customers;
	
	
	public Order(String code, String title) {
		this.code = code.replaceFirst("([A-Z]{3}\\d{6})\\w","$1");
		this.title = title;
		this.customers = new LinkedList<Customer>();
	}
	
	
	
	public void add(Customer customer) {
		customers.add(customer);
	}
	
	public String getTitle() {
		return title;
	}
	public String getCode() {
		return code;
	}
	public int qty() {
		return this.customers.size();
	}
	

	public String toString() {
		return this.qty() + ";" + this.code + ";" + this.title + ";"  + this.customers + System.lineSeparator(); 
	}
	
	
}
