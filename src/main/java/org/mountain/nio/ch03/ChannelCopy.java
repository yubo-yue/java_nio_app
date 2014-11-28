package org.mountain.nio.ch03;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopy {
	
	private static final int KB = 1024;
	
	public static void main(String[] args) throws IOException {
		ReadableByteChannel source = Channels.newChannel(System.in);
		WritableByteChannel dest = Channels.newChannel(System.out);
		
		channelCopy(source, dest);
		
		source.close();
		dest.close();
	}

	private static void channelCopy (ReadableByteChannel src, WritableByteChannel dest) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocate(16 * KB);
		
		while (src.read(buffer) != -1) {
			buffer.flip();
			
			dest.write(buffer);
			//if partial transfer, shift remainder down, if buffer is empty, same as doing clear()
			buffer.compact();
		}
		
		buffer.flip();
		//make sure that buffer is fully drained
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
}
