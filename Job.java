/*
 * The class functions as the constructor for the Object job
 * it holds all the information on a single job
 */

public class Job {
	
	private int arrival;
	private int id;
	private int cpu_time;
	private int time_rem;
	private int currentq;
	private int totalt;
	
	public Job(int arrival, int id, int cpu_time, int time_rem, int currentq, int totalt){
		this.arrival = arrival;
		this.id = id;
		this.cpu_time = cpu_time;
		this.time_rem = time_rem;
		this.currentq = currentq;
		this.totalt = totalt;
	}

	public int getArrival() {
		return arrival;
	}

	public void setArrival(int arrival) {
		this.arrival = arrival;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCpu_time() {
		return cpu_time;
	}

	public void setCpu_time(int cpu_time) {
		this.cpu_time = cpu_time;
	}

	public int getTime_rem() {
		return time_rem;
	}

	public void setTime_rem(int time_rem) {
		this.time_rem = time_rem;
	}

	public int getCurrentq() {
		return currentq;
	}

	public void setCurrentq(int currentq) {
		this.currentq = currentq;
	}

	public int getTotalt() {
		return totalt;
	}

	public void setTotalt(int totalt) {
		this.totalt = totalt;
	}

}