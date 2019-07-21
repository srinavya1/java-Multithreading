package threads.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*Countdown latch is a useful utility when working with threads 
 * it lets you count down on the number of times of usage 
 * */

class Mythread extends Thread {
	private CountDownLatch countDownlatch;
	public  Mythread(CountDownLatch countDownlatch) {
		this.countDownlatch=countDownlatch;
	}
@Override
	public void run() {
	System.out.println("Processing");
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("processed");
	countDownlatch.countDown();
}	
}

	

public class CountDownLatchTest{
	
	public static void main(String[] args) {
		CountDownLatch latch =new CountDownLatch(3);
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
		for(int i=0;i<4;i++) {
			newFixedThreadPool.execute(new Mythread(latch));
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("latch dounted down ");
		newFixedThreadPool.shutdown();/****This statement is crucial to check if the thread pool is conpleted 
		whithout this statemnt even if all tasks are complete await termination will not be true
		**/
		try {
			if(newFixedThreadPool.awaitTermination(1, TimeUnit.DAYS)){
			System.out.println("All threads Completed");}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}