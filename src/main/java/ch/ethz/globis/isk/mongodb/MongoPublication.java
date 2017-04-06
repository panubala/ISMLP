package ch.ethz.globis.isk.mongodb;

import java.util.List;

import org.bson.Document;

public class MongoPublication extends MongoDomainObject {
	
	String title;
	int year;
	String electronicEdition;

	public MongoPublication() {
		super();
	}
	
	public MongoPublication(String id, String title, List<MongoPerson> authors, int year, String electronicEdition) {
		super(id);
		this.title = title;
		this.year = year;
		this.electronicEdition = electronicEdition;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (title != null)
			document.append("title", title);
		if (year != 0)
			document.append("year", year);
		if (electronicEdition != null)
			document.append("electronicEdition", electronicEdition);
		
		return document;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;

	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getElectronicEdition() {
		return electronicEdition;
	}

	public void setElectronicEdition(String electronicEdition) {
		this.electronicEdition = electronicEdition;
	}

}
