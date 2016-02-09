/*
 * This class holds the necessary components/functions of the CPU
 * like getJob and setJob which pull and push information from and to the CPU
 * the BusyFlag method is used to determine whether or not the CPU is busy within the simulation
 */

public class CPU {
	
	private static Job job;
	
	public CPU(){
		job = null;
	}

	public static Job getJob() {
		return job;
	}

	public static void setJob(Job jb) {
		job = jb;
	}
	
	public static Job removeJob(){
		Job temp;
		temp = job;
		job = null;
		return temp;
	}
	
	public static boolean BusyFlag(){
		if(job!= null){
			return true;
		}
		else{
			return false;
		}
	}
	

}
