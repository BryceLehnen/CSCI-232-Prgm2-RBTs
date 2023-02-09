
/**
 * Handles the data for the temperature
 * 
 * @author Bryce Lehnen
 */
public class CO2 {

	// Initialize variables
	private String date;
	private double info;
	
	/**
	 * Constructor method that sets the date and data
	 * 
	 * @param date
	 * @param data
	 */
	public CO2(String d, String i) {
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
	public String co2Date() {
		return date;
	}
	
	/**
	 * Returns the data converted into
	 * a double from a string
	 * 
	 * @return The CO2 data
	 */
	public double co2Info() {
		return info;
	}
}
