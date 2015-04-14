package org.mountain.nio.ch03;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapFile {

	private static final PrintStream out = System.out;

	public static void dumpBuffer(String prefix, ByteBuffer buffer) {
		out.println(prefix + " : '");
		int nulls = 0;
		int limit = buffer.limit();

		for (int i = 0; i < limit; ++i) {
			char c = (char) buffer.get(i);
			if (c == '\u0000') {
				nulls++;
				continue;
			}

			if (nulls != 0) {
				out.print("|[" + nulls + " null]|");
				nulls = 0;
			}
			out.print(c);
		}
		out.println("'");
	}

	public static void showBuffers(ByteBuffer ro, ByteBuffer rw, ByteBuffer cow) {
		dumpBuffer("R/O", ro);
		dumpBuffer("R/W", rw);
		dumpBuffer("COW", cow);
		System.out.println("");
	}

	public static void main(String[] args) throws IOException {
		File temp = File.createTempFile("mmaptest", null);
		RandomAccessFile file = new RandomAccessFile(temp, "rw");
		FileChannel channel = file.getChannel();

		ByteBuffer t = ByteBuffer.allocate(100);
		t.put("This is the file content".getBytes());
		t.flip();
		channel.write(t, 0);

		t.clear();
		t.put("This is more file content".getBytes());
		t.flip();
		channel.write(t, 8192);

		MappedByteBuffer ro = channel.map(FileChannel.MapMode.READ_ONLY, 0,
				channel.size());
		MappedByteBuffer rw = channel.map(FileChannel.MapMode.READ_WRITE, 0,
				channel.size());
		MappedByteBuffer cow = channel.map(FileChannel.MapMode.PRIVATE, 0,
				channel.size());

		// the buffer states before any modifications
		System.out.println("Begin");
		showBuffers(ro, rw, cow);
		// Modify the copy-on-write buffer
		cow.position(8);
		cow.put("COW".getBytes());
		System.out.println("Change to COW buffer");
		showBuffers(ro, rw, cow);

		// Modify the read/write buffer
		rw.position(9);
		rw.put(" R/W ".getBytes());
		rw.position(8194);
		rw.put(" R/W ".getBytes());
		rw.force();
		System.out.println("Change to R/W buffer");
		showBuffers(ro, rw, cow);

	}

}
