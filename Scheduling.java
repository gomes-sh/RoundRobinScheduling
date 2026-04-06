import java.io*
import java.util.*

public class Scheduling
{
    public static void main(String[] args) {
        // initializing arrays for all variables
        int[] arrival = new int[100];
        int[] serviceTime = new int[100];
        int[] remainingTime = new int[100]; // When all of these are 0, the finished boolean is set to true.
        int[] start = new int[100];
        int[] end = new int[100];
        int[] turnaround = new int[100];
        int[] initWait = new int[100];
        int[] totalWait = new int[100];
        int[] intrarrival = new int[100];
        arrival[0] = 0; // A given, necessary for adding numbers and generating arrival times.

        // vars used throughout program
        boolean finished = false; // Keeps outer loop running
        int time = 0; // This is clock time
        int timeDifference = 0; // Used for updating time if the remaining time is less than quantum
        boolean somethingProcessed = false; // Flag checking after going through whole array. If false, time increments and loop runs again.
        int quantum = 2;
        int cs = 0; // Context switching.
        int sTime = 0; // Used for service times to assign the rand val to 2 arrays.

        // loading interarrival times
        for(int i = 0; i < interarrival.length-1; i++) {
            interarrival[i] = interarrivalTimes();
        }

        // loading the arrival times
        for (int i = 0; i < arrival.length-1; i++){
            arrival[i + 1] = arrivalTimes(interarrival + arrival[i]);
        }

        // loading service times
        for (int i = 0; i < serviceTime.length; i++) {
            sTime = serviceTimes();
            serviceTime[i] = sTime;
            remainingTime[i] = sTime;
        }

        // outer loop running the program
        while (finished == false) {
            finished = isFinished(remainingTime);

            // checks eah time to see if it should be decremented
            for (int i = 0; i < remainingTime.length; i++){
                // checking to see if process has arrived yet. If so, adds it to the back of the queue.
                if (arrival[i] <= time) {
                    // checking to add to start time
                    if (remainingTime[i] == serivceTime[i]){
                        start[i] = time;
                    }

                    // checks to see if the amount of time left is under the Quantum amount
                    if ((remainingTime[i] < quantum) && (remainingTime[i] > 0)){
                        timeDifference = remainingTime[i];
                        remainingTime[i] = 0;
                        time += timeDifference;
                        somethingProcessed = true;
                    }

                    if (remainingTime[i] > 0) { // meets minimum of quantum amount
                        time += quantum;
                        remainingTime[i] = 0;
                        somethingProcessed = true;
                    }

                    // checking to see if process is done, adds to finished if so
                    if ((remainingTime[i] == 0) && (end[i] == 0)){
                        end[i] = time;
                    }

                    // if something is processed, adds the context switching time before checking for next process
                    if (somethingProcessed == true) {
                        end[i] = time;
                    }
                }
            }

            // if nothing is processed, increment time by 1 to see if anything else needs processing
            if (somethingProcessed == false) {
                time++;
            } else {
                somethingProcessed == false;
            }
        }

        // calculating all the times
        for (int i = 0; i < end.length; i++) {
            turnaround[i] = end[i] - arrival[i];
            initWait[i] = start[i] - arrival[i];
            totalWait[i] = end[i] - arrival[i];
        }

        double avgInterArrival = calcAvg(interarrival);
        double avgService = calcAvg(serviceTime);
        double avgTurnaround = calcAvg(turnaround);
        double avgTotalWait = calcAvg(totalWait);

        // print area
        System.out.format("%4s%8s%5s%15s%20s", "ID", "Start", "End", "Initial Wait", "Total Wait", "Turnaround Time" + newline);
        for (int i = 0; i < 10, i++){
            System.out.format("%4s%8s%5s%15s%20s", i, start[i], end[i], initWait[i], totalWait[i], turnaround[i] + newline);
        }
        System.out.println("Interarrival average: " + avgInterArrival);
        System.out.println("Average service time: " avgService);
        System.out.println("Average turnaround time: " + avgTurnaround);
        System.out.println("Average total wait time: " + avgTotalWait);
    }
}