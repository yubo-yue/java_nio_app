package org.mountain.nio.ch03;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Marketing {

	private static final String DEMOGRAPHIC = "blahblah.txt";

	// These are just representative; add your own
	private static String[] col1 = { "Aggregate", "Enable", "Leverage",
			"Facilitate", "Synergize", "Repurpose", "Strategize", "Reinvent",
			"Harness" };
	private static String[] col2 = { "cross-platform", "best-of-breed",
			"frictionless", "ubiquitous", "extensible", "compelling",
			"mission-critical", "collaborative", "integrated" };
	private static String[] col3 = { "methodologies", "infomediaries",
			"platforms", "schemas", "mindshare", "paradigms",
			"functionalities", "web services", "infrastructures" };
	private static String newline = System.getProperty("line.separator");

	private static ByteBuffer[] utterBS(int howMany) throws UnsupportedEncodingException {
		List<ByteBuffer> list = new LinkedList<ByteBuffer>();

		for (int i = 0; i < howMany; i++) {
			list.add(pickRandom(col1, " "));
			list.add(pickRandom(col2, " "));
			list.add(pickRandom(col3, " "));
		}
		
		ByteBuffer[] bufs = new ByteBuffer[list.size()];
		list.toArray(bufs);
		return bufs;
	}

	private static Random rand = new Random();

	private static ByteBuffer pickRandom(String[] strings, String suffix)
			throws UnsupportedEncodingException {
		String string = strings[rand.nextInt(strings.length)];
		int total = string.length() + suffix.length();
		ByteBuffer buf = ByteBuffer.allocate(total);
		buf.put(string.getBytes("US-ASCII"));
		buf.put(suffix.getBytes("US-ASCII"));
		buf.flip();
		return buf;
	}
	
	public static void main(String[] args) throws IOException {
		
		int reps = 10;
		
		if (args.length > 0)
		{
			reps = Integer.parseInt(args[0]);
		}
		
		FileOutputStream fos = new FileOutputStream(DEMOGRAPHIC);
		GatheringByteChannel gatherChannel = fos.getChannel();
		
		ByteBuffer[] bs = utterBS(reps);
		
		while (gatherChannel.write(bs) > 0) {
			
		}
		
		fos.close();
	}
}
