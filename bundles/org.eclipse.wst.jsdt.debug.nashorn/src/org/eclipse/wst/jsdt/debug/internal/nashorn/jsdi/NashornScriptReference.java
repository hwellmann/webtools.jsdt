package org.eclipse.wst.jsdt.debug.internal.nashorn.jsdi;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.wst.jsdt.debug.core.jsdi.Location;
import org.eclipse.wst.jsdt.debug.core.jsdi.ScriptReference;
import org.eclipse.wst.jsdt.debug.core.jsdi.VirtualMachine;

public class NashornScriptReference implements ScriptReference {
	
	private NashornVirtualMachine vm;
	private String sourcePath;
	private List<Location> lineLocations = new ArrayList<Location>();
	private Map<String, Location> functionLocations = new HashMap<String, Location>();
	
	public NashornScriptReference(NashornVirtualMachine vm, String sourcePath) {
		this.vm = vm;
		this.sourcePath = sourcePath;
	}

	@Override
	public NashornVirtualMachine virtualMachine() {
		return vm;
	}

	@Override
	public List<Location> allLineLocations() {
		return Collections.unmodifiableList(lineLocations);
	}

	@Override
	public Location lineLocation(int lineNumber) {
		for (Location loc : lineLocations) {
			if (loc.lineNumber() == lineNumber) {
				return loc;
			}
		}
		return null;
	}

	@Override
	public List<Location> allFunctionLocations() {
		return new ArrayList<Location>(functionLocations.values());
	}

	@Override
	public Location functionLocation(String functionName) {
		return functionLocations.get(functionName);
	}

	@Override
	public String source() {
		return null;
	}

	@Override
	public URI sourceURI() {
		String path = sourcePath.replaceAll("<eval>", "_eval_");
		return URI.create(path);
	}

	public void addLineLocation(Location location) {
		lineLocations.add(location);
	}
	
	public void addFunctionLocation(Location location) {
		functionLocations.put(location.functionName(), location);
	}
}
