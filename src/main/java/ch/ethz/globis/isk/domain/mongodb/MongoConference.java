package ch.ethz.globis.isk.domain.mongodb;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

public class MongoConference extends MongoDomainObject {
	
	String name;
	Set<MongoConferenceEdition> editions;
	
	public MongoConference() {
		super();
		editions = new HashSet<>();
	}
	
	public MongoConference(String id, String name, Set<MongoConferenceEdition> editions) {
		super(id);
		this.name = name;
		this.editions = editions;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (name != null)
			document.append("name", name);
		if (editions.size() > 0)
			document.append("editions", editions);
		
		return document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MongoConferenceEdition> getEditions() {
		return Collections.unmodifiableSet(editions);
	}

	public void setEditions(Set<MongoConferenceEdition> editions) {
		this.editions = editions;
	}

	public void addEdition(MongoConferenceEdition edition) {
		editions.add(edition);
	}

}
