package threads.lock;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Runner {
	private int count = 0;
	Lock lock = new ReentrantLock();
	Condition cond = lock.newCondition();

//	private synchronized void increment() {/*Solution 1 Have increment as sychronized*/
	private void increment() {
		for (int i = 0; i < 10000; i++) {
			count++;
		}
	}

	public void first() throws InterruptedException {
		lock.lock();
		System.out.println("going to wait");
		cond.await();
		System.out.println("resumed");
		try {
			increment();
		} finally {
			lock.unlock();
		}
	}

	public void second() {
		
		lock.lock();
		System.out.println("press enter to resume");
		Scanner s =new Scanner(System.in);
		s.nextLine();
		try {
			increment();
		} finally {

			cond.signal();
			lock.unlock();
		}
		
	}

	public void getCount() {
		System.out.println("Count is " + count);
	}
}

public class ReentrantLockTest {
	public static void main(String[] args) {

		final Runner runner = new Runner();
		Thread firstRunner = new Thread(() -> {
			try {
				runner.first();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		Thread secondRunner = new Thread(() -> {
			runner.second();
		});

		firstRunner.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		secondRunner.start();
		try {
			firstRunner.join();
			secondRunner.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		runner.getCount();
	}
}
