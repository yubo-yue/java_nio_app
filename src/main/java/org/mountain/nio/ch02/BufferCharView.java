package org.mountain.nio.ch02;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferCharView {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BufferCharView.class);

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
		CharBuffer charBuffer = buffer.asCharBuffer();
		buffer.put(0, (byte) 0);
		buffer.put(1, (byte) 'H');
		buffer.put(2, (byte) 0);
		buffer.put(3, (byte) 'i');
		buffer.put(4, (byte) 0);
		buffer.put(5, (byte) '!');
		buffer.put(6, (byte) 0);
		println(buffer);
		println(charBuffer);

	}

	public static void println(Buffer buffer) {
		LOGGER.info("pos = " + buffer.position() + ", limit = "
				+ buffer.limit() + ", capacity = " + buffer.capacity() + ": '"
				+ buffer.toString() + "'");
	}
}
