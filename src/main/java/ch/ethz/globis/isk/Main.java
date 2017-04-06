package ch.ethz.globis.isk;

public class Main  {
	
    public static void main(String[] args) {
    	MongoDomainDB db = new MongoDomainDB("database");
    	db.open();
    	try {
			db.parseXml();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	db.close();
    }
    
}
