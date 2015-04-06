package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi;

import org.eclipse.wst.jsdt.debug.core.jsdi.Mirror;
import org.eclipse.wst.jsdt.debug.core.jsdi.VirtualMachine;

public class NashornMirror implements Mirror {

	protected final NashornVirtualMachine vm;

	/**
	 * Constructor
	 * 
	 * @param vm
	 * @param description
	 */
	public NashornMirror(NashornVirtualMachine vm) {
		this.vm = vm;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.core.jsdi.Mirror#virtualMachine()
	 */
	public VirtualMachine virtualMachine() {
		return this.vm;
	}
	
	/**
	 * Re-throws the given exception as a {@link RuntimeException} with the given message
	 * @param message
	 * @param t
	 * @since 1.1
	 */
	protected void handleException(String message, Throwable t) {
		throw new RuntimeException(message, t);
	}
}
