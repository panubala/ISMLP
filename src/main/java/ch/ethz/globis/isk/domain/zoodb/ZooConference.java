package ch.ethz.globis.isk.domain.zoodb;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Conference;
import ch.ethz.globis.isk.domain.ConferenceEdition;

public class ZooConference extends ZooPC implements Conference {
	
	String id;
	String name;
	Set<ConferenceEdition> editions;
	
	public ZooConference() {
		zooActivateWrite();
		editions = new HashSet<>();
	}
	
	public ZooConference(String id, String name, Set<ConferenceEdition> editions) {
		zooActivateWrite();
		this.id = id;
		this.name = name;
		this.editions = editions;
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
	public Set<ConferenceEdition> getEditions() {
		zooActivateRead();
		return Collections.unmodifiableSet(editions);
	}

	@Override
	public void setEditions(Set<ConferenceEdition> editions) {
		zooActivateWrite();
		this.editions = editions;
	}

	@Override
	public void addEdition(ConferenceEdition edition) {
		zooActivateWrite();
		editions.add(edition);
	}

}
