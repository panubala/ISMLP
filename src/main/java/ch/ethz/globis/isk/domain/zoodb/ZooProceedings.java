package ch.ethz.globis.isk.domain.zoodb;

import java.util.HashSet;
import java.util.Set;

import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.InProceedings;
import ch.ethz.globis.isk.domain.Proceedings;
import ch.ethz.globis.isk.domain.Publisher;
import ch.ethz.globis.isk.domain.Series;

public class ZooProceedings extends ZooPublication implements Proceedings {
	
	String note;
	int number;
	Publisher publisher;
	String volume;
	String isbn;
	Series series;
	ConferenceEdition conferenceEdition;
	Set<InProceedings> publications;
	
	public ZooProceedings() {
		zooActivateWrite();
		publications = new HashSet<>();
	}
	
	public ZooProceedings(String note, int number, Publisher publisher, String volume, String isbn,
			Series series, ConferenceEdition conferenceEdition, Set<InProceedings> publications) {
		zooActivateWrite();
		this.note = note;
		this.number = number;
		this.publisher = publisher;
		this.volume = volume;
		this.isbn = isbn;
		this.series = series;
		this.conferenceEdition = conferenceEdition;
		this.publications = publications;
	}
	
	public String getNote() {
		zooActivateRead();
		return note;
	}

	public void setNote(String note) {
		zooActivateWrite();
		this.note = note;
	}

	public int getNumber() {
		zooActivateRead();
		return number;
	}

	public void setNumber(int number) {
		zooActivateWrite();
		this.number = number;
	}

	public Publisher getPublisher() {
		zooActivateRead();
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		zooActivateWrite();
		this.publisher = publisher;
	}

	public String getVolume() {
		zooActivateRead();
		return volume;
	}

	public void setVolume(String volume) {
		zooActivateWrite();
		this.volume = volume;
	}

	public String getIsbn() {
		zooActivateRead();
		return isbn;
	}

	public void setIsbn(String isbn) {
		zooActivateWrite();
		this.isbn = isbn;
	}

	public Series getSeries() {
		zooActivateRead();
		return series;
	}

	public void setSeries(Series series) {
		zooActivateWrite();
		this.series = series;
	}

	public ConferenceEdition getConferenceEdition() {
		zooActivateRead();
		return conferenceEdition;
	}

	public void setConferenceEdition(ConferenceEdition conferenceEdition) {
		zooActivateWrite();
		this.conferenceEdition = conferenceEdition;
	}

	public Set<InProceedings> getPublications() {
		zooActivateRead();
		return publications;
	}

	public void setPublications(Set<InProceedings> publications) {
		zooActivateWrite();
		this.publications = publications;
	}

}
