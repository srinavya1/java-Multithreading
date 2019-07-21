package threads.pubsup;

import java.util.LinkedList;
import java.util.Random;

public class WaitAndNotifyPubSubTest {
	public static void main(String[] args) throws InterruptedException {
		final int count =10;
		LinkedList<Integer> queue  =new LinkedList<Integer>();
		Object lock =new Object();
		Thread producerThread = new Thread(() -> {
			Random r =new Random();
			while(true)		
			 {synchronized (lock) {
				while(queue.size()==count) {
				
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
System.out.println("Adding to queue ");
				queue.add(r.nextInt(1000));	
				lock.notifyAll();
			}
				
				}
			
		});

		Thread consumerThread = new Thread(() -> {
			Random r =new Random();
			while(true) {
			synchronized (lock) {
				
			while(queue.size()==0) {
				try {
					System.out.println("QUEUE SIZE   ZERO :GOING TO WAIT");
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			Integer removedInteger = queue.removeFirst();
			System.out.println("The removed integer is "+removedInteger+" and queue size is "+queue.size());
			lock.notifyAll();
		
						}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**THE PLACE OF THE SLEEP STATEMENT IS CRUCIAL .iF THE SLEEP IS
			 *  WITHIN THE SYNCHROZIZED BLOCK THE CONSUMER WILL NEVER GET TO EXECUTE UNTILL THE CONSUMER ISSUES A 
			 *  WAIT .I.E ALL ITEMS ARE CONSUMED nOTIFY ALL WILL NOT HAVE ANY EFFECT  .
			 *  iF WE HAVE THE SLEEP OUTSIDE THE SYNCHROZED BLOCK THE PRODUCER IS GIVEN
			 *  CONTROL AFTER EACH REMOVAL */
		
			}
		});	
		producerThread.start();
		Thread.sleep(100);
		consumerThread.start();
		producerThread.join();
		consumerThread.join();
	}
}
