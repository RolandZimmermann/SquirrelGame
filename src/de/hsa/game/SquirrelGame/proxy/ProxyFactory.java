package de.hsa.game.SquirrelGame.proxy;

import java.lang.reflect.Proxy;

import de.hsa.game.SquirrelGame.botapi.ControllerContext;

public class ProxyFactory {
	public static Object newInstance(Object ob) {
		return Proxy.newProxyInstance(ob.getClass().getClassLoader(),
				new Class<?>[] { ControllerContext.class }, new ProxyBot(ob));
	}
}
