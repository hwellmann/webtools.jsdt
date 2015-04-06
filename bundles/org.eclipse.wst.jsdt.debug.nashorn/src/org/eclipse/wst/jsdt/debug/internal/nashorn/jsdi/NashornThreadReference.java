package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi;

import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.wst.jsdt.debug.core.jsdi.StackFrame;
import org.eclipse.wst.jsdt.debug.core.jsdi.ThreadReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.VirtualMachine;

public class NashornThreadReference implements ThreadReference {
	
	private IThread thread;
	private VirtualMachine vm;

	public NashornThreadReference(IThread thread, VirtualMachine vm) {
		this.thread = thread;
		this.vm = vm;
	}

	@Override
	public VirtualMachine virtualMachine() {
		return vm;
	}

	@Override
	public int frameCount() {
		try {
			return thread.getStackFrames().length;
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public StackFrame frame(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List frames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void interrupt() {
		// TODO Auto-generated method stub
		
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
	public int status() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAtBreakpoint() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSuspended() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		try {
			return thread.getName();
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "<unnamed>";
	}

}
