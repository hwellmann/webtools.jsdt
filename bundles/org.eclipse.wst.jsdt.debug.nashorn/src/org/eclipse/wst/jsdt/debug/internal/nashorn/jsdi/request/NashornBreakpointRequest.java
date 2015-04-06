package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.request;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.BreakpointRequest;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornLocation;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornVirtualMachine;

public class NashornBreakpointRequest extends NashornEventRequest implements
		BreakpointRequest {

	private NashornLocation location;
	private IJavaLineBreakpoint breakpoint;

	public NashornBreakpointRequest(NashornVirtualMachine vm, NashornLocation location) {
		super(vm);
		this.location = location;
	}

	@Override
	public NashornLocation location() {
		return location;
	}

	@Override
	public void addThreadFilter(ThreadReference thread) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addConditionFilter(String condition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addHitCountFilter(int hitcount) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void setEnabled(boolean enabled) {
		checkDeleted();
		if (this.enabled == enabled) {
			return;
		}
		try {
			if (enabled) {
				// set breakpoint
				breakpoint = JDIDebugModel.createLineBreakpoint(ResourcesPlugin
						.getWorkspace().getRoot(), location.type(), location
						.lineNumber(), -1, -1, 0, false, null);
				breakpoint.setPersisted(false);
				vm.getJavaTarget().breakpointAdded(breakpoint);
				breakpoint.setEnabled(true);
			} else {
				breakpoint.setEnabled(false);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.enabled = enabled;
	}
}
