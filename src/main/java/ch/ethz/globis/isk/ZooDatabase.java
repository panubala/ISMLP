package ch.ethz.globis.isk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
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
import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;
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
    
    /*// Helper function for 2.) and 3.)
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
    }*/
    
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
    
    // 5.) Helper function for faster recursion in 5.)
    /*private int getShortestAuthorPath(ZooPerson author1, ZooPerson author2) {
    	Collection<ZooPerson> coAuthors = getCoAuthors(author1.getName());
    	for (Publication publication : author1.getAuthoredPublications()) {
    		if (publication.getAuthors().contains(author2))
    			return 1;
    		else
    			return 1 + getShortestAuthorPath(author1, author2);
    	}
    	return 1 + getShortestAuthorPath(author1, author2);
    	
    }*/
    
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
    public int getNumPublicationsByConference(String conferenceName){
    	
    	return null;
    }
    
	
}
