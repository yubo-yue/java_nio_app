package org.mountain.nio;

import java.util.concurrent.TimeUnit;

public class OutOfMemoryTest {

	public static void main(String[] args) throws InterruptedException {
		double[][] doubles = new double[100][10000];
		for (int i = 0; i < 100; i++)
		{
			doubles[i] = new double[10000];
			System.out.println("Running");
			TimeUnit.SECONDS.sleep(1);
		}
		
		System.out.println("End of program.");
	}

}
