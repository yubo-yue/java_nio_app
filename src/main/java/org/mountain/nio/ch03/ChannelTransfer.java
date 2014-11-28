package org.mountain.nio.ch03;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelTransfer {

	
	public static final void main(String[] args) throws IOException
	{
		if (args.length == 0)
		{
			System.err.println("Usage: file name ...");
			return;
		}
		
		catFiles(Channels.newChannel(System.out), args);
	}
	
	public static void catFiles(WritableByteChannel target, String[] files) throws IOException
	{
		for (int i = 0; i < files.length; i++)
		{
			FileInputStream fis = new FileInputStream(files[i]);
			FileChannel channel = fis.getChannel();
			
			channel.transferTo(0,  channel.size(), target);
			
			channel.close();
			fis.close();
		}
	}
}
