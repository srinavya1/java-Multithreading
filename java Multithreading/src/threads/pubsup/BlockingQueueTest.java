package threads.pubsup;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**Blocking queue can be used to solve the problem of producer and consumer 
 * the put methord waits to put if queue is full and the get methord waits if queue 
 * is empty and both the methords are synchronized
 * 
 */


public class BlockingQueueTest {
	public static void main(String[] args) throws InterruptedException{
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
		Thread producerThread = new Thread(() -> {
			Random r = new Random();
			while (true) {
				try {
					queue.put(r.nextInt(100));
					System.out.println("added to qure and queue size is  "+queue.size());
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread consumerThread = new Thread(() -> {
			Random r = new Random();
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(r.nextInt(10)==0)
				try {
					System.out.println("Number "+queue.take()+" Taken from the queue  and queue size is " +queue.size());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		});
		producerThread.start();
		consumerThread.start();
		producerThread.join();
		consumerThread.join();
	}
}
