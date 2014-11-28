package org.mountain.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

	final Selector selector;
	final ServerSocketChannel serverSocketChannel;
	final boolean isWithThreadPool;
	
	public Reactor(int port, boolean isWithThreadPool) throws IOException {
		this.isWithThreadPool = isWithThreadPool;
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		SelectionKey selectionKey0 = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		selectionKey0.attach(new Acceptor());
	}
	
	@SuppressWarnings("rawtypes")
	public void run() {
		System.out.println("Server listening to port : " + serverSocketChannel.socket().getLocalPort());
		try {
		while (!Thread.interrupted()) {
			selector.select();
			Set selected = selector.selectedKeys();
			Iterator it = selected.iterator();
			while (it.hasNext()) {
				dispatch((SelectionKey)(it.next()));
			}
			selected.clear();
		}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	void dispatch(SelectionKey k)
	{
		Runnable r = (Runnable) (k.attachment());
		if (r != null)
		{
			r.run();
		}
	}
	
	class Acceptor implements Runnable{

		public void run() {
			try {
				SocketChannel socketChannel = serverSocketChannel.accept();
				if (socketChannel != null) {
					if (isWithThreadPool) {
						
					} else {
						new Handler(selector, socketChannel);
					}
				}
				System.out.println("Connection Accepted by Reactor");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) throws IOException
	{
		Reactor reactor = new Reactor(9900, false);
		new Thread(reactor).start();
	}

}
