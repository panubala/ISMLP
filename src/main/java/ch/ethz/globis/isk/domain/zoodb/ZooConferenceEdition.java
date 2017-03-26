package ch.ethz.globis.isk.domain.zoodb;

import org.zoodb.api.impl.ZooPC;

import ch.ethz.globis.isk.domain.Conference;
import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.Proceedings;

public class ZooConferenceEdition extends ZooPC implements ConferenceEdition {
	
	String id;
	Conference conference;
	int year;
	Proceedings proceedings;
	
	public ZooConferenceEdition() {
		
	}
	
	public ZooConferenceEdition(String id, Conference conference, int year, Proceedings proceedings) {
		zooActivateWrite();
		this.id = id;
		this.conference = conference;
		this.year = year;
		this.proceedings = proceedings;
	}
	

	public String getId() {
		zooActivateRead();
		return id;
	}

	public void setId(String id) {
		zooActivateWrite();
		this.id = id;
	}

	public Conference getConference() {
		zooActivateRead();
		return conference;
	}

	public void setConference(Conference conference) {
		zooActivateWrite();
		this.conference = conference;
	}

	public int getYear() {
		zooActivateRead();
		return year;
	}

	public void setYear(int year) {
		zooActivateWrite();
		this.year = year;
	}

	public Proceedings getProceedings() {
		zooActivateRead();
		return proceedings;
	}

	public void setProceedings(Proceedings proceedings) {
		zooActivateWrite();
		this.proceedings = proceedings;
	}

}
