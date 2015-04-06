package org.eclipse.wst.jsdt.debug.nashorn.model;

import java.util.List;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.internal.debug.core.model.JDIDebugTarget;
import org.eclipse.wst.jsdt.debug.core.model.IJavaScriptDebugTarget;
import org.eclipse.wst.jsdt.debug.core.model.IScriptGroup;

public class NashornJavaScriptDebugTarget implements IJavaScriptDebugTarget {
	
	private IJavaDebugTarget javaTarget;

	public NashornJavaScriptDebugTarget(IJavaDebugTarget jdiTarget) {
		this.javaTarget = jdiTarget;
	}

	@Override
	public IProcess getProcess() {
		return javaTarget.getProcess();
	}

	@Override
	public IThread[] getThreads() throws DebugException {
		return javaTarget.getThreads();
	}

	@Override
	public boolean hasThreads() throws DebugException {
		return false;
	}

	@Override
	public String getName() throws DebugException {
		return javaTarget.getName() + " (Nashorn)";
	}

	@Override
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getModelIdentifier() {
		return javaTarget.getModelIdentifier();
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return javaTarget.getDebugTarget();
	}

	@Override
	public ILaunch getLaunch() {
		return javaTarget.getLaunch();
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return javaTarget.getAdapter(adapter);
	}

	@Override
	public boolean canTerminate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated() {
		return javaTarget.isTerminated();
	}

	@Override
	public void terminate() throws DebugException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canResume() {
		return javaTarget.canResume();
	}

	@Override
	public boolean canSuspend() {
		return javaTarget.canSuspend();
	}

	@Override
	public boolean isSuspended() {
		return javaTarget.isSuspended();
	}

	@Override
	public void resume() throws DebugException {
		javaTarget.resume();
	}

	@Override
	public void suspend() throws DebugException {
		javaTarget.suspend();
	}

	@Override
	public void breakpointAdded(IBreakpoint breakpoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canDisconnect() {
		return false;
	}

	@Override
	public void disconnect() throws DebugException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDisconnected() {
		return false;
	}

	@Override
	public boolean supportsStorageRetrieval() {
		return false;
	}

	@Override
	public IMemoryBlock getMemoryBlock(long startAddress, long length)
			throws DebugException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List allScripts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List allScriptsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScriptGroup getScriptGroup() {
		// TODO Auto-generated method stub
		return null;
	}

}
