package ch.ethz.globis.isk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.zoodb.api.impl.ZooPC;
import org.zoodb.jdo.ZooJdoHelper;
import org.zoodb.tools.ZooHelper;

import ch.ethz.globis.isk.domain.Conference;
import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.zoodb.ZooConference;
import ch.ethz.globis.isk.domain.zoodb.ZooConferenceEdition;
import ch.ethz.globis.isk.domain.zoodb.ZooInProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;
import ch.ethz.globis.isk.domain.zoodb.ZooPublisher;
import ch.ethz.globis.isk.domain.zoodb.ZooSeries;

@SuppressWarnings("unchecked")
public class ZooDatabase {
	
	String name;
	PersistenceManager pm;
	
	public ZooDatabase(String name, boolean overwrite) {
        this.name = name;
        if (ZooHelper.dbExists(name)) {
        	if (!overwrite)
        		return;
            ZooHelper.removeDb(name);
        }
        ZooHelper.createDb(name);
    	pm = ZooJdoHelper.openDB(name);
    	
		try {
	    	pm.currentTransaction().begin();
	    	pm.currentTransaction().setRetainValues(true);
	    	List<ZooPublication> publications = parseXml();
	    	for (ZooPublication publication : publications)
	    		pm.makePersistent(publication);
	    	pm.currentTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	        if (pm.currentTransaction().isActive()) {
	            pm.currentTransaction().rollback();
	        }
	    	pm.close();
	        pm.getPersistenceManagerFactory().close();
		}
	}
    
