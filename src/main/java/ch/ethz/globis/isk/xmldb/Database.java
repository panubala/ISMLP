package ch.ethz.globis.isk.xmldb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.globis.isk.xmldb.api.BaseXClient;
import ch.ethz.globis.isk.xmldb.api.BaseXClient.Query;

public class Database {
	
	private BaseXClient session;
	
	public Database() {
		
	}
	
	public void open() {
		try {
			session = new BaseXClient("localhost", 1984, "admin", "admin");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Query execute(String input) {			
		try {
			return session.query(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Query executeFile(String fileName) {
		return execute(readFile(fileName));
	}
    
    public String readFile(String fileName) {
    	try {
	    	InputStream is = new FileInputStream("xml/" + fileName);
	    	BufferedReader buf = new BufferedReader(new InputStreamReader(is));
	    	String line = buf.readLine();
	    	StringBuilder sb = new StringBuilder();
	    	while(line != null) {
	    		sb.append(line).append("\n");
	    		line = buf.readLine();
	    	}
	    	String fileAsString = sb.toString();
	    	buf.close();
	    	return fileAsString;
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
 }
