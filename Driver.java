import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Kate McNally
 * ID: 011047098
 * Project Title: Multi-Level Feedback Queue Lab
 * Description: Simulates Multi- level feedback queues for a CPU
 * Date: July 15, 2015
 * Begin the project through the driver class
 * Instructions: Run the program and look at the csis.txt file to see the results
 *
 */

public class Driver {

	public static void main(String[] args) throws IOException{
		PrintWriter pw = new PrintWriter(new FileWriter("csis.txt"));
		MFQClass mfq = new MFQClass(pw);
		mfq.OutputHeader();
		mfq.RunSimulation();
		mfq.OutputStats();
		
		pw.close();
	}

}
