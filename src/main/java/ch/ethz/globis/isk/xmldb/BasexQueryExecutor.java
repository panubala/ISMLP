package ch.ethz.globis.isk.xmldb;



import ch.ethz.globis.isk.xmldb.api.BaseXClient.Query;

public class BasexQueryExecutor {
    
	private Database db;

    public BasexQueryExecutor(Database db) {
    	
    	// TODO: Check if extra query.execute() necessary
    	this.db = db;
    	
    }
    
    // Find a publication by ID
    // Input1: ID string
    public void query1(String id) {

    	String input =
    	  "let $publications := doc('publications.xml')/root//* "
		+ "return <root>{ "
		+   "for $p in $publications "
		+   "return "
		+     "if (contains($p/id, '" + id + "')) "
		+     "then $p "
		+     "else () "
		+ "}</root> ";
    	
    	Query query = db.execute(input);
    	
    }

    // Find publications by filter (title, begin-offset, end-offset)
    // Param1: title
    // Param2: begin-offset
    // Param3: end-offset
    public void query2(String id, int beginOffset, int endOffset) {
    	
    	String input =
		 "let $publications := doc('publications.xml')/root//*[contains(title, '" + id + "')] "
		+"return <root>{ "
		+  "subsequence($publications, " + beginOffset +  ", " + (endOffset - beginOffset) + ") "
		+"}</root> ";
    	
    	Query query = db.execute(input);
    	
    }

    
    //  Find publications by filter (title, begin-offset, end-offset) ordered by title
    // Param1: title
    // Param2: begin-offset
    // Param3: end-offset
    public void query3(String id, int beginOffset, int endOffset) {
    	
    	String input =
		 "let $publications := doc('publications.xml')/root//*[contains(title, '" + id + "')] "
		+"return <root>{ "
		+  "let $sorted := for $publication in $publications "
		+     "order by $publication/title/text() "
		+     "return $publication "
		+  "return subsequence($sorted, " + beginOffset +  ", " + (endOffset - beginOffset) + ") "
		+"}</root> ";
    	Query query = db.execute(input);
    	
    }

    // Find the co-authors of a person with given name
    // Param1: name of person
    public void query4(String authorId) {
    	
    	String input =
		 "let $author := doc('persons.xml')/root//*[id = '" + authorId + "'] "
		+"return <root>{ "
		+  "<author>{ "
		+    "$author/id, "
		+    "for $i in doc('inproceedings.xml')/root//*[id = $author//iid/text()] "
		+    "return "
		+      "for $coAuthor in $i//author "
		+      "return "
		+        "if ($coAuthor/text() = $author/id/text()) "
		+        "then () "
		+        "else <coAuthor>{$coAuthor/text()}</coAuthor> "
		+  "}</author> "
		+"}</root> ";
    	Query query = db.execute(input);
    	
    }

    // Find the shortest path between two authors with the author distance
    // Param1: author 1
    // Param2: author 2
    public void query5(String author1Id, String author2Id) {
    	
    	String input =
    	 "declare function local:shortestPath($authors, $target, $depth) { "
    	+  "if ($depth > 20) "
    	+  "then 'The authors do not have anything in common' "
    	+  "else "
    	+    "if (some $author in $authors satisfies $author/id/text() = $target/id/text()) "
    	+    "then $depth "
    	+    "else local:shortestPath(doc('coAuthors.xml')/root//*[id/text() = $authors//coAuthor/text()], $target, $depth + 1) "
    	+"}; "
    	+"let $coAuthors := doc('coAuthors.xml')/root//*, "
    	+"$author := $coAuthors[id = '" + author1Id + "'], "
    	+"$target := $coAuthors[id = '" + author2Id + "'] "
    	+"return <root>{ "
    	+  "<item>{ "
    	+    "<shortestPath>{ "
    	+      "local:shortestPath($author, $target, 0) "
    	+    "}</shortestPath> "
    	+  "}</item> "
    	+"}</root> ";
    	Query query = db.execute(input);
    	
    }

    // Compute the global average number of authors per publication
    public void query6() {
    	Query query = db.executeFile("query6.xq");
    	
    }

    // Count the number of publications per year in a given interval of years
    // Param1: beginning year
    // Param2: end year
    public void query7(int beginYear, int endYear) {
    	
    	if (beginYear > endYear){
    		int temp = beginYear;
    		beginYear = endYear;
    		endYear = temp;
    	}
    	
    	String input = "let $inproceedings := doc('publications.xml')/root/inproceedings[(ceid >= " + beginYear + ") and (ceid <= " + endYear + ")] "
    			+ "return <root>{ "
    			+ 	"for $year in distinct-values($inproceedings/ceid) "
    			+ 	"order by $year "
    			+ 	"return "
    			+ 		"<result> "
    			+ 			"<year>{ "
    			+ 				"$year "
    			+ 			"}</year> "
    			+ 			"<num>{ "
    			+ 				"count($inproceedings[ceid = $year]) "
    			+ 			"}</num> "
    			+ 		"</result> "
    			+ "}</root>";
    	
    	
    	Query query = db.execute(input);
    	
    }

