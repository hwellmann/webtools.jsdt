package org.eclipse.wst.jsdt.debug.internal.nashorn;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaClassPrepareBreakpoint;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jdi.ReferenceType;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.event.ClassPrepareEvent;

public class JavaClassPrepareWildcardBreakpoint extends
		JavaClassPrepareBreakpoint {

	private static final String WILDCARD = "*";
	
	private static Logger log = LoggerFactory
			.getLogger(JavaClassPrepareWildcardBreakpoint.class);

	private Map<IJavaThread, ReferenceType> threadToType = new HashMap<IJavaThread, ReferenceType>();

	public JavaClassPrepareWildcardBreakpoint(final IResource resource,
			final String typeName, final int memberType, final int charStart,
			final int charEnd, final boolean add,
			final Map<String, Object> attributes) throws DebugException {
		super(resource, typeName, memberType, charStart, charEnd, add,
				attributes);

	}

	@Override
	public boolean handleClassPrepareEvent(ClassPrepareEvent event,
			JDIDebugTarget target, boolean suspendVote) {
		try {
			if (isEnabled() && matchesPattern(event)) {
				ThreadReference threadRef = event.thread();
				JDIThread thread = target.findThread(threadRef);
				if (thread == null || thread.isIgnoringBreakpoints()) {
					return true;
				}

				ReferenceType type = event.referenceType();
				threadToType.put(thread, type);
				return handleBreakpointEvent(event, thread, suspendVote);
			}
		} catch (CoreException e) {
		}
		return true;
	}

	private boolean matchesPattern(ClassPrepareEvent event)
			throws CoreException {
		String className = event.referenceType().name();
		String pattern = getTypeName();
		boolean isMatch = false;
		if (pattern.startsWith(WILDCARD)) {
			isMatch = className.endsWith(pattern.substring(1));
		} else if (pattern.endsWith(WILDCARD)) {
			isMatch = className.startsWith(pattern.substring(0,
					pattern.length() - 1));
		} else {
			isMatch = className.equals(pattern);
		}
		if (isMatch) {
			log.debug("ClassPrepare type = {}, pattern = {}", className,
					pattern);
		}
		return isMatch;
	}

	public ReferenceType getReferenceType(IJavaThread thread) {
		return threadToType.get(thread);
	}
}
