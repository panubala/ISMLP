package ch.ethz.globis.isk.domain.zoodb;

import java.util.HashSet;
import java.util.Set;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.Series;

public class ZooSeries extends ZooPC implements Series {
	
	String id;
	String name;
	Set<Publication> publications;
	
	public ZooSeries() {
		zooActivateWrite();
		publications = new HashSet<>();
	}
	
	public ZooSeries(String id, String name, Set<Publication> publications) {
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
		return publications;
	}

	@Override
	public void setPublications(Set<Publication> publications) {
		zooActivateWrite();
		this.publications = publications;
	}

}