    List<ZooPublication> parseXml() throws Exception {
		List<ZooPublication> publications = new ArrayList<>();
		
		File dblbFiltered = new File("src/main/java/ch/ethz/globis/isk/dblp_filtered.xml");
		
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(dblbFiltered);
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element element = (Element) node;
				switch(element.getTagName()) {
				case "inproceedings":		publications.add(parseInProceedings(element));	break;
				case "proceedings":			publications.add(parseProceedings(element));	break;
				}
	        }
			node = node.getNextSibling();
		}
		
		return publications;
    }
    
    ZooInProceedings parseInProceedings(Element element) {
    	ZooInProceedings inProceedings = new ZooInProceedings();
    	inProceedings.setId(element.getAttribute("key"));
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "author":
					ZooPerson author = new ZooPerson();
					author.setName(value);
					author.addAuthoredPublication(inProceedings);
					inProceedings.addAuthor(author);
					break;
				case "title":
					inProceedings.setTitle(value);
					break;
				case "year":
					inProceedings.setYear(Integer.valueOf(value));
					break;
				case "pages":
					inProceedings.setPages(value);
					break;
				case "crossref":
					ZooProceedings proceedings = getById(ZooProceedings.class, value);
					if (proceedings == null) {
						proceedings = new ZooProceedings();
						proceedings.setId(value);
					}
					proceedings.addPublication(inProceedings);
					break;
				}
			}
			node = node.getNextSibling();
		}
		
		return inProceedings;
    }
    
    ZooProceedings parseProceedings(Element element) {
    	String id = element.getAttribute("key");
		ZooProceedings proceedings = getById(ZooProceedings.class, id);
		if (proceedings == null) {
			proceedings = new ZooProceedings();
			proceedings.setId(id);
		}
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            Conference conference;
	            ConferenceEdition conferenceEdition;
	            Collection<ZooConferenceEdition> zooConferenceEditions;
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "editor":
					ZooPerson editor = new ZooPerson();
					editor.setName(value);
					editor.addEditedPublication(proceedings);
					proceedings.addAuthor(editor);
					break;
				case "title":
					proceedings.setTitle(value);
					break;
				case "publisher":
					ZooPublisher publisher = new ZooPublisher();
					publisher.setName(value);
					publisher.addPublication(proceedings);
					proceedings.setPublisher(publisher);
					break;
				case "series":
					ZooSeries series = new ZooSeries();
					series.setName(value);
					series.addPublication(proceedings);
					proceedings.setSeries(series);
					break;
				case "year":
					proceedings.setYear(Integer.valueOf(value));
					
					zooConferenceEditions = getWithFilter(ZooConferenceEdition.class, "proceedings.id == '" + proceedings.getId() + "'");
					conferenceEdition = zooConferenceEditions.isEmpty() ? null : zooConferenceEditions.iterator().next();
					if (conferenceEdition == null) {
						conferenceEdition = new ZooConferenceEdition();
						conferenceEdition.setProceedings(proceedings);
						conference = new ZooConference();
						conference.addEdition(conferenceEdition);
						conferenceEdition.setConference(conference);
					}
					conferenceEdition.setYear(proceedings.getYear());
					break;
				case "isbn":
					proceedings.setIsbn(value);
					break;
				case "bookTitle":
					zooConferenceEditions = getWithFilter(ZooConferenceEdition.class, "proceedings.id == '" + proceedings.getId() + "'");
					conferenceEdition = zooConferenceEditions.isEmpty() ? null : zooConferenceEditions.iterator().next();
					if (conferenceEdition == null) {
						conferenceEdition = new ZooConferenceEdition();
						conferenceEdition.setProceedings(proceedings);
						conference = new ZooConference();
						conferenceEdition.setConference(conference);
						conference.addEdition(conferenceEdition);
					} else
						conference = conferenceEdition.getConference();
					conference.setName(value);
					break;
				}
			}
			node = node.getNextSibling();
		}
		
		return proceedings;
    }
    
    public void open() {
        pm = ZooJdoHelper.openDB(name);
    	pm.currentTransaction().begin();
    	pm.currentTransaction().setRetainValues(true);
    }
    
    public void close() {
    	pm.currentTransaction().commit();
    	if (pm.currentTransaction().isActive()) {
	        pm.currentTransaction().rollback();
	    }
		pm.close();
		pm.getPersistenceManagerFactory().close();
    }
    
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
    	assert !pm.isClosed() && pm.currentTransaction().isActive();
		Collection<T> collection = (Collection<T>) pm.newQuery(c, filter).execute();
		return collection;
    }
    
    //---------------------- Quick access functions-------------------
    
    // 1.) Find publication by id
    
    public ZooPublication getPublicationById(String id){
    	return getById(ZooPublication.class, id);
    }
    
    // 2.) Find publication by filter (Title, begin-offset, end-offset)
    // TODO: Check if colleciton.toArray(pubsArray) is used correctly
    // TODO: Check if this behaves correctly (e.g. return empty string if beginOffset > List.length())
    public List<ZooPublication> getPublicationsByFilter(String title, int beginOffset, int endOffset){
    	
    	String filter = "this.getTitle() == '" + title + "'";
    	Collection<ZooPublication> collection = getWithFilter(ZooPublication.class, filter);
    	
    	// NEW IMPLEMENTATION
    	
    	List<ZooPublication> publicationList = new ArrayList<ZooPublication>(collection);
    	List<ZooPublication> resultList      = new ArrayList<ZooPublication>();
    	
    	
    	// Asssume the input should be the other way around
    	if(beginOffset > endOffset){
    		return getPublicationsByFilter(title, endOffset, beginOffset);
    	}
    	// If the list is shorter than the Offset, return an empty list
    	if(beginOffset > publicationList.size()){
    		return resultList;
    	}
    	// Handle seperately because subList() would return an empty list
    	if(beginOffset == endOffset){
    		resultList.add(publicationList.get(beginOffset));
    		return resultList;
    	}
    	// To avoid an error, Handle this case seperately
    	if(endOffset > publicationList.size()){
    		resultList = publicationList.subList(beginOffset, publicationList.size());
    	}
    	
    	resultList = publicationList.subList(beginOffset, endOffset);
    	
    	return resultList;
    	
    	// OLD IMPLEMENTATION
//    	ZooPublication[] pubsArray = new ZooPublication[collection.size()];
//    	collection.toArray(pubsArray);
//    	
//    	ZooPublication[] subArray = new ZooPublication[endOffset-beginOffset];
//    	
//    	System.arraycopy(pubsArray, beginOffset, subArray, 0, endOffset-beginOffset);
//    	
//    	return subArray;
    	
    }
    
    // 3.) Find publication by filter ordered by name
    // TODO: Check if we need to offset first and then sort (this function) or the other way around.
    // TODO: Check if the returned list actually is the sorted List or just the original  list.
    public List<ZooPublication> getPublicationsByFilterOrdered(String title, int beginOffset, int endOffset){
    	
    	List<ZooPublication> pubList = getPublicationsByFilter(title, beginOffset, endOffset);
    	
    	Comparator<ZooPublication> compTitles = new Comparator<ZooPublication>(){
    		public int compare(ZooPublication z1, ZooPublication z2){
    			return z1.getTitle().compareTo(z2.getTitle());
    		}
    	};
    	
    	Collections.sort(pubList, compTitles);
    	
    	return pubList;
    }
    
    // TODO: Fix filter string. Might not work
    // TODO: Check if editors count as well
    // 4.) Find co-authors of a person with given name
    public List<ZooPerson> getCoAuthors(ZooPerson author){
    	
    	String AuthorId = author.getId();
    	
    	// TODO: Fix this filter
    	String filter = "this.getAuthoredPublications().getAuthors().contains('" + author.getName() + "'";
    	Collection <ZooPerson> collection = getWithFilter(ZooPerson.class, filter);
    	
    	List<ZooPerson> resultList = new ArrayList<ZooPerson>(collection);
    	
    	return resultList;
    }
    
    // 5.) Shortest Path between 2 authors
    // TODO: Implement
    public List<ZooPerson> getShortestAuthorPath(ZooPerson author1, ZooPerson author2){
    	
    	return null;
    }
    
    // 6.) Compute global average of authors per publication
    // TODO: Check if editors count as well
    // TODO: Check filter.
    public int globalAverageAuthors(){
    	
    	String filter = "avg(getAuthors().size())";
    	
    	int avgNum = (Integer) pm.newQuery(ZooInProceedings.class, filter).execute();
    	
    	return avgNum;
    }
    
	
}
