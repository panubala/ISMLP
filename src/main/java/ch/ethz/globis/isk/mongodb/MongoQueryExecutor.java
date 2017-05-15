package ch.ethz.globis.isk.mongodb;


import java.util.Arrays;
import java.util.Iterator;

import org.bson.Document;

import com.mongodb.QueryBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;
import com.mongodb.operation.GroupOperation;

public class MongoQueryExecutor {
    
	private Database db;

	
	// TODO: Maybe change iterator() to something else in all queries.
    public MongoQueryExecutor(Database db) {
    	
    	this.db = db;
    	
    }

    public void publications() {

    	db.publications.find().iterator();

    }

    public void proceedings() {

    	db.publications.find(Filters.exists("publications")).iterator();
				
    }
    
    public void inProceedings() {

    	db.publications.find(Filters.exists("proceedings")).iterator();

    }
    
    public void conferenceEditions() {

    	db.conferenceEditions.find().iterator();
    }


    public void conferences() {
    	
    	db.conferences.find().iterator();

    }
    
    public void persons() {
    	// Search persons by name
    	
    	/*String name = this.jTextField6.getText();
    	String expr = "(this['name'] != undefined && this['name'].toString().includes('" + name + "'))";*/
    	
    	db.persons.find().iterator();

    }

    public void publishers() {
    	
    	db.publishers.find().iterator();
    }
    
    public void series() {

    	db.series.find().iterator();

    }
    
