package com.future.jdk8.js.engine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Date;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class Nashorn {
	
	static String fun1(String name) {
	    System.out.format("Hi there from Java, %s", name);
	    return "greetings from java";
	}
	
	@Test                                                                          
	public void test1() throws ScriptException {                                   
	    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	    engine.eval("print('Hello World!');");                                     
	}
	
	@Test                                                                          
	public void test2() throws ScriptException, FileNotFoundException {            
	    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	    engine.eval(new FileReader("E:\\GitHub\\微服务\\boot-maven-docker-compose\\src\\main\\java\\com\\future\\jdk8\\fn.js"));                                  
	}
	
	@Test                                                                          
	public void test3() throws Exception {                                         
	    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	    engine.eval(new FileReader("E:\\GitHub\\微服务\\boot-maven-docker-compose\\src\\main\\java\\com\\future\\jdk8\\fn.js"));                                  

	    Invocable invocable = (Invocable) engine;                                  

	    Object result = invocable.invokeFunction("fun1", "Peter Parker");          
	    System.out.println(result);                                                
	    System.out.println(result.getClass());        
	} 
	@Test                                                                          
	public void test4() throws Exception {                                         
	    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	    engine.eval(new FileReader("E:\\GitHub\\微服务\\boot-maven-docker-compose\\src\\main\\java\\com\\future\\jdk8\\fn.js"));                                  

	    Invocable invocable = (Invocable) engine;                                  

	    invocable.invokeFunction("fun2", new Date());                              
	    // [object java.util.Date]                                                 

	    invocable.invokeFunction("fun2", LocalDateTime.now());                     
	    // [object java.time.LocalDateTime]                                        

	    invocable.invokeFunction("fun2", new Nashorn());                          
	    // [object common.Employee]                                                
	}
}
