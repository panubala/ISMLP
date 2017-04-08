package ch.ethz.globis.isk.domain.mongodb;

import org.bson.Document;

public class MongoConferenceEdition extends MongoDomainObject {
	
	MongoConference conference;
	int year;
	MongoProceedings proceedings;
	
	public MongoConferenceEdition() {
		super();
	}
	
	public MongoConferenceEdition(String id, MongoConference conference, int year, MongoProceedings proceedings) {
		super(id);
		this.conference = conference;
		this.year = year;
		this.proceedings = proceedings;
	}
	
	@Override
	public Document toDocument() {
		Document document = super.toDocument();
		
		if (conference != null)
			document.append("conference", conference);
		if (year != 0)
			document.append("year", year);
		if (proceedings != null)
			document.append("proceedings", proceedings);
		
		return document;
	}

	public MongoConference getConference() {
		return conference;
	}

	public void setConference(MongoConference conference) {
		this.conference = conference;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public MongoProceedings getProceedings() {
		return proceedings;
	}

	public void setProceedings(MongoProceedings proceedings) {
		this.proceedings = proceedings;
	}

}
