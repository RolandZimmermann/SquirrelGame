package de.hsa.game.SquirrelGame.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
	private View view;

	public Client(String address, int port) {
		this.port = port;
		try {
			server = new Socket(address, port);
			out = new ObjectOutputStream(server.getOutputStream());
			out.writeObject(new Message(Header.CHAT, "HALLO?"));
			in = new ObjectInputStream(server.getInputStream());
			in.readObject();
			shouldRun = true;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setMessage(Message messageOut) {
		this.messageOut.add(messageOut);
	}
	
	public View getView() {
		return this.view;
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
		if (!shouldRun) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

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

	public void send() {
		try {
			while (shouldRun) {
				while (this.messageOut.size() > 0) {
					out.writeObject(this.messageOut.get(0));
					out.flush();
					this.messageOut.remove(0);
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					shouldRun = false;
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void recive() {
		while (shouldRun) {
			try {
				if(server.isClosed()) {
					shouldRun = false;
				}
				messageIn = (Message) in.readObject();
				if(messageIn.getHeader() == Header.CHAT) {
					chatMessages.add((String) messageIn.getObject());
				}
				if(messageIn.getHeader() == Header.UPDATE) {
					view = (View) messageIn.getObject();
				}
			} catch (ClassNotFoundException | IOException e) {
				shouldRun = false;
				e.printStackTrace();
			}
		}
	}
}
