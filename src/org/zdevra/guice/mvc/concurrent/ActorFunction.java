package org.zdevra.guice.mvc.concurrent;

public interface ActorFunction<M> {	
	public void act(M message) throws Exception;
}
