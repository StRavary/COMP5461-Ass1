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
		this.chopsticks = new int[piNumberOfPhilosophers];
		for (int i = 0; i < piNumberOfPhilosophers; i++) {
			chopsticks[i] = 1; // 1 means available, 0 means in use
		}
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
		int i = piTID - 1;
		while (!(chopsticks[i] == 1 && chopsticks[(i + 1) % numberOfPhilosophers] == 1)) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Monitor.pickUp():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
		chopsticks[i] = 0;
		chopsticks[(i + 1) % numberOfPhilosophers] = 0;
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		// ...
		int i = piTID - 1;
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
		while (isTalking) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Monitor.requestTalk():");
				DiningPhilosophers.reportException(e);
				System.exit(1);
			}
		}
		isTalking = true;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk() {
		// ...
		isTalking = false;
		notifyAll();
	}
}

// EOF
