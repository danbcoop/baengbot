import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Customer {
	protected String name;
	protected String type;
	protected String comment;
	protected LocalDate dateOrder;
	protected LinkedList<LocalDate> dateQuery;
	
	
	public Customer(String name, String type) {
		this.name = name;
		this.type = type;
		this.comment = "";
		this.dateOrder = LocalDate.now();
		this.dateQuery = new LinkedList<LocalDate>();
	}
	
	public Customer(String name, String type, String dateOrder, String dateQuery, String comment) {
//		System.out.println("name: " +name + "\n Type: "+type+ "\n date:"+ dateOrder);
		this.name = name;
		this.type = type;
		this.comment = comment;
		this.dateOrder = LocalDate.parse(dateOrder,DateTimeFormatter.ISO_DATE);
		this.dateQuery = new LinkedList<LocalDate>();
		
	}
	
	public String toString() {
		return name + "/" + type + "/" + this.dateOrder.format(DateTimeFormatter.ISO_DATE) +"/R" + this.dateQuery + "/" + comment;
		
	}
	
	public LocalDate getOrderDate () {
		return this.dateOrder;
	}
	
	public String orderDate () {
		return this.dateOrder.format(DateTimeFormatter.ISO_DATE);
	}
	
	public LinkedList<LocalDate> getQueryDate () {
		return this.dateQuery;
	}
	
	

}
