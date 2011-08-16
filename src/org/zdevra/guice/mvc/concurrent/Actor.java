package org.zdevra.guice.mvc.concurrent;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Actor<M> implements UncaughtExceptionHandler {
	
// --------------------------------------------------------
	
	private final static int MAILBOX_SIZE       = 100;
	private final static int SEND_DELAY_MSEC    = 10;
	private final static int RECEIVE_DELAY_MSEC = 300;
	
	private final ActorFunction<M> actorFunction; 
	private final BlockingQueue<M> mailbox; 
	
	private int currentThreadsCount;
	private final int maxThreadsCount;
	private final int minThreadsCount;
		
	private Object increaseDecreaseLock = new int[1];
	private Object messageLock = new int[1];
		
// --------------------------------------------------------

	private class ActorThread implements Runnable {


		@Override
		public void run() {
			try {
				while (true) {
					M msg = mailbox.poll(RECEIVE_DELAY_MSEC, TimeUnit.MILLISECONDS);
					if (msg != null) {
						actorFunction.act(msg);
					} else {
						if (canDecreaseThreads()) {
							System.out.println("thread useless");
							return; 
						}
					}					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				decreaseThreads();
			}
		}	
	}
	
	
// --------------------------------------------------------
	
	
	public Actor(ActorFunction<M> actorFunction) {
		this(actorFunction, 1, 10);
	}

	
	public Actor(ActorFunction<M> actorFunction, int minThreadsCount, int maxThreadsCount) {
		this.mailbox = new ArrayBlockingQueue<M>(MAILBOX_SIZE);
		this.actorFunction = actorFunction;
		this.minThreadsCount = minThreadsCount;
		this.maxThreadsCount = maxThreadsCount;
	}
	
	
	public final void sendEx(M message) throws InterruptedException {
		boolean res = false;
		while (res == false) {
			res = send(message);
		}
	}
	
	
	public final boolean send(M message) throws InterruptedException {
		synchronized ( messageLock ) {								
			boolean res = this.mailbox.offer(message, SEND_DELAY_MSEC, TimeUnit.MILLISECONDS);
			if (!res) {
				increaseThreads();
			}
			
			if (currentThreadsCount < minThreadsCount) {
				for (int i = currentThreadsCount; i < minThreadsCount; i++) {
					increaseThreads();
				}								
			}			
			return res;			
		}		
	}
	
		
	private boolean increaseThreads() {
		synchronized (increaseDecreaseLock) {
			if (currentThreadsCount < maxThreadsCount ) {
				Thread t = new Thread(new ActorThread());
				t.setUncaughtExceptionHandler(this);
				t.start();
				currentThreadsCount++;
				System.out.println("increased threads to:" + currentThreadsCount);
				return true;
			}
			return false;
		}
	}
	
	
	private void decreaseThreads() {
		synchronized (increaseDecreaseLock) {
			currentThreadsCount--;
			System.out.println("decreased threads to:" + currentThreadsCount);
		}
	}
	
	
	private boolean canDecreaseThreads() {
		synchronized (increaseDecreaseLock) {
			if (currentThreadsCount > minThreadsCount) {
				return true;
			}
			return false;
		}
	}
	

	@Override
	public final void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace();
	}
	
// --------------------------------------------------------		
}
