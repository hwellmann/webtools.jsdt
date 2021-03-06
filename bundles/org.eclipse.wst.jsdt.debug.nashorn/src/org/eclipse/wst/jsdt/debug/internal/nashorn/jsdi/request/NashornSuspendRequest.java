/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.request;

import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.SuspendRequest;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornVirtualMachine;

/**
 * Rhino implementation of {@link SuspendRequest}
 * 
 * @since 1.0
 */
public class NashornSuspendRequest extends NashornEventRequest implements SuspendRequest {

	private final ThreadReference thread;

	/**
	 * Constructor
	 * @param vm
	 * @param thread
	 */
	public NashornSuspendRequest(NashornVirtualMachine vm, ThreadReference thread) {
		super(vm);
		this.thread = thread;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.core.jsdi.request.SuspendRequest#thread()
	 */
	public ThreadReference thread() {
		return thread;
	}
}