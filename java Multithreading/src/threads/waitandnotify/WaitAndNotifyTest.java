package threads.waitandnotify;

import java.util.Random;
import java.util.Scanner;
/**
 * wait and notify can only be called within the synchronized blocks
 * wait releases the intrinsic lock obtained by the synchronied block on the object
 * it wait untill any other thread calles notify on the same object 
 * 
 * ***/
public class WaitAndNotifyTest {
	public static void main(String[] args) throws InterruptedException {
		Object lock =new Object();
		Thread producerThread = new Thread(() -> {
			synchronized (lock) {
				System.out.println("in Producer begining to wait");
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Resumed....");
			}
		});

		Thread consumerThread = new Thread(() -> {
		synchronized (lock) {
			System.out.println("In consumer ");	
			System.out.println("press enter to continue ");
			Scanner s=new Scanner(System.in);
			s.nextLine();
			lock.notify();/*notify does not hand over the control directly .
			 Intrinsic lock is released on the object only when the sycronzed code block completes */
			System.out.println("are you sure ??? ");
			s.nextLine();

			}

			
		});	
		producerThread.start();
		consumerThread.start();
		producerThread.join();
		consumerThread.join();
	}

}