    // -----QUERIES-----
    
    
    // Find a publication by ID
    // Input1: ID string
    public void query1(String id) {
    	
    	db.publications.find(Filters.eq("_id", id)).iterator();
    	
    }
    
    
    // Find publications by filter (title, begin-offset, end-offset)
    // Param1: title
    // Param2: begin-offset
    // Param3: end-offset
    public void query2(String title, int beginOffset, int endOffset) {
    	
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("title",
    						new Document("$regex", ".*" + title + ".*i")
    					)
					),
					new Document("$skip", beginOffset),
					new Document("$limit", endOffset - beginOffset)
    			)).iterator();
    	
    }
    
    
    //  Find publications by filter (title, begin-offset, end-offset) ordered by title
    // Param1: title
    // Param2: begin-offset
    // Param3: end-offset
    public void query3(String title, int beginOffset, int endOffset) {

    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("title",
    						new Document("$regex", ".*" + title + ".*i")
    					)
					),
					new Document("$skip", beginOffset),
					new Document("$limit", endOffset - beginOffset),
					new Document("$sort", new Document("title", 1))
    			)).iterator();
    }
    
    // Find the co-authors of a person with given name
    // Param1: name of person
    public void query4(String name) {
    	//String name = "Kevin D. Ashley";
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
    				new Document("$match",
						new Document("authors", name)
					),
    				new Document("$project",
    					new Document("author", name)
    					.append("coAuthors",
		    				new Document("$filter",
								new Document("input", "$authors")
			    				.append("as", "author")
			    				.append("cond",
			    					new Document("$ne", Arrays.asList(
			    						"$$author", name
		    						))
								)
		    				)
	    				)
					),
    				new Document("$unwind", "$coAuthors"),
    				new Document("$group",
    					new Document("_id", "$author")
    					.append("coAuthors",
							new Document("$addToSet", "$coAuthors")
						)
					)
    			)).iterator();
    	
    }
    
    // Find the shortest path between two authors with the author distance
    // Param1: author 1
    // Param2: author 2
    public void query5(String author1, String author2) {
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
					new Document("$match",
						new Document("$or", Arrays.asList(
							new Document("authors", author1),
							new Document("authors", author2)
						))
					),
					/*new Document("$lookup",
						new Document("from", "persons")
						.append("localField", "authors")
						.append("foreignField", "name")
						.append("as", "author")
					),*/
					new Document("$unwind", "$authors"),
    						
					new Document("$graphLookup",
						new Document("from", "$$ROOT")
						.append("startWith", "$authors")
						.append("connectFromField", "authors")
						.append("connectToField", "authors")
						.append("as", "connectedAuthors")
					)
    			)).iterator();

    }
    
    // Compute the global average number of authors per publication
    public double query6() {
    	
//    	dbpublications.aggregate([
//    	                          {$project: {_id: 1, title: 1, authors: 1,
//    	                        	  total: {$size: {"$ifNull": ["$authors", []]}}}},
//    	                          {$group: {_id: "Average Number", count: {$avg: "$total"}}}
//    	    ])
    	
    	AggregateIterable<Document> query = db.publications.aggregate(Arrays.asList(
    			new Document("$project", 
    					new Document("_id", 1)
    					.append("title", 1)
    					.append("authors", 1)
    					.append("total",
    							new Document("$size",
    									new Document("$ifNull",
    											Arrays.asList("$authors", Arrays.asList())
    											)
    									)
    							)
    					),
    			new Document("$group", 
    					new Document("_id", "average")
    					.append("count",
    							new Document("$avg", "$total")))
    			));
    	
    	Document firstRes = query.first();
    	
    	if(firstRes.containsKey("count")){
//    		this.resultLabel.setText(Double.toString(firstRes.getDouble("count")));
    		return firstRes.getDouble("count");
    	}else{
    		throw new RuntimeException("Invalid Result. ");
    	}

    	
    }
    
    
    // Count the number of publications per year in a given interval of years
    // Param1: beginning year
    // Param2: end year
    public void query7(int yearFrom, int yearTo) {
    	// Number of publications per year
    	
    	if(yearFrom > yearTo){
    		int temp = yearTo;
    		yearTo = yearFrom;
    		yearFrom = temp;
    	}
    	
    	/*String expr = "(this['year'] != undefined && this['year'] >= "
    					+ yearFrom
    					+ " && this['year'] <= "
    					+ yearTo
    					+ ")";
    	    	
		long result = db.publications.count(Filters.where(expr)); 
		resultLabel.setText(Long.toString(result));
    	
    	new Table(db.publications, 
    			db.publications.find(Filters.where(expr)).iterator(), 
    			"Publications between " + yearFrom + " and " + yearTo,
    			new String[] {"Title", "Year"}, 
    			new String[] {"title", "year"},
    			false);*/
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
					new Document("$project",
						new Document("_id", "$_id")
						.append("year", "$year")
						.append("gte",
							new Document("$gte", Arrays.asList(
								"$year", yearFrom
							))
						)
						.append("lte",
							new Document("$lte", Arrays.asList(
								"$year", yearTo
							))
						)
					),
					new Document("$match",
						new Document("$and", Arrays.asList(
							new Document("gte", true),
							new Document("lte", true)
						))
					),
					new Document("$group",
						new Document("_id", "$_id")
						.append("count",
							new Document("$sum", 1)
						)
						.append("year",
							new Document("$first", "$year")
						)
					),
					new Document("$group",
						new Document("_id", "$year")
						.append("count",
							new Document("$sum", "$count")
						)
					),
					new Document("$sort", new Document("_id", 1))
    			)).iterator();
    	
    }
    
    // Count all publications for a conference
    // Param1: name of conference
    public void query8(String conferenceName) {
    	
    	Iterator<Document> iterator =
    			db.conferences.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("count",
							new Document("$sum",
								new Document("$size", "$proceedings.publications")
							)
						)
					)
    			)).iterator();
    	
    }

    
    //  Count all authors and editors of a conference
    // Param 1: name of conference
    public void query9(String conferenceName) {
    	
    	Iterator<Document> iterator =
    			db.conferences.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "proceedings.publications")
						.append("foreignField", "_id")
						.append("as", "inProceedings")
					),
					new Document("$unwind", "$inProceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("editors",
							new Document("$addToSet", "$proceedings.editors")
						)
						.append("authors",
							new Document("$addToSet", "$inProceedings.authors")
						)
					),
					
					new Document("$project",
						new Document("name", "$name")
						.append("authors",
							new Document("$setUnion", Arrays.asList(
								"$authors", "$editors"
							))
						)
					),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("count",
							new Document("$sum",
								new Document("$size", "$authors")
							)
						)
					)
    			)).iterator();
    	
    }
    
    // Retrieve all authors of a given conference
    // Param1: name of conference
    public void query10(String conferenceName) {
	
		Iterator<Document> iterator =
				db.conferences.aggregate(Arrays.asList(
					new Document("$match",
						new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "proceedings.publications")
						.append("foreignField", "_id")
						.append("as", "inProceedings")
					),
					new Document("$unwind", "$inProceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("authors",
							new Document("$addToSet", "$proceedings.editors")
						)
						.append("authors",
							new Document("$addToSet", "$inProceedings.authors")
						)
					),
					new Document("$unwind", "$authors"),
					new Document("$unwind", "$authors"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("authors",
							new Document("$addToSet", "$authors")
						)
					)
				)).iterator();
	
    }
    
    // Retrieve all publications of a given conferenc
    // Param 1: name of conference
    public void query11(String conferenceName) {
    	
    	Iterator<Document> iterator =
    			db.conferences.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "proceedings.publications")
						.append("foreignField", "_id")
						.append("as", "inProceedings")
					),
					new Document("$unwind", "$inProceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("inProceedings",
							new Document("$addToSet", "$inProceedings._id")
						)
						.append("proceedings",
							new Document("$addToSet", "$proceedings._id")
						)
					),
					
					new Document("$project",
						new Document("name", "$name")
						.append("publications",
							new Document("$setUnion", Arrays.asList(
								"$inProceedings", "$proceedings"
							))
						)
					)
    			)).iterator();
    	
    }
    
    // TODO: Missing code. ???
    public void query12() {
    	
    	
    	// JavaScript command: Always returns empty arrays as intersection. (???)
//    	db.getCollection('persons').aggregate([
//    	                                       {$match: {"authoredPublications": {$exists: true}}},
//    	                                       {$match: {"editedPublications": {$exists: true}}},
//    	                                       {$project: {"_id": 1, "name": 1, "authoredPublications": 1, "editedPublications": 1,
//    	                                           "intersect": {$setIntersection: ["$authoredPublications", "$editedPublications"]}
//    	                                           }
//    	                                       },
//    	                                       {$match: {"intersect": { $exists: true, $ne: [] }}}
//    	                                   ])
    	
    	throw new RuntimeException("Query12");
		
    }
    
    // Publication with given author as last author
    // Param 1: name of last author
    public void query13(String name) {
		    	
    	// Exact matching could be used alternatively
    	//String expr = "this.authors != undefined && this.authors[this.authors.length - 1] === '" + name + "'";
    	String expr = "this.authors != undefined && this.authors[this.authors.length - 1].includes('" + name + "')";
    	
    	Iterator<Document> iterator = db.publications.find(Filters.where(expr)).iterator();
    	
    	
    }
    
    // list of publishers of Proceedings whose authors appear in any InProceedings in a range of years
    // Param 1: year beginning
    // Param 2: year ending
    public void query14(int yearFrom, int yearTo) {
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
					new Document("$project",
						new Document("_id", "$_id")
						.append("publisher", "$publisher")
						.append("authors", "$authors")
						.append("editors", "$editors")
						.append("year", "$year")
						.append("gte",
							new Document("$gte", Arrays.asList(
								"$year", yearFrom
							))
						)
						.append("lte",
							new Document("$lte", Arrays.asList(
								"$year", yearTo
							))
						)
					),
					new Document("$match",
						new Document("$and", Arrays.asList(
							new Document("gte", true),
							new Document("lte", true)
						))
					),
					
					new Document("$lookup",
						new Document("from", "$$ROOT")
						.append("localField", "authors")
						.append("foreignField", "editors")
						.append("as", "proceedings")
					),
					
					new Document("$group",
						new Document("_id", "$publisher")
					)
    			)).iterator();
    	
    }
}