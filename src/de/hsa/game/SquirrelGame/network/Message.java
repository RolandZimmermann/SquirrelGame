package de.hsa.game.SquirrelGame.network;

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Header {
		CHAT,ACTION,UPDATE;
	}
	
	private Header header;
	private Object object;
	
	public Message(Header header, Object object) {
		this.header = header;
		this.object = object;
	}
	
	public Header getHeader() {
		return header;
	}
	
	public Object getObject() {
		return object;
	}
}
