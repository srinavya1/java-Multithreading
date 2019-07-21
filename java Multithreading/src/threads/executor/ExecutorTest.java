package threads.executor;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		 Future<Integer> submit = threadPool.submit(()-> {

		
				Random r=new Random();
				int nextInt = r.nextInt(2000)+2500;
				System.out.println("Sleeping for "+ nextInt + "millisecs ");
				
				if (nextInt>3000) {
					
					throw new IOException("sleeping more than 3 seconds");
				}
			Thread.sleep(nextInt);
System.out.println("finished");
			return nextInt;
		
	});
		 threadPool.shutdown(); 
		try {
			submit.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
}
	}
