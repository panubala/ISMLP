package ch.ethz.globis.isk.domain.zoodb;

import java.util.HashSet;
import java.util.Set;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;

public class ZooPerson extends ZooPC implements Person {
	
	String id;
	String name;
	Set<Publication> authoredPublications;
	Set<Publication> editedPublications;
	
	public ZooPerson() {
		zooActivateWrite();
		authoredPublications = new HashSet<>();
		editedPublications = new HashSet<>();
	}
	
	public ZooPerson(String id, String name, Set<Publication> authoredPublications, Set<Publication> editedPublications) {
		zooActivateWrite();
		this.id = id;
		this.name = name;
		this.authoredPublications = authoredPublications;
		this.editedPublications = editedPublications;
	}
	
	@Override
	public String getId() {
		zooActivateRead();
		return id;
	}

	@Override
	public void setId(String id) {
		zooActivateWrite();
		this.id = id;
	}

	@Override
	public String getName() {
		zooActivateRead();
		return name;
	}

	@Override
	public void setName(String name) {
		zooActivateWrite();
		this.name = name;
	}

	@Override
	public Set<Publication> getAuthoredPublications() {
		zooActivateRead();
		return authoredPublications;
	}

	@Override
	public void setAuthoredPublications(Set<Publication> authoredPublications) {
		zooActivateWrite();
		this.authoredPublications = authoredPublications;
	}

	@Override
	public Set<Publication> getEditedPublications() {
		zooActivateRead();
		return editedPublications;
	}

	@Override
	public void setEditedPublications(Set<Publication> editedPublications) {
		zooActivateWrite();
		this.editedPublications = editedPublications;
	}

}
