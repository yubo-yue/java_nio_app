package org.mountain.nio.ch02;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LockTest.class);

	private static final int SIZEOF_INT = 4;
	private static final int INDEX_START = 0;
	private static final int INDEX_COUNT = 10;
	private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;
	
	private ByteBuffer buffer = ByteBuffer.allocate(INDEX_SIZE);
	private IntBuffer indexBuffer = buffer.asIntBuffer();
	
	private Random random = new Random();
	
	private int idxval = 1;
	
	private int lastLineLen = 0;
	
	private void println(String msg) {
		LOGGER.info("\r");
		LOGGER.info(msg);
		
		for (int i = msg.length(); i < lastLineLen; i++)
		{
			LOGGER.info(" ");
		}
		LOGGER.info("\r");
		lastLineLen = msg.length();
	}
}
