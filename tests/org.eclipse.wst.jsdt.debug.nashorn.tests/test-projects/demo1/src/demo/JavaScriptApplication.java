package demo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


/**
 * @author hwellmann
 *
 */
public class JavaScriptApplication {

    /**
     * @param args
     * @throws ScriptException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws ScriptException, InterruptedException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        nashorn.eval("load('src/counter.js')");
     }

}
