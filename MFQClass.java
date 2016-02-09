import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;


public class MFQClass {
	
	private PrintWriter pw;
	public MFQClass(PrintWriter pw){
		this.pw = pw;
	}
	
	CPU cpu = new CPU();
	int clock = 0;
	int sum;
	int sum1;
	int idletime;
	int totalA;
	int totaltime = 0;
	boolean done;
	int wait = 0;
	ObjectQueue input = new ObjectQueue();
	ObjectQueue queue1 = new ObjectQueue();
	ObjectQueue queue2 = new ObjectQueue();
	ObjectQueue queue3 = new ObjectQueue();
	ObjectQueue queue4 = new ObjectQueue();
	ObjectQueue output = new ObjectQueue();
	
	public static ObjectQueue getjobs() throws IOException{
		Scanner fileScan = new Scanner (new File("mfq.txt"));
		String buf;
		Job job;
		ObjectQueue inputq = new ObjectQueue();	
		while(fileScan.hasNext()){
			buf = fileScan.nextLine();
			String delims = "[ ]+";
			String [] tokens = buf.split(delims);
			job = new Job(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Integer.valueOf(tokens[2]), 1, 0);
			inputq.insert(job);
		}
		fileScan.close();
		return inputq;
		}
	
	public void OutputHeader(){
		System.out.println("Event, System Time, PID, CPU Time Needed, Total Time in System, Lowest Level Queue");
		pw.println("Event, SystemTime, PID, CPU, T_t, LLQ");
	}
	
	public void PrintA(Job job){
		System.out.println("Arrival     "+job.getArrival()+"     "+job.getId()+"     "+job.getCpu_time());
		pw.println("Arrival     "+job.getArrival()+"      "+job.getId()+"  "+job.getCpu_time());
	}
	
	public void BumpJob(Job job){
		System.out.println("The bump job is running for: "+job.getId());
		if(job.getCurrentq() == 1){
			queue1.remove();
			queue2.insert(job);
			job.setCurrentq(2);
			CPU.setJob(null);
		}
		else if(job.getCurrentq() == 2){
			queue2.remove();
			queue3.insert(job);
			job.setCurrentq(3);
			CPU.setJob(null);
		}
		else if(job.getCurrentq() == 3){
			queue3.remove();
			queue4.insert(job);
			job.setCurrentq(4);
			CPU.setJob(null);
		}
		else if(job.getCurrentq() == 4){
			queue4.remove();
			queue4.insert(job);
			job.setCurrentq(4);
			CPU.setJob(null);
		}
	}
	
	public int CurrentQtime(Job job){
		if((CPU.getJob().getCurrentq()==1)){
			return 2;
		}
		else if((CPU.getJob().getCurrentq()==2)){
			return 4;
		}
		else if((CPU.getJob().getCurrentq()==3)){
			return 8;
		}
		else{
			return 16;
		}
	}
	
	public void CompleteJob(Job job, int count){
		if(job.getCurrentq() == 1){
			job.setTotalt((count-job.getArrival()));
			System.out.println("Departure... "+" System Time: " + count + " PID: "+job.getId()+" CPU Time Needed: "+job.getCpu_time()+" Total Time in System: "+(count-job.getArrival())+" LLQ: "+job.getCurrentq());
			pw.println("Departure   " + count + "      "+job.getId()+"  "+job.getCpu_time()+"   "+(count-job.getArrival())+"    "+job.getCurrentq());
			output.insert(queue1.remove());
			CPU.setJob(null);
		}
		else if(job.getCurrentq() == 2){
			job.setTotalt((count-job.getArrival()));
			System.out.println("Departure... "+" System Time: " + count + " PID: "+job.getId()+" CPU Time Needed: "+job.getCpu_time()+" Total Time in System: "+(count-job.getArrival())+" LLQ: "+job.getCurrentq());
			pw.println("Departure   " + count + "      "+job.getId()+"  "+job.getCpu_time()+"   "+(count-job.getArrival())+"    "+job.getCurrentq());
			output.insert(queue2.remove());
			CPU.setJob(null);
		}
		else if(job.getCurrentq() == 3){
			job.setTotalt((count-job.getArrival()));
			System.out.println("Departure... "+" System Time: " + count + " PID: "+job.getId()+" CPU Time Needed: "+job.getCpu_time()+" Total Time in System: "+(count-job.getArrival())+" LLQ: "+job.getCurrentq());
			pw.println("Departure   " + count + "      "+job.getId()+"  "+job.getCpu_time()+"   "+(count-job.getArrival())+"    "+job.getCurrentq());
			output.insert(queue3.remove());
			CPU.setJob(null);
		}
		else if(job.getCurrentq() == 4){
			job.setTotalt((count-job.getArrival()));
			System.out.println("Departure... "+" System Time: " + count + " PID: "+job.getId()+" CPU Time Needed: "+job.getCpu_time()+" Total Time in System: "+(count-job.getArrival())+" LLQ: "+job.getCurrentq());
			pw.println("Departure   " + count + "      "+job.getId()+"  "+job.getCpu_time()+"   "+(count-job.getArrival())+"    "+job.getCurrentq());
			output.insert(queue4.remove());
			CPU.setJob(null);
		}
	}
	
