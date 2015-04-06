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
package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.event;

import org.eclipse.wst.jsdt.debug.core.jsdi.ScriptReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.event.ScriptLoadEvent;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ScriptLoadRequest;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornScriptReference;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornVirtualMachine;

/**
 * Rhino implementation of {@link ScriptLoadEvent}
 * 
 * @since 1.0
 */
public final class NashornScriptLoadEvent extends NashornLocatableEvent implements ScriptLoadEvent {

	private final NashornScriptReference script;

	/**
	 * Constructor
	 * @param vm
	 * @param thread
	 * @param script
	 * @param request
	 */
	public NashornScriptLoadEvent(NashornVirtualMachine vm, ThreadReference thread, NashornScriptReference script, ScriptLoadRequest request) {
		super(vm, thread, null, request);
		this.script = script;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.jsdt.debug.core.jsdi.event.ScriptLoadEvent#script()
	 */
	public ScriptReference script() {
		return script;
	}
}