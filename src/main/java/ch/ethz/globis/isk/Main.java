package ch.ethz.globis.isk;

import javax.jdo.PersistenceManager;

import org.zoodb.jdo.ZooJdoHelper;

public class Main 
{
    public static void main(String[] args)
    {
    	System.out.println("Hello World!");
    	PersistenceManager pm = ZooJdoHelper.openOrCreateDB("database");
    }
}
