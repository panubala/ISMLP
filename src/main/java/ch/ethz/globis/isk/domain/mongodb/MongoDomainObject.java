package ch.ethz.globis.isk.domain.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public abstract class MongoDomainObject {
	
	String id;
	
	public MongoDomainObject() {
		
	}
	
	public MongoDomainObject(String id) {
		this.id = id;
	}
	
	public String getWindowTitle() {
		return "Domain Objects";
	}
	
	public List<String> getColumnNames() {
		List<String> columnNames = new ArrayList<>();
		columnNames.add("ID");
		return columnNames;
	}
	
	public void fromDocument(Document document) {
		id = document.getString("_id");
	}
    
    public Document toDocument() {
    	Document document = new Document();
    	if (id != null)
    		document.append("_id", id);
    	return document;
    }
	
    public String getId() {
    	return id;
    }

    public void setId(String id) {
    	this.id = id;
    }
    
}
