package ch.ethz.globis.isk.mongodb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

public class MongoInProceedings extends MongoPublication {
	
	String note;
	String pages;
	List<MongoPerson> authors;
	MongoProceedings proceedings;
	
	public MongoInProceedings() {
		super();
		authors = new ArrayList<>();
	}
	
	public MongoInProceedings(String note, String pages, List<MongoPerson> authors, MongoProceedings proceedings) {
		super();
		this.note = note;
		this.pages = pages;
		this.authors = authors;
		this.proceedings = proceedings;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (note != null)
			document.append("note", note);
		if (pages != null)
			document.append("pages", pages);
		if (authors.size() > 0)
			document.append("authors", authors);
		if (proceedings != null)
			document.append("proceedings", proceedings);
		
		return document;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public List<MongoPerson> getAuthors() {
		return Collections.unmodifiableList(authors);
	}

	public void addAuthor(MongoPerson author) {
		authors.add(author);
	}

	public void setAuthors(List<MongoPerson> authors) {
		this.authors = authors;
	}

	public MongoProceedings getProceedings() {
		return proceedings;
	}

	public void setProceedings(MongoProceedings proceedings) {
		this.proceedings = proceedings;
	}

}
