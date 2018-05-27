package de.hsa.game.SquirrelGame.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.log.GameLogger;

public class ProxyBot implements InvocationHandler {

	private static Logger logger = Logger.getLogger(GameLogger.class.getName());
	static {
		new GameLogger();
	}

	private Object obj;

	public ProxyBot(Object obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		logger.info(method.getName() + " || " + args.toString());
		return null;
	}

}
