package de.hsa.game.SquirrelGame.proxy;

import java.lang.reflect.Proxy;

import de.hsa.games.fatsquirrel.botapi.ControllerContext;


public class ProxyFactory {
	public static Object newInstance(ControllerContext ob) {
		return Proxy.newProxyInstance(ob.getClass().getClassLoader(),
				new Class<?>[] { ControllerContext.class }, new ProxyBot(ob));
	}
}
