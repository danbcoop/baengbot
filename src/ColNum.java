final public class ColNum {
	public int code;
	public int price;
	public int title;
	public int issue;

	public ColNum(String line) {
		
		String[] columns = line.split(";");
		int i = 0;
		for (String column : columns) {
			if (column.equals(Config.marCode()) ||  column.equals(Config.dcCode())  ||  column.equals(Config.diaCode())) 
				code = i; 
			else if (column.equals(Config.marPrice()) ||  column.equals(Config.dcPrice()) ||  column.equals(Config.diaPrice())) 
				price = i;
			else if (column.equals(Config.marTitle()) ||  column.equals(Config.dcTitle())  ||  column.equals(Config.diaTitle()))
				title = i;
			else if (column.equals(Config.marIssue()) ||  column.equals(Config.dcIssue())  ||  column.equals(Config.diaIssue()))
				issue = i;

			i++;  
		}
	}
} 

