package ch.ethz.globis.isk;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
	
	public ZooDatabase(String name) {
        if (ZooHelper.dbExists(name)) {
            ZooHelper.removeDb(name);
        }
        this.name = name;
        ZooHelper.createDb(name);
    	pm = ZooJdoHelper.openDB(name);
    	
		try {
	    	pm.currentTransaction().begin();
	    	pm.currentTransaction().setNontransactionalRead(true);
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
	            String id = element.getAttribute("key");
	            
	            ZooPublication publication;
				switch(element.getTagName()) {
				case "inproceedings":		publication = parseInProceedings(element);	break;
				case "proceedings":			publication = parseProceedings(element);	break;
				default:					publication = null;
				}
				
				if (publication != null) {
					publication.setId(id);
					publications.add(publication);
				}
	        }
			node = node.getNextSibling();
		}
		
		return publications;
    }
    
    ZooInProceedings parseInProceedings(Element element) {
    	ZooInProceedings inProceedings = new ZooInProceedings();
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            
	            String id = child.getAttribute("key");
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "author":
					ZooPerson author = getById(ZooPerson.class, id);
					if (author == null) {
						author = new ZooPerson();
						author.setId(id);
						author.setName(value);
					}
					author.getAuthoredPublications().add(inProceedings);
					inProceedings.getAuthors().add(author);
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
				}
			}
			node = node.getNextSibling();
		}
		
		return inProceedings;
    }
    
    ZooProceedings parseProceedings(Element element) {
    	ZooProceedings proceedings = new ZooProceedings();
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            
	            String id = child.getAttribute("key");
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "editor":
					ZooPerson editor = getById(ZooPerson.class, id);
					if (editor == null) {
						editor = new ZooPerson();
						editor.setId(id);
						editor.setName(value);
					}
					editor.getEditedPublications().add(proceedings);
					proceedings.getAuthors().add(editor);
					break;
				case "title":
					proceedings.setTitle(value);
					break;
				case "publisher":
					ZooPublisher publisher = getById(ZooPublisher.class, id);
					if (publisher == null) {
						publisher = new ZooPublisher();
						publisher.setId(id);
						publisher.setName(value);
					}
					publisher.getPublications().add(proceedings);
					proceedings.setPublisher(publisher);
					break;
				case "series":
					ZooSeries series = getById(ZooSeries.class, id);
					if (series == null) {
						series = new ZooSeries();
						series.setId(id);
						series.setName(value);
					}
					series.getPublications().add(proceedings);
					proceedings.setSeries(series);
					break;
				case "year":
					proceedings.setYear(Integer.valueOf(value));
					break;
				case "isbn":
					proceedings.setIsbn(value);
					break;
				}
			}
			node = node.getNextSibling();
		}
		
		return proceedings;
    }
    
    public <T extends ZooPC>T getById(Class<T> c, String id) {
        String filter = "this.getId()=='" + id + "'";
        Collection<T> collection = getWithFilter(c, filter);
        
        if(collection.isEmpty()){
        	return null;
        }else if(collection.size() > 1){
        	throw new RuntimeException("Non-unique ID");
        }else{
        	return (T) collection.iterator().next();
        }
    }
    
//    public <T extends ZooPC>T getWithFilter(Class<T> c, String filter) {
//		Collection<T> collection = (Collection<T>) pm.newQuery(c, filter).execute();
//		if (collection.isEmpty())
//			return null;
//		else if (collection.size() > 1)
//			throw new RuntimeException("Non-unique ID");
//		else
//			return (T) collection.iterator().next();
//    }
    
    public <T extends ZooPC> Collection<T> getWithFilter(Class<T> c, String filter) {
		Collection<T> collection = (Collection<T>) pm.newQuery(c, filter).execute();
		if (collection.isEmpty())
			return null;
		else
			return collection;
    }
    
    //---------------------- Quick access functions-------------------
    
    // 1.) Find publication by id
    
    public ZooPublication getPublicationById(String id){
    	return getById(ZooPublication.class, id);
    }
    
    // TODO: Check if colleciton.toArray(pubsArray) is used correctly
    // 2.) Find publication by filter (Title, begin-offset, end-offset)
    public ZooPublication[] getPublicationsByFilter(String title, int beginOffset, int endOffset){
    	
    	String filter = "this.getTitle() == '" + title + "'";
    	Collection<ZooPublication> collection = getWithFilter(ZooPublication.class, filter);
    	
    	ZooPublication[] pubsArray = new ZooPublication[collection.size()];
    	collection.toArray(pubsArray);
    	
    	ZooPublication[] subArray = new ZooPublication[endOffset-beginOffset];
    	
    	System.arraycopy(pubsArray, beginOffset, subArray, 0, endOffset-beginOffset);
    	
    	return subArray;
    	
    }
    
    // 3.) Find publication by filter ordered by name
    // 4.) Find co-authors of a person with given name.
	
}
