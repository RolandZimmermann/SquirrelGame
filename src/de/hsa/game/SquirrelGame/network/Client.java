package de.hsa.game.SquirrelGame.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import de.hsa.game.SquirrelGame.network.Message.Header;

public class Client implements Runnable {

	private Socket server;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean shouldRun;

	private Timer output;
	private Timer input;

	private String address;
	private int port;

	private Message messageIn;
	private Vector<Message> messageOut = new Vector<>();
	
	private Vector<String> chatMessages = new Vector<>();
	private Vector<byte[][]> views = new Vector<>();

	public Client(String address, int port) {
		this.port = port;
		this.address = address;
	}

	public void setMessage(Message messageOut) {
		this.messageOut.add(messageOut);
	}
	
	public byte[][] getView() {
		if(views.isEmpty()) {
			return null;
		}
		byte[][] toReturn = views.get(0);
		views.clear();
		return toReturn;
	}

	public Vector<String> getChatMessage() {
		Vector<String> output = new Vector<>();
		int size = this.chatMessages.size();
		for (int i = 0; i < size; i++) {
			output.add(chatMessages.get(0));
			chatMessages.remove(0);
		}

		return output;
	}

	public boolean isClosed() {
		return server.isClosed();
	}

	public void run() {
		output = new Timer();
		output.schedule(new TimerTask() {

			@Override
			public void run() {
				send();
			}
		}, 1);
		input = new Timer();
		input.schedule(new TimerTask() {

			@Override
			public void run() {
				recive();
			}
		}, 1);

	}

	public void init() throws UnknownHostException, IOException, ClassNotFoundException {
		server = new Socket(address, port);
		out = new ObjectOutputStream(server.getOutputStream());
		out.writeObject(new Message(Header.CHAT, "HALLO?"));
		in = new ObjectInputStream(server.getInputStream());
		in.readObject();
		shouldRun = true;
	}

	public void send() {
		try {
			while (shouldRun) {
				while (this.messageOut.size() > 0 && shouldRun) {
					out.writeObject(this.messageOut.get(0));
					out.flush();
					this.messageOut.remove(0);
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					shouldRun = false;
					Thread.currentThread().interrupt();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void recive() {
		while (shouldRun) {
			try {
				if(server.isClosed() || Thread.interrupted()) {
					shouldRun = false;
				}
				messageIn = (Message) in.readObject();
				if(messageIn.getHeader() == Header.CHAT) {
					chatMessages.add((String) messageIn.getObject());
				}
				if(messageIn.getHeader() == Header.UPDATE) {
					views.add((byte[][]) messageIn.getObject());
				}
			} catch (ClassNotFoundException | IOException e) {
				shouldRun = false;
				Thread.currentThread().interrupt();
				
			}
		}
	}

	public void close() {
		try {
			shouldRun = false;
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.currentThread().interrupt();
		
	}
}
