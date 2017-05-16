package ch.ethz.globis.isk.Zoo;

import java.util.Arrays;
import java.util.Iterator;

import org.bson.Document;

import ch.ethz.globis.isk.Zoo.Database;
import ch.ethz.globis.isk.domain.zoodb.ZooConference;
import ch.ethz.globis.isk.domain.zoodb.ZooConferenceEdition;
import ch.ethz.globis.isk.domain.zoodb.ZooInProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;
import ch.ethz.globis.isk.domain.zoodb.ZooPublisher;
import ch.ethz.globis.isk.domain.zoodb.ZooSeries;;

public class ZooQueryExecutor {

	private Database db;
	private String filter = "";

	public ZooQueryExecutor(Database db) {

		this.db = db;

	}

	public void publications() {

		db.getWithFilter(ZooPublication.class, filter);

	}

	public void proceedings() {

		db.getWithFilter(ZooProceedings.class, filter);

	}

	public void inProceedings() {

		db.getWithFilter(ZooInProceedings.class, filter);

	}

	public void conferenceEditions() {

		db.getWithFilter(ZooConferenceEdition.class, filter);
	}

	public void conferences() {

		db.getWithFilter(ZooConference.class, filter);

	}

	public void persons() {
		db.getWithFilter(ZooPerson.class, filter);

	}

	public void publishers() {

		db.getWithFilter(ZooPublisher.class, filter);
	}

	public void series() {

		db.getWithFilter(ZooSeries.class, filter);

	}

	// -----QUERIES-----

	// Find a publication by ID
	// Input1: ID string
	public void query1(String id) {

		db.getPublicationById(id);

	}

	// Find publications by filter (title, begin-offset, end-offset)
	// Param1: title
	// Param2: begin-offset
	// Param3: end-offset
	public void query2(String title, int beginOffset, int endOffset) {

		db.getPublicationsByTitle(title, beginOffset, endOffset);
	}

	// Find publications by filter (title, begin-offset, end-offset) ordered by
	// title
	// Param1: title
	// Param2: begin-offset
	// Param3: end-offset
	public void query3(String title, int beginOffset, int endOffset) {

		db.getPublicationsByTitleOrdered(title, beginOffset, endOffset);
	}

	// Find the co-authors of a person with given name
	// Param1: name of person
	public void query4(String name) {
		// String name = "Kevin D. Ashley";

		db.getCoAuthors(name);

	}

	// Find the shortest path between two authors with the author distance
	// Param1: author 1
	// Param2: author 2
	public void query5(String author1, String author2) {

		db.getShortestAuthorPath(author1, author2);

	}

	// Compute the global average number of authors per publication
	public void query6() {
		db.getGlobalAverageAuthors();

	}

	// Count the number of publications per year in a given interval of years
	// Param1: beginning year
	// Param2: end year
	public void query7(int yearFrom, int yearTo) {
		db.getPublicationsPerYear(yearFrom, yearTo);

	}

	//TODO:Implement
	// Count all publications for a conference
	// Param1: name of conference
	public void query8(String conferenceName) {

		db.getNumPublicationsByConference(conferenceName);

	}

	//TODO:Implement
	// Count all authors and editors of a conference
	// Param 1: name of conference
	public void query9(String conferenceName) {

		db.getNumAuthorsAndEditiorsByConference(conferenceName);

	}
	
	//TODO:Implement
	// Retrieve all authors of a given conference
	// Param1: name of conference
	public void query10(String conferenceName) {

		db.getAllAuthorsByConference(conferenceName);

	}

	// Retrieve all publications of a given conference
	// Param 1: name of conference
	public void query11(String conferenceName) {

		db.getAllPublicationsByConference(conferenceName);

	}

	//TODO:Implement
	// list of persons which were at the same time author in an InProceedings
	// and editor in the appropriate Proceedings
	public void query12() {

		db.getAllPersonByAuthorInPreAndEditorInPre();
	}

	// Publication with given author as last author
	// Param 1: name of last author
	public void query13(String name) {

		db.getAllPublicationsByLastAuthor(name);

	}

	// list of publishers of Proceedings whose authors appear in any
	// InProceedings in a range of years
	// Param 1: year beginning
	// Param 2: year ending
	public void query14(int yearFrom, int yearTo) {

		db.getAllPublisherInRange(yearFrom, yearTo);

	}

}
