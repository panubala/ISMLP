package ch.ethz.globis.isk.mongodb;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

public class MongoSeries extends MongoDomainObject {
	
	String name;
	Set<MongoPublication> publications;
	
	public MongoSeries() {
		super();
		publications = new HashSet<>();
	}
	
	public MongoSeries(String id, String name, Set<MongoPublication> publications) {
		super(id);
		this.name = name;
		this.publications = publications;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (name != null)
			document.append("name", name);
		if (publications.size() > 0)
			document.append("publications", publications);
		
		return document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MongoPublication> getPublications() {
		return Collections.unmodifiableSet(publications);
	}

	public void setPublications(Set<MongoPublication> publications) {
		this.publications = publications;
	}
	
	public void addPublication(MongoPublication publication) {
		publications.add(publication);
	}

}
