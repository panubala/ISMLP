package ch.ethz.globis.isk.domain.zoodb;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.zoodb.api.impl.ZooPC;
import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.Publisher;

public class ZooPublisher extends ZooPC implements Publisher {
	
	String id;
	String name;
	Set<Publication> publications;
	
	public ZooPublisher() {
		zooActivateWrite();
		publications = new HashSet<>();
	}
	
	public ZooPublisher(String id, String name, Set<Publication> publications) {
		zooActivateWrite();
		this.id = id;
		this.name = name;
		this.publications = publications;
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
	public Set<Publication> getPublications() {
		zooActivateRead();
		return Collections.unmodifiableSet(publications);
	}

	@Override
	public void setPublications(Set<Publication> publications) {
		zooActivateWrite();
		this.publications = publications;
	}
	
	@Override
	public void addPublication(Publication publication) {
		zooActivateWrite();
		publications.add(publication);
	}

}
