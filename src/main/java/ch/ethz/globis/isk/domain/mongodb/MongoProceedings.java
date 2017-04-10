 package ch.ethz.globis.isk.domain.mongodb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

public class MongoProceedings extends MongoPublication {
	
	String note;
	int number;
	MongoPublisher publisher;
	String volume;
	String isbn;
	List<MongoPerson> editors;
	MongoSeries series;
	MongoConferenceEdition conferenceEdition;
	Set<MongoInProceedings> publications;
	
	public MongoProceedings() {
		super();
		editors = new ArrayList<>();
		publications = new HashSet<>();
	}
	
	public MongoProceedings(String note, int number, MongoPublisher publisher, String volume, String isbn, List<MongoPerson> editors, 
			MongoSeries series, MongoConferenceEdition conferenceEdition, Set<MongoInProceedings> publications) {
		super();
		this.note = note;
		this.number = number;
		this.publisher = publisher;
		this.volume = volume;
		this.isbn = isbn;
		this.editors = editors;
		this.series = series;
		this.conferenceEdition = conferenceEdition;
		this.publications = publications;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (note != null)
			document.append("note", note);
		if (number != 0)
			document.append("editors", editors);
		if (publisher != null)
			document.append("publisher", publisher);
		if (volume != null)
			document.append("volume", volume);
		if (isbn != null)
			document.append("isbn", isbn);
		if (series != null)
			document.append("series", series);
		if (conferenceEdition != null)
			document.append("conferenceEdition", conferenceEdition);
		if (publications.size() > 0)
			document.append("publications", publications);
		
		return document;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public MongoPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(MongoPublisher publisher) {
		this.publisher = publisher;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<MongoPerson> getEditors() {
		return Collections.unmodifiableList(editors);
	}

	public void addEditor(MongoPerson editor) {
		editors.add(editor);
	}

	public void setEditors(List<MongoPerson> editors) {
		this.editors = editors;
	}

	public MongoSeries getSeries() {
		return series;
	}

	public void setSeries(MongoSeries series) {
		this.series = series;
	}

	public MongoConferenceEdition getConferenceEdition() {
		return conferenceEdition;
	}

	public void setConferenceEdition(MongoConferenceEdition conferenceEdition) {
		this.conferenceEdition = conferenceEdition;
	}

	public Set<MongoInProceedings> getPublications() {
		return Collections.unmodifiableSet(publications);
	}

	public void setPublications(Set<MongoInProceedings> publications) {
		this.publications = publications;
	}

	public void addPublication(MongoInProceedings publication) {
		publications.add(publication);
	}

}
