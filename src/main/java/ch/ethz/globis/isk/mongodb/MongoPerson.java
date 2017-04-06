package ch.ethz.globis.isk.mongodb;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

public class MongoPerson extends MongoDomainObject {
	
	String name;
	Set<MongoPublication> authoredPublications;
	Set<MongoPublication> editedPublications;
	
	public MongoPerson() {
		super();
		authoredPublications = new HashSet<>();
		editedPublications = new HashSet<>();
	}
	
	public MongoPerson(String id, String name, Set<MongoPublication> authoredPublications, Set<MongoPublication> editedPublications) {
		super(id);
		this.name = name;
		this.authoredPublications = authoredPublications;
		this.editedPublications = editedPublications;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (name != null)
			document.append("name", name);
		if (authoredPublications.size() > 0)
			document.append("authoredPublications", authoredPublications);
		if (editedPublications.size() > 0)
			document.append("editedPublications", editedPublications);
		
		return document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<MongoPublication> getAuthoredPublications() {
		return Collections.unmodifiableSet(authoredPublications);
	}

	public void setAuthoredPublications(Set<MongoPublication> authoredPublications) {
		this.authoredPublications = authoredPublications;
	}

	public void addAuthoredPublication(MongoPublication publication) {
		authoredPublications.add(publication);
	}

	public Set<MongoPublication> getEditedPublications() {
		return Collections.unmodifiableSet(editedPublications);
	}

	public void setEditedPublications(Set<MongoPublication> editedPublications) {
		this.editedPublications = editedPublications;
	}

	public void addEditedPublication(MongoPublication publication) {
		editedPublications.add(publication);
	}

}
