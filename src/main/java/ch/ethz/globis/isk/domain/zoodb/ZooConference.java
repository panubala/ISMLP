package ch.ethz.globis.isk.domain.zoodb;

import java.util.Set;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Conference;
import ch.ethz.globis.isk.domain.ConferenceEdition;

public class ZooConference extends ZooPC implements Conference {
	
	String id;
	String name;
	Set<ConferenceEdition> editions;
	
	public ZooConference(String id, String name, Set<ConferenceEdition> editions){
		this.id = id;
		this.name = name;
		this.editions = editions;
	}
	
	
	public String getId() {
		zooActivateRead();
		return id;
	}

	public void setId(String id) {
		zooActivateWrite();
		this.id = id;

	}

	public String getName() {
		zooActivateRead();
		return name;
	}

	public void setName(String name) {
		zooActivateWrite();
		this.name = name;

	}

	public Set<ConferenceEdition> getEditions() {
		zooActivateRead();
		return editions;
	}

	public void setEditions(Set<ConferenceEdition> editions) {
		zooActivateWrite();
		this.editions = editions;

	}

}
