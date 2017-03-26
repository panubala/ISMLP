package ch.ethz.globis.isk;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.zoodb.api.impl.ZooPC;
import org.zoodb.jdo.ZooJdoHelper;
import org.zoodb.tools.ZooHelper;

import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;
import ch.ethz.globis.isk.domain.zoodb.ZooPublisher;
import ch.ethz.globis.isk.domain.zoodb.ZooSeries;
import ch.ethz.globis.isk.domain.zoodb.ZooInProceedings;

import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public class Main  {
	
	static PersistenceManager pm;
	
    public static void main(String[] args) {
    	String dbName = "database";
    	
        if (ZooHelper.dbExists(dbName)) {
            ZooHelper.removeDb(dbName);
        }
        ZooHelper.createDb(dbName);
    	pm = ZooJdoHelper.openDB(dbName);
    	
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
    
    static List<ZooPublication> parseXml() throws Exception {
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
    
    static ZooInProceedings parseInProceedings(Element element) {
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
    
    static ZooProceedings parseProceedings(Element element) {
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
    
    private static <T extends ZooPC>T getById(Class<T> c, String id) {
        String filter = "id=='" + id + "'";
		Collection<T> collection = (Collection<T>) pm.newQuery(c, filter).execute();
		if (collection.isEmpty())
			return null;
		else if (collection.size() > 1)
			throw new RuntimeException("Non-unique ID");
		else
			return (T) collection.iterator().next();
    }
    
}
