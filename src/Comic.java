import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Comic {
	private int issue;
	private String name;
	private LocalDate date;
	private String code;
	
	public Comic(String name, int issue, String code) {
		this.name = name;
		this.issue = issue;
		this.code = code;
		this.date = LocalDate.now();
	}
	
	public Comic(String name, int issue, String code, String date) {
		this.name = name;
		this.issue = issue;
		this.code = code;
		this.date = LocalDate.parse(date,DateTimeFormatter.ISO_DATE);
	}
	
	public String getName() {
		return name;
	}
	
	public int getIssue() {
		return issue;
	}
	
	public LocalDate getdate() {
		return date;
	}
	
	public String toString() {
		return this.name + ";" + this.issue + ";" + this.code + ";" + this.date.format(DateTimeFormatter.ISO_DATE) + System.lineSeparator(); 
	}
	
	
}
