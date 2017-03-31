package spasko.simpleLPT;

import java.util.concurrent.TimeUnit;

import jnpout32.pPort;

public class Test extends Thread {
	private short[] steps = { 1, 2, 4, 8, 16, 32, 64, 128 };
	private pPort lpt = new pPort();
	private short port = 888;
	private long timeStep = 100000 * 1000;
	private Mainform mainform;
	private volatile boolean stopRequested = false;

	public void requestStop() {
		stopRequested = true;
	}

	public Test(Mainform mainform) {
		this.mainform = mainform;
	}

	public void run() {
		all: while (true) {
			try {
				for (int i = 1; i < 50; i++) {
					if (stopRequested)
						break all;
					lpt.output(port, steps[i % steps.length]);
					mainform.setData(steps[i % steps.length]);
					TimeUnit.NANOSECONDS.sleep(timeStep);
				}
				for (int i = 50; i > 1; i--) {
					if (stopRequested)
						break all;
					lpt.output(port, steps[i % steps.length]);
					mainform.setData(steps[i % steps.length]);
					TimeUnit.NANOSECONDS.sleep(timeStep);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
