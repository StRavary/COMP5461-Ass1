/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	private int chopsticks[];
	private int numberOfPhilosophers;
	private boolean isTalking = false;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		// TODO: set appropriate number of chopsticks based on the # of philosophers

		// Create one chopstick for every philosopher
		// In the dining philosophers problem, the number of chopsticks equals the number of philosophers
		this.chopsticks = new int[piNumberOfPhilosophers];

		// Initially, all chopsticks are available because no philosopher is eating yet
		// Each position in the array represents one chopstick, 1 means available, 0 means currently taken
		for (int i = 0; i < piNumberOfPhilosophers; i++) {
			chopsticks[i] = 1;
		}

		// Store the number of philosophers so other methods can determine the correct chopstick indexes
		this.numberOfPhilosophers = piNumberOfPhilosophers;


	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) {
		// ...

		// Convert philosopher ID into an array index
		// Philosopher IDs start at 1, but arrays start at index 0
		int i = piTID - 1;

		// Wait until both required chopsticks are available
		// The left chopstick is at index i
		// The right chopstick is the next index, using modulo (%) to wrap around so last philosopher can use first chopstick
		while (!(chopsticks[i] == 1 &&
				chopsticks[(i + 1) % numberOfPhilosophers] == 1)) {
			try {
				wait(); // Release the monitor and wait until a chopstick becomes available
			} catch (InterruptedException e) {
				System.err.println("Monitor.pickUp():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}

		// Reserve both chopsticks so another philosopher cannot use them while current philosopher eats
		chopsticks[i] = 0;
		chopsticks[(i + 1) % numberOfPhilosophers] = 0;


	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		// ...

		// Convert philosopher ID into an array index
		int i = piTID - 1;

		// Mark both chopsticks as available again after current philosopher finishes eating
		chopsticks[i] = 1;
		chopsticks[(i + 1) % numberOfPhilosophers] = 1;

		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk() {
		// ...

		// If another philosopher is already talking, wait until the talking philosopher finishes
		while (isTalking) {
			try {
				wait(); // Release the monitor until talking becomes available
			} catch (InterruptedException e) {
				System.err.println("Monitor.requestTalk():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}

		// Lock talking access so no other philosopher can talk at once
		isTalking = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk() {
		// ...

		// Release the talking lock so another philosopher can talk
		isTalking = false;

		notifyAll();
	}
}

// EOF
