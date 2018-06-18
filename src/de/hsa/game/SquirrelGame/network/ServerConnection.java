package de.hsa.game.SquirrelGame.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import de.hsa.game.SquirrelGame.network.Message.Header;
import javafx.event.ActionEvent;

public class ServerConnection implements Runnable {

	private Socket socket;
	private Server server;
	private String name;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean shouldRun;
	private Message messageIn;
	private Vector<Message> messageOut = new Vector<>();

	private Timer output;
	private Timer input;
	
	private Vector<String> chatMessages = new Vector<>();
	private Vector<String> events = new Vector<>();

	public ServerConnection(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		this.name = socket.getInetAddress().toString();

		try {
			in = new ObjectInputStream(socket.getInputStream());
			in.readObject();
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(new Message(Header.CHAT,"HALLO"));
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		shouldRun = true;
	}

	public void run() {
		if (!shouldRun) {
			try {
				socket.close();
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

	public void setMessage(Message messageOut) {
		this.messageOut.add(messageOut);
	}

	public Vector<String> getChatMessage() {
		Vector<String> chat = new Vector<String>();
		int size = chatMessages.size();
		for (int i = 0; i < size ; i++) {
			chat.add(chatMessages.get(0));
			chatMessages.remove(0);
		}
		
		return chat;
	}

	public String getEvents() {
		if(events.isEmpty()) {
			return null;
		}
		String toReturn = events.get(0);
		events.clear();
		return toReturn;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean isConnected() {
		return shouldRun;
	}
	
	public void disconnect() {
		shouldRun = false;
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
				if(socket.isClosed()) {
					shouldRun = false;
				}
				messageIn = (Message) in.readObject();
				if(messageIn.getHeader() == Header.CHAT) {
					chatMessages.add((String) messageIn.getObject());
				}
				if(messageIn.getHeader() == Header.ACTION) {
					events.add((String) messageIn.getObject());
				}
			} catch (ClassNotFoundException | IOException e) {
				shouldRun = false;
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

}
