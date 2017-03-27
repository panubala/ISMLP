package ch.ethz.globis.isk.domain.zoodb;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.InProceedings;
import ch.ethz.globis.isk.domain.Proceedings;
import ch.ethz.globis.isk.domain.Publication;
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

	@Override
	public String getNote() {
		zooActivateRead();
		return note;
	}

	@Override
	public void setNote(String note) {
		zooActivateWrite();
		this.note = note;
	}

	@Override
	public int getNumber() {
		zooActivateRead();
		return number;
	}

	@Override
	public void setNumber(int number) {
		zooActivateWrite();
		this.number = number;
	}

	@Override
	public Publisher getPublisher() {
		zooActivateRead();
		return publisher;
	}

	@Override
	public void setPublisher(Publisher publisher) {
		zooActivateWrite();
		this.publisher = publisher;
	}

	@Override
	public String getVolume() {
		zooActivateRead();
		return volume;
	}

	@Override
	public void setVolume(String volume) {
		zooActivateWrite();
		this.volume = volume;
	}

	@Override
	public String getIsbn() {
		zooActivateRead();
		return isbn;
	}

	@Override
	public void setIsbn(String isbn) {
		zooActivateWrite();
		this.isbn = isbn;
	}

	@Override
	public Series getSeries() {
		zooActivateRead();
		return series;
	}

	@Override
	public void setSeries(Series series) {
		zooActivateWrite();
		this.series = series;
	}

	@Override
	public ConferenceEdition getConferenceEdition() {
		zooActivateRead();
		return conferenceEdition;
	}

	@Override
	public void setConferenceEdition(ConferenceEdition conferenceEdition) {
		zooActivateWrite();
		this.conferenceEdition = conferenceEdition;
	}

	@Override
	public Set<InProceedings> getPublications() {
		zooActivateRead();
		return Collections.unmodifiableSet(publications);
	}

	@Override
	public void setPublications(Set<InProceedings> publications) {
		zooActivateWrite();
		this.publications = publications;
	}

	@Override
	public void addPublication(InProceedings publication) {
		zooActivateWrite();
		publications.add(publication);
	}

}
