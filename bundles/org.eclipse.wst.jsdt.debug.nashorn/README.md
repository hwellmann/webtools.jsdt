Nashorn Debugging Support for JSDT (Experimental)
===================================================

Goals
-----

* Run and debug mixed Java/JavaScript applications using the Nashorn JavaScript engine of JDK 8.
* Set breakpoints in JavaScript sources and see the JVM stopping when running the script under Nashorn.
* Inspect mixed Java/JavaScript callstacks.
* Switch from Java to JavaScript variable inspection when the current stack frame belongs to a Nashorn script class.

Design
------

* New bundle `org.eclipse.wst.jsdt.debug.nashorn`, using `*.jsdt.debug.rhino` as a starting point.
* No separate UI bundle for now.
* Currently, `org.eclipse.wst.jsdt.debug.nashorn.tests` just contains some test sources, but no automatic tests.
* The `*.jsdt.debug.nashorn` bundle contributes an alternative launch delegate for local Java applications.
* You launch a Nashorn application via the default Java launcher and then select the Nashorn delegate to enable Nashorn debugging.
* A JavaScriptDebugTarget is added to the launch, in addition to the default IJavaDebugTarget. This new JavaScript target wraps the standard Java debug target, translating JavaScript breakpoints to Java breakpoints in the corresponding Nashorn script classes and mapping Java breakpoint events to the corresponding JavaScript breakpoint events.
* Since Nashorn Java classes do not contain a stratum for the original JavaScript source information, we obtain JavaScript source locations by installing a class prepare breakpoint for `jdk.nashorn.internal.scripts.Script*` and by extracting line locations for all methods of the script class when the breakpoint is hit.


Giving it a try
---------------

* Get an Eclipse SDK with source bundles. I'm currently using Luna SR1.
* Clone this fork of JSDT and import it into your workspace: `https://github.com/hwellmann/webtools.jsdt`.
* Launch `org.eclipse.wst.jsdt.debug.nashorn` via **Run As | Eclipse Application**.
* In the lauched Eclipse instance, create a Java project with the sources from `org.eclipse.wst.jsdt.debug.nashorn.tests/test-projects/demo1`.
* Set a breakpoint in `src/counter.js` line 4.
* Launch `src/demo/JavaScriptApplication.java` via **Debug As | Java Application**.
* Notice the two debug targets.
* See the breakpoint being hit with a call stack more or less like this:

    Thread [main] (Suspended (breakpoint at line 4 in Script$Recompilation$2$15$counter))       
        Script$Recompilation$2$15$counter.foo(ScriptFunction, Object) line: 4   
        905654280.invokeStatic_LL_L(Object, Object, Object) line: not available 
        1894369629.reinvoke(Object, Object, Object) line: not available 
        1418385211.exactInvoker(Object, Object, Object) line: not available     
        844112759.linkToCallSite(Object, Object, Object) line: not available    
        Script$1$counter.:program(ScriptFunction, Object) line: 9       
        905654280.invokeStatic_LL_L(Object, Object, Object) line: not available 
        293002476.invokeExact_MT(Object, Object, Object, Object) line: not available    
        RecompilableScriptFunctionData(ScriptFunctionData).invoke(ScriptFunction, Object, Object...) line: 636  
        ScriptFunctionImpl(ScriptFunction).invoke(Object, Object...) line: 229  
        ScriptRuntime.apply(ScriptFunction, Object, Object...) line: 387        
        Context.evaluateSource(Source, ScriptObject, ScriptObject) line: 1150   
        Context.load(ScriptObject, Object) line: 799    
        Global.load(Object, Object) line: 995   
        1359484306.invokeStaticInit_LL_L(Object, Object, Object) line: not available    
        888473870.reinvoke(Object, Object, Object, Object) line: not available  
        1687940142.exactInvoker(Object, Object, Object, Object) line: not available     
        1765795529.linkToCallSite(Object, Object, Object, Object) line: not available   
        Script$\^eval\_.:program(ScriptFunction, Object) line: 1        
        905654280.invokeStatic_LL_L(Object, Object, Object) line: not available 
        293002476.invokeExact_MT(Object, Object, Object, Object) line: not available    
        RecompilableScriptFunctionData(ScriptFunctionData).invoke(ScriptFunction, Object, Object...) line: 636  
        ScriptFunctionImpl(ScriptFunction).invoke(Object, Object...) line: 229  
        ScriptRuntime.apply(ScriptFunction, Object, Object...) line: 387        
        NashornScriptEngine.evalImpl(ScriptFunction, ScriptContext, Global) line: 437   
        NashornScriptEngine.evalImpl(ScriptFunction, ScriptContext) line: 401   
        NashornScriptEngine.evalImpl(Source, ScriptContext) line: 397   
        NashornScriptEngine.eval(String, ScriptContext) line: 152       
        NashornScriptEngine(AbstractScriptEngine).eval(String) line: 264        
        JavaScriptApplication.main(String[]) line: 22   

* That's all for now.

Known issues
------------
* This is a proof-of-concept pre-alpha version.
* Due to missing clean-up, the breakpoint will not be hit when launching `JavaScriptApplication` a second time. You have to relaunch Eclipse (the "inner" one). 
       