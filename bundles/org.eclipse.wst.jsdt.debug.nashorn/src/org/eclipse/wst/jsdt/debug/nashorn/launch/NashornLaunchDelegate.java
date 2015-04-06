package org.eclipse.wst.jsdt.debug.nashorn.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jdt.launching.JavaLaunchDelegate;

public class NashornLaunchDelegate extends JavaLaunchDelegate {

	public NashornLaunchDelegate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		super.launch(configuration, mode, launch, monitor);
	}

}
