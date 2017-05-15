package ch.ethz.globis.isk.mongodb;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

import ch.ethz.globis.isk.domain.mongodb.MongoDomainObject;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Database {
	
	private String name;
	private MongoClient mongoClient;
	
	//private IndexOptions unique = new IndexOptions().unique(true);
	private UpdateOptions upsert = new UpdateOptions().upsert(true);
	
	public MongoCollection<Document> conferences;
	public MongoCollection<Document> conferenceEditions;
	public MongoCollection<Document> persons;
	public MongoCollection<Document> publications;
	public MongoCollection<Document> publishers;
	public MongoCollection<Document> series;
	
	public Database(String name) {
        this.name = name;
	}
	
	public void open() {
		
		System.out.println("---### DEBUG: MongoDB: Connectind to localhost...");
		
        mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase(name);
        
        if(null == database){
        	System.out.println("---### ERROR: Could not connect to database. database == null");
        }else{
        	System.out.println("---### DEBUG: Loaded database with name " + database.getName());
        }
        
    	conferences 		= database.getCollection("conferences");
    	conferenceEditions 	= database.getCollection("conferenceEditions");
    	persons 			= database.getCollection("persons");
    	publications 		= database.getCollection("publications");
    	publishers 			= database.getCollection("publishers");
    	series 				= database.getCollection("series");
    	
    	//conferences.createIndex(Indexes.ascending("name"), unique);
    	//conferenceEditions.createIndex(Indexes.ascending("conference", "year"), unique);
    	//persons.createIndex(Indexes.ascending("name"), unique);
    	//publishers.createIndex(Indexes.ascending("name"), unique);
    	//series.createIndex(Indexes.ascending("name"), unique);
	}
	
	public void close() {
        mongoClient.close();
	}
    
    public void parseXml() throws Exception {
    	open();
    	
    	File dblbFiltered = new File("src/main/java/ch/ethz/globis/isk/dblp_filtered.xml");
		
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document document = builder.parse(dblbFiltered);
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element element = (Element) node;
				switch(element.getTagName()) {
				case "inproceedings":		parseInProceedings(element);	break;
				case "proceedings":			parseProceedings(element);		break;
				}
	        }
			node = node.getNextSibling();
		}
		
		close();
    }
    
    void parseInProceedings(Element element) {
    	String id = element.getAttribute("key");
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "author":
					publications.updateOne(
							eq("_id", id),
							addToSet("authors", value),
							upsert
					);
					persons.updateOne(
							eq("name", value),
							addToSet("authoredPublications", id),
							upsert
					);
					break;
				case "title":
					publications.updateOne(
							eq("_id", id),
							set("title", value),
							upsert
					);
					break;
				case "year":
					publications.updateOne(
							eq("_id", id),
							set("year", Integer.valueOf(value)),
							upsert
					);
					break;
				case "pages":
					publications.updateOne(
							eq("_id", id),
							set("pages", value),
							upsert
					);
					break;
				case "crossref":
					publications.updateOne(
							eq("_id", id),
							set("proceedings", value),
							upsert
					);
					publications.updateOne(
							eq("_id", value),
							addToSet("publications", id),
							upsert
					);
					break;
				}
			}
			node = node.getNextSibling();
		}
    }
    
    void parseProceedings(Element element) {
    	String id = element.getAttribute("key");

    	String conferenceName = null;
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "editor":
					publications.updateOne(
							eq("_id", id),
							addToSet("editors", value),
							upsert
					);
					persons.updateOne(
							eq("name", value),
							addToSet("editedPublications", id),
							upsert
					);
					break;
				case "title":
					publications.updateOne(
							eq("_id", id),
							set("title", value),
							upsert
					);
					break;
				case "publisher":
					publications.updateOne(
							eq("_id", id),
							set("publisher", value),
							upsert
					);
					publishers.updateOne(
							eq("name", value),
							addToSet("publications", id),
							upsert
					);
					break;
				case "series":
					publications.updateOne(
							eq("_id", id),
							set("series", value),
							upsert
					);
					series.updateOne(
							eq("name", value),
							addToSet("publications", id),
							upsert
					);
					break;
				case "year":
					publications.updateOne(
							eq("_id", id),
							set("year", Integer.valueOf(value)),
							upsert
					);
					publications.updateOne(
							eq("_id", id),
							set("conferenceEdition", id),
							upsert
					);
					conferenceEditions.updateOne(
							eq("_id", id),
							set("year", Integer.valueOf(value)),
							upsert
					);
					conferenceEditions.updateOne(
							eq("_id", id),
							set("proceedings", id),
							upsert
					);
					break;
				case "isbn":
					publications.updateOne(
							eq("_id", id),
							set("isbn", value),
							upsert
					);
					break;
				case "booktitle":
					publications.updateOne(
							eq("_id", id),
							set("conference", value),
							upsert
					);
					conferenceEditions.updateOne(
							eq("_id", id),
							set("conference", value),
							upsert
					);
					conferenceName = value;
					break;
				}
			}
			node = node.getNextSibling();
		}
		
		conferences.updateOne(
				eq("name", conferenceName),
				addToSet("editions", id),
				upsert
		);
    }
    
    public <T extends MongoDomainObject> MongoCursor<Document> find() {
    	MongoCursor<Document> cursor = publications.find().iterator();
    	return cursor;
    }
 }
