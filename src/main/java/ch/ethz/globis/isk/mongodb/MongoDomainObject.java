package ch.ethz.globis.isk.mongodb;

import org.bson.Document;

public abstract class MongoDomainObject {
	
	String id;
	
	public MongoDomainObject() {
		
	}
	
	public MongoDomainObject(String id) {
		this.id = id;
	}
	
    public String getId() {
    	return id;
    }

    public void setId(String id) {
    	this.id = id;
    }
    
    public Document toDocument() {
    	Document document = new Document();
    	if (id != null)
    		document.append("_id", id);
    	return document;
    }
    
}
