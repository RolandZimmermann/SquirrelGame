package de.hsa.game.SquirrelGame.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server implements Runnable{

	private int port;
	private boolean shouldRun = true;

	private ServerSocket server;
	private Vector<ServerConnection> connections = new Vector<ServerConnection>();
	
	public Vector<ServerConnection> getConnections() {
		return this.connections;
	}
	
	public Server(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			while (shouldRun) {
				Socket connection = server.accept();
				ServerConnection sc = new ServerConnection(connection);
				new Thread(sc).start();
				connections.add(sc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ServerSocket getServer() {
		return this.server;
	}

	public void stop() {
		for(ServerConnection sc: connections) {
			sc.stop();
		}
		shouldRun = false;		
	}
	

}
