package org.eclipse.wst.jsdt.debug.nashorn.launch;

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.EventRequestManager;
import org.eclipse.wst.jsdt.debug.core.jsdi.request.ScriptLoadRequest;
import org.eclipse.wst.jsdt.debug.internal.nashorn.JavaClassPrepareWildcardBreakpoint;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornEventQueue;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornLocation;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornScriptReference;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornThreadReference;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornVirtualMachine;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.event.NashornEventSet;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.event.NashornScriptLoadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ReferenceType;

public class NashornScriptLoadBreakpointListener implements IJavaBreakpointListener {
	
	private static Logger log = LoggerFactory.getLogger(NashornScriptLoadBreakpointListener.class);
	
	private NashornVirtualMachine vm;

	public NashornScriptLoadBreakpointListener(NashornVirtualMachine vm) {
		this.vm = vm;
	}
	
	@Override
	public void addingBreakpoint(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int installingBreakpoint(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint, IJavaType type) {
		return IJavaBreakpointListener.DONT_CARE;
	}

	@Override
	public void breakpointInstalled(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
		if (breakpoint instanceof JavaClassPrepareWildcardBreakpoint) {
			JavaClassPrepareWildcardBreakpoint classPrepareBreakpoint = (JavaClassPrepareWildcardBreakpoint) breakpoint;
			ReferenceType type = classPrepareBreakpoint.getReferenceType(thread);
			if (type != null) {
				findLineLocations(type, thread);
			}
			return DONT_SUSPEND;
		}

		
		return DONT_CARE;
	}

	private void findLineLocations(ReferenceType type, IJavaThread thread) {
		log.debug("script class {}", type.name());
		NashornScriptReference script = null;
		for (Method method : type.methods()) {
			NashornScriptReference s = addLocations(method);
			if (s == null) {
				continue;
			}
			else {
				script = s;
			}
		}

		NashornEventQueue queue = (NashornEventQueue) vm.eventQueue();
		NashornScriptLoadEvent event = new NashornScriptLoadEvent(vm, vm.lookupThread(thread), script, null);
		NashornEventSet eventSet = new NashornEventSet(vm);

		EventRequestManager erm = vm.eventRequestManager();
		List<ScriptLoadRequest> scriptLoadRequests = erm.scriptLoadRequests();
		NashornThreadReference threadRef = vm.lookupThread(thread);
		for (ScriptLoadRequest request : scriptLoadRequests) {
			if (request.isEnabled()) {
				eventSet.add(new NashornScriptLoadEvent(vm, threadRef, script, request));
			}
		}
		
		eventSet.add(event);
		queue.put(eventSet);
		eventSet.awaitConsumer();
		
	}
	
	
	private NashornScriptReference addLocations(Method method) {
		try {
			List<Location> locations = method.allLineLocations();
			if (locations.isEmpty()) {
				return null;
			}
			String sourcePath = locations.get(0).sourceName();
			NashornScriptReference script = vm.lookupScript(sourcePath);
			if (script == null) {
				script = new NashornScriptReference(vm, sourcePath);
				vm.addScript(script);
			}
			log.trace("method {}", method);
			boolean first = true;
			for (Location loc : locations) {
				log.trace("    {}", loc);
				NashornLocation nashornLoc = new NashornLocation(script, loc);
				script.addLineLocation(nashornLoc);
				if (first) {
					first = false;
					script.addFunctionLocation(nashornLoc);
				}
			}
			return script;
		} catch (AbsentInformationException e) {
			// ignore
		}
		return null;
	}

	@Override
	public void breakpointRemoved(IJavaDebugTarget target,
			IJavaBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint breakpoint,
			DebugException exception) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint breakpoint,
			Message[] errors) {
		// TODO Auto-generated method stub
		
	}
}
