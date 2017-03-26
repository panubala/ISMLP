package ch.ethz.globis.isk;

import javax.jdo.PersistenceManager;

import org.zoodb.jdo.ZooJdoHelper;

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
import java.util.List;

public class Main  {
	
    public static void main(String[] args) {
    	PersistenceManager pm = ZooJdoHelper.openOrCreateDB("database");
    	
		try {
	    	pm.currentTransaction().begin();
	    	pm.currentTransaction().setRetainValues(true);
	    	List<ZooPublication> publications = parseXml();
	    	for (ZooPublication publication : publications)
	    		pm.makePersistent(publication);
	    	pm.currentTransaction().commit();
		} catch (Exception e) {
			pm.currentTransaction().rollback();
		} finally {
	    	pm.close();
		}
    }
    
    private static List<ZooPublication> parseXml() throws Exception {
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
    
    private static ZooInProceedings parseInProceedings(Element element) {
    	ZooInProceedings inProceedings = new ZooInProceedings();
    	
    	if(element.hasAttribute("key")){
        	element.getAttribute("key");
        	inProceedings.setId(element.getAttribute("key"));
    	}
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "author":
					ZooPerson author = new ZooPerson();
					author.setName(value);
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
    
    private static ZooProceedings parseProceedings(Element element) {
    	ZooProceedings proceedings = new ZooProceedings();
    	
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
	            Element child = (Element) node;
	            String value = child.getFirstChild().getNodeValue();
	            
				switch(child.getTagName()) {
				case "editor":
					ZooPerson editor = new ZooPerson();
					editor.setName(value);
					editor.getEditedPublications().add(proceedings);
					proceedings.getAuthors().add(editor);
					break;
				case "title":
					proceedings.setTitle(value);
					break;
				case "publisher":
					ZooPublisher publisher = new ZooPublisher();
					publisher.setName(value);
					publisher.getPublications().add(proceedings);
					proceedings.setPublisher(publisher);
					break;
				case "series":
					ZooSeries series = new ZooSeries();
					series.setName(value);
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
    
    public ZooPublication getPublicationById(PersistenceManager pm, String id){
    	
    	ZooPublication result;
    	
    	Query q = pm.newQuery(ZooPublication.class);
    	q.setFilter("this.getId() == " + id + "");
    	Collection c = (Collection) q.execute();
    	
    	if(c.isEmpty()){
    		return null;
    	}else if (c.size() > 1){
    		throw new RuntimeException("Non-Unique ID");
    	}else{
    		result = (ZooPublication) c.iterator().next();
    		return result;
    	}
    	
    }
    
}
