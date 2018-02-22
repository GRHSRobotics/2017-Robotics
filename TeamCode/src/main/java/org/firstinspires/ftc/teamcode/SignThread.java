package org.firstinspires.ftc.teamcode;

public class SignThread implements Runnable {

	private SignIdentifier signIdentifier;
	private boolean stopRequested = false;

	public Thread thread;

	public SignThread(SignIdentifier signIdentifier) {
		this.signIdentifier = signIdentifier;
	}

	@Override
	public void run() {
		while (!stopRequested) {
			signIdentifier.run();
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop() {
		signIdentifier.stop();
		stopRequested = true;
	}

}