    // Count all publications for a conference
    // Param1: name of conference
    public void query8(String confID) {
    	
    	// e.g. "CONCUR"    	
    	
    	String input = "let $publications := doc('publications.xml')/root "
    			+ "return <root>{ "
    			+ 	"<item>{ "
    			+ 		"<num>{ "
    			+ 			"for $p in $publications/proceedings[cid = '" + confID + "'] "
    			+ 			"return ( "
    			+ 				"count( "
    			+ 					"for $ip in  $publications/inproceedings "
    			+ 					"where $ip/pid = $p/id "
    			+ 					"return $ip "
    			+ 				") "
    			+ 			") "
    			+ 		"}</num> "
    			+ 	"}</item> "
    			+ "}</root>";
    	
    	Query query = db.execute(input);
    	
    }

    //  Count all authors and editors of a conference
    // Param 1: name of conference
    public void query9(String confID) {
    	
    	String input =
    			"let $proceedings := doc('proceedings.xml')/root//*[cid/text() = '" + confID + "'], "
    			+ "$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()] "
    			+ "return <root><item><count>{count(distinct-values($proceedings/editor/text() | $inproceedings/author/text()))}</count></item></root> ";
        	
        	Query query = db.execute(input);
        	
    }

    // Retrieve all authors of a given conference
    // Param1: name of conference
    public void query10(String confID) {
    	
    	// e.g. "CONPAR"
    	
    	
    	/*String input = "let $publications := doc('publications.xml')/root "
    			+ "return <root>{ "
    			+ 	"for $p in $publications/proceedings "
    			+ 	"where $p/cid = '" + confID +"' "
    			+ 	"return( "
    			+ 		"for $e in $p/editor "
    			+ 		"return <name><author>{$e/text()}</author></name> "
    			+ 	") "
    			+ "} "
    			+ "{ "
    			+ 	"for $p in $publications/proceedings "
    			+ 	"where $p/cid = '" + confID +"' "
    			+ 	"return ( "
    			+ 		"for $ip in $publications/inproceedings "
    			+ 		"where $ip/pid = $p/id "
    			+ 		"return "
    			+ 			"for $a in $ip/author "
    			+ 			"return <name>{$a}</name> "
    			+ 	") "
    			+ "}</root>";*/
    	
    	String input =
	    	"let $proceedings := doc('proceedings.xml')/root//*[cid/text() = '" + confID + "'], "
			+"$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()] "
			+"return "
			+  "<root>{ "
			+    "for $author in distinct-values($proceedings/editor/text() | $inproceedings/author/text()) "
			+    "return "
			+      "<author>{ "
			+        "<id>{$author}</id> "
			+      "}</author> "
			+  "}</root> ";
    	
    	
    	Query query = db.execute(input);
    	
    }

    // Retrieve all publications of a given conferenc
    // Param 1: name of conference
    public void query11(String confID) {
    	
    	String input =
    			"let $proceedings := doc('proceedings.xml')/root//*[cid/text() = '" + confID + "'], "
    			+ "$inproceedings := doc('inproceedings.xml')/root//*[pid/text() = $proceedings/id/text()] "
    			+ "return <root>{$inproceedings}</root> ";
    		
        	Query query = db.execute(input);
        	
    }
    
    //  list of persons which were at the same time author in an InProceedings and editor in the appropriate Proceedings
    public void query12() {
    	Query query = db.executeFile("query12.xq");
    	
    }

    // Publication with given author as last author
    // Param 1: name of last author
    public void query13(String authorID) {
    	
    	String input =
    			"let $inproceedings :=doc('inproceedings.xml')/root/* "
    			+"return <root>{ "
    			+  "for $ip in $inproceedings "
    			+  "where some $author in $ip/author[last()] satisfies $author/text() = '" + authorID + "' "
    			+  "return $ip "
    			+"}</root> ";
    		
        	Query query = db.execute(input);
        	
    }

    // list of publishers of Proceedings whose authors appear in any InProceedings in a range of years
    // Param 1: year beginning
    // Param 2: year ending
    public void query14(int beginYear, int endYear) {
    	
    	if (beginYear > endYear){
    		int temp = beginYear;
    		beginYear = endYear;
    		endYear = temp;
    	}
    	
    	String input =
    			"let $proceedings := doc('proceedings.xml')/root/*[1982 <= ceid and ceid <= 1986], "
    			+"$inproceedings := doc('inproceedings.xml')/root/*, "
    			+"$publishers := "
    			+  "for $p in $proceedings "
    			+  "where every $e in $p/editor satisfies $e/id/text() = $inproceedings/author/id/text() "
    			+  "return $p/publisher "
    			+"return <root>{ "
    			+  "for $publisher in distinct-values($publishers) "
    			+  "return <publisher><id>{$publisher}</id></publisher> "
    			+"}</root> ";
    		
        	Query query = db.execute(input);
        	
    }
}