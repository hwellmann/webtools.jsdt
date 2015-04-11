package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.wst.jsdt.debug.core.jsdi.event.EventQueue;
import org.eclipse.wst.jsdt.debug.core.jsdi.event.EventSet;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.request.NashornEventRequestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NashornEventQueue implements EventQueue {
	
	private static Logger log = LoggerFactory.getLogger(NashornEventQueue.class);
	
	private NashornVirtualMachine vm;
	private NashornEventRequestManager erm;
	private BlockingQueue<EventSet> delegate = new LinkedBlockingQueue<EventSet>();

	public NashornEventQueue(NashornVirtualMachine vm, NashornEventRequestManager erm) {
		this.vm = vm;
		this.erm = erm;
	}

	@Override
	public EventSet remove() {
		return remove(1000);
	}

	@Override
	public EventSet remove(int timeout) {
		
		try {
			EventSet eventSet = delegate.poll(timeout, TimeUnit.MILLISECONDS);
			if (eventSet != null) {
				log.trace("removed event set {}", eventSet);
			}
			return eventSet;
		} catch (InterruptedException e) {
			// ignore
		}
		return null;
	}

	public void put(EventSet eventSet) {
		log.trace("putting event set {}", eventSet);
		delegate.offer(eventSet);
	}
}
