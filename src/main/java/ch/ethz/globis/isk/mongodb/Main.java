package ch.ethz.globis.isk.mongodb;

public class Main  {
	
    public static void main(String[] args) {
    	Database db = new Database("database");
    	db.open();
    	try {
			db.parseXml();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	db.close();
    }
}
