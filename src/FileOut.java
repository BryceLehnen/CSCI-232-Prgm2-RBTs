import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Used to create and write to an output file regardless
 * of what is inputed to the writer (ie. int, string, char, etc)
 * 
 * Taken from an old program
 * 
 * @author Bryce Lehnen
 */
public class FileOut {
    PrintStream fout;
    
    // Constructor
    FileOut(String filename) {
    	try {
    		fout = new PrintStream(new FileOutputStream(filename));
    	}
    	catch (IOException fo) {
    		System.out.println(fo);
    	}
    }
    
    // Writer methods that add new line
    public void writer(String out) {
    	fout.println(out);
    }
    public void writer(int out) {
    	fout.println(out);
    }
    public void writer(char out) {
    	fout.println(out);
    }
    public void writer(double out) {
    	fout.println(out);
    }
    public void writer(float out) {
    	fout.println(out);
    }
    
    // Write methods that add onto the same line
    public void write(String out) {
    	fout.print(out);
    }
    public void write(int out) {
    	fout.print(out);
    }
    public void write(char out) {
    	fout.print(out);
    }
    public void write(double out) {
    	fout.print(out);
    }
    public void write(float out) {
    	fout.print(out);
    }
}
