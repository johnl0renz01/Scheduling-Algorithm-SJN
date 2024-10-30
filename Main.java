import java.io.IOException;
import java.lang.Thread;
import java.lang.Integer;
import java.util.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Main {

	public static final String RED_FG = "\u001B[31m";
	public static final String BLUE_FG = "\u001B[34m";
	public static final String GREEN_FG = "\u001B[32m";
	public static final String COLOR_RESET = "\u001B[0m";

	private static final DecimalFormat df = new DecimalFormat("0.00");

	public static void header() throws IOException, InterruptedException {
		new ProcessBuilder("clear").inheritIO().start().waitFor();
		System.out.println("FL-M5: ACT2 - SJN");
		System.out.println("DELA CRUZ, JOHN LORENZ N.\n");
	}

	public static void pressEnterToContinue() {
		System.out.print("\nPress \'Enter\' key to continue...");
		try {
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (Exception e) {
		}
	}

	public static void processList(int processQuantity, String processVariables[]) {
		System.out.print("Process List: ");
		for (int i = 0; i < processQuantity; i++) {
			if (i == (processQuantity - 1)) {
				System.out.println(processVariables[i]);
			} else {
				System.out.print(processVariables[i] + ", ");
			}
		}
	}

	public static void table(int processQuantity, int processArrivalTime[], int processBurstTime[],
			int timelineValues[], int timelineLimit, int timelineCounter, String timelineVariables[],
			String processVariables[]) {

		String completionTime[] = new String[processQuantity];

		boolean idleExist = false;
		if (timelineLimit > processQuantity) {
			idleExist = true;
		}

		for (int i = 0; i < processQuantity; i++) {
			if (idleExist) {
				completionTime[i] = String.valueOf(timelineValues[i + 1]);
			} else {
				completionTime[i] = String.valueOf(timelineValues[i]);
			}
		}

		String sortedCompletionTime[] = new String[processQuantity];

		for (int i = 0; i < processQuantity; i++) {
			sortedCompletionTime[i] = " ";
		}

		for (int i = 0; i < processQuantity; i++) {
			for (int j = 0; j < processQuantity; j++) {
				if (timelineLimit > processQuantity) {
					if (processVariables[i].equals(timelineVariables[j + 1])) {
						sortedCompletionTime[i] = completionTime[j];
						break;
					}
				} else {
					if (processVariables[i].equals(timelineVariables[j])) {
						sortedCompletionTime[i] = completionTime[j];
						break;
					}
				}
			}
		}

		Formatter table = new Formatter();
		table.format("\n%1s%2s%1s%2s%1s%2s%1s", BLUE_FG + "Process" + COLOR_RESET, "|",
				BLUE_FG + "Arrival Time" + COLOR_RESET, "|", GREEN_FG + "Burst Time" + COLOR_RESET, "|",
				BLUE_FG + "Completion Time" + COLOR_RESET + "|");

		for (int i = 0; i < processQuantity; i++) {
			table.format("\n%13s", COLOR_RESET + "P" + (i + 1) + COLOR_RESET);
			table.format("%4s", "|");

			table.format("%15s", COLOR_RESET + processArrivalTime[i] + COLOR_RESET);
			table.format("%7s", "|");
			table.format("%12s", COLOR_RESET + processBurstTime[i] + COLOR_RESET);
			table.format("%8s", "|");

			table.format("%17s", RED_FG + sortedCompletionTime[i] + COLOR_RESET);
			table.format("%8s", "|");
		}
		System.out.println(table);
	}

	public static void timeline(int timelineCounter, int timelineValues[], String timelineVariables[]) {
		System.out.println("\nTimeline:");
		Formatter timeline = new Formatter();

		timeline.format("\n%1s", "| ");
		for (int i = 0; i < timelineCounter; i++) {
			timeline.format("%1s", " " + timelineVariables[i] + "  | ");
		}
		System.out.println(timeline);
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		header();

		int processQuantity = 0;
		int processCounter = 0;
		boolean flag = false;

		boolean validProcessesQuantity = false;
		System.out.print("Enter no. of Processes: ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					processQuantity = input.nextInt();
					if (processQuantity <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.print("Enter no. of Processes: ");
					}
				} while (processQuantity <= 0);
				validProcessesQuantity = true;
			} catch (InputMismatchException ex) {
				validProcessesQuantity = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.print("Enter no. of Processes: ");
			}
		} while (!validProcessesQuantity);

		String processVariables[] = new String[processQuantity];
		for (int i = 0; i < processQuantity; i++) {
			processVariables[i] = "P" + String.valueOf(i + 1);
		}

		String processListVariables[] = new String[processQuantity];
		for (int i = 0; i < processQuantity; i++) {
			processListVariables[i] = "P" + String.valueOf(i + 1);
		}
		int processArrivalTime[] = new int[processQuantity];
		int processBurstTime[] = new int[processQuantity];

		do {
			int arrivalInput = 0;
			int burstInput = 0;
			boolean validArrival = false;
			boolean validBurst = false;
			header();
			System.out.println("Enter no. of Processes: " + processQuantity);
			System.out.println("\nProcess " + (processCounter + 1) + ": ");
			System.out.print("Arrival Time: ");
			do {
				try {
					Scanner input = new Scanner(System.in);
					arrivalInput = input.nextInt();
					if (arrivalInput >= 0) {
						validArrival = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter no. of Processes: " + processQuantity);
						System.out.println("\nProcess " + (processCounter + 1) + ": ");
						System.out.print("Arrival Time: ");
					}
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter no. of Processes: " + processQuantity);
					System.out.println("\nProcess " + (processCounter + 1) + ": ");
					System.out.print("Arrival Time: ");
				}
			} while (!validArrival);

			System.out.print("Burst Time: ");

			do {
				try {
					Scanner input = new Scanner(System.in);
					burstInput = input.nextInt();
					if (burstInput > 0) {
						validBurst = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter no. of Processes: " + processQuantity);
						System.out.println("\nProcess " + (processCounter + 1) + ": ");
						System.out.println("Arrival Time: " + arrivalInput);
						System.out.print("Burst Time: ");
					}
				} catch (InputMismatchException ex) {
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter no. of Processes: " + processQuantity);
					System.out.println("\nProcess " + (processCounter + 1) + ": ");
					System.out.println("Arrival Time: " + arrivalInput);
					System.out.print("Burst Time: ");
				}
			} while (!validBurst);

			processArrivalTime[processCounter] = arrivalInput;
			processBurstTime[processCounter] = burstInput;
			processCounter++;

		} while (processCounter < processQuantity);

		int timelineValues[] = new int[processQuantity + 1];
		int burstTimeComparison[] = new int[processQuantity];
		String timelineVariables[] = new String[processQuantity + 1];

		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		pressEnterToContinue();

		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		table(processQuantity, processArrivalTime, processBurstTime, timelineValues, 0, 0, timelineVariables,
				processVariables);

		for (int i = 0; i < processQuantity; i++) {
			timelineValues[i] = 0;
			burstTimeComparison[i] = 0;
			timelineVariables[i] = "";
		}
		boolean processChecker[] = new boolean[processQuantity];
		int timelineCounter = 0;

		int processBurstTimeVal = 0;
		int processIndex = 0;

		int currentArrival = 0;
		int totalArrivalTime = 0;

		int shortestTime = 0;
		int shortestTimeIndex = 0;

		// first variable
		for (int i = 0; i < processQuantity; i++) {
			if (i == 0) {
				currentArrival = processArrivalTime[i];
				processIndex = i;
			}

			if (processArrivalTime[i] <= currentArrival) {
				if (processArrivalTime[i] < currentArrival) {
					processIndex = i;
				}
				currentArrival = processArrivalTime[i];

				if (currentArrival == 0) {
					flag = true;
				}

				if (shortestTime == 0) {
					shortestTime = processBurstTime[i];
					processIndex = i;
				} else if (processBurstTime[i] < shortestTime) {
					shortestTime = processBurstTime[i];
					processIndex = i;
				}
			}
		}

		int timelineLimit = processQuantity;

		if (!flag) {
			timelineValues[timelineCounter] = processArrivalTime[processIndex];
			timelineVariables[timelineCounter] = "idle";
			timelineCounter++;

		} else {
			timelineValues[timelineCounter] = processBurstTime[processIndex];
			timelineVariables[timelineCounter] = processVariables[processIndex];
			processChecker[processIndex] = true;

			totalArrivalTime = timelineValues[timelineCounter];
			timelineCounter++;
		}

		pressEnterToContinue();

		// print timeline
		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		table(processQuantity, processArrivalTime, processBurstTime, timelineValues, timelineLimit, timelineCounter,
				timelineVariables, processVariables);
		timeline(timelineCounter, timelineValues, timelineVariables);

		if (!flag) {
			timelineValues[timelineCounter] = timelineValues[timelineCounter - 1] + processBurstTime[processIndex];
			timelineVariables[timelineCounter] = processVariables[processIndex];
			processChecker[processIndex] = true;

			totalArrivalTime = timelineValues[timelineCounter];
			timelineCounter++;
			timelineLimit++;

			pressEnterToContinue();

			header();
			System.out.println("Enter no. of Processes: " + processQuantity);
			processList(processQuantity, processVariables);
			table(processQuantity, processArrivalTime, processBurstTime, timelineValues, timelineLimit, timelineCounter,
					timelineVariables, processVariables);
			timeline(timelineCounter, timelineValues, timelineVariables);
			pressEnterToContinue();
		} else {
			pressEnterToContinue();
		}

		// remaining variables
		flag = false;
		do {
			int timeCompCounter = 0;

			// list all available burst time
			for (int i = 0; i < processQuantity; i++) {
				if (!processChecker[i]) {
					if (processArrivalTime[i] < totalArrivalTime) {
						burstTimeComparison[timeCompCounter] = processBurstTime[i];
						timeCompCounter++;
					}
				}
			}

			// compare burst time
			for (int i = 0; i < timeCompCounter; i++) {
				if (i == 0) {
					shortestTime = burstTimeComparison[i];
				}

				if (burstTimeComparison[i] < shortestTime) {
					shortestTime = burstTimeComparison[i];
				}
			}

			for (int i = 0; i < processQuantity; i++) {
				if (shortestTime == processBurstTime[i]) {
					processIndex = i;
				}
			}

			// put burst time in timeline
			if (!flag) {
				timelineValues[timelineCounter] = timelineValues[timelineCounter - 1] + processBurstTime[processIndex];
				timelineVariables[timelineCounter] = processVariables[processIndex];
				processChecker[processIndex] = true;
				totalArrivalTime = timelineValues[timelineCounter];
				timelineCounter++;
			}

			// print timeline
			header();
			System.out.println("Enter no. of Processes: " + processQuantity);
			processList(processQuantity, processVariables);
			table(processQuantity, processArrivalTime, processBurstTime, timelineValues, timelineLimit, timelineCounter,
					timelineVariables, processVariables);
			timeline(timelineCounter, timelineValues, timelineVariables);
			pressEnterToContinue();
		} while (timelineCounter < timelineLimit);

		int sortedTimelineValues[] = new int[processQuantity];

		for (int i = 0; i < processQuantity; i++) {
			for (int j = 0; j < processQuantity; j++) {
				if (timelineLimit > processQuantity) {
					if (processVariables[i].equals(timelineVariables[j + 1])) {
						sortedTimelineValues[i] = timelineValues[j + 1];
						break;
					}
				} else {
					if (processVariables[i].equals(timelineVariables[j])) {
						sortedTimelineValues[i] = timelineValues[j];
						break;
					}
				}
			}
		}

		int valuesTAT[] = new int[processQuantity];
		int valuesWT[] = new int[processQuantity];

		for (int i = 0; i < processQuantity; i++) {
			valuesTAT[i] = sortedTimelineValues[i] - processArrivalTime[i];
			valuesWT[i] = valuesTAT[i] - processBurstTime[i];
		}

		// computing average TAT & WT
		float averageTAT = 0;
		float averageWT = 0;

		for (int i = 0; i < processQuantity; i++) {
			averageTAT += valuesTAT[i];
			averageWT += valuesWT[i];
		}

		averageTAT /= processQuantity;
		averageWT /= processQuantity;

		header();
		System.out.println("Enter no. of Processes: " + processQuantity);
		processList(processQuantity, processVariables);
		table(processQuantity, processArrivalTime, processBurstTime, timelineValues, timelineLimit, timelineCounter,
				timelineVariables, processVariables);
		timeline(timelineCounter, timelineValues, timelineVariables);

		System.out.println("\nAverage Turnaround Time: " + RED_FG + df.format(averageTAT) + COLOR_RESET);
		System.out.println("Average Waiting Time: " + RED_FG + df.format(averageWT) + COLOR_RESET);
	}
}