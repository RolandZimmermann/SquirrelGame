package de.hsa.game.SquirrelGame.network;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ServerHandler {

	private Vector<ServerConnection> connections;
	private Vector<String> chatMessage = new Vector<>();
	private Map<ServerConnection, String> event = new ConcurrentHashMap<>();
	public boolean shouldRun = true;

	public ServerHandler(Vector<ServerConnection> connections) {
		this.connections = connections;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					checkConnections();
				}
			}
		}).start();
	}

	public Vector<String> getChatMessage() {
		Vector<String> output = new Vector<>();
		int size = chatMessage.size();
		for (int i = 0; i < size; i++) {
			output.add(chatMessage.get(0));
			chatMessage.remove(0);
		}

		return output;
	}
	
	public Map<ServerConnection, String> getEvents() {
//		Map<ServerConnection, String> output = new ConcurrentHashMap<ServerConnection, String>();
//		for(ServerConnection e : event.keySet()) {
//			output.put(e, event.get(e));
//		}
//		return output;
		return event;
	}

	public void sendMessage(ServerConnection serverConnection, Message message) {
		for (ServerConnection e : connections) {
			if (serverConnection.equals(e)) {
				e.setMessage(message);
			}
		}
	}

	public void kick(ServerConnection serverConnection) {
		for (ServerConnection e : connections) {
			if (serverConnection.equals(e)) {
				e.disconnect();
			}
		}
	}

	private void checkConnections() {
		for (int i = 0; i < connections.size(); i++) {
			if (!connections.get(i).isConnected()) {
				event.remove(connections.get(i));
				connections.remove(i);
				continue;
			}
			
			Vector<String> chatMessage = connections.get(i).getChatMessage();
			if (chatMessage != null) {
				for (String j : chatMessage) {
					this.chatMessage.add(connections.get(i).getName() + ": " + j);
				}
			}
			Vector<String> events = connections.get(i).getEvents();
			if(events != null) {
				for(String k : events) {
					event.put(connections.get(i), k);
				}
			}
		}
	}

	public Vector<ServerConnection> getConnections() {
		return this.connections;
	}
}
