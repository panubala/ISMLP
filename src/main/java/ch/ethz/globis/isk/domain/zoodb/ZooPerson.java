package main.java.ch.ethz.globis.isk.domain.zoodb;

import java.util.Set;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;

public class ZooPerson extends ZooPC implements Person {
	
	private String name;
	private String ID;
	
	public ZooPerson(String name, String ID){
		this.name = name;
		this.ID = ID;
	}

	@Override
	public String getId() {
		zooActivateRead();
		return ID;
	}

	@Override
	public void setId(String id) {
		zooActivateWrite();
        this.ID = ID;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAuthoredPublications(Set<Publication> authoredPublications) {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<Publication> getEditedPublications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEditedPublications(Set<Publication> editedPublications) {
		// TODO Auto-generated method stub

	}

}
