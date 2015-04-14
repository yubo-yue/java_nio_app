package org.mountain.nio.ch03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

public class TimeServer {
	private static final int DEFAULT_TIME_PORT = 37;
	private static final long DIFF_1900 = 2208988800L;

	protected DatagramChannel channel;

	public TimeServer(int port) throws IOException {
		this.channel = DatagramChannel.open();
		this.channel.socket().bind(new InetSocketAddress(port));
		System.out.println("Listening on port " + port + " for time requests.");
	}

	public void listen() throws IOException {
		// allocate the buffer can hold one long value
		ByteBuffer longBuffer = ByteBuffer.allocate(8);

		longBuffer.order(ByteOrder.BIG_ENDIAN);

		longBuffer.putLong(0, 0);
		longBuffer.position(4);
		ByteBuffer buffer = longBuffer.slice();

		while (true) {
			buffer.clear();
			SocketAddress sa = this.channel.receive(buffer);

			if (sa == null)
				continue;

			buffer.clear();

			longBuffer.putLong((System.currentTimeMillis() / 1000) + DIFF_1900);
			this.channel.send(buffer, sa);
		}
	}

	public static void main(String[] args) {
		int port = DEFAULT_TIME_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		try {
			TimeServer server = new TimeServer(port);
			server.listen();
		} catch (SocketException e) {
			System.out.println("Can't bind to port " + port
					+ ", try a different one");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
