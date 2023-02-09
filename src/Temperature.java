
/**
 * Handles the data for the temperature
 * 
 * @author Bryce Lehnen
 */
public class Temperature {

	// Initialize variables
	private String date;
	private double info;
	
	/**
	 * Constructor method that sets the date and data
	 * Needs to change celsius to F
	 * 
	 * @param date
	 * @param data
	 */
	public Temperature(String d, String i) {
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
		
		// Converts the info from the .csv into F
		info = Math.round((Double.valueOf(i) * 1.8)*100.0)/100.0;
	}
	
	/**
	 * Returns the date
	 * 
	 * @return The date
	 */
	public String tempDate() {
		return date;
	}
	
	/**
	 * Returns the data converted into
	 * a double from a string
	 * 
	 * @return The temperature data
	 */
	public double tempInfo() {
		return info;
	}
}
