package de.hsa.game.SquirrelGame.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.hsa.game.SquirrelGame.log.GameLogger;
import de.hsa.games.fatsquirrel.botapi.ControllerContext;

public class ProxyBot implements InvocationHandler {

	private static Logger logger = Logger.getLogger(ProxyBot.class.getName());

	private ControllerContext obj;

	public ProxyBot(ControllerContext obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		logger.log(Level.INFO, method.getName());
		try {
		return method.invoke(this.obj, args);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(),e);
			throw e;
		}
	}

}
