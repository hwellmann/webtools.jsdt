package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.wst.jsdt.debug.core.jsdi.Location;
import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.BreakpointRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.DebuggerStatementRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.EventRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.EventRequestManager;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ExceptionRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ResumeRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ScriptLoadRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.StepRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.SuspendRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ThreadEnterRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ThreadExitRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.VMDeathRequest;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.VMDisconnectRequest;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornLocation;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornVirtualMachine;

public class NashornEventRequestManager implements EventRequestManager {
	
	private NashornVirtualMachine vm;
	
	private List<BreakpointRequest> breakpointRequests = new ArrayList<BreakpointRequest>();
	private List<ScriptLoadRequest> scriptLoadRequests = new ArrayList<ScriptLoadRequest>();
	
    public NashornEventRequestManager(NashornVirtualMachine vm) {
		this.vm = vm;
	}	

	@Override
	public BreakpointRequest createBreakpointRequest(Location location) {
		NashornBreakpointRequest request = new NashornBreakpointRequest(vm, (NashornLocation) location);
		breakpointRequests.add(request);
		return request;
	}

	@Override
	public List<BreakpointRequest> breakpointRequests() {
		return Collections.unmodifiableList(breakpointRequests);
	}

	@Override
	public DebuggerStatementRequest createDebuggerStatementRequest() {
		
		return new NashornDebuggerStatementRequest(vm);
	}

	@Override
	public List debuggerStatementRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExceptionRequest createExceptionRequest() {
		return new NashornExceptionRequest(vm);
	}

	@Override
	public List exceptionRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScriptLoadRequest createScriptLoadRequest() {
		NashornScriptLoadRequest request = new NashornScriptLoadRequest(vm);
		scriptLoadRequests.add(request);
		return request;
	}

	@Override
	public List scriptLoadRequests() {
		return Collections.unmodifiableList(scriptLoadRequests);
	}

	@Override
	public StepRequest createStepRequest(ThreadReference thread, int step) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List stepRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuspendRequest createSuspendRequest(ThreadReference thread) {
		return new NashornSuspendRequest(vm, thread);
	}

	@Override
	public List suspendRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResumeRequest createResumeRequest(ThreadReference thread) {
		return new NashornResumeRequest(vm, thread);
	}

	@Override
	public List resumeRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadEnterRequest createThreadEnterRequest() {
		return new NashornThreadEnterRequest(vm);
	}

	@Override
	public List threadEnterRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ThreadExitRequest createThreadExitRequest() {
		return new NashornThreadExitRequest(vm);
	}

	@Override
	public List threadExitRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEventRequest(EventRequest eventRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEventRequest(List eventRequests) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VMDeathRequest createVMDeathRequest() {
		return new NashornVMDeathRequest(vm);
	}

	@Override
	public List vmDeathRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VMDisconnectRequest createVMDisconnectRequest() {
		return new NashornVMDisconnectRequest(vm);
	}

	@Override
	public List vmDisconnectRequests() {
		// TODO Auto-generated method stub
		return null;
	}

}
