package org.eclipse.wst.jsdt.debug.nashorn.launch;

import java.util.HashMap;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.debug.core.IJavaClassPrepareBreakpoint;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.JDIDebugModel;
import org.eclipse.wst.jsdt.debug.core.jsdi.VirtualMachine;
import org.eclipse.wst.jsdt.debug.internal.core.model.JavaScriptDebugTarget;
import org.eclipse.wst.jsdt.debug.internal.nashorn.JavaClassPrepareWildcardBreakpoint;
import org.eclipse.wst.jsdt.debug.internal.nashorn.NashornDebugPlugin;
import org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi.NashornVirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugTargetCreationListener implements IDebugEventSetListener {

	private static final String SCRIPT_PATTERN = "jdk.nashorn.internal.scripts.Script*";

	private static Logger log = LoggerFactory
			.getLogger(DebugTargetCreationListener.class);

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			if (event.getSource() instanceof IJavaDebugTarget) {
				IJavaDebugTarget target = (IJavaDebugTarget) event.getSource();

				switch (event.getKind()) {

				case DebugEvent.CREATE:
					handleCreateEvent(target);
					break;

				case DebugEvent.TERMINATE:
					handleTerminateEvent(target);
				}
			}
		}
	}

	private void handleCreateEvent(IJavaDebugTarget target) {
		ILaunch launch = target.getLaunch();
		IProcess process = target.getProcess();
		NashornVirtualMachine vm = new NashornVirtualMachine(target);
		createScriptLoadBreakpoints(vm, target);
		JavaScriptDebugTarget jsTarget = new JavaScriptDebugTarget(vm, process,
				launch, true, false);
	}

	private void handleTerminateEvent(IJavaDebugTarget target) {
		ILaunch launch = target.getLaunch();
		for (IDebugTarget t : launch.getDebugTargets()) {
			if (t instanceof JavaScriptDebugTarget) {

				JavaScriptDebugTarget jsTarget = (JavaScriptDebugTarget) t;
				VirtualMachine vm = jsTarget.getVM();
				if (vm instanceof NashornVirtualMachine) {
					NashornVirtualMachine nashornVm = (NashornVirtualMachine) vm;
					NashornScriptLoadBreakpointListener javaBreakpointListener = nashornVm
							.getJavaBreakpointListener();
					JDIDebugModel
							.removeJavaBreakpointListener(javaBreakpointListener);
				}
				jsTarget.shutdown();
			}
		}
	}

	private void createScriptLoadBreakpoints(NashornVirtualMachine vm,
			IJavaDebugTarget target) {
		NashornScriptLoadBreakpointListener javaBreakpointListener = new NashornScriptLoadBreakpointListener(
				vm);
		vm.setJavaBreakpointListener(javaBreakpointListener);
		JDIDebugModel.addJavaBreakpointListener(javaBreakpointListener);

		log.debug("creating ClassPrepare breakpoint for {}", SCRIPT_PATTERN);
		try {
			IJavaClassPrepareBreakpoint bp = new JavaClassPrepareWildcardBreakpoint(
					ResourcesPlugin.getWorkspace().getRoot(), SCRIPT_PATTERN,
					IJavaClassPrepareBreakpoint.TYPE_CLASS, -1, -1, false,
					new HashMap<String, Object>());
			bp.setPersisted(false);
			target.breakpointAdded(bp);
			bp.addBreakpointListener("org.eclipse.wst.jsdt.debug.nashorn.breakpointListener");
		} catch (CoreException e) {
			NashornDebugPlugin.log(e);
		}
	}
}
