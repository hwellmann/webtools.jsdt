package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.wst.jsdt.debug.core.breakpoints.IJavaScriptBreakpoint;
import org.eclipse.wst.jsdt.debug.core.jsdi.BooleanValue;
import org.eclipse.wst.jsdt.debug.core.jsdi.NullValue;
import org.eclipse.wst.jsdt.debug.core.jsdi.NumberValue;
import org.eclipse.wst.jsdt.debug.core.jsdi.StringValue;
import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.UndefinedValue;
import org.eclipse.wst.jsdt.debug.core.jsdi.VirtualMachine;
import org.eclipse.wst.jsdt.debug.core.jsdi.event.EventQueue;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.EventRequestManager;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.request.NashornEventRequestManager;
import org.eclipse.wst.jsdt.debug.nashorn.launch.NashornScriptLoadBreakpointListener;

public class NashornVirtualMachine implements VirtualMachine {
	
	private IJavaDebugTarget javaTarget;
	private NashornEventRequestManager erm;
	private NashornEventQueue queue;
	private Map<IThread, NashornThreadReference> threadMap = new HashMap<IThread, NashornThreadReference>();
	private Map<String, NashornScriptReference> scriptMap = new HashMap<String, NashornScriptReference>();
	
	private NashornScriptLoadBreakpointListener javaBreakpointListener;

	public NashornVirtualMachine(IJavaDebugTarget javaTarget) {
		this.javaTarget = javaTarget;
		this.erm = new NashornEventRequestManager(this);
		this.queue = new NashornEventQueue(this, erm);
		wrapThreads();
	}

	private void wrapThreads() {
		try {
			for (IThread thread : javaTarget.getThreads()) {
				wrapThread(thread);
			}
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String version() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List allThreads() {
		List<ThreadReference> threads = new ArrayList<ThreadReference>(threadMap.values());
		return threads;
	}

	@Override
	public List allScripts() {
		List<NashornScriptReference> scripts = new ArrayList<NashornScriptReference>(scriptMap.values());
		return scripts;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UndefinedValue mirrorOfUndefined() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NullValue mirrorOfNull() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooleanValue mirrorOf(boolean bool) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NumberValue mirrorOf(Number number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringValue mirrorOf(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventRequestManager eventRequestManager() {
		return erm;
	}

	@Override
	public EventQueue eventQueue() {
		return queue;
	}

	@Override
	public boolean canUpdateBreakpoints() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateBreakpoint(IJavaScriptBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		
	}

	public NashornThreadReference wrapThread(IThread thread) {
		NashornThreadReference threadRef = new NashornThreadReference(thread, this);
		threadMap.put(thread, threadRef);
		return threadRef;
	}
	
	public NashornThreadReference lookupThread(IThread thread) {
		return threadMap.get(thread);
	}
	
	public NashornScriptReference lookupScript(String sourcePath) {
		return scriptMap.get(sourcePath);
	}
	
	public void addScript(NashornScriptReference script) {
		scriptMap.put(script.sourceURI().toString(), script);
	}
	
	public IJavaDebugTarget getJavaTarget() {
		return javaTarget;
	}

	public NashornScriptLoadBreakpointListener getJavaBreakpointListener() {
		return javaBreakpointListener;
	}

	public void setJavaBreakpointListener(
			NashornScriptLoadBreakpointListener javaBreakpointListener) {
		this.javaBreakpointListener = javaBreakpointListener;
	}
	
    
}
