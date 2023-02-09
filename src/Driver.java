import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is the driver class for Program 2
 * 
 * @author Bryce Lehnen
 */
public class Driver {
	
	// Initialize variables
	private static Scanner fco2;	// file for co2
	private static Scanner fsl;		// file for sea level
	private static Scanner ft;		// file for temp
	private static FileOut fout = new FileOut("WorldDataChange.txt");

	/**
	 * Opens the file
	 * Reads the file
	 * Takes the input, ensures that the date is made uniform ie (../../....)
	 * Places the input data into each respective data class
	 * Places that data into the red black tree to be properly sorted (6 total)
	 * 
	 * Once the file has been fully read:
	 * Prints the lowest and highest for each data type to 
	 * the console and writes the same data to an output file
	 * 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		// Creation of 6 RBTs
		// 3 contatin <info, date> and 3 are <date, info>
		RedBlackTree<Double, String> temprbt = new RedBlackTree<Double, String>();
		RedBlackTree<Double, String> searbt = new RedBlackTree<Double, String>();
		RedBlackTree<Double, String> co2rbt = new RedBlackTree<Double, String>();
		
		RedBlackTree<String, Double> dtemp = new RedBlackTree<String, Double>();
		RedBlackTree<String, Double> dsea = new RedBlackTree<String, Double>();
		RedBlackTree<String, Double> dco2 = new RedBlackTree<String, Double>();
		
		// Reading and writing data for the temperature
		// File opening
		try {
			ft = new Scanner(new File("temperature_anomaly.csv"));
		}
		// This will catch any errors in reading the file
		catch (IOException e) {
			System.err.println(e);
		}
		
		String tempdata;
		String[] temparr;
		// File reading
		while (ft.hasNextLine()) {
			tempdata = ft.nextLine();
			temparr = tempdata.split(",");
			
			// Checks if it's world data
			// Much of the data is specific for the northern or southern
			// hemispheres but we only care about world averages
			if (temparr[0].equals("World")) {
				// Sends raw info to the Temperature class to ensure
				// that the information is uniform
				// ie. dates are consistent and temp_anomaly is in F
				Temperature rawtemp = new Temperature(temparr[2], temparr[3]);
				
				// Checks to see if the info is already in the rbt
				// as there is a possiblity of repeats
				if (!dtemp.Contains(rawtemp.tempDate())) {
					temprbt.insert(rawtemp.tempInfo(), rawtemp.tempDate());
					dtemp.insert(rawtemp.tempDate(), rawtemp.tempInfo());
				}
			}
		}
		ft.close();
		
		// Reading and writing data for the sea level
		// File opening
		try {
			fsl = new Scanner(new File("sea_level.csv"));
		}
		// This will catch any errors in reading the file
		catch (IOException e) {
			System.err.println(e);
		}
		
		String sldata;
		String[] slarr;
		// File reading
		while (fsl.hasNextLine()) {
			sldata = fsl.nextLine();
			slarr = sldata.split(",");
			
			// Checks if it's world data
			// Avoids the first line this way
			if (slarr[0].equals("World")) {
				// Sends raw info to the SeaLevel class to ensure
				// that information is uniform
				SeaLevel rawsl = new SeaLevel(slarr[2], slarr[3]);
				
				// Checks to see if the info is already in the rbt
				// as there is a possiblity of repeats
				if (!dsea.Contains(rawsl.seaDate())) {
					searbt.insert(rawsl.seaInfo(), rawsl.seaDate());
					dsea.insert(rawsl.seaDate(), rawsl.seaInfo());
				}
			}
		}
		fsl.close();
		
		// Reading and writing data for the co2 levels
		// File reading
		try {
			fco2 = new Scanner(new File("co2.csv"));
		}
		// This will catch any errors in reading the file
		catch (IOException e) {
			System.err.println(e);
		}
		
		String co2data;
		String[] co2arr;
		// File reading
		while (fco2.hasNextLine()) {
			co2data = fco2.nextLine();
			co2arr = co2data.split(",");
			
			// Checks if it's world data
			// Avoids the first line this way
			if (co2arr[0].equals("World")) {
				// Sends raw info to the CO2 class to ensure
				// that the info is uniform
				CO2 rawco2 = new CO2(co2arr[2], co2arr[3]);
				
				// Checks to see if the info is already in the rbt
				// as there is a possibility of repeats
				if (!dco2.Contains(rawco2.co2Date())) {
					co2rbt.insert(rawco2.co2Info(), rawco2.co2Date());
					dco2.insert(rawco2.co2Date(), rawco2.co2Info());
				}
			}
		}
		fco2.close();
		
		// Now that all the date has been inserted into the rbts
		// the mins and maxes of each tree will be printed to the console
		// and written to an output file along with other pieces of info
		// for that date if it exists (ie. if the min for SeaLevel also has
		// info in the other tree's with the same date then those are printed
		// as well)
		// High and low data for temps
		double lowtemp = temprbt.min();
		String dltemp = temprbt.get(lowtemp);
		double hightemp = temprbt.max();
		String dhtemp = temprbt.get(hightemp);
		
		// High and low data for sea level
		double lowsea = searbt.min();
		String dlsea = searbt.get(lowsea);
		double highsea = searbt.max();
		String dhsea = searbt.get(highsea);
		
		// High and low data for co2
		double lowco2 = co2rbt.min();
		String dlco2 = co2rbt.get(lowco2);
		double highco2 = co2rbt.max();
		String dhco2 = co2rbt.get(highco2);
		
		// Printing to console and writing to output file
		// Lowest temperature anomaly
		System.out.println("Lowest temperature anomaly (F): " + lowtemp + " on " + dltemp);
		fout.writer("Lowest temperature anomaly (F): " + lowtemp + " on " + dltemp);
		if (dsea.Contains(dltemp)) {
			System.out.println("On that same date, the average sea level rise was " + dsea.get(dltemp));
			fout.writer("On that same date, the average sea level rise was " + dsea.get(dltemp));
		}
		if (dco2.Contains(dltemp)) {
			System.out.println("On that same date, the average CO2 concentration was " + dco2.get(dltemp));
			fout.writer("On that same date, the average CO2 concentration was " + dco2.get(dltemp));
		}
		
		System.out.println();
		fout.writer("");
		// Highest temperature anomaly
		System.out.println("Highest temperature anomaly (F): " + hightemp + " on " + dhtemp);
		fout.writer("Highest temperature anomaly (F): " + hightemp + " on " + dhtemp);
		if (dsea.Contains(dhtemp)) {
			System.out.println("On that same date, the average sea level rise was " + dsea.get(dhtemp));
			fout.writer("On that same date, the average sea level rise was " + dsea.get(dhtemp));
		}
		if (dco2.Contains(dhtemp)) {
			System.out.println("On that same date, the average CO2 concentration was " + dco2.get(dhtemp));
			fout.writer("On that same date, the average CO2 concentration was " + dco2.get(dhtemp));
		}
		
		System.out.println();
		fout.writer("");
		// Lowest sea level rise
		System.out.println("Lowest sea level rise: " + lowsea + " on " + dlsea);
		fout.writer("Lowest sea level rise: " + lowsea + " on " + dlsea);
		if (dtemp.Contains(dlsea)) {
			System.out.println("On that same date, the temperature anomaly (F) was " + dtemp.get(dlsea));
			fout.writer("On that same date, the temperature anomaly (F) was " + dtemp.get(dlsea));
		}
		if (dco2.Contains(dlsea)) {
			System.out.println("On that same date, the average CO2 concentration was " + dco2.get(dlsea));
			fout.writer("On that same date, the average CO2 concentration was " + dco2.get(dlsea));
		}
		
		System.out.println();
		fout.writer("");
		// Highest sea level rise
		System.out.println("Highest sea level rise: " + highsea + " on " + dhsea);
		fout.writer("Highest sea level rise: " + highsea + " on " + dhsea);
		if (dtemp.Contains(dhsea)) {
			System.out.println("On that same date, the temperature anomaly (F) was " + dtemp.get(dhsea));
			fout.writer("On that same date, the temperature anomaly (F) was " + dtemp.get(dhsea));
		}
		if (dco2.Contains(dhsea)) {
			System.out.println("On that same date, the average CO2 concentration was " + dco2.get(dhsea));
			fout.writer("On that same date, the average CO2 concentration was " + dco2.get(dhsea));
		}
		
		System.out.println();
		fout.writer("");
		// Lowest average co2 concentration
		System.out.println("Lowest average CO2 concentration: " + lowco2 + " on " + dlco2);
		fout.writer("Lowest average CO2 concentration: " + lowco2 + " on " + dlco2);
		if (dtemp.Contains(dlco2)) {
			System.out.println("On that same date, the temperature anomaly (F) was " + dtemp.get(dlco2));
			fout.writer("On that same date, the temperature anomaly (F) was " + dtemp.get(dlco2));
		}
		if (dsea.Contains(dlco2)) {
			System.out.println("On that same date, the average sea level rise was " + dsea.get(dlco2));
			fout.writer("On that same date, the average sea level rise was " + dsea.get(dlco2));
		}
		
		System.out.println();
		fout.writer("");
		// Highest average co2 concentration
		System.out.println("Highest average CO2 concentration: " + highco2 + " on " + dhco2);
		fout.writer("Highest average CO2 concentration: " + highco2 + " on " + dhco2);
		if (dtemp.Contains(dhco2)) {
			System.out.println("On that same date, the temperature anomaly (F) was " + dtemp.get(dhco2));
			fout.writer("On that same date, the temperature anomaly (F) was " + dtemp.get(dhco2));
		}
		if (dsea.Contains(dhco2)) {
			System.out.println("On that same date, the average sea level rise was " + dsea.get(dhco2));
			fout.writer("On that same date, the average sea level rise was " + dsea.get(dhco2));
		}
	}
}






