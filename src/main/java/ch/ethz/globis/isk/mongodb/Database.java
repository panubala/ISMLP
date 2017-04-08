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
        mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase(name);
        
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
							push("authors", value),
							upsert
					);
					persons.updateOne(
							eq("name", value),
							push("authoredPublications", id),
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
							push("publications", id),
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
							push("editors", value),
							upsert
					);
					persons.updateOne(
							eq("name", value),
							push("editedPublications", id),
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
							push("publications", id),
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
							push("publications", id),
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
				push("editions", id),
				upsert
		);
    }
    
    public <T extends MongoDomainObject> MongoCursor<Document> find() {
    	MongoCursor<Document> cursor = publications.find().iterator();
    	return cursor;
    }
/*    
    public <T extends ZooPC>T getById(Class<T> c, String id) {
        String filter = "id == '" + id + "'";
        Collection<T> collection = getWithFilter(c, filter);
        if(collection.isEmpty()){
        	return null;
        }else if(collection.size() > 1){
        	throw new RuntimeException("Non-unique ID");
        }else{
        	return (T) collection.iterator().next();
        }
    }
    
    public <T extends ZooPC> Collection<T> getWithFilter(Class<T> c, String filter) {
		return getWithFilter(c, filter, null);
    }
    
    public <T extends ZooPC> Collection<T> getWithFilter(Class<T> c, String filter, String variables) {
    	assert !pm.isClosed() && pm.currentTransaction().isActive();
		Query query = pm.newQuery(c, filter);
		if (variables != null)
			query.declareVariables(variables);
		Collection<T> collection = (Collection<T>) query.execute();
		return collection;
    }

    //---------------------- Quick access functions -------------------
    
    // 1.) Find publication by id
    public ZooPublication getPublicationById(String id){
    	return getById(ZooPublication.class, id);
    }
    
    // Helper function for 2.) and 3.)
    private Collection<ZooPublication> getPublicationsWithFilter(String filter, int beginOffset, int endOffset) {
    	Collection<ZooPublication> collection = getWithFilter(ZooPublication.class, filter);
    	
    	List<ZooPublication> publicationList = new ArrayList<ZooPublication>(collection);
    	List<ZooPublication> resultList      = new ArrayList<ZooPublication>();
    	
    	
    	// Assume the input should be the other way around
    	if(beginOffset > endOffset){
    		return getPublicationsWithFilter(filter, endOffset, beginOffset);
    	}
    	// If the list is shorter than the Offset, return an empty list
    	if(beginOffset >= publicationList.size()){
    		return resultList;
    	}
    	// Handle separately because subList() would return an empty list
    	if(beginOffset == endOffset){
    		resultList.add(publicationList.get(beginOffset));
    		return resultList;
    	}

    	// To avoid an error, handle this case separately
    	if(endOffset >= publicationList.size()){
    		return publicationList.subList(beginOffset, publicationList.size());

    	}
    	
    	resultList = publicationList.subList(beginOffset, endOffset);
    	
    	return collection;
    }
    
    // 2.) Find publication by filter (title, begin-offset, end-offset)
    public Collection<ZooPublication> getPublicationsByTitle(String title, int beginOffset, int endOffset) {
    	String filter = "title.contains('" + title + "') range " + beginOffset + ", " + endOffset;
    	return getWithFilter(ZooPublication.class, filter);
    }
    
    // 3.) Find publication by filter ordered by name
    public Collection<ZooPublication> getPublicationsByTitleOrdered(String title, int beginOffset, int endOffset) {
    	String filter = "title.contains('" + title + "') order by title asc range " + beginOffset + ", " + endOffset;
    	return getWithFilter(ZooPublication.class, filter);
    }
    
    // 4.) Find co-authors of a person by name
    public Collection<ZooPerson> getCoAuthors(String name) throws JDOObjectNotFoundException {
    	Collection<ZooPerson> authors = getWithFilter(ZooPerson.class, "name == '" + name + "'");
    	if (authors.isEmpty())
    		throw new JDOObjectNotFoundException("Author " + name + " not found.");
    	ZooPerson author = authors.iterator().next();
    	return getCoAuthors(author);
    }
    
    // 4.) Find co-authors of a person
    private Collection<ZooPerson> getCoAuthors(ZooPerson author) {
    	Collection<ZooPerson> coAuthors = new ArrayList<>();
    	for (Publication publication : author.getAuthoredPublications()) {
        	for (Person coAuth : publication.getAuthors()) {
        		ZooPerson coAuthor = (ZooPerson) coAuth;
    			if (coAuthor != author) {
    				coAuthors.add(coAuthor);
    			}
        	}
    	}
    	return coAuthors;
    }
    
    // 5.) Shortest Path between two authors by name
    public int getShortestAuthorPath(String name1, String name2) throws JDOObjectNotFoundException {
    	Collection<ZooPerson> authors = getWithFilter(ZooPerson.class, "name == '" + name1 + "'");
    	if (authors.isEmpty())
    		throw new JDOObjectNotFoundException("Author " + name1 + " not found.");
    	ZooPerson author1 = authors.iterator().next();
    	
    	authors = getWithFilter(ZooPerson.class, "name == '" + name2 + "'");
    	if (authors.isEmpty())
    		throw new JDOObjectNotFoundException("Author " + name2 + " not found.");
    	ZooPerson author2 = authors.iterator().next();
    	
    	return getShortestAuthorPath(author1, author2);
    }
    
    // 5.) Shortest Path between two authors
    private int getShortestAuthorPath(ZooPerson author1, ZooPerson author2) throws JDOObjectNotFoundException {
    	List<ZooPerson> authors = new ArrayList<>();
    	List<ZooPerson> doneAuthors = new ArrayList<>();
    	authors.add(author1);
    	
    	int shortestPath = 0;
    	
    	while (!authors.isEmpty()) {    		
    		for (ZooPerson author : authors) {
    			if (author.equals(author2)){
    				return shortestPath;
    			}
    		}
    		
    		shortestPath++;
    		for (int i = 0; i < authors.size(); i++) {
    			ZooPerson doneAuthor = authors.get(i);
    			for (ZooPerson author : getCoAuthors(doneAuthor.getName())) {
    				if (!doneAuthors.contains(author))
    						authors.add(author);
    			}
    			authors.remove(doneAuthor);
    			doneAuthors.add(doneAuthor);
    		}
    	}
    	
    	throw new JDOObjectNotFoundException("Author " + author1.getName() + " and author " + author2.getName() + " are not connected.");
    }
    
    // 6.) Compute global average of authors per publication
    public float getGlobalAverageAuthors() {
    	Collection<ZooInProceedings> inProceedings = getWithFilter(ZooInProceedings.class, "");
    	int count = 0;
    	for (ZooInProceedings inProceeding : inProceedings)
    		count += inProceeding.getAuthors().size();
    	return count / (float) inProceedings.size();
    }

    // 7.) Count the number of publications per year in a given interval of years.
    public Collection<Integer> getPublicationsPerYear(int yearBegin, int yearEnd){

    	List<Integer> list = new ArrayList<Integer>();
    	
    	for(int i = yearBegin; i <= yearEnd; i++){
    		list.add(getWithFilter(ZooPublication.class, "this.year == " + i).size());
    	}
    	
    	return list;
    }
    
    // 8.) Count the number of publications by conference
    //TODO
//    public int getNumPublicationsByConference(String conferenceName){
//    	
//    	
//    	return null;
//    }
    
    // 9.) Count all authors and editors of a conference
    //TODO
    public int getNumAuthorsAndEditiorsByConference(String conferenceName){
    	return 0;
    }
    
    
    //10.) Retrieve all authors of a given conference
    //TODO: Implement
    public List<ZooPerson> getAllAuthorsByConference(Conference conference){
    	
////    	List<ZooPerson> authors = (List<ZooPerson>) getWithFilter(ZooPerson.class, "");
//    	
//    	Set<ConferenceEdition> editions = conference.getEditions();
//    	
//    	List<Proceedings> proceedings = new ArrayList<>();
//    	
//    	for(ConferenceEdition edition: editions){
//    		proceedings.add(edition.getProceedings());
//    	}
//    	
//    	List<Proceedings> publications = new ArrayList<>();
//    	
//    	for(Proceedings preceeding:proceedings ){
////    		publications.addAll(preceeding.getPublications());
//    	}
//    		
    	
    	return null;
    }
    
    //11.) Retrieve all publications of a given conference
    //TODO: Implement
    
    public List<ZooProceedings> getAllPublicationsByConference(String conference){
	
    	
    	Collection<ZooProceedings> proceedings = getWithFilter(ZooProceedings.class, "");
    	
    	List<ZooProceedings> publications = new ArrayList<>();
    	
    	for(ZooProceedings proceeding:proceedings){
    		if (proceeding.getConferenceEdition().getConference().getName().equals(conference) && !publications.contains(proceeding)){
    			publications.add(proceeding);
    		};
    	}
    	
    	return publications;
    }
    
    //12.) Retrieve a list of persons which were at the same time author in an InProceedings and editor in the appropriate Proceedings.
    //TODO
    public List<ZooPerson> getAllPersonByAuthorInPreAndEditorInPre(String name){
    	
//    	Collection<ZooPerson> authors = getWithFilter(ZooPerson.class, "name == '" + name + "'");
//    	Set<Publication> authoredPublications = new HashSet<Publication>();
//    	Set<Publication> editedPublications = new HashSet<Publication>();
//    	
//    	for(ZooPerson author: authors){
//    		authoredPublications.addAll(author.getAuthoredPublications());
//    		editedPublications.addAll(author.getEditedPublications());
//    	}
//    	
//    	Collection<ZooInProceedings> inPreceedings = getWithFilter(ZooInProceedings.class, "");
//    	
//    	for(ZooInProceedings inPreceeding: inPreceedings){
//    		inPreceeding.getProceedings().getPublications();
//    		
//    	}
    	return null;
    }
    
    //13.) List of all publications where a given author appears as the last author.
    public List<ZooPublication> getAllPublicationsByLastAuthor(String name){
    	Collection<ZooPublication> publications = getWithFilter(ZooPublication.class, "");
    	
    	List<Person> authors = new ArrayList<>();
    	List<ZooPublication> publicationsList = new ArrayList<>();
    	
    	for(ZooPublication publication: publications){
    		authors.addAll(publication.getAuthors());
    		
    		if (authors.get(authors.size() - 1).getName().equals(name)){
    			publicationsList.add(publication);
    		}
    		
    		authors.clear();
    	}
    	
    	return publicationsList;
    }
    
    //14.) Retrieve the list of publishers of Proceedings whose authors appear in any InProceedings 
    //		in a range of years, e.g. between 1982 and 1986.
    
    public List<Publisher> getAllPublisherInRange(int year1, int year2){
    	Collection<ZooConferenceEdition> editions = getWithFilter(ZooConferenceEdition.class, "");
    	
    	List<ZooConferenceEdition> checkedEditions = new ArrayList<>();
    	
    	for(ZooConferenceEdition edition: editions){
    		if (year1 <= edition.getYear() && edition.getYear() <= year2 ){
    			checkedEditions.add(edition);	
    		}
    	}
    	
    	List<Proceedings> proceedings = new ArrayList<>();
    	
    	for(ZooConferenceEdition checkedEdition: checkedEditions){
    		proceedings.add(checkedEdition.getProceedings());
    	}
    	
    	List<Publisher> publisher = new ArrayList<>();
    	
    	for(Proceedings proceeding: proceedings){
    		if (!publisher.contains(proceeding.getPublisher())){
    			publisher.add(proceeding.getPublisher());
    		};
    	}
    	
    	return publisher;
    }
 */   
 }