	public void RunSimulation() throws IOException{
		input = getjobs();
		done = false;
		while(!done){
			System.out.println("clock: "+clock);
			System.out.println("Busy? "+CPU.BusyFlag());
			//check for new Arrival
			if (!input.isEmpty()){
				if( ((Job) input.query()).getArrival() == clock){
					queue1.insert(input.remove());
					PrintA((Job) queue1.query());
					System.out.println("Job at front of q1: "+((Job) queue1.query()).getId()+" Enters at: "+ clock);
				}
			}

			if(!CPU.BusyFlag()){
				if(!queue1.isEmpty()){
					CPU.setJob((Job) queue1.query());
				}
				else if(!queue2.isEmpty()){
					CPU.setJob((Job) queue2.query());
				}
				else if(!queue3.isEmpty()){
					CPU.setJob((Job) queue3.query());
				}
				else if(!queue4.isEmpty()){
					CPU.setJob((Job) queue4.query());
				}
				else{
					idletime++;
				}
			}
			else{
				CPU.getJob().setTime_rem(CPU.getJob().getTime_rem() - 1);
				sum1 = 0;
				for(int i = 1; i<=CPU.getJob().getCurrentq()-1; i++){
					sum1 += Math.pow(2.0, i);
				}
				sum = sum1;
				System.out.println("Job " + CPU.getJob().getId() + " Time at previous Queues: " + (sum));
				System.out.println("Job " + CPU.getJob().getId() + " Time at Queue: "+ CPU.getJob().getCurrentq() + " : " + ((CPU.getJob().getCpu_time() - CPU.getJob().getTime_rem()) - (sum)));
				System.out.println("Job " + CPU.getJob().getId() + " Time remains: "+CPU.getJob().getTime_rem());
				if(CPU.getJob().getTime_rem() == 0){
					CompleteJob(CPU.getJob(), clock);
					totaltime += ((Job) output.query()).getTotalt();
					wait += ((Job) output.query()).getTotalt() - ((Job) output.query()).getCpu_time();
					if(!queue1.isEmpty()){
						CPU.setJob((Job) queue1.query());
					}
					else if(!queue2.isEmpty()){
						CPU.setJob((Job) queue2.query());
					}
					else if(!queue3.isEmpty()){
						CPU.setJob((Job) queue3.query());
					}
					else if(!queue4.isEmpty()){
						CPU.setJob((Job) queue4.query());
					}
				}
				else if(((CPU.getJob().getCpu_time() - CPU.getJob().getTime_rem()) - sum == CurrentQtime(CPU.getJob()))){
					BumpJob(CPU.getJob());
					if(!queue1.isEmpty()){
						CPU.setJob((Job) queue1.query());
					}
					else if(!queue2.isEmpty()){
						CPU.setJob((Job) queue2.query());
					}
					else if(!queue3.isEmpty()){
						CPU.setJob((Job) queue3.query());
					}
					else if(!queue4.isEmpty()){
						CPU.setJob((Job) queue4.query());
					}
				}
			}
			clock++;
			if (input.isEmpty() && queue1.isEmpty() && queue2.isEmpty() && queue3.isEmpty() && queue4.isEmpty() ){
				done = true;
			}
		}
		
	}
	
	public void OutputStats(){
		pw.println(" ");
		pw.println("KEY: Event, SystemTime, PID = Process ID, CPU = CPU Time Needed, T_t = Total Time in System, LLQ = Lowest Level Queue");
		pw.println(" ");
		pw.println("Stats:");
	 	DecimalFormat decFor = new DecimalFormat("0.00");
		//Total Number of Jobs
		System.out.println("Total Number of Jobs: " + 16);
		pw.println("Total Number of Jobs: " + 16);
		//Total time of all jobs in the system, add up all the output_count-arrival times
		System.out.println("The total time of all the jobs in the system is: "+totaltime);
		pw.println("The total time of all the jobs in the system is: "+totaltime);
		//Average Response Time, to 2 decimal places 
        System.out.println("The average response time is: "+0.00+" seconds");
        pw.println("The average response time is: "+0.00+" seconds");
		//Average Turnaround Time
        double averagett = totaltime/16;
        System.out.println("The average turnaround time is: "+ decFor.format(averagett));
        pw.println("The average turnaround time is: "+ decFor.format(averagett));
		//Average waiting time, time in system-CPU time
        double avwait = wait/16;
        System.out.println("The average waiting time is: "+ decFor.format(avwait));
        pw.println("The average waiting time is: "+ decFor.format(avwait));
		//Average throughput, 16/total time:
		double averagetp = 16/totaltime;
		System.out.println("The average throughput is: " + decFor.format(averagetp));
		pw.println("The average throughput is: " + decFor.format(averagetp));
		//CPU idle time
		System.out.println("The total idle time of the CPU is: " + idletime);
		pw.println("The total idle time of the CPU is: " + idletime);
	}

}