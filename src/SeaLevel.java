
/**
 * Handles the data for the sea level
 * 
 * @author Bryce Lehnen
 */
public class SeaLevel {

	// Initialize variables
	private String date;
	private double info;
	
	/**
	 * Constructor method that sets the date and data
	 * 
	 * @param date
	 * @param data
	 */
	public SeaLevel(String d, String i) {
		// Converts the date from the .csv
		// into day/month/year
		if (d.contains("-")) {
			String[] temp = d.split("-");
			date = temp[2] + "/" + temp[1] + "/" + temp[0];
		}
		else if (d.contains("/")) {
			String[] temp = d.split("/");
			date = temp[1] + "/" + temp[0] + "/" + temp[2];
		}
		
		// Converts the info from the .csv to a double
		info = Math.round(Double.valueOf(i)*100.0)/100.0;
	}
	
	/**
	 * Returns the date
	 * 
	 * @return The date
	 */
	public String seaDate() {
		return date;
	}
	
	/**
	 * Returns the data converted into
	 * a double from a string
	 * 
	 * @return The sea level data
	 */
	public double seaInfo() {
		return info;
	}
}
